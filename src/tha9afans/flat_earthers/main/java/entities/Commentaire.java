package entities;

import java.sql.Date;
import java.util.Objects;

public class Commentaire {
    private int id;
    private int id_user;
    private Evenement evenement;
    private String commentaire;

    private Date date;
    public  Commentaire(){}

    public Commentaire(int id_user, Evenement evenement, String commentaire, Date date) {
        this.id_user = id_user;
        this.evenement = evenement;
        this.commentaire = commentaire;
        this.date =date;
    }

    public Commentaire(int id, int id_user, Evenement evenement, String commentaire, Date date) {
        this.id = id;
        this.id_user = id_user;
        this.evenement = evenement;
        this.commentaire = commentaire;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "id_user=" + id_user +
                ", evenement=" + evenement +
                ", commentaire='" + commentaire + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commentaire that = (Commentaire) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        return hash;
    }
}
