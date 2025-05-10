package Railway.dataObjects.Enum;

public class Enum {
    public enum TableSuccessfulBooking {
        DEPART_STATION("Depart Station", 1),
        ARRIVE_STATION("Arrive Station", 2),
        SEAT_TYPE("Seat Type", 3),
        DEPART_DATE("Depart Date",4),
        BOOK_DATE("Book Date",5),
        EXPIRED_DATE("Expired Date", 6),
        AMOUNT("Amount", 7),
        TOTAL_PRICE("Total Price",8);

        public final String label;
        public final int columnIndex;

        TableSuccessfulBooking(String label, int columnIndex) {
            this.label = label;
            this.columnIndex = columnIndex;
        }

        public int getColumnIndex() {
            return columnIndex;
        }

        public static TableSuccessfulBooking fromLabel(String label) {
            for (TableSuccessfulBooking f : values()) {
                if (f.label.equalsIgnoreCase(label)) {
                    return f;
                }
            }
            throw new IllegalArgumentException("No TicketField with label: " + label);
        }
    }
    public enum BookedTicketColumn {
        NO("No.", 1),
        DEPART_STATION("Depart Station", 2),
        ARRIVE_STATION("Arrive Station", 3),
        SEAT_TYPE("Seat Type", 4),
        DEPART_DATE("Depart Date", 5),
        BOOK_DATE("Book Date", 6),
        EXPIRED_DATE("Expired Date", 7),
        STATUS("Status", 8),
        AMOUNT("Amount", 9),
        TOTAL_PRICE("Total Price", 10),
        OPERATION("Operation", 11);

        private final String label;
        private final int columnIndex;

        BookedTicketColumn(String label, int columnIndex) {
            this.label = label;
            this.columnIndex = columnIndex;
        }

        public int getColumnIndex() {
            return columnIndex;
        }

        public String getLabel() {
            return label;
        }

        public static BookedTicketColumn fromLabel(String label) {
            for (BookedTicketColumn f : values()) {
                if (f.label.equalsIgnoreCase(label)) {
                    return f;
                }
            }
            throw new IllegalArgumentException("No TicketField with label: " + label);
        }
    }



}
