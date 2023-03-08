package entities;

import java.util.Objects;

public class Panier {



    private int id;

    private double total;
    private Personne personne;





    public Panier() {
    }

    public Panier(int id, double total,Personne personne) {
        this.id = id;

        this.total = total;

        this.personne = personne;

    }


    public Panier( double total, Personne personne) {
        this.total = total;

        this.personne = personne;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }





    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Panier panier)) return false;
        return getId() == panier.getId() && Double.compare(panier.getTotal(), getTotal()) == 0   && Objects.equals(getPersonne(), panier.getPersonne());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),  getTotal(), getPersonne());
    }

    @Override
    public String toString() {
        return "Panier{" +
                "id=" + id +
                ", total=" + total +
                ", personne=" + personne +
                '}';
    }
}
