class Room {
    private int room_id;
    private String room_type;
    private boolean aircondition;          // our room attributes 
    private boolean balcony;
    private double price;
    private int reservationday;

    public Room(int room_id, String room_type, boolean aircondition, boolean balcony, double price) {
        this.room_id = room_id;
        this.room_type = room_type;
        this.aircondition = aircondition;      // constructor
        this.balcony = balcony;
        this.price = price;
        reservationday = 0;
    }

    public int getRoom_id() {
        return room_id;
    }

    public String getRoom_type() {
        return room_type;
    }                                       // getter and setters

    public boolean isAircondition() {
        return aircondition;
    }

    public boolean isBalcony() {
        return balcony;
    }

    public double getPrice() {
        return price;
    }
    public void addReservationday(int day) {
    	reservationday += day;
    }
    public int getReservationday() {
    	return reservationday;
    }
}
