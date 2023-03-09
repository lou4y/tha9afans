package services;

import entities.Evenement;
import entities.Facture;
import entities.Personne;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceFacture implements IService<Facture> {
    Connection cnx = DataSource.getInstance().getCnx();
    ServiceCommande sc = new ServiceCommande();
    RandomRef randomRef = new RandomRef();

    @Override
    public void ajouter(Facture p) {
        try{
            String randomRef = RandomRef.randomString(8);
            p.setRefrancefacture(randomRef);
            String req = "INSERT INTO `facture` ( `datefacture`,`tva`,`refrancefacture`, `id_commende`) VALUES ('" + p.getDatefacture()+"', '"+p.getTva()  +"', '" + randomRef +"', '"+ p.getCommande().getId()+ "')";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Facture ajoutée");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void supprimer(int id) {
        try{
            String req = "DELETE FROM facture WHERE id_facture= ?";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Facture supprimée");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void modifier(Facture p) {

    }

    @Override
    public List<Facture> getAll() {
        List<Facture> list = new ArrayList<>();
        try {
            String req = "Select * from facture";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Facture e = new Facture(
                        rs.getInt(1),
                        Timestamp.valueOf(rs.getString(2)),
                        rs.getDouble(3),
                        rs.getString(4),
                        sc.getOneById(rs.getInt(5))

                );
                list.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;

    }

    @Override
    public Facture getOneById(int id) {
        Facture e = null;
        try {
            String req = "Select * from facture where id = " + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                e = new Facture(
                        rs.getInt(1),
                        Timestamp.valueOf(rs.getString(2)),
                        rs.getDouble(3),
                        rs.getString(4),
                        sc.getOneById(rs.getInt(5))

                );
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return e;
    }
    public List<Facture> getAllByUser(Personne personne) {
        List<Facture> list = new ArrayList<>();
        return list.stream().filter(e -> e.getCommande().getPersonne().getId() == personne.getId()).collect(Collectors.toList());
    }
}