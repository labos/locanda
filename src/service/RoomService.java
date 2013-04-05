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

import java.util.List;

import model.Room;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RoomService {
	
	public Integer insertRoom(Room room);
	public Integer updateRoom(Room room);
	public Integer deleteRoom(Integer id);
	public Integer countRoomsByIdRoomType(Integer id_roomType);
	
	public List<Room> findAll();
	public List<Room> findRoomsByIdStructure(Integer id_structure);
	public List<Room> findRoomsByIdStructureOrdered(Integer id_structure);
	public List<Integer> findRoomIdsByIdStructure(Integer id_structure);
	public List<Room> findRoomsByIdStructure(Integer id_structure,Integer offset,Integer rownum);
	public List<Room> findRoomsByIdRoomType(Integer id_roomType);
		
	public Room findRoomById(Integer id);
	public Room findRoomByIdStructureAndName(Integer id_structure, String name);
	public Integer findIdStructureByIdRoom(Integer idRoom);
}
