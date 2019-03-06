package takenokopack.models.personages;

import takenokopack.models.HexCoordSystem;
import takenokopack.models.Hexagon;

/**
 * La class Panda
 * @author CheikhElWali
 */


//Implementation de l'Interface Personage
public class Panda extends Personage {

	public Panda() {
		this.currentHex = HexCoordSystem.INSTANCE.getPlateau().get(HexCoordSystem.INSTANCE.makeKeyFromCoords(0,0,0));
	}

	/* 
	 * Déplacement de Panda vers une autre Tuile
	 * et qui mange un bambou dans cette Tuile	
	*/
	@Override
	public void move(Hexagon nextHex) {
		if(!nextHex.equals(this.currentHex))
		super.move(nextHex);
		else throw new IllegalArgumentException("Panda is already here");
	}

	@Override
	public String toString() {
		return "Panda{" +
				"currentHex=" + currentHex.getX() +" "+currentHex.getY()+" "+currentHex.getZ()+
				'}';
	}
}

	


