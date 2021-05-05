package Bank.fxui;


import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Bank.model.BankUser;
import Bank.model.BankUserData;
import Bank.model.BankUserProfile;


public class BankLoginController {

	@FXML
	private TextField txtEmail;

	@FXML
    private TextField txtPassword;
	
    @FXML
    private Button loginButton;
    
    @FXML
    private Label loginStatusText;
    
    private static BankUser bankUser;
    
    public static BankUser getUser(){
    	return bankUser;
    }
    
     
	@FXML
	public void loginButtonAction(ActionEvent event) {
		checkLogin(event);	
	}
	
	
	@FXML
	private BankUserData retreveBankUserDataFromFile() {
		BankFileSupport bankFileSupport = new BankFileSupport();
		BankUserData bankUserData = new BankUserData();
		
		String inputEmail = txtEmail.getText().toString();
		String formatedEmail = inputEmail.replace(".","_");
		String emailAsFilename = formatedEmail+".txt";
		
		try {
			bankUserData = bankFileSupport.getBankUserDataFromFile(emailAsFilename);
			return bankUserData;
		
		} catch (IllegalArgumentException e) {
			loginStatusText.setText("User data is corrupted " + e.getMessage());
			return null;
		
		} catch (FileNotFoundException e) {
			loginStatusText.setText("This bank user does not exist");
			return null;
		}	
	}
		
	
	@FXML
	private void checkLogin(ActionEvent event) {
		try {
			if ((txtEmail.getText().toString().equals("user.one@ntnu.no") || txtEmail.getText().toString().equals("user.two@ntnu.no"))
				&& txtPassword.getText().toString().equals("adminadmin")) {
				
				BankUserProfile bankUserProfile = new BankUserProfile(txtEmail.getText().toString(), "adminadmin");
			
				BankUserData bankUserData = retreveBankUserDataFromFile();
				if(bankUserData == null) {
					throw new Exception();
				}
				
				// Using the Data from the file and the input to creates a bankUser object
				bankUser = new BankUser(
						bankUserProfile.getNameFromEmail(bankUserProfile.getEmail()),
						bankUserProfile.getEmail(),
						bankUserProfile.getPassword(),
						bankUserData
						);
				
				// Changing view to bank.fxml and then bankController takes over
				loginStatusText.setText("login success");
				((Node) event.getSource()).getScene().getWindow().hide();			
				Stage primaryStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Bank.fxml"));
				Scene scene = new Scene(root, 480,600);
				primaryStage.setScene(scene);
				primaryStage.show();	
			}
			else if (txtEmail.getText().isEmpty() || txtPassword.getText().isEmpty()) {
				throw new IllegalArgumentException("Please enter all your login data");
			}else {
				throw new IllegalArgumentException("Wrong username or password");
			}
		
		} catch(IllegalArgumentException e){
			loginStatusText.setText(e.getMessage());
		
		} catch(IOException e) {
			loginStatusText.setText(e.getMessage());
		
		}catch (Exception e) {
		}
	}
	
	
	
	

}