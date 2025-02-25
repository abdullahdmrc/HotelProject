public class Reservation {
	private int customerId;
	private int roomId;
	private DateRange dateRange;
	private Room room;
	private Date endDate;
	
	public Reservation(int customerId, int roomId, DateRange dateRange) {
		this.customerId = customerId;
		this.roomId = roomId;
		this.dateRange = dateRange;
	}
	
	public int getCustomerId() {
        return customerId;
    }

    public int getRoomId() {
        return roomId;
    }                                       

    public DateRange getDateRange() {
        return dateRange;
    }
    public Date getEndDate() {
        return endDate;
    }
    public Room getRoom() {
        return room;
    }
}
