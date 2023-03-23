package common;

public class CreditCard {

	private String number;
	private String expiryMonth;
	private String expiryYear;
	private String cvv;

	public CreditCard(String number, String expiryMonth, String expiryYear, String cvv){
		this.setNumber(number);
		this.setExpiryMonth(expiryMonth);
		this.setExpiryYear(expiryYear);
		this.setCvv(cvv);
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getExpiryMonth() {
		return expiryMonth;
	}

	public void setExpiryMonth(String expiryMonth) {
		this.expiryMonth = expiryMonth;
	}

	public String getExpiryYear() {
		return expiryYear;
	}

	public void setExpiryYear(String expiryYear) {
		this.expiryYear = expiryYear;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

}
