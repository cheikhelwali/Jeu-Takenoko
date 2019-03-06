package takenokopack.models.objectives;

import takenokopack.controller.Enums;
import takenokopack.models.Bamboo;
import takenokopack.models.improvements.DefaultImprovement;

import java.util.ArrayList;
import java.util.List;

/**
 Creation d'une fabrique des objectives qui va permet de generé des listes
 des objectives concrete en passant le nom d'objectif desiré en parametre
 */
public class ObjectivesFactory {
    public List<Objective> getObjectives(Enums.ObjectivesType type) {
        List<Objective> listOfObjectives = new ArrayList<>();
        if(type == null)  throw new IllegalArgumentException("Type of obejctif not found");
        switch (type){
            case OBJECTIVEPANDA:
                for (int i = 0; i <5 ; i++) {
                    listOfObjectives.add(new ObjectivePanda(3,
                            new Bamboo(Enums.Color.GREEN),
                            new Bamboo(Enums.Color.GREEN)));
                }
                for (int i = 0; i < 4; i++) {
                    listOfObjectives.add(new ObjectivePanda(4,
                            new Bamboo(Enums.Color.YELLOW),
                            new Bamboo(Enums.Color.YELLOW)));
                }
                for (int i = 0; i < 3; i++) {
                    listOfObjectives.add(new ObjectivePanda(5,
                            new Bamboo(Enums.Color.PINK),
                            new Bamboo(Enums.Color.PINK)));
                }
                for (int i = 0; i < 3; i++) {
                    listOfObjectives.add(new ObjectivePanda(6,
                            new Bamboo(Enums.Color.GREEN),
                            new Bamboo(Enums.Color.PINK),
                            new Bamboo(Enums.Color.YELLOW)));
                }
                return listOfObjectives;

            case OBJECTIVEGARDENER:
                /*Green bamboos objectives*/
                listOfObjectives.add(new ObjectiveGardener(8,new DefaultImprovement(),
                        new Bamboo(Enums.Color.GREEN,3),
                        new Bamboo(Enums.Color.GREEN,3),
                        new Bamboo(Enums.Color.GREEN,3),
                        new Bamboo(Enums.Color.GREEN,3)));
                listOfObjectives.add(new ObjectiveGardener(5,new DefaultImprovement(),
                        new Bamboo(Enums.Color.GREEN,4)));
                listOfObjectives.add(new ObjectiveGardener(4,new DefaultImprovement(),
                        new Bamboo(Enums.Color.GREEN,4)));
                listOfObjectives.add(new ObjectiveGardener(4,new DefaultImprovement(),
                        new Bamboo(Enums.Color.GREEN,4)));//Enclosure
                listOfObjectives.add(new ObjectiveGardener(3,new DefaultImprovement(),
                        new Bamboo(Enums.Color.GREEN,4)));//Fertilized

                /*Yellow bamboos objectives*/
                listOfObjectives.add(new ObjectiveGardener(7,new DefaultImprovement(),
                        new Bamboo(Enums.Color.YELLOW,3),
                        new Bamboo(Enums.Color.YELLOW,3),
                        new Bamboo(Enums.Color.YELLOW,3)));

                listOfObjectives.add(new ObjectiveGardener(6,new DefaultImprovement(),
                        new Bamboo(Enums.Color.YELLOW,4)));

                listOfObjectives.add(new ObjectiveGardener(5,new DefaultImprovement(),
                        new Bamboo(Enums.Color.YELLOW,4)));
                listOfObjectives.add(new ObjectiveGardener(4,new DefaultImprovement(),
                        new Bamboo(Enums.Color.YELLOW,4)));//Fertilized
                listOfObjectives.add(new ObjectiveGardener(5,new DefaultImprovement(),
                        new Bamboo(Enums.Color.YELLOW,4)));//Enclosure
                /*Pink bamboos objectives*/
                listOfObjectives.add(new ObjectiveGardener(6,new DefaultImprovement(),
                        new Bamboo(Enums.Color.PINK,3),
                        new Bamboo(Enums.Color.PINK,3)));

                listOfObjectives.add(new ObjectiveGardener(7,new DefaultImprovement(),
                        new Bamboo(Enums.Color.PINK,4)));

                listOfObjectives.add(new ObjectiveGardener(6,new DefaultImprovement(),
                        new Bamboo(Enums.Color.PINK,4)));
                listOfObjectives.add(new ObjectiveGardener(5,new DefaultImprovement(),
                        new Bamboo(Enums.Color.PINK,4)));//Fertilized
                listOfObjectives.add(new ObjectiveGardener(6,new DefaultImprovement(),
                        new Bamboo(Enums.Color.PINK,4)));//Enclosure

                return listOfObjectives;
        }

        return null;
    }
}
