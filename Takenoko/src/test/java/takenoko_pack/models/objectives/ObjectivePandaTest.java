package takenoko_pack.models.objectives;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import takenokopack.controller.Enums;
import takenokopack.models.Bamboo;
import takenokopack.models.Bot;
import takenokopack.models.Hexagon;
import takenokopack.models.objectives.Objective;
import takenokopack.models.objectives.ObjectivePanda;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ObjectivePandaTest {
    private Bot player;

    @BeforeEach
    void setUp(){
        player = new Bot("testBot");
    }
    @Test
    void checkObjective() {
        List<Bamboo> bambooList = new ArrayList<>();
        Objective objP1 = new ObjectivePanda(2,
                new Bamboo(Enums.Color.GREEN),
                new Bamboo(Enums.Color.GREEN),
                new Bamboo(Enums.Color.GREEN));

        Objective objP2 = new ObjectivePanda(2,
                new Bamboo(Enums.Color.PINK),
                new Bamboo(Enums.Color.YELLOW),
                new Bamboo(Enums.Color.GREEN));

        /*on verifie pour les cas quand le joueur n'a pas le nombre de bamboos suffisante
        * 1 Bamboo de 1 section*/
        bambooList.add(new Bamboo(Enums.Color.GREEN));
        player.setOwnBamboos(bambooList);
        assertEquals(1,player.getOwnBamboos().size());
        assertFalse(objP1.checkObjective(player));
        assertEquals(1,player.getOwnBamboos().size());



        /*2 bamboos de 1 section chaqu'un*/
        bambooList.add(new Bamboo(Enums.Color.GREEN));
        player.setOwnBamboos(bambooList);
        assertEquals(2,player.getOwnBamboos().size());
        assertFalse(objP1.checkObjective(player));
        assertEquals(2,player.getOwnBamboos().size());

        /*3 bamboos de 1 section chaqu'un*/
        bambooList.add(new Bamboo(Enums.Color.GREEN));
        player.setOwnBamboos(bambooList);
        assertEquals(3,player.getOwnBamboos().size());
        assertTrue(objP1.checkObjective(player));
        assertTrue(player.getOwnBamboos().isEmpty());//apres validation d'objective la liste est vide
        assertTrue(objP1.getBamboosCombination().isEmpty());//apres validation d'objective la liste est vide

        /*3 bamboos de couleur different 1 section chaqu'un*/
        bambooList.add(new Bamboo(Enums.Color.GREEN));
        bambooList.add(new Bamboo(Enums.Color.PINK));
        bambooList.add(new Bamboo(Enums.Color.YELLOW));
        player.setOwnBamboos(bambooList);
        assertTrue(objP2.checkObjective(player));
        assertTrue(player.getOwnBamboos().isEmpty());
        assertTrue(objP2.getBamboosCombination().isEmpty());
    }
}