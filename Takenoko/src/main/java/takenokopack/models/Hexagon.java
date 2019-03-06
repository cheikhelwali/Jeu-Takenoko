package takenokopack.models;


import takenokopack.controller.Enums;
import takenokopack.models.improvements.DefaultImprovement;

import java.util.ArrayList;
import java.util.List;

public class Hexagon implements Comparable{
	private int x;
	private int y;
	private int z;
	private boolean isIrrigated=true;
	private Bamboo bamboo;
	private DefaultImprovement improvement;
	private Enums.Color color;

	 public Hexagon(Enums.Color color, DefaultImprovement improvement)
	 {
	     this.color = color;
         this.improvement = improvement;
         bamboo = new Bamboo(color,0);
     }
    public Hexagon(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Hexagon(int x, int y, int z, Enums.Color color, DefaultImprovement improvement)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.color = color;
        this.improvement = improvement;
        bamboo = new Bamboo(color,0);
    }
    /*-----Getters/Setters------*/
    public Enums.Color getColor(){
	     return this.color;
    }

    public DefaultImprovement getImprovement(){
	     return improvement;
    }

	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}

	public int getZ(){
		return this.z;
	}

    public Bamboo getBamboo() {
        return bamboo;
    }

    /*method return true if hexagon is irrigated or improvement is watershed*/
    public boolean getIsIrrigated(){
        if (isIrrigated) return true;
        else return improvement.getIsIrrigated();
    }

    public void setIrrigated(boolean irrigated) {
        isIrrigated = irrigated;
    }

    public void setX(int x) {
		this.x = x;
	}


	public void setY(int y) {
		this.y = y;
	}


	public void setZ(int z) {
		this.z = z;
	}
    /*--------Methods---------*/
	
	/* 
	 * la méthode addBambou sert a ajouter un bambou dans la tuile 
	 *  si cette derniere contient < 4 (nombre maximum de bambou dans une tuile )
	*/
	public void addBambooSection() {
	    if (this.getIsIrrigated())
		for (int i = 0; i < improvement.nbBambooToGrow(); i++) {
			if(bamboo.getNbSections()< 4)
				bamboo.setNbSections(bamboo.getNbSections()+1);
		}
	}

	public Bamboo removeBamboo(){
	    if(bamboo.getNbSections() > 0 && improvement.ifCanEatBamboo()){
	        bamboo.setNbSections(bamboo.getNbSections()-1);
	        return new Bamboo(this.color);
        }
        return null;
    }

	/*
	 * la méthode getNighbours sert a renvoyer les slots voisins d'une tuile
	*/
	public List<Hexagon> getNeighbours() {
        List<Hexagon> listNeighbours = new ArrayList<>();
        /* create hexagons all around this hexagon*/
        listNeighbours.add(new Hexagon(this.getX() + 0, this.getY() + 1, this.getZ() - 1)); //TOP
        listNeighbours.add(new Hexagon(this.getX() + 1, this.getY() + 0, this.getZ() - 1)); //TOP-RIGHT
        listNeighbours.add(new Hexagon(this.getX() + 1, this.getY() - 1, this.getZ() + 0)); //BOTTOM-RIGHT
        listNeighbours.add(new Hexagon(this.getX() + 0, this.getY() - 1, this.getZ() + 1)); //BOTTOM
        listNeighbours.add(new Hexagon(this.getX() - 1, this.getY() + 0, this.getZ() + 1)); //BOTTOM-LEFT
        listNeighbours.add(new Hexagon(this.getX() - 1, this.getY() + 1, this.getZ() + 0)); //TOP-LEFT

        return listNeighbours;
	}
    /* this method will return list of neighbours hexagons which are already placed on the plateau*/
	public List<Hexagon> getExistingNeighbours(){
	    List<Hexagon> existingNeighbours = new ArrayList<>();
        for (Hexagon hex: HexCoordSystem.INSTANCE.getPlateau().values()) {
            for (Hexagon neighbour:this.getNeighbours()) {
                if(hex.equals(neighbour)) existingNeighbours.add(hex);
            }
        }
        return existingNeighbours;
	}

	public boolean irrigatedNeighbourExist(){
	    for (Hexagon neighbour:this.getExistingNeighbours()){
	        if(neighbour.getIsIrrigated())
	            return true;
        }
	    return false;
    }

    @Override
    public String toString() {
        return "Hexagon{" +
                "(" + x + ","+y +","+ z +
                "), bamboos=" + bamboo.getNbSections() +
                ", color=" + color +
                '}';
    }

    

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bamboo == null) ? 0 : bamboo.hashCode());
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((improvement == null) ? 0 : improvement.hashCode());
		result = prime * result + (isIrrigated ? 1231 : 1237);
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
		return result;
	}
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hexagon hexagon = (Hexagon) o;
        return x == hexagon.x &&
                y == hexagon.y &&
                z == hexagon.z;
    }
	/*override this method to sort the hexagons DESC by number of sections */
    @Override
    public int compareTo(Object o) {
        Hexagon hexagon = (Hexagon) o;
        return(hexagon.getBamboo().getNbSections() -this.getBamboo().getNbSections());
    }
}
