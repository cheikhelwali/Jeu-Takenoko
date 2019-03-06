package takenokopack.controller.pickers;


public class Picker {
    /*Action picker*/
    private static ActionPicker actionPicker = new ActionPicker();
    public static void resetActionPicker ()
    {
        actionPicker = new ActionPicker();
    }
    public static ActionPicker getActionPicker ()
    {
        return actionPicker;
    }
    /*Irrigation channel picker*/
    private IrrigationPicker irrigationPicker = new IrrigationPicker();
    public IrrigationPicker getIrrigationPicker(){
        return irrigationPicker;
    }
}
