package entities;

public class Jaime {
    private int id;
    private Personne user;
    private Evenement evenement;

    public Jaime() {
    }

    public Jaime(Personne user, Evenement evenement) {
        this.user = user;
        this.evenement = evenement;
    }

    public Jaime(int id, Personne user, Evenement evenement) {
        this.id = id;
        this.user = user;
        this.evenement = evenement;
    }

    public Personne getUser() {
        return user;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    @Override
    public String toString() {
        return "jaime{" +
                "user=" + user +
                ", evenement=" + evenement +
                '}';
    }
}
