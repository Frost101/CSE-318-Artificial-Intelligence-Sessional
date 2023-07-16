import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();

        // Generate a random integer between 'min' and 'max'
        int randomNumber = random.nextInt(10 - 3 + 1) + 3;
        System.out.println(randomNumber);

    }
}
