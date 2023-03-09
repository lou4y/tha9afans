package entities;

import java.sql.Date;
import java.util.Objects;

public class Commentaire {
    private int id;
    private Personne user;
    private Evenement evenement;
    private String commentaire;

    private Date date;
    public  Commentaire(){}

    public Commentaire(Personne user, Evenement evenement, String commentaire, Date date) {
        this.user = user;
        this.evenement = evenement;
        this.commentaire = commentaire;
        this.date =date;
    }

    public Commentaire(int id, Personne user, Evenement evenement, String commentaire, Date date) {
        this.id = id;
        this.user = user;
        this.evenement = evenement;
        this.commentaire = commentaire;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public Personne getuser() {
        return user;
    }

    public void setuser(Personne user) {
        this.user = user;
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
                "user=" + user +
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
