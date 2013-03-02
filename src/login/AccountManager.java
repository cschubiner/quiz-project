package login;

import java.util.HashMap;
import java.util.Map;

public class AccountManager {
	
	private Map<String, String> loginMap;
	
	//constructor
	public AccountManager(){
		loginMap = new HashMap<String, String>();
		//initial accounts as required by handout
		loginMap.put("Patrick", "1234");
		loginMap.put("Molly", "FloPup");
	}
	
	//checks to see if the given user is in the database
	public boolean userExists(String userName){
		return loginMap.containsKey(userName);
	}
	
	//if the user does not exist, return false
	//otherwise return if the correct password for that user was provided
	public boolean login(String userName, String password){
		if(!userExists(userName)) return false;
		return loginMap.get(userName).equals(password);
	}
	
	//return true if the account creation was successful, false otherwise
	//if successful, account is added to loginMap
	public boolean createAccount(String userName, String password){
		if(userExists(userName)) return false;
		loginMap.put(userName, password);
		return true;
	}
	
	
	
	
	
	

}
