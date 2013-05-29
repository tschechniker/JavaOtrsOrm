package de.tschechniker.datasourcehandler.models.otrs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.soap.SOAPException;

import de.tschechniker.datasourcehandler.annotations.Class;
import de.tschechniker.datasourcehandler.annotations.Create;
import de.tschechniker.datasourcehandler.annotations.Delete;
import de.tschechniker.datasourcehandler.annotations.Id;
import de.tschechniker.datasourcehandler.annotations.MappedBy;
import de.tschechniker.datasourcehandler.annotations.Method;
import de.tschechniker.datasourcehandler.exceptions.IdNotInizializedException;
import de.tschechniker.datasourcehandler.exceptions.NoConnectionException;
import de.tschechniker.datasourcehandler.resources.OtrsDS;
import de.tudan.otrsclient.SimpleSoapMessageParser;

@Class("TicketObject")
@Method(get="TicketGet", create="TicketCreate", delete="TicketDelete", update="")
public class Ticket extends OtrsDS {
	@Id
	@MappedBy("TicketID")
	@Delete("TicketID")
	private int id;

	@MappedBy("Queue")
	@Create("Queue")
	private String queue;

	@MappedBy("StateID")
	@Create("StateID")
	private int stateID;

	@Create("CustomerID")
	@MappedBy("CustomerID")
	private String customerID;

	@MappedBy("OwnerID")
	@Create("OwnerID")
	private int ownerID;

	@Create("CustomerUser")
	@MappedBy("CustomerUserID")
	private String customerUserID;

	@MappedBy("Title")
	@Create("Title")
	private String title;

	@MappedBy("Lock")
	@Create("Lock")
	private String lock;

	@Create("Priority")
	@MappedBy("Priority")
	private String priority;

	@Create("UserID")
	@Delete("UserID")
	private int UserID = 1;

	private Map<String, Object> dynamicFields;

	public Ticket() {
		super();
	}

	public Ticket(int id) throws NoConnectionException,
			IdNotInizializedException {
		super(id);
	}

	@Override
	public List<?> getAll() throws NoConnectionException, SOAPException,
			IOException, IdNotInizializedException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getStateID() {
		return stateID;
	}

	public void setStateID(int stateID) {
		this.stateID = stateID;
	}

	public CustomerUser getCustomerUser() throws NoConnectionException,
			IdNotInizializedException {
		return new CustomerUser(this.customerUserID);
	}

	public User getOwner() throws NoConnectionException,
			IdNotInizializedException {
		return new User(this.ownerID);
	}

	public CustomerCompany getCustomerCompany() throws NoConnectionException,
			IdNotInizializedException {
		return new CustomerCompany(this.customerID);
	}

	public List<Ticket> getWithDynamicField(String fieldName, String value)
			throws IdNotInizializedException {
		return this.getWithDynamicField(fieldName, value, "Equal");
	}

	public List<Ticket> getWithDynamicField(String fieldName, String value,
			String MatchMode) throws IdNotInizializedException {
		List<Ticket> tickets = new ArrayList<Ticket>();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("Result", "ARRAY");
			Map<String, Object> dynField = new HashMap<String, Object>();
			dynField.put(MatchMode, value);
			params.put("DynamicField_" + fieldName, dynField);
			params.put("UserID", "1");
			List<?> ids = new SimpleSoapMessageParser().nodesToList(this
					.dispatchCall(this.getClass(this), "TicketSearch", params));
			// System.out.println(ids);
			for (Object id : ids) {
				System.out.println(id);
				tickets.add(new Ticket((Integer) id));
			}
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tickets;
	}

	public Object getDynamicField(String name) {
		return this.dynamicFields.get(name);
	}

	public Map<String, Object> getDynamicFields() {
		return this.dynamicFields;
	}

	@Override
	public void prefill() throws IdNotInizializedException {
		super.prefill();
		try {
			this.dynamicFields = new HashMap<String, Object>();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("TicketID", this.getID());
			params.put("UserID", 1);
			params.put("DynamicFields", "1");

			Map<String, Object> data = new SimpleSoapMessageParser()
					.nodesToMap(this.dispatchCall(this.getClass(this),
							this.getClassMethod("get"), params));
			for (Entry<String, Object> entry : data.entrySet()) {
				if (entry.getKey().startsWith("DynamicField_")) {
					String DynamicFieldName = entry.getKey().replace(
							"DynamicField_", "");
					this.dynamicFields.put(DynamicFieldName, entry.getValue());
				}
			}
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void lock() {
		this.lock = "lock";
	}

	public void unlock() {
		this.lock = "unlock";
	}

	public boolean isLocked() {
		if (this.lock.equals("lock")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", queue=" + queue + ", stateID=" + stateID
				+ ", customerID=" + customerID + ", ownerID=" + ownerID
				+ ", customerUserID=" + customerUserID + ", title=" + title
				+ ", lock=" + lock + ", priority=" + priority
				+ ", dynamicFields=" + dynamicFields + "]";
	}

}
