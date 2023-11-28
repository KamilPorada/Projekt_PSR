package org.example;

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

                // Read method choice from the client
                int methodChoice = inFromClient.readInt();

                // Odczytanie nazwy funkcji od klienta
                String functionName = (String) inFromClient.readObject();
                Function<Double, Double> function = getFunctionByName(functionName);

                double a = inFromClient.readDouble();
                double b = inFromClient.readDouble();
                int n = inFromClient.readInt();

                double result = 0;

                // Calculate result based on the chosen method
                long millisActualTime = System.nanoTime();
                switch (methodChoice) {
                    case 1:
                        result = NumericalIntegration.rectangleMethod(a, b, n, function);
                        break;
                    case 2:
                        result = NumericalIntegration.trapezeMethod(a, b, n, function);
                        break;
                    case 3:
                        result = NumericalIntegration.simsponMethod(a, b, n, function);
                        break;
                    default:
                        System.out.println("Niepoprawny wybór metody.");
                        // Handle the case of an invalid method choice
                        // You might want to send an error message back to the client
                }

                // Send the result back to the client
                outToClient.writeDouble(result);
                long executionTime = System.nanoTime() - millisActualTime;
                outToClient.writeLong(executionTime);
                outToClient.flush();

                // Zamknięcie strumieni i gniazda
                outToClient.close();
                inFromClient.close();
                clientSocket.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Function<Double, Double> getFunctionByName(String functionName) {
        switch (functionName) {
            case "1":
                return Math::sin;
            case "2":
                return Math::cos;
            case "3":
                return Math::sqrt;
            // Dodaj inne funkcje w miarę potrzeb
            default:
                throw new IllegalArgumentException("Unknown function name: " + functionName);
        }
    }
}
