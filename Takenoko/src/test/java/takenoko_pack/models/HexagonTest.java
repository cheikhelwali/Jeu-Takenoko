package takenoko_pack.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import takenokopack.controller.Enums;
import takenokopack.models.HexCoordSystem;
import takenokopack.models.Hexagon;
import takenokopack.models.improvements.DefaultImprovement;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HexagonTest {

    @BeforeEach
    void setUp() {
        HexCoordSystem.INSTANCE.resetPlateau();
    }

    @Test
    void getExistingNeighbours() {
        Map<String, Hexagon> plateau = HexCoordSystem.INSTANCE.getPlateau();
        /*put 6 hexagons all around base hex 0_0_0*/
        Hexagon hex1 = new Hexagon(0,1,-1,Enums.Color.NON,new DefaultImprovement());
        Hexagon hex2 = new Hexagon(1,0,-1,Enums.Color.NON,new DefaultImprovement());
        Hexagon hex3 = new Hexagon(1,-1,0,Enums.Color.NON,new DefaultImprovement());
        Hexagon hex4 = new Hexagon(0,-1,1,Enums.Color.NON,new DefaultImprovement());
        Hexagon hex5 = new Hexagon(-1,0,1,Enums.Color.NON,new DefaultImprovement());
        Hexagon hex6 = new Hexagon(-1,1,0,Enums.Color.NON,new DefaultImprovement());
        plateau.put(HexCoordSystem.INSTANCE.makeKey(hex1), hex1);
        plateau.put(HexCoordSystem.INSTANCE.makeKey(hex2), hex2);
        plateau.put(HexCoordSystem.INSTANCE.makeKey(hex3), hex3);
        plateau.put(HexCoordSystem.INSTANCE.makeKey(hex4), hex4);
        plateau.put(HexCoordSystem.INSTANCE.makeKey(hex5), hex5);
        plateau.put(HexCoordSystem.INSTANCE.makeKey(hex6), hex6);

        /*check if base hexagon have 6 neighbours*/
        assertSame(plateau.get("0_0_0").getExistingNeighbours().size(),plateau.size()-1);
    }

    @Test
    void testAddBambooSections(){
        Hexagon hex = new Hexagon(Enums.Color.GREEN,new DefaultImprovement());
        hex.setIrrigated(true);
        assertSame(0,hex.getBamboo().getNbSections());
        hex.addBambooSection();
        hex.addBambooSection();
        hex.addBambooSection();
        hex.addBambooSection();
        hex.addBambooSection();
        assertSame(4,hex.getBamboo().getNbSections());

        hex.removeBamboo();
        hex.removeBamboo();
        hex.removeBamboo();
        hex.removeBamboo();
        hex.removeBamboo();
        hex.removeBamboo();
        assertSame(0,hex.getBamboo().getNbSections());


    }
}