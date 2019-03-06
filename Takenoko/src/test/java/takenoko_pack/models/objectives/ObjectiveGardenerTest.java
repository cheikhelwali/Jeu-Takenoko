package takenoko_pack.models.objectives;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import takenokopack.controller.Enums;
import takenokopack.models.Bamboo;
import takenokopack.models.Bot;
import takenokopack.models.HexCoordSystem;
import takenokopack.models.Hexagon;
import takenokopack.models.improvements.DefaultImprovement;
import takenokopack.models.improvements.Watershed;
import takenokopack.models.objectives.ObjectiveGardener;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ObjectiveGardenerTest {
    Bot player;
    @BeforeEach
    void setUp() {
        player = new Bot("testBot");
        HexCoordSystem.INSTANCE.resetPlateau();
    }

    @Test
    void checkObjectievInsufficientConditio(){
        ObjectiveGardener objG0= new ObjectiveGardener(2,new Watershed(),
                new Bamboo(Enums.Color.GREEN,4),
                new Bamboo(Enums.Color.GREEN,4),
                new Bamboo(Enums.Color.GREEN,4));
        HexCoordSystem.INSTANCE.placeHexagone(new Hexagon(Enums.Color.GREEN,new Watershed()),null);
        HexCoordSystem.INSTANCE.placeHexagone(new Hexagon(Enums.Color.GREEN,new Watershed()),null);
        HexCoordSystem.INSTANCE.placeHexagone(new Hexagon(Enums.Color.GREEN,new DefaultImprovement()),null);
        HexCoordSystem.INSTANCE.placeHexagone(new Hexagon(Enums.Color.GREEN,new DefaultImprovement()),null);
        /*les cas quand on a situation insuffisante pour realiser un objective*/
        for (Hexagon hex : HexCoordSystem.INSTANCE.getPlateau().values()) {
            hex.getBamboo().setNbSections(4);
        }
        assertFalse(objG0.checkObjective(player));


    }

    @Test
    void checkObjective() {
        List<Hexagon> hexagonList = new ArrayList<>();
        hexagonList.add(new Hexagon(Enums.Color.GREEN, new DefaultImprovement()));
        hexagonList.add(new Hexagon(Enums.Color.GREEN, new DefaultImprovement()));
        hexagonList.add(new Hexagon(Enums.Color.GREEN, new DefaultImprovement()));

        hexagonList.add(new Hexagon(Enums.Color.GREEN, new DefaultImprovement()));
        hexagonList.add(new Hexagon(Enums.Color.GREEN, new DefaultImprovement()));
        hexagonList.add(new Hexagon(Enums.Color.GREEN, new DefaultImprovement()));

        hexagonList.add(new Hexagon(Enums.Color.PINK, new Watershed()));
        hexagonList.add(new Hexagon(Enums.Color.YELLOW, new Watershed()));
        hexagonList.add(new Hexagon(Enums.Color.GREEN, new Watershed()));
        for (Hexagon hex:hexagonList) {
            hex.getBamboo().setNbSections(4);
            HexCoordSystem.INSTANCE.placeHexagone(hex,null);
        }

        ObjectiveGardener objG1 = new ObjectiveGardener(2,new DefaultImprovement(),
                                                                new Bamboo(Enums.Color.GREEN,4),
                                                                new Bamboo(Enums.Color.GREEN,4),
                                                                new Bamboo(Enums.Color.GREEN,4));
        ObjectiveGardener objG2 = new ObjectiveGardener(2,new Watershed(),
                                                                new Bamboo(Enums.Color.GREEN,4),
                                                                new Bamboo(Enums.Color.PINK,4),
                                                                new Bamboo(Enums.Color.YELLOW,4));

        assertTrue(objG2.checkObjective(player));
        assertTrue(objG1.checkObjective(player));
        assertTrue(objG1.checkObjective(player));
        assertFalse(objG1.checkObjective(player));

        for (Hexagon hex:HexCoordSystem.INSTANCE.getPlateau().values()) {
            assertSame(0,hex.getBamboo().getNbSections());
        }
    }
}