package takenokopack.strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import takenokopack.controller.Enums;
import takenokopack.models.Bot;
import takenokopack.models.HexCoordSystem;
import takenokopack.models.Hexagon;
import takenokopack.models.objectives.Objective;
import takenokopack.models.personages.Personage;

public class BasicStrategy extends Strategy{
	
	private Random rand = new Random();

	
	@Override
    public int nextAction(Personage gardener, Personage panda, Bot player) {
        /*we generate one action index randomly*/
        return new Random().nextInt(Enums.Action.values().length);
    }
	
	@Override
    public String toString() {
        return "BasicStrategy";
    }
	
	@Override
    public Hexagon pandaAction(Personage gardener,Personage panda,Bot player) {
		 List <Hexagon> hexagons=HexCoordSystem.INSTANCE.possibleSlots(panda.getCurrentHex());
		 if(!player.getObjectivesToDo().isEmpty()) {
			 for (Hexagon hexagone : hexagons) {
				 if(hexagone.getBamboo().getNbSections()!= 0)
					 return hexagone ;
			 }
		 }
		 
		 return hexagons.get(rand.nextInt(hexagons.size()));
        
    }
	
	@Override
    public Hexagon gardenerAction(Personage gardener,Personage panda,Bot player) {
		 List <Hexagon> hexagons=HexCoordSystem.INSTANCE.possibleSlots(gardener.getCurrentHex());
		 if(!player.getObjectivesToDo().isEmpty()) {
			  for (Hexagon hexagone : hexagons) {
	                if(this.nearlyDoneObjHex(player).contains(hexagone)) 
	                	return hexagone;
	            }
			  
			  for(Hexagon hex : hexagons) {
				  if(this.hexObj(player).contains(hex))
	                	return hex;
			  }
		 }
		 
		 return hexagons.get(rand.nextInt(hexagons.size()));
			 

    }
	
	public List<Hexagon> nearlyDoneObjHex(Bot player){
    	List<Hexagon> nearlyDoneObjHex = new ArrayList<>();
    	List<Hexagon> HexObjectives = this.hexObj(player) ;
     	for(int i=0;i<HexObjectives.size();i++) {
    		if(HexObjectives.get(i).getBamboo().getNbSections()==3)
    			nearlyDoneObjHex.add(HexObjectives.get(i));
    	}
    	return nearlyDoneObjHex ;
    }
	
	
	public List<Hexagon> hexObj(Bot player){
    	List<Hexagon> hexObjectives = new ArrayList<>() ;
    	for (Objective objective : player.getObjectivesToDo()) {
    		for (Hexagon hexagone : HexCoordSystem.INSTANCE.getPlateau().values()) {
    			if ( objective.getBamboosCombination().get(0).getColor() == hexagone.getColor())
    				hexObjectives.add(hexagone) ;
    			
    		}
    	}
    	
    	return hexObjectives ;
    }
}
