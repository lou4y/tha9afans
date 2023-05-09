package services;
import entities.Evenement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;


import entities.Personne;
import utils.DataSource;
/**
 *
 * @author ghazo
 */
public class ServiceEvenement implements IService<Evenement> {
    Connection cnx = DataSource.getInstance().getCnx();
    ServicePersonne sp=new ServicePersonne();
    ServiceCategorieEvenement sc = new ServiceCategorieEvenement();

    @Override
    public void ajouter(Evenement e) {
        try {
            String req = "INSERT INTO `evenement`(`createur_id`, `categorie_id`, `nom`, `description`, `date`, `localisation`, `freeorpaid`, `online`, `link`, `addresse`) VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, e.getCreateur().getId());
            ps.setInt(2, e.getCategorieEvenement().getId());
            ps.setString(3, e.getNom());
            ps.setString(4, e.getDescription());
            ps.setDate(5, e.getDate());
            ps.setString(6, e.getLocalisation());
            ps.setBoolean(7, e.isFreeorpaid());
            ps.setBoolean(8, e.isOnline());
            ps.setString(9, e.getLink());
            ps.setString(10,e.getAddress());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Evenement e) {
        try {
            String req = "UPDATE `evenement` SET `createur_id`='" + e.getCreateur().getId()  + "',`categorie_id`='" + e.getCategorieEvenement().getId() +"',`nom`='" + e.getNom() +"',`description`='" + e.getDescription() + "',`date`='" + e.getDate() + "',`localisation`='" + e.getLocalisation() + "',`freeorpaid`='" + e.isFreeorpaid() + "',`online`='" + e.isOnline() + "',`link`='" + e.getLink() +"',`addresse`='" + e.getAddress() + "' WHERE `Evenement`.`id` = " + e.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("evenement updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `evenement` WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("evenement deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Evenement> getAll() {

        List<Evenement> list = new ArrayList<>();
        try {
            String req = "Select * from evenement";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Evenement e = new Evenement(rs.getInt(1), rs.getString("nom"), rs.getString("description"), sc.getOneById(rs.getInt("categorie_id")), rs.getDate("date"), sp.getOneById(rs.getInt("createur_id")), rs.getString("localisation"), rs.getString("addresse"), rs.getBoolean("freeorpaid"),rs.getBoolean("online"),rs.getString("link"));
                list.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    @Override
    public Evenement getOneById(int id) {
        Evenement e = null;
        try {
            String req = "Select * from evenement where id = " + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                 e = new Evenement(rs.getInt(1), rs.getString("nom"), rs.getString("description"), sc.getOneById(rs.getInt("categorie_id")), rs.getDate("date"), sp.getOneById(rs.getInt("createur_id")), rs.getString("localisation"), rs.getString("addresse"), rs.getBoolean("freeorpaid"),rs.getBoolean("online"),rs.getString("link"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return e;
    }

///////////////////////////////////////////////////
    public List<Evenement> getAllByName(String nom,List<Evenement> list) {
        return list.stream().filter(e -> e.getNom().contains(nom)).collect(Collectors.toList());
}
    public List<Evenement> getAllByUser(Personne personne, List<Evenement> list) {
        return list.stream().filter(e -> e.getCreateur().getId()==personne.getId()).collect(Collectors.toList());
    }
public int getId(Evenement evenement) {
    List<Evenement> list = getAll();
    Evenement ev = list.stream()
            .filter(e -> e.getNom().equals(evenement.getNom()) &&
                    e.getDate().equals(evenement.getDate()) &&
                    e.getCategorieEvenement().equals(evenement.getCategorieEvenement()) &&
                    e.getDescription().equals(evenement.getDescription()) &&
                    e.getLocalisation().equals(evenement.getLocalisation()))
            .findFirst()
            .orElse(null);
    return ev != null ? ev.getId() : -1;
}
    public int existe(Evenement evenement) {
        List<Evenement> list = getAll();
        Evenement ev = list.stream()
                .filter(e -> e.getNom().equals(evenement.getNom()) &&
                        e.getDate().equals(evenement.getDate()) &&
                        e.getCategorieEvenement().equals(evenement.getCategorieEvenement()) &&
                        e.getDescription().equals(evenement.getDescription()) &&
                        e.getLocalisation().equals(evenement.getLocalisation()))
                .findFirst()
                .orElse(null);
        return ev != null ? 1 : -1;
    }

    public List<Evenement> getAllByRegion(String localisation,List<Evenement> list) {
        return list.stream().filter(e -> e.getLocalisation().contains(localisation)).collect(Collectors.toList());
    }
    public List<Evenement> getAllByCategorie(String categorie,List<Evenement> list) {
        return list.stream().filter(e -> e.getCategorieEvenement().getNom().contains(categorie)).collect(Collectors.toList());
    }

    public List<Evenement> tri(List<Evenement> list) {
        return   list.stream().sorted(Comparator.comparing(Evenement::getDate)).collect(Collectors.toList());
    }
    public List<Evenement> tri_inverse(List<Evenement> list) {
        return   list.stream().sorted(Comparator.comparing(Evenement::getDate, Comparator.reverseOrder())).collect(Collectors.toList());
    }
}


