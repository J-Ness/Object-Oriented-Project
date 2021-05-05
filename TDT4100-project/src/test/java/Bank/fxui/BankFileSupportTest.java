package Bank.fxui;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Bank.model.BankUserAccount;
import Bank.model.BankUserData;

import java.io.FileNotFoundException;
import java.io.IOException;


public class BankFileSupportTest {
	
    private BankUserData testData;
    private BankFileSupport bankFileSupport = new BankFileSupport();

	
	
	private void createBankUserdata() {
		BankUserAccount debit = new BankUserAccount("1204.01.99300","100.0" , "1.2");
		BankUserAccount credit = new BankUserAccount("1204.02.45727","1500.0" , "1.1");
		BankUserAccount savings = new BankUserAccount("1204.03.35083","3355.0" , "1.5");
		BankUserAccount credit2 = new BankUserAccount("1204.02.08519","300.0" , "1.1");
		
		testData = new BankUserData();
		testData.addBankAccount(debit);
		testData.addBankAccount(credit);
		testData.addBankAccount(savings);
		testData.addBankAccount(credit2);

	}
	
	@BeforeEach
	public void setup() {
		createBankUserdata();
	}
	
	@Test
	public void testLoad() {
		BankUserData savedBankUserData; // Required to ignore Eclipse warning
		try {
			savedBankUserData = bankFileSupport.getBankUserDataFromFile("test-save.txt");
		} catch (FileNotFoundException e) {
			fail("Could not load saved file");
			return;
		}
		assertEquals(testData.getAllAccounts().toString(), savedBankUserData.getAllAccounts().toString());
	}

	
	@Test
	public void testLoadNonExistingFile() {
		assertThrows(
			FileNotFoundException.class, 
			() -> testData = bankFileSupport.getBankUserDataFromFile("this_filename_does_not_exsist"), 
			"FileNotFoundException should be thrown when file does not exist!"
		);
	}

	@Test
	public void testLoadInvalidFile() {
		assertThrows(
			Exception.class, 
			() -> testData = bankFileSupport.getBankUserDataFromFile("invalid_save"), 
			"An exception should be thrown if loaded file is invalid!"
		);
	}
	
	@Test
	public void testSave() {
		try {
			bankFileSupport.writeAccountInfoToFile("test_save_new.txt", testData);
		} catch (IOException e) {
			fail("Could not save file");
		}
		
		BankUserData testBankUserData = null, newBankUserData = null;
		
		try {
			testBankUserData = bankFileSupport.getBankUserDataFromFile("test-save.txt");
		}catch(FileNotFoundException e) {
			fail("Could not load saved file");
		}
		
		try {
			newBankUserData = bankFileSupport.getBankUserDataFromFile("test_save_new.txt");
		}catch(FileNotFoundException e) {
			fail("Could not load saved file");
		}
		
		assertNotNull(testBankUserData);
		assertNotNull(newBankUserData);
		assertTrue(testBankUserData.getAllAccounts().toString().equals(newBankUserData.getAllAccounts().toString()));
		
		
	}
}
