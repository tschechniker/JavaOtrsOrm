package de.tschechniker.datasourcehandler.resources;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import de.tschechniker.datasourcehandler.DSHandler;
import de.tschechniker.datasourcehandler.exceptions.IdNotInizializedException;
import de.tschechniker.datasourcehandler.exceptions.NoConnectionException;
import de.tschechniker.datasourcehandler.models.otrs.Ticket;
import de.tudan.otrsclient.OtrsConnector;
import de.tudan.otrsclient.SimpleSoapMessageParser;

public abstract class OtrsDS extends DSHandler {

	private static String otrsUser;
	private static String otrsPass;
	private static String otrsUrl;
	private OtrsConnector connector;

	/*
	 * Creates a OtrsDS Object
	 */
	public OtrsDS() {
		super();
	}

	/*
	 * Creates a OtrsDS Object and prefill's it
	 */
	public OtrsDS(Object id) throws NoConnectionException,
			IdNotInizializedException {
		super();
		if (this.testConnection()) {
			this.setID(id);
			this.prefill();
		}
	}

	/*
	 * Inizialize the OtrsDS
	 */
	public static void inizialize(String user, String pass, String url) {
		OtrsDS.otrsUser = user;
		OtrsDS.otrsPass = pass;
		OtrsDS.otrsUrl = url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tschechniker.datasourcehandler.DSHandler#testConnection()
	 */
	@Override
	public boolean testConnection() throws NoConnectionException {
		this.connect();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Search", "*");
		Object[] result = null;
		try {
			result = new SimpleSoapMessageParser().nodesToArray(this.connector
					.dispatchCall("UserObject", "UserSearch", params));
		} catch (SOAPException | IOException e) {
			throw new NoConnectionException(e.getMessage());
		}
		this.disconnect();
		if (result != null && result.length > 0) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tschechniker.datasourcehandler.DSHandler#connect()
	 */
	@Override
	public void connect() throws NoConnectionException {
		try {
			this.connector = new OtrsConnector(OtrsDS.otrsUrl, OtrsDS.otrsUser,
					OtrsDS.otrsPass);
		} catch (MalformedURLException e) {
			throw new NoConnectionException(e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tschechniker.datasourcehandler.DSHandler#disconnect()
	 */
	@Override
	public void disconnect() {
		this.connector = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tschechniker.datasourcehandler.DSHandler#prefill()
	 */
	@Override
	protected void prefill() throws IdNotInizializedException {
		try {
			if (this.getID() != null) {
				if (this.getFieldValue(this.getIDName()) instanceof Integer
						&& (Integer) this.getFieldValue(this.getIDName()) == 0) {
					throw new IdNotInizializedException();
				}
				Map<String, Object> params = new HashMap<String, Object>();
				params.put(this.getMappedBy(this.getIDName()),
						this.getFieldValue(this.getIDName()));
				Map<String, Object> data = new SimpleSoapMessageParser()
						.nodesToMap(this.dispatchCall(
								this.getClass(this), this.getClassMethod(),
								params));
				this.prefill(data);
			} else {
				throw new IdNotInizializedException();
			}
		} catch (IllegalArgumentException | IllegalAccessException
				| SOAPException | IOException | NoConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public SOAPMessage dispatchCall(String object, String method,
			Map<String, Object> params) throws SOAPException, IOException,
			NoConnectionException {
		this.connect();
		SOAPMessage tmp = this.connector.dispatchCall(object, method, params);
		this.disconnect();
		return tmp;
	}

	public SOAPMessage dispatchCall(String object, String method)
			throws SOAPException, IOException, NoConnectionException {
		this.connect();
		SOAPMessage tmp = this.connector.dispatchCall(object, method);
		this.disconnect();
		return tmp;
	}

	public abstract List<?> getAll() throws NoConnectionException,
			SOAPException, IOException, IdNotInizializedException;

	protected Map<String, Object> getAllProperties(String method,
			Map<String, Object> params) throws SOAPException, IOException,
			NoConnectionException {
		this.connect();
		return new SimpleSoapMessageParser().nodesToMap(this.connector
				.dispatchCall(this.getClass(this), method, params));
	}
	
	public boolean create() throws SOAPException, IOException, NoConnectionException{
		Map<String, Object> params = this.getAllCreate();
		List<?> id = new SimpleSoapMessageParser().nodesToList(this.dispatchCall(this.getClass(this), "TicketCreate", params));
		if(id.size() == 1){
			this.setID(id.get(0));
			return true;
		} else {
			return false;
		}
	}
	
	public boolean delete(){
		// TODO Implement delete method
		
		
		return false;
	}
	
	public boolean update(){
		// TODO Implement update method
		return false;
	}

}
