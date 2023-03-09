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
            ps.setInt(4, userlogged.getIdUser());
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
        try {
            PreparedStatement ps = conx.prepareStatement(req);
            ps.setDate(1, p.getDate_reservation());
            ps.setBoolean(2, p.getIspPaid());
            ps.setString(3, p.getPayment_info());
            ps.setInt(4, userlogged.getIdUser());
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
                .filter(reservation -> reservation.getUser().getNom() != null)
                .map(res -> res.getBillet())
                .filter(billet -> billet.getEvenement().getId() == evenement.getId())
                .collect(Collectors.toList());
        return evenement.getNb_participants() > billets.size();
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

    public void setAllBillet(Evenement e) {
        int id_event = findIdEvenement(e);
        if (id_event == -1) {
            System.out.println("evenement n'existe pas je suis dans la fonction setAllBillet serviceBillet");
            return;
        }
        for (int i = 0; i < e.getNb_participants(); i++) {
            Billet b = new Billet( Integer.toString(i), e.getDate(), e.getPrix(), e);
            serviceBillet.ajouter(b);
            Reservation r = new Reservation(Date.valueOf("1990-01-01"), false, "ok",
                    new Utilisateur("0","null","0","0","0","0","0",null),b);
            /*new User(0, "null"), b)*/
            ajouter(r);
        }
    }
}
