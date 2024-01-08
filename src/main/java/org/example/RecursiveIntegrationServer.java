package org.example;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RecursiveIntegrationServer {
    static int tik = 1;
    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(2000);

            while (true) {
                Socket clientSocket = serverSocket.accept();

                ObjectInputStream inFromClient = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream outToClient = new ObjectOutputStream(clientSocket.getOutputStream());

                int methodChoice = inFromClient.readInt();

                double a = inFromClient.readDouble();
                double b = inFromClient.readDouble();
                int n = inFromClient.readInt();

                String mathFunction = (String) inFromClient.readObject();

                double[][] result = new double[100][100];

                long nanoStartTime = 0, nanoEndTime = 0;
                nanoStartTime = System.nanoTime();

                for(int i=0;i<n;i++) {
                    for(int j=0;j<n;j++)
                    {
                        if(j<=i)
                            result[i][j]=RombergMethod(i,j,a,b,n, mathFunction, outToClient);
                        else
                            result[i][j]=0.0;
                    }
                }

                String temp="";
                for(int i=0;i<n;i++) {
                    for(int j=0;j<n;j++)
                    {
                        if(j<=i)
                            temp=temp+String.format("%.3f", Math.round(result[i][j] * 1000.0) / 1000.0)+"  ";
                    }
                    temp=temp+"\n";
                }
                nanoEndTime = System.nanoTime();
                outToClient.writeObject("Stop");
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

    private static double RombergMethod(int i, int j, double begin, double end, int n, String mathFunction, ObjectOutputStream outToClient) throws IOException {
        outToClient.writeObject("Tik" + tik++);
        double h=(end-begin)/Math.pow(2,i);
        if(i==0 && j==0)
            return 0.5*calculateValue(mathFunction, begin)+0.5*calculateValue(mathFunction, end);
        else if(i!=0 && j==0) {
            double sum=0;
            for(int k=1;k<=Math.pow(2,i-1);k++)
            {
                sum = sum + calculateValue(mathFunction,begin+(2*k-1)*h);
            }
            return 0.5 * RombergMethod(i-1,0,begin,end,n, mathFunction, outToClient) + h * sum;
        }
        else
            return ((Math.pow(4,j)*RombergMethod(i,j-1,begin,end,n, mathFunction, outToClient))-
                    RombergMethod(i-1,j-1,begin,end,n, mathFunction, outToClient))/(Math.pow(4,j)-1);
    }
}
