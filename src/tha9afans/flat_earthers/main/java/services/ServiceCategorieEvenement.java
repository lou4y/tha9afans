package services;


import entities.CategorieEvenement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.DataSource;
/**
 *
 * @author ghazo
 */
public class ServiceCategorieEvenement implements IService<CategorieEvenement> {
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(CategorieEvenement c) {
        try {
            String req = "INSERT INTO `categorie` (`nom`) VALUES (?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, c.getNom());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(CategorieEvenement c) {
        try {
            String req = "UPDATE `categorie_evenement` SET `nom` = '" + c.getNom() + "' WHERE `id` = " + c.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Categorie updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `categorie_evenement` WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Categorie deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<CategorieEvenement> getAll() {
        List<CategorieEvenement> list = new ArrayList<>();
        try {
            String req = "Select * from categorie_evenement";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                CategorieEvenement c = new CategorieEvenement(rs.getInt(1), rs.getString("nom"));
                list.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    @Override
    public CategorieEvenement getOneById(int id) {
        CategorieEvenement c = null;
        try {
            String req = "Select * from categorie_evenement where id = " + id;;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                c = new CategorieEvenement(rs.getInt(1), rs.getString("nom"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return c;
    }

}
