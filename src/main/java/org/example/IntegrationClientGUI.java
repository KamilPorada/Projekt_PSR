package org.example;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class IntegrationClientGUI extends JFrame {
    private JTextField fromTextField, toTextField, partitionsTextField, functionTextField;
    private JComboBox<String> methodComboBox;
    private JLabel resultLabel, timeLabel, titleLabel, titleEquationLabel, equationLabel, methodLabel, rangeLabel, fromRangeLabel, toRangeLabel, partitionsLabel, progressLabel, integrationTableLengthLabel;
    private JButton createFunctionButton, calculateButton, resetButton, exitButton;
    private JProgressBar progressBar;

    private JSlider integrationTableLength;

    private int progress = 0;

    private String stringFunction, mathFunction;

    public IntegrationClientGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension d = kit.getScreenSize();
        int windowWidth = d.width/2;
        int windowHeight = 3*d.height/5;
        setBounds(d.width/4,d.height/5,windowWidth,windowHeight);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(220,220,220));
        panel.setLayout(null);

        titleLabel = new JLabel("<html><body style='text-align: center'>KALKULATOR CAŁKOWANIA NUMERYCZNEGO", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.CENTER_BASELINE, 18));
        titleLabel.setBounds(0, 0, windowWidth, 30);
        panel.add(titleLabel);

        titleEquationLabel = new JLabel("<html><body style='text-align: center'>Wzór funkcji:", SwingConstants.CENTER);
        titleEquationLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        titleEquationLabel.setBounds(0, 30, windowWidth, 20);
        panel.add(titleEquationLabel);

        equationLabel = new JLabel("<html><body style='text-align: center'>f(x)=.................................", SwingConstants.CENTER);
        equationLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        equationLabel.setBounds(0, 50, windowWidth, 20);
        panel.add(equationLabel);

        createFunctionButton = new JButton("Stwórz wzór fukcji");
        createFunctionButton.setFont(new Font("Arial", Font.BOLD, 16));
        createFunctionButton.setBounds(d.width/6, 80, windowWidth/3, 25);
        createFunctionButton.setOpaque(true);
        createFunctionButton.setContentAreaFilled(true);
        createFunctionButton.setBorderPainted(false);
        createFunctionButton.setFocusPainted(false);
        createFunctionButton.setBackground(new Color(10,100,255));
        createFunctionButton.setForeground(Color.white);
        panel.add(createFunctionButton);

        methodLabel = new JLabel("<html><body style='text-align: right'>Metoda całkowania numerycznego:", SwingConstants.RIGHT);
        methodLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        methodLabel.setBounds(0, 130, windowWidth/2-10, 20);
        panel.add(methodLabel);

        methodComboBox = new JComboBox<>(new String[]{"Metoda prostokątów (iteracyjna)", "Metoda trapezów (iteracyjna)", "Metoda parabol (iteracyjna)", "Metoda Romberga (rekurencyjna)", "Wszystkie"});
        methodComboBox.setBounds(windowWidth/2+20,130,windowWidth/2-100,20);
        panel.add(methodComboBox);

        rangeLabel = new JLabel("<html><body style='text-align: center'>Zakres całkowania:", SwingConstants.CENTER);
        rangeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        rangeLabel.setBounds(0, 170, windowWidth, 20);
        panel.add(rangeLabel);

        toRangeLabel = new JLabel("<html><body style='text-align: right'>Górny zakres całkowania:", SwingConstants.RIGHT);
        toRangeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        toRangeLabel.setBounds(0, 200, windowWidth/2+30, 20);
        panel.add(toRangeLabel);

        toTextField = new JTextField();
        toTextField.setBounds(windowWidth/2+80,200,100,20);
        panel.add(toTextField);

        fromRangeLabel = new JLabel("<html><body style='text-align: right'>Dolny zakres całkowania:", SwingConstants.RIGHT);
        fromRangeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        fromRangeLabel.setBounds(0, 230, windowWidth/2+30, 20);
        panel.add(fromRangeLabel);

        fromTextField = new JTextField();
        fromTextField.setBounds(windowWidth/2+80,230,100,20);
        panel.add(fromTextField);

        partitionsLabel= new JLabel("<html><body style='text-align: right'>Liczba podziałów:", SwingConstants.RIGHT);
        partitionsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        partitionsLabel.setBounds(0, 260, windowWidth/2+30, 20);
        panel.add(partitionsLabel);

        integrationTableLengthLabel= new JLabel("<html><body style='text-align: right'>1", SwingConstants.RIGHT);
        integrationTableLengthLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        integrationTableLengthLabel.setBounds(390, 260, 20, 20);
        integrationTableLengthLabel.setVisible(false);
        panel.add(integrationTableLengthLabel);

        partitionsTextField = new JTextField();
        partitionsTextField.setBounds(windowWidth/2+80,260,100,20);
        panel.add(partitionsTextField);

        integrationTableLength = new JSlider(1,15,1);

        integrationTableLength.setBounds(windowWidth/2+80,260,100,20);
        integrationTableLength.setVisible(false);
        panel.add(integrationTableLength);

        calculateButton = new JButton("Oblicz");
        calculateButton.setFont(new Font("Arial", Font.BOLD, 16));
        calculateButton.setBounds(d.width/6, 300, windowWidth/3, 25);
        calculateButton.setOpaque(true);
        calculateButton.setContentAreaFilled(true);
        calculateButton.setBorderPainted(false);
        calculateButton.setFocusPainted(false);
        calculateButton.setBackground(new Color(10,100,255));
        calculateButton.setForeground(Color.white);
        panel.add(calculateButton);

        progressBar = new JProgressBar(0, 100);
        progressBar.setBounds(10,340,windowWidth-20,30);
        progressBar.setBackground(new Color(10,100,255));
        panel.add(progressBar);

        progressLabel= new JLabel("<html><body style='text-align: center'>0%", SwingConstants.CENTER);
        progressLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        progressLabel.setBounds(10, 360, windowWidth-20, 20);
        panel.add(progressLabel);

        resultLabel= new JLabel("<html><body style='text-align: center'>Przybliżony wynik≈", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        resultLabel.setBounds(0, 390, windowWidth, 20);
        panel.add(resultLabel);

        timeLabel= new JLabel("<html><body style='text-align: center'>Czas całkowania:", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        timeLabel.setBounds(0, 420, windowWidth, 20);
        panel.add(timeLabel);

        resetButton = new JButton("Resetuj pola");
        resetButton.setFont(new Font("Arial", Font.BOLD, 16));
        resetButton.setBounds(d.width/15, 460, windowWidth/3, 25);
        resetButton.setOpaque(true);
        resetButton.setContentAreaFilled(true);
        resetButton.setBorderPainted(false);
        resetButton.setFocusPainted(false);
        resetButton.setBackground(new Color(10,100,255));
        resetButton.setForeground(Color.white);
        panel.add(resetButton);

        exitButton = new JButton("Zakończ program");
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.setBounds(windowWidth/2, 460, windowWidth/3, 25);
        exitButton.setOpaque(true);
        exitButton.setContentAreaFilled(true);
        exitButton.setBorderPainted(false);
        exitButton.setFocusPainted(false);
        exitButton.setBackground(new Color(10,100,255));
        exitButton.setForeground(Color.white);
        panel.add(exitButton);

        createFunctionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFunction();
            }
        });

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    performIntegration();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        methodComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(methodComboBox.getSelectedIndex() == 3){
                    partitionsLabel.setText("<html><body style='text-align: right'>Długość tablicy całkowej:");
                    partitionsTextField.setVisible(false);
                    integrationTableLength.setVisible(true);
                    integrationTableLengthLabel.setVisible(true);
                }
                else{
                    partitionsLabel.setText("<html><body style='text-align: right'>Liczba podziałów:");
                    partitionsTextField.setVisible(true);
                    integrationTableLength.setVisible(false);
                    integrationTableLengthLabel.setVisible(false);
                }
                panel.revalidate();
                panel.repaint();
            }
        });

        integrationTableLength.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                integrationTableLengthLabel.setText(String.valueOf(integrationTableLength.getValue()));
            }
        });

        add(panel);

    }

    private void createFunction() {
        FunctionCreationDialog dialog = new FunctionCreationDialog(this);
        dialog.setVisible(true);

        stringFunction = dialog.getStringFunction();
        mathFunction = dialog.getMathFunction();
        equationLabel.setText(stringFunction);

    }

    private void performIntegration() {
        try {
            Socket socket;
            double a = Double.parseDouble(fromTextField.getText());
            double b = Double.parseDouble(toTextField.getText());
            int option = methodComboBox.getSelectedIndex() + 1;
            int n = option == 4 ? integrationTableLength.getValue() : Integer.parseInt(partitionsTextField.getText());

            if(option == 4){
                socket = new Socket("localhost", 1000);
            }
            else{
                socket = new Socket("localhost", 3000);
            }


            ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());


            outToServer.writeInt(option);
            outToServer.flush();

            outToServer.writeDouble(a);
            outToServer.writeDouble(b);
            if(option == 4){
                outToServer.writeInt(Math.round(n/2));
                outToServer.writeInt(n-Math.round(n/2));
            }
            else{
                outToServer.writeInt(n);
            }
            outToServer.flush();

            outToServer.writeObject(mathFunction);
            outToServer.flush();

            SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
                double[] rectangle = new double[7];
                double[] trapeze = new double[7];
                double[] parabolas = new double[7];
                double[][] romberg = new double[16][16];

                long startTime, endTime;
                @Override
                protected Void doInBackground() throws Exception {
                    if(option == 1 || option == 2){
                        for (int i = 0; i < n; i++) {
                            progress = inFromServer.readInt();
                            int percentage = (int) (((double) (progress + 1) / n) * 100);
                            publish(percentage);
                        }
                    }
                    else if(option == 3){
                        int x = n*2;
                        for (int i = 1; i < x; i++) {
                            progress = inFromServer.readInt();
                            int percentage = (int) (((double) (progress + 1) / x) * 100);
                            publish(percentage);
                        }
                    }
                    else if(option == 4){
                        int[] counter = {1,7,27,81,213,519,1207,2725,6033,13179,28515,61257,130861,278287,589551};

                        int i =0;
                        String tik = "";
                        for(;;){
                            tik = (String) inFromServer.readObject();

                            if(tik.compareTo("Stop") == 0)
                                break;
                            int percentage = (int) (((double) i / counter[n-1]) * 100);
                            publish(percentage);
                            i++;
                        }
                    }
                    else if(option == 5){
                        startTime = System.nanoTime();
                        String tikr = "";
                        int tik;
                        //        int counter = 589551 + 4444433;
                        int counter = 589551 + 444434;
                        int c =0;
                        int percentage = 0;

                        for(;;){
                            tik = inFromServer.readInt();
                            if(tik == -1)
                                break;
                            percentage = (int) (((double) c / counter) * 100);
                            publish(percentage);
                            c++;
                        }
                        rectangle = (double[]) inFromServer.readObject();
                        trapeze = (double[]) inFromServer.readObject();
                        parabolas = (double[]) inFromServer.readObject();

                        inFromServer.readLong();

                        Socket socket = new Socket("localhost", 1000);

                        ObjectOutputStream outToRecursiveServer = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream inFromRecursiveServer = new ObjectInputStream(socket.getInputStream());

                        outToRecursiveServer.writeInt(option);
                        outToRecursiveServer.writeDouble(a);
                        outToRecursiveServer.writeDouble(b);
                        outToRecursiveServer.writeInt(10);
                        outToRecursiveServer.writeInt(5);
                        outToRecursiveServer.writeObject(mathFunction);
                        outToRecursiveServer.flush();


                        for(;;){
                            tikr = (String) inFromRecursiveServer.readObject();
                            if(tikr.compareTo("Stop") == 0){
                                System.out.println("Stop");
                                break;
                            }
                            percentage = (int) (((double) c / counter) * 100);
                            publish(percentage);
                            c++;
                        }

                        romberg = (double[][]) inFromRecursiveServer.readObject();
//                        for(int i=0;i<romberg.length;i++){
//                            for(int j=0; j<romberg.length; j++){
//                                System.out.print(romberg[i][j] + "    ");
//                            }
//                            System.out.println();
//                        }
                        inFromRecursiveServer.readLong();
                        endTime = System.nanoTime();
                    }
                    return null;
                }

                @Override
                protected void process(java.util.List<Integer> chunks) {
                        for (int percentage : chunks) {
                            progressBar.setValue(percentage);
                            progressLabel.setText("<html><body style='text-align: center'>" + percentage + "%");
                        }
                }

                @Override
                protected void done() {
                    if(option!=5) {
                        try {
                            double result = inFromServer.readDouble();
                            String roundedResultAsString = String.format("%.4f", result);
                            resultLabel.setText("Wynik działania: " + roundedResultAsString);
                            long elapsedTimeInNanoseconds = inFromServer.readLong();
                            timeLabel.setText("Czas całkowania: " + formatElapsedTimeInSeconds(elapsedTimeInNanoseconds));
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else{
                        Socket socket = null;
                        ObjectOutputStream outToSummaryServer;
                        ObjectInputStream inFromSummaryServer;
                        try {
                            socket = new Socket("localhost", 4000);
                            outToSummaryServer = new ObjectOutputStream(socket.getOutputStream());
                            inFromSummaryServer = new ObjectInputStream(socket.getInputStream());

                            outToSummaryServer.writeObject(rectangle);
                            outToSummaryServer.writeObject(trapeze);
                            outToSummaryServer.writeObject(parabolas);
                            outToSummaryServer.writeObject(romberg);
                            outToSummaryServer.writeObject(formatElapsedTimeInSeconds(endTime-startTime));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                    }
                }
            };

            worker.execute();

        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
        }
    }


    private void resetFields() {
        fromTextField.setText("");
        toTextField.setText("");
        partitionsTextField.setText("");
        resultLabel.setText("Wynik działania:");
        timeLabel.setText("Czas całkowania:");
        progressLabel.setText("<html><body style='text-align: center'> 0%");
        progressBar.setValue(0);
    }

    private static String formatElapsedTimeInSeconds(long nanoseconds) {
        double seconds = nanoseconds / 1_000_000_000.0;
        return String.format("%.3fs", seconds);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new IntegrationClientGUI().setVisible(true));
    }
}


