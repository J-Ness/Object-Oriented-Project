package Bank.model;

public class BankUser {
	
	private BankUserProfile userProfile;
	private BankUserData userData;
	
	
	// Constructor
	
	public BankUser(String name, String email, String password, BankUserData bankUserData) {
		
		BankUserProfile bankUserProfile = new BankUserProfile(name,email,password);
		
		this.userData = bankUserData;
		this.userProfile = bankUserProfile;	
	}
		
	
	// Getter
	public BankUserData getUserData() {
		return this.userData;
	}
	
	public BankUserProfile getUseProfile() {
		return this.userProfile;
	}
	
}
