package entities;

import java.util.Objects;

public class PanierProduit {
    private  Panier panier; ;
    private  Produit produit;
    private int quantite;




    public PanierProduit() {
    }

    public PanierProduit(Panier panier, Produit produit, int quantite) {
        this.panier = panier;
        this.produit = produit;
        this.quantite = quantite;

    }

    public Panier getPanier() {
        return panier;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PanierProduit that)) return false;
        return getQuantite() == that.getQuantite() && Objects.equals(getPanier(), that.getPanier()) && Objects.equals(getProduit(), that.getProduit() );
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPanier(), getProduit(), getQuantite());
    }

    @Override
    public String toString() {
        return "PanierProduit{" +
                "panier=" + panier +
                ", produit=" + produit +
                ", quantite=" + quantite +

                '}';
    }
}
