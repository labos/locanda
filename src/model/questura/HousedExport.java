package model.questura;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import model.Housed;

import org.apache.solr.client.solrj.beans.Field;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class HousedExport implements Serializable{

	@Field
	private Integer id;
	@Field
	private Integer id_housed;
	private Housed housed = null;
	@Field
	private Integer mode;
	@Field
	private Boolean exported;
	@Field
	private Boolean exportedQuestura;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HousedExport other = (HousedExport) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId_housed() {
		return id_housed;
	}

	public void setId_housed(Integer id_housed) {
		this.id_housed = id_housed;
	}

	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

	public Boolean getExported() {
		return exported;
	}

	public void setExported(Boolean exported) {
		this.exported = exported;
	}

	public Housed getHoused() {
		return housed;
	}

	public void setHoused(Housed housed) {
		this.housed = housed;
	}

	public Boolean getExportedQuestura() {
		return exportedQuestura;
	}

	public void setExportedQuestura(Boolean exportedQuestura) {
		this.exportedQuestura = exportedQuestura;
	}
}
