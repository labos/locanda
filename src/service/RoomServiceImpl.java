package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import model.Room;
import model.Structure;

@Service
public class RoomServiceImpl implements RoomService{

	@Override
	public List<Room> findRoomsByIdStructure(Structure structure) {
		
		return structure.getRooms();
	}

	@Override
	public Room findRoomByName(Structure structure, String name) {
		Room ret = null;
		
		for(Room each: structure.getRooms()){
			if(each.getName().equals(name)){
				return each;
			}
		}
		return ret;
	}

	@Override
	public List<Room> findRoomsByRoomTypeId(Structure structure, Integer idRoomType) {
		List <Room> ret = null;
		
		ret = new ArrayList<Room>();
		
		for(Room each: structure.getRooms()){
			if(each.getRoomType().getId().equals(idRoomType)){
				ret.add(each);
			}
		}
		ret.removeAll(Collections.singletonList(null));
		return ret;
	}

	@Override
	public Room findRoomById(Structure structure, Integer idRoom) {
		Room ret = null;
		
		for(Room each: structure.getRooms()){
			if(each.getId().equals(idRoom)){
				return each;
			}
		}
		return ret;
	}

	@Override
	public Integer insertRoom(Structure structure, Room room) {
		structure.getRooms().add(room);		
		return 1;
	}

	@Override
	public Integer updateRoom(Structure structure, Room room) {
		Room originalRoom = null;
		
		originalRoom = this.findRoomById(structure,room.getId());
		if(originalRoom==null){
			return 0;
		}
		originalRoom.setName(room.getName());
		originalRoom.setNotes(room.getNotes());
		originalRoom.setRoomType(room.getRoomType());
		originalRoom.setFacilities(room.getFacilities());
		//la lista delle immagini gli viene aggiornata solo in occasione di ogni upload
		//originalRoom.setImages(room.getImages());
		return 1;
	}

	@Override
	public Integer deleteRoom(Structure structure, Room room) {
		structure.getRooms().remove(room);
		return 1;
	}

}
