package model.questura;

import java.util.ArrayList;
import java.util.List;

public class HousedExportGroup {
	private HousedExport housedExportLeader = null;
	private List<HousedExport> housedExportMembers = null;
	
	public HousedExportGroup(){
		this.housedExportMembers = new ArrayList<HousedExport>();
	}
	
	public HousedExport getHousedExportLeader() {
		return housedExportLeader;
	}
	public void setHousedExportLeader(HousedExport housedExportLeader) {
		this.housedExportLeader = housedExportLeader;
	}
	public List<HousedExport> getHousedExportMembers() {
		return housedExportMembers;
	}
	public void setHousedExportMembers(List<HousedExport> housedExportMembers) {
		this.housedExportMembers = housedExportMembers;
	}
	
	

	
	

}
