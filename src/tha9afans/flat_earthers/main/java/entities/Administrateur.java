package entities;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Date;

public class Administrateur extends Personne {

    public Administrateur( String email, String role, String password,String cin, String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
        super(email, role, password,cin, nom, prenom, telephone, adresse, dateNaissance);
    }

    public Administrateur(int id, String email, String password, String cin, String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
        super(id, email, password, cin, nom, prenom, telephone, adresse, dateNaissance);
    }
    public Administrateur(int id, String email,String role, String password,  String cin, String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
        super(id, email,role, password, cin, nom, prenom, telephone, adresse, dateNaissance);
    }
    public Administrateur(int id, String email, String role, String password, String cin, String nom, String prenom, String telephone, String adresse, InputStream photo, Date dateNaissance) {
        super(id,email,role, password, cin, nom, prenom,  telephone, adresse,photo, dateNaissance);
    }



}
