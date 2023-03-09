package services;

import entities.Billet;
import entities.Evenement;
import utils.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ServiceBillet implements IService<Billet>{

    ServiceEvenement serviceEvenement = new ServiceEvenement();    //FIXME instancier les services dont j'ai besoin
    Connection conx = DataSource.getInstance().getCnx();

    //FIXME //////// CRUD \\\\\\\\\\\\
    @Override
    public void ajouter(Billet p) {
        String req="INSERT INTO `billet`(`code`, `date_validite`, `prix`, `id_evenement`)" +
                " VALUES (?,?,?,?)";
        try {
            PreparedStatement ps= conx.prepareStatement(req);
            ps.setString(1,p.getCode());
            ps.setDate(2,p.getDate_validite());
            ps.setDouble(3,p.getPrix());
            ps.setInt(4,p.getEvenement().getId());
            ps.executeUpdate();
            System.out.println("Billet ajoutée");
        }catch(SQLException e){
            System.out.println(e);
        }
    }

    @Override
    public void supprimer(int id) {
        String req="DELETE FROM `billet` WHERE id=?";
        try {
            PreparedStatement ps= conx.prepareStatement(req);
            ps.setInt(1,id);
            ps.executeUpdate();
            System.out.println("billet supprimée");
        }catch(SQLException e){
            System.out.println(e);
        }
    }

    @Override
    public void modifier(Billet p) {
        String req="UPDATE `billet` SET `code`=?,`date_validite`=?," +
                "`prix`=?,`id_evenement`=? WHERE id=?";
        try {
            PreparedStatement ps= conx.prepareStatement(req);
            ps.setString(1,p.getCode());
            ps.setDate(2,p.getDate_validite());
            ps.setDouble(3,p.getPrix());
            ps.setInt(4,p.getEvenement().getId());
            ps.setInt(5,p.getId());
            ps.executeUpdate();
            System.out.println("billet "+p.getId() +" mise a jour");
        }catch(SQLException e){
            System.out.println(e);
        }
    }

    @Override
    public List<Billet> getAll() {
        List<Billet> list = new ArrayList<>();
        String req="SELECT * FROM `billet`";
        try{
            Statement st = conx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Billet p = new Billet(rs.getInt(1),rs.getString(2),rs.getDate(3),
                        rs.getDouble(4),serviceEvenement.getOneById(rs.getInt(5)));
                list.add(p);
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        return list;
    }

    @Override
    public Billet getOneById(int id) {
        Billet p=null;
        String req="SELECT * FROM `billet` WHERE id = "+id;
        try{
            Statement st = conx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                p = new Billet(rs.getInt(1), rs.getString(2), rs.getDate(3),
                        rs.getDouble(4), serviceEvenement.getOneById(rs.getInt(5)));
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        return p;
    }
    public int getId(Billet billet) {
        List<Billet> list = getAll();
        Billet b = list.stream()
                .filter(e -> e.getEvenement().getId()==(billet.getEvenement().getId()) &&
                        e.getCode().equals(billet.getCode()) &&
                        e.getDate_validite().equals(billet.getDate_validite()) &&
                        e.getPrix()==(billet.getPrix()))
                .findFirst()
                .orElse(null);
        return b != null ? b.getId() : -1;
    }
}
