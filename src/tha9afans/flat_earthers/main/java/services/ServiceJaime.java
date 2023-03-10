package services;


import entities.Jaime;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceJaime  {
    ServiceEvenement se = new ServiceEvenement();
    ServicePersonne sp= new ServicePersonne();
    Connection cnx = DataSource.getInstance().getCnx();


    public void ajouter(Jaime jaime) {
    try {
            String req = "INSERT INTO `jaime` (`id_user`, `id_event`) VALUES (?, ?)";
        PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, jaime.getUser().getId());
            ps.setInt(2, jaime.getEvenement().getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
    }

    }

    public void supprimer(Jaime jaime) {
        try {
            String req = "DELETE FROM `jaime` WHERE id_user = " + jaime.getUser().getId() + " AND id_event = " + jaime.getEvenement().getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("jaime deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
    public List<Jaime> getAll(){
        List<Jaime> list = new ArrayList<>();
        try {
            String req = "SELECT * FROM `jaime`";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                list.add(new Jaime(sp.getOneById(rs.getInt("id_user")), se.getOneById(rs.getInt("id_event"))) );
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }
    public boolean existe(Jaime jaime) {
        List<Jaime> list = getAll();
        Jaime ev = list.stream()
                .filter(e -> e.getUser().getId() == jaime.getUser().getId() && e.getEvenement().getId() == jaime.getEvenement().getId())
                .findFirst()
                .orElse(null);
        return ev != null ? true : false;
    }
}
