package entities;

import java.sql.Timestamp;
import java.util.Objects;

public class Commande {
    private int id;

    private Timestamp dateCommande;

    private Produit produit;

    private Double total;

    private Personne personne;

    public Commande() {
    }

    public Commande(int id, Timestamp dateCommande, Produit produit, Double total, Personne personne) {
        this.id = id;
        this.dateCommande = dateCommande;
        this.produit = produit;
        this.total = total;
        this.personne = personne;
    }

    public Commande(Timestamp dateCommande, Produit produit, Double total, Personne personne) {
        this.dateCommande = dateCommande;
        this.produit = produit;
        this.total = total;
        this.personne = personne;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Timestamp dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Commande commande)) return false;
        return getId() == commande.getId() && Objects.equals(getDateCommande(), commande.getDateCommande()) && Objects.equals(getProduit(), commande.getProduit()) && Objects.equals(getTotal(), commande.getTotal()) && Objects.equals(getPersonne(), commande.getPersonne());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDateCommande(), getProduit(), getTotal(), getPersonne());
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", dateCommande=" + dateCommande +
                ", produit=" + produit +
                ", total=" + total +
                ", personne=" + personne +
                '}';
    }
}
