package org.example;

import java.util.function.Function;

public class NumericalIntegration {

    public static double function(double x) {
        return Math.sin(x);
    }

    public static double rectangleMethod(double a, double b, int n, Function<Double, Double> function){
        double h = (b - a) / n;
        double suma = 0.0;

        for (int i = 0; i < n; i++) {
            double x = a + i * h;
            suma += function(x);
        }

        return suma * h;
    }

    public static double trapezeMethod(double a, double b, int n, Function<Double, Double> function) {
        double h = (b - a) / n;
        double suma = 0.5 * (function(a) + function(b));

        for (int i = 1; i < n; i++) {
            double x = a + i * h;
            suma += function(x);
        }

        return suma * h;
    }

    public static double simsponMethod(double a, double b, int n, Function<Double, Double> function) {
        double h = (b - a) / n;
        double suma = function(a) + function(b);

        for (int i = 1; i < n; i++) {
            double x = a + i * h;
            suma += 2 * function(x);
        }

        for (int i = 1; i <= n; i++) {
            double x = a + i * h;
            suma += 4 * function(x - h / 2);
        }

        return (h / 6) * suma;
    }


}
