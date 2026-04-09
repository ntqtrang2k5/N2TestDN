package common;

import java.util.Random;

public class Utilities {
    public static String generateRandomEmail() {
        Random random = new Random();
        int randomNumber = random.nextInt(100000);
        return "test" + randomNumber + "@gmail.com";
    }
}