package de.tschechniker.datasourcehandler.models.otrs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPException;

import de.tschechniker.datasourcehandler.annotations.Class;
import de.tschechniker.datasourcehandler.annotations.Id;
import de.tschechniker.datasourcehandler.annotations.MappedBy;
import de.tschechniker.datasourcehandler.annotations.Method;
import de.tschechniker.datasourcehandler.exceptions.IdNotInizializedException;
import de.tschechniker.datasourcehandler.exceptions.NoConnectionException;
import de.tschechniker.datasourcehandler.resources.OtrsDS;
import de.tudan.otrsclient.SimpleSoapMessageParser;

@Class("UserObject")
@Method("GetUserData")
public class User extends OtrsDS{
	
	public User() throws NoConnectionException {
		super();
	}
	
	public User(int id) throws NoConnectionException, IdNotInizializedException{
		super(id);
	}
	
	public void test(){
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("UserID", this.id);
			System.err.println(this.getAllProperties("GetUserData", params));
		} catch (SOAPException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Id
	@MappedBy("UserID")
	private Integer id;
	@MappedBy("UserFirstname")
	private String firstname;
	@MappedBy("UserLastname")
	private String lastname;
	@MappedBy("UserLogin")
	private String username;
	@MappedBy("UserEmail")
	private String email;

	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String toString(){
		return this.id+" "+this.username+" "+this.firstname+" "+this.lastname+" "+this.email;
	}
	
	
	public static User getUser(int ID) throws NoConnectionException, IdNotInizializedException{
		User user = new User();
		user.setID(ID);
		user.prefill();
		return user;
	}


	@Override
	public List<User> getAll() throws NoConnectionException, SOAPException, IOException, IdNotInizializedException {
		List<User> users = new ArrayList<User>();
		Map<String, Object> userData = new SimpleSoapMessageParser().nodesToMap(this.dispatchCall(this.getClass(this), "UserList"));
		for(Map.Entry<String, Object> entry : userData.entrySet()){
			User tmp = new User();
			tmp.setID(Integer.valueOf(entry.getKey()));
			tmp.prefill();
			users.add(tmp);
		}
		
		return users;
	}

}
