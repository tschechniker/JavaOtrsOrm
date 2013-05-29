package de.tschechniker.datasourcehandler.models.otrs;

import java.io.IOException;
import java.util.List;
import javax.xml.soap.SOAPException;

import de.tschechniker.datasourcehandler.annotations.Class;
import de.tschechniker.datasourcehandler.annotations.Id;
import de.tschechniker.datasourcehandler.annotations.MappedBy;
import de.tschechniker.datasourcehandler.annotations.Method;
import de.tschechniker.datasourcehandler.exceptions.IdNotInizializedException;
import de.tschechniker.datasourcehandler.exceptions.NoConnectionException;
import de.tschechniker.datasourcehandler.resources.OtrsDS;

@Class("CustomerUserObject")
@Method("CustomerUserDataGet")
public class CustomerUser extends OtrsDS{
	
	@Id
	@MappedBy("User")
	String Id;
	@MappedBy("UserLogin")
	String username;
	@MappedBy("UserFistname")
	String firstname;
	@MappedBy("UserLastname")
	String lastname;
	@MappedBy("UserEmail")
	String email;
	@MappedBy("UserCustomerID")
	String CompanyID;
	

	public CustomerUser() throws NoConnectionException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CustomerUser(String ID) throws NoConnectionException, IdNotInizializedException{
		super(ID);
	}

	@Override
	public List<?> getAll() throws NoConnectionException, SOAPException,
			IOException, IdNotInizializedException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompanyID() {
		return CompanyID;
	}

	public void setCompanyID(String companyID) {
		CompanyID = companyID;
	}
	
	@Override
	public String toString() {
		return "CustomerUser [Id=" + Id + ", username=" + username
				+ ", firstname=" + firstname + ", lastname=" + lastname
				+ ", email=" + email + ", CompanyID=" + CompanyID + "]";
	}
	
	
	
	
	

}
