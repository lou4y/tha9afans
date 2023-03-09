package entities;

import java.sql.Date;

public class Billet {
    private int id;
    private String code;
    private Date date_validite;
    private double prix;
    private Evenement evenement;

    public  Billet(){

    }
    public Billet(int id, String code, Date date_validite, double prix, Evenement evenement) {
        this.id = id;
        this.code = code;
        this.date_validite = date_validite;
        this.prix = prix;
        this.evenement = evenement;
    }

    public Billet(String code, Date date_validite, double prix, Evenement evenement) {
        this.code = code;
        this.date_validite = date_validite;
        this.prix = prix;
        this.evenement = evenement;
    }


    public int getId() {return id;}
    public String getCode() {return code;}
    public void setCode(String code) {this.code = code;}
    public Date getDate_validite() {return date_validite;}
    public void setDate_validite(Date date_validite) {this.date_validite = date_validite;}
    public double getPrix() {return prix;}
    public void setPrix(double prix) {this.prix = prix;}
    public Evenement getEvenement() {return evenement;}
    public void setEvenement(Evenement evenement) {this.evenement = evenement;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Billet billet = (Billet) o;

        return id == billet.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Billet{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", date_validite=" + date_validite +
                ", prix=" + prix +
                ", evenement=" + evenement +
                '}';
    }
}
