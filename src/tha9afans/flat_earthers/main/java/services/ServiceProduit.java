package services;

import entities.Evenement;
import entities.Produit;
import utils.DataSource;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceProduit implements IService <Produit> {
    Connection cnx = DataSource.getInstance().getCnx();
    ServiceCategorie sc = new ServiceCategorie();


    @Override
    public void ajouter(Produit p) {
        try {
            if(p.getRemise()!=null || p.getRemise()!=0){
                p.setPrixapresremise(p.getPrix()-p.getPrix()*p.getRemise()/100);
            }
            String req = "INSERT INTO `produit` ( `nom`, `description`, `libelle`, `id_vendeur`, `prix`,`image` ,`id_categorie`,`remise`,`rating`,`prixapresremise`) VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, p.getNom());
            ps.setString(2, p.getDescription());
            ps.setString(3, String.valueOf(p.getLibelle()));
            ps.setString(4, String.valueOf(p.getId_vendeur()));
            ps.setDouble(5, p.getPrix());
            ps.setBlob(6,p.getImage());
            ps.setInt(7, p.getCategorie().getId());
            ps.setDouble(8,p.getRemise());
            ps.setDouble(9,p.getRating());
            ps.setDouble(10,p.getPrixapresremise());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `produit` WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Product deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }


    }

    @Override
    public void modifier(Produit p) {
        try {
            if(p.getRemise()!=null || p.getRemise()!=0){
                p.setPrixapresremise(p.getPrix()-p.getPrix()*p.getRemise()/100);
            }
            String req = "UPDATE `produit` SET `nom` = '" + p.getNom() +
                    "', `description` = '" + p.getDescription() +"', `libelle` = '" + p.getLibelle() +"', `id_vendeur` = '" + p.getId_vendeur()  + "', `prix` = '" + p.getPrix() +
                    "', `id_categorie` = '" + p.getCategorie().getId() +"', `remise` = '" + p.getRemise()+"', `rating` = '" + p.getRating()
                    +"', `prixapresremise` = '" + p.getPrixapresremise()+ "'WHERE `produit`.`id` = " + p.getId();

            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Produit updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public List<Produit> getAll() { List<Produit> list = new ArrayList<>();
        try {
            String req = "Select * from produit";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Blob blob = rs.getBlob("image");
                InputStream inputStream = blob.getBinaryStream();
                Produit p = new Produit(rs.getInt(1),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getInt("libelle"),
                        rs.getInt("id_vendeur"),
                        rs.getDouble("prix"),
                        inputStream,
                        sc.getOneById(rs.getInt("id_categorie")),
                        rs.getDouble("remise"),
                        rs.getDouble("rating"),
                        rs.getDouble("prixapresremise")

                );
                list.add(p);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }



    @Override
    public Produit getOneById(int id) {
        Produit p = null;

        try {
            String req = "Select * from produit WHERE `id`= '" + id + "'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Blob blob = rs.getBlob("image");
                InputStream inputStream = blob.getBinaryStream();
                p = new Produit(rs.getInt(1), rs.getString("nom"), rs.getString("description")
                        , rs.getInt("libelle"),rs.getInt("id_vendeur"), rs.getDouble("prix"),inputStream,
                         sc.getOneById(rs.getInt("id_categorie")),
                         rs.getDouble("remise"),rs.getDouble("rating"),rs.getDouble("prixapresremise"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return p;
    }
    public List<Produit> getAllByUser(int id) { List<Produit> list = new ArrayList<>();
        try {
            String req = "Select * from produit WHERE `id_vendeur`= '" + id + "'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Blob blob = rs.getBlob("image");
                InputStream inputStream = blob.getBinaryStream();
                Produit p = new Produit(rs.getInt(1), rs.getString("nom"), rs.getString("description")
                        , rs.getInt("libelle"),rs.getInt("id_vendeur"), rs.getDouble("prix"),inputStream,
                        sc.getOneById(rs.getInt("id_categorie")),
                        rs.getDouble("remise"),rs.getDouble("rating"), rs.getDouble("prixapresremise"));
                list.add(p);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    public List<Produit> getAllByName(String nom, List<Produit> list) {
        return list.stream().filter(e -> e.getNom().contains(nom)).collect(Collectors.toList());
    }
}
