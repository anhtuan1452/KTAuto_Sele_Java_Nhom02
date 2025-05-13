package Railway.dataObjects;

public class Ticket {
    private String DepartStation;
    private String ArriveStation;
    private String Date;
    private String SeatType;
    private String TicketAmount;
    public Ticket(String Date, String DepartStation, String ArriveStation, String SeatType, String TicketAmount) {
        this.DepartStation = DepartStation;
        this.ArriveStation = ArriveStation;
        this.Date = Date;
        this.SeatType = SeatType;
        this.TicketAmount = TicketAmount;
    }
    public Ticket(String Date) {
        this.DepartStation = "Quãng Ngãi";
        this.ArriveStation = "Sài Gòn";
        this.Date = Date;
        this.SeatType = "Soft bed";
        this.TicketAmount = "1";
    }
    public String getDepartStation() {
        return DepartStation;
    }
    public void setDepartStation(String departStation) {
        DepartStation = departStation;
    }
    public String getArriveStation() {
        return ArriveStation;
    }
    public void setArriveStation(String arriveStation) {
        ArriveStation = arriveStation;
    }
    public String getDate() {
        return Date;
    }
    public void setDate(String date) {
        Date = date;
    }
    public String getSeatType() {
        return SeatType;
    }
    public void setSeatType(String seatType) {
        SeatType = seatType;
    }
    public String getTicketAmount() {
        return TicketAmount;
    }
    public void setTicketAmount(String ticketAmount) {
        TicketAmount = ticketAmount;
    }
}
