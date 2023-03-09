package services;

import entities.Evenement;
import entities.Panier;
import entities.Personne;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServicePanier implements IService<Panier> {


    Connection cnx = DataSource.getInstance().getCnx();
    ServicePersonne sp = new ServicePersonne();



    @Override
    public void ajouter(Panier e) {

        try {
            String req = "INSERT INTO `panier`(`total`, `id_user`) VALUES (?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setDouble(1, e.getTotal());
            ps.setInt(2, e.getPersonne().getId());

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM panier WHERE id_panier= ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Panier deleted!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void modifier(Panier p) {
        try {
            String req = "UPDATE panier SET`totalPrix`=? WHERE  `id_panier`= ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setDouble(1, p.getTotal());
            ps.executeUpdate();

            System.out.println("panier Updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public List<Panier> getAll() {

        List<Panier> list = new ArrayList<>();
        try {
            String req = "Select * from panier";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Panier e = new Panier(

                        rs.getInt(1),
                        rs.getDouble(2),
                        sp.getOneById(rs.getInt(3)));
                list.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;

    }

    @Override
    public Panier getOneById(int id) {
        Panier e = null;
        try {
            String req = "Select * from panier where id = " + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                e = new Panier(
                        rs.getInt(1),
                        rs.getDouble(2),
                        sp.getOneById(rs.getInt(3)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return e;
    }




    public void updatetotal(Panier panier , Double total){
        try {
            String req = "UPDATE panier SET`total`=? WHERE  `id`= ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setDouble(1, total);
            ps.setInt(2, panier.getId());
            ps.executeUpdate();

            System.out.println("panier Updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public void updateCartTotal(Panier panier ,double total) {
        try {
            String req = "UPDATE panier SET total = ? WHERE id = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setDouble(1, total);
            ps.setInt(2, panier.getId()); // replace cartId with the id of the cart you want to update
            ps.executeUpdate();
            cnx.close();
        } catch (SQLException e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }
    public Panier GetPanierByUser(Personne personne){
            List<Panier> list = getAll();
            list= list.stream().filter(e -> e.getPersonne().getId()==personne.getId()).collect(Collectors.toList());
        System.out.println(list);
        return list.get(0);
    }
    public boolean panierexiste(Personne personne){
        List<Panier> list = getAll();
        list= list.stream().filter(e -> e.getPersonne().getId()==personne.getId()).collect(Collectors.toList());
        System.out.println(list);
        return list.size()>0?true:false;
    }


}