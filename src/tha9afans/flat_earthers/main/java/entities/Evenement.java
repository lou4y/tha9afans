package entities;
import java.sql.Date;
/**
 *
 * @author ghazo
 */
public class Evenement {
    private int id;
    private String nom;
    private String description;
    private CategorieEvenement categorieEvenement;
    private Date date ;

    private int id_createur;
    private String localisation;
    private int nb_participants;

    private int nb_aime;
    private int prix;
    public Evenement() {
    }

    public Evenement(String nom, String description, CategorieEvenement categorieEvenement, Date date, int id_createur, String localisation, int nb_participants, int nb_aime, int prix) {
        this.nom = nom;
        this.description = description;
        this.categorieEvenement = categorieEvenement;
        this.date = date;
        this.id_createur = id_createur;
        this.localisation = localisation;
        this.nb_participants = nb_participants;
        this.nb_aime = nb_aime;
        this.prix=prix;
    }

    public Evenement(int id, String nom, String description, CategorieEvenement categorieEvenement, Date date, int id_createur, String localisation, int nb_participants, int nb_aime, int prix) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.categorieEvenement = categorieEvenement;
        this.date = date;
        this.id_createur = id_createur;
        this.localisation = localisation;
        this.nb_participants = nb_participants;
        this.nb_aime = nb_aime;
        this.prix=prix;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
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

    public CategorieEvenement getCategorie() {return categorieEvenement;}

    public void setCategorie(CategorieEvenement categorieEvenement) {this.categorieEvenement = categorieEvenement;}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId_createur() {
        return id_createur;
    }

    public void setId_createur(int id_createur) {
        this.id_createur = id_createur;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public int getNb_participants() {
        return nb_participants;
    }

    public void setNb_participants(int nb_participants) {
        this.nb_participants = nb_participants;
    }

    public int getNb_aime() {
        return nb_aime;
    }

    public void setNb_aime(int nb_aime) {
        this.nb_aime = nb_aime;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", categorie=" + categorieEvenement +
                ", date=" + date +
                ", id_createur=" + id_createur +
                ", localisation='" + localisation + '\'' +
                ", nb_participants=" + nb_participants +
                ", nb_aime=" + nb_aime +
                ", prix=" + prix +
                '}';
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
        final Evenement other = (Evenement) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        return hash;
    }


}
