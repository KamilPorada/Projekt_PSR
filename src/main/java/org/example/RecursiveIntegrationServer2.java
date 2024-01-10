package org.example;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RecursiveIntegrationServer2 {
    static int tik = 1;
    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(2222);

            while (true) {
                Socket socket = serverSocket.accept();
                ObjectInputStream inFromServer1 = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream outToServer1 = new ObjectOutputStream(socket.getOutputStream());


                double a = inFromServer1.readDouble();
                double b = inFromServer1.readDouble();
                int n1 = inFromServer1.readInt();
                int n2 = inFromServer1.readInt();
                int n = inFromServer1.readInt();
                String mathFunction = (String) inFromServer1.readObject();
                double[][] result = (double[][]) inFromServer1.readObject();

                for(int i=n1;i<n;i++) {
                    for(int j=0;j<n;j++)
                    {
                        if(j<=i)
                            result[i][j]=RombergMethod(i,j,a,b, mathFunction, outToServer1);
                        else
                            result[i][j]=0.0;
                    }
                }

                outToServer1.writeObject("Stop");
                outToServer1.writeObject(result);
                outToServer1.flush();

                outToServer1.close();
                inFromServer1.close();
                socket.close();
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

        return expression.evaluate();
    }

    private static double RombergMethod(int i, int j, double begin, double end, String mathFunction, ObjectOutputStream outToServer1) throws IOException {
        outToServer1.writeObject("Tik z serwera 2: " + tik++);
        double h=(end-begin)/Math.pow(2,i);
        if(i==0 && j==0)
            return 0.5*calculateValue(mathFunction, begin)+0.5*calculateValue(mathFunction, end);
        else if(i!=0 && j==0) {
            double sum=0;
            for(int k=1;k<=Math.pow(2,i-1);k++)
            {
                sum = sum + calculateValue(mathFunction,begin+(2*k-1)*h);
            }
            return 0.5 * RombergMethod(i-1,0,begin,end, mathFunction, outToServer1) + h * sum;
        }
        else
            return ((Math.pow(4,j)*RombergMethod(i,j-1,begin,end, mathFunction, outToServer1))-
                    RombergMethod(i-1,j-1,begin,end, mathFunction, outToServer1))/(Math.pow(4,j)-1);
    }
}
