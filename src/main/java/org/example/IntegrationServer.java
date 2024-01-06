package org.example;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Function;

public class IntegrationServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1245);

            while (true) {
                Socket clientSocket = serverSocket.accept();

                ObjectInputStream inFromClient = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream outToClient = new ObjectOutputStream(clientSocket.getOutputStream());

                int methodChoice = inFromClient.readInt();

                double a = inFromClient.readDouble();
                double b = inFromClient.readDouble();
                int n = inFromClient.readInt();

                String mathFunction = (String) inFromClient.readObject();

                double result = 0;

                long nanoActualTime = System.nanoTime();
                switch (methodChoice) {
                    case 1:
                        double h = (b - a) / n;
                        double sum = 0.0;

                        for (int i = 0; i < n; i++) {
                            double x = a + i * h;
                            sum += calculateValue(mathFunction,x);
                            outToClient.writeInt(i);
                        }
                        result = sum * h;
                        break;
                    case 2:
                        h = (b - a) / n;
                        sum = 0.5 * (calculateValue(mathFunction,a) + calculateValue(mathFunction,b));

                        for (int i = 0; i < n; i++) {
                            double x = a + i * h;
                            sum += calculateValue(mathFunction,x);
                            outToClient.writeInt(i);
                        }
                        result = sum * h;
                        break;
                    case 3:
                        h = (b - a) / n;
                        sum = calculateValue(mathFunction,a) + calculateValue(mathFunction, b);

                        for (int i = 1; i < n; i++) {
                            double x = a + i * h;
                            sum += 2 * calculateValue(mathFunction, x);
                            outToClient.writeInt(i);
                        }

                        for (int i = 1; i <= n; i++) {
                            double x = a + i * h;
                            sum += 4 * calculateValue(mathFunction,x - h / 2);
                            outToClient.writeInt(n+i);
                        }
                        result = (h / 6) * sum;
                        break;
                    default:
                        System.out.println("Niepoprawny wybór metody.");
                }

                outToClient.writeDouble(result);
                long executionTime = System.nanoTime() - nanoActualTime;
                outToClient.writeLong(executionTime);
                outToClient.flush();

                outToClient.close();
                inFromClient.close();
                clientSocket.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static double calculateValue(String exp, double x) {
        Expression expression = new ExpressionBuilder(exp)
                .variables("x")
                .build()
                .setVariable("x", x);

        double result = expression.evaluate();
        return result;
    }
}
