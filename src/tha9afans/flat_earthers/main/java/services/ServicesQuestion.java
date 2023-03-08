package services;

import entities.Question;
import utils.DataSource;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicesQuestion implements Services<Question> {

    Connection cnx = DataSource.getInstance().getCnx();

//    @Override
//    public void add(Question p) {
//        try {
//            String req = "INSERT INTO question( `question`, `answer`, `timer`, `first_possible_answer`, `second_possible_answer`, `third_possible_answer`, `image`) VALUES (?,?,?,?,?,?,?)";
//            PreparedStatement ps = cnx.prepareStatement(req);
//            ps.setString(1, p.getQuestion());
//            ps.setString(2, p.getRight_answer());
//            ps.setInt(3, p.getTimer());
//            ps.setString(4, p.getFirst_possible_answer());
//            ps.setString(5, p.getSecond_possible_answer());
//            ps.setString(6, p.getThird_possible_answer());
//            if (p.getQuestion_image() != null) {
//                ps.setBlob(7, p.getQuestion_image());
//            } else {
//                ps.setNull(7, Types.BLOB);
//            }
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }

    @Override
    public void add(Question p) {
        try {
            // Check if the question already exists in the database
            String checkQuery = "SELECT * FROM question WHERE question = ?";
            PreparedStatement checkStmt = cnx.prepareStatement(checkQuery);
            checkStmt.setString(1, p.getQuestion());
            ResultSet checkResult = checkStmt.executeQuery();
            if (checkResult.next()) {
                System.out.println("Question already exists in database.");
                return;
            }

            // If the question doesn't exist, insert it into the database
            String req = "INSERT INTO question( `question`, `answer`, `timer`, `first_possible_answer`, `second_possible_answer`, `third_possible_answer`, `image`) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, p.getQuestion());
            ps.setString(2, p.getRight_answer());
            ps.setInt(3, p.getTimer());
            ps.setString(4, p.getFirst_possible_answer());
            ps.setString(5, p.getSecond_possible_answer());
            ps.setString(6, p.getThird_possible_answer());
            if (p.getQuestion_image() != null) {
                ps.setBlob(7, p.getQuestion_image());
            } else {
                ps.setNull(7, Types.BLOB);
            }
            ps.executeUpdate();
            System.out.println("Question added to database.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void delete(int id) {
        try {
            String req = "DELETE FROM `question` WHERE question_id =" + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Question deleted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

    }

    @Override
    public void update(Question p) {
        try {
            String req = "UPDATE `question` SET `question`='" + p.getQuestion() + "',`answer`='" + p.getRight_answer() + "',`timer`='" + p.getTimer() + "',`first_possible_answer`='" + p.getFirst_possible_answer() + "',`second_possible_answer`='" + p.getSecond_possible_answer() + "',`third_possible_answer`='" + p.getThird_possible_answer() + "' WHERE `question`.`question_id`=" + p.getQuestion_id();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Question updated");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public List<Question> getAll() {
        List<Question> List = new ArrayList<>();
        try {
            String req = "SELECT * FROM `question`";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Question q = new Question(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(5), rs.getString(6), rs.getString(7), null, rs.getInt(4));
                Blob blob = rs.getBlob(8);
                if (blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    q.setQuestion_image(inputStream);
                }
                List.add(q);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return List;
    }

    @Override
    public Question getById(int id) {
        Question q = null;
        try {
            String req = "SELECT * FROM `question` WHERE question_id =" + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                q = new Question(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(5), rs.getString(6), rs.getString(7), (InputStream) rs.getBinaryStream(8), rs.getInt(4));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return q;
    }

    public boolean questionExists(Question q) {
        boolean exists = false;
        try {
            String req = "SELECT * FROM question WHERE question = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, q.getQuestion());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

}
