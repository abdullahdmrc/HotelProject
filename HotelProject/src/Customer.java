import java.util.regex.Pattern;

public class Customer {

	private static int Id = 0;
	private int customerId;

	private String name;
	private String surname;
	private String gender;

	private Date birthdate;
	
	private Address address;

	private Phone phone;
	
	private String[] cbd = new String[3];


	private int reservationday;

	public Customer(String cname, String csurname, String cgender, String cbirthdate, String caddresstext,
			String cdistrict, String ccity, String cphone) {

		this.cbd = cbirthdate.split(Pattern.quote("."));

		Id += 1;

		this.name = cname;
		this.surname = csurname;
		this.gender = cgender;

		this.birthdate = new Date(Integer.valueOf(cbd[0]), Integer.valueOf(cbd[1]), Integer.valueOf(cbd[2]));

		this.address = new Address(caddresstext, cdistrict, ccity);

		this.phone = new Phone( Integer.valueOf(cphone.substring(1,3)) , Integer.valueOf(cphone.substring(3,6)) , Integer.valueOf(cphone.substring(6)));
		this.customerId = Id;
		reservationday = 0;
	}

	public String GetName() {
		return this.name;
	}

	public String GetSurName() {
		return this.surname;
	}

	public String GetGender() {
		return this.gender;
	}

	public Date GetBirthDate() {
		return this.birthdate;
	}

	public String GetAddressText() {
		return this.address.getAddresstext();
	}

	public String GetDistrict() {
		return this.address.getDistrict();
	}

	public String GetCity() {
		return this.address.getCity();
	}

	public Phone GetPhone() {
		return this.phone;
	}

	public String GetCustomerId() {
		return String.valueOf(customerId);
	}
	public void addReservationday(int days) {
		reservationday += days;
	}
	public int getReservationday() {
		return reservationday;
	}
}
