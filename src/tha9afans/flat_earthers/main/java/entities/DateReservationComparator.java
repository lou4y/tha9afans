package entities;

import java.util.Comparator;

public class DateReservationComparator implements Comparator<Reservation> {
    @Override
    public int compare(Reservation o1, Reservation o2) {
        return  - o1.getDate_reservation().compareTo(o2.getDate_reservation());
    }
}
