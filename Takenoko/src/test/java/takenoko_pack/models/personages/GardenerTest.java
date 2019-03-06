package takenoko_pack.models.personages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import takenokopack.controller.Enums;
import takenokopack.models.HexCoordSystem;
import takenokopack.models.Hexagon;
import takenokopack.models.improvements.DefaultImprovement;
import takenokopack.models.personages.Gardener;
import takenokopack.models.personages.Personage;

import static org.junit.jupiter.api.Assertions.*;

class GardenerTest {
    private Personage gardener;

    @BeforeEach
    void setUp() {
        HexCoordSystem.INSTANCE.resetPlateau();
        gardener = new Gardener();
    }

    @Test
    void move() {
        /*check if gardener is on the base hexagon*/
        assertSame(gardener.getCurrentHex(), HexCoordSystem.INSTANCE.getPlateau().values().toArray()[0]);
        /*create a new hexagon on the plateau*/
        HexCoordSystem.INSTANCE.placeHexagone(new Hexagon(Enums.Color.GREEN, new DefaultImprovement()),null);
        /*move gardener*/
        gardener.move((Hexagon) HexCoordSystem.INSTANCE.getPlateau().values().toArray()[1]);
        /*check gardener position*/
        assertEquals(gardener.getCurrentHex(),HexCoordSystem.INSTANCE.getPlateau().values().toArray()[1]);
    }

    @Test
    void testException(){
        /*create a new hexagon on the plateau*/
        HexCoordSystem.INSTANCE.placeHexagone(new Hexagon(Enums.Color.GREEN, new DefaultImprovement()),null);
        /*move gardener*/
        gardener.move((Hexagon) HexCoordSystem.INSTANCE.getPlateau().values().toArray()[1]);
        /*move gardener on the same hexagon*/
        try {
            gardener.move(gardener.getCurrentHex());
        }
        catch (IllegalArgumentException e){
            assertEquals("Gardener is already here",e.getMessage());
        }
    }
}