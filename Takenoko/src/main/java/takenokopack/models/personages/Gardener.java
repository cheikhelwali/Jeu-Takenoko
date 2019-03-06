package takenokopack.models.personages;

import takenokopack.models.HexCoordSystem;
import takenokopack.models.Hexagon;

/**
 * La Classe Gardener qui presente le jardinier dans le jeu takenoko 
 * @author oualidbenazzouz
 *
 */

// Implementation de l'Interface Personage
public class Gardener extends Personage {
	
	public Gardener() {
        this.currentHex = HexCoordSystem.INSTANCE.getPlateau().get(HexCoordSystem.INSTANCE.makeKeyFromCoords(0,0,0));
	}
	
	/* 
	 * déplacement de jardinier vers une tuile et  pousse un Bambou 
	 * dans cette tuile
	*/
	@Override
	public void move(Hexagon nextHex) {
		if(!nextHex.equals(this.currentHex)){

            super.move(nextHex);
           	this.currentHex.addBambooSection();
           	for(Hexagon neighbour:currentHex.getExistingNeighbours()){
           	    if(neighbour.getColor() == currentHex.getColor())
           		neighbour.addBambooSection();
			}

        }
		else throw new IllegalArgumentException("Gardener is already here");
	}

    @Override
    public String toString() {
        return "Gardener{" +
                "currentHex=" + currentHex.getX() +" "+currentHex.getY()+" "+currentHex.getZ()+
                '}';
    }
}
		

