package takenokopack.controller;

public class Enums {
    public enum Color {
        PINK,
        YELLOW,
        GREEN,
        NON
    }
    public enum Improvement {
        DEFAULT,
        WATERSHED,
        ENCLOSURE,
        FERTILIZER,
    }
    public enum Action {
        MOVEGARDENER,
        MOVEPANDA,
        CHOOSEOBJECTIVE,
        PLACEHEXAGON
        /*,
        placeIrrigationChannel*/
    }
    public enum ObjectivesType{
        OBJECTIVEPANDA,
        OBJECTIVEGARDENER,
    }
}
