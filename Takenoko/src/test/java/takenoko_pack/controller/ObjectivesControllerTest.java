package takenoko_pack.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import takenokopack.controller.Enums;
import takenokopack.controller.ObjectivesController;

import static org.junit.jupiter.api.Assertions.*;

class ObjectivesControllerTest {

    @BeforeEach
    void setUp() {
        ObjectivesController.getInstance().resetObjectivesController();
    }

    @Test
    void getOneObjective() {
        int nbTotalOfObjectives = ObjectivesController.getInstance().getObjectiveGardenerList().size()+ObjectivesController.getInstance().getObjectivePandaList().size();

       for (int i=0; i < nbTotalOfObjectives; i++){
           assertNotNull(ObjectivesController.getInstance().getOneObjective(null));
       }
       assertTrue(ObjectivesController.getInstance().getObjectiveGardenerList().isEmpty());
       assertTrue(ObjectivesController.getInstance().getObjectivePandaList().isEmpty());
    }
    @Test
    void getCertainObjective(){
        assertSame(15,ObjectivesController.getInstance().getObjectivePandaList().size());
        assertSame(15,ObjectivesController.getInstance().getObjectiveGardenerList().size());

        for (int i=0; i< 15;i++)
            ObjectivesController.getInstance().getOneObjective(Enums.ObjectivesType.OBJECTIVEPANDA);

        assertTrue(ObjectivesController.getInstance().getObjectivePandaList().isEmpty());
        assertSame(15,ObjectivesController.getInstance().getObjectiveGardenerList().size());


        for (int i=0; i< 50;i++)
            ObjectivesController.getInstance().getOneObjective(Enums.ObjectivesType.OBJECTIVEGARDENER);
        assertTrue(ObjectivesController.getInstance().getObjectivePandaList().isEmpty());
        assertTrue(ObjectivesController.getInstance().getObjectiveGardenerList().isEmpty());
    }

}