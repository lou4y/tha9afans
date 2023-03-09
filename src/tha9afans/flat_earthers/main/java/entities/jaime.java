package entities;

public class jaime {
    private Personne user;
    private Evenement evenement;

    public jaime() {
    }

    public jaime(Personne user, Evenement evenement) {
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
