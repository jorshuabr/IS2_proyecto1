package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Forecast;
import domain.Question;
import domain.RegularUser;
import exceptions.UserAlreadyExistException;

class BLFacadeImplementationMockTest {
	
	DataAccess da = Mockito.mock(DataAccess.class);
	BLFacadeImplementation bl = new BLFacadeImplementation(da);
	BLFacade sut = new BLFacadeImplementation(da);

	/**/
	@Test
	@DisplayName("valor de apuesta negativo")
	public void test1() throws UserAlreadyExistException{
		RegularUser user = new RegularUser("name", "pass", "fname", "lname", "31/01", "j@j.com", "1212", 684123123, "k.2.3", 20);
		
		Question q = new Question();
		q.setBetMinimum(2);
		Forecast f = new Forecast("nombreForecast", (float)12, q);
		
		
		int actual = bl.createApuesta(f, user, (float) -2); 
		int expected = 0;
		
		assertEquals(expected, actual);
	}
	
	@Test
	@DisplayName("valor de apuesta menor a valor de apuesta minima")
	public void test2() throws UserAlreadyExistException {
		//RegularUser user = new RegularUser("name", "pass", "fname", "lname", "31/01", "j@j.com", "1212", 684123123, "k.2.3", 20);
		RegularUser user = bl.registrar("name", "pass", "fname", "lname", "31/01", "j@j.com", "1212", 684123123, "k.2.3", 20);
		Question q = new Question();
		q.setBetMinimum(2); //apuesta minima de 2
		Forecast f = new Forecast("nombreForecast", (float)12, q);
		
		
		int actual = bl.createApuesta(f, user, (float) 1);
		int expected = 0;
		assertEquals(expected, actual);
	}
	
	@Test
	@DisplayName("Usuario ya registrado en la BD")
	public void test4() throws UserAlreadyExistException {
	
		Mockito.doThrow(UserAlreadyExistException.class).when(da).registrar("name", "pass", "fname", "lname", "31/01", "j@j.com", "1212", 684123123, "k.2.3", 20);

		assertThrows(Exception.class, () -> sut.registrar("name", "pass", "fname", "lname", "31/01", "j@j.com", "1212", 684123123, "k.2.3", 20));
		
	}
	
	
	@Test
	@DisplayName("correcta creación de apuesta")
	public void test5() {
		RegularUser user = new RegularUser("name", "pass", "fname", "lname", "31/01", "j@j.com", "1212", 684123123, "k.2.3", 20);
		
		Question q = new Question();
		q.setBetMinimum(2);
		Forecast f = new Forecast("nombreForecast", (float)12, q);
		
		
		int actual = bl.createApuesta(f, user, (float) -2); 
		int expected = 0;
		
		assertEquals(expected, actual);
	}
	
}
