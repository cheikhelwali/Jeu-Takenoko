package takenoko_pack.strategies;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import takenokopack.controller.Enums;
import takenokopack.controller.EnvironnementController;
import takenokopack.controller.Enums.Color;
import takenokopack.controller.pickers.Picker;
import takenokopack.models.Bamboo;
import takenokopack.models.Bot;
import takenokopack.models.HexCoordSystem;
import takenokopack.models.Hexagon;
import takenokopack.models.improvements.DefaultImprovement;
import takenokopack.models.objectives.ObjectiveGardener;
import takenokopack.models.objectives.ObjectivePanda;
import takenokopack.models.personages.Gardener;
import takenokopack.models.personages.Panda;
import takenokopack.models.personages.Personage;
import takenokopack.strategies.AdvancedAIStrategy;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AdvancedAIStrategyTest {
	 private Personage gardener;

	 private Personage panda;
	 private Bot player;
	@BeforeEach
	void setUp(){
		HexCoordSystem.INSTANCE.resetPlateau();
		gardener = new Gardener() ;
		panda = new Panda();
		player = new Bot("AA");
		player.setStrategy(new AdvancedAIStrategy());
	}

	@Test
	void hexObjTest() {
		Bot player1 = new Bot("oualid") ;
		
		HexCoordSystem.INSTANCE.resetPlateau();
		AdvancedAIStrategy strategieGardener = new AdvancedAIStrategy() ;
		player1.setStrategy(strategieGardener);

	
		// On cree 3 hexagones avec 3 couleurs differents 
		Hexagon h1 = new Hexagon(Enums.Color.GREEN, new DefaultImprovement()) ;
		Hexagon h2 = new Hexagon(Enums.Color.YELLOW, new DefaultImprovement()) ;
		Hexagon h3 = new Hexagon(Enums.Color.PINK, new DefaultImprovement()) ;
		
		
		// On pose les hexagones sur le plateau de jeu 
		HexCoordSystem.INSTANCE.placeHexagone(h1,null);
		HexCoordSystem.INSTANCE.placeHexagone(h2,null);
		HexCoordSystem.INSTANCE.placeHexagone(h3,null);
		
		// On donne 2 objectives a faire pour le joueur (Player1)
		player1.getObjectivesToDo().add(new ObjectiveGardener(6,new DefaultImprovement(),
                new Bamboo(Enums.Color.YELLOW,4))) ;
		player1.getObjectivesToDo().add(new ObjectiveGardener(6,new DefaultImprovement(),
                new Bamboo(Enums.Color.PINK,4))) ;   
		
		/* On verifie bien si la méthode hexObj nous renvoie les hexagones ou on doit deplacer le jardinier 
		 * pour atteindre les objectives de player1
		 * dans ce cas on doit déplacer le jardinier au hexagones h1 et h2 qui ont la même configuration de nos objectives 
		 */
		//assertSame(h2,strategieGardener.hexObj(player1).get(0)) ;
		//assertSame(h3,strategieGardener.hexObj(player1).get(1)) ;
		
				
	}
	
	@Test
	void nearlyDoneObjTest() {
		Bot player1 = new Bot("oualid") ;
		
		HexCoordSystem.INSTANCE.resetPlateau();
		AdvancedAIStrategy strategieGardener = new AdvancedAIStrategy() ;
		player1.setStrategy(strategieGardener);
		// strategieGardener.hexObj(player1) ;
	
		
		// On cree 3 hexagones avec 3 couleurs differents 
		
		Hexagon h1 = new Hexagon(Enums.Color.GREEN, new DefaultImprovement()) ;
		Hexagon h2 = new Hexagon(Enums.Color.YELLOW, new DefaultImprovement()) ;
		Hexagon h3 = new Hexagon(Enums.Color.PINK, new DefaultImprovement()) ;
		
		// on ajout 3 bambous dans l'hexagone h1 et h2 
		
		for(int i=0 ; i<3 ;i++) {
			h1.addBambooSection();
			h2.addBambooSection(); 
		}
		
		// on pose les hexagones dans le plateau de jeu 
		
		HexCoordSystem.INSTANCE.placeHexagone(h1,null);
		HexCoordSystem.INSTANCE.placeHexagone(h2,null);
		HexCoordSystem.INSTANCE.placeHexagone(h3,null);
		
		// On donne 3 objectives a faire pour le joueur (Player1)
		
		player1.getObjectivesToDo().add(new ObjectiveGardener(6,new DefaultImprovement(),
                new Bamboo(Enums.Color.YELLOW,4))) ;
		player1.getObjectivesToDo().add(new ObjectiveGardener(6,new DefaultImprovement(),
                new Bamboo(Enums.Color.GREEN,4))) ;  
		player1.getObjectivesToDo().add(new ObjectiveGardener(6,new DefaultImprovement(),
                new Bamboo(Enums.Color.PINK,4))) ;
		
		
		
		/*
		 * On verifie si la méthode nearlyDoneObjHex() nous renvoie bien les hexagones ou on doit
		 * déplacer le jardinier pour atteindre un des objectifs de joueur (Player1)
		 * 
		 * dans ce cas les hexagones h1 et h2 ont 3 bambous chacun c'est a dire une fois qu'on déplace le jardinier 
		 * à un de ces hexagones , un objectifs sera atteint 
		 */
		//assertSame(h2,strategieGardener.nearlyDoneObjHex(player1).get(0)) ;
		//assertSame(h1,strategieGardener.nearlyDoneObjHex(player1).get(1)) ;

	}
    @Test
    void testObjectivePandaRanking(){
        Hexagon hex2 = new Hexagon(1,0,-1,Enums.Color.YELLOW,new DefaultImprovement());
        Hexagon hex3 = new Hexagon(0,-1,1,Enums.Color.YELLOW,new DefaultImprovement());
        Hexagon hex7 = new Hexagon(1,-1,0,Enums.Color.PINK,new DefaultImprovement());


        HexCoordSystem.INSTANCE.placeHexagone(hex2, hex2);
        HexCoordSystem.INSTANCE.placeHexagone(hex3, hex3);
        HexCoordSystem.INSTANCE.placeHexagone(hex7, hex7);
              /*
        player.getObjectivesToDo().add(new ObjectiveGardener(5,new DefaultImprovement(),
				new Bamboo(Enums.Color.PINK,4)));
			*/
        player.getObjectivesToDo().add(new ObjectivePanda(5,
                new Bamboo(Enums.Color.PINK,1),
                new Bamboo(Enums.Color.PINK,1)));

        /*On pink hexagon we have 0 bamboo sections and in player possession we have also 0 pink bamboos
         * but it is accessible to the gardener and panda in the same time
         * well ranking will be 0/2 + 1/2/4 + 0.05  = 0.175 */
        player.objectivesRanking(panda,gardener);
        assertEquals(0.175d,player.getObjectivesToDo().get(0).getSuccessRate());


        /*move gardener on pink hexagon and he grows a bamboo section*/
        gardener.move(hex7);
        /*now on pink hex we have 1 bamboo section and this hex is accessible to the panda
         * well ranking will be 0/2 + 1/2/2 + 0.05 = 0.3*/
        player.objectivesRanking(panda,gardener);
        assertEquals(0.3d,player.getObjectivesToDo().get(0).getSuccessRate());

        /*eating bamboo section by movement of the panda on pink hex*/
        panda.move(hex7);
        player.getOwnBamboos().add(hex7.removeBamboo());
        /*on pink rest 0 section
         * player owns 1 pink section and we don't have another pink hex accessible
         * ranking will be 1/2 + 0.0 + 0.05 = 0.675*/
        player.objectivesRanking(panda,gardener);
        assertEquals(0.55d,player.getObjectivesToDo().get(0).getSuccessRate());

        /*we add another pink hexagons with 1 bamboo section accessible for the panda */
        Hexagon hex8 = new Hexagon(2,-1,-1,Enums.Color.PINK,new DefaultImprovement());
        hex8.addBambooSection();
        HexCoordSystem.INSTANCE.placeHexagone(hex8, hex8);
        /*ranking will be 1/2 + 1/2/2 + 0.05 = 0.8*/
        player.objectivesRanking(panda,gardener);
        assertEquals(0.8d,player.getObjectivesToDo().get(0).getSuccessRate());
    }
    
    @Test
	void pandaActionTest(){
		Bot player1 = new Bot("oualid") ;
		ArrayList ownBamboos = new ArrayList();
		HexCoordSystem.INSTANCE.resetPlateau();
		Personage panda = new Panda();
		Personage gardener = new Gardener();
		AdvancedAIStrategy advStrategie = new AdvancedAIStrategy() ;
		player1.setStrategy(advStrategie);
		
		
		// On cree 3 hexagones avec 3 couleurs differents 
		Hexagon h1 = new Hexagon(Enums.Color.GREEN, new DefaultImprovement()) ;
		Hexagon h2 = new Hexagon(Enums.Color.YELLOW, new DefaultImprovement()) ;
		Hexagon h3 = new Hexagon(Enums.Color.PINK, new DefaultImprovement()) ;
		
		// on ajout un bambou a l'hexagone jaune 
		h2.addBambooSection();
		
		// on pose les hexagones dans le plateau de jeu 
		HexCoordSystem.INSTANCE.placeHexagone(h1,null);
		HexCoordSystem.INSTANCE.placeHexagone(h2,null);
		HexCoordSystem.INSTANCE.placeHexagone(h3,null);
		
		// on ajout un bambou jaune dans le plateau de joueur 
		ownBamboos.add(new Bamboo(Enums.Color.YELLOW));
		player1.setOwnBamboos(ownBamboos) ;
		
		// on donne au joueur un objectif panda ( le joueur doit avoir deux bambous jaunes pour le realiser)
		player1.getObjectivesToDo().add(new ObjectivePanda(4,
                new Bamboo(Enums.Color.YELLOW),
                new Bamboo(Enums.Color.YELLOW))) ;
	
		/*
		 * comme le joueur a déja un bambou jaune on verifie que la méthode pandaAction nous renvoie l'endroit 
		 * ou on doit déplacer le jardinier pour accomplir l'objectif 
		 * dans ce cas pour accomplir l'objectif il faut déplacer le jardinier dans la tuile jaune pour manger un bambou
		 * et pour terminer l'objectif 
		 */
		
		assertEquals(h2,advStrategie.pandaAction(gardener, panda, player1));
		
	}
	
	@Test
	void gardenerActionTest() {
		
		Bot player1 = new Bot("oualid") ;
		Personage panda = new Panda();
		Personage gardener = new Gardener();
		HexCoordSystem.INSTANCE.resetPlateau();
		AdvancedAIStrategy advStrategie = new AdvancedAIStrategy() ;
		player1.setStrategy(advStrategie);
		
		// On cree 3 hexagones avec 3 couleurs differents 
		Hexagon h1 = new Hexagon(Enums.Color.GREEN, new DefaultImprovement()) ;
		Hexagon h2 = new Hexagon(Enums.Color.YELLOW, new DefaultImprovement()) ;
		Hexagon h3 = new Hexagon(Enums.Color.PINK, new DefaultImprovement()) ;
			
		
		// on pose les hexagones dans le plateau de jeu 	
		HexCoordSystem.INSTANCE.placeHexagone(h1,null);
		HexCoordSystem.INSTANCE.placeHexagone(h2,null);
		HexCoordSystem.INSTANCE.placeHexagone(h3,null);
		
		// on donne un objectif de jardinier (grow 4 bamboos in a plot) au joueur 
		player1.getObjectivesToDo().add(new ObjectiveGardener(5,new DefaultImprovement(),
                        new Bamboo(Enums.Color.GREEN,4))) ;
		
		// on rajout 3 bambous dans l'hexagone vert
		for(int i=0;i<3;i++) {
			h1.addBambooSection();
		}
		
		/*
		 * dans cette situation le joueur doit déplacer le jardinier dans la tuile verte pour accomplir son objectif 
		 * on verifie bien si la méthode gardenerAction nous renvoie l'hexagone h1 pour accomplir l'objectif .
		 */
		
		assertEquals(h1,advStrategie.gardenerAction(gardener, panda, player1));
			
		
	}
	
	@Test
	void nextActionTest() {
		Bot player1 = new Bot("oualid") ;
		ArrayList ownBamboos = new ArrayList();
		Personage panda = new Panda();
		Personage gardener = new Gardener();
		HexCoordSystem.INSTANCE.resetPlateau();
		
		// on va verifier si le joueur choisit la meilleur action ca depend a la situation ou il se trouve 
		
		AdvancedAIStrategy advStrategie = new AdvancedAIStrategy() ;
		player1.setStrategy(advStrategie);
		
		// On cree 3 hexagones avec 3 couleurs differents 
		Hexagon h1 = new Hexagon(Enums.Color.GREEN, new DefaultImprovement()) ;
		Hexagon h2 = new Hexagon(Enums.Color.YELLOW, new DefaultImprovement()) ;
		Hexagon h3 = new Hexagon(Enums.Color.PINK, new DefaultImprovement()) ;
					
				
		// on pose les hexagones dans le plateau de jeu 	
		HexCoordSystem.INSTANCE.placeHexagone(h1,null);
		HexCoordSystem.INSTANCE.placeHexagone(h2,null);
		HexCoordSystem.INSTANCE.placeHexagone(h3,null);
		
		// on donne au joueur un objectif panda ( le joueur doit avoir deux bambous jaunes pour le realiser)
		player1.getObjectivesToDo().add(new ObjectivePanda(4,
		         new Bamboo(Enums.Color.YELLOW),
		         new Bamboo(Enums.Color.YELLOW))) ;
		
		// on donne un objectif de jardinier (grow 4 bamboos in a plot) au joueur 
		player1.getObjectivesToDo().add(new ObjectiveGardener(5,new DefaultImprovement(),
		         new Bamboo(Enums.Color.GREEN,4))) ;
		
		
		// on ajout 3 bambous dans l'hexagone h1 
		for(int i=0;i<3;i++) {
			h1.addBambooSection();
		}
		
		/*
		 * dans cette situation le joueur est proche de finir son objectiveGardener
		 * on verifie bien si sa prochaine action sera de bouger le jardinier vers cette Tuile pour
		 * atteindre son objectif .
		 * 
		 * la méthode nextAction nous renvoie le numéro pour la prochaine action
		 *  0 : pour déplacer le jardinier 
		 *  1 : pour déplacer le panda 
		 *  2 : pour choisir un objectif
		 *  3 : pour placer un hexagone 
		 *  
		 *  dans ce cas la méthode doit nous renvoyer 0
		 */
		assertEquals(0,advStrategie.nextAction(gardener, panda, player1));
	}
    @Test
    void testBestSpotToGoGardner() {

        Bot player = new Bot("cheikh");
        HashMap<Hexagon, Double> gardenerSpots = null;
        Hexagon hex2 = new Hexagon(1, 0, -1, Color.PINK, new DefaultImprovement());
        Hexagon hex3 = new Hexagon(1, -1, 0, Color.PINK, new DefaultImprovement());
        Hexagon hex4 = new Hexagon(2, -1, -1, Color.GREEN, new DefaultImprovement());
        Hexagon hex5 = new Hexagon(2, 0, -2, Color.YELLOW, new DefaultImprovement());
        Hexagon hex6 = new Hexagon(-1, 1, 0, Color.GREEN, new DefaultImprovement());
        Hexagon hex7 = new Hexagon(-1, 0, 1, Color.PINK, new DefaultImprovement());
        HexCoordSystem.INSTANCE.placeHexagone(hex2, hex2);
        HexCoordSystem.INSTANCE.placeHexagone(hex3, hex3);
        HexCoordSystem.INSTANCE.placeHexagone(hex4, hex4);
        HexCoordSystem.INSTANCE.placeHexagone(hex5, hex5);
        HexCoordSystem.INSTANCE.placeHexagone(hex6, hex6);
        HexCoordSystem.INSTANCE.placeHexagone(hex7, hex7);


        /*
        we add two objectives of type gardener to our player's objectives
         - one requires 4 green bamboos and the other one 4 yellow bamboos
         */
        player.getObjectivesToDo().add(new ObjectiveGardener(6, new DefaultImprovement(),
                new Bamboo(Color.YELLOW, 4)));
        player.getObjectivesToDo().add(new ObjectiveGardener(5, new DefaultImprovement(),
                new Bamboo(Color.GREEN, 4)));

        /*
        we want to create a situation where the are three hexagons in the game, each one of them have 3 section of bamboo.
        Green hexagon with 3 bamboos, another green with three bamboos and a yellow one with 3 bamboo.
        the gardener can reach the yellow hexagon and only one green hexagon, but the other green hexagon,
        is reachable from the yellow plot.
        So we are going to verify if the bot will choose the yellow hexagon, as the one to move the gardener to.
         */

        for (int i = 0; i < 3 ; i++) {
            hex6.addBambooSection();
            hex5.addBambooSection();
            hex4.addBambooSection();
        }

        /*the bestSpotToGoGardener will return us all the hexagon visited by the gardener (Simulation) and the ranking
        of the objectives at each plot visited.
        */
        gardenerSpots = (new AdvancedAIStrategy()).bestSpotToGoGardener(gardener, panda, player);
        /*
        And here we choose the plot that got us the highest average.
         */
        Hexagon hexagoneMax = Collections.max(gardenerSpots.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();
        /*
        And indeed its the hexagon with the color yellow that has the maximum average.
        Hex5 has yellow color.
         */
        assertEquals(hexagoneMax,hex5);

    }

    @Test
    void testbestHexagonToPlace() {

	    double avg1=0,avg2=0;
        Bot player = new Bot("cheikh");

        Hexagon hex2 = new Hexagon(1, 0, -1, Color.YELLOW, new DefaultImprovement());
        Hexagon hex3 = new Hexagon(1, -1, 0, Color.GREEN, new DefaultImprovement());
        Hexagon hex4 = new Hexagon(0, 1, -1, Color.GREEN, new DefaultImprovement());
        Hexagon hex6 = new Hexagon(-1, 1, 0, Color.GREEN, new DefaultImprovement());
        Hexagon hex7 = new Hexagon(-1, 0, 1, Color.YELLOW, new DefaultImprovement());
        Hexagon hex8 = new Hexagon(0, -1, 1, Color.PINK, new DefaultImprovement());
        Hexagon hex9 = new Hexagon(2, -1, 1, Color.PINK, new DefaultImprovement());

        HexCoordSystem.INSTANCE.placeHexagone(hex2, hex2);
        HexCoordSystem.INSTANCE.placeHexagone(hex3, hex3);
        HexCoordSystem.INSTANCE.placeHexagone(hex4, hex4);
        HexCoordSystem.INSTANCE.placeHexagone(hex6, hex6);
        HexCoordSystem.INSTANCE.placeHexagone(hex7, hex7);

         /*
        Here we want to create a situation, where we have an objective of type gardener, that need an hexagon of color PINK.
        but in the game right now we don't have one,
         */

        player.getObjectivesToDo().add(new ObjectiveGardener(6, new DefaultImprovement(),
                new Bamboo(Color.PINK, 4)));

       /*
       We are going to put an hexagone of color pink, but in a slot were the gardener can't reach it in one step.
       And calculate the objectives ranking, then we are going to put an hexagon with color PINK, but this time
       the gardener can reach it, then we update our objectives ranking.
       we should have the latest average ration bigger than the previous one, the previous being when the gardener couldn't reach the plot
        */


        HexCoordSystem.INSTANCE.placeHexagone(hex9, hex9);
        player.objectivesRanking(panda,gardener);
        avg2=player.moyenneObjSuccessRatio();

        HexCoordSystem.INSTANCE.placeHexagone(hex8, hex8);
        player.objectivesRanking(panda,gardener);
        avg1=player.moyenneObjSuccessRatio();


       assertTrue(avg1>avg2);



    }

}
