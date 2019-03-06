package takenokopack.models;

import takenokopack.controller.Enums;
import takenokopack.controller.EnvironnementController;
import takenokopack.controller.ObjectivesController;
import takenokopack.controller.pickers.Picker;
import takenokopack.models.objectives.Objective;
import takenokopack.models.objectives.ObjectiveGardener;
import takenokopack.models.objectives.ObjectivePanda;
import takenokopack.models.personages.Personage;
import takenokopack.strategies.Strategy;

import java.util.*;

public class Bot {
	private String name;
	private int score;
	private Strategy strategy;
	private List<Bamboo> ownBamboos = new ArrayList<>();
	private List<Objective> objectivesToDo = new ArrayList<>();
	private List<Objective> objectivesRealized = new ArrayList<>();
	public Bot(String name){
	    this.name =name;
	    this.score=0;
    }

    /*-----Getters/Setters-----*/
    public String getName(){
	    return this.name;
    }

    public List<Bamboo> getOwnBamboos() {
        return ownBamboos;
    }

    public List<Objective> getObjectivesToDo() {
        return objectivesToDo;
    }

    public List<Objective> getObjectivesRealized() {
        return objectivesRealized;
    }

    public void setOwnBamboos(List<Bamboo> ownBamboos) {
        this.ownBamboos = ownBamboos;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score += score;
    }

    public void resetScore() {
        this.score = 0;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    /*-----Methods------*/
	public boolean placeHexagon(Personage gardener,Personage panda) {
	    /*if it does not exist placeHexagon action in actionPicker
	    * return false and search another action*/
        if(!Picker.getActionPicker().existConcretAction(Enums.Action.PLACEHEXAGON) ||
                EnvironnementController.getInstance().getListHexagons().isEmpty()) {
            return false;
        }
        /*place new hexagon in strategy*/
        if(!strategy.hexagonAction(this,gardener,panda)){
            return false;
        }

        Picker.getActionPicker().removeAction(Enums.Action.PLACEHEXAGON);
        return true;
    }
	
	 public boolean movePanda(Personage panda,Personage gardener){
        /*if it does note exist action movePanda in the picker and on the board we have
        * less of 2 hexagon return false and search another action*/
	    if(!Picker.getActionPicker().existConcretAction(Enums.Action.MOVEPANDA)||
                HexCoordSystem.INSTANCE.getPlateau().size() < 2) {
            return false;
        }

         Hexagon nextHex = strategy.pandaAction(gardener,panda,this);

         if(nextHex == null){
             return false;
         }

        /*get action from strategy*/
    	panda.move(nextHex);

    	/*panda eat bamboo if it exist*/
        Bamboo eated = panda.getCurrentHex().removeBamboo();
        if(eated!=null)
            ownBamboos.add(eated);
    	Picker.getActionPicker().removeAction(Enums.Action.MOVEPANDA);
         return true;
    }
    
    public boolean moveGardener(Personage gardener,Personage panda){
        /*if it does note exist action moveGardener in the picker and on the board we have
         * less of 2 hexagon return false and search another action*/
        if(!Picker.getActionPicker().existConcretAction(Enums.Action.MOVEGARDENER) ||
                HexCoordSystem.INSTANCE.getPlateau().size() < 2) {
            return false;
        }

    	/*perform action in strategy and get next hex*/
        Hexagon nextHex = strategy.gardenerAction(gardener,panda,this);

        if(nextHex == null){
            return false;
        }
        gardener.move(nextHex);
        Picker.getActionPicker().removeAction(Enums.Action.MOVEGARDENER);
        return true;
    }


    public boolean chooseObjective(){
	    if(!Picker.getActionPicker().existConcretAction(Enums.Action.CHOOSEOBJECTIVE)||
                !ObjectivesController.getInstance().existObjectives() ||
                objectivesToDo.size() == 5) {
            return false;
        }

        objectivesToDo.add(strategy.objectiveAction());

        Picker.getActionPicker().removeAction(Enums.Action.CHOOSEOBJECTIVE);
	    return true;
    }
   
    public void verifyObjectives(){
	    List<Objective> verifiedObjectives = new ArrayList<>();
        if(!objectivesToDo.isEmpty()){
            for (Objective objToVerify:objectivesToDo) {
                if(objToVerify.checkObjective(this)){
                    verifiedObjectives.add(objToVerify);
                    this.setScore(objToVerify.getScore());
                }
            }
            objectivesToDo.removeAll(verifiedObjectives);
            objectivesRealized.addAll(verifiedObjectives);
        }
    }

    /*this method count nb of bamboos section by color for provided list*/
    public int bambooSectionCounter(List<Bamboo> bamboos,Enums.Color bambooColor){
	    int nbBamboo=0;
        for (Bamboo bamboo:bamboos) {
            if(bamboo.getColor().equals(bambooColor)){
                nbBamboo+=bamboo.getNbSections();
            }
        }
	    return nbBamboo;
    }
    /*return list required list of bamboos to meet the objectives in the possession*/
    public List<Bamboo> requiredBambooSection(){
        List<Bamboo> requiredSections = new ArrayList<>();
        List<Bamboo> tmpOwnBamboo = new ArrayList<>(this.getOwnBamboos());
        if(!this.getObjectivesToDo().isEmpty())
        this.getObjectivesToDo().forEach(objective -> {
            if(objective.getObjectiveType().equals(Enums.ObjectivesType.OBJECTIVEPANDA))
            objective.getBamboosCombination().forEach(bamboo -> {
                if (!tmpOwnBamboo.contains(bamboo)) {
                    requiredSections.add(bamboo);
                } else tmpOwnBamboo.remove(bamboo);
            });
        });
        return requiredSections;
    }

    public List<Enums.Action> play(Personage gardener, Personage panda) {
	    /*Firstly we verify objectives(if we have objectives to do) */

	    /* reset actionPicker that means each time we have all the actions available*/
        Picker.resetActionPicker();
        /*list for actions executed ONLY FOR DISPLAY
        * TO REMOVE AFTER
        * */

        List<Enums.Action> actionExecuted = new ArrayList<>();
        while(Picker.getActionPicker().existActions()){
            verifyObjectives();
            /*we choose one action index from strategy for each while iteration*/
            switch (strategy.nextAction(gardener,panda,this)){
                case 0:
                    if(moveGardener(gardener,panda)){
                        actionExecuted.add(Enums.Action.MOVEGARDENER);
                        objectivesRanking(panda,gardener);
                        verifyObjectives();
                    }
                    break;
                case 1:
                    if(movePanda(panda,gardener)){
                        actionExecuted.add(Enums.Action.MOVEPANDA);
                        objectivesRanking(panda,gardener);
                        verifyObjectives();
                    }
                    break;
                case 2:
                    if(chooseObjective()){
                        actionExecuted.add(Enums.Action.CHOOSEOBJECTIVE);
                        objectivesRanking(panda,gardener);
                        verifyObjectives();
                    }
                    break;
                case 3:
                    if(placeHexagon(gardener,panda)){
                        actionExecuted.add(Enums.Action.PLACEHEXAGON);
                        objectivesRanking(panda,gardener);
                        verifyObjectives();
                    }
                    break;
                case -1:
                    actionExecuted.add(null);
                    break ; 
                default :
                	break ; 
            }
            /*we verify objectives(if we have objectives to do) */
        }


        return actionExecuted;
    }

    public void objectivesRanking(Personage panda,Personage gardener){
        double nextStepRatio;
        double ratio = 0d;
        int nbOfBambooSectionRequired;
        int nbOfBambooSectinOwned;
        int objScore;
        for(Objective objective:this.getObjectivesToDo()){
            nbOfBambooSectinOwned = 0;
            /*calc number of sections required for each bamboo color*/
            int greenInObjective = this.bambooSectionCounter(objective.getBamboosCombination(),Enums.Color.GREEN);
            int yellowInObjective = this.bambooSectionCounter(objective.getBamboosCombination(),Enums.Color.YELLOW);
            int pinkInObjective = this.bambooSectionCounter(objective.getBamboosCombination(),Enums.Color.PINK);
            /*calc total of section required*/
            nbOfBambooSectionRequired = greenInObjective + yellowInObjective + pinkInObjective;
            objScore = objective.getScore();

            if(objective instanceof ObjectivePanda){
                /*the step represent progress of the objective at each favorable action*/
                nextStepRatio = 0d;

                /*Calc number of bamboos section owned by the player for each color*/
                int greenOwned = this.bambooSectionCounter(this.ownBamboos,Enums.Color.GREEN);
                int yellowOwned = this.bambooSectionCounter(this.ownBamboos,Enums.Color.YELLOW);
                int pinkOwned = this.bambooSectionCounter(this.ownBamboos,Enums.Color.PINK);

                int requiredGreen = greenInObjective - greenOwned;
                int requiredYellow = yellowInObjective - yellowOwned;
                int requiredPink = pinkInObjective - pinkOwned;

                /*Calc total number of bamboos section owned by the player
                 * if this color is required by objective*/
                nbOfBambooSectinOwned += Math.min(greenOwned,greenInObjective);
                nbOfBambooSectinOwned += Math.min(yellowOwned,yellowInObjective);
                nbOfBambooSectinOwned += Math.min(pinkOwned,pinkInObjective);

                for(Hexagon hexagon:HexCoordSystem.INSTANCE.possibleSlots(panda.getCurrentHex())){
                    if((requiredGreen > 0 && hexagon.getColor().equals(Enums.Color.GREEN) ||
                            (requiredYellow > 0 && hexagon.getColor().equals(Enums.Color.YELLOW)) ||
                            (requiredPink > 0 && hexagon.getColor().equals(Enums.Color.PINK))) &&
                            hexagon.getBamboo().getNbSections()>0){
                        nextStepRatio = 1d/nbOfBambooSectionRequired/2;
                    }
                }

                if(nextStepRatio == 0d){
                    List<Hexagon> intersect = HexCoordSystem.INSTANCE.possibleSlots(panda.getCurrentHex());
                    intersect.retainAll(HexCoordSystem.INSTANCE.possibleSlots(gardener.getCurrentHex()));
                    if( HexCoordSystem.INSTANCE.possibleSlots(panda.getCurrentHex()).contains(gardener.getCurrentHex())
                            && !intersect.contains(gardener.getCurrentHex()))
                        intersect.add(gardener.getCurrentHex());
                    if(!intersect.isEmpty()){
                        for (Hexagon hex:intersect){
                            if((requiredGreen > 0 && hex.getColor().equals(Enums.Color.GREEN) ||
                                    (requiredYellow > 0 && hex.getColor().equals(Enums.Color.YELLOW)) ||
                                    (requiredPink > 0 && hex.getColor().equals(Enums.Color.PINK)))){
                                nextStepRatio = 1d/nbOfBambooSectionRequired/4;
                            }
                        }
                    }
                }
                if(nbOfBambooSectinOwned == nbOfBambooSectionRequired){
                    ratio = 100 + (double)objScore/100;
                }
                else{
                    ratio = (double) nbOfBambooSectinOwned/nbOfBambooSectionRequired + nextStepRatio+(double)objScore/100;
                }
            }

            if(objective instanceof ObjectiveGardener){
                nbOfBambooSectinOwned =0;
                nextStepRatio =0d;
                List<Hexagon> toGrowSection = new ArrayList<>();
                /*this list will contain all hexagons that correspond to our objective*/
                List<Hexagon> foundedHex = new ArrayList<>();

                /*we are looking for all the hexagons that claim to accomplish the goal */
                for (Hexagon hexagon:HexCoordSystem.INSTANCE.getPlateau().values()){
                    if(objective.getBamboosCombination().get(0).getColor().equals(hexagon.getColor())&&
                            hexagon.getBamboo().getNbSections()<=(nbOfBambooSectionRequired)/objective.getBamboosCombination().size()&&
                            !foundedHex.contains(hexagon)){
                        foundedHex.add(hexagon);
                    }
                }
                /*we sort founded hexagons in DESC order by nb of bamboo sections*/
                Collections.sort(foundedHex);

                for(Hexagon hex:foundedHex){
                    if(hex.getBamboo().getNbSections() < (nbOfBambooSectionRequired)/objective.getBamboosCombination().size()){
                        toGrowSection.add(hex);
                    }
                }
                Collections.sort(toGrowSection);

                /*keep first N hexagons with greater number of bamboos
                 * N - NUMBER OF HEX REQUIRED BY OBJECTIVE*/
                if(foundedHex.size() > objective.getBamboosCombination().size()){
                    foundedHex.subList(objective.getBamboosCombination().size(),foundedHex.size()).clear();
                }
                /*Calc number of bamboos sections which are on the hexagons left*/
                for(Hexagon hexagon:foundedHex){
                    nbOfBambooSectinOwned += hexagon.getBamboo().getNbSections();
                }

                for (Hexagon hexagon:HexCoordSystem.INSTANCE.possibleSlots(gardener.getCurrentHex())){
                    if(toGrowSection.contains(hexagon)) {
                        nextStepRatio = 1d/nbOfBambooSectionRequired/2;
                        if (nextStepRatio != 0d) break;
                    }
                }

                if(nextStepRatio == 0d){
                    for(Hexagon hexagon:HexCoordSystem.INSTANCE.possibleSlots(gardener.getCurrentHex())){
                        for (Hexagon foundHex:toGrowSection){
                            if(HexCoordSystem.INSTANCE.possibleSlots(hexagon).contains(foundHex))
                                nextStepRatio = 1d/nbOfBambooSectionRequired/4;
                            if (nextStepRatio != 0d) break;
                        }
                    }
                }

                if(nbOfBambooSectinOwned == nbOfBambooSectionRequired)
                    ratio = 100 + (double)objScore/100;
                else
                    ratio = (double) nbOfBambooSectinOwned/nbOfBambooSectionRequired + nextStepRatio + (double)objScore/100;

            }
            objective.setSuccessRate(ratio);
        }
        Collections.sort(this.getObjectivesToDo());
    }
    public double moyenneObjSuccessRatio(){
        double sum=0;
        if(this.getObjectivesToDo().isEmpty())
        	return 0.0 ;
        for (Objective objective : this.getObjectivesToDo()) {
            sum+=objective.getSuccessRate();
        
        }
        return (sum/this.getObjectivesToDo().size());
    }


}
