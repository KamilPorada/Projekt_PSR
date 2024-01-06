package org.example;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Function;

public class IntegrationServer {
    public static double function(double x) {
        return (5*Math.pow(x,6)-2*Math.cos(3*x));
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1245);

            while (true) {
                Socket clientSocket = serverSocket.accept();

                ObjectInputStream inFromClient = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream outToClient = new ObjectOutputStream(clientSocket.getOutputStream());

                int methodChoice = inFromClient.readInt();

                String functionName = (String) inFromClient.readObject();
                Function<Double, Double> function = getFunctionByName(functionName);

                double a = inFromClient.readDouble();
                double b = inFromClient.readDouble();
                int n = inFromClient.readInt();

                String mathFunction = (String) inFromClient.readObject();
                calculateValue(mathFunction,n);

                double result = 0;

//                long nanoActualTime = System.nanoTime();
//                switch (methodChoice) {
//                    case 1:
//                        double h = (b - a) / n;
//                        double sum = 0.0;
//
//                        for (int i = 0; i < n; i++) {
//                            double x = a + i * h;
//                            sum += function(x);
//                            outToClient.writeInt(i);
//                        }
//                        result = sum * h;
//                        break;
//                    case 2:
//                        h = (b - a) / n;
//                        sum = 0.5 * (function(a) + function(b));
//
//                        for (int i = 0; i < n; i++) {
//                            double x = a + i * h;
//                            sum += function(x);
//                            outToClient.writeInt(i);
//                        }
//                        result = sum * h;
//                        break;
//                    case 3:
//                        h = (b - a) / n;
//                        sum = function(a) + function(b);
//
//                        for (int i = 1; i < n; i++) {
//                            double x = a + i * h;
//                            sum += 2 * function(x);
//                            outToClient.writeInt(i);
//                        }
//
//                        for (int i = 1; i <= n; i++) {
//                            double x = a + i * h;
//                            sum += 4 * function(x - h / 2);
//                            outToClient.writeInt(n+i);
//                        }
//                        result = (h / 6) * sum;
//                        break;
//                    default:
//                        System.out.println("Niepoprawny wybór metody.");
//                }
//
//                outToClient.writeDouble(result);
//                long executionTime = System.nanoTime() - nanoActualTime;
//                outToClient.writeLong(executionTime);
//                outToClient.flush();

                outToClient.close();
                inFromClient.close();
                clientSocket.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void calculateValue(String exp, int x) {
        Expression expression = new ExpressionBuilder(exp)
                .variables("x")
                .build()
                .setVariable("x", x);

        double result = expression.evaluate();
        System.out.println(result);

    }

    private static Function<Double, Double> getFunctionByName(String functionName) {
        switch (functionName) {
            case "1":
                return Math::sin;
            case "2":
                return Math::cos;
            case "3":
                return Math::sqrt;
            default:
                throw new IllegalArgumentException("Unknown function name: " + functionName);
        }
    }
}
