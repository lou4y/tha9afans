package services;

import entities.Commentaire;
import entities.Evenement;
import entities.Session;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceCommentaire implements IService<Commentaire> {
    Connection cnx = DataSource.getInstance().getCnx();
    ServiceEvenement se =new ServiceEvenement();
    ServicePersonne sp =new ServicePersonne();
    @Override
    public void ajouter(Commentaire commentaire) {
        try {
            String req = "INSERT INTO `commentaire`(`id_user`, `id_evenement`, `commentaire`, `date`) VALUES (?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, commentaire.getuser().getId());
            ps.setInt(2, commentaire.getEvenement().getId());
            ps.setString(3, commentaire.getCommentaire());
            ps.setDate(4, commentaire.getDate());
            ps.executeUpdate();
            System.out.println("Commentaire added !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Commentaire commentaire) {
        try {
            String req = "UPDATE `commentaire` SET `id_user`='" + commentaire.getuser().getId() + "',`id_evenement`='" + commentaire.getEvenement().getId() + "',`commentaire`='" + commentaire.getCommentaire() + "',`date`='" + commentaire.getDate() + "' WHERE `commentaire`.`id` = " + commentaire.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Commentaire updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `commentaire` WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("commentaire deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Commentaire> getAll() {
        List<Commentaire> list = new ArrayList<>();
        try {
            String req = "Select * from commentaire";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Commentaire commentaire = new Commentaire(rs.getInt(1),sp.getOneById(rs.getInt(2)),se.getOneById((rs.getInt(3))),rs.getString(4),rs.getDate(5));
                list.add(commentaire);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    @Override
    public Commentaire getOneById(int id) {
        Commentaire commentaire = null;
        try {
            String req = "Select * from commentaire where id = " + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                commentaire = new Commentaire(sp.getOneById(rs.getInt(1)),se.getOneById(rs.getInt(2)),rs.getString(3),rs.getDate(4));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return commentaire;
    }

    public List<Commentaire> getAllCommentsByEvent(Evenement evenement) {
        List<Commentaire> list = getAll();
        return list.stream().filter(e -> e.getEvenement().equals(evenement)).collect(Collectors.toList());
    }
}
