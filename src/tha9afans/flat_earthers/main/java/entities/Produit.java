package entities;

import java.io.InputStream;

public class Produit {
    private int id;
    private String nom,description;
    private int libelle,id_vendeur;
    private double  prix;
    private Categorie categorie;
    private InputStream image;
    private Double remise;

    private Double rating;
    private Double prixapresremise;



    public Produit() {
    }

    public Produit(int id, String nom, String description, int libelle, int id_vendeur, double prix, Categorie categorie,Double remise,Double rating) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.libelle = libelle;
        this.id_vendeur = id_vendeur;
        this.prix = prix;
        this.categorie=categorie;
        this.remise=remise;
        this.rating=rating;
    }

    public Produit(int id, String nom, String description, int libelle, int id_vendeur, double prix, InputStream image,Categorie categorie, Double remise, Double rating, Double prixapresremise) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.libelle = libelle;
        this.id_vendeur = id_vendeur;
        this.prix = prix;
        this.image = image;
        this.categorie = categorie;
        this.remise = remise;
        this.rating = rating;
        this.prixapresremise = prixapresremise;
    }

    public Produit(String nom, String description, int libelle, int id_vendeur, double prix, InputStream image, Categorie categorie, Double remise, Double rating) {
        this.nom = nom;
        this.description = description;
        this.libelle = libelle;
        this.id_vendeur = id_vendeur;
        this.prix = prix;
        this.categorie = categorie;
        this.image = image;
        this.remise = remise;
        this.rating = rating;
    }

    public Produit(int id, String nom, String description, int libelle, int id_vendeur, double prix, InputStream image, Categorie categorie, Double remise, Double rating) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.libelle = libelle;
        this.id_vendeur = id_vendeur;
        this.prix = prix;
        this.image = image;
        this.categorie = categorie;
        this.remise = remise;
        this.rating=rating;
    }
    public Produit(int id, String nom, String description, int libelle, int id_vendeur, double prix, Categorie categorie,InputStream image, Double remise) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.libelle = libelle;
        this.id_vendeur = id_vendeur;
        this.prix = prix;
        this.image = image;
        this.categorie = categorie;
        this.remise = remise;
    }

    public Produit(String nom, String description, int libelle, int id_vendeur, double prix, Categorie categorie) {
        this.nom = nom;
        this.description = description;
        this.libelle = libelle;
        this.id_vendeur = id_vendeur;
        this.prix = prix;
        this.categorie=categorie;
    }

    public Produit(int id, String nom, String description, int libelle, int id_vendeur, double prix, Categorie categorie, InputStream image) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.libelle = libelle;
        this.id_vendeur = id_vendeur;
        this.prix = prix;
        this.categorie = categorie;
        this.image = image;
    }

    public Produit(String nom, String description, int libelle, int id_vendeur, double prix, Categorie categorie, InputStream image) {
        this.nom = nom;
        this.description = description;
        this.libelle = libelle;
        this.id_vendeur = id_vendeur;
        this.prix = prix;
        this.categorie = categorie;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }

    public Double getRemise() {
        return remise;
    }

    public void setRemise(Double remise) {
        this.remise = remise;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLibelle() {
        return libelle;
    }

    public void setLibelle(int libelle) {
        this.libelle = libelle;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getId_vendeur() {
        return id_vendeur;
    }

    public void setId_vendeur(int id_vendeur) {
        this.id_vendeur = id_vendeur;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Double getRating(){ return rating;}

    public void setRating(Double rating){ this.rating= rating;}

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", libelle=" + libelle +
                ", id_vendeur=" + id_vendeur +
                ", prix=" + prix +
                ", categorie=" + categorie +
                '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    public Double getPrixapresremise() {
        return prixapresremise;
    }

    public void setPrixapresremise(Double prixapresremise) {
        this.prixapresremise = prixapresremise;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produit other = (Produit) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
