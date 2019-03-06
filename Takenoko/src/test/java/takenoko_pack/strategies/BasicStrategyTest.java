package takenoko_pack.strategies;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import takenokopack.controller.Enums;
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
import takenokopack.strategies.BasicStrategy;

class BasicStrategyTest {
	private Personage gardener;

	@BeforeEach
	void setUp() throws Exception {
		HexCoordSystem.INSTANCE.resetPlateau();
		
		
	}

	
	

	@Test
	void hexObjTest() {
		Bot player1 = new Bot("oualid") ;
		gardener = new Gardener() ;
		
		HexCoordSystem.INSTANCE.resetPlateau();
		BasicStrategy basicStr = new BasicStrategy() ;
		player1.setStrategy(basicStr);
		
	
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
		
	
		
		assertSame(h2,basicStr.hexObj(player1).get(0)) ;
		assertSame(h3,basicStr.hexObj(player1).get(1)) ;  
		
				
	}
	
	@Test
	void nearlyDoneObjTest() {
		Bot player1 = new Bot("oualid") ;
		
		HexCoordSystem.INSTANCE.resetPlateau();
		BasicStrategy basicStr = new BasicStrategy() ;
		player1.setStrategy(basicStr);
		
	
		
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
		assertSame(h2,basicStr.nearlyDoneObjHex(player1).get(0)) ;
		assertSame(h1,basicStr.nearlyDoneObjHex(player1).get(1)) ; 		
		
		
	}
	
	@Test
	void gardenerActionBasicStrategyTest() {
		Bot player1 = new Bot("oualid") ;
		Personage panda = new Panda();
		Personage gardener = new Gardener();
		HexCoordSystem.INSTANCE.resetPlateau();
		BasicStrategy basicStr = new BasicStrategy();
		player1.setStrategy(basicStr);
		
		// On cree 3 hexagones avec 3 couleurs differents 
		Hexagon h1 = new Hexagon(Enums.Color.GREEN, new DefaultImprovement()) ;
		Hexagon h2 = new Hexagon(Enums.Color.YELLOW, new DefaultImprovement()) ;
		Hexagon h3 = new Hexagon(Enums.Color.PINK, new DefaultImprovement()) ;
					
				
		// on pose les hexagones dans le plateau de jeu 	
		HexCoordSystem.INSTANCE.placeHexagone(h1,null);
		HexCoordSystem.INSTANCE.placeHexagone(h2,null);
		HexCoordSystem.INSTANCE.placeHexagone(h3,null);
		
		player1.getObjectivesToDo().add(new ObjectiveGardener(5,new DefaultImprovement(),
                new Bamboo(Enums.Color.GREEN,4))) ;
		player1.getObjectivesToDo().add(new ObjectiveGardener(5,new DefaultImprovement(),
                new Bamboo(Enums.Color.YELLOW,4))) ;
		player1.getObjectivesToDo().add(new ObjectiveGardener(5,new DefaultImprovement(),
                new Bamboo(Enums.Color.PINK,4))) ;
		
		// on rajout 3 bambous dans l'hexagone vert
		for(int i=0;i<3;i++) {
			h1.addBambooSection();
		}
		
		/*
		 * Dans cette situation le joueur doit choisir de déplacer le jardinier au hexagone qui est proche 
		 * être realisé 
		 * dans ce cas c'est l'hexagone h1
		 */
		
		assertEquals(h1,basicStr.gardenerAction(gardener, panda, player1));
		
	}
	
	@Test
	void pandaActionBasicStrategyTest() {
		Bot player1 = new Bot("oualid") ;
		HexCoordSystem.INSTANCE.resetPlateau();
		Personage panda = new Panda();
		Personage gardener = new Gardener();
		BasicStrategy basicStr = new BasicStrategy();
		player1.setStrategy(basicStr);
		
		// On cree 3 hexagones avec 3 couleurs differents 
		Hexagon h1 = new Hexagon(Enums.Color.GREEN, new DefaultImprovement()) ;
		Hexagon h2 = new Hexagon(Enums.Color.YELLOW, new DefaultImprovement()) ;
		Hexagon h3 = new Hexagon(Enums.Color.PINK, new DefaultImprovement()) ;
							
						
		// on pose les hexagones dans le plateau de jeu 	
		HexCoordSystem.INSTANCE.placeHexagone(h1,null);
		HexCoordSystem.INSTANCE.placeHexagone(h2,null);
		HexCoordSystem.INSTANCE.placeHexagone(h3,null);
		
		player1.getObjectivesToDo().add(new ObjectivePanda(4,
                new Bamboo(Enums.Color.YELLOW),
                new Bamboo(Enums.Color.YELLOW))) ;
		
		h2.addBambooSection();
		
		/*
		 * avec Basic strategy la méthode pandaAction nous renvoie un hexagone qui contient des bambous 
		 * dans ce cas la méthode doit nous renvoyer l'hexagone h3
		 */
		
		assertEquals(h2,basicStr.pandaAction(gardener, panda, player1));
	}

}
