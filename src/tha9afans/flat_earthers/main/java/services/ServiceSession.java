package services;
import entities.Evenement;
import entities.Session;
import utils.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceSession implements IService<Session>{
    Connection cnx = DataSource.getInstance().getCnx();
    ServiceEvenement se = new ServiceEvenement();
    @Override
    public void ajouter(Session session) {
        try {
            String req = "INSERT INTO `session` (`id_evenement`,`titre`,`description`,`parlant`,`debit`,`fin`) VALUES (?,?,?, ?, ?, ?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, session.getEvenement().getId());
            ps.setString(2, session.getTitre());
            ps.setString(3, session.getDescription());
            ps.setString(4, session.getParlant());
            ps.setTime(5, session.getDebit());
            ps.setTime(6, session.getFin());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Session session) {
        try {
            String req = "UPDATE `session` SET `id_evenement`='" + session.getEvenement().getId() + "',`titre`='" + session.getTitre() + "',`description`='" + session.getDescription() + "',`parlant`='" + session.getParlant() + "',`debit`='" + session.getDebit() + "',`fin`='" + session.getFin() + "' WHERE `Session`.`id` = " + session.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Session updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `session` WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Session deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public List<Session> getAll() {
        List<Session> list = new ArrayList<>();
        try {
            String req = "Select * from session";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                list.add(new Session(rs.getInt("id"), se.getOneById(rs.getInt("id_evenement")), rs.getString("titre"), rs.getString("description"), rs.getString("parlant"), rs.getTime("debit"), rs.getTime("fin")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    @Override
    public Session getOneById(int id) {
        Session s = null;
        try {
            String req = "Select * from session where id = " + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                s = new Session(rs.getInt("id"), se.getOneById(rs.getInt("id_evenement")), rs.getString("titre"), rs.getString("description"), rs.getString("parlant"), rs.getTime("debit"), rs.getTime("fin"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return s;
    }



    //////////////////////////////// metier ////////////////////////////////
    public List<Session> getAllByEvent(Evenement evenement) {
        List<Session> list = getAll();
        return list.stream().filter(e -> e.getEvenement().equals(evenement)).collect(Collectors.toList());
    }
}
