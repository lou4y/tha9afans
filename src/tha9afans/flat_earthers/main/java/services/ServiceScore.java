package services;

import entities.Personne;
import entities.Question;
import entities.Quiz;
import entities.Score;
import utils.DataSource;

import javax.swing.*;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceScore implements Services<Score>{
    Connection cnx = DataSource.getInstance().getCnx();


    public void add(Score s) {
        try {
            String req = "INSERT INTO score (id_user, quiz_id, score, times_played) VALUES (?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, s.getScore());
            ps.setInt(2, s.getPersonne().getId());
            ps.setInt(3, s.getQuiz().getQuiz_id());
            ps.setInt(4, s.getTimes_played());
            ps.executeUpdate();
            System.out.println("Score added to database.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {

    }


    public void delete(Score score ) {
        try{
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
        int score = p.getScore() > getByUserQuiz(p.getPersonne(),p.getQuiz()).getScore()?p.getScore():getByUserQuiz(p.getPersonne(),p.getQuiz()).getScore();
        try {
            String req = "UPDATE `score` SET `id_user`='" + p.getPersonne().getId() + "',`quiz_id`='" + p.getQuiz().getQuiz_id() + "',`score`='" + p.getScore() + "',`times_played`='" + p.getTimes_played()+1 + "' WHERE `score`.`id_user`=" + p.getPersonne().getId() + " AND `score`.`quiz_id`=" + p.getQuiz().getQuiz_id();
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
            String req = "SELECT * FROM `scroe`";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Score q = new Score(servicePersonne.getOneById(rs.getInt("id_user")), serviceQuiz.getById(rs.getInt("quiz_id")), rs.getInt("score"), rs.getInt("times_played"));
                List.add(q);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return List;
    }

    @Override
    public Score getById(int id) {
        return null;
    }


    public Score getByUserQuiz(Personne p, Quiz q) {
        ServicePersonne servicePersonne = new ServicePersonne();
        ServicesQuiz serviceQuiz = new ServicesQuiz();
        Score s = null;
        try {
            String req = "SELECT * FROM `score` WHERE id_user = " + p.getId() + " AND quiz_id = " + q.getQuiz_id();
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
}
