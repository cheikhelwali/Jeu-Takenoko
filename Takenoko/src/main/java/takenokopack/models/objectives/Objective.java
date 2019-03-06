package takenokopack.models.objectives;

import takenokopack.controller.Enums;
import takenokopack.models.Bamboo;
import takenokopack.models.Bot;
import takenokopack.models.improvements.DefaultImprovement;

import java.util.ArrayList;
import java.util.List;

public abstract class Objective implements Comparable{
    Enums.ObjectivesType objectiveType;
    private List<Bamboo> bamboosCombination;
    int score;
    DefaultImprovement improvement;
    protected double successRate=0.0;
    Objective(){
        score = 0;
        bamboosCombination = new ArrayList<>();
    }

    public DefaultImprovement getImprovement() {
        return improvement;
    }

    public List<Bamboo> getBamboosCombination() {
        return bamboosCombination;
    }

    public int getTotalSectionsNb(){
        int bamboosNumber=0;
        for(Bamboo bamboo:getBamboosCombination()) {
            bamboosNumber+=bamboo.getNbSections();
        }
        return bamboosNumber;
    }
    public Enums.ObjectivesType getObjectiveType(){
        return objectiveType;
    }
    public int getScore() {
        return score;
    }

    public boolean checkObjective(Bot player){
        return false;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }
    @Override
    public int compareTo(Object o) {
        Objective objective = (Objective) o;
        if(this.successRate<objective.successRate)
            return 1;
        else if(objective.successRate<this.successRate)
            return -1;
        return 0;
    }

    @Override
    public String toString() {
        return "Objective{" +
                objectiveType +
                ", bamboos=" + bamboosCombination +
                ", score=" + score +
                ", ratio=" + successRate +
                "}\n";
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bamboosCombination == null) ? 0 : bamboosCombination.hashCode());
		result = prime * result + ((improvement == null) ? 0 : improvement.hashCode());
		result = prime * result + ((objectiveType == null) ? 0 : objectiveType.hashCode());
		result = prime * result + score;
		long temp;
		temp = Double.doubleToLongBits(successRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Objective other = (Objective) obj;
		if (bamboosCombination == null) {
			if (other.bamboosCombination != null)
				return false;
		} else if (!bamboosCombination.equals(other.bamboosCombination))
			return false;
		if (improvement == null) {
			if (other.improvement != null)
				return false;
		} else if (!improvement.equals(other.improvement))
			return false;
		if (objectiveType != other.objectiveType)
			return false;
		if (score != other.score)
			return false;
		if (Double.doubleToLongBits(successRate) != Double.doubleToLongBits(other.successRate))
			return false;
		return true;
	}

	
    

}
