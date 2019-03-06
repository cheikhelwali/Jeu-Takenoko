package takenokopack.controller.pickers;

import java.util.HashMap;

import takenokopack.controller.Enums;

public class ActionPicker extends HashMap<Enums.Action, Integer> {

    private int nbOfActionsToDo = 2;
    private boolean needAction =false;

    /*for each action we put value 1 player can do (by default) 2 actions one more time for on turn*/
    public ActionPicker(){
        for(Enums.Action action:Enums.Action.values()){
            this.put(action,1);
        }
    }

    public boolean existActions(){
        return nbOfActionsToDo > 0;
        
    }

    public boolean existConcretAction(Enums.Action action){
        return this.get(action) >= 1 && existActions();
    }

    public void lockAction(Enums.Action action){
        this.replace(action,-1);
    }

    public void unlockAction(Enums.Action action){
        if(this.get(action) == -1)
        this.replace(action,1);
    }

    public boolean isNeedAction() {
        return needAction;
    }

    public void setNeedAction(boolean needAction) {
        this.needAction = needAction;
    }

    public void unlockAllActions(){
        for(Enums.Action action:Enums.Action.values()){
            if(this.get(action) == -1)
                this.replace(action,1);
        }
    }

    public void removeAction(Enums.Action action){
        if(this.get(action) > 0){
            int newValue = this.get(action)-1;
            this.replace(action,newValue);
            nbOfActionsToDo--;
        }
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + nbOfActionsToDo;
		result = prime * result + (needAction ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActionPicker other = (ActionPicker) obj;
		if (nbOfActionsToDo != other.nbOfActionsToDo)
			return false;
		return (needAction == other.needAction) ;
			
	}


    
   
}
