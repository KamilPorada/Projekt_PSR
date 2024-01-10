package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SummaryServer extends JFrame {

    private static JLabel timeLabel, from, to, equation;
    private static JTable iterativeMethodsTable, recursiveMethodTable;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket1 = new ServerSocket(4444);

            while (true) {
                Socket clientSocket = serverSocket1.accept();
                ObjectInputStream inFromClient = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream outToClient = new ObjectOutputStream(clientSocket.getOutputStream());

                int a = inFromClient.readInt();
                int b = inFromClient.readInt();
                String stringFunction = (String) inFromClient.readObject();
                double[] rectangle = (double[]) inFromClient.readObject();
                double[] trapeze = (double[]) inFromClient.readObject();
                double[] parabolas = (double[]) inFromClient.readObject();
                double[][] romberg = (double[][]) inFromClient.readObject();
                String executionTime = (String) inFromClient.readObject();

                String[] iterativeColumnNames = {"Kolumna 1", "Kolumna 2", "Kolumna 3", "Kolumna 4", "Kolumna 5", "Kolumna 6", "Kolumna 7", "Kolumna 8"};

                Object[] row1 = new Object[rectangle.length+1];
                row1[0] = "Metoda prostokątów";
                for (int i = 0; i < rectangle.length; i++) {
                    row1[i + 1] = String.format("%.4f", rectangle[i]);
                }

                Object[] row2 = new Object[trapeze.length+1];
                row2[0] = "Metoda trapezów";
                for (int i = 0; i < trapeze.length; i++) {
                    row2[i + 1] = String.format("%.4f", trapeze[i]);
                }

                Object[] row3 = new Object[parabolas.length+1];
                row3[0] = "Metoda parabol";
                for (int i = 0; i < parabolas.length; i++) {
                    row3[i + 1] = String.format("%.4f", parabolas[i]);
                }

                Object[][] iterativeData = {
                        {"", "<html><body style='text-align: center'>10<sup>1</sup>", "<html><body style='text-align: center'>10<sup>2</sup>", "<html><body style='text-align: center'>10<sup>3</sup>", "<html><body style='text-align: center'>10<sup>4</sup>", "<html><body style='text-align: center'>10<sup>5</sup>", "<html><body style='text-align: center'>10<sup>6</sup>", "<html><body style='text-align: center'>10<sup>7</sup>"},
                        row1,row2,row3
                };

                String[] recursiveColumnNames = {"Kolumna 1", "Kolumna 2", "Kolumna 3", "Kolumna 4", "Kolumna 5", "Kolumna 6", "Kolumna 7", "Kolumna 8", "Kolumna 9", "Kolumna 10", "Kolumna 11", "Kolumna 12", "Kolumna 13", "Kolumna 14", "Kolumna 15", "Kolumna 16"};

                Object[][] recursiveData = new Object[16][16];

                for(int i=0;i<recursiveData.length;i++){
                    for(int j=0;j<recursiveData.length;j++){
                        if(i == 0){
                            if(j == 0)
                                recursiveData[i][j] = "i,j";
                            else
                                recursiveData[i][j] = j;
                        }
                        else{
                            if(j == 0)
                                recursiveData[i][j] = i;
                            else
                                recursiveData[i][j] = String.format("%.4f", romberg[i-1][j-1]);
                        }
                    }
                }



                SwingUtilities.invokeLater(() -> {
                    SummaryServer summaryServer = new SummaryServer();
                    summaryServer.setTimeLabel("<html><body style='text-align: center'>Całkowity czas całkowania: " + executionTime);
                    summaryServer.setIterativeTableContent(iterativeData,iterativeColumnNames);
                    summaryServer.setRecursiveTableContent(recursiveData,recursiveColumnNames);
                    from.setText(String.valueOf(a));
                    to.setText(String.valueOf(b));
                    equation.setText(stringFunction + "dx");
                    summaryServer.setVisible(true);
                });

                clientSocket.close();
                inFromClient.close();
                outToClient.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTimeLabel(String text) {
        timeLabel.setText(text);
    }

    public void setIterativeTableContent(Object[][] iterativeData, String[] iterativeColumnNames) {
        TableModel model = iterativeMethodsTable.getModel();

        if (model instanceof DefaultTableModel) {
            DefaultTableModel defaultModel = (DefaultTableModel) model;

            defaultModel.setRowCount(0);

            for (Object[] row : iterativeData) {
                defaultModel.addRow(row);
            }
        } else {
            DefaultTableModel defaultModel = new DefaultTableModel(iterativeData, iterativeColumnNames);

            iterativeMethodsTable.setModel(defaultModel);
        }

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 1; i < iterativeMethodsTable.getColumnCount(); i++) {
            iterativeMethodsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        iterativeMethodsTable.repaint();
    }

    public void setRecursiveTableContent(Object[][] recursiveData, String[] recursiveColumnNames) {
        TableModel model = recursiveMethodTable.getModel();

        if (model instanceof DefaultTableModel) {
            DefaultTableModel defaultModel = (DefaultTableModel) model;

            defaultModel.setRowCount(0);

            for (Object[] row : recursiveData) {
                defaultModel.addRow(row);
            }
        } else {
            DefaultTableModel defaultModel = new DefaultTableModel(recursiveData, recursiveColumnNames);

            recursiveMethodTable.setModel(defaultModel);
        }

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < recursiveMethodTable.getColumnCount(); i++) {
            recursiveMethodTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        recursiveMethodTable.repaint();
    }

    public SummaryServer (){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension d = kit.getScreenSize();
        int windowWidth = 9*d.width/10;
        int windowHeight = 9*d.height/10;
        setBounds(d.width/20,d.height/20,windowWidth,windowHeight);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(220,220,220));
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("<html><body style='text-align: center'>ZESTAWIENIE WYNIKÓW DZIAŁANIA ALGORYTMÓW CAŁKOWANIA NUMERYCZNEGO", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBounds(0, 0, windowWidth, 30);
        panel.add(titleLabel);


        JPanel integrationPanel = new JPanel();
        integrationPanel.setBackground(new Color(220,220,220));
        integrationPanel.setLayout(null);
        integrationPanel.setBounds(0, 30, windowWidth, 70);

        panel.add(integrationPanel);

        JLabel integrate = new JLabel("∫");
        integrate.setFont(new Font("Arial", Font.BOLD, 60));
        integrate.setBounds(600, 5, 100, 70);
        integrationPanel.add(integrate);

        from = new JLabel("2");
        from.setFont(new Font("Arial", Font.BOLD, 13));
        from.setBounds(617, 48, 20, 20);
        integrationPanel.add(from);

        to = new JLabel("8");
        to.setFont(new Font("Arial", Font.BOLD, 13));
        to.setBounds(617, 5, 20, 20);
        integrationPanel.add(to);

        equation = new JLabel("(2x-3)dx");
        equation.setFont(new Font("Arial", Font.PLAIN, 18));
        equation.setBounds(630, 22, 300, 28);
        integrationPanel.add(equation);


        JLabel subTitle1Label = new JLabel("<html><body style='text-align: center'>Metody Iteracyjne", SwingConstants.CENTER);
        subTitle1Label.setFont(new Font("Arial", Font.BOLD, 16));
        subTitle1Label.setBounds(0, 105, windowWidth, 30);
        panel.add(subTitle1Label);

        Object[][] iterativeData = {};

        String[] iterativeColumnNames = {};

        iterativeMethodsTable = new JTable(iterativeData, iterativeColumnNames);
        iterativeMethodsTable.setFont(new Font("Arial", Font.PLAIN, 16));
        iterativeMethodsTable.setRowHeight(30);
        iterativeMethodsTable.setBackground(new Color(220,220,220));
        iterativeMethodsTable.setBounds(0, 140, windowWidth, 120);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 1; i < iterativeMethodsTable.getColumnCount(); i++) {
            iterativeMethodsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        iterativeMethodsTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        iterativeMethodsTable.setGridColor(Color.BLACK);

        JTableHeader header = iterativeMethodsTable.getTableHeader();
        header.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        panel.add(iterativeMethodsTable);

        JLabel subTitle2Label = new JLabel("<html><body style='text-align: center'>Metoda Rekurencyjna Romberga", SwingConstants.CENTER);
        subTitle2Label.setFont(new Font("Arial", Font.BOLD, 16));
        subTitle2Label.setBounds(0, 270, windowWidth, 30);
        panel.add(subTitle2Label);

        Object[][] recursiveData = {};

        String[] RecursiveColumnNames = {};

        recursiveMethodTable = new JTable(recursiveData, RecursiveColumnNames);
        recursiveMethodTable.setFont(new Font("Arial", Font.PLAIN, 16));
        recursiveMethodTable.setRowHeight(20);
        recursiveMethodTable.setBackground(new Color(220,220,220));
        recursiveMethodTable.setBounds(0, 310, windowWidth, 320);

        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < recursiveMethodTable.getColumnCount(); i++) {
            recursiveMethodTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        recursiveMethodTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        recursiveMethodTable.setGridColor(Color.BLACK);

        header = recursiveMethodTable.getTableHeader();
        header.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        panel.add(recursiveMethodTable);

        timeLabel = new JLabel("<html><body style='text-align: center'>Całkowity czas całkowania: 0,000s", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        timeLabel.setBounds(0, 660, windowWidth, 30);
        panel.add(timeLabel);

        JButton exitButton = new JButton("POWRÓT");
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.setBounds(windowWidth/2-(windowWidth/3)/2, 710, windowWidth/3, 25);
        exitButton.setOpaque(true);
        exitButton.setContentAreaFilled(true);
        exitButton.setBorderPainted(false);
        exitButton.setFocusPainted(false);
        exitButton.setBackground(new Color(10,100,255));
        exitButton.setForeground(Color.white);
        panel.add(exitButton);

        exitButton.addActionListener(e -> System.exit(0));

        add(panel);
    }
}
