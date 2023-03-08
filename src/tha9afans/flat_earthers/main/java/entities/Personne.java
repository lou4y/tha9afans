package entities;


import javafx.scene.image.Image;

import java.io.InputStream;
import java.sql.Date;

public abstract class Personne {
    private int id;
    private String cin,nom,prenom,email,password,telephone,adresse,role;
    private Date dateNaissance;
    private InputStream photo;;
    public Personne(){}

    public Personne(int id, String cin, String nom, String prenom, String email, String password, String role, String telephone, String adresse, Date dateNaissance) {
        this.id = id;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.role = role;
        this.telephone = telephone;
        this.adresse = adresse;
        this.dateNaissance=dateNaissance;
    }
    public Personne(String cin, String nom, String prenom, String email, String password, String telephone, String adresse,InputStream photo,Date dateNaissance) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.adresse = adresse;
        this.photo=photo;
        this.dateNaissance=dateNaissance;
    }

    public Personne(int id, String cin, String nom, String prenom, String email, String password, String role,  String telephone, String adresse,InputStream photo, Date dateNaissance) {
        this.id = id;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.role = role;
        this.telephone = telephone;
        this.adresse = adresse;
        this.photo = photo;
        this.dateNaissance = dateNaissance;

    }

    public Personne(int id, String cin, String nom, String prenom, String email, String password, String telephone, String adresse, Date dateNaissance) {
        this.id = id;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
    }

    public Personne(String cin, String nom, String prenom, String email, String password, String role, String telephone, String adresse, Date dateNaissance) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.role=role;
        this.telephone=telephone;
        this.adresse=adresse;
        this.dateNaissance=dateNaissance;

    }

    public Personne(String cin, String nom, String prenom, String email, String password, String telephone, String adresse, Date dateNaissance) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }



    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public InputStream getPhoto() {
        return photo;
    }

    public void setPhoto(InputStream photo) {
        this.photo = photo;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    @Override
    public String toString() {
        return "Personne{" +
                "cin='" + cin + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", telephone='" + telephone + '\'' +
                ", adresse='" + adresse + '\'' +
                ", photo=" + photo +
                ", role=" + role.toString() +
                ", date de naissance="+dateNaissance+
                '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Personne other = (Personne) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}

