package takenoko_pack.models.improvements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import takenokopack.controller.Enums;
import takenokopack.models.Bot;
import takenokopack.models.HexCoordSystem;
import takenokopack.models.Hexagon;
import takenokopack.models.improvements.DefaultImprovement;
import takenokopack.models.improvements.Fertilizer;
import takenokopack.models.personages.Gardener;
import takenokopack.models.personages.Personage;

import static org.junit.jupiter.api.Assertions.*;

class FertilizerTest {
    Personage gardener;
    @BeforeEach
    void setUp() {
        HexCoordSystem.INSTANCE.resetPlateau();
        gardener = new Gardener();
    }

    @Test
    void nbBambooToGrow() {
        HexCoordSystem.INSTANCE.placeHexagone(new Hexagon(Enums.Color.GREEN,new Fertilizer()),null);
        HexCoordSystem.INSTANCE.placeHexagone(new Hexagon(Enums.Color.GREEN,new DefaultImprovement()),null);
        Hexagon hex = (Hexagon) HexCoordSystem.INSTANCE.getPlateau().values().toArray()[1];
        Hexagon hex2 = (Hexagon) HexCoordSystem.INSTANCE.getPlateau().values().toArray()[2];
        assertEquals(0, hex.getBamboo().getNbSections());
        gardener.move(hex);
        if(hex.getImprovement().toString().equals(Enums.Improvement.FERTILIZER.toString()))
        assertEquals(2, hex.getBamboo().getNbSections());
        gardener.move(hex2);
        gardener.move(hex);
        if(hex.getImprovement().toString().equals(Enums.Improvement.FERTILIZER.toString()))
            assertEquals(4, hex.getBamboo().getNbSections());
    }
}