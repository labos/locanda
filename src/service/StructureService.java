package service;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import model.Extra;
import model.RoomType;
import model.Structure;
import model.listini.Convention;

@Transactional
public interface StructureService {
	public Double calculateExtraItemUnitaryPrice(Structure structure, Date dateIn, Date dateOut, RoomType roomType, Convention convention, Extra extra);
	public void refreshPriceLists(Structure structure);

}
