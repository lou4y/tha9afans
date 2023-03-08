package entities;

public class jaime {
    private int id_user;
    private Evenement evenement;

    public jaime() {
    }

    public jaime(int id_user, Evenement evenement) {
        this.id_user = id_user;
        this.evenement = evenement;
    }

    public int getId_user() {
        return id_user;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    @Override
    public String toString() {
        return "jaime{" +
                "id_user=" + id_user +
                ", evenement=" + evenement +
                '}';
    }
}
