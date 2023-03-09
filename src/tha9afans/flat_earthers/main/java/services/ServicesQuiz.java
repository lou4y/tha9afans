package services;

import entities.Question;
import entities.Quiz;
import utils.DataSource;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicesQuiz implements Services<Quiz> {

    Connection cnx = DataSource.getInstance().getCnx();
    ServicesQuestion servicesQuestion = new ServicesQuestion();


    public void add(Quiz p) {
        try {
            // Check if the quiz name already exists in the database
            String checkQuery = "SELECT * FROM quiz WHERE quiz_name = ?";
            PreparedStatement checkStmt = cnx.prepareStatement(checkQuery);
            checkStmt.setString(1, p.getQuiz_name());
            ResultSet checkResult = checkStmt.executeQuery();
            if (checkResult.next()) {
                System.out.println("Quiz name already exists in database.");
                return;
            }

            // If the quiz name doesn't exist, insert the quiz into the database
            String req = "INSERT INTO quiz( `quiz_name`, `nbr_questions`, `quiz_cover`, `quiz_description` ) VALUES (?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, p.getQuiz_name());
            ps.setInt(2, p.getNumber_of_questions());
            ps.setString(4, p.getQuiz_description());
            if (p.getQuiz_cover() != null) {
                ps.setBlob(3, p.getQuiz_cover());
            } else {
                ps.setNull(3, Types.BLOB);
            }
            ps.executeUpdate();
            System.out.println("Quiz added to database.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void delete(int id) {
        try {
            String req = "DELETE FROM `quiz` WHERE quiz_id =" + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Quiz deleted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }

    @Override
    public void update(Quiz p) {
        try {
            String req = "UPDATE `quiz` SET `quiz_name`='" + p.getQuiz_name() + "',`nbr_questions`='" + p.getNumber_of_questions() + "',`quiz_description`='" + p.getQuiz_description() + "',`times_played`='" + p.getTimes_played() + "' WHERE `quiz`.`quiz_id`=" + p.getQuiz_id();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Quiz updated");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Quiz> getAll() {
        List<Quiz> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM quiz";
            Statement statement = cnx.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuiz_id(resultSet.getInt("quiz_id"));
                quiz.setQuiz_name(resultSet.getString("quiz_name"));
                quiz.setNumber_of_questions(resultSet.getInt("nbr_questions"));
                quiz.setQuiz_description(resultSet.getString("quiz_description"));
                Blob blob = resultSet.getBlob("quiz_cover");
                if (blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    quiz.setQuiz_cover(inputStream);
                }
                list.add(quiz);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }



    @Override
    public Quiz getById(int id) {
        Quiz q = null;
        try {
            String req = "SELECT * FROM `quiz` WHERE quiz_id =" + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                q = new Quiz();
                q.setQuiz_id(rs.getInt("quiz_id"));
                q.setQuiz_name(rs.getString("quiz_name"));
                q.setNumber_of_questions(rs.getInt("nbr_questions"));
                q.setQuiz_description(rs.getString("quiz_description"));
                q.setQuiz_cover(rs.getBlob("quiz_cover").getBinaryStream());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return q;
    }

    public int getId(Quiz quiz) {
        List<Quiz> list = getAll();
        Quiz q = list.stream()
                .filter(e -> e.getQuiz_name().equals(quiz.getQuiz_name())&&
                        e.getNumber_of_questions() == quiz.getNumber_of_questions()&&
                        e.getQuiz_description().equals(quiz.getQuiz_description()))
                .findFirst()
                .orElse(null);
        return q != null ? q.getQuiz_id(): -1;
    }

    public boolean quizExists(String quizName) {
        try {
            String query = "SELECT COUNT(*) FROM quiz WHERE quiz_name = ?";
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setString(1, quizName);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



}

