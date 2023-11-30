package org.example;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;



public class IntegrationClientGUI extends JFrame {
    private JTextField fromTextField, toTextField, partitionsTextField, functionTextField;
    private JComboBox<String> methodComboBox;
    private JLabel resultLabel, timeLabel, titleLabel, titleEquationLabel, equationLabel, methodLabel, rangeLabel, fromRangeLabel, toRangeLabel, partitionsLabel, progressLabel;
    private JButton createFunctionButton, calculateButton, resetButton, exitButton;
    private JProgressBar progressBar;

    private int progress = 0;

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

        equationLabel = new JLabel("<html><body style='text-align: center'>f(x)=sin(x)", SwingConstants.CENTER);
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

        methodComboBox = new JComboBox<>(new String[]{"Metoda prostokątów", "Metoda trapezów", "Metoda parabol"});
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

        partitionsTextField = new JTextField();
        partitionsTextField.setBounds(windowWidth/2+80,260,100,20);
        panel.add(partitionsTextField);

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

        add(panel);
    }

    private void createFunction() {
        FunctionCreationDialog dialog = new FunctionCreationDialog(this);
        dialog.setVisible(true);

        // Pobierz wzór funkcji z okna modalnego
        String function = dialog.getFunction();
        if (function != null) {
            functionTextField.setText(function);
        }
    }

    private void performIntegration() {
        try {
            double a = Double.parseDouble(fromTextField.getText());
            double b = Double.parseDouble(toTextField.getText());
            int n = Integer.parseInt(partitionsTextField.getText());

            Socket socket = new Socket("localhost", 1245);
            ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());

            int option = methodComboBox.getSelectedIndex() + 1; // Index starts from 0
            outToServer.writeInt(option);
            outToServer.flush();

            outToServer.writeObject("1");
            outToServer.flush();

            outToServer.writeDouble(a);
            outToServer.writeDouble(b);
            outToServer.writeInt(n);
            outToServer.flush();

            SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
                @Override
                protected Void doInBackground() throws Exception {
                    if(option!=3){
                        for (int i = 0; i < n; i++) {
                            progress = inFromServer.readInt();
                            int percentage = (int) (((double) (progress + 1) / n) * 100);
                            publish(percentage);
                        }
                    }
                    else{
                        int x = n*2;
                        for (int i = 1; i < x; i++) {
                            progress = inFromServer.readInt();
                            int percentage = (int) (((double) (progress + 1) / x) * 100);
                            publish(percentage);
                        }
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
                    try {
                        double result = inFromServer.readDouble();
                        long elapsedTimeInNanoseconds = inFromServer.readLong();
                        double roundedResult = Math.round(result * 10000.0) / 10000.0;
                        resultLabel.setText("Wynik działania: " + roundedResult);
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

class FunctionCreationDialog extends JDialog {
    private JTextField functionTextField;

    public FunctionCreationDialog(JFrame parent) {
        super(parent, "Stwórz wzór funkcji", true);
        setSize(400, 200);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel functionLabel = new JLabel("Wzór funkcji:");
        functionTextField = new JTextField();

        JButton addButton = new JButton("Dodaj");
        JButton cancelButton = new JButton("Anuluj");

        panel.add(functionLabel);
        panel.add(functionTextField);
        panel.add(addButton);
        panel.add(cancelButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                functionTextField.setText(null);
                dispose();
            }
        });

        add(panel);
    }

    public String getFunction() {
        return functionTextField.getText();
    }
}
