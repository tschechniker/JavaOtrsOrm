OTRS-ORM for JAVA
=================

This ORM is based on the OTRS-Client form tudan. You can find the Code for the OTRS-Client here: https://github.com/gtudan/OTRS-Client

Do not use in Production! This Project is still in pre alpha status!

How to use
-----------
```java
	public static void main(String[] args){
		OtrsDS.inizialize("OTRS_SOAP_USER", "OTRS_SOAP_PASS", "http://<yourDomain>/otrs/rpc.pl");
		
		/*
		 * Get the User with ID 1
		 */
		try {
			User user1 = new User(1);
		} catch (NoConnectionException | IdNotInizializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		 * Get the Company with ID 1
		 */
		try {
			CustomerCompany company1 = new CustomerCompany("1");
		} catch (NoConnectionException | IdNotInizializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		 * Get the Customer with username test@example.com
		 */
		try {
			CustomerUser customeruser1 = new CustomerUser("test@example.com");
		} catch (NoConnectionException | IdNotInizializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		 * Get the Ticket with ID 1
		 */
		try {
			Ticket ticket1 = new Ticket(1);
		} catch (NoConnectionException | IdNotInizializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
```

