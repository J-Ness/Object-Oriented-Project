package Bank.fxui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class BankApp extends Application{
   
	@Override
    public void start(Stage stage) throws Exception {
    	Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("BankLogin.fxml"));
        stage.setTitle("My app");
    	stage.setScene(new Scene(parent));
        stage.show();
    }

    public static void main(String[] args) {
        launch(BankApp.class, args);
    }
    
}


