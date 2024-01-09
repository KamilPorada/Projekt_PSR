package org.example;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RecursiveIntegrationServer1 {
    static int tik = 1;
    public static void main(String[] args) {

        try {
            ServerSocket serverSocket1 = new ServerSocket(1000);

            while (true) {
                System.out.println("Działam na serwerze 1");

                //komunikacja z klientem
                Socket clientSocket = serverSocket1.accept();
                ObjectInputStream inFromClient = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream outToClient = new ObjectOutputStream(clientSocket.getOutputStream());

                //komunikacja z serwerem 2
                Socket socket2 = new Socket("localhost", 2000);
                ObjectOutputStream outToServer2 = new ObjectOutputStream(socket2.getOutputStream());
                ObjectInputStream inFromServer2 = new ObjectInputStream(socket2.getInputStream());

                int methodChoice = inFromClient.readInt();

                double a = inFromClient.readDouble();
                double b = inFromClient.readDouble();
                int n1 = inFromClient.readInt();
                int n2 = inFromClient.readInt();
                int n = n1 + n2;

                String mathFunction = (String) inFromClient.readObject();

                double[][] result = new double[100][100];

                long nanoStartTime = 0, nanoEndTime = 0;
                nanoStartTime = System.nanoTime();

                for(int i=0;i<n1;i++) {

                    for(int j=0;j<n;j++)
                    {
                        if(j<=i)
                            result[i][j]=RombergMethod(i,j,a,b, mathFunction, outToClient);
                        else
                            result[i][j]=0.0;
                    }
                }

                String temp="";
                for(int i=0;i<n1;i++) {
                    for(int j=0;j<n;j++)
                    {
                        if(j<=i)
                            temp=temp+String.format("%.3f", Math.round(result[i][j] * 1000.0) / 1000.0)+"  ";
                    }
                    temp=temp+"\n";
                }

                outToServer2.writeDouble(a);
                outToServer2.writeDouble(b);
                outToServer2.writeInt(n1);
                outToServer2.writeInt(n2);
                outToServer2.writeInt(n);
                outToServer2.writeObject(mathFunction);
                outToServer2.writeObject(result);
                outToServer2.writeObject(temp);

                String tik ="";

                for(;;){
                    tik = (String) inFromServer2.readObject();
                    outToClient.writeObject("tik");
                    if(tik.compareTo("Stop") == 0){
                        break;
                    }
                }

                result = (double[][]) inFromServer2.readObject();
                temp = (String) inFromServer2.readObject();

                nanoEndTime = System.nanoTime();
                outToClient.writeObject("Stop");
                if(methodChoice == 5)
                    outToClient.writeObject(result);
                else
                    outToClient.writeDouble(result[n-1][n-1]);
                long executionTime = nanoEndTime - nanoStartTime;
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

    private static double RombergMethod(int i, int j, double begin, double end, String mathFunction, ObjectOutputStream outToClient) throws IOException {
        outToClient.writeObject("Tik z serwera 1: " + tik++);
        double h=(end-begin)/Math.pow(2,i);
        if(i==0 && j==0)
            return 0.5*calculateValue(mathFunction, begin)+0.5*calculateValue(mathFunction, end);
        else if(i!=0 && j==0) {
            double sum=0;
            for(int k=1;k<=Math.pow(2,i-1);k++)
            {
                sum = sum + calculateValue(mathFunction,begin+(2*k-1)*h);
            }
            return 0.5 * RombergMethod(i-1,0,begin,end, mathFunction, outToClient) + h * sum;
        }
        else
            return ((Math.pow(4,j)*RombergMethod(i,j-1,begin,end, mathFunction, outToClient))-
                    RombergMethod(i-1,j-1,begin,end, mathFunction, outToClient))/(Math.pow(4,j)-1);
    }
}
