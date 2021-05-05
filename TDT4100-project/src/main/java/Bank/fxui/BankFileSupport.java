package Bank.fxui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;

import Bank.model.BankUserAccount;
import Bank.model.BankUserData;


public class BankFileSupport implements IBankFileReading {


	public void writeAccountInfoToFile(String filename, BankUserData bankUserData) throws IOException {
		try(FileWriter writer = new FileWriter(filename)) {
			
		for (BankUserAccount bankAccount : bankUserData.getAllAccounts()) {
			writer.write(bankAccount.getAccountID() + "," + bankAccount.getBalance() + "," + bankAccount.getInterestRate() + "\n");
		}
		writer.flush();
		writer.close();
		}
	}
		
	public BankUserData getBankUserDataFromFile(String filename) throws FileNotFoundException {
		try(Scanner scanner = new Scanner(new File(filename))) {
			BankUserData bankUserData = new BankUserData(); 
		
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] lineInfo = line.split(",");
		
				String accountID = lineInfo[0];
				String balance = lineInfo[1];
				String interestRate = lineInfo[2];

				BankUserAccount bankAccount = new BankUserAccount(accountID, balance, interestRate);
				bankUserData.addBankAccount(bankAccount);
			}
		
			scanner.close();
			return bankUserData;
		}	
	}
	

}


	
