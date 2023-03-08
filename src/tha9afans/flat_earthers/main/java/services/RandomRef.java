package services;

public class RandomRef {
    public static String randomString(int len) {

        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(len);
        sb.append("ref"); // Ajoute "ref" au début du mot
        for (int i = 3; i < len; i++) {
            sb.append(AB.charAt((int) (Math.random() * AB.length())));
        }
        return sb.toString();
    }
    }

