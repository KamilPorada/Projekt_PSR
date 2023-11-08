package org.example;

public class NumericalIntegration {

    public static double function(double x) {
        return Math.sin(x);
    }

    public static double rectangleMethod(double a, double b, int n) {
        double h = (b - a) / n;
        double suma = 0.0;

        for (int i = 0; i < n; i++) {
            double x = a + i * h;
            suma += function(x);
        }

        return suma * h;
    }

    public static double trapezeMethod(double a, double b, int n) {
        double h = (b - a) / n;
        double suma = 0.5 * (function(a) + function(b));

        for (int i = 1; i < n; i++) {
            double x = a + i * h;
            suma += function(x);
        }

        return suma * h;
    }

    public static double simsponMethod(double a, double b, int n) {
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

    public static void main(String[] args) {
        double a = -20.0;
        double b = 312.14;
        int n = 3;

        double wynikProstokaty = rectangleMethod(a, b, n);
        double wynikTrapezy = trapezeMethod(a, b, n);
        double wynikParabol = simsponMethod(a, b, n);

        System.out.println("Wynik całkowania (Metoda Prostokątów): " + wynikProstokaty);
        System.out.println("Wynik całkowania (Metoda Trapezów): " + wynikTrapezy);
        System.out.println("Wynik całkowania (Metoda Parabol): " + wynikParabol);
    }
}
