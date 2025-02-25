import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

class Hotel {
	private Room[] rooms;
	private Reservation[] reservations;
	private Customer[] customers;
	private Employee[] employees;
	private int employeeid = 1;

	// constructor for when we create "new" object
	public Hotel() {
		rooms = new Room[0];
		reservations = new Reservation[0];
		customers = new Customer[1];
		employees = new Employee[50];
	}

	// adding Rooms
	public void addRoom(int number_of_rooms, String room_type, boolean aircondition, boolean balcony, double price) {
		for (int i = 0; i < number_of_rooms; i++) { // this line shows how many add roomtype like 3 deluxe
			int room_id = rooms.length + 1; // for new room's id , when we add new room , room's id automatically
											// assigned from 1 to ...
			Room room = new Room(room_id, room_type, aircondition, balcony, price);

			Room[] newRooms = new Room[rooms.length + 1];
			System.arraycopy(rooms, 0, newRooms, 0, rooms.length); // when we add new rooms in the array ,we need to we
																	// need to refresh the array

			newRooms[rooms.length] = room;
			rooms = newRooms;
		}
	}

	// listing rooms
	public void listRooms() {
		for (Room room : rooms) {
			System.out.println("Room #" + room.getRoom_id() + " " + room.getRoom_type() + " aircondition: "
					+ room.isAircondition() + " balcony: " + room.isBalcony() + " price: " + room.getPrice() + "TL");
		}
	}

	// Calculate the net profit
	public void Profit() {

		System.out.println("3.");
		double profit = 0;
		double income = 0;
		double total_income = 0;

		for (int i = 0; i < rooms.length; i++) {
			income = rooms[i].getPrice() * rooms[i].getReservationday();
			total_income += income;
		}
		double salaries = 0;
		double total_salary = 0;
		for (int i = 0; i < employees.length; i++) {
			if (employees[i] != null) {

				salaries = Double.parseDouble(employees[i].getSalary()) * 12;
				total_salary += salaries;
			}

		}
		double constant_expenses = 10000.00 * 12;
		profit = total_income - total_salary - constant_expenses;

		System.out.println("Income= " + total_income);
		System.out.println("Salary= " + total_salary);
		System.out.println("Constant Expenses= " + constant_expenses);
		System.out.println("Profit= " + total_income + "-" + total_salary + "-" + constant_expenses + " = " + profit);

	}

	// calculate the monthly occupancy
	public void calculateMonthlyOccupancyRate() {
		int[] daysInMonth = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		int[] monthlyOccupancy = new int[12];
		int totalReservations = 0;

		// Iterate through each reservation
		for (Reservation reservation : reservations) {
			DateRange dateRange = reservation.getDateRange();
			Date startDate = dateRange.getStartDate();
			Date endDate = dateRange.getEndDate();

			// Calculate the number of days between the start and end dates
			int days = DateRange.Days(endDate) - DateRange.Days(startDate);

			// Increment the reservation count for each month in the date range
			for (int month = startDate.getMonth() - 1; month < endDate.getMonth(); month++) {

				int daysInMonthForReservation = days;

				if (startDate.getMonth() != endDate.getMonth()) {
					// If the reservation doesn't cover the entire month, adjust the number of days
					// accordingly
					if (month == startDate.getMonth() - 1 && endDate.getMonth() > startDate.getMonth()) {
						daysInMonthForReservation = (daysInMonth[month] - startDate.getDay() + 1);
					} else if (month == endDate.getMonth() - 1) {
						daysInMonthForReservation = (endDate.getDay());
					} else {
						daysInMonthForReservation = daysInMonth[month];
					}
				}

				// Increment the monthly occupancy rate by the number of days the room was
				// reserved
				monthlyOccupancy[month] += daysInMonthForReservation;
				totalReservations++;
			}
		}

		// Calculate and print the monthly occupancy rates
		for (int month = 0; month < 12; month++) {
			
			double occupancyRate = ((double) monthlyOccupancy[month] / ((double) daysInMonth[month] * rooms.length)) * 100;
			BigDecimal bd = new BigDecimal(occupancyRate).setScale(0,RoundingMode.HALF_UP);
		      occupancyRate = bd.doubleValue();
			System.out.println(" Month " + (month + 1) + ": " + occupancyRate+ "%");
		}

		// Print the overall occupancy rate
		double overallOccupancyRate = (double) totalReservations / (double) (365 * rooms.length);
		System.out.println("Overall occupancy rate: " + overallOccupancyRate * 100 + "%");
		
	}

	public void searchRoom(String sd, String ed) {

		String[] arr = sd.split(Pattern.quote("."));

		Date startDate = new Date(Integer.valueOf(arr[0]), Integer.valueOf(arr[1]), Integer.valueOf(arr[2]));

		arr = ed.split(Pattern.quote("."));

		Date endDate = new Date(Integer.valueOf(arr[0]), Integer.valueOf(arr[1]), Integer.valueOf(arr[2]));

		int[] notIncluded = new int[rooms.length];
		int index = 0;

		for (Reservation reserved : reservations) {

			if (!(reserved.getDateRange().endDate.compare(startDate) < 1
					|| reserved.getDateRange().startDate.compare(endDate) > -1)) {
				boolean sameRoom = false;
				for (int i : notIncluded) {
					if (i == reserved.getRoomId()) {
						sameRoom = true;
					}
				}
				if (!sameRoom) {
					notIncluded[index] = reserved.getRoomId();
					index++;
				}
			}
		}
		boolean flag = false;
		for (int i = 0; i < rooms.length; i++) {

			for (int j = 0; j < notIncluded.length; j++) {

				if (rooms[i].getRoom_id() == notIncluded[j]) {
					flag = true;
					break;
				}

			}
			if (flag == false) {
				System.out.println(" Room #" + rooms[i].getRoom_id() + " " + rooms[i].getRoom_type() + " aircondition: "
						+ rooms[i].isAircondition() + " balcony: " + rooms[i].isBalcony() + " price: "
						+ rooms[i].getPrice() + "TL");
			}
			flag = false;

		}

	}

	// adds a reservation
	public void addReservation(int customerId, int roomId, Date startDate, Date endDate) {
		
		//	Checks start and end dates
		if(startDate.compare(endDate) == 1) {
			System.out.println("    Start date can not be bigger than end date.");
			return;
		}

		// Checks if given room exists
		boolean roomExists = false;

		for (Room room : rooms) {
			if (room.getRoom_id() == roomId) {
				roomExists = true;
			}
		}

		if (!roomExists) {
			System.out.printf("    The room with an id of %d does not exist.\n", roomId);
			return;
		}

		// Checks if given customer exists
		boolean customerExists = false;

		for (Customer customer : customers) {
			if (customer != null) {
				if (Integer.parseInt(customer.GetCustomerId()) == customerId) {
					customerExists = true;
				}
			}
		}

		if (!customerExists) {
			System.out.printf("    The customer with an id of %d does not exist.\n", customerId);
			return;
		}

		// Makes an array of reserved dates of the room
		DateRange[] reservedDates = new DateRange[0];

		for (Reservation reservation : reservations) {
			if (reservation.getRoomId() == roomId) {
				DateRange[] newDates = new DateRange[reservedDates.length + 1];
				System.arraycopy(reservedDates, 0, newDates, 0, reservedDates.length);

				newDates[reservedDates.length] = reservation.getDateRange();
				reservedDates = newDates;
			}
		}

		// Checks if the date range crosses one of the reservation dates
		for (DateRange dateRange : reservedDates) {
			if (!(endDate.compare(dateRange.startDate) < 1 || startDate.compare(dateRange.endDate) > -1)) {
				System.out.println("    The room is already reserved.");
				return;
			}
		}

		// Adds a reservation to the list of reservations
		Reservation reservation = new Reservation(customerId, roomId, new DateRange(startDate, endDate));

		Reservation[] newReservations = new Reservation[reservations.length + 1];
		System.arraycopy(reservations, 0, newReservations, 0, reservations.length);

		newReservations[reservations.length] = reservation;
		reservations = newReservations;
		// Adding reservation day to customer
		customers[customerId - 1].addReservationday(DateRange.Days(endDate) - DateRange.Days(startDate));
		// Adding reservation day to room
		rooms[roomId - 1].addReservationday(DateRange.Days(endDate) - DateRange.Days(startDate));
	}

	// lists reservations
	public void listReservations() {
		//System.out.println("Reservation list:");

		for (Reservation reservation : reservations) {

			String customerName = "";

			for (Customer customer : customers) {
				if (customer != null && Integer.parseInt(customer.GetCustomerId()) == reservation.getCustomerId()) {
					customerName = customer.GetName() + " " + customer.GetSurName();
				}
			}

			System.out.printf("    Room #%d   %-15s   %-10s  %-10s\n", reservation.getRoomId(), customerName,
					reservation.getDateRange().startDate.formatDate("."),
					reservation.getDateRange().endDate.formatDate("."));
		}
	}

	// Calculate best customer
	public void favCustomer() {
		Customer fav = new Customer("", "", "", "1.1.1980", "", "", "", "+901111111111");
		for (int i = 0; i < customers.length; i++) {
			if (customers[i] != null && fav.getReservationday() < customers[i].getReservationday()) {
				fav = customers[i];
			}
		}
		System.out.printf("\nThe best customer : " + fav.GetName() + " " + fav.GetSurName() + " "
				+ fav.getReservationday() + " days");
	}

	// Calculate the most reserved room
	public void favRoom() {
		Room fav = new Room(0, null, false, false, 0);
		for (int i = 0; i < rooms.length; i++) {
			if (rooms[i] != null && fav.getReservationday() < rooms[i].getReservationday()) {
				fav = rooms[i];
			}
		}
		System.out.print("\nThe most reserved room : Room #" + fav.getRoom_id() + "\n");
	}

	public void addCustomer(String name, String surname, String gender, String birthdate, String addresstext,
			String district, String city, String phone) {

		Customer[] allCustomers = new Customer[customers.length + 1];

		System.arraycopy(customers, 0, allCustomers, 0, customers.length);

		allCustomers[customers.length - 1] = new Customer(name, surname, gender, birthdate, addresstext, district, city,
				phone);

		customers = allCustomers;
		allCustomers = null;

	}

	public void listCustomers() {

		for (Customer customer : customers) {
			if (customer != null) {

				System.out.printf(" Customer #%s  %-7s %-7s %-7s  %02d.%02d.%-6d %-10s +%2d (%3d) %7d \n",
						customer.GetCustomerId(), customer.GetName(), customer.GetSurName(), customer.GetGender(),
						customer.GetBirthDate().day, customer.GetBirthDate().month, customer.GetBirthDate().year,
						customer.GetCity(), customer.GetPhone().countrycode, customer.GetPhone().citycode,
						customer.GetPhone().number);

			}

		}

	}

	public void searchCustomer(String pattern) {
		int patternIndex;

		if (pattern.contains("*")) {

			patternIndex = pattern.indexOf("*");
			pattern = pattern.substring(0, patternIndex);

			for (Customer customer : customers) {
				if (customer != null) {
					if (customer.GetName().contains(pattern)) {

						System.out.printf(" Customer #%s  %-7s %-7s %-7s  %02d.%02d.%-6d %-10s +%2d (%3d) %7d \n",
								customer.GetCustomerId(), customer.GetName(), customer.GetSurName(),
								customer.GetGender(), customer.GetBirthDate().day, customer.GetBirthDate().month,
								customer.GetBirthDate().year, customer.GetCity(), customer.GetPhone().countrycode,
								customer.GetPhone().citycode, customer.GetPhone().number);

					}

				}

			}

		} else if (pattern.contains("?")) {

			patternIndex = pattern.indexOf("?");
			int length = pattern.length();
			pattern = pattern.substring(0, patternIndex);

			for (Customer customer : customers) {
				if (customer != null) {
					if (customer.GetName().contains(pattern) && customer.GetName().length() == length) {

						System.out.printf(" Customer #%s  %-7s %-7s %-7s  %02d.%02d.%-6d %-10s +%2d (%3d) %7d \n",
								customer.GetCustomerId(), customer.GetName(), customer.GetSurName(),
								customer.GetGender(), customer.GetBirthDate().day, customer.GetBirthDate().month,
								customer.GetBirthDate().year, customer.GetCity(), customer.GetPhone().countrycode,
								customer.GetPhone().citycode, customer.GetPhone().number);

					}

				}

			}

		}

	}

	public void addEmployee(String name, String surname, String gender, String birthdate, String address, String state,
			String city, String phone, String job, String salary) {
		employees[employeeid-1] = new Employee(name, surname, gender, birthdate, address, state, city, phone, job, salary);
		employeeid++;
	}

	public void listEmployees() {
		System.out.println("Employee List");
		for (Employee employee : employees) {
			if (employee != null) {
				System.out.printf("Employee #%s  %-10s %-10s %-10s %-10s %s", employee.getID(), employee.getName(),
						employee.getSurname(), employee.getGender(), employee.getBirthdate(), employee.getJob());
				System.out.println();
			}
		}
	}

	public void deleteEmployee(int id) {
		employees[id-1] = null;
		System.out.print(id + " has been deleted.");
	}

	// Simulation
	public void simulation(Date startDate, Date endDate) {

		Date date = new Date(startDate.day, startDate.month, startDate.year);

		int[] customerCounts = new int[365];
		double[] satisfactions = new double[365];
		double averageSatisfaction = 0;

		int dateCounter = 0;
		
		int houseKeeperCount = 0;
		for(Employee employee : employees) {
			if(employee != null && employee.getJob().equalsIgnoreCase("housekeeper")) {
				houseKeeperCount++;
			}
		}

		System.out.print("\nDay          :     ");

		while (date.compare(endDate) < 1) {
			System.out.printf("%-7s", date.day);

			int customerCount = 0;

			for (Reservation reservation : reservations) {
				if (date.compare(reservation.getDateRange().startDate) > -1
						&& date.compare(reservation.getDateRange().endDate) == -1) {
					customerCount++;
				}
			}

			customerCounts[dateCounter] = customerCount;
			if (customerCount == 0) {
				satisfactions[dateCounter] = 100;
			} else {
				satisfactions[dateCounter] = (300 * houseKeeperCount) / customerCount;
			}

			if (satisfactions[dateCounter] > 100)
				satisfactions[dateCounter] = 100;

			averageSatisfaction += satisfactions[dateCounter];

			dateCounter++;
			date.increment();
		}

		averageSatisfaction /= dateCounter;

		System.out.print("\nCustomer     :     ");

		for (int i = 0; i < dateCounter; i++) {
			System.out.printf("%-7s", customerCounts[i]);
		}

		System.out.print("\nSatisfaction :     ");

		for (int i = 0; i < dateCounter; i++) {
			System.out.printf("%-7s", (int) satisfactions[i] + "%");
		}

		System.out.println("\nAverage Satisfaction = " + averageSatisfaction + "%");

	}
}
