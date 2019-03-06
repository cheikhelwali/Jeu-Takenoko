package takenokopack.models;

import takenokopack.controller.Enums;
import takenokopack.controller.EnvironnementController;
import takenokopack.models.improvements.DefaultImprovement;

import java.util.*;

public enum HexCoordSystem {

	INSTANCE;
	/*Attributes*/
	private HashMap<String, Hexagon> plateau = new HashMap<>();
	private List<Hexagon> freeSlots = new ArrayList<>();
	private Random rand = new Random();

	HexCoordSystem(){
		resetPlateau();
    }
	/*-----Getters/Setters-----*/
	public Map<String, Hexagon> getPlateau(){
		return this.plateau;
	}

	public Hexagon getOneHexagon(Hexagon hexagon){
		return this.getPlateau().get(makeKey(hexagon));
	}

	public List<Hexagon> getFreeSlots() {
        return freeSlots;
    }

	/*-------Methods---------*/
	public void placeHexagone(Hexagon hexagon, Hexagon slotToPlace) {
            int index = rand.nextInt(getFreeSlots().size());
            if(slotToPlace == null){
				hexagon.setX(getFreeSlots().get(index).getX());
				hexagon.setY(getFreeSlots().get(index).getY());
				hexagon.setZ(getFreeSlots().get(index).getZ());
			}
			else{
				hexagon.setX(slotToPlace.getX());
				hexagon.setY(slotToPlace.getY());
				hexagon.setZ(slotToPlace.getZ());
			}
            
            this.plateau.put(this.makeKey(hexagon), hexagon);
            updateFreeSlots(hexagon);
    }

	public void updateFreeSlots(Hexagon hexagon){
            /*we add all hexagon's neighbours to the list which are
	    *not already in the list
	    *not already on the plateau
	    *have more than 1 neighbours*/
	    for (Hexagon neighbour : getOneHexagon(hexagon).getNeighbours()){
	        if( !getFreeSlots().contains(neighbour) &&
                !plateau.containsKey(makeKey(neighbour)) &&
                neighbour.getExistingNeighbours().size() > 1)
	            getFreeSlots().add(neighbour);
        }
        /*we remove current hexagon from the list of free slots*/
	    this.getFreeSlots().remove(hexagon);
    }

    /*reset plateau will delete all hex and put the first hex on the plateau*/
    public void resetPlateau(){
		EnvironnementController.getInstance().initlistHexagons();
        plateau.clear();
        getFreeSlots().clear();
        Hexagon base = new Hexagon(0,0,0,Enums.Color.NON,new DefaultImprovement());
        base.setIrrigated(false);
        plateau.put(makeKey(base), base);
        /*after initialization all neighbours slots of 0,0,0 are free*/
        getFreeSlots().addAll(plateau.get(makeKeyFromCoords(0,0,0)).getNeighbours());
    }

	public String makeKey(Hexagon hexagon) {
		return String.format("%d_%d_%d", hexagon.getX(), hexagon.getY(), hexagon.getZ());
	}

	public String makeKeyFromCoords(int x,int y,int z){
        return String.format("%d_%d_%d",x, y, z);
    }

		public List<Hexagon> possibleSlots(Hexagon hexagone) {
			ArrayList<Hexagon> possiblePos = new ArrayList<>();
			for (Hexagon hex : this.plateau.values()) {
				if (!hex.equals(hexagone)&& (hexagone.getX() == hex.getX() || hexagone.getY() == hex.getY() || hexagone.getZ() == hex.getZ()))
					possiblePos.add(hex);
				}
			return possiblePos;
		}

}