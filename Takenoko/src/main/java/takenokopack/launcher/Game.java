package takenokopack.launcher;

import takenokopack.controller.Enums;
import takenokopack.controller.ObjectivesController;
import takenokopack.models.Bot;
import takenokopack.models.HexCoordSystem;
import takenokopack.models.Hexagon;
import takenokopack.models.improvements.DefaultImprovement;
import takenokopack.models.personages.Gardener;
import takenokopack.models.personages.Panda;
import takenokopack.models.personages.Personage;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.NumberFormat;

public class Game {

    Personage gardener = new Gardener();
    Personage panda = new Panda();
    int turn = 0;
    boolean stopGame = false;
    StringBuilder bld = new StringBuilder("");
    private HashMap<String, Statistics> statistics= new HashMap<>();
    NumberFormat nt = NumberFormat.getPercentInstance();

    public void resetGame(List<Bot> playersList){
        for (Bot player:playersList){
            player.getObjectivesRealized().clear();
            player.getObjectivesToDo().clear();
            player.resetScore();
            player.getOwnBamboos().clear();
        }
        HexCoordSystem.INSTANCE.resetPlateau();
        ObjectivesController.getInstance().resetObjectivesController();
        Hexagon base = new Hexagon(0,0,0,Enums.Color.NON,new DefaultImprovement());
        panda.setCurrentHex(base);
        gardener.setCurrentHex(base);
        this.turn = 0;
        this.stopGame = false;
        this.bld = new StringBuilder("");
    }

    public void startGame(int number, List<Bot> playersList, Logger log){
        nt.setMinimumFractionDigits(2);
        for(int i = 0; i < number; i++){
            System.out.println(i);
            resetGame(playersList);
            while(true) {
                if (stopGame)
                    break;

                for (Bot player:playersList) {
                    if(player.getObjectivesRealized().size() >= 6) {
                        player.setScore(2);
                        stopGame = true;
                    }

                    if(!statistics.containsKey(player.getName())) statistics.put(player.getName(), new Statistics(0,0,0));
                    /*get executed actions list
                     * ONLY FOR DISPLAY
                     * TO REMOVE AFTER
                     * */
                    List<Enums.Action> executedAction = player.play(gardener, panda);
//
//                    bld.append("------------------------------------------------------------\n");
//                    bld.append(player.getName());
//                    bld.append(" - ");
//                    bld.append(player.getStrategy());
//                    bld.append(": \n");
//                    bld.append(String.format("\t Actions: %s %n",executedAction.toString()));
//                    bld.append(String.format("\t Turn: %d %n",turn));
//                    bld.append(String.format("\t OwnBamboos: %s %n",player.getOwnBamboos()));
//                    bld.append(String.format("\t objectivesToDo: %s %n",player.getObjectivesToDo()));
//                    bld.append(String.format("\t objectivesRealized: %s %n",player.getObjectivesRealized()));
//
//                    for (Hexagon hexagon : HexCoordSystem.INSTANCE.getPlateau().values()) {
//                        bld.append(String.format("\t %s %n", hexagon.toString()));
//                    }
//                    bld.append(String.format("\t Score: %d %n",player.getScore()));
//                    bld.append(String.format("\t %s %n",panda.toString()));
//                    bld.append(String.format("\t %s %n)",gardener.toString()));
//                    bld.append("\n------------------------------------------------------------");
                }

                turn++;
            }
//            if(playersList.get(0).getScore()>playersList.get(1).getScore()) bld.append(String.format("%n winner is %s with the strategy : %s %n",playersList.get(0).getName(),playersList.get(0).getStrategy().toString()));
//            else bld.append(String.format("%n winner is %s with the strategy : %s %n",playersList.get(1).getName(),playersList.get(1).getStrategy().toString()));
//
//            if(log.isLoggable(Level.ALL)) {
//                log.fine(bld.toString());
//            }

            if(playersList.get(0).getScore() > playersList.get(1).getScore()){
                statistics.get(playersList.get(0).getName()).setWins();
                statistics.get(playersList.get(1).getName()).setLosts();
            } else if(playersList.get(0).getScore() < playersList.get(1).getScore()){
                statistics.get(playersList.get(0).getName()).setLosts();
                statistics.get(playersList.get(1).getName()).setWins();
            }

            statistics.get(playersList.get(0).getName()).addScore(playersList.get(0).getScore());
            statistics.get(playersList.get(1).getName()).addScore(playersList.get(1).getScore());
        }

        bld.append("\n\n\t\t\t\t\t WON \t\t\t LOST \t\t DRAW \t\t Average score \n");
        bld.append("----------------------------------------------------------------------------");
        for (Bot player:playersList) {
            bld.append("\n");
            bld.append(player.getName());
            bld.append("\t\t");
            bld.append(statistics.get(player.getName()).wins);
            bld.append("  ");
            bld.append(nt.format((float) statistics.get(player.getName()).wins/number));
            bld.append("\t\t");
            bld.append(statistics.get(player.getName()).losts);
            bld.append("  ");
            bld.append(nt.format((float) statistics.get(player.getName()).losts/number));
            bld.append("\t\t");
            bld.append(number - statistics.get(player.getName()).wins - statistics.get(player.getName()).losts);
            bld.append("  ");
            bld.append(nt.format((float) (number - statistics.get(player.getName()).wins - statistics.get(player.getName()).losts)/number));
            bld.append("\t\t");
            bld.append(statistics.get(player.getName()).totalScore/number);
            bld.append("\n"+player.getStrategy().toString());
            bld.append("\n----------------------------------------------------------------------------");
        }

        if(log.isLoggable(Level.ALL)) {
            log.fine(bld.toString());
        }
    }

    public class Statistics{
        private int wins;
        private int losts;
        private int totalScore;

        public Statistics(int toalScore, int wins, int losts){
            this.totalScore = toalScore;
            this.wins = wins;
            this.losts = losts;
        }

        public void addScore(int totalScore) {
            this.totalScore += totalScore;
        }

        public void setWins() {
            this.wins++;
        }

        public void setLosts() {
            this.losts++;
        }
    }
}
