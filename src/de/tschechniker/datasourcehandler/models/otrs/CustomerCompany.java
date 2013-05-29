package de.tschechniker.datasourcehandler.models.otrs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.soap.SOAPException;

import de.tschechniker.datasourcehandler.annotations.Class;
import de.tschechniker.datasourcehandler.annotations.Id;
import de.tschechniker.datasourcehandler.annotations.MappedBy;
import de.tschechniker.datasourcehandler.annotations.Method;
import de.tschechniker.datasourcehandler.exceptions.IdNotInizializedException;
import de.tschechniker.datasourcehandler.exceptions.NoConnectionException;
import de.tschechniker.datasourcehandler.resources.OtrsDS;
import de.tudan.otrsclient.SimpleSoapMessageParser;

@Class("CustomerCompanyObject")
@Method("CustomerCompanyGet")
public class CustomerCompany extends OtrsDS {	
	@Override
	public String toString() {
		return "CustomerCompany [CustomerId=" + CustomerId + ", name=" + name
				+ "]";
	}

	@Id
	@MappedBy("CustomerID")
	String CustomerId;
	@MappedBy("CustomerCompanyName")
	private String name;

	public CustomerCompany(){
		super();
	}
	
	public CustomerCompany(String id) throws NoConnectionException, IdNotInizializedException{
		super(id);
	}
	public void test() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("CustomerID", this.CustomerId);
			System.err.println(this.getAllProperties("CustomerCompanyGet",
					params));
		} catch (SOAPException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<?> getAll() throws NoConnectionException, SOAPException,
			IOException, IdNotInizializedException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCustomerId() {
		return CustomerId;
	}

	public void setCustomerId(String customerId) {
		CustomerId = customerId;
	}

	public List<CustomerUser> getCustomerUser() {
		List<CustomerUser> users = new ArrayList<CustomerUser>();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("CustomerID", this.getID());
			Map<String, Object> data = new SimpleSoapMessageParser()
					.nodesToMap(this.dispatchCall(
							"CustomerUserObject", "CustomerSearch", params));
			for(Entry<String, Object> dat : data.entrySet()){
				users.add(new CustomerUser(dat.getKey()));
			}
		} catch (SOAPException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IdNotInizializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return users;
	}

}
