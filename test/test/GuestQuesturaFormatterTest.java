package test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import model.GuestQuesturaFormatter;

public class GuestQuesturaFormatterTest {
	GuestQuesturaFormatter m;
	
	@Before
	public void setUp() throws Exception {
		m = new GuestQuesturaFormatter();
		m.setTipoAllogiato(14);
		m.setDataArrivo(new Date("02/05/2011"));
		m.setCognome("Mura");
		m.setNome("Tore");
		m.setSesso("M");
		m.setDataDiNascita(new Date("12/02/1950"));
		m.setComuneDiNascita("Santulussurgiu");
		m.setProvinciaDiNascita("CAGLIARI");
		m.setStatoDiNascita("Italia");
		m.setCittadinanza("Italiana");
		m.setComuneResidenza("Selargius");
		m.setProvinciaResidenza("Cagliari");
		m.setStatoResidenza("Italia");
		m.setIndirizzo("Via delle fresche frasche, n1");
		m.setTipoDocumento("Patente");
		m.setNumeroDocumento("CC123456lkjdf");
		m.setLuogoRilascioDocumento("Villanovaforru");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void outputLengthTest() {				
		//System.out.print(m.getRowQuestura());
		assertEquals(236,m.getRowQuestura().length());
	}

	@Test 
	public void sessoTest() {
		assertEquals("M", m.getRowQuestura().substring(92, 93));
		
	}

}
