package Bank.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BankUserProfileTest {

	private BankUserProfile bankUserProfile;
	
	@BeforeEach
	public void setup() {
		bankUserProfile = new BankUserProfile("sander.waasjo@ntnu.no", "adminadmin");
		bankUserProfile  = new BankUserProfile("Sander Waasjo","sander.waasjo@ntnu.no","adminadmin");
	}
	
	@Test
	@DisplayName("Test constructor")
	public void testConstructor() {
		Assertions.assertEquals("sander waasjo",bankUserProfile.getName());
		Assertions.assertEquals("sander.waasjo@ntnu.no", bankUserProfile.getEmail());
		Assertions.assertEquals("adminadmin", bankUserProfile.getPassword());
	}
	
	@Test
	@DisplayName("Test constructor number two")
	public void testConstructorTwo() {
		Assertions.assertEquals("sander waasjo", bankUserProfile.getNameFromEmail(bankUserProfile.getEmail()));
		Assertions.assertEquals("sander.waasjo@ntnu.no",bankUserProfile.getEmail());
		Assertions.assertEquals("adminadmin", bankUserProfile.getPassword());
	}
	
	private void testInvalidName(String invalidName, String existingName) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			bankUserProfile.setName(invalidName);
		});
		Assertions.assertEquals(existingName, bankUserProfile.getName());
	}
	
	
	@Test
	@DisplayName("Name check test")
	public void testCheckName() {
		String name = bankUserProfile.getName();
		testInvalidName("Sander", name);
		testInvalidName("S W", name);
		testInvalidName("Sander Fjeld Waasjo", name);
		testInvalidName("S. Waasjo", name);
		testInvalidName("Sander@ Waasjo", name);
		Assertions.assertDoesNotThrow(() -> {
			bankUserProfile.setName("sander waasjo");
		});
		Assertions.assertEquals("sander waasjo", bankUserProfile.getName());
	}
	
	
	private void testInvalidEmail(String invalidEmail, String existingEmail, Class<? extends Exception> ex) {
		Assertions.assertThrows(ex, () -> {
			bankUserProfile.setEmail(invalidEmail);
		});
		Assertions.assertEquals(existingEmail, bankUserProfile.getEmail());
	}
	
	@Test
	@DisplayName("check email")
	public void testCheckEmail() {
		bankUserProfile.setName("sander waasjo");
		String email = bankUserProfile.getEmail();
		testInvalidEmail("sander.waasjo@ntnu", email, IllegalArgumentException.class);
		testInvalidEmail("sander.waasjo(at)ntnu.no", email, IllegalArgumentException.class);
		testInvalidEmail("sander.fjeld.waasjo@ntnu.no", email, IllegalArgumentException.class);
		Assertions.assertDoesNotThrow(() -> {
			bankUserProfile.setEmail("sander.waasjo@ntnu.no");
		});
		assertEquals("sander.waasjo@ntnu.no", bankUserProfile.getEmail());
	}
	
	
}
