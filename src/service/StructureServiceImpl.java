package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Booking;
import model.Extra;
import model.Image;
import model.RoomFacility;
import model.RoomType;
import model.Structure;
import model.StructureFacility;
import model.listini.Convention;
import model.listini.ExtraPriceList;
import model.listini.ExtraPriceListItem;
import model.listini.RoomPriceList;
import model.listini.RoomPriceListItem;
import model.listini.Season;

@Service
public class StructureServiceImpl implements StructureService{
	@Autowired
	private SeasonService seasonService = null;
	
	public Double calculateExtraItemUnitaryPrice(Structure structure, Date dateIn, Date dateOut, RoomType roomType, Convention convention, Extra extra) {
		Double ret = 0.0;
		ExtraPriceList priceList = null;
		ExtraPriceList otherPriceList = null;
		Season season = null;
		Season otherSeason = null;
		Double price = 0.0;
		Double otherPrice = 0.0;
		
		season = this.getSeasonService().findSeasonByDate(structure.getId(),dateIn );
		priceList = structure.findExtraPriceListBySeasonAndRoomTypeAndConvention(season, roomType,convention);
		price = priceList.findExtraPrice(extra);
		
		//se ho un booking a cavallo di due stagioni, prendo il prezzo più basso
		otherSeason = this.getSeasonService().findSeasonByDate(structure.getId(),dateOut );
		otherPriceList = structure.findExtraPriceListBySeasonAndRoomTypeAndConvention(otherSeason, roomType, convention);	
		price = priceList.findExtraPrice(extra);
		otherPrice = otherPriceList.findExtraPrice(extra);
		
		ret = Math.min(price,otherPrice);
		
		return ret;
	}
	
	
	public void refreshPriceLists(Structure structure){
		RoomPriceList newRoomPriceList = null;
		ExtraPriceList newExtraPriceList = null;
		RoomPriceListItem newRoomPriceListItem = null;
		ExtraPriceListItem newExtraPriceListItem = null;
		Double[] prices = null;
		Double price = 0.0;
		
		//for (Season eachSeason : structure.getSeasons()) {
		for (Season eachSeason : this.getSeasonService().findSeasonsByStructureId(structure.getId())) {
			for (RoomType eachRoomType : structure.getRoomTypes()) {
				for (Convention eachConvention : structure.getConventions()) {
					newRoomPriceList = new RoomPriceList();
					newRoomPriceList.setId(structure.nextKey());
					newRoomPriceList.setSeason(eachSeason);
					newRoomPriceList.setRoomType(eachRoomType);
					newRoomPriceList.setConvention(eachConvention);
					List<RoomPriceListItem> roomItems = new ArrayList<RoomPriceListItem>();
					for (int i=1; i<=eachRoomType.getMaxGuests(); i++) {
						newRoomPriceListItem = new RoomPriceListItem();
						newRoomPriceListItem.setId(structure.nextKey());
						newRoomPriceListItem.setNumGuests(i);
						prices = new Double[7];
						for (int y=0; y<7; y++) {
							prices[y] = 0.0;
						}
						newRoomPriceListItem.setPrices(prices);
						roomItems.add(newRoomPriceListItem);
					}
					newRoomPriceList.setItems(roomItems);
					structure.addRoomPriceList(newRoomPriceList);
					
					newExtraPriceList = new ExtraPriceList();
					newExtraPriceList.setId(structure.nextKey());
					newExtraPriceList.setSeason(eachSeason);
					newExtraPriceList.setRoomType(eachRoomType);
					newExtraPriceList.setConvention(eachConvention);
					List<ExtraPriceListItem> extraItems = new ArrayList<ExtraPriceListItem>();
					for (Extra eachExtra : structure.getExtras()) {
						newExtraPriceListItem = new ExtraPriceListItem();
						newExtraPriceListItem.setId(structure.nextKey());
						newExtraPriceListItem.setExtra(eachExtra);
						newExtraPriceListItem.setPrice(price);
						extraItems.add(newExtraPriceListItem);
					}
					newExtraPriceList.setItems(extraItems);
					structure.addExtraPriceList(newExtraPriceList);
				}
			}
		}
	}

	

	@Override
	public Boolean hasRoomFreeInPeriod(Structure structure, Integer roomId, Date dateIn, Date dateOut) {
		//Estraggo i Booking della camera con roomId dato
		List<Booking> roomBookings = new ArrayList<Booking>();
		
		for(Booking each: structure.getBookings()){
			if(each.getRoom().getId().equals(roomId)){
				roomBookings.add(each);
			}
		}
		//               dateIn |-------------------------| dateOut              dateIn |-----| dateOut 
		//       |------------------|    |---|     |---------------------------|    |------------------|  roomBookings
		//             aBooking         aBooking         aBooking							aBooking 
		
		for(Booking aBooking: roomBookings){
			if(aBooking.getDateOut().after(dateIn) && (aBooking.getDateOut().compareTo(dateOut)<= 0 ) ){
				return false;
			}
			if(aBooking.getDateIn().after(dateIn) && aBooking.getDateIn().before(dateOut)){
				return false;
			}
			if(aBooking.getDateIn().after(dateIn) && aBooking.getDateOut().before(dateOut)){
				return false;
			}
			if(aBooking.getDateOut().after(dateOut) && aBooking.getDateIn().compareTo(dateIn)<=0){
				return false;
			}
		}
		return true;
	}
	
	


	@Override
	public Boolean hasRoomFreeForBooking(Structure structure, Booking booking) {
		//Estraggo i Booking della camera con roomId dato
		List<Booking> roomBookings = new ArrayList<Booking>();
		
		for(Booking each: structure.getBookings()){
			if( each.getRoom().getId().equals(booking.getRoom().getId()) && !each.equals(booking)    ){
				roomBookings.add(each);
			}
		}
		//               dateIn |-------------------------| dateOut    
		//       |------------------|    |---|     |---------------------------|    roomBookings
		//             aBooking         aBooking         aBooking
		
		for(Booking aBooking: roomBookings){
			if(aBooking.getDateOut().after(booking.getDateIn()) && aBooking.getDateOut().before(booking.getDateOut())){
				return false;
			}
			if(aBooking.getDateIn().after(booking.getDateIn()) && aBooking.getDateIn().before(booking.getDateOut())){
				return false;
			}
		}
		return true;
		
	}

	

	@Override
	public Image findImageById(Structure structure, Integer id) {
		for(Image each: structure.getImageLists()){
			if(each.getId().equals(id)){
				return each;
			}
		}
		return null;
	}


	@Override
	public Integer insertImage(Structure structure, Image structureImage) {
		structure.getImageLists().add(structureImage);
		return 1;
	}


	@Override
	public Integer deleteImage(Structure structure, Image structureImage) {
		structure.getImageLists().remove(structureImage);
		return 1;
	}


	@Override
	public StructureFacility findStructureFacilityById(Structure structure,Integer id) {
		for(StructureFacility each: structure.getStructureFacilities() ){
			if(each.getId().equals(id)){
				return each;
			}
		}
		return null;
	}


	@Override
	public Integer insertStructureFacility(Structure structure,	StructureFacility structureFacility) {
		structure.getStructureFacilities().add(structureFacility);
		return 1;
	}


	@Override
	public Integer deleteStructureFacility(Structure structure,StructureFacility structureFacility) {
		structure.getStructureFacilities().remove(structureFacility);
		return 1;
	}


	public SeasonService getSeasonService() {
		return seasonService;
	}


	public void setSeasonService(SeasonService seasonService) {
		this.seasonService = seasonService;
	}


	@Override
	public Integer addRoomFacility(Structure structure, RoomFacility roomFacility) {
		structure.getRoomFacilities().add(roomFacility);
		return 1;
	}


	@Override
	public RoomFacility findRoomFacilityByName(Structure structure,String roomFacilityName) {
		for(RoomFacility each: structure.getRoomFacilities()){
			if(each.getName().equals(roomFacilityName)){
				return each;
			}
		}
		return null;
	}

	
	@Override
	public RoomFacility findRoomFacilityById(Structure structure, Integer id) {
		RoomFacility ret = null;
		
		for (RoomFacility each:structure.getRoomFacilities()){
			if (each.getId().equals(id)) {
				return each;
			}
		}
		return ret;
	}


	@Override
	public List<RoomFacility> findRoomFacilitiesByIds(Structure structure, List<Integer> ids) {
		List<RoomFacility> ret = new ArrayList<RoomFacility>();
		for(Integer each:ids){
			RoomFacility aRoomFacility = this.findRoomFacilityById(structure,each);
			ret.add(aRoomFacility);
		}
		return ret;
	}
	
	

}
