package service;

import java.util.Date;

import model.Booking;
import model.Extra;
import model.RoomType;
import model.Structure;
import model.listini.Convention;
import model.listini.ExtraPriceList;
import model.listini.Season;

public class StructureService {
	public Double calculateExtraItemUnitaryPrice(Structure structure, Date dateIn, Date dateOut, RoomType roomType, Convention convention, Extra extra) {
		Double ret = 0.0;
		ExtraPriceList priceList = null;
		ExtraPriceList otherPriceList = null;
		Season season = null;
		Season otherSeason = null;
		Double price = 0.0;
		Double otherPrice = 0.0;
		
		season = structure.findSeasonByDate(dateIn);
		priceList = structure.findExtraPriceListBySeasonAndRoomTypeAndConvention(season, roomType,convention);
		price = priceList.findExtraPrice(extra);
		
		//se ho un booking a cavallo di due stagioni, prendo il prezzo più basso
		otherSeason = structure.findSeasonByDate(dateOut);
		otherPriceList = structure.findExtraPriceListBySeasonAndRoomTypeAndConvention(otherSeason, roomType, convention);	
		price = priceList.findExtraPrice(extra);
		otherPrice = otherPriceList.findExtraPrice(extra);
		
		ret = Math.min(price,otherPrice);
		
		return ret;
	}

}
