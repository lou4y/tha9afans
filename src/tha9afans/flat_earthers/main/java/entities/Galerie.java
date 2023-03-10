package entities;

import java.io.InputStream;

public class Galerie {
   private Evenement evenement;
    private InputStream photo;;

    public Galerie(Evenement evenement) {
        this.evenement = evenement;
    }

    public Galerie(Evenement evenement, InputStream photo) {
        this.evenement = evenement;
        this.photo = photo;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public InputStream getPhoto() {
        return photo;
    }

    public void setPhoto(InputStream photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "galerie{" +
                "evenement=" + evenement +
                ", photo=" + photo +
                '}';
    }

}

