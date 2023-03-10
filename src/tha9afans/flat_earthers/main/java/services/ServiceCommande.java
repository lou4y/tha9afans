package services;

import entities.Commande;
import entities.Evenement;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCommande implements IService<Commande> {


    Connection cnx = DataSource.getInstance().getCnx();
    ServiceProduit spo = new ServiceProduit();
    ServicePersonne sp = new ServicePersonne();

    @Override
    public void ajouter(Commande p) {
        try{
            String req = "INSERT INTO `commande`(`dateCommande`, `total`, `id_user`) VALUES (?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setTimestamp(1, p.getDateCommande());
            ps.setDouble(2, p.getTotal());
            ps.setInt(3, p.getPersonne().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void supprimer(int id) {
        try{
            String req = "DELETE FROM commande WHERE id_commande= ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Commande deleted!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void modifier(Commande p) {
        try{
            String req = "UPDATE commande SET`dateCommande`=?,`total`=?,`id_user`=? WHERE  `id_commande`= ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setTimestamp(1, p.getDateCommande());
            ps.setDouble(2, p.getTotal());
            ps.setInt(3, p.getPersonne().getId());
            ps.setInt(4, p.getId());
            ps.executeUpdate();

            System.out.println("Commande Updated !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Commande> getAll() {
        List<Commande> list = new ArrayList<>();
        try {
            String req = "Select * from commande ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Commande e = new Commande(
                        rs.getInt(1),
                        Timestamp.valueOf(rs.getString(2)),
                        rs.getDouble(3),
                        sp.getOneById(rs.getInt(4)));
                list.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    @Override
    public Commande getOneById(int idp) {
        Commande e = null;
        try {
            String req = "Select * from commande where id = " + idp;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                e = new Commande(
                        rs.getInt(1),
                        Timestamp.valueOf(rs.getString(2)),
                        rs.getDouble(3),
                        sp.getOneById(rs.getInt(4)));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return e;
    }


    public int getId(Commande commande) {
        List<Commande> list = getAll();
        Commande c = list.stream()
                .filter(e -> /*e.getDateCommande().equals(commande.getDateCommande()) &&*/
                        e.getTotal().equals(commande.getTotal()) &&
                        e.getPersonne().equals(commande.getPersonne())
                        )
                .findFirst().orElse(null);
        System.out.println(c);
        return c != null ? c.getId() : -1;

    }



}