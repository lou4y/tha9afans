package entities;
import java.sql.Date;
import java.util.Objects;

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

    private Personne createur;
    private String localisation;
private String address;
    private boolean freeorpaid;

    private boolean online;
    private String link;

    public Evenement() {
    }

    public Evenement(String nom, String description, CategorieEvenement categorieEvenement, Date date, Personne createur, String localisation, String address, boolean freeorpaid, boolean online, String link) {
        this.nom = nom;
        this.description = description;
        this.categorieEvenement = categorieEvenement;
        this.date = date;
        this.createur = createur;
        this.localisation = localisation;
        this.address = address;
        this.freeorpaid = freeorpaid;
        this.online = online;
        this.link = link;
    }

    public Evenement(int id, String nom, String description, CategorieEvenement categorieEvenement, Date date, Personne createur, String localisation, String address, boolean freeorpaid, boolean online, String link) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.categorieEvenement = categorieEvenement;
        this.date = date;
        this.createur = createur;
        this.localisation = localisation;
        this.address = address;
        this.freeorpaid = freeorpaid;
        this.online = online;
        this.link = link;
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

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategorieEvenement getCategorieEvenement() {
        return categorieEvenement;
    }

    public void setCategorieEvenement(CategorieEvenement categorieEvenement) {
        this.categorieEvenement = categorieEvenement;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Personne getCreateur() {
        return createur;
    }

    public void setCreateur(Personne createur) {
        this.createur = createur;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isFreeorpaid() {
        return freeorpaid;
    }

    public void setFreeorpaid(boolean freeorpaid) {
        this.freeorpaid = freeorpaid;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evenement evenement = (Evenement) o;
        return id == evenement.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", categorieEvenement=" + categorieEvenement +
                ", date=" + date +
                ", createur=" + createur +
                ", localisation='" + localisation + '\'' +
                ", address='" + address + '\'' +
                ", freeorpaid=" + freeorpaid +
                ", online=" + online +
                ", link='" + link + '\'' +
                '}';
    }
}
