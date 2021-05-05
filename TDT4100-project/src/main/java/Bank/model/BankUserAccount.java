package Bank.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class BankUserAccount {	
	
	private static final Collection<String> ALL_INTEREST_RATES = Arrays.asList("1.2","1.1","1.5","1.8");
	
	private static final HashMap<String,String> ALL_ACCOUNT_TYPES = new HashMap<String,String>();
	static {
		ALL_ACCOUNT_TYPES.put("debit","01");
		ALL_ACCOUNT_TYPES.put("credit","02");
		ALL_ACCOUNT_TYPES.put("savings","03");
		ALL_ACCOUNT_TYPES.put("funds","04");
	};
	
	//Fields
	
	private List<String> accountID;
	private Double balance;		
	private Double interestRate; 
	
	
	
	// Constructor
	
	public BankUserAccount(String accountType) {
		this.accountID = generateAccountID(accountType);
		this.balance = 0.0;
		this.interestRate = retriveInterestRate();
	}

	public BankUserAccount(String accountID, String str_balance, String str_interestRate) {
		this.accountID = setAccountID(accountID);
		
		// validate and set balance
		Double balance = Double.parseDouble(str_balance);
		notNegativeCheck(balance);
		this.balance = balance;
		
		// validate and set interest rate
		Double interestRate = Double.parseDouble(str_interestRate);
		notNegativeCheck(interestRate);
		validateInterestRate(interestRate);
		this.interestRate = interestRate;
	}
	
	
	// Logic
	
	private List<String> generateAccountID(String accountType) {
		/*
		 1204 BANK ID // XX Account type // XXXXX Account ID (Adds up to 5*10000 possible accounts)
		 "debit 01","credit 02","savings 03","funds 04","mortgage 05" 
		 */
		
		List<String> accountID = new ArrayList<>();
		
		// Adding bankID
		accountID.add("1204"); 
		
		// Find Account typeID
		accountType = accountType.toLowerCase();
		
		for (String checkAccountType : ALL_ACCOUNT_TYPES.keySet()) {	
			if (checkAccountType.equals(accountType)) {
				
				accountID.add(ALL_ACCOUNT_TYPES.get(accountType)); // adds accountTypeID to AccountID 
				break;
			}
		}
		
		// Check if the accountType parameter is valid else throw IllegalArgumentException
		if (accountID.size() == 2) {	
			
			// Generate personalID
			Random rand = new Random();
			int upperbound = 99999;
			Integer int_random = rand.nextInt(upperbound);
			String str_random = int_random.toString();
			
			// Add zeros to the front if it's not 5 digits
			if (str_random.length() != 5) {
				int howManyZerosToAdd = 5 - str_random.length();
				for (int i = 0; i < howManyZerosToAdd; i++) {
					str_random = "0"+str_random;
				}
			}
			accountID.add(str_random); // adds personalID
			
			
			return accountID;
					
		} else {
			throw new IllegalArgumentException("must be either Debit, Credit, Savings or Funds");	
			}
		}
	

	public void deposit(Double depositAmount) {
		notNegativeCheck(depositAmount);
		double roundDepositAmount = Math.round(depositAmount * 100.0)/100.0;
		this.balance += roundDepositAmount;	
	}
	
	public void withdraw(Double withdrawAmount) {
		notNegativeCheck(withdrawAmount);
		
		double roundWithdrawAmount = Math.round(withdrawAmount * 100.0)/100.0;
		
		if (this.balance < roundWithdrawAmount) {
			throw new IllegalArgumentException("you are asking to withdraw more than you have.");
		}
		
		this.balance -= roundWithdrawAmount;
	}
	
	
	// Getters
		
	public Double getBalance() {
		return this.balance;
	}
	
	public Double getInterestRate() {
		return this.interestRate;
	}
	
	public String getAccountID() {
		String str_AccountID = "";
		for (String str : accountID) {
			str_AccountID += str+".";
		}
		return str_AccountID.substring(0,str_AccountID.length()-1);
	}
	
	
	//Setters
	
	private Double retriveInterestRate() {
		String accountType = this.accountID.get(1);
		int int_accountType = Integer.parseInt(accountType); 
		
		// Remove one to get correct index for ALL_INTEREST_RATES
		int_accountType--;
		
		int counter = 0;
		for (String interestRate : ALL_INTEREST_RATES) {
			
			if (counter == int_accountType) { 
				
				return Double.parseDouble(interestRate);	
			}
			else {
				counter++;
			}	
		}
		throw new IllegalStateException("Something went wrong while setting intrestRate"); 
	}
	
	private ArrayList<String> setAccountID(String accountID) {	
		
		ArrayList<String> actualAccountID = new ArrayList<>();
			
		validateAccountID(accountID);
			
		String[] lst_accountID = accountID.split("[.]");
		for (String el : lst_accountID) {
			actualAccountID.add(el);
		}
		return actualAccountID;
	}
	
	
	// Validate
	
	private void notNegativeCheck(Double number) {
		if (number < 0) {
			throw new IllegalArgumentException("number cannot be negative!");
		}
	}
	
	private void validateAccountID(String accountID) {
		String[] lst_accountID = accountID.split("[.]");
		if (lst_accountID.length != 3 ||
			!lst_accountID[0].equals("1204") ||
			!ALL_ACCOUNT_TYPES.containsValue(lst_accountID[1]) ||
			lst_accountID[2].length() != 5) 
			
			throw new IllegalArgumentException("invalid accountID");
		}
	
	private void validateInterestRate(Double interestRate) {
		if (!retriveInterestRate().equals(interestRate)) {
			throw new IllegalArgumentException("invalid interestRate");
		}
	}
	
	@Override
	public String toString() {
		String account = String.format(
				"""
				AccountID: %s
					Balance: %.2f
					InterestRate: %.2f
				""",
				this.getAccountID(),
				this.getBalance(),
				this.getInterestRate()
				);			
		return account;
	}
	
}
