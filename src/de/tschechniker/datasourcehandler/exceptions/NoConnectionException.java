package de.tschechniker.datasourcehandler.exceptions;

public class NoConnectionException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7877822057102946843L;

	public NoConnectionException(String reason){
		super(reason);
	}
}
