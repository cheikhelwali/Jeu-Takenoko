package takenokopack.models.improvements;

import takenokopack.controller.Enums;

public class Fertilizer extends DefaultImprovement {
    private static final Enums.Improvement improvement = Enums.Improvement.FERTILIZER;

    @Override
    public int nbBambooToGrow() {
        return 2;
    }

    @Override
    public String toString() {
        return improvement.toString();
    }
}
