package model;

import java.util.ArrayList;
import java.util.List;

public class Group {
	private Housed leader = null;
	private List<Housed> members = null;
	
	public Group(){
		this.members = new ArrayList<Housed>();
	}
	
	
	public String printGroup(){
		StringBuilder sb = new StringBuilder();
		
		sb.append(this.getLeader().getGuest().getFirstName() + "   " + this.getLeader().getHousedType().getDescription() + "\n" );
		for(Housed each: members){
			sb.append(each.getGuest().getFirstName() + " Membro Gruppo o Famiglia"  + "\n");
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
	
	
	

}
