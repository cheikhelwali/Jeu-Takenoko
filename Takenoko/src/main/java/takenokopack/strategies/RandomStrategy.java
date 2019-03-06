package takenokopack.strategies;


import takenokopack.controller.Enums;
import takenokopack.models.Bot;
import takenokopack.models.personages.Personage;

import java.util.Random;

public class RandomStrategy extends Strategy {

    @Override
    public int nextAction(Personage gardener, Personage panda, Bot player) {
        /*we generate one action index randomly*/
        return new Random().nextInt(Enums.Action.values().length);
    }
    @Override
    public String toString() {
        return "RandomStrategy";
    }

}
