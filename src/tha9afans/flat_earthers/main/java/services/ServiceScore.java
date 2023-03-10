package services;

import entities.Personne;
import entities.Quiz;
import entities.Score;
import utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServiceScore implements Services<Score> {
    Connection cnx = DataSource.getInstance().getCnx();
    AuthResponseDTO userlogged = UserSession.getUser_LoggedIn();


    public void add(Score s) {
        try {
            if (s.getPersonne() == null || s.getQuiz() == null) {
                throw new IllegalArgumentException("Personne or Quiz object cannot be null");
            }
            String req = "INSERT INTO score (id_user, quiz_id, score, times_played) VALUES (?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, s.getPersonne().getId());
            ps.setInt(2, s.getQuiz().getQuiz_id());
            ps.setInt(3, s.getScore());
            ps.setInt(4, s.getTimes_played());
            ps.executeUpdate();
            System.out.println("Score added to database.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void delete(int id) {
        try {
            String req = "DELETE FROM `score` WHERE id=" + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Score deleted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(Score score) {
        try {
            String req = "DELETE FROM `score` WHERE id_user =" + score.getPersonne().getId() + " AND quiz_id = " + score.getQuiz().getQuiz_id();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Score deleted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }

    @Override
    public void update(Score p) {
        int score = p.getScore() > getByUserQuiz(p.getPersonne(), p.getQuiz()).getScore() ? p.getScore() : getByUserQuiz(p.getPersonne(), p.getQuiz()).getScore();
        try {
            String req = "UPDATE `score` SET `id_user`='" + p.getPersonne().getId() + "',`quiz_id`='" + p.getQuiz().getQuiz_id() + "',`score`='" + score + "',`times_played`='" + (p.getTimes_played() + 1) + "' WHERE `score`.`id_user`=" + p.getPersonne().getId() + " AND `score`.`quiz_id`=" + p.getQuiz().getQuiz_id();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Score updated");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public List<Score> getAll() {
        ServicePersonne servicePersonne = new ServicePersonne();
        ServicesQuiz serviceQuiz = new ServicesQuiz();
        List<Score> List = new ArrayList<>();
        try {
            String req = "SELECT * FROM `score`";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Score q = new Score(servicePersonne.getOneById(rs.getInt("id_user")), serviceQuiz.getById(rs.getInt("quiz_id")), rs.getInt("score"), rs.getInt("times_played"));
                List.add(q);
            }
        } catch (Exception e
        ) {
            System.out.println(e.getMessage());
        }
        return List;    }


        @Override
    public Score getById(int id) {
        try {
            String req = "SELECT * FROM `score` WHERE id = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ServicePersonne servicePersonne = new ServicePersonne();
                ServicesQuiz serviceQuiz = new ServicesQuiz();
                return new Score(servicePersonne.getOneById(rs.getInt("id_user")), serviceQuiz.getById(rs.getInt("quiz_id")), rs.getInt("score"), rs.getInt("times_played"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public Score getByUserQuiz(Personne p, Quiz q) {
        ServicePersonne servicePersonne = new ServicePersonne();
        ServicesQuiz serviceQuiz = new ServicesQuiz();
        Score s = null;
        try {
            String req = "SELECT * FROM `score` WHERE id_user = " + p.getId() + " AND quiz_id = " + (q != null ? q.getQuiz_id() : -1);
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                s = new Score(servicePersonne.getOneById(rs.getInt("id_user")), serviceQuiz.getById(rs.getInt("quiz_id")), rs.getInt("score"), rs.getInt("times_played"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return s;
    }
    public List<Score> getTop3UsersByScore() {
        ServicePersonne servicePersonne = new ServicePersonne();
        ServicesQuiz serviceQuiz = new ServicesQuiz();
        List<Score> topScores = new ArrayList<>();
        try {
            String req = "SELECT id_user, score FROM `score` ORDER BY score DESC LIMIT 3";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Score s = new Score(servicePersonne.getOneById(rs.getInt("id_user")), rs.getInt("score"));
                String nom = s.getPersonne().getNom();
                String prenom = s.getPersonne().getPrenom();
                int score = s.getScore();
                topScores.add(s);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(topScores);
        return topScores;
    }




}


