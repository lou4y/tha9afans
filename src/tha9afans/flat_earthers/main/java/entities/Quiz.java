package entities;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class Quiz {
    private int quiz_id;
    private String quiz_name;
    private String quiz_description;
    private int number_of_questions;
    private InputStream quiz_cover;
    private int times_played;

    private ArrayList<Question> questions = new ArrayList<Question>();

    public Quiz() {
    }

    public Quiz(String quiz_name, String quiz_description, int number_of_questions, InputStream quiz_cover) {
        this.quiz_name = quiz_name;
        this.quiz_description = quiz_description;
        this.number_of_questions = number_of_questions;
        this.quiz_cover = quiz_cover;

    }

    public Quiz(int quiz_id, String quiz_name, String quiz_description, int number_of_questions, InputStream quiz_cover) {
        this.quiz_id = quiz_id;
        this.quiz_name = quiz_name;
        this.quiz_description = quiz_description;
        this.number_of_questions = number_of_questions;
        this.quiz_cover = quiz_cover;

    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public String getQuiz_name() {
        return quiz_name;
    }

    public void setQuiz_name(String quiz_name) {
        this.quiz_name = quiz_name;
    }

    public String getQuiz_description() {
        return quiz_description;
    }

    public void setQuiz_description(String quiz_description) {
        this.quiz_description = quiz_description;
    }

    public int getNumber_of_questions() {
        return number_of_questions;
    }

    public void setNumber_of_questions(int number_of_questions) {
        this.number_of_questions = number_of_questions;
    }

    public InputStream getQuiz_cover() { return quiz_cover; }

    public void setQuiz_cover(InputStream quiz_cover) {
        this.quiz_cover = quiz_cover;
    }

    public int getTimes_played() {
        return times_played;
    }

    public void setTimes_played(int times_played) {
        this.times_played = times_played;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return quiz_id == quiz.quiz_id && number_of_questions == quiz.number_of_questions && times_played == quiz.times_played && Objects.equals(quiz_name, quiz.quiz_name) && Objects.equals(quiz_description, quiz.quiz_description) && Objects.equals(quiz_cover, quiz.quiz_cover);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quiz_id, quiz_name, quiz_description, number_of_questions, quiz_cover, times_played);
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "quiz_id=" + quiz_id +
                ", quiz_name='" + quiz_name + '\'' +
                ", quiz_description='" + quiz_description + '\'' +
                ", number_of_questions=" + number_of_questions +
                ", quiz_cover=" + quiz_cover +
                ", times_played=" + times_played +
                '}';
    }


}
