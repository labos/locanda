package service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.StructureMapper;

import model.Adjustment;
import model.BookedExtraItem;
import model.Booking;
import model.Extra;
import model.Guest;
import model.Image;
import model.Payment;
import model.Room;
import model.RoomFacility;
import model.RoomType;
import model.Structure;
import model.StructureFacility;
import model.listini.Convention;
import model.listini.ExtraPriceList;
import model.listini.ExtraPriceListItem;
import model.listini.Period;
import model.listini.RoomPriceList;
import model.listini.RoomPriceListItem;
import model.listini.Season;

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
	
	
	
	@Override
	public Structure findStructureByIdUser(Integer id_user) {		
		return this.getStructureMapper().findStructureByIdUser(id_user);
	}


	@Override
	public Structure findStructureById(Integer id) {		
		return this.getStructureMapper().findStructureById(id);
	}


	@Override
	public List<RoomFacility> findRoomFacilitiesByIdStructure(Structure structure) {
		
		return structure.getRoomFacilities();
	}

	
	
	@Override
	public Integer updateStructure(Structure structure) {
		
		return this.getStructureMapper().updateStructure(structure);
	}


	public Double calculateExtraItemUnitaryPrice(Structure structure, Date dateIn, Date dateOut, RoomType roomType, Convention convention, Extra extra) {
		Double ret = 0.0;
		ExtraPriceList priceList = null;
		ExtraPriceList otherPriceList = null;
		Season season = null;
		Season otherSeason = null;
		Double price = 0.0;
		Double otherPrice = 0.0;
		
		season = this.getSeasonService().findSeasonByDate(structure.getId(),dateIn );
		//priceList = structure.findExtraPriceListBySeasonAndRoomTypeAndConvention(season, roomType,convention);
		priceList = this.getExtraPriceListService().findExtraPriceListByStructureAndSeasonAndRoomTypeAndConvention(structure,season, roomType,convention);
		
		price = priceList.findExtraPrice(extra);
		
		//se ho un booking a cavallo di due stagioni, prendo il prezzo più basso
		otherSeason = this.getSeasonService().findSeasonByDate(structure.getId(),dateOut );
		//otherPriceList = structure.findExtraPriceListBySeasonAndRoomTypeAndConvention(otherSeason, roomType, convention);	
		otherPriceList = this.getExtraPriceListService().findExtraPriceListByStructureAndSeasonAndRoomTypeAndConvention(structure,season, roomType,convention);
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
			for (RoomType eachRoomType : this.getRoomTypeService().findRoomTypesByIdStructure(structure)) {
				for (Convention eachConvention : this.getConventionService().findConventionsByIdStructure(structure)) {
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
					//structure.addRoomPriceList(newRoomPriceList);
					this.getRoomPriceListService().insertRoomPriceList(structure, newRoomPriceList);
					newExtraPriceList = new ExtraPriceList();
					newExtraPriceList.setId(structure.nextKey());
					newExtraPriceList.setSeason(eachSeason);
					newExtraPriceList.setRoomType(eachRoomType);
					newExtraPriceList.setConvention(eachConvention);
					List<ExtraPriceListItem> extraItems = new ArrayList<ExtraPriceListItem>();
					for (Extra eachExtra : this.getExtraService().findExtrasByIdStructure(structure.getId())) {
						newExtraPriceListItem = new ExtraPriceListItem();
						newExtraPriceListItem.setId(structure.nextKey());
						newExtraPriceListItem.setExtra(eachExtra);
						newExtraPriceListItem.setPrice(price);
						extraItems.add(newExtraPriceListItem);
					}
					newExtraPriceList.setItems(extraItems);
					//structure.addExtraPriceList(newExtraPriceList);
					this.getExtraPriceListService().insertExtraPriceList(structure, newExtraPriceList);
				}
			}
		}
	}

	

	@Override
	public Boolean hasRoomFreeInPeriod(Structure structure, Integer roomId, Date dateIn, Date dateOut) {
		//Estraggo i Booking della camera con roomId dato
		List<Booking> roomBookings = new ArrayList<Booking>();
		
		for(Booking each: this.getBookingService().findBookingsByIdStructure(structure)){
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
		
		for(Booking each: this.getBookingService().findBookingsByIdStructure(structure)){
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
		for(Image each: structure.getImages()){
			if(each.getId().equals(id)){
				return each;
			}
		}
		return null;
	}


	@Override
	public Integer insertImage(Structure structure, Image structureImage) {
		structure.getImages().add(structureImage);
		return 1;
	}


	@Override
	public Integer deleteImage(Structure structure, Image structureImage) {
		structure.getImages().remove(structureImage);
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


	public void buildStructure(Structure structure) {

		this.buildRoomFacilities(structure);
		this.buildRoomTypes(structure);
		this.buildRooms(structure);
		this.buildGuests(structure);
		this.buildSeasons(structure);
		this.buildConventions(structure);
		this.buildRoomPriceLists(structure);
		this.buildExtras(structure);
		this.buildExtraPriceLists(structure);
		this.buildBookings(structure);
		this.buildImages(structure);
		this.buildStructureFacilities(structure);

	}

	private void buildRooms(Structure structure) {
		Room aRoom = null;

		aRoom = new Room();
		aRoom.setId(structure.nextKey());
		aRoom.setName("101");
		aRoom.addRoomFacility(this.findRoomFacilitiesByIdStructure(structure).get(0));
		aRoom.addRoomFacility(this.findRoomFacilitiesByIdStructure(structure).get(2));
		aRoom.setRoomType(this.getRoomTypeService()
				.findRoomTypesByIdStructure(structure).get(0));
		this.getRoomService().insertRoom(structure, aRoom);

		aRoom = new Room();
		aRoom.setId(structure.nextKey());
		aRoom.setName("201");
		aRoom.addRoomFacility(this.findRoomFacilitiesByIdStructure(structure).get(1));
		aRoom.setRoomType(this.getRoomTypeService()
				.findRoomTypesByIdStructure(structure).get(1));
		this.getRoomService().insertRoom(structure, aRoom);
	}

	private void buildRoomTypes(Structure structure) {
		RoomType aRoomType = null;
		Image image = new Image();
		RoomFacility roomTypeFacility = new RoomFacility();
		image.setId(structure.nextKey());
		image.setName("singola");
		image.setFileName("single.jpg");
		roomTypeFacility.setId(structure.nextKey());
		roomTypeFacility.setName("wifi");
		roomTypeFacility.setFileName("wifi.png");

		aRoomType = new RoomType();
		aRoomType.setId(structure.nextKey());
		aRoomType.setName("singola");
		aRoomType.setMaxGuests(1);

		aRoomType.addRoomTypeFacility(roomTypeFacility);
		this.addRoomFacility(structure, roomTypeFacility);

		roomTypeFacility = new RoomFacility();
		roomTypeFacility.setId(structure.nextKey());
		roomTypeFacility.setName("air conditioned");
		roomTypeFacility.setFileName("air_conditioned.png");
		aRoomType.addRoomTypeFacility(roomTypeFacility);
		this.addRoomFacility(structure, roomTypeFacility);

		aRoomType.addRoomTypeImage(image);

		this.getRoomTypeService().insertRoomType(structure, aRoomType);

		image = new Image();
		image.setId(structure.nextKey());
		image.setName("doppia");
		image.setFileName("double.jpg");
		aRoomType = new RoomType();
		aRoomType.setId(structure.nextKey());
		aRoomType.setName("doppia");
		aRoomType.setMaxGuests(2);
		aRoomType.addRoomTypeImage(image);
		this.getRoomTypeService().insertRoomType(structure, aRoomType);
	}

	private void buildRoomFacilities(Structure structure) {
		RoomFacility aRoomFacility = null;

		aRoomFacility = new RoomFacility();
		aRoomFacility.setId(structure.nextKey());
		aRoomFacility.setName("AAD");
		aRoomFacility.setFileName("AAD.gif");
		this.addRoomFacility(structure, aRoomFacility);

		aRoomFacility = new RoomFacility();
		aRoomFacility.setId(structure.nextKey());
		aRoomFacility.setName("BAR");
		aRoomFacility.setFileName("BAR.gif");
		this.addRoomFacility(structure, aRoomFacility);

		aRoomFacility = new RoomFacility();
		aRoomFacility.setId(structure.nextKey());
		aRoomFacility.setName("PHO");
		aRoomFacility.setFileName("PHO.gif");
		this.addRoomFacility(structure, aRoomFacility);

		aRoomFacility = new RoomFacility();
		aRoomFacility.setId(structure.nextKey());
		aRoomFacility.setName("RAD");
		aRoomFacility.setFileName("RAD.gif");
		this.addRoomFacility(structure, aRoomFacility);

		aRoomFacility = new RoomFacility();
		aRoomFacility.setId(structure.nextKey());
		aRoomFacility.setName("TEL");
		aRoomFacility.setFileName("TEL.gif");
		this.addRoomFacility(structure, aRoomFacility);

	}

	private void buildGuests(Structure structure) {
		structure.setGuests(this.getGuestService().findGuestsByIdStructure(
				structure.getId()));

	}

	private void buildBookings(Structure structure) {
		Booking aBooking = null;
		Room aRoom = null;
		Guest aGuest = null;
		List<Extra> extras = null;
		Date dateIn = null;
		Date dateOut = null;
		Double roomSubtotal = 0.0;
		Adjustment anAdjustment = null;
		Payment aPayment = null;
		List<BookedExtraItem> bookedExtraItems = null;

		aBooking = new Booking();
		aRoom = this.getRoomService().findRoomByName(structure, "101");

		aGuest = this.getGuestService()
				.findGuestsByIdStructure(structure.getId()).get(0);
		extras = new ArrayList<Extra>();
		extras.add(this.getExtraService()
				.findExtrasByIdStructure(structure.getId()).get(0));
		extras.add(this.getExtraService()
				.findExtrasByIdStructure(structure.getId()).get(1));
		aBooking.addExtras(extras);
		aBooking.setBooker(aGuest);
		aBooking.setRoom(aRoom);
		dateIn = new Date(System.currentTimeMillis());
		dateOut = new Date(System.currentTimeMillis() + 3 * 24 * 3600 * 1000);
		dateIn = DateUtils.truncate(dateIn, Calendar.DAY_OF_MONTH);
		dateOut = DateUtils.truncate(dateOut, Calendar.DAY_OF_MONTH);
		aBooking.setDateIn(dateIn);
		aBooking.setDateOut(dateOut);
		aBooking.setId(structure.nextKey());
		aBooking.setNrGuests(1);
		aBooking.setConvention(this.getConventionService()
				.findConventionsByIdStructure(structure).get(0));
		roomSubtotal = this.getBookingService()
				.calculateRoomSubtotalForBooking(structure, aBooking);

		aBooking.setRoomSubtotal(roomSubtotal);
		bookedExtraItems = this.calculateBookedExtraItems(structure, aBooking);
		aBooking.setExtraItems(bookedExtraItems);

		anAdjustment = new Adjustment();
		anAdjustment.setId(structure.nextKey());
		anAdjustment.setDate(DateUtils.truncate(new Date(),
				Calendar.DAY_OF_MONTH));
		anAdjustment.setDescription("Sconto per doccia malfunzionante");
		anAdjustment.setAmount(new Double("-50.0"));
		aBooking.addAdjustment(anAdjustment);

		aPayment = new Payment();
		aPayment.setId(structure.nextKey());
		aPayment.setDate(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
		aPayment.setDescription("Acconto");
		aPayment.setAmount(new Double("60.0"));
		aBooking.addPayment(aPayment);
		aBooking.setStatus("checkedout");
		this.getBookingService().insertBooking(structure, aBooking);
	}

	private List<BookedExtraItem> calculateBookedExtraItems(
			Structure structure, Booking booking) {
		BookedExtraItem bookedExtraItem = null;
		List<BookedExtraItem> bookedExtraItems = null;

		bookedExtraItems = new ArrayList<BookedExtraItem>();
		for (Extra each : booking.getExtras()) {
			bookedExtraItem = booking.findExtraItem(each);
			if (bookedExtraItem == null) {
				bookedExtraItem = new BookedExtraItem();
				bookedExtraItem.setExtra(each);
				bookedExtraItem.setQuantity(booking
						.calculateExtraItemMaxQuantity(each));
				bookedExtraItem.setMaxQuantity(booking
						.calculateExtraItemMaxQuantity(each));
				bookedExtraItem.setUnitaryPrice(this.calculateExtraItemUnitaryPrice(structure,
								booking.getDateIn(), booking.getDateOut(),
								booking.getRoom().getRoomType(),
								booking.getConvention(), each));

			} else {
				bookedExtraItem.setMaxQuantity(booking
						.calculateExtraItemMaxQuantity(each));
				bookedExtraItem.setUnitaryPrice(this.calculateExtraItemUnitaryPrice(structure,
								booking.getDateIn(), booking.getDateOut(),
								booking.getRoom().getRoomType(),
								booking.getConvention(), each));
			}
			bookedExtraItems.add(bookedExtraItem);
		}
		return bookedExtraItems;
	}

	private void buildExtras(Structure structure) {

		structure.setExtras(this.getExtraService().findExtrasByIdStructure(
				structure.getId()));

	}

	private void buildSeasons(Structure structure) {
		Season aSeason = null;
		Period aPeriod = null;
		SimpleDateFormat sdf = null;

		structure.setSeasons(this.getSeasonService().findSeasonsByStructureId(
				structure.getId()));

	}

	private void buildConventions(Structure structure) {
		Convention convention = null;

		// convenzione di default
		convention = new Convention();
		convention.setId(structure.nextKey());
		convention.setName("agevolazione Default");
		convention.setDescription("Default convention");
		convention.setActivationCode("XXX");

		this.getConventionService().insertConvention(structure, convention);

	}

	private void buildExtraPriceLists(Structure structure) {
		ExtraPriceList extraPriceList = null;
		ExtraPriceListItem extraPriceListItem = null;
		Double price = null;

		// Listino Extra per Camera Singola Bassa Stagione
		extraPriceList = new ExtraPriceList();
		extraPriceList.setId(structure.nextKey());
		extraPriceList.setRoomType(this.getRoomTypeService()
				.findRoomTypesByIdStructure(structure).get(0));
		extraPriceList.setSeason(this.getSeasonService().findSeasonByName(
				structure.getId(), "Bassa Stagione"));
		extraPriceList.setConvention(this.getConventionService()
				.findConventionsByIdStructure(structure).get(0));
		for (Extra eachExtra : this.getExtraService().findExtrasByIdStructure(
				structure.getId())) {
			extraPriceListItem = new ExtraPriceListItem();
			extraPriceListItem.setId(structure.nextKey());
			extraPriceListItem.setExtra(eachExtra);
			price = 10.0;
			extraPriceListItem.setPrice(price);
			extraPriceList.addItem(extraPriceListItem);
		}
		this.getExtraPriceListService().insertExtraPriceList(structure,
				extraPriceList);

		// Listino Extra per Camera Singola Alta Stagione
		extraPriceList = new ExtraPriceList();
		extraPriceList.setId(structure.nextKey());
		extraPriceList.setRoomType(this.getRoomTypeService()
				.findRoomTypesByIdStructure(structure).get(0));
		extraPriceList.setSeason(this.getSeasonService().findSeasonByName(
				structure.getId(), "Alta Stagione"));
		extraPriceList.setConvention(this.getConventionService()
				.findConventionsByIdStructure(structure).get(0));
		for (Extra eachExtra : this.getExtraService().findExtrasByIdStructure(
				structure.getId())) {
			extraPriceListItem = new ExtraPriceListItem();
			extraPriceListItem.setId(structure.nextKey());
			extraPriceListItem.setExtra(eachExtra);
			price = 15.0;
			extraPriceListItem.setPrice(price);
			extraPriceList.addItem(extraPriceListItem);
		}
		this.getExtraPriceListService().insertExtraPriceList(structure,
				extraPriceList);
		// Listino Extra per Camera Doppia Bassa Stagione
		extraPriceList = new ExtraPriceList();
		extraPriceList.setId(structure.nextKey());
		extraPriceList.setRoomType(structure.getRoomTypes().get(1));
		extraPriceList.setSeason(this.getSeasonService().findSeasonByName(
				structure.getId(), "Bassa Stagione"));
		extraPriceList.setConvention(this.getConventionService()
				.findConventionsByIdStructure(structure).get(0));
		for (Extra eachExtra : this.getExtraService().findExtrasByIdStructure(
				structure.getId())) {
			extraPriceListItem = new ExtraPriceListItem();
			extraPriceListItem.setId(structure.nextKey());
			extraPriceListItem.setExtra(eachExtra);
			price = 10.0;
			extraPriceListItem.setPrice(price);
			extraPriceList.addItem(extraPriceListItem);
		}
		this.getExtraPriceListService().insertExtraPriceList(structure,
				extraPriceList);

		// Listino Extra per Camera Doppia Alta Stagione
		extraPriceList = new ExtraPriceList();
		extraPriceList.setId(structure.nextKey());
		extraPriceList.setRoomType(structure.getRoomTypes().get(1));
		extraPriceList.setSeason(this.getSeasonService().findSeasonByName(
				structure.getId(), "Alta Stagione"));
		extraPriceList.setConvention(this.getConventionService()
				.findConventionsByIdStructure(structure).get(0));
		for (Extra eachExtra : this.getExtraService().findExtrasByIdStructure(
				structure.getId())) {
			extraPriceListItem = new ExtraPriceListItem();
			extraPriceListItem.setId(structure.nextKey());
			extraPriceListItem.setExtra(eachExtra);
			price = 15.0;
			extraPriceListItem.setPrice(price);
			extraPriceList.addItem(extraPriceListItem);
		}
		this.getExtraPriceListService().insertExtraPriceList(structure,
				extraPriceList);
	}

	private void buildRoomPriceLists(Structure structure) {
		RoomPriceList roomPriceList = null;
		RoomPriceListItem roomPriceListItem = null;
		Double[] prices = null;

		// Listino Room per Camera Singola Bassa Stagione
		roomPriceList = new RoomPriceList();
		roomPriceList.setId(structure.nextKey());
		roomPriceList.setRoomType(this.getRoomTypeService()
				.findRoomTypesByIdStructure(structure).get(0));
		roomPriceList.setSeason(this.getSeasonService().findSeasonByName(
				structure.getId(), "Bassa Stagione"));
		roomPriceList.setConvention(this.getConventionService()
				.findConventionsByIdStructure(structure).get(0));
		roomPriceListItem = new RoomPriceListItem();
		roomPriceListItem.setId(structure.nextKey());
		roomPriceListItem.setNumGuests(1);
		prices = new Double[7];
		prices[0] = 50.0;// lun
		prices[1] = 50.0;// mar
		prices[2] = 50.0;// mer
		prices[3] = 50.0;// gio
		prices[4] = 50.0;// ven
		prices[5] = 50.0;// sab
		prices[6] = 50.0;// dom
		roomPriceListItem.setPrices(prices);
		roomPriceList.addItem(roomPriceListItem);
		this.getRoomPriceListService().insertRoomPriceList(structure,
				roomPriceList);

		// Listino Room per Camera Singola Alta Stagione
		roomPriceList = new RoomPriceList();
		roomPriceList.setId(structure.nextKey());
		roomPriceList.setRoomType(this.getRoomTypeService()
				.findRoomTypesByIdStructure(structure).get(0));
		roomPriceList.setSeason(this.getSeasonService().findSeasonByName(
				structure.getId(), "Alta Stagione"));
		roomPriceList.setConvention(this.getConventionService()
				.findConventionsByIdStructure(structure).get(0));
		roomPriceListItem = new RoomPriceListItem();
		roomPriceListItem.setId(structure.nextKey());
		roomPriceListItem.setNumGuests(1);
		prices = new Double[7];
		prices[0] = 80.0;// lun
		prices[1] = 80.0;// mar
		prices[2] = 80.0;// mer
		prices[3] = 80.0;// gio
		prices[4] = 80.0;// ven
		prices[5] = 80.0;// sab
		prices[6] = 80.0;// dom
		roomPriceListItem.setPrices(prices);
		roomPriceList.addItem(roomPriceListItem);
		this.getRoomPriceListService().insertRoomPriceList(structure,
				roomPriceList);

		// Listino Room per Camera Doppia Bassa Stagione
		roomPriceList = new RoomPriceList();
		roomPriceList.setId(structure.nextKey());
		roomPriceList.setRoomType(this.getRoomTypeService()
				.findRoomTypesByIdStructure(structure).get(1));
		roomPriceList.setSeason(this.getSeasonService().findSeasonByName(
				structure.getId(), "Bassa Stagione"));
		roomPriceList.setConvention(this.getConventionService()
				.findConventionsByIdStructure(structure).get(0));
		roomPriceListItem = new RoomPriceListItem();
		roomPriceListItem.setId(structure.nextKey());
		roomPriceListItem.setNumGuests(1);
		prices = new Double[7];
		prices[0] = 80.0;// lun
		prices[1] = 80.0;// mar
		prices[2] = 80.0;// mer
		prices[3] = 80.0;// gio
		prices[4] = 80.0;// ven
		prices[5] = 80.0;// sab
		prices[6] = 80.0;// dom
		roomPriceListItem.setPrices(prices);
		roomPriceList.addItem(roomPriceListItem);

		roomPriceListItem = new RoomPriceListItem();
		roomPriceListItem.setId(structure.nextKey());
		roomPriceListItem.setNumGuests(2);
		prices = new Double[7];
		prices[0] = 100.0;// lun
		prices[1] = 100.0;// mar
		prices[2] = 100.0;// mer
		prices[3] = 100.0;// gio
		prices[4] = 100.0;// ven
		prices[5] = 100.0;// sab
		prices[6] = 100.0;// dom
		roomPriceListItem.setPrices(prices);
		roomPriceList.addItem(roomPriceListItem);

		// structure.addRoomPriceList(roomPriceList);
		this.getRoomPriceListService().insertRoomPriceList(structure,
				roomPriceList);

		// Listino Room per Camera Doppia Alta Stagione
		roomPriceList = new RoomPriceList();
		roomPriceList.setId(structure.nextKey());
		roomPriceList.setRoomType(this.getRoomTypeService()
				.findRoomTypesByIdStructure(structure).get(1));
		roomPriceList.setSeason(this.getSeasonService().findSeasonByName(
				structure.getId(), "Alta Stagione"));
		roomPriceList.setConvention(this.getConventionService()
				.findConventionsByIdStructure(structure).get(0));
		roomPriceListItem = new RoomPriceListItem();
		roomPriceListItem.setId(structure.nextKey());
		roomPriceListItem.setNumGuests(1);
		prices = new Double[7];
		prices[0] = 90.0;// lun
		prices[1] = 90.0;// mar
		prices[2] = 90.0;// mer
		prices[3] = 90.0;// gio
		prices[4] = 90.0;// ven
		prices[5] = 90.0;// sab
		prices[6] = 90.0;// dom
		roomPriceListItem.setPrices(prices);
		roomPriceList.addItem(roomPriceListItem);

		roomPriceListItem = new RoomPriceListItem();
		roomPriceListItem.setId(structure.nextKey());
		roomPriceListItem.setNumGuests(2);
		prices = new Double[7];
		prices[0] = 130.0;// lun
		prices[1] = 130.0;// mar
		prices[2] = 130.0;// mer
		prices[3] = 130.0;// gio
		prices[4] = 130.0;// ven
		prices[5] = 130.0;// sab
		prices[6] = 130.0;// dom
		roomPriceListItem.setPrices(prices);
		roomPriceList.addItem(roomPriceListItem);

		this.getRoomPriceListService().insertRoomPriceList(structure,
				roomPriceList);
	}

	private void buildImages(Structure structure) {

		Image img = null;

		img = new Image();
		img.setId(structure.nextKey());
		img.setName("Facciata");
		img.setFileName("facciata.jpg");
		this.insertImage(structure, img);
	}

	private void buildStructureFacilities(Structure structure) {

		StructureFacility structFacility = null;

		structFacility = new StructureFacility();
		structFacility.setId(structure.nextKey());
		structFacility.setName("Restaurant");
		structFacility.setFileName("restaurant.png");
		this.insertStructureFacility(structure,
				structFacility);
	}	

}
