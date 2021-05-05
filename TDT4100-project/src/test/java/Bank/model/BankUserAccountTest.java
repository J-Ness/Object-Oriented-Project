package Bank.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BankUserAccountTest {
	BankUserAccount debit;
	BankUserAccount credit;
	BankUserAccount savings;
	BankUserAccount funds;
	BankUserAccount existingDebit;
	BankUserAccount illegalNewDebit;
	BankUserAccount illegalExistingDebit;
	
	private static double epsilon = 0.001;
	
	@BeforeEach
	public void setup() {
		debit = new BankUserAccount("debit");
		credit = new BankUserAccount("Credit");
		savings = new BankUserAccount("SAVINGS");
		funds = new BankUserAccount("fundS");
		
		existingDebit = new BankUserAccount("1204.01.52135","500","1.2");
	}
	private String getAccountType(String accountID) {
		String[] list_accountID = accountID.split("[.]");
		return list_accountID[1];
	}
	
	@Test
	@DisplayName("Test Constructor For New Account")
	public void constructorNewAccount() {
		Assertions.assertEquals("01", getAccountType(debit.getAccountID()));
		Assertions.assertEquals(1.2, debit.getInterestRate());
		
		Assertions.assertEquals("02", getAccountType(credit.getAccountID()));
		Assertions.assertEquals(1.1, credit.getInterestRate());
		
		Assertions.assertEquals("03", getAccountType(savings.getAccountID()));
		Assertions.assertEquals(1.5, savings.getInterestRate());
		
		Assertions.assertEquals("04", getAccountType(funds.getAccountID()));
		Assertions.assertEquals(1.8, funds.getInterestRate());
	}
	
	@Test
	@DisplayName("Invalid Argument In Constructor For New Account")
	public void testConstructorNewAccountIllegalArgumentInput() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			illegalNewDebit = new BankUserAccount("Debi");
		});
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			illegalNewDebit = new BankUserAccount("debit.");
		});
	}
	
	
	@Test
	@DisplayName("Test Constructor For Existing Account")
	public void constructorExistingAccount() {
		Assertions.assertEquals("01", getAccountType(existingDebit.getAccountID()));
		Assertions.assertEquals(500, existingDebit.getBalance());
		Assertions.assertEquals(1.2, existingDebit.getInterestRate());
	}
	
	@Test
	@DisplayName("Invalid Argument in Constructor for Existing Account")
	public void testConstructorExistingAccountIllegalArgumentInput() {
		// accountID
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			illegalExistingDebit = new BankUserAccount("1204.01","500","1.2");
		});
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			illegalExistingDebit = new BankUserAccount("1204.08.12534","500","1.2");
		});
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			illegalExistingDebit = new BankUserAccount("1204.01.125","500","1.2");
		});
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			illegalExistingDebit = new BankUserAccount("1004.01.12535","500","1.2");
		});
		
		// balance
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			illegalExistingDebit = new BankUserAccount("1204.01.12535","-10","1.2");
		});
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			illegalExistingDebit = new BankUserAccount("1204.01.12535","fem hundre","1.2");
		});
		
		// interestRate
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			illegalExistingDebit = new BankUserAccount("1204.01.12535","500","30.5");
		});
	}
	
	@Test
	@DisplayName("Test deposit")
	public void testDeposit() {
		Assertions.assertEquals(0.0, debit.getBalance(),epsilon);
		debit.deposit(100.0);
		Assertions.assertEquals(100.0, debit.getBalance(),epsilon);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			debit.deposit(-50.0);
		}, "Can't deposit a negative amount");
		Assertions.assertEquals(100.0, debit.getBalance(),epsilon);
		
	}
	
	@Test
	@DisplayName("Test withdraw")
	public void testWithdraw() {
		Assertions.assertEquals(0.0, debit.getBalance(),epsilon);
		debit.deposit(100.0);
		Assertions.assertEquals(100.0, debit.getBalance(),epsilon);
		
		debit.withdraw(20.0);
		Assertions.assertEquals(80.0, debit.getBalance(),epsilon);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			debit.withdraw(-50.0);
		}, "Can't withdraw a negative amount");
		Assertions.assertEquals(80.0, debit.getBalance(),epsilon);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			debit.withdraw(500.0);
		}, "Can't withdraw more than is in balance");
		Assertions.assertEquals(80.0, debit.getBalance(),epsilon);
		
	}
	
	
}
