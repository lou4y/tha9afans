package entities;

import java.util.Objects;

public class Score {
    private Personne personne;
    private Quiz quiz;
    private int score;
    private int times_played;

    public Score(Personne personne, Quiz quiz, int score, int times_played) {
        this.personne = personne;
        this.quiz = quiz;
        this.score = score;
        this.times_played = times_played;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTimes_played() {
        return times_played;
    }

    public void setTimes_played(int times_played) {
        this.times_played = times_played;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score1 = (Score) o;
        return score == score1.score && times_played == score1.times_played && Objects.equals(personne, score1.personne) && Objects.equals(quiz, score1.quiz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personne, quiz, score, times_played);
    }

    @Override
    public String toString() {
        return "Score{" +
                "personne=" + personne +
                ", quiz=" + quiz +
                ", score=" + score +
                ", times_played=" + times_played +
                '}';
    }
}
