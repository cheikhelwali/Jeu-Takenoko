package takenoko_pack.models.personages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import takenokopack.controller.Enums;
import takenokopack.models.HexCoordSystem;
import takenokopack.models.Hexagon;
import takenokopack.models.improvements.DefaultImprovement;
import takenokopack.models.personages.Panda;
import takenokopack.models.personages.Personage;

import static org.junit.jupiter.api.Assertions.*;

class PandaTest {
    private Personage panda;

    @BeforeEach
    void setUp() {
        HexCoordSystem.INSTANCE.resetPlateau();
        panda = new Panda();
    }

    @Test
    void move() {
        /*check if panda is on the base hexagon*/
        assertSame(panda.getCurrentHex(), HexCoordSystem.INSTANCE.getPlateau().values().toArray()[0]);
        /*create a new hexagon on the plateau*/
        HexCoordSystem.INSTANCE.placeHexagone(new Hexagon(Enums.Color.GREEN, new DefaultImprovement()),null);
        /*move panda*/
        panda.move((Hexagon) HexCoordSystem.INSTANCE.getPlateau().values().toArray()[1]);
        /*check panda position*/
        assertEquals(panda.getCurrentHex(),HexCoordSystem.INSTANCE.getPlateau().values().toArray()[1]);
    }

    @Test
    void testException(){
        /*create a new hexagon on the plateau*/
        HexCoordSystem.INSTANCE.placeHexagone(new Hexagon(Enums.Color.GREEN, new DefaultImprovement()),null);
        /*move panda*/
        panda.move((Hexagon) HexCoordSystem.INSTANCE.getPlateau().values().toArray()[1]);
        /*move panda on the same hexagon*/
        try {
            panda.move(panda.getCurrentHex());
        }
        catch (IllegalArgumentException e){
            assertEquals("Panda is already here",e.getMessage());
        }
    }
}