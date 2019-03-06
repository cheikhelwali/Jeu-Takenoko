package takenokopack.models.objectives;

import takenokopack.controller.Enums;
import takenokopack.models.Bamboo;
import takenokopack.models.Bot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObjectivePanda extends Objective{

    public ObjectivePanda(int score, Bamboo... bamboos){
        super();
        this.objectiveType = Enums.ObjectivesType.OBJECTIVEPANDA;
        this.score = score;
        this.getBamboosCombination().addAll(Arrays.asList(bamboos));
    }

    public Enums.ObjectivesType getObjectiveType(){
        return this.objectiveType;
    }

    @Override
    public boolean checkObjective(Bot player) {
        List<Bamboo> foundedBamboos = new ArrayList<>();
        List<Bamboo> tmp = new ArrayList<>();
        /*search if player has bamboos as in objective*/
        for (Bamboo bambooPlayer:player.getOwnBamboos()) {
            for (Bamboo bambooObj:getBamboosCombination()) {
                if(bambooObj.equals(bambooPlayer) && !getBamboosCombination().isEmpty()) {
                    /*si on trouve un bamboo qui apartient au player dans la list bamboos d'objective
                    * on garde le bamboo de player
                    * et on garde le bamboo d'objective dans la variable tmp et on le supprime de la liste
                    * des bamboos d'objectives pour ne pas comprarer plsieurs fois avec le meme bamboo*/
                    foundedBamboos.add(bambooPlayer);
                    tmp.add(bambooObj);
                    getBamboosCombination().remove(bambooObj);
                    break;
                }
            }
        }
        /*if objective's list of bamboos is null -> objectives is realised
        * we remove these bamboos from player arraylist and return true*/
        if (getBamboosCombination().isEmpty()){
            for (Bamboo bamboo:foundedBamboos){
                player.getOwnBamboos().remove(bamboo);
            }
            return true;
        }
        /*si l'objective n'etait pas verifie on rajoute tous les bamboos a la place*/
        else
            getBamboosCombination().addAll(tmp);

        return false;
    }

    @Override
    public String toString() {
        return super.toString();
    }


}
