package Bank.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BankUserTest {

	private BankUser bankUser;
	private BankUserData bankUserData;
	
	@BeforeEach
	public void setup() {
		bankUser = new BankUser("sander waasjo","sander.waasjo@ntnu.no", "adminadmin", bankUserData);
	}
	
	@Test
	@DisplayName("Test constructor")
	public void testConstructor() {
	}
	
	
	@Test
	@DisplayName("Illegal Argument in Constructor")
	public void IllegalArgumentInConstructor() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			bankUser = new BankUser("sander", "sander.waasjo@ntnu.no", "adminadmin", bankUserData);
		});
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			bankUser = new BankUser("sander waasjo", "sander@ntnu.no", "adminadmin", bankUserData);
		});
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			bankUser = new BankUser("sander waasjo", "sander.waasjo@ntnu.no", "admin", bankUserData);
		});
		}
	
}
