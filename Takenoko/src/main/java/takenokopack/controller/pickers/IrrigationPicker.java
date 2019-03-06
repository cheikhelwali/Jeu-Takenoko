package takenokopack.controller.pickers;

public class IrrigationPicker {
    private static int nbOfIrrigationChannel = 18;

    public boolean existIrrigationChannel(){
        return nbOfIrrigationChannel>0;
    }
    public static void removeIrrigationChannel(){
        nbOfIrrigationChannel--;
    }

}
