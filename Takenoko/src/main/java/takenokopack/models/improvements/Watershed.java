package takenokopack.models.improvements;

import takenokopack.controller.Enums;

public class Watershed extends DefaultImprovement {
    private static final Enums.Improvement improvement = Enums.Improvement.WATERSHED;

    @Override
    public boolean getIsIrrigated() {
        return true;
    }

    @Override
    public String toString() {
        return improvement.toString();
    }

}
