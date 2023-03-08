package services;

import  entities.Administrateur;
import entities.Personne;
import entities.Utilisateur;
import utils.DataSource;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.Date;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ServicePersonne implements IService<Personne>{
    Connection cnx = DataSource.getInstance().getCnx();


    @Override
    public void ajouter(Personne p) {
        String role="";
        if(p instanceof Utilisateur){
            role="utilisateur";
        }
        else if (p instanceof Administrateur){
            role="administrateur";
        }
        try {
            String req = "INSERT INTO `personnes` (`cin`,`nom`, `prenom`,`email`,`password`,`role`,`telephone`,`adresse`,`photo`,`dateNaissance`) " +
                    "VALUES (?, ?, ?, ?, ?, role, ?, ?, ?, ?)";
            PreparedStatement pst = cnx.prepareStatement(req);

            // Set the values of the query parameters
            pst.setString(1, p.getCin());
            pst.setString(2, p.getNom());
            pst.setString(3, p.getPrenom());
            pst.setString(4, p.getEmail());
            pst.setString(5, p.getPassword());
            //pst.setString(6, role);
            pst.setString(6, p.getTelephone());
            pst.setString(7, p.getAdresse());
            pst.setBlob(8, p.getPhoto());
            pst.setDate(9, p.getDateNaissance());


            // Execute the update query
            pst.executeUpdate();
            System.out.println("Personne created !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `personnes` WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Personne deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void modifier(Personne p) {
            try {
                // Prepare the update query
                String req = "UPDATE `personnes` SET `cin` = ?, `nom` = ?, `prenom` = ?, `email` = ?, `password` = ?, `role` = ?, `telephone` = ?, `adresse` = ?, `photo` = ?, `dateNaissance` = ? WHERE `personnes`.`id` = ?";
                PreparedStatement pst = cnx.prepareStatement(req);

                // Set the values of the query parameters
                pst.setString(1, p.getCin());
                pst.setString(2, p.getNom());
                pst.setString(3, p.getPrenom());
                pst.setString(4, p.getEmail());
                pst.setString(5, p.getPassword());
                pst.setString(6, p.getRole());
                pst.setString(7, p.getTelephone());
                pst.setString(8, p.getAdresse());
                pst.setBlob(9, p.getPhoto());
                pst.setDate(10, p.getDateNaissance());
                pst.setInt(11, p.getId());

                // Execute the update query
                pst.executeUpdate();

                System.out.println("Personne updated !");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }


    }


    @Override
    public List<Personne> getAll() {
        Personne p=null;
        List<Personne> list = new ArrayList<>();
        try {
            String req = "Select * from personnes";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                if (rs.getString("role").equals("utilisateur")){
                    p=new Utilisateur(rs.getInt(1),rs.getString(2),rs.getString(3),
                            rs.getString(4),rs.getString(5),rs.getString(6), rs.getString(7),
                            rs.getString(8),rs.getString(9), rs.getDate(11));
                }
                else if(rs.getString("role").equals("administrateur")){
                    p =new Administrateur(rs.getInt(1),rs.getString(2),rs.getString(3),
                            rs.getString(4),rs.getString(5),rs.getString(6), rs.getString(7),
                            rs.getString(8),rs.getString(9),rs.getDate(11));

                }
                list.add(p);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    @Override
    public Personne getOneById(int idu) {
        Personne p = null;
        try {
            String req = "Select * from personnes WHERE `id`= '" + idu + "'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Blob blob = rs.getBlob(10);
                InputStream inputStream = blob.getBinaryStream();
                if(rs.getString("role").equals("utilisateur")){
                    p= new Utilisateur(rs.getInt(1),rs.getString(2),rs.getString(3),
                            rs.getString(4),rs.getString(5),rs.getString(6), rs.getString(7),
                            rs.getString(8),rs.getString(9),inputStream,rs.getDate(11));
                }
                else if(rs.getString("role").equals("administrateur")){
                    p=new Administrateur(rs.getInt("id"),rs.getString("cin"),rs.getString("nom"),
                            rs.getString("prenom"),rs.getString("email"),rs.getString("password"), rs.getString("role"),
                            rs.getString("telephone"),rs.getString("adresse"),inputStream,rs.getDate(11));

                }
            }


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return p;
    }
    public List<Personne> getPersonByNomPrenom(String name) {
        Personne p = null;
        List<Personne> list = new ArrayList<>();
        try {
            String req = "Select * from personnes WHERE `nom`= '" + name + "' OR  `prenom`='"+ name+ "'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                if(rs.getString("role").equals("utilisateur")){
                    p= new Utilisateur(rs.getInt(1),rs.getString(2),rs.getString(3),
                            rs.getString(4),rs.getString(5),rs.getString(6), rs.getString(7),
                            rs.getString(8),rs.getString(9),Date.valueOf(rs.getString(10)));
                }
                else if(rs.getString("role").equals("administrateur")){
                    p=new Administrateur(rs.getInt(1),rs.getString(2),rs.getString(3),
                            rs.getString(4),rs.getString(5),rs.getString(6), rs.getString(7),
                            rs.getString(8),rs.getString(9),Date.valueOf(rs.getString(10)));

                }
                list.add(p);
            }


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }
    public List<Personne> getPersonByAdresse(String adresse) {
        Personne p = null;
        List<Personne> list = new ArrayList<>();
        try {
            String req = "Select * from personnes WHERE `adresse`= '" + adresse + "' ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                if(rs.getString("role").equals("utilisateur")){
                    p= new Utilisateur(rs.getInt(1),rs.getString(2),rs.getString(3),
                            rs.getString(4),rs.getString(5),rs.getString(6), rs.getString(7),
                            rs.getString(8),rs.getString(9),Date.valueOf(rs.getString(10)));
                }
                else if(rs.getString("role").equals("administrateur")){
                    p=new Administrateur(rs.getInt(1),rs.getString(2),rs.getString(3),
                            rs.getString(4),rs.getString(5),rs.getString(6), rs.getString(7),
                            rs.getString(8),rs.getString(9),Date.valueOf(rs.getString(10)));

                }
                list.add(p);
            }


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }
    public Map<String, Integer> getUserStatsByRegion() throws SQLException {
        Map<String, Integer> stats = new HashMap<>();
        String req = "SELECT adresse, COUNT(*) FROM personnes GROUP BY adresse";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            String region = rs.getString("adresse");
            int count = rs.getInt("COUNT(*)");
            stats.put(region, count);
        }
        return stats;
    }
    public Map<String, Integer> getEventData() throws SQLException {
        Map<String, Integer> data = new HashMap<>();
        String query = "SELECT nom, nb_participants FROM evenement";
        Statement stmt = cnx.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            String eventName = rs.getString("nom");
            int participantCount = rs.getInt("nb_participants");
            data.put(eventName, participantCount);
        }
        return data;
    }
}
