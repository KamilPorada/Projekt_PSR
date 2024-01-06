package org.example;

public class Romberg {
    private int n=1;
    private double[][] result = new double[100][100];
    private double begin=-1, end=3;

    private long start, stop;


    public void calculate() {
        for(int i=0;i<n;i++) {
            start = System.currentTimeMillis();
            for(int j=0;j<n;j++)
            {
                if(j<=i)
                    result[i][j]=RombergMethod(i,j,begin,end,n);
                else
                    result[i][j]=0.0;
            }
            stop = System.currentTimeMillis();
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
        System.out.println(temp);

        System.out.println(((stop-start) + " ms"));

    }

    private double RombergMethod(int i, int j, double begin, double end, int n) {
        double h=(end-begin)/Math.pow(2,i);
        if(i==0 && j==0)
            return 0.5*f(begin)+0.5*f(end);
        else if(i!=0 && j==0) {
            double sum=0;
            for(int k=1;k<=Math.pow(2,i-1);k++)
            {
                sum = sum + f(begin+(2*k-1)*h);
            }
            return 0.5 * RombergMethod(i-1,0,begin,end,n) + h * sum;
        }
        else
            return ((Math.pow(4,j)*RombergMethod(i,j-1,begin,end,n))-
                    RombergMethod(i-1,j-1,begin,end,n))/(Math.pow(4,j)-1);
    }

    private static double f(double x) {
        return(4*x*x*x+2*x-1);
    }

}
