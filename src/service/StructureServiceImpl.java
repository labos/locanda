/*******************************************************************************
 *
 *  Copyright 2011 - Sardegna Ricerche, Distretto ICT, Pula, Italy
 *
 * Licensed under the EUPL, Version 1.1.
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 *  http://www.osor.eu/eupl
 *
 * Unless required by applicable law or agreed to in  writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 * In case of controversy the competent court is the Court of Cagliari (Italy).
 *******************************************************************************/
package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Booking;
import model.Extra;
import model.RoomType;
import model.Structure;
import model.listini.Convention;
import model.listini.ExtraPriceList;
import model.listini.ExtraPriceListItem;
import model.listini.RoomPriceList;
import model.listini.RoomPriceListItem;
import model.listini.Season;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.StructureMapper;

@Service
public class StructureServiceImpl implements StructureService{
	@Autowired
	private SeasonService seasonService = null;
	@Autowired
	private RoomPriceListService roomPriceListService = null;
	@Autowired
	private ExtraPriceListService extraPriceListService = null;
	@Autowired
	private RoomTypeService roomTypeService = null;
	@Autowired
	private BookingService bookingService = null;
	@Autowired
	private ExtraService extraService = null;
	@Autowired
	private ConventionService conventionService = null;
	@Autowired
	private StructureMapper structureMapper = null;
	@Autowired
	private RoomService roomService = null;
	@Autowired
	private GuestService guestService = null;
	@Autowired 
	private ImageService imageService = null;	
	@Autowired
	private FacilityService facilityService = null;
	
	
	public List<Structure> findAll(){
		return this.structureMapper.findAll();
	}
	
	@Override
	public Structure findStructureByIdUser(Integer id_user) {	
		Structure ret = null;
			
		ret = this.getStructureMapper().findStructureByIdUser(id_user);	
		return ret;
	}

	@Override
	public Structure findStructureById(Integer id) {
		return this.getStructureMapper().findStructureById(id);
	}

	
	@Override
	public Integer updateStructure(Structure structure) {
		return this.getStructureMapper().updateStructure(structure);
	}
	
	public Integer insertStructure(Structure structure){
		Integer ret = 0;
		Convention defaultConvention = null;
		
		ret = this.getStructureMapper().insertStructure(structure);
		if(ret>0){
			defaultConvention = new Convention();
			defaultConvention.setName("Nessuna Convenzione");
			defaultConvention.setDescription("Nessuna Convenzione");
			defaultConvention.setActivationCode("thisconventionshouldntneverberemoved");
			defaultConvention.setId_structure(structure.getId());
			ret = ret + this.getConventionService().insertConvention(defaultConvention);
		}
		return ret;
	}

	public Double calculateExtraItemUnitaryPrice(Integer id_structure, Date dateIn, Date dateOut, RoomType roomType, Convention convention, Extra extra) {
		Double ret = 0.0;
		ExtraPriceList priceList = null;
		ExtraPriceList otherPriceList = null;
		Season season = null;;
		Double price = 0.0;
		Double otherPrice = 0.0;
		
		season = this.getSeasonService().findSeasonByDate(id_structure,dateIn );
		priceList = this.getExtraPriceListService().findExtraPriceListByIdStructureAndIdSeasonAndIdRoomTypeAndIdConvention(
				id_structure, season.getId(), roomType.getId(), convention.getId());
		
		price = priceList.findExtraPrice(extra);
		
		//If a booking covers more than one season, considers the lowest price
		otherPriceList = this.getExtraPriceListService().findExtraPriceListByIdStructureAndIdSeasonAndIdRoomTypeAndIdConvention(
				id_structure, season.getId(), roomType.getId(), convention.getId());
		price = priceList.findExtraPrice(extra);
		otherPrice = otherPriceList.findExtraPrice(extra);
		
		ret = Math.min(price,otherPrice);
		
		return ret;
	}
	
	public void addPriceListsForSeason(Integer id_structure, Integer id_season) {
		RoomPriceList newRoomPriceList = null;
		ExtraPriceList newExtraPriceList = null;
		RoomPriceListItem newRoomPriceListItem = null;
		ExtraPriceListItem newExtraPriceListItem = null;
		Double price = 0.0;
		Season theNewSeason = null;

		theNewSeason = this.getSeasonService().findSeasonById(id_season);
		
		for (RoomType eachRoomType : this.getRoomTypeService().findRoomTypesByIdStructure(id_structure)) {
			for (Convention eachConvention : this.getConventionService().findConventionsByIdStructure(id_structure)) {
				//RoomPriceList
				newRoomPriceList = new RoomPriceList();
				newRoomPriceList.setId_structure(id_structure);
				newRoomPriceList.setSeason(theNewSeason);
				newRoomPriceList.setId_season(theNewSeason.getId());
				newRoomPriceList.setRoomType(eachRoomType);
				newRoomPriceList.setId_roomType(eachRoomType.getId());
				newRoomPriceList.setConvention(eachConvention);
				newRoomPriceList.setId_convention(eachConvention.getId());
				List<RoomPriceListItem> roomItems = new ArrayList<RoomPriceListItem>();
				for (int i = 1; i <= eachRoomType.getMaxGuests(); i++) {
					newRoomPriceListItem = new RoomPriceListItem();
					newRoomPriceListItem.setNumGuests(i);
					newRoomPriceListItem.setPriceMonday(0.0);// lun
					newRoomPriceListItem.setPriceTuesday(0.0);// mar
					newRoomPriceListItem.setPriceWednesday(0.0);// mer
					newRoomPriceListItem.setPriceThursday(0.0);// gio
					newRoomPriceListItem.setPriceFriday(0.0);// ven
					newRoomPriceListItem.setPriceSaturday(0.0);// sab
					newRoomPriceListItem.setPriceSunday(0.0);// dom
					roomItems.add(newRoomPriceListItem);
				}
				newRoomPriceList.setItems(roomItems);
				this.getRoomPriceListService().insertRoomPriceList(newRoomPriceList);
				
				//ExtraPriceList
				newExtraPriceList = new ExtraPriceList();
				newExtraPriceList.setId_structure(id_structure);
				newExtraPriceList.setSeason(theNewSeason);
				newExtraPriceList.setId_season(theNewSeason.getId());				
				newExtraPriceList.setRoomType(eachRoomType);
				newExtraPriceList.setId_roomType(eachRoomType.getId());
				newExtraPriceList.setConvention(eachConvention);
				newExtraPriceList.setId_convention(eachConvention.getId());				
				
				List<ExtraPriceListItem> extraItems = new ArrayList<ExtraPriceListItem>();
				for (Extra eachExtra : this.getExtraService().findExtrasByIdStructure(id_structure)) {
					newExtraPriceListItem = new ExtraPriceListItem();
					newExtraPriceListItem.setExtra(eachExtra);
					newExtraPriceListItem.setId_extra(eachExtra.getId());
					newExtraPriceListItem.setPrice(price);
					extraItems.add(newExtraPriceListItem);
				}
				newExtraPriceList.setItems(extraItems);
				this.getExtraPriceListService().insertExtraPriceList(newExtraPriceList);
			}
		}
	}
	
	public void addPriceListsForRoomType(Integer id_structure, Integer id_roomType) {
		RoomPriceList newRoomPriceList = null;
		ExtraPriceList newExtraPriceList = null;
		RoomPriceListItem newRoomPriceListItem = null;
		ExtraPriceListItem newExtraPriceListItem = null;
		Double price = 0.0;
		RoomType theNewRoomType = null;

		theNewRoomType = this.getRoomTypeService().findRoomTypeById(id_roomType);
		
		for (Season eachSeason : this.getSeasonService().findSeasonsByIdStructure(id_structure)) {
			for (Convention eachConvention : this.getConventionService().findConventionsByIdStructure(id_structure)) {
				//RoomPriceList
				newRoomPriceList = new RoomPriceList();
				newRoomPriceList.setId_structure(id_structure);
				newRoomPriceList.setSeason(eachSeason);
				newRoomPriceList.setId_season(eachSeason.getId());
				newRoomPriceList.setRoomType(theNewRoomType);
				newRoomPriceList.setId_roomType(theNewRoomType.getId());
				newRoomPriceList.setConvention(eachConvention);
				newRoomPriceList.setId_convention(eachConvention.getId());
				List<RoomPriceListItem> roomItems = new ArrayList<RoomPriceListItem>();
				for (int i = 1; i <= theNewRoomType.getMaxGuests(); i++) {
					newRoomPriceListItem = new RoomPriceListItem();
					newRoomPriceListItem.setNumGuests(i);
					newRoomPriceListItem.setPriceMonday(0.0);// mon
					newRoomPriceListItem.setPriceTuesday(0.0);// tue
					newRoomPriceListItem.setPriceWednesday(0.0);// wed
					newRoomPriceListItem.setPriceThursday(0.0);// thu
					newRoomPriceListItem.setPriceFriday(0.0);// fri
					newRoomPriceListItem.setPriceSaturday(0.0);// sat
					newRoomPriceListItem.setPriceSunday(0.0);// sun
					roomItems.add(newRoomPriceListItem);
				}
				newRoomPriceList.setItems(roomItems);
				this.getRoomPriceListService().insertRoomPriceList(newRoomPriceList);
				
				//ExtraPriceList
				newExtraPriceList = new ExtraPriceList();
				newExtraPriceList.setId_structure(id_structure);
				newExtraPriceList.setSeason(eachSeason);
				newExtraPriceList.setId_season(eachSeason.getId());				
				newExtraPriceList.setRoomType(theNewRoomType);
				newExtraPriceList.setId_roomType(theNewRoomType.getId());
				newExtraPriceList.setConvention(eachConvention);
				newExtraPriceList.setId_convention(eachConvention.getId());				
				
				List<ExtraPriceListItem> extraItems = new ArrayList<ExtraPriceListItem>();
				for (Extra eachExtra : this.getExtraService().findExtrasByIdStructure(id_structure)) {
					newExtraPriceListItem = new ExtraPriceListItem();
					newExtraPriceListItem.setExtra(eachExtra);
					newExtraPriceListItem.setId_extra(eachExtra.getId());
					newExtraPriceListItem.setPrice(price);
					extraItems.add(newExtraPriceListItem);
				}
				newExtraPriceList.setItems(extraItems);
				this.getExtraPriceListService().insertExtraPriceList(newExtraPriceList);
			}
		}
	}
	
	public void addPriceListsForConvention(Integer id_structure, Integer id_convention) {
		RoomPriceList newRoomPriceList = null;
		ExtraPriceList newExtraPriceList = null;
		RoomPriceListItem newRoomPriceListItem = null;
		ExtraPriceListItem newExtraPriceListItem = null;
		Double price = 0.0;
		Convention theNewConvention = null;

		theNewConvention = this.getConventionService().findConventionById(id_convention);
		
		for (Season eachSeason : this.getSeasonService().findSeasonsByIdStructure(id_structure)) {
			for (RoomType eachRoomType : this.getRoomTypeService().findRoomTypesByIdStructure(id_structure)) {
				//RoomPriceList
				newRoomPriceList = new RoomPriceList();
				newRoomPriceList.setId_structure(id_structure);
				newRoomPriceList.setSeason(eachSeason);
				newRoomPriceList.setId_season(eachSeason.getId());
				newRoomPriceList.setRoomType(eachRoomType);
				newRoomPriceList.setId_roomType(eachRoomType.getId());
				newRoomPriceList.setConvention(theNewConvention);
				newRoomPriceList.setId_convention(theNewConvention.getId());
				List<RoomPriceListItem> roomItems = new ArrayList<RoomPriceListItem>();
				for (int i = 1; i <= eachRoomType.getMaxGuests(); i++) {
					newRoomPriceListItem = new RoomPriceListItem();
					newRoomPriceListItem.setNumGuests(i);
					newRoomPriceListItem.setPriceMonday(0.0);// mon
					newRoomPriceListItem.setPriceTuesday(0.0);// tue
					newRoomPriceListItem.setPriceWednesday(0.0);// wed
					newRoomPriceListItem.setPriceThursday(0.0);// thu
					newRoomPriceListItem.setPriceFriday(0.0);// fri
					newRoomPriceListItem.setPriceSaturday(0.0);// sat
					newRoomPriceListItem.setPriceSunday(0.0);// sun
					roomItems.add(newRoomPriceListItem);
				}
				newRoomPriceList.setItems(roomItems);
				this.getRoomPriceListService().insertRoomPriceList(newRoomPriceList);
				
				//ExtraPriceList
				newExtraPriceList = new ExtraPriceList();
				newExtraPriceList.setId_structure(id_structure);
				newExtraPriceList.setSeason(eachSeason);
				newExtraPriceList.setId_season(eachSeason.getId());				
				newExtraPriceList.setRoomType(eachRoomType);
				newExtraPriceList.setId_roomType(eachRoomType.getId());
				newExtraPriceList.setConvention(theNewConvention);
				newExtraPriceList.setId_convention(theNewConvention.getId());				
				
				List<ExtraPriceListItem> extraItems = new ArrayList<ExtraPriceListItem>();
				for (Extra eachExtra : this.getExtraService().findExtrasByIdStructure(id_structure)) {
					newExtraPriceListItem = new ExtraPriceListItem();
					newExtraPriceListItem.setExtra(eachExtra);
					newExtraPriceListItem.setId_extra(eachExtra.getId());
					newExtraPriceListItem.setPrice(price);
					extraItems.add(newExtraPriceListItem);
				}
				newExtraPriceList.setItems(extraItems);
				this.getExtraPriceListService().insertExtraPriceList(newExtraPriceList);
			}
		}
	}
	
	public void modifyPriceListsForExtra(Integer id_structure, Integer id_extra) {
		ExtraPriceListItem newExtraPriceListItem = null;
		
		for (ExtraPriceList eachPriceList : this.getExtraPriceListService().findExtraPriceListsByIdStructure(id_structure)) {
			newExtraPriceListItem = new ExtraPriceListItem();
			newExtraPriceListItem.setId_extra(id_extra);
			newExtraPriceListItem.setPrice(0.0);
			newExtraPriceListItem.setId_extraPriceList(eachPriceList.getId());
			this.getExtraPriceListService().insertExtraPriceListItem(newExtraPriceListItem);
		}
	}
	
	@Override
	public Boolean hasRoomFreeInPeriod(Integer id_structure, Integer roomId, Date dateIn, Date dateOut) {
		//Extracts all bookings related with the room with that roomId
		List<Booking> roomBookings = new ArrayList<Booking>();
		
		for(Booking each: this.getBookingService().findBookingsByIdStructure(id_structure)){
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
	public Boolean hasRoomFreeForBooking(Integer id_structure, Booking booking) {
		//Extracts all bookings related with the room with that roomId
		List<Booking> roomBookings = new ArrayList<Booking>();
		
		for(Booking each: this.getBookingService().findBookingsByIdStructure(id_structure)){
			if(each.getRoom().getId().equals(booking.getRoom().getId()) && !each.equals(booking)){
				roomBookings.add(each);
			}
		}
		//              dateIn |--------------------------| dateOut    dateIn |--------| dateOut
		//       |------------------|    |---|     |--------------------------------------|    roomBookings
		//             aBooking         aBooking         aBooking
		
		for(Booking aBooking: roomBookings){
			if(aBooking.getDateOut().after(booking.getDateIn()) && aBooking.getDateIn().before(booking.getDateOut())){
				return false;
			}
			if(aBooking.getDateIn().before(booking.getDateOut()) && aBooking.getDateOut().after(booking.getDateIn())){
				return false;
			}
			if (aBooking.getDateOut().after(booking.getDateOut()) && aBooking.getDateIn().before(booking.getDateIn())) {
				return false;
			}
		}
		return true;	
	}
	
	public SeasonService getSeasonService() {
		return seasonService;
	}
	public void setSeasonService(SeasonService seasonService) {
		this.seasonService = seasonService;
	}
	public RoomPriceListService getRoomPriceListService() {
		return roomPriceListService;
	}
	public void setRoomPriceListService(RoomPriceListService roomPriceListService) {
		this.roomPriceListService = roomPriceListService;
	}
	public ExtraPriceListService getExtraPriceListService() {
		return extraPriceListService;
	}
	public void setExtraPriceListService(ExtraPriceListService extraPriceListService) {
		this.extraPriceListService = extraPriceListService;
	}
	public RoomTypeService getRoomTypeService() {
		return roomTypeService;
	}
	public void setRoomTypeService(RoomTypeService roomTypeService) {
		this.roomTypeService = roomTypeService;
	}
	public BookingService getBookingService() {
		return bookingService;
	}
	public void setBookingService(BookingService bookingService) {
		this.bookingService = bookingService;
	}
	public ExtraService getExtraService() {
		return extraService;
	}
	public void setExtraService(ExtraService extraService) {
		this.extraService = extraService;
	}
	public ConventionService getConventionService() {
		return conventionService;
	}
	public void setConventionService(ConventionService conventionService) {
		this.conventionService = conventionService;
	}
	public StructureMapper getStructureMapper() {
		return structureMapper;
	}
	public void setStructureMapper(StructureMapper structureMapper) {
		this.structureMapper = structureMapper;
	}
	public RoomService getRoomService() {
		return roomService;
	}
	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}
	public GuestService getGuestService() {
		return guestService;
	}
	public void setGuestService(GuestService guestService) {
		this.guestService = guestService;
	}
	public ImageService getImageService() {
		return imageService;
	}
	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}
	public FacilityService getFacilityService() {
		return facilityService;
	}
	public void setFacilityService(
			FacilityService structureFacilityService) {
		this.facilityService = structureFacilityService;
	}

}