package Bank.model;

import java.util.ArrayList;
import java.util.List;

public class BankUserData {
	
	private List<BankUserAccount> allAccounts = new ArrayList<>();
	
	// Constructor
	
	public BankUserData() {
	}
		
	
	// Logic
	
	public void createNewBankAccount(String accountType) {
		this.allAccounts.add(new BankUserAccount(accountType));
	}
	
	public void deleteBankAccount(BankUserAccount bankAccount) {
		if (bankAccount.getBalance() <= 1.00) {
			allAccounts.remove(bankAccount);
		} else {
			throw new IllegalStateException("your balance is not empty");
		}
	}
	
	
	// Getters
	
	/* Makes a copy of collection and makes sure it's not possible to manipulate the original
 	witch ensures the right encapsulation and increases security */
	public List<BankUserAccount> getAllAccounts() {
		List<BankUserAccount> allAccountsCopy = new ArrayList<>();
		
		for (BankUserAccount bankAccount : this.allAccounts) {
			allAccountsCopy.add(bankAccount);
		}
		return allAccountsCopy;	
	}
	
	
	public Double getTotalBalance() {
		Double totalBalance = 0.00;
		for (BankUserAccount bankAccount: this.allAccounts) {
			
			totalBalance += bankAccount.getBalance();
		}
		return totalBalance;
	}
	
	
	public void addBankAccount(BankUserAccount bankAccount) {
		allAccounts.add(bankAccount);
	}
	
}

	

		

	



	
	
