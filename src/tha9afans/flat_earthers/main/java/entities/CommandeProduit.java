package entities;

import java.util.Objects;

public class CommandeProduit {

    private Commande commande;
    private int quantite ;
    private Produit produit;

    public CommandeProduit() {
    }

    public CommandeProduit(Commande commande, int quantite, Produit produit) {
        this.commande = commande;
        this.quantite = quantite;
        this.produit = produit;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandeProduit that)) return false;
        return getQuantite() == that.getQuantite() && Objects.equals(getCommande(), that.getCommande()) && Objects.equals(getProduit(), that.getProduit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommande(), getQuantite(), getProduit());
    }

    @Override
    public String toString() {
        return "commandeproduit{" +
                "commande=" + commande +
                ", quantite=" + quantite +
                ", produit=" + produit +
                '}';
    }
}
