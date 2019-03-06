package takenokopack.models.personages;

import takenokopack.models.HexCoordSystem;
import takenokopack.models.Hexagon;

/**
 * l'interface Personage contient les points communs de la classe Panda
 * et Gardener
 * 
 * @author ultradron
 *
 *
 */
public abstract class Personage {
	
	Hexagon currentHex;

    /*-----Getters/Setters-----*/
	public Hexagon getCurrentHex() {
		return currentHex;
	}

    public void setCurrentHex(Hexagon currentHex) {
		this.currentHex = currentHex;
	}



	/*
     * déplacement  vers une tuile
     */
    public void move(Hexagon hex) {
        if(HexCoordSystem.INSTANCE.getOneHexagon(hex) == null) throw new IllegalArgumentException("Hexagon does'n exist");
        this.currentHex = hex;
    }
    public void reset(){
       currentHex = HexCoordSystem.INSTANCE.getPlateau().get(HexCoordSystem.INSTANCE.makeKeyFromCoords(0,0,0));
    }

	
}