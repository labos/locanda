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
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import model.Image;

@Transactional
public interface RoomImageService {	
	public Integer insert(Integer id_room,Integer id_image);
	
	public List<Integer> findIdImageByIdRoom(Integer id_room,Integer offset, Integer rownum);
	public Integer findIdByIdRoomAndIdImage(Integer id_room, Integer id_image );
	
	public Integer delete(Integer id);
	public Integer deleteByIdImage(Integer id_image);	
	public Integer deleteByIdRoom(Integer id_room);	
	
	
}