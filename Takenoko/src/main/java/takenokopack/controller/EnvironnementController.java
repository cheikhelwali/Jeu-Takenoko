package takenokopack.controller;

import takenokopack.models.Hexagon;
import takenokopack.models.improvements.DefaultImprovement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class EnvironnementController {
    private static EnvironnementController ourInstance = new EnvironnementController();
    private List<Hexagon> listHexagons = new ArrayList<>();

    public EnvironnementController(){
        initlistHexagons();
    }

    public static EnvironnementController getInstance ()
    {
        return ourInstance;
    }

    /*all hexagones available*/
    public void initlistHexagons(){
        listHexagons.clear();
        //6 hex yellow and 6 green without improvements
        for (int i = 0; i<6;i++){
            listHexagons.add(new Hexagon(Enums.Color.GREEN, new DefaultImprovement()));
            listHexagons.add(new Hexagon(Enums.Color.YELLOW, new DefaultImprovement()));
        }
        //hex green with improvements
        listHexagons.add(new Hexagon(Enums.Color.GREEN, new DefaultImprovement()));//watershed
        listHexagons.add(new Hexagon(Enums.Color.GREEN, new DefaultImprovement()));//watershed
        listHexagons.add(new Hexagon(Enums.Color.GREEN, new DefaultImprovement()));//enclosure
        listHexagons.add(new Hexagon(Enums.Color.GREEN,new DefaultImprovement()));//enclosure
        listHexagons.add(new Hexagon(Enums.Color.GREEN, new DefaultImprovement()));//fertilizer

        //hex yellow with improvements
        listHexagons.add(new Hexagon(Enums.Color.YELLOW, new DefaultImprovement()));//watershed
        listHexagons.add(new Hexagon(Enums.Color.YELLOW, new DefaultImprovement()));//enclosure
        listHexagons.add(new Hexagon(Enums.Color.YELLOW, new DefaultImprovement()));//fertilizer

        //hex pink with improvements
        listHexagons.add(new Hexagon(Enums.Color.PINK, new DefaultImprovement()));//enclosure
        listHexagons.add(new Hexagon(Enums.Color.PINK, new DefaultImprovement()));//watershed
        listHexagons.add(new Hexagon(Enums.Color.PINK,new DefaultImprovement()));//fertilizer
        listHexagons.add(new Hexagon(Enums.Color.PINK, new DefaultImprovement()));//non state
        listHexagons.add(new Hexagon(Enums.Color.PINK, new DefaultImprovement()));//non state
        listHexagons.add(new Hexagon(Enums.Color.PINK, new DefaultImprovement()));//non state
        listHexagons.add(new Hexagon(Enums.Color.PINK, new DefaultImprovement()));//non state
    }

    public List<Hexagon> getThreeHexagons(){
        Random rand = new Random();
        ArrayList<Hexagon> listOfThreeHex = new ArrayList<>();
        if (listHexagons.isEmpty()) return Collections.emptyList(); 

        for (int i = 0; i < Math.min(3, listHexagons.size()); i++)
        {
            listOfThreeHex.add(listHexagons.remove(rand.nextInt(listHexagons.size())));
        }
        return listOfThreeHex;
    }
    public Hexagon getOneHex(Enums.Color color){
        for(int i = 0; i < listHexagons.size();i++)
            if(listHexagons.get(i).getColor().equals(color))
                return  listHexagons.remove(i);
        return listHexagons.remove(new Random().nextInt(listHexagons.size()));
    }
    /**
     A method which put the hexagons not chosen into the list of all hex.
     */
    public void giveBackHexagone(List<Hexagon> restHexagons)
    {
        if (!restHexagons.isEmpty()) listHexagons.addAll(restHexagons);
    }
    /**
     Getter for list of all hexagones
     **/
    public List<Hexagon> getListHexagons(){
        return this.listHexagons;
    }
}
