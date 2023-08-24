import java.util.Random;

public class UniformRandomGenerator {

    UniformRandomGenerator(){

    }
    public double getRandomNumber(double min,double max){
        Random random = new Random();
        double randomValue = min + (max - min) * random.nextDouble();
        return randomValue;
    }
}
