package services;

import entities.*;
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


    public void ajouterproduitcommande(Produit produit, Personne personne) {
        try {

            PreparedStatement ps1 = cnx.prepareStatement("SELECT * FROM commande WHERE id_user = ?");
            ps1.setInt(1, personne.getId());
            ResultSet rs = ps1.executeQuery();

            Commande commande;
            if (rs.next()) {
                Timestamp date = rs.getTimestamp("date");
                commande = new Commande(date, 0.0, personne);
            } else {
                PreparedStatement ps2 = cnx.prepareStatement("INSERT INTO commande (id_user) VALUES (?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps2.setInt(1, personne.getId());
                ps2.executeUpdate();
                rs = ps2.getGeneratedKeys();
                rs.next();
                Timestamp date = rs.getTimestamp("date");
                commande = new Commande (date, 0.0, personne);
            }
            PreparedStatement ps3 = cnx
                    .prepareStatement("SELECT * FROM commandeproduit WHERE id_commende = ? AND id_produit  = ?");
            ps3.setInt(1, commande.getId());
            ps3.setInt(2, produit.getId());
            rs = ps3.executeQuery();

            if (rs.next()) {
                PreparedStatement ps4 = cnx.prepareStatement(
                        "UPDATE commandeproduit SET quantity = quantity + 1 WHERE id_commende = ? AND id_produit = ?");
                ps4.setInt(1, commande.getId());
                ps4.setInt(2, produit.getId());
                ps4.executeUpdate();
            } else {
                PreparedStatement ps5 = cnx.prepareStatement(
                        "INSERT INTO commandeproduit (id_commende,quantity,id_produit) VALUES (?, ?, ?)");
                ps5.setInt(1, commande.getId());
                ps5.setInt(2, 1);
                ps5.setInt(3, produit.getId());

                ps5.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    ServiceCommande scom = new ServiceCommande();

    public List<CommandeProduit> getprodutcommande(Commande commande) {
        List<CommandeProduit> list = new ArrayList<>();
        try {
            PreparedStatement ps = cnx.prepareStatement("SELECT * FROM commandeproduit WHERE id_commende = ?");
            ps.setInt(1, commande.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CommandeProduit p = new CommandeProduit(
                        scom.getOneById(rs.getInt(1)),
                        rs.getInt(2),
                        sp.getOneById(rs.getInt(3)));

                list.add(p);
            }
        } catch (SQLException ex) {

            System.out.println("Erreur  : " + ex.getMessage());
        }
        return list;

    }




}
