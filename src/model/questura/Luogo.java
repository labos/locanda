package model.questura;

import java.util.Date;

public interface Luogo {

	public void setCodice(Integer codice);
	public String getDescrizione();
	public void setDescrizione(String descrizione);
	public String getProvincia();
	public void setProvincia(String provincia);
	public Date getDataFineVal();
	public void setDataFineVal(Date dataFineVal);
	
	
}
