
public class Employee {
private static int id = 0;
private int employeeid;
private String name, surname, gender, birthdate, address,state,city,phone,job,salary;
public Employee(String name, String surname,String gender, String birthdate, String address,String state,String city,String phone, String job,String salary) {
	id++;
	this.name = name;
	this.surname = surname;
	this.gender = gender;
	this.birthdate = birthdate;
	this.address = address;
	this.state = state;
	this.city = city;
	this.phone = phone;
	this.job = job;
	this.salary = salary;
	employeeid = id;
}
public int getID() {
	return employeeid;
}
public String getName() {
	return name;
}
public String getSurname() {
	return surname;
}
public String getGender() {
	return gender;
}
public String getBirthdate() {
	return birthdate;
}
public String getJob() {
	return job;
}
public String getSalary() {
	return salary;
}
}
