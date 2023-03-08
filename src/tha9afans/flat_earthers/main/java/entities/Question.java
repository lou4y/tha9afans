package entities;


import java.io.InputStream;
import java.util.Objects;

public class Question {

    private int question_id;
    private String question;
    private String first_possible_answer;
    private String second_possible_answer;
    private String third_possible_answer;
    private String right_answer;
    private InputStream question_image;
    private int timer;

    public Question() {
    }

    public Question(String question, String first_possible_answer, String second_possible_answer, String third_possible_answer, String right_answer, InputStream question_image, int timer) {
        this.question = question;
        this.first_possible_answer = first_possible_answer;
        this.second_possible_answer = second_possible_answer;
        this.third_possible_answer = third_possible_answer;
        this.right_answer = right_answer;
        this.timer = timer;
        this.question_image = question_image;
    }

    public Question(int question_id, String question, String first_possible_answer, String second_possible_answer, String third_possible_answer, String right_answer, InputStream question_image, int timer) {
        this.question_id = question_id;
        this.question = question;
        this.first_possible_answer = first_possible_answer;
        this.second_possible_answer = second_possible_answer;
        this.third_possible_answer = third_possible_answer;
        this.right_answer = right_answer;
        this.timer = timer;
        this.question_image = question_image;

    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getFirst_possible_answer() {
        return first_possible_answer;
    }

    public void setFirst_possible_answer(String first_possible_answer) {
        this.first_possible_answer = first_possible_answer;
    }

    public String getSecond_possible_answer() {
        return second_possible_answer;
    }

    public void setSecond_possible_answer(String second_possible_answer) {
        this.second_possible_answer = second_possible_answer;
    }

    public String getThird_possible_answer() {
        return third_possible_answer;
    }

    public void setThird_possible_answer(String third_possible_answer) {
        this.third_possible_answer = third_possible_answer;
    }

    public String getRight_answer() {
        return right_answer;
    }

    public void setRight_answer(String right_answer) {
        this.right_answer = right_answer;
    }

    public InputStream getQuestion_image() {
        return question_image;
    }

    public void setQuestion_image(InputStream question_image) {
        this.question_image = question_image;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question_id=" + question_id +
                "question='" + question + '\'' +
                ", first_possible_answer='" + first_possible_answer + '\'' +
                ", second_possible_answer='" + second_possible_answer + '\'' +
                ", third_possible_answer='" + third_possible_answer + '\'' +
                ", right_answer='" + right_answer + '\'' +
                ", question_image=" + question_image +
                ", timer=" + timer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question1 = (Question) o;
        return question_id == question1.question_id && timer == question1.timer && Objects.equals(question, question1.question) && Objects.equals(first_possible_answer, question1.first_possible_answer) && Objects.equals(second_possible_answer, question1.second_possible_answer) && Objects.equals(third_possible_answer, question1.third_possible_answer) && Objects.equals(right_answer, question1.right_answer) && Objects.equals(question_image, question1.question_image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question_id, question, first_possible_answer, second_possible_answer, third_possible_answer, right_answer, question_image, timer);
    }
}
