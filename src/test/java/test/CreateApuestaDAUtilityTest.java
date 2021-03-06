package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Event;
import domain.Forecast;
import domain.Question;
import domain.RegularUser;
import exceptions.UserAlreadyExistException;

class CreateApuestaDAUtilityTest {
	//antiguo
	//static DataAccess da = new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));;
	//BLFacadeImplementation sut = new BLFacadeImplementation(da);
	
	//Los Junit de la clase DataAccess para crearApuesta nos son correctos. El sut debe ser DataAccess,
	//y se trabaja con DataAccessUtility para manipular la BD. Revisa todos los casos.
	static DataAccess sut = new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));;
	

	@Test
	@DisplayName("valor de apuesta negativo")
	public void test1(){
		//int vApuesta =-3;
		RegularUser user = new RegularUser("name", "pass", "fname", "lname", "31/01", "j@j.com", "1212", 684123123, "k.2.3", 20);
		
		Question q = new Question();
		q.setBetMinimum(2);
		Forecast f = new Forecast("nombreForecast", (float)12, q);
		
		int actual = sut.createApuesta(f, user, (float) -1);
		
		assertEquals(4, actual);
		
	}
	
	@Test
	@DisplayName("valor de apuesta menor a valor de apuesta minima")
	public void test2() {
		RegularUser user = new RegularUser("name", "pass", "fname", "lname", "31/01", "j@j.com", "1212", 684123123, "k.2.3", 20);
		Question q = new Question();
		q.setBetMinimum(2); //apuesta minima de 2
		Forecast f = new Forecast("nombreForecast", (float)12, q);
		
		int actual = sut.createApuesta(f, user, (float) 1);
		int expected = 3;
		assertEquals(expected, actual);
	}
	
	@Test
	@DisplayName("valor de apuesta mayor a saldo de usuario")
	public void test3() throws UserAlreadyExistException {
		RegularUser user = new RegularUser("name", "pass", "fname", "lname", "31/01", "j@j.com", "1212", 684123123, "k.2.3", 20);
		//RegularUser user = sut.registrar("name", "pass", "fname", "lname", "31/01", "j@j.com", "1212", 684123123, "k.2.3", 20);
		
		Question q = new Question();
		q.setBetMinimum(2); //apuesta minima de 2
		Forecast f = new Forecast("nombreForecast", (float)12, q);
		
		
		int actual = sut.createApuesta(f, user, (float) 100); //valor de apuesta de 100
		int expected = 2;
		assertEquals(expected, actual);
	}
	
	/**/
	@SuppressWarnings("unused")
	@Test
	@DisplayName("Usuario ya registrado en la BD")
	public void test4() {
		
		Question q = new Question();
		q.setBetMinimum(9);
		Forecast f = new Forecast("nombreForecast", (float)12, q);

		assertThrows(Exception.class, () -> sut.registrar("name", "pass", "fname", "lname", "31/01", "j@j.com", "1212", 684123123, "k.2.3", 20));
		
	}
	
	
	@Test
	@DisplayName("correcta creaci??n de apuesta")@SuppressWarnings("deprecation")
	public void test5() {
		Event e = new Event("demo", new Date("31/01/2010"));
		Question q = new Question();
		q.setBetMinimum(2); 
		q.setEvent(e);
		Forecast f = new Forecast("nombreForecast", (float)12, q);
		RegularUser user = (RegularUser) sut.getAllUsers().get(0);
		
		int actual = sut.createApuesta(f, user, (float) 12); //valor de apuesta de 100
		int expected = 1;
		assertEquals(expected, actual);
	}
	
	
}
