package services;

import entities.Question;
import entities.Quiz;
import utils.DataSource;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceQuizQuestion {
    Connection cnx = DataSource.getInstance().getCnx();

    public void add(int quiz_id, int question_id) {
        try {
            String req = "INSERT INTO quiz_question( `quiz_id`, `question_id`) VALUES (?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, quiz_id);
            ps.setInt(2, question_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(int quiz_id, int question_id) {
        try {
            String req = "DELETE FROM quiz_question WHERE quiz_id = ? AND question_id = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, quiz_id);
            ps.setInt(2, question_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public  List<Question> getAllByQuiz(int id){
        List<Question> question = new ArrayList<>();
        try {
            String req = "SELECT * FROM quiz_question where quiz_id =" + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                ServicesQuestion servicesQuestion = new ServicesQuestion();
                question.add(servicesQuestion.getById(rs.getInt("question_id")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return question;
    }



}
