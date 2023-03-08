package entities;

import java.sql.Timestamp;
import java.util.Objects;

public class Facture {
    private int id;
    private Timestamp datefacture;
    private double tva;
    private String refrancefacture;
    private Commande commande;


    public Facture(int id, Timestamp datefacture, double tva, String refrancefacture, Commande commande) {
        this.id = id;
        this.datefacture = datefacture;
        this.tva = tva;
        this.refrancefacture = refrancefacture;
        this.commande = commande;
    }
    public Facture() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDatefacture() {
        return datefacture;
    }

    public void setDatefacture(Timestamp datefacture) {
        this.datefacture = datefacture;
    }

    public double getTva() {
        return tva;
    }

    public void setTva(double tva) {
        this.tva = tva;
    }

    public String getRefrancefacture() {
        return refrancefacture;
    }

    public void setRefrancefacture(String refrancefacture) {
        this.refrancefacture = refrancefacture;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Facture facture)) return false;
        return getId() == facture.getId() && Double.compare(facture.getTva(), getTva()) == 0 && Objects.equals(getDatefacture(), facture.getDatefacture()) && Objects.equals(getRefrancefacture(), facture.getRefrancefacture()) && Objects.equals(getCommande(), facture.getCommande());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDatefacture(), getTva(), getRefrancefacture(), getCommande());
    }

    @Override
    public String toString() {
        return "Facture{" +
                "id=" + id +
                ", datefacture=" + datefacture +
                ", tva=" + tva +
                ", refrancefacture='" + refrancefacture + '\'' +
                ", commande=" + commande +
                '}';
    }
}
