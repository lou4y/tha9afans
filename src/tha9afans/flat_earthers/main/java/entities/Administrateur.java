package entities;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Date;

public class Administrateur extends Personne {

    public Administrateur(String cin, String nom, String prenom, String email, String password, String role, String telephone, String adresse, Date dateNaissance) {
        super(cin, nom, prenom, email, password, role, telephone, adresse, dateNaissance);
    }

    public Administrateur(int id, String cin, String nom, String prenom, String email, String password, String telephone, String adresse, Date dateNaissance) {
        super(id, cin, nom, prenom, email, password, telephone, adresse, dateNaissance);
    }
    public Administrateur(int id, String cin, String nom, String prenom, String email, String password, String role, String telephone, String adresse, Date dateNaissance) {
        super(id, cin, nom, prenom, email, password,role, telephone, adresse, dateNaissance);
    }
    public Administrateur(int id, String cin, String nom, String prenom, String email, String password, String role, String telephone, String adresse, InputStream photo, Date dateNaissance) {
        super(id, cin, nom, prenom, email, password,role, telephone, adresse,photo, dateNaissance);
    }



}
