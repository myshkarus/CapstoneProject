package common;

public class Person extends User {

	String contactNumber;
	Role role;
	Address address;

	public enum Role {USER, SUPPLIER}


	public Person() {
		super();
	}

	public Person(String firstName, String lastName, String email, String contactNumber, String password, Role role, Address address) {
		super(firstName, lastName, email, password);
		this.setContactNumber(contactNumber);
		this.setRole(role);
		this.setAddress(address);
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}



	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}


}
