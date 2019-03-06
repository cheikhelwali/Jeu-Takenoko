package takenokopack.models.improvements;

import takenokopack.controller.Enums;

public class DefaultImprovement {
    private static final Enums.Improvement improvement = Enums.Improvement.DEFAULT;
    private static int one = 1 ;

    public int nbBambooToGrow(){
        return one;
    }

    public boolean ifCanEatBamboo(){
        return true;
    }

    public boolean getIsIrrigated ()
    {
        return false;
    }
    @Override
    public String toString(){
        return improvement.toString();
    }

}
