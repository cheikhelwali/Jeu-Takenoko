package takenoko_pack.models.improvements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import takenokopack.controller.Enums;
import takenokopack.models.Bamboo;
import takenokopack.models.Bot;
import takenokopack.models.HexCoordSystem;
import takenokopack.models.Hexagon;
import takenokopack.models.improvements.Enlcosure;
import takenokopack.models.personages.Panda;
import takenokopack.models.personages.Personage;
import takenokopack.strategies.RandomStrategy;

import static org.junit.jupiter.api.Assertions.*;

class EnlcosureTest {
    Personage panda;
    Bot player;
    @BeforeEach
    void setUp() {
        HexCoordSystem.INSTANCE.resetPlateau();
        panda = new Panda();
        player = new Bot("TestBot");
        player.setStrategy(new RandomStrategy());
    }

    @Test
    void ifCanEatBamboo() {
        HexCoordSystem.INSTANCE.placeHexagone(new Hexagon(Enums.Color.GREEN,new Enlcosure()),null);
            Hexagon hex = (Hexagon) HexCoordSystem.INSTANCE.getPlateau().values().toArray()[1];
            hex.addBambooSection();
            hex.addBambooSection();
            hex.addBambooSection();
            hex.addBambooSection();
        assertEquals(4, hex.getBamboo().getNbSections());
            player.movePanda(panda,null);
            player.movePanda(panda,null);
            player.movePanda(panda,null);
        assertEquals(4, hex.getBamboo().getNbSections());
    }
}