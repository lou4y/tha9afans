package entities;


/**
 *
 * @author ghazo
 */
public class CategorieEvenement {
    private int id ;
    private String nom ;

    public CategorieEvenement() {
    }

    public CategorieEvenement(String nom) {
        this.nom = nom;
    }

    public CategorieEvenement(int id, String nom) {
        this.id = id;
        this.nom = nom;
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

    @Override
    public String toString() {
        return "Categorie{" + "nom=" + nom + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.id;
        return hash;
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
        final CategorieEvenement other = (CategorieEvenement) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
