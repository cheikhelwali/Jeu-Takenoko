package takenoko_pack.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import takenokopack.controller.Enums;
import takenokopack.controller.EnvironnementController;
import takenokopack.controller.ObjectivesController;
import takenokopack.models.Bamboo;
import takenokopack.models.Bot;
import takenokopack.models.HexCoordSystem;
import takenokopack.models.Hexagon;
import takenokopack.models.improvements.DefaultImprovement;
import takenokopack.models.objectives.Objective;
import takenokopack.models.objectives.ObjectiveGardener;
import takenokopack.models.objectives.ObjectivePanda;
import takenokopack.models.personages.Gardener;
import takenokopack.models.personages.Panda;
import takenokopack.models.personages.Personage;

import static org.junit.jupiter.api.Assertions.*;

class BotTest {

    Bot player;
    Personage panda;
    Personage gardener;
    @BeforeEach
    void setUp(){
        player = new Bot("TestBot");
        ObjectivesController.getInstance().resetObjectivesController();
        HexCoordSystem.INSTANCE.resetPlateau();
        panda = new Panda();
        gardener = new Gardener();
    }

    @Test
    void bambooSectionCounterTest() {
        assertTrue(player.getOwnBamboos().isEmpty());
        for (int i=0; i<3;i++)
        {
            player.getOwnBamboos().add(new Bamboo(Enums.Color.GREEN));
            player.getOwnBamboos().add(new Bamboo(Enums.Color.PINK));
            player.getOwnBamboos().add(new Bamboo(Enums.Color.YELLOW));
        }
        assertEquals(9,player.getOwnBamboos().size());
        assertEquals(3,player.bambooSectionCounter(player.getOwnBamboos(),Enums.Color.GREEN));
        assertEquals(3,player.bambooSectionCounter(player.getOwnBamboos(),Enums.Color.PINK));
        assertEquals(3,player.bambooSectionCounter(player.getOwnBamboos(),Enums.Color.YELLOW));
        Objective obj =  new ObjectiveGardener(8,new DefaultImprovement(),
                new Bamboo(Enums.Color.GREEN,3),
                new Bamboo(Enums.Color.GREEN,3),
                new Bamboo(Enums.Color.GREEN,3),
                new Bamboo(Enums.Color.GREEN,3));
        assertEquals(0,player.bambooSectionCounter(obj.getBamboosCombination(),Enums.Color.YELLOW));
        assertEquals(0,player.bambooSectionCounter(obj.getBamboosCombination(),Enums.Color.PINK));
        assertEquals(12,player.bambooSectionCounter(obj.getBamboosCombination(),Enums.Color.GREEN));

    }
    @Test
    void requiredBambooSectionTest() {
        assertTrue(player.requiredBambooSection().isEmpty());
        player.getObjectivesToDo().add(new ObjectivePanda(3,new Bamboo(Enums.Color.GREEN),new Bamboo(Enums.Color.GREEN)));
        player.getObjectivesToDo().add(new ObjectivePanda(3,new Bamboo(Enums.Color.PINK),new Bamboo(Enums.Color.PINK)));
        player.getObjectivesToDo().add(new ObjectivePanda(3,new Bamboo(Enums.Color.PINK),new Bamboo(Enums.Color.YELLOW),new Bamboo(Enums.Color.GREEN)));
        assertEquals(3,player.bambooSectionCounter(player.requiredBambooSection(),Enums.Color.GREEN));
        assertEquals(3,player.bambooSectionCounter(player.requiredBambooSection(),Enums.Color.PINK));
        assertEquals(1,player.bambooSectionCounter(player.requiredBambooSection(),Enums.Color.YELLOW));
        player.getOwnBamboos().add(new Bamboo(Enums.Color.YELLOW));
        player.getOwnBamboos().add(new Bamboo(Enums.Color.GREEN));
        player.getOwnBamboos().add(new Bamboo(Enums.Color.PINK));

        assertEquals(2,player.bambooSectionCounter(player.requiredBambooSection(),Enums.Color.GREEN));
        assertEquals(2,player.bambooSectionCounter(player.requiredBambooSection(),Enums.Color.PINK));
        assertEquals(0,player.bambooSectionCounter(player.requiredBambooSection(),Enums.Color.YELLOW));
    }
    @Test
    void objectivesRanking() {
        /*-----------------------------Panda Objectives--------------------------------*/
        player.getObjectivesToDo().add(new ObjectivePanda(3,
                new Bamboo(Enums.Color.GREEN),
                new Bamboo(Enums.Color.GREEN)));
        player.getObjectivesToDo().add(new ObjectivePanda(5,
                new Bamboo(Enums.Color.PINK),
                new Bamboo(Enums.Color.PINK)));

        player.getObjectivesToDo().add(new ObjectivePanda(6,
                new Bamboo(Enums.Color.PINK),
                new Bamboo(Enums.Color.GREEN),
                new Bamboo(Enums.Color.YELLOW)));
        player.getObjectivesToDo().add(new ObjectiveGardener(8,new DefaultImprovement(),
                new Bamboo(Enums.Color.GREEN,3),
                new Bamboo(Enums.Color.GREEN,3),
                new Bamboo(Enums.Color.GREEN,3),
                new Bamboo(Enums.Color.GREEN,3)));
        player.getObjectivesToDo().add(new ObjectiveGardener(6,new DefaultImprovement(),
                new Bamboo(Enums.Color.YELLOW,4)));

        player.getOwnBamboos().add(new Bamboo(Enums.Color.GREEN));
        player.getOwnBamboos().add(new Bamboo(Enums.Color.GREEN));
        player.getOwnBamboos().add(new Bamboo(Enums.Color.GREEN));
        player.objectivesRanking(panda,gardener);

        /*----------------------------------Gardener Objectives--------------------------------------------------*/

        Hexagon hex1 = new Hexagon(0,1,-1,Enums.Color.GREEN,new DefaultImprovement());
        Hexagon hex2 = new Hexagon(1,0,-1,Enums.Color.GREEN,new DefaultImprovement());
        Hexagon hex3 = new Hexagon(1,-1,0,Enums.Color.GREEN,new DefaultImprovement());
        Hexagon hex4 = new Hexagon(0,-1,1,Enums.Color.GREEN,new DefaultImprovement());
        Hexagon hex5 = new Hexagon(-1,0,1,Enums.Color.GREEN,new DefaultImprovement());
        Hexagon hex6 = new Hexagon(-1,1,0,Enums.Color.GREEN,new DefaultImprovement());
        Hexagon hex7 = new Hexagon(-1,2,0,Enums.Color.YELLOW,new DefaultImprovement());
        Hexagon hex8 = new Hexagon(-1,2,0,Enums.Color.YELLOW,new DefaultImprovement());
        hex1.addBambooSection();
        hex1.addBambooSection();
        hex1.addBambooSection();

        hex2.addBambooSection();
        hex2.addBambooSection();
        hex2.addBambooSection();

        hex3.addBambooSection();
        hex3.addBambooSection();
        hex3.addBambooSection();

        hex4.addBambooSection();
        hex4.addBambooSection();
        hex4.addBambooSection();

        hex5.addBambooSection();
        hex5.addBambooSection();
        hex5.addBambooSection();

        hex7.addBambooSection();
        hex7.addBambooSection();
        hex8.addBambooSection();
        hex8.addBambooSection();

        HexCoordSystem.INSTANCE.placeHexagone(hex1,null);
        HexCoordSystem.INSTANCE.placeHexagone(hex2,null);
        HexCoordSystem.INSTANCE.placeHexagone(hex3,null);
        HexCoordSystem.INSTANCE.placeHexagone(hex4,null);
        HexCoordSystem.INSTANCE.placeHexagone(hex5,null);
        HexCoordSystem.INSTANCE.placeHexagone(hex6,null);
        HexCoordSystem.INSTANCE.placeHexagone(hex7,null);
        HexCoordSystem.INSTANCE.placeHexagone(hex8,null);

        player.objectivesRanking(panda,gardener);
        assertTrue(player.getObjectivesToDo().get(0).getSuccessRate() >= player.getObjectivesToDo().get(1).getSuccessRate());
        assertTrue(player.getObjectivesToDo().get(1).getSuccessRate() >= player.getObjectivesToDo().get(2).getSuccessRate());
        assertTrue(player.getObjectivesToDo().get(2).getSuccessRate() >= player.getObjectivesToDo().get(3).getSuccessRate());
    }
}