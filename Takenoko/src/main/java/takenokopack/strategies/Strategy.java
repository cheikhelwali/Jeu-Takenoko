package takenokopack.strategies;

import takenokopack.controller.Enums;
import takenokopack.controller.EnvironnementController;
import takenokopack.controller.ObjectivesController;
import takenokopack.controller.Enums.Action;
import takenokopack.controller.pickers.Picker;
import takenokopack.models.Bamboo;
import takenokopack.models.Bot;
import takenokopack.models.HexCoordSystem;
import takenokopack.models.Hexagon;
import takenokopack.models.objectives.Objective;
import takenokopack.models.personages.Personage;

import java.util.*;

public class Strategy {


    private LinkedHashMap<Enums.Action,Double> bestMove = new LinkedHashMap<Enums.Action,Double>();
    private Random rand = new Random();

    public Random getRandom() {
        return rand;
    }
    public LinkedHashMap<Action,Double> getBestMove() {
        return bestMove;
    }

    public Hexagon pandaAction(Personage gardener,Personage panda,Bot player) {
        List <Hexagon> hexagons= HexCoordSystem.INSTANCE.possibleSlots(panda.getCurrentHex());

        //we randomly choose a spot where the panda can go from the arraylist.
        return hexagons.get(rand.nextInt(hexagons.size()));
    }

    public Hexagon gardenerAction(Personage gardener,Personage panda,Bot player) {
        /*get all possible slots where gardener can go*/
        List <Hexagon> hexagons=HexCoordSystem.INSTANCE.possibleSlots(gardener.getCurrentHex());
        //we randomly choose a spot where the gardener can go from the arraylist.
        return  hexagons.get(new Random().nextInt(hexagons.size()));
    }


    public boolean hexagonAction(Bot player,Personage gardener,Personage panda) {
        //Get 3 hex to place
        List<Hexagon> threeHexToPlace = EnvironnementController.getInstance().getThreeHexagons();
        //choose one random hex from 3 available
        Hexagon choosedHex = threeHexToPlace.remove(getRandom().nextInt(threeHexToPlace.size()));
        /*place new hexagon*/
        HexCoordSystem.INSTANCE.placeHexagone(choosedHex,null);
        //return not chosen hexagons back in list from controller
        EnvironnementController.getInstance().giveBackHexagone(threeHexToPlace);
        return true;
    }


    public Objective objectiveAction() {
        return ObjectivesController.getInstance().getOneObjective(null);
    }


    public int nextAction(Personage gardener,Personage panda,Bot player) {
        return new Random().nextInt(Enums.Action.values().length);
    }


    public HashMap<Hexagon, Double> bestSpotToGoGardener(Personage gardener,Personage panda,Bot player) {

        Hexagon currentHex = gardener.getCurrentHex();
        HashMap<Hexagon, Double> gardenerSpots = new HashMap<>();
        List<Hexagon> growedBambooHex;
        // remplir le hashmap par la liste des hexagones ou on peut déplacer le jardinier et avec 0 comme moyenne pour chaque hexagon
        List<Hexagon> posSlots = HexCoordSystem.INSTANCE.possibleSlots(currentHex);
        for (Hexagon slot : posSlots) {
            gardenerSpots.put(slot, 0.0);
        }
        // la simulation .. ( on va reemplir la liste de hashmap par les moyennes )
        for (Map.Entry<Hexagon, Double> entry : gardenerSpots.entrySet()) {
            growedBambooHex= new ArrayList<>();
            // we similate that the gardener moved to this entry of the hashmap which is the Possible slots
            Hexagon hex = entry.getKey();
            gardener.setCurrentHex(hex);
            if(hex.getBamboo().getNbSections() < 4){
                hex.addBambooSection();
                growedBambooHex.add(hex);
            }

            // on cherche les voisins de hex qui ont la même couleur et on ajout un bambou     dans chacun de ces voisins
            for (Hexagon hexagon : hex.getExistingNeighbours()) {
                if (hexagon.getColor()==hex.getColor() && hexagon.getBamboo().getNbSections() < 4) {
                    hexagon.addBambooSection();
                    growedBambooHex.add(hexagon);
                }
            }
            // maintenant on calcule la moyenne !
            player.objectivesRanking(panda,gardener);
            entry.setValue(player.moyenneObjSuccessRatio());
            // we get everything how it was, so simulate the same conditions for the next hexagone.
            for (Hexagon hexagon : growedBambooHex) {
                    hexagon.removeBamboo();
            }
            gardener.setCurrentHex(currentHex);
            player.objectivesRanking(panda,gardener);
        }
        // Collections.max(gardenerSpots.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();
        return gardenerSpots;
    }
  /*
    this method returns an HashMap, that contains all the hexagons that the panda can go to as a key,
    and the value will be the average of the objectives success rates at each hexagon.
    So when we need to choose which hexagon we move the panda to, it will be the hexagon with the highest
    average of objectives ranking.
    */

    public HashMap<Hexagon, Double> bestSpotToGoPanda (Personage gardener,Personage panda,Bot player){

        Hexagon currentHex = panda.getCurrentHex();
        HashMap<Hexagon, Double> pandaPossibleSlots = new HashMap<>();
        boolean ate=false;
        Bamboo bamboo=null;

            /*
            we are going to fill in the hashmap with all the slots that the panda can visit,
            and we put 0 as an average for each entry.
             */
        List<Hexagon> posSlots = new ArrayList<>(HexCoordSystem.INSTANCE.possibleSlots(currentHex));
        for (Hexagon slot : posSlots) {
            pandaPossibleSlots.put(slot, 0.0);
        }
            /*
            this is where we do the simulation of moving the panda for all the possible slots,
            so we can fill in the hashmap with all the averages.
             */
        for (Map.Entry<Hexagon, Double> entry : pandaPossibleSlots.entrySet()) {
            ate = false;
            // we similate that the panda moved to this entry of the hashmap which is one possible slot.
            Hexagon hex = entry.getKey();
            panda.setCurrentHex(hex);
            if(hex.getBamboo().getNbSections()>0) {
                bamboo = hex.removeBamboo(); // if the hexagone has one or more bambo we simulate eating it.
                player.getOwnBamboos().add(bamboo);
                ate=true;
            }
            // we calculate the objective ranking for the new conditions
            player.objectivesRanking(panda,gardener);
            entry.setValue(player.moyenneObjSuccessRatio());
            // we get everything how it was, so simulate the same conditions for the next hexagon.
            if(ate) {
                hex.addBambooSection();
                player.getOwnBamboos().remove(bamboo);
            }
            panda.setCurrentHex(currentHex);
            player.objectivesRanking(panda,gardener);
        }

        return pandaPossibleSlots;
    }

    /*
    this method simulate the action of choosing an hexagon,
    what we do is taking three hexagons from the available hexagons, and try to put each one of them in all
    the freeslots, and then we calculate the average of the success ratio each time we put a hexagon to simulate,
    and then we choose the hexagon that got us the highest average of the success ratio.
     */
    public HashMap<Hexagon,Double> bestHexagonToPlace(Personage gardener,Personage panda,Bot player){
        List<Hexagon> hexTotest=EnvironnementController.getInstance().getThreeHexagons();

        List<Hexagon> freeslots=new ArrayList<>();
        freeslots.addAll(HexCoordSystem.INSTANCE.getFreeSlots());
        HashMap<Hexagon,Double> chosedHexagon = new HashMap<>();
        double avg=0.0;

        for(Hexagon chosenHex:hexTotest) {
            for (int i = 0; i < freeslots.size(); i++) {
                HexCoordSystem.INSTANCE.placeHexagone(chosenHex, freeslots.get(i));
                player.objectivesRanking(panda,gardener);
                if(player.moyenneObjSuccessRatio() >= avg){
                    chosedHexagon.put(chosenHex,player.moyenneObjSuccessRatio());
                }
                HexCoordSystem.INSTANCE.getPlateau().remove(HexCoordSystem.INSTANCE.makeKey(chosenHex));
            }
            HexCoordSystem.INSTANCE.getFreeSlots().clear();
            HexCoordSystem.INSTANCE.getFreeSlots().addAll(freeslots);
            player.objectivesRanking(panda,gardener);
            avg = player.moyenneObjSuccessRatio();
        }
        EnvironnementController.getInstance().giveBackHexagone(hexTotest);
        return chosedHexagon;
    }
    

    public HashMap<Action,Double> bestMoveToDo(Personage gardener,Personage panda,Bot player) {
        double maxAvgGard = 0.0, maxAvgPand = 0.0, maxAvgHex = 0.0;
        HashMap<Action, Double> next = new HashMap<>();

        if (player.getObjectivesToDo().isEmpty() &&
                Picker.getActionPicker().existConcretAction(Action.CHOOSEOBJECTIVE)) {
            next.put(Action.CHOOSEOBJECTIVE, 1.0d);
            return next;
        }

        if (Picker.getActionPicker().existConcretAction(Action.MOVEGARDENER) &&
                HexCoordSystem.INSTANCE.getPlateau().size() > 1) {
            //move Gardener
            maxAvgGard = Collections.max(this.bestSpotToGoGardener(gardener, panda, player).entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getValue();
            next.put(Action.MOVEGARDENER, maxAvgGard);
        }
        if (Picker.getActionPicker().existConcretAction(Action.MOVEPANDA) &&
                HexCoordSystem.INSTANCE.getPlateau().size() > 1) {
            //move panda
            maxAvgPand = Collections.max(this.bestSpotToGoPanda(gardener, panda, player).entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getValue();
            next.put(Action.MOVEPANDA, maxAvgPand);
        }
        if (Picker.getActionPicker().existConcretAction(Action.PLACEHEXAGON) &&
                EnvironnementController.getInstance().getListHexagons().size() > 0) {
            //next hex to place
            maxAvgHex = Collections.max(this.bestHexagonToPlace(gardener, panda, player).entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getValue();
            next.put(Action.PLACEHEXAGON, maxAvgHex);
        }
        return next;
    }
}
