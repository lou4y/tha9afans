package services;

import entities.*;
import utils.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServiceReservation implements IService<Reservation> {
    AuthResponseDTO userlogged=UserSession.getUser_LoggedIn(); //hedhi t7otouha f kol controlleur te5oulek luser eli 3mal login
    ServiceBillet serviceBillet = new ServiceBillet();
    ServicePersonne serviceUser = new ServicePersonne();
    ServiceEvenement serviceEvenement = new ServiceEvenement();
    Connection conx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Reservation p) {


        String req = "INSERT INTO `reservation`(`date_reservation`, `isPaid`, `payment_info`, `id_user`, `id_billet`)" +
                " VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps = conx.prepareStatement(req);
            ps.setDate(1, p.getDate_reservation());
            ps.setBoolean(2, p.getIspPaid());
            ps.setString(3, p.getPayment_info());
            ps.setInt(4, 0);
            ps.setInt(5, p.getBillet().getId());
            ps.executeUpdate();
            System.out.println("reservation ajoutée");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM `reservation` WHERE id=?";
        try {
            PreparedStatement ps = conx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("reservation supprimée");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void modifier(Reservation p) {
        String req = "UPDATE `reservation` SET `date_reservation`=? ,`isPaid`=?," +
                "`payment_info`=?,`id_user`=?,`id_billet`=? WHERE id=?";
        System.out.println("modif");
        try {
            PreparedStatement ps = conx.prepareStatement(req);
            ps.setDate(1, p.getDate_reservation());
            ps.setBoolean(2, p.getIspPaid());
            ps.setString(3, p.getPayment_info());
            ps.setInt(4, p.getUser().getId());
            ps.setInt(5, p.getBillet().getId());
            ps.setInt(6, p.getId());
            ps.executeUpdate();
            System.out.println("Reservation " + p.getId() + " mise a jour");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public List<Reservation> getAll() {
        List<Reservation> list = new ArrayList<>();
        String req = "SELECT * FROM `reservation` ";
        try {
            Statement st = conx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Reservation p = new Reservation(rs.getInt(1), rs.getDate(2), rs.getBoolean(3),
                        rs.getString(4), serviceUser.getOneById(rs.getInt(5)),
                        serviceBillet.getOneById(rs.getInt(6)));
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    @Override
    public Reservation getOneById(int id) {
        Reservation p = null;
        String req = "SELECT * FROM `reservation` WHERE id= " + id;
        try {
            Statement st = conx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                p = new Reservation(rs.getInt(1), rs.getDate(2), rs.getBoolean(3),
                        rs.getString(4), serviceUser.getOneById(rs.getInt(5)),
                        serviceBillet.getOneById(rs.getInt(6)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return p;
    }

//TODO //////// CRUD \\\\\\\\\\\\


//TODO //////// METIER \\\\\\\\\\\\


    public boolean billetDisponible(Evenement evenement) {
        List<Billet> billets =getAll().stream()
                .filter(reservation -> reservation.getUser().getId() == 0)
                .map(res -> res.getBillet())
                .filter(billet -> billet.getEvenement().getId() == evenement.getId())
                .collect(Collectors.toList());
        return  billets.size() > evenement.getNb_participants() ;
    }
    public Billet billetDispo(Evenement evenement) {
        List<Billet> billets =getAll().stream()
                .filter(reservation -> reservation.getUser().getId() == 0)
                .map(res -> res.getBillet())
                .filter(billet -> billet.getEvenement().getId() == evenement.getId())
                .collect(Collectors.toList());
        System.out.println(billets);
        return billets.get(0);
    }
    public Reservation ReservationDispo(Evenement evenement) {
        List<Reservation> reservations =getAll().stream()
                .filter(reservation -> reservation.getUser().getId() == 0 && reservation.getBillet().getEvenement().getId()==evenement.getId())
                .collect(Collectors.toList());
        System.out.println(reservations.get(0));
        return reservations.get(0);
    }
    public int ReservationDispon(Evenement evenement) {
        List<Reservation> reservations =getAll().stream()
                .filter(reservation -> reservation.getUser().getId() == 0 && reservation.getBillet().getEvenement().getId()==evenement.getId())
                .collect(Collectors.toList());
        if (reservations.size() == 0)
            return 0;
        else
            return reservations.size();
    }


    public List<Reservation> getAllReservationsByUser(Personne user){
        List<Reservation> reservations = getAll().stream()
                .filter(e -> e.getUser().getId() == user.getId())
                .sorted(new DateReservationComparator())
                .collect(Collectors.toList());       //By RESERVATION DATE
        // .collect(Collectors.groupingBy(Collectors.groupingBy(reservation->))
        return reservations;    // TODO here we can use sorted(comparator.comparing(params)).thenComparing(params)
    }

    public ArrayList<Reservation> getAllReservationsEvenementByUser(Evenement evenement, Personne user){
        List<Reservation> reservations = getAllReservationsByUser(user);
        if(reservations==null){
            System.out.println("the user"+user.getNom()+" dosn't have reservations to show");
            return null;
        }
        return new ArrayList<>(reservations.stream()
                .filter(e-> e.getBillet().getEvenement().equals(evenement))
                .collect(Collectors.toList()));
    }

    public int findIdEvenement(Evenement e ){
        ArrayList<Evenement> list = new ArrayList(serviceEvenement.getAll());
        Optional<Evenement> eventIsPresent = list.stream()
                .filter(t->t.getNom()==e.getNom() && t.getLocalisation()==e.getLocalisation())
                .findFirst();
        if( !eventIsPresent.isPresent() ){
            return -1;
        }else
            return eventIsPresent.get().getId();
    }

    public void setAllBillet(int id) {
        ServiceEvenement se= new ServiceEvenement();

        if (id == 0) {
            System.out.println("evenement n'existe pas je suis dans la fonction setAllBillet serviceBillet");
            return;
        }
        for (int i = 0; i < se.getOneById(id).getNb_participants(); i++) {
            Billet b = new Billet( Integer.toString(i), se.getOneById(id).getDate(), se.getOneById(id).getPrix(),se.getOneById(id));
            serviceBillet.ajouter(b);
           int  idb=serviceBillet.getId(b);
            Reservation r = new Reservation(Date.valueOf("1990-01-01"), false, "ok", null,serviceBillet.getOneById(idb));
            /*new User(0, "null"), b)*/
            ajouter(r);
        }
    }
}
