package takenokopack.controller;

import java.util.List;
import java.util.Random;

import takenokopack.models.objectives.Objective;
import takenokopack.models.objectives.ObjectivesFactory;

public class ObjectivesController {
    private static ObjectivesController objectivesControllerInstance = new ObjectivesController();
    private ObjectivesFactory objectivesFactory = new ObjectivesFactory();
    private List<Objective> objectivePandaList;
    private List<Objective> objectiveGardenerList;

    public ObjectivesController(){
        objectivePandaList = objectivesFactory.getObjectives(Enums.ObjectivesType.OBJECTIVEPANDA);
        objectiveGardenerList = objectivesFactory.getObjectives(Enums.ObjectivesType.OBJECTIVEGARDENER);
    }
    /*-----Getters/Setter*/

    public static ObjectivesController getInstance ()
    {
        return objectivesControllerInstance;
    }

    public boolean existObjectives(){
        return !objectivePandaList.isEmpty() || !objectiveGardenerList.isEmpty();
    }
    public List<Objective> getObjectivePandaList() {
        return objectivePandaList;
    }

    public List<Objective> getObjectiveGardenerList() {
        return objectiveGardenerList;
    }

    /*----------methods------------*/
    public void resetObjectivesController(){
        objectivePandaList = objectivesFactory.getObjectives(Enums.ObjectivesType.OBJECTIVEPANDA);
        objectiveGardenerList = objectivesFactory.getObjectives(Enums.ObjectivesType.OBJECTIVEGARDENER);
    }
    public Objective getOneObjective(Enums.ObjectivesType objectivesType){
        Objective selectedObj;
        int index;
        while (true){

            if(objectivesType == null)
                index = new Random().nextInt(Enums.ObjectivesType.values().length);
            else
                index = Enums.ObjectivesType.valueOf(objectivesType.toString()).ordinal();
            switch (index){
                case 0:
                    if (!objectivePandaList.isEmpty()){
                        selectedObj = objectivePandaList.get(new Random().nextInt(objectivePandaList.size()));
                        objectivePandaList.remove(selectedObj);
                        return selectedObj;
                    }
                    else{
                        objectivesType = Enums.ObjectivesType.OBJECTIVEGARDENER;
                    }
                    break;
                case 1:
                    if (!objectiveGardenerList.isEmpty()){
                        selectedObj = objectiveGardenerList.get(new Random().nextInt(objectiveGardenerList.size()));
                        objectiveGardenerList.remove(selectedObj);
                        return  selectedObj;
                    }
                    else{
                        objectivesType = Enums.ObjectivesType.OBJECTIVEPANDA;
                    }
                    break;
                default:

            }
            if (objectivePandaList.isEmpty() && objectiveGardenerList.isEmpty())
                return null;
        }
    }
}
