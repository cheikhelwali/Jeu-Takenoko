package takenoko_pack.models.objectives;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import takenokopack.controller.Enums;
import takenokopack.models.Bamboo;
import takenokopack.models.improvements.DefaultImprovement;
import takenokopack.models.objectives.Objective;
import takenokopack.models.objectives.ObjectiveGardener;
import takenokopack.models.objectives.ObjectivePanda;
import takenokopack.models.objectives.ObjectivesFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObjectiveTest {
    private ObjectivesFactory objectivesFactory;

    @BeforeEach
    void setUp() {
        objectivesFactory = new ObjectivesFactory();
    }

    @Test
    void testObjectivesPanda(){
        for (Objective objective:objectivesFactory.getObjectives(Enums.ObjectivesType.OBJECTIVEPANDA)) {
        }
    }
    /*
     we want to know what is the total number of sections of bamboos are in an objective.
     */
    @Test
    void getTotalSectionsNbTest(){

        /*
        In this first objective objG we have 3 bamboos, each contains three sections.
        so the total number of sections of bamboos should be 9
        */
        ObjectiveGardener objG= new ObjectiveGardener(2,new DefaultImprovement(),
                new Bamboo(Enums.Color.GREEN,3),
                new Bamboo(Enums.Color.GREEN,3),
                new Bamboo(Enums.Color.GREEN,3)
                );
        /*
        In this second objective objG2 we have 0 bamboos, that mean 0 sections.
        so the total number of sections of bamboos should be 0
        */
        ObjectiveGardener objG2= new ObjectiveGardener(2,new DefaultImprovement());
        /*
        In this third objective objP1 we have 3 bamboos,
        each contains one sections (because if nb of sections not defined, its 1 as default).
        so the total number of sections of bamboos should be 3
        */
        Objective objP1 = new ObjectivePanda(2,
                new Bamboo(Enums.Color.GREEN),
                new Bamboo(Enums.Color.GREEN),
                new Bamboo(Enums.Color.GREEN));
        assertEquals(9,objG.getTotalSectionsNb());
        assertEquals(0,objG2.getTotalSectionsNb());
        assertEquals(3,objP1.getTotalSectionsNb());
    }



}