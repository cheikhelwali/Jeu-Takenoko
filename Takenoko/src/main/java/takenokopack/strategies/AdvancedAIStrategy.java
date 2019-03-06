package takenokopack.strategies;

import takenokopack.controller.Enums;
import takenokopack.controller.EnvironnementController;
import takenokopack.controller.ObjectivesController;
import takenokopack.controller.pickers.Picker;
import takenokopack.models.Bot;
import takenokopack.models.HexCoordSystem;
import takenokopack.models.Hexagon;
import takenokopack.models.objectives.Objective;
import takenokopack.models.personages.Personage;

import java.util.*;
import java.util.Map.Entry;

public class AdvancedAIStrategy extends Strategy {


    @Override
    public Hexagon pandaAction(Personage gardener,Personage panda,Bot player) {

        HashMap<Hexagon, Double> bestSpotToGoPanda=bestSpotToGoPanda(gardener,panda,player);
        /*
         we  move the gardener to the best spot.
         */
        return Collections.max(bestSpotToGoPanda.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();
    }

    @Override
    public Hexagon gardenerAction(Personage gardener,Personage panda,Bot player) {

        HashMap<Hexagon, Double> bestSpotToGoGardener=bestSpotToGoGardener(gardener,panda,player);
        return Collections.max(bestSpotToGoGardener.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();

    }
    @Override
    public boolean hexagonAction(Bot player,Personage gardener,Personage panda){
        HashMap<Hexagon, Double> bestSpotToPlaceHex=bestHexagonToPlace(gardener,panda,player);
        Hexagon maxHex = Collections.max(bestSpotToPlaceHex.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();
        Hexagon hex = EnvironnementController.getInstance().getOneHex(maxHex.getColor());
        /*place new hexagon*/
        HexCoordSystem.INSTANCE.placeHexagone(hex,maxHex);
        return true;
    }

    @Override
    public Objective objectiveAction () {
        return ObjectivesController.getInstance().getOneObjective(null);
    }

    @Override
    public int nextAction(Personage gardener,Personage panda,Bot player) {
        Double avg = player.moyenneObjSuccessRatio();
        int nextActionOrdinal;
        HashMap<Enums.Action,Double> next = bestMoveToDo(gardener, panda, player);

        if(next.keySet().stream().findFirst().isPresent()) {
            Enums.Action action = Collections.max(next.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();
            if(next.get(action) > avg && Picker.getActionPicker().existConcretAction(action)){
                return action.ordinal();
            }
        }
        nextActionOrdinal = new Random().nextInt(Enums.Action.values().length);
        return nextActionOrdinal;
    }

    @Override
    public String toString () {
        return "AdvancedAIStrategy";
    }
}
    

    
    
