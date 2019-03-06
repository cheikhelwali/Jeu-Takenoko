package takenokopack.launcher;

import takenokopack.models.Bot;
import takenokopack.strategies.AdvancedAIStrategy;
import takenokopack.strategies.BasicStrategy;
import takenokopack.strategies.RandomStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.logging.Level;


public class App {
	/**
	 * La méthode principale de la classe principale du programme
	 * @param args
	 */
	public static void main(String[] args) {
		Logger log = Logger.getLogger("appLogger");
		ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(Level.ALL);
		log.addHandler(handler);
		log.setLevel(Level.ALL);

		List<Bot> playersList = new ArrayList<>();
		playersList.add(new Bot("Player One:"));
		playersList.add(new Bot("Player Two:"));

        playersList.get(0).setStrategy(new RandomStrategy());
        playersList.get(1).setStrategy(new AdvancedAIStrategy());

        Game game = new Game();
        game.startGame(1, playersList, log);
    }
}
