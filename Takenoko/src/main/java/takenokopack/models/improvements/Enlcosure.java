package takenokopack.models.improvements;

import takenokopack.controller.Enums;

public class Enlcosure extends DefaultImprovement{
    private static final Enums.Improvement improvement = Enums.Improvement.ENCLOSURE;

    @Override
    public boolean ifCanEatBamboo() {
        return false;
    }

    @Override
    public String toString() {
        return improvement.toString();
    }
}
