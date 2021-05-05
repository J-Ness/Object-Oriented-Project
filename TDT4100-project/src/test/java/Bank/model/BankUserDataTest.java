package Bank.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BankUserDataTest {

	private BankUserData bankUserData;
	private BankUserAccount debit = new BankUserAccount("debit");
	private BankUserAccount credit = new BankUserAccount("credit");
	
	@BeforeEach
	public void setup() {		
		bankUserData = new BankUserData();
	}
	

	@Test
	@DisplayName("Test create new bank account")
	public void testCreateNewBankAccount() {
		Assertions.assertEquals(0, bankUserData.getAllAccounts().size());
		bankUserData.createNewBankAccount("debit");
		Assertions.assertEquals(1, bankUserData.getAllAccounts().size());
	}
	

	@Test
	@DisplayName("Test add bank account ")
	public void testAddBankAccount() {
		Assertions.assertEquals(0, bankUserData.getAllAccounts().size());
		bankUserData.addBankAccount(debit);
		Assertions.assertEquals(1, bankUserData.getAllAccounts().size());
	}
	
	

	@Test
	@DisplayName("Test delete bank account")
	public void testDeleteBankAccount() {
		debit.deposit(100.0);
		bankUserData.addBankAccount(debit);
		
		assertThrows(IllegalStateException.class, () -> {
			bankUserData.deleteBankAccount(debit);	
	},"you cannot delete while you have a balance > 1kr");
		Assertions.assertEquals(1, bankUserData.getAllAccounts().size());
		
		debit.withdraw(99.5);
		bankUserData.deleteBankAccount(debit);
		Assertions.assertEquals(0, bankUserData.getAllAccounts().size());
	}	
	
	
	@Test
	@DisplayName("Test get total balance from all accounts")
	public void testGetTotalBalance(){
		Assertions.assertEquals(0,bankUserData.getTotalBalance());
		
		bankUserData.addBankAccount(debit);
		bankUserData.addBankAccount(credit);
		debit.deposit(100.0);
		credit.deposit(100.0);
		
		Assertions.assertEquals(200,bankUserData.getTotalBalance());
	}
	
	@Test
	@DisplayName("Test get all accounts")
	public void testGetAllAccounts(){
		bankUserData.addBankAccount(debit);
		bankUserData.addBankAccount(credit);
		Assertions.assertEquals(2,bankUserData.getAllAccounts().size());
		
		bankUserData.getAllAccounts().remove(1);
		Assertions.assertEquals(2,bankUserData.getAllAccounts().size());	
	}

}
