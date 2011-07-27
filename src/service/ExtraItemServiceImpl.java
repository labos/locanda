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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.ExtraItemMapper;
import persistence.mybatis.mappers.ExtraMapper;

import model.Extra;
import model.ExtraItem;

@Service
public class ExtraItemServiceImpl implements ExtraItemService{
	@Autowired
	private ExtraItemMapper extraItemMapper = null;
	@Autowired
	private ExtraMapper extraMapper = null;

	@Override
	public List<ExtraItem> findExtraItemsByIdBooking(Integer id_booking) {
		List<ExtraItem> extraItems = null;
		Extra extra = null;
		
		extraItems = this.getExtraItemMapper().findExtraItemsByIdBooking(id_booking);
		for(ExtraItem each: extraItems){
			extra = this.getExtraMapper().findExtraById(each.getId_extra());
			each.setExtra(extra);
		}
		return extraItems;
	}

	@Override
	public Integer insertExtraItem(ExtraItem extraItem) {		
		return this.getExtraItemMapper().insertExtraItem(extraItem);
	}

	@Override
	public Integer deleteExtraItemsByIdBooking(Integer id_booking) {		
		return this.getExtraItemMapper().deleteExtraItemsByIdBooking(id_booking);
	}
	
	public ExtraItemMapper getExtraItemMapper() {
		return extraItemMapper;
	}
	public void setExtraItemMapper(ExtraItemMapper extraItemMapper) {
		this.extraItemMapper = extraItemMapper;
	}
	public ExtraMapper getExtraMapper() {
		return extraMapper;
	}

	public void setExtraMapper(ExtraMapper extraMapper) {
		this.extraMapper = extraMapper;
	}	

}