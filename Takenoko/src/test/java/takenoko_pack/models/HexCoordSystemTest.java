package takenoko_pack.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import takenokopack.controller.Enums;
import takenokopack.models.HexCoordSystem;
import takenokopack.models.Hexagon;
import takenokopack.models.improvements.DefaultImprovement;

import static org.junit.jupiter.api.Assertions.*;

class HexCoordSystemTest {

    @BeforeEach
    void setUp() {
        HexCoordSystem.INSTANCE.resetPlateau();
    }

    @Test
    void freeSlotsTest() {
        //Firstly plot contain 1 hex (0m0m0)
        assertTrue(HexCoordSystem.INSTANCE.getPlateau().containsKey("0_0_0"));
        /*when we have only base hex on the plateau we have 6 free slots*/
        assertEquals(6,HexCoordSystem.INSTANCE.getFreeSlots().size());

        HexCoordSystem.INSTANCE.placeHexagone(new Hexagon(Enums.Color.PINK, new DefaultImprovement()),null);
        //plot contain 2 hex
        assertEquals(2, HexCoordSystem.INSTANCE.getPlateau().size());
        //if we have 2 hex on the plot -> we have 5 free slots
        assertEquals(5, HexCoordSystem.INSTANCE.getFreeSlots().size());

        /*we put more hex on the plot*/
        HexCoordSystem.INSTANCE.placeHexagone(new Hexagon(Enums.Color.GREEN, new DefaultImprovement()),null);
        HexCoordSystem.INSTANCE.placeHexagone(new Hexagon(Enums.Color.GREEN, new DefaultImprovement()),null);
        HexCoordSystem.INSTANCE.placeHexagone(new Hexagon(Enums.Color.GREEN, new DefaultImprovement()),null);
        HexCoordSystem.INSTANCE.placeHexagone(new Hexagon(Enums.Color.GREEN, new DefaultImprovement()),null);

        /*each slot on the free slots array must have more than one hexagon
        * except base hexagon neighbours slots*/
        for (Hexagon slot:HexCoordSystem.INSTANCE.getFreeSlots()) {
            if(!slot.getExistingNeighbours().contains(new Hexagon(0,0,0,Enums.Color.NON,new DefaultImprovement())))
            assertTrue(slot.getExistingNeighbours().size() > 1);
        }
    }
    @Test
    void testIrrigation(){
        //Firstly plot contain 1 hex (0m0m0)
        assertTrue(HexCoordSystem.INSTANCE.getPlateau().containsKey("0_0_0"));
        /*when we have only base hex on the plateau we have 6 free slots*/
        assertEquals(6,HexCoordSystem.INSTANCE.getFreeSlots().size());

        for (int i = 0; i < 27; i++) {
            HexCoordSystem.INSTANCE.placeHexagone(new Hexagon(Enums.Color.PINK, new DefaultImprovement()),null);
        }

        /*we get base's hex neighbours and verify if all neighbours are irrigated*/
        Hexagon base = (Hexagon) HexCoordSystem.INSTANCE.getPlateau().values().toArray()[0];
        for (Hexagon hex:base.getExistingNeighbours()) {
            assertTrue(hex.getIsIrrigated());
        }
    }
}