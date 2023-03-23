package common;

public class Address {
	private String addressLineOne;
	private String addressLineTwo;
	private String city;
	private String postalCode;
	private String state;
	private String country;

	public Address() {
	}

	public Address(String addressLineOne, String addressLineTwo, String city, String postalCode, String state,
			String country) {
		this.setAddressLineOne(addressLineOne);
		this.setAddressLineTwo(addressLineTwo);
		this.setCity(city);
		this.setPostalCode(postalCode);
		this.setState(state);
		this.setCountry(country);
	}

	public String getAddressLineOne() {
		return addressLineOne;
	}

	public void setAddressLineOne(String addressLineOne) {
		this.addressLineOne = addressLineOne;
	}

	public String getAddressLineTwo() {
		return addressLineTwo;
	}

	public void setAddressLineTwo(String addressLineTwo) {
		this.addressLineTwo = addressLineTwo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
