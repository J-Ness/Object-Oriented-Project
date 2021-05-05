package Bank.model;

import java.util.*;

public class BankUserProfile {

	// fieldss
	
	private String name;
	private String email;
	private String password;
	private static final String[] nationCodes = {"ad", "ae", "af", "ag", "ai", "al", "am", "ao", "aq", "ar", "as", "at", "au", "aw", "ax", "az", "ba", "bb", "bd", "be", "bf", "bg", "bh", "bi", "bj", "bl", "bm", "bn", "bo", "bq", "br", "bs", "bt", "bv", "bw", "by", "bz", "ca", "cc", "cd", "cf", "cg", "ch", "ci", "ck", "cl", "cm", "cn", "co", "cr", "cu", "cv", "cw", "cx", "cy", "cz", "de", "dj", "dk", "dm", "do", "dz", "ec", "ee", "eg", "eh", "er", "es", "et", "fi", "fj", "fk", "fm", "fo", "fr", "ga", "gb", "gd", "ge", "gf", "gg", "gh", "gi", "gl", "gm", "gn", "gp", "gq", "gr", "gs", "gt", "gu", "gw", "gy", "hk", "hm", "hn", "hr", "ht", "hu", "id", "ie", "il", "im", "in", "io", "iq", "ir", "is", "it", "je", "jm", "jo", "jp", "ke", "kg", "kh", "ki", "km", "kn", "kp", "kr", "kw", "ky", "kz", "la", "lb", "lc", "li", "lk", "lr", "ls", "lt", "lu", "lv", "ly", "ma", "mc", "md", "me", "mf", "mg", "mh", "mk", "ml", "mm", "mn", "mo", "mp", "mq", "mr", "ms", "mt", "mu", "mv", "mw", "mx", "my", "mz", "na", "nc", "ne", "nf", "ng", "ni", "nl", "no", "np", "nr", "nu", "nz", "om", "pa", "pe", "pf", "pg", "ph", "pk", "pl", "pm", "pn", "pr", "ps", "pt", "pw", "py", "qa", "re", "ro", "rs", "ru", "rw", "sa", "sb", "sc", "sd", "se", "sg", "sh", "si", "sj", "sk", "sl", "sm", "sn", "so", "sr", "ss", "st", "sv", "sx", "sy", "sz", "tc", "td", "tf", "tg", "th", "tj", "tk", "tl", "tm", "tn", "to", "tr", "tt", "tv", "tw", "tz", "ua", "ug", "um", "us", "uy", "uz", "va", "vc", "ve", "vg", "vi", "vn", "vu", "wf", "ws", "ye", "yt", "za", "zm", "zw"};
	
	
	// Constructor
	
	public BankUserProfile(String name, String email, String password) {
		this.name = setName(name);
		this.email = setEmail(email);
		this.password = setNewPassword(password);
	}
	
	public BankUserProfile(String email, String password) {
		this.name = getNameFromEmail(email);
		this.email = setEmail(email);
		this.password = setNewPassword(password);
	}
	
	
	// Setters
	
	public String setName(String name) {
		checkName(name);
		//this.name = name;
		return name;
	}
	
	public String setEmail(String email) {
		checkEmail(email);
		//this.email = email;
		return email;
	}
		
	private String setNewPassword(String password) {
		validatePassword(password);
		//this.password = password;
		return password;
	}


	// Getters
	
	public String getNameFromEmail(String email) {
		String[] elEmail = email.split("[@.]"); 
		String name = elEmail[0]+ " " + elEmail[1];
		return name;
	}
	
	
	public String getName() {
		return this.name;
	}

	public String getEmail() {
		return this.email;
	}
	
	public String getPassword() {
		return this.password;
	}
 
	
	// Validate 
	
	private void checkName(String name) {
		String[] list = name.split(" ");
		if (list.length != 2) {
			throw new IllegalArgumentException();
		}
		if (list[0].length() < 2 || list[1].length() < 2) {
			throw new IllegalArgumentException();
		}

		String namelist = String.join("",list).toLowerCase();
		
	    char[] charArray = namelist.toCharArray();
	    for (int i = 0; i < charArray.length; i++) {
	       char ch = charArray[i];
	       
	       if (!(ch >= 'a' && ch <= 'z')) {
	          throw new IllegalArgumentException();
	       }
	    }
	}
	
	
	private void checkEmail(String email) {
		if (email.contains("@")) {
			
			name = this.name.toLowerCase();
			email = email.toLowerCase();

			String[] elEmail = email.split("[@.]"); 
															
			
			String[] splitName = name.split(" ");
			
			if (elEmail.length != 4) {
				throw new IllegalArgumentException("Email must contain 'firstname.lastname@ntnu.nationCode'");
			}
			
			if (!splitName[0].equals(elEmail[0]) || !splitName[1].equals(elEmail[1])) {
				//if (!elEmail[2].equals("ntnu")) {
				System.out.println(this.name + splitName[0] + splitName[1]);
				throw new IllegalArgumentException("Your name must match the email");
			    
			}
			if (!Arrays.asList(nationCodes).contains(elEmail[3])) {
				throw new IllegalArgumentException("The nation code is not a real one");
			}
		}
		else {
			throw new IllegalArgumentException("Email must contain '@'");
			}
	}
	
	
	private void validatePassword(String password) {
		if (password.length() < 8 || password == null)
			throw new IllegalArgumentException("Password must be at least 8 characters");
	}
	

	@Override
	public String toString() {
	String profile =
			"Name: " + getName() + "\n" + 
			"Email: " + getEmail() + "\n" +
			"Password: " + getPassword()+ "\n";
	return profile;
	}
		
		
}
	
