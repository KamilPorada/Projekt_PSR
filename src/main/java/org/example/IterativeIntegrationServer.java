package org.example;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class IterativeIntegrationServer {

    static int tik=0;

    public static void main(String[] args) {
        try {

            ServerSocket serverSocket = new ServerSocket(3000);

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

                double[] rectangle = new double[7];
                double[] trapeze = new double[7];
                double[] parabolas = new double[7];

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
                    case 5:
                        for(int j=1; j<=5; j++){
                            n = (int) Math.pow(10,j);
                            h = (b - a) / n;
                            sum = 0.0;

                            for (int i = 0; i < n; i++) {
                                double x = a + i * h;
                                sum += calculateValue(mathFunction,x);
                                outToClient.writeInt(1);
                            }
                            result = sum * h;
                            rectangle[j-1] = result;
                        }

                        for(int j=1; j<=5; j++){
                            n = (int) Math.pow(10,j);
                            h = (b - a) / n;
                            sum = 0.5 * (calculateValue(mathFunction,a) + calculateValue(mathFunction,b));

                            for (int i = 0; i < n; i++) {
                                double x = a + i * h;
                                sum += calculateValue(mathFunction,x);
                                outToClient.writeInt(2);
                            }
                            result = sum * h;
                            trapeze[j-1] = result;
                        }

                        for(int j=1; j<=5; j++){
                            n = (int) Math.pow(10,j);
                            h = (b - a) / n;
                            sum = calculateValue(mathFunction,a) + calculateValue(mathFunction, b);

                            for (int i = 1; i < n; i++) {
                                double x = a + i * h;
                                sum += 2 * calculateValue(mathFunction, x);
                                outToClient.writeInt(3);
                            }

                            for (int i = 1; i <= n; i++) {
                                double x = a + i * h;
                                sum += 4 * calculateValue(mathFunction,x - h / 2);
                                outToClient.writeInt(3);
                            }
                            result = (h / 6) * sum;
                            parabolas[j-1] = result;
                        }

                        outToClient.writeInt(-1);

                    default:
                        System.out.println("Niepoprawny wybór metody.");
                }

                long executionTime = System.nanoTime() - nanoActualTime;
                if(methodChoice == 5){
                    outToClient.writeObject(rectangle);
                    outToClient.writeObject(trapeze);
                    outToClient.writeObject(parabolas);
                }
                else{
                    outToClient.writeDouble(result);
                }
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
