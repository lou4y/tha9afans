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
            String req = "INSERT INTO `evenement`(`nom`, `description`, `id_categorie`, `date`, `id_createur`, `localisation`,`nb_participants`,`nb_aime`,`prix`) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, e.getNom());
            ps.setString(2, e.getDescription());
            ps.setInt(3, e.getCategorie().getId());
            ps.setDate(4, e.getDate());
            ps.setInt(5, e.getcreateur().getId());
            ps.setString(6, e.getLocalisation());
            ps.setInt(7, e.getNb_participants());
            ps.setInt(8, e.getNb_aime());
            ps.setInt(9, e.getPrix());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Evenement e) {
        try {
            String req = "UPDATE `evenement` SET `nom`='" + e.getNom() + "',`description`='" + e.getDescription() + "',`id_categorie`='" + e.getCategorie().getId() + "',`date`='" + e.getDate() + "',`id_createur`='" + e.getcreateur().getId() + "',`localisation`='" + e.getLocalisation() + "',`nb_participants`='" + e.getNb_participants() + "',`nb_aime`='" + e.getNb_aime() + "',`prix`='" + e.getPrix() + "' WHERE `Evenement`.`id` = " + e.getId();
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
                Evenement e = new Evenement(rs.getInt(1), rs.getString("nom"), rs.getString(3), sc.getOneById(rs.getInt(4)), rs.getDate(5), sp.getOneById(rs.getInt(6)), rs.getString(7), rs.getInt(8), rs.getInt(9),rs.getInt(10));
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
                e = new Evenement(rs.getInt(1), rs.getString("nom"), rs.getString(3), sc.getOneById(rs.getInt(4)), rs.getDate(5), sp.getOneById(rs.getInt(6)), rs.getString(7), rs.getInt(8), rs.getInt(9),rs.getInt(10));
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
        return list.stream().filter(e -> e.getcreateur().getId()==personne.getId()).collect(Collectors.toList());
    }
public int getId(Evenement evenement) {
    List<Evenement> list = getAll();
    Evenement ev = list.stream()
            .filter(e -> e.getNom().equals(evenement.getNom()) &&
                    e.getDate().equals(evenement.getDate()) &&
                    e.getCategorie().equals(evenement.getCategorie()) &&
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
                        e.getCategorie().equals(evenement.getCategorie()) &&
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
        return list.stream().filter(e -> e.getCategorie().getNom().contains(categorie)).collect(Collectors.toList());
    }

    public List<Evenement> tri(List<Evenement> list) {
        return   list.stream().sorted(Comparator.comparing(Evenement::getDate)).collect(Collectors.toList());
    }
    public List<Evenement> tri_inverse(List<Evenement> list) {
        return   list.stream().sorted(Comparator.comparing(Evenement::getDate, Comparator.reverseOrder())).collect(Collectors.toList());
    }
}


