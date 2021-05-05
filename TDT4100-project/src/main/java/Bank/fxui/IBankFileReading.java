package Bank.fxui;

import java.io.FileNotFoundException;
import java.io.IOException;

import Bank.model.BankUserData;

public interface IBankFileReading  {
	
	void writeAccountInfoToFile(String filename, BankUserData bankUserData) throws IOException;
	
	BankUserData getBankUserDataFromFile(String filename) throws FileNotFoundException;
	
}
