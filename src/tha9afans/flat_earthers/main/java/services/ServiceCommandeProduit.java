package services;

import entities.CommandeProduit;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCommandeProduit  implements IService<CommandeProduit>{
    Connection cnx = DataSource.getInstance().getCnx();
    ServiceCommande sc = new ServiceCommande();
    ServiceProduit sp = new ServiceProduit();

    @Override
    public void ajouter(CommandeProduit p) {
        try {
            String req = "INSERT INTO commandeproduit( `id_commende`,`quantite`,`id_produit` ) VALUES (?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, p.getCommande().getId());
            ps.setInt(2, p.getQuantite());
            ps.setInt(3, p.getProduit().getId());
            ps.executeUpdate();
            System.out.println("Commande created ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }


    }

    @Override
    public void supprimer(int id) {
        try{
            String req = "DELETE FROM commandeproduit WHERE id_commandeproduit= ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Commande deleted!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void modifier(CommandeProduit p) {
        try{
            String req = "UPDATE commandeproduit SET `id_commende`=?,`quantite`=?,`id_produit`=? WHERE  `id_commandeproduit`= ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, p.getCommande().getId());
            ps.setInt(2, p.getQuantite());
            ps.setInt(3, p.getProduit().getId());
            ps.executeUpdate();

            System.out.println("Commande Updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public List<CommandeProduit> getAll() {

        List<CommandeProduit> list = new ArrayList<>();
        try {
            String req = "Select * from commandeproduit";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                CommandeProduit e = new CommandeProduit(
                        sc.getOneById(rs.getInt(1)),
                        rs.getInt(2),
                        sp.getOneById(rs.getInt(3))
                );
                list.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;

    }

    @Override
    public CommandeProduit getOneById(int id) {
        CommandeProduit result = null;
        try {
            String req = "SELECT * FROM commandeproduit WHERE id_=" + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                result = new CommandeProduit(
                        sc.getOneById(rs.getInt(1)),
                        rs.getInt(2),
                        sp.getOneById(rs.getInt(3)));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }
}
