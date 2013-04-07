package model.questura;

import java.util.ArrayList;
import java.util.List;

import model.GuestQuesturaFormatter;
import model.Housed;

public class Group {
	private Housed leader = null;
	private List<Housed> members = null;
	private Integer numberOfOccupiedRooms = 0;
	
	public Group(){
		this.members = new ArrayList<Housed>();
	}
	
	
	public String printGroup(){
		StringBuilder sb = new StringBuilder();
		GuestQuesturaFormatter guestQuesturaFormatter = null;
		
		guestQuesturaFormatter = new GuestQuesturaFormatter();
		//guestQuesturaFormatter.setModalita(1);
		guestQuesturaFormatter.setDataFromHousedForRegione(this.getLeader());
		sb.append(guestQuesturaFormatter.getRowRegione());
		//sb.append(this.getLeader().getGuest().getFirstName() + "   " + this.getLeader().getHousedType().getDescription() + "\n" );
		for(Housed each: members){
			if(!each.equals(this.getLeader())){
			guestQuesturaFormatter = new GuestQuesturaFormatter();
			//guestQuesturaFormatter.setModalita(1);
			guestQuesturaFormatter.setDataFromHousedForRegione(each);
			sb.append(guestQuesturaFormatter.getRowRegione());
			}
			//sb.append(each.getGuest().getFirstName() + " Membro Gruppo o Famiglia"  + "\n");
		}
		
		return sb.toString();
	
	}
	public Housed getLeader() {
		return leader;
	}
	public void setLeader(Housed leader) {
		this.leader = leader;
	}
	public List<Housed> getMembers() {
		return members;
	}
	public void setMembers(List<Housed> members) {
		this.members = members;
	}


	public Integer getNumberOfOccupiedRooms() {
		return numberOfOccupiedRooms;
	}


	public void setNumberOfOccupiedRooms(Integer numberOfOccupiedRooms) {
		this.numberOfOccupiedRooms = numberOfOccupiedRooms;
	}	
	

}
