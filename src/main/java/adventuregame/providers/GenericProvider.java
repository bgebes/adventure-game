package adventuregame.providers;

import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GenericProvider {
    public static Scanner scanner = new Scanner(System.in);
    public static Random random = new Random();

    public static Object findObjectById(ArrayList list, int id) {
        return list.get(id);
    }

    public static int getRandomInt(int min, int max) {
        return random.nextInt(max) + min;
    }

    public static boolean checkIntInRange(long min, long max, long value) {
        return ValueRange.of(min, max).isValidIntValue(value);
    }
}
