package entities;

import java.sql.Date;
import java.util.List;

public class Reservation {
    private int id;
    private Date date_reservation;
    private boolean ispPaid;
    private String payment_info;
    private Personne personne;
    private Billet billet;

    public Reservation(){}
    public Reservation(int id, Date date_reservation, boolean ispPaid, String payment_info, Personne personne, Billet billet) {
        this.id = id;
        this.date_reservation = date_reservation;
        this.ispPaid = ispPaid;
        this.payment_info = payment_info;
        this.personne = personne;
        this.billet = billet;
    }

    public Reservation(Date date_reservation, boolean ispPaid, String payment_info, Personne personne, Billet billet) {
        this.date_reservation = date_reservation;
        this.ispPaid = ispPaid;
        this.payment_info = payment_info;
        this.personne = personne;
        this.billet = billet;
    }

    public Reservation(int id, Date date_reservation) {
        this.id = id;
        this.date_reservation = date_reservation;
    }

    public int getId() {return id;}
    public Date getDate_reservation() {return date_reservation;}
    public void setDate_reservation(Date date_reservation) {this.date_reservation = date_reservation;}
    public boolean getIspPaid() {return ispPaid;}
    public void setIspPaid(boolean ispPaid) {this.ispPaid = ispPaid;}
    public String getPayment_info() {return payment_info;}
    public void setPayment_info(String payment_info) {this.payment_info = payment_info;}
    public Personne getUser() {return personne;}
    public void setUser(Personne user) {this.personne = user;}
    public Billet getBillet() {return billet;}
    public void setBillet(Billet billet) {this.billet = billet;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservation that = (Reservation) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", date_reservation=" + date_reservation +
                ", ispPaid=" + ispPaid +
                ", payment_info='" + payment_info + '\'' +
                ", user=" + personne +
                ", billetList=" + billet +
                '}';
    }
}
