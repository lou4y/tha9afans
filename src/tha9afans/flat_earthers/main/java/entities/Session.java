package entities;
    import java.sql.Time;
public class Session {
    private int id;
    private Evenement evenement;
    private String titre;
    private String description;
    private String parlant;
    private Time debit;
    private Time fin;

    public Session() {
    }

    public Session(Evenement evenement, String titre, String description, String parlant, Time debit, Time fin) {
        this.evenement = evenement;
        this.titre = titre;
        this.description = description;
        this.parlant = parlant;
        this.debit = debit;
        this.fin = fin;
    }

    public Session(int id, Evenement evenement, String titre, String description, String parlant, Time debit, Time fin) {
        this.id = id;
        this.evenement = evenement;
        this.titre = titre;
        this.description = description;
        this.parlant = parlant;
        this.debit = debit;
        this.fin = fin;
    }

    public int getId() {
        return id;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParlant() {
        return parlant;
    }

    public void setParlant(String parlant) {
        this.parlant = parlant;
    }

    public Time getDebit() {
        return debit;
    }

    public void setDebit(Time debit) {
        this.debit = debit;
    }

    public Time getFin() {
        return fin;
    }

    public void setFin(Time fin) {
        this.fin = fin;
    }

    @Override
    public String toString() {
        return "Session{" +
                "evenement=" + evenement +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", parlant='" + parlant + '\'' +
                ", debit=" + debit +
                ", fin=" + fin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return id == session.id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        return hash;
    }
}
