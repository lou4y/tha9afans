package services;

import entities.Galerie;
import utils.DataSource;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceGalerie  {
    Connection cnx = DataSource.getInstance().getCnx();


    public void ajouter(Galerie p) {
        try {
            String req = "INSERT INTO `Galerie` (`id_event`, `photo`) VALUES (?, ?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, p.getEvenement().getId());
            ps.setBlob(2, p.getPhoto());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }



    public void supprimer(Galerie p) {
        try {
            String req = "DELETE FROM `jaime` WHERE `id_event` = " + p.getEvenement() + " AND `photo` = " + p.getPhoto();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Galerie deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
    public List<Galerie> getAll(){
        ServiceEvenement se = new ServiceEvenement();
        List<Galerie> list = new ArrayList<>();
        try {
            String req = "SELECT * FROM `Galerie`";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Blob blob = rs.getBlob("photo");
                System.out.println(blob);
                InputStream inputStream = blob.getBinaryStream();
                Galerie g = new Galerie(se.getOneById(rs.getInt("id_event")), inputStream);

                list.add(g);
            }
        } catch (SQLException ex) {
            System.out.println("error"+ex.getMessage());
        }
        return list;
    }
    public List<Galerie> getallbyevent(int id){
        ServiceEvenement se = new ServiceEvenement();
        List<Galerie> list = new ArrayList<>();
        try {
            String req = "SELECT * FROM `Galerie` WHERE event_id = " + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Blob blob = rs.getBlob("photo");
                InputStream inputStream = blob.getBinaryStream();
                list.add(new Galerie(se.getOneById(rs.getInt("event_id")), inputStream) );
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }
}
