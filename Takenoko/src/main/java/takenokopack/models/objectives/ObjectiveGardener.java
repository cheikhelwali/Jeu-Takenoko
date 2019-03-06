package takenokopack.models.objectives;

import takenokopack.controller.Enums;
import takenokopack.models.Bamboo;
import takenokopack.models.Bot;
import takenokopack.models.HexCoordSystem;
import takenokopack.models.Hexagon;
import takenokopack.models.improvements.DefaultImprovement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObjectiveGardener extends Objective{

    public ObjectiveGardener(int score, DefaultImprovement improvement, Bamboo... bamboos){
        super();
        this.objectiveType = Enums.ObjectivesType.OBJECTIVEGARDENER;
        this.score = score;
        this.improvement = improvement;
        this.getBamboosCombination().addAll(Arrays.asList(bamboos));
    }

    @Override
    public boolean checkObjective(Bot player) {
        List<Hexagon> foundedHexagons = new ArrayList<>();
        /*Search all hexagons which have objective's combination of bamboos*/
        for (Bamboo bambooObj:getBamboosCombination()){
            for (Hexagon hex:HexCoordSystem.INSTANCE.getPlateau().values()){
                if(bambooObj.equals(hex.getBamboo()) &&
                    !foundedHexagons.contains(hex)&&
                    foundedHexagons.size() < getBamboosCombination().size()&&
                        improvement.toString().equals(hex.getImprovement().toString())){
                    foundedHexagons.add(hex);
                }
            }
        }

        if(foundedHexagons.size() == getBamboosCombination().size()) {
            for (Hexagon hex:foundedHexagons) {
                Bamboo hexBamboo = HexCoordSystem.INSTANCE.getOneHexagon(hex).getBamboo();
                    HexCoordSystem.INSTANCE.getOneHexagon(hex).getBamboo().setNbSections(hexBamboo.getNbSections()-getBamboosCombination().get(0).getNbSections());
            }
            return true;
        }
        return false;
    }
    @Override
    public String toString() {
        return super.toString();
    }


}
