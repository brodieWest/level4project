package main.utils;

public class MathsUtils {
    public static double round(double number, int factor) {
        return Math.round(number/factor) * factor;
    }
}
