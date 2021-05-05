package Bank.fxui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

import Bank.model.BankUser;
import Bank.model.BankUserAccount;
import Bank.model.BankUserData;
import Bank.model.BankUserProfile;



public class BankController{
	
	@FXML
	private Button button;
	
	@FXML
	private TextField txtEmail;
	
	@FXML 
	private TextField txtPassword;
	
	@FXML
	private Label wrongLogin;
	
	@FXML
	private TextField accountID;

	@FXML
	private TextField amount;
	
	@FXML
	private TextField totalBalance;
	
	@FXML
	private TextField accountTypeTextField;
	
	@FXML
	private Text statusText;
	
	@FXML
	private ListView<BankUserAccount> outputAccountInfo;
	
	@FXML
	private TextArea outputUserProfileInfo;
    
	private static BankUser bankUser;

    

	
	@FXML 
	void initialize(){
			bankUser = BankLoginController.getUser();
			outputAccountInfo.getSelectionModel().selectedItemProperty().addListener((prop, oldValue, newValue) -> {
				handleOutputAccountInfo();
			});
			// Print userInfo
			BankUserProfile profileInfo = bankUser.getUseProfile();
			outputUserProfileInfo.setText(profileInfo.toString());
		
			handleOutputAccountInfo();
		
			updateAccountInfo();
	}
	
	@FXML
	private void updateAccountInfo() {
        try {
        	// Account Overview
        	outputAccountInfo.getItems().clear();
        	BankUserData userData = bankUser.getUserData();
        	for (BankUserAccount account : userData.getAllAccounts()) {
        		outputAccountInfo.getItems().add(account);
        	}
        
        	// Total Balance
        	totalBalance.setText(userData.getTotalBalance().toString());
        
        	// Write new userData to file 
        	String email = bankUser.getUseProfile().getEmail();
        	String formatedEmail = email.replace(".","_");
        	String emailAsFilename = formatedEmail + ".txt";
        	BankFileSupport bankFileSupport = new BankFileSupport();
       
        	bankFileSupport.writeAccountInfoToFile(emailAsFilename,userData);
        	
        } catch (IOException e) {
        	statusText.setText("Unable to update account info " + e.getMessage());
        }
        
    }
	
	
	private void handleOutputAccountInfo() {
		if (accountID != null)
			if (outputAccountInfo.getSelectionModel().getSelectedItem() != null) {
				System.out.println(outputAccountInfo.getSelectionModel().getSelectedItem());
				accountID.setText((outputAccountInfo.getSelectionModel().getSelectedItem()).getAccountID());
			}
	}
 
	
	@FXML
	void depositButtonOnAction() {
		try {
			String accountID = this.accountID.getText();
			Double amount = Double.parseDouble(this.amount.getText());
			boolean depositComplete = false;
			
			if (accountID != null) {
				BankUserData userData = bankUser.getUserData();
				for (BankUserAccount account : userData.getAllAccounts()) {
					if (accountID.equals(account.getAccountID())) {
					account.deposit(amount);
					statusText.setText(("Deposit succsessfull"));
					depositComplete = true;
					}
				}
			}if (!depositComplete) {
				throw new IllegalArgumentException("Wrong Account ID");
				}
		}catch (IllegalArgumentException e) {
			statusText.setText("Cant deposit " + e.getMessage()); 
		}
		updateAccountInfo();
	}

	@FXML
	void withdrawButtonOnAction() {
		try {
			String accountID = this.accountID.getText();
			Double amount = Double.parseDouble(this.amount.getText());
			boolean withdrawComplete = false;
			
			if (accountID != null) {
				BankUserData userData = bankUser.getUserData();
				for (BankUserAccount account : userData.getAllAccounts()) {
					if (accountID.equals(account.getAccountID())) {
						account.withdraw(amount);
						statusText.setText(("withdraw succsessfull"));
						withdrawComplete = true;
				}
			}
			}if (!withdrawComplete) {
				throw new IllegalArgumentException("wrong Account ID");
				}
		}catch (IllegalArgumentException e) {
			statusText.setText("Unable to withdraw " + e.getMessage());
		}
		updateAccountInfo();
	}
	
	@FXML
	void handleCreateNewAccountButtonOnAction() {
		try {	
			String accountType = this.accountTypeTextField.getText(); 
			BankUserData userData = bankUser.getUserData();
			userData.createNewBankAccount(accountType);
			//throw new IOException("Account created");
			statusText.setText("Account created");
			updateAccountInfo();
		} catch(IllegalArgumentException e) {
			statusText.setText("Failed to create account " + e.getMessage());
		}
	}
	
	@FXML
	void handleDeleteButtonOnAction() {
		try {
			String accountID = this.accountID.getText();
		
			if (accountID != null) {
				BankUserData userData = bankUser.getUserData();
				for (BankUserAccount account : userData.getAllAccounts()) {
					if (accountID.equals(account.getAccountID())) {
						bankUser.getUserData().deleteBankAccount(account);
						statusText.setText(("Acccount was succsessfully deleted"));
					}
				}	
			}
		}catch (IllegalStateException e) {
			statusText.setText("Unable to delete " + e.getMessage());
		}
		updateAccountInfo();
	}

}



