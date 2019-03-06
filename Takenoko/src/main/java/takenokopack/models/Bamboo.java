package takenokopack.models;

import takenokopack.controller.Enums;



public class Bamboo {
    private Enums.Color color;
    private int nbSections;

    public Bamboo(Enums.Color color){
        this.color = color;
        nbSections = 1;
    }

    public Bamboo(Enums.Color color, int nbSections){
        this.color = color;
        this.nbSections = nbSections;
    }

    public Enums.Color getColor() {
        return color;
    }

    public void setColor(Enums.Color color) {
        this.color = color;
    }

    public int getNbSections() {
        return nbSections;
    }

    public void setNbSections(int nbSections) {
        this.nbSections = nbSections;
    }

    @Override
    public String toString() {
        return "Bamboo{" +
                "color=" + color +
                ", nbSections=" + nbSections +
                '}';
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + nbSections;
		return result;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bamboo bamboo = (Bamboo) o;
        return nbSections == bamboo.nbSections &&
                color == bamboo.color;
    }
    

}
