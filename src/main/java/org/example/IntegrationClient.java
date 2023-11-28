package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class IntegrationClient {
    private static String formatElapsedTimeInSeconds(long nanoseconds) {
        double seconds = nanoseconds / 1_000_000_000.0;
        return String.format("%.3fs", seconds);
    }
    public static void main(String[] args) {

        try {
            Scanner scanner = new Scanner(System.in);
            boolean exit = false;

            while (!exit) {
                Socket socket = new Socket("localhost", 1245);

                ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());

                // Display menu options
                System.out.println("MENU");
                System.out.println("1. Metoda prostokątów");
                System.out.println("2. Metoda trapezów");
                System.out.println("3. Metoda parabol");
                System.out.println("4. Wyjście");

                System.out.print("Wybierz opcję:");
                int option = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (option) {
                    case 1:
                    case 2:
                    case 3:
                        // Wczytywanie danych od użytkownika
                        System.out.println("Zakres całkowania:");
                        System.out.print("od: ");
                        double a = scanner.nextDouble();

                        System.out.print("do: ");
                        double b = scanner.nextDouble();

                        System.out.print("Liczba podziałów: ");
                        int n = scanner.nextInt();

                        outToServer.writeInt(option);
                        outToServer.flush();

                        // Send the function name to the server
                        outToServer.writeObject("1"); // Change this to the desired function name
                        outToServer.flush();

                        outToServer.writeDouble(a);
                        outToServer.writeDouble(b);
                        outToServer.writeInt(n);
                        outToServer.flush();

                        double result = inFromServer.readDouble();
                        System.out.println("Wynik całkowania: " + result);
                        long elapsedTimeInNanoseconds = inFromServer.readLong();
                        String formattedTime = formatElapsedTimeInSeconds(elapsedTimeInNanoseconds);
                        System.out.println("Czas całkowania: " + formattedTime);
                        break;

                    case 4:
                        exit = true;
                        break;

                    default:
                        System.out.println("Niepoprawna opcja. Wybierz ponownie.");
                        break;
                }

                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
