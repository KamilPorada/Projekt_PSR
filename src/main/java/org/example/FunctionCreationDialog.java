package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class FunctionCreationDialog extends JDialog {

    private JLabel dialogTitleLabel, dialogFunction, dialogTitleInstruction, dialogTitleFunction, dialogTitleOperands, dialogInstruction;
    private JLabel dialogPolynominale, dialogSquare, dialogSinus, dialogCosinus, dialogTangens, dialogCotangens, dialogPostPostfixSinus, dialogPostPostfixCosinus, dialogPostPostfixTangens, dialogPostPostfixCotngens;
    private JButton dialogAddPolyminale, dialogAddSquare, dialogAddSinus, dialogAddCosinus, dialogAddTangens, dialogAddCotangens, dialogAdding, dialogSubstraction, dialogMultiplication, dialogDividing, dialogAccept, dialogReset;
    private JTextField dialogPrefixPolynominale, dialogPostfixPolynominale, dialogPrefixSquare, dialogPostfixSquare, dialogPrefixSinus, dialogPostfixSinus,
            dialogPrefixCosinus, dialogPostfixCosinus, dialogPrefixTangens, dialogPostfixTangens, dialogPrefixCotangens, dialogPostfixCotangens;

    private boolean isOperand = false;

    private String mathFunction = "";

    public String getStringFunction() {
        return dialogFunction.getText();
    }

    public String getMathFunction() {
        return mathFunction;
    }

    public FunctionCreationDialog(JFrame parent) {
        super(parent, "Stwórz wzór funkcji", true);
        final String[] function = {"f(x)="};

        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension d = t.getScreenSize();
        int dialogWidth = 2*d.width/5;
        int dialogHeight = d.height/2;
        setSize(dialogWidth, dialogHeight);
        setLocationRelativeTo(parent);
        setBackground(new Color(220,220,220));


        JPanel panel = new JPanel();
        panel.setLayout(null);

        dialogTitleLabel = new JLabel("<html><body style='text-align: center'>Wzór funkcji:", SwingConstants.CENTER);
        dialogTitleLabel.setFont(new Font("Arial", Font.CENTER_BASELINE, 18));
        dialogTitleLabel.setBounds(0, 0, dialogWidth, 30);
        panel.add(dialogTitleLabel);

        dialogFunction = new JLabel("<html><body style='text-align: center'>f(x)=.................................", SwingConstants.CENTER);
        dialogFunction.setFont(new Font("Arial", Font.CENTER_BASELINE, 16));
        dialogFunction.setBounds(0, 30, dialogWidth, 30);
        panel.add(dialogFunction);

        dialogTitleFunction = new JLabel("<html><body style='text-align: center'>Podstawowe funkcje matematyczne:", SwingConstants.CENTER);
        dialogTitleFunction.setFont(new Font("Arial", Font.CENTER_BASELINE, 16));
        dialogTitleFunction.setBounds(dialogWidth/4, 60, dialogWidth/2, 30);
        panel.add(dialogTitleFunction);

        dialogAddPolyminale = new JButton("+");
        dialogAddPolyminale.setFont(new Font("Arial", Font.PLAIN, 18));
        dialogAddPolyminale.setBounds(230, 100, 20, 20);
        dialogAddPolyminale.setBorder(BorderFactory.createLineBorder(new Color(10,100,255),2,true));
        panel.add(dialogAddPolyminale);

        dialogPrefixPolynominale = new JTextField();
        dialogPrefixPolynominale.setBounds(260,100,30,20);
        dialogPrefixPolynominale.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(dialogPrefixPolynominale);

        dialogPolynominale = new JLabel("<html><body style='text-align: center'>x", SwingConstants.CENTER);
        dialogPolynominale.setFont(new Font("Arial", Font.CENTER_BASELINE, 16));
        dialogPolynominale.setBounds(287, 100, 20, 20);
        panel.add(dialogPolynominale);

        dialogPostfixPolynominale = new JTextField();
        dialogPostfixPolynominale.setBounds(305,95,30,15);
        dialogPostfixPolynominale.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(dialogPostfixPolynominale);

        dialogAddSquare = new JButton("+");
        dialogAddSquare.setFont(new Font("Arial", Font.PLAIN, 18));
        dialogAddSquare.setBounds(230, 140, 20, 20);
        dialogAddSquare.setBorder(BorderFactory.createLineBorder(new Color(10,100,255),2,true));
        panel.add(dialogAddSquare);

        dialogPrefixSquare = new JTextField();
        dialogPrefixSquare.setBounds(260,140,30,20);
        dialogPrefixSquare.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(dialogPrefixSquare);

        dialogSquare = new JLabel("<html><body style='text-align: center'>√", SwingConstants.CENTER);
        dialogSquare.setFont(new Font("Arial", Font.CENTER_BASELINE, 16));
        dialogSquare.setBounds(287, 140, 20, 20);
        panel.add(dialogSquare);

        dialogPostfixSquare = new JTextField();
        dialogPostfixSquare.setBounds(305,140,30,20);
        dialogPostfixSquare.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(dialogPostfixSquare);

        dialogAddSinus = new JButton("+");
        dialogAddSinus.setFont(new Font("Arial", Font.PLAIN, 18));
        dialogAddSinus.setBounds(230, 180, 20, 20);
        dialogAddSinus.setBorder(BorderFactory.createLineBorder(new Color(10,100,255),2,true));
        panel.add(dialogAddSinus);

        dialogPrefixSinus = new JTextField();
        dialogPrefixSinus.setBounds(260,180,30,20);
        dialogPrefixSinus.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(dialogPrefixSinus);

        dialogSinus = new JLabel("<html><body style='text-align: center'>sin(", SwingConstants.CENTER);
        dialogSinus.setFont(new Font("Arial", Font.CENTER_BASELINE, 16));
        dialogSinus.setBounds(293, 180, 30, 20);
        panel.add(dialogSinus);

        dialogPostfixSinus = new JTextField();
        dialogPostfixSinus.setBounds(328,181,33,20);
        dialogPostfixSinus.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(dialogPostfixSinus);

        dialogPostPostfixSinus = new JLabel("<html><body style='text-align: center'>x)", SwingConstants.CENTER);
        dialogPostPostfixSinus.setFont(new Font("Arial", Font.CENTER_BASELINE, 16));
        dialogPostPostfixSinus.setBounds(352, 180, 30, 20);
        panel.add(dialogPostPostfixSinus);

        dialogAddCosinus = new JButton("+");
        dialogAddCosinus.setFont(new Font("Arial", Font.PLAIN, 18));
        dialogAddCosinus.setBounds(230, 220, 20, 20);
        dialogAddCosinus.setBorder(BorderFactory.createLineBorder(new Color(10,100,255),2,true));
        panel.add(dialogAddCosinus);

        dialogPrefixCosinus = new JTextField();
        dialogPrefixCosinus.setBounds(260,220,30,20);
        dialogPrefixCosinus.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(dialogPrefixCosinus);

        dialogCosinus = new JLabel("<html><body style='text-align: center'>cos(", SwingConstants.CENTER);
        dialogCosinus.setFont(new Font("Arial", Font.CENTER_BASELINE, 16));
        dialogCosinus.setBounds(293, 220, 33, 20);
        panel.add(dialogCosinus);

        dialogPostfixCosinus = new JTextField();
        dialogPostfixCosinus.setBounds(328,221,30,20);
        dialogPostfixCosinus.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(dialogPostfixCosinus);

        dialogPostPostfixCosinus = new JLabel("<html><body style='text-align: center'>x)", SwingConstants.CENTER);
        dialogPostPostfixCosinus.setFont(new Font("Arial", Font.CENTER_BASELINE, 16));
        dialogPostPostfixCosinus.setBounds(352, 220, 30, 20);
        panel.add(dialogPostPostfixCosinus);

        dialogAddTangens = new JButton("+");
        dialogAddTangens.setFont(new Font("Arial", Font.PLAIN, 18));
        dialogAddTangens.setBounds(230, 260, 20, 20);
        dialogAddTangens.setBorder(BorderFactory.createLineBorder(new Color(10,100,255),2,true));
        panel.add(dialogAddTangens);

        dialogPrefixTangens = new JTextField();
        dialogPrefixTangens.setBounds(260,260,30,20);
        dialogPrefixTangens.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(dialogPrefixTangens);

        dialogTangens = new JLabel("<html><body style='text-align: center'>tan(", SwingConstants.CENTER);
        dialogTangens.setFont(new Font("Arial", Font.CENTER_BASELINE, 16));
        dialogTangens.setBounds(293, 260, 33, 20);
        panel.add(dialogTangens);

        dialogPostfixTangens = new JTextField();
        dialogPostfixTangens.setBounds(328,261,30,20);
        dialogPostfixTangens.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(dialogPostfixTangens);

        dialogPostPostfixTangens = new JLabel("<html><body style='text-align: center'>x)", SwingConstants.CENTER);
        dialogPostPostfixTangens.setFont(new Font("Arial", Font.CENTER_BASELINE, 16));
        dialogPostPostfixTangens.setBounds(352, 260, 30, 20);
        panel.add(dialogPostPostfixTangens);

        dialogAddCotangens = new JButton("+");
        dialogAddCotangens.setFont(new Font("Arial", Font.PLAIN, 18));
        dialogAddCotangens.setBounds(230, 300, 20, 20);
        dialogAddCotangens.setBorder(BorderFactory.createLineBorder(new Color(10,100,255),2,true));
        panel.add(dialogAddCotangens);

        dialogPrefixCotangens = new JTextField();
        dialogPrefixCotangens.setBounds(260,300,30,20);
        dialogPrefixCotangens.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(dialogPrefixCotangens);

        dialogCotangens = new JLabel("<html><body style='text-align: center'>ctg(", SwingConstants.CENTER);
        dialogCotangens.setFont(new Font("Arial", Font.CENTER_BASELINE, 16));
        dialogCotangens.setBounds(293, 300, 33, 20);
        panel.add(dialogCotangens);

        dialogPostfixCotangens = new JTextField();
        dialogPostfixCotangens.setBounds(328,301,30,20);
        dialogPostfixCotangens.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(dialogPostfixCotangens);

        dialogPostPostfixCotngens = new JLabel("<html><body style='text-align: center'>x)", SwingConstants.CENTER);
        dialogPostPostfixCotngens.setFont(new Font("Arial", Font.CENTER_BASELINE, 16));
        dialogPostPostfixCotngens.setBounds(352, 300, 30, 20);
        panel.add(dialogPostPostfixCotngens);

        dialogTitleOperands = new JLabel("<html><body style='text-align: center'>Operatory arytmetyczne:", SwingConstants.CENTER);
        dialogTitleOperands.setFont(new Font("Arial", Font.CENTER_BASELINE, 16));
        dialogTitleOperands.setBounds(3*dialogWidth/4, 90, dialogWidth/4, 35);
        panel.add(dialogTitleOperands);

        dialogAdding = new JButton("+");
        dialogAdding.setFont(new Font("Arial", Font.PLAIN, 20));
        dialogAdding.setBounds(470, 130, 25, 25);
        dialogAdding.setBorder(BorderFactory.createLineBorder(new Color(10,100,255),2,true));
        dialogAdding.setEnabled(false);
        panel.add(dialogAdding);

        dialogSubstraction = new JButton("-");
        dialogSubstraction.setFont(new Font("Arial", Font.PLAIN, 20));
        dialogSubstraction.setBounds(510, 130, 25, 25);
        dialogSubstraction.setBorder(BorderFactory.createLineBorder(new Color(10,100,255),2,true));
        dialogSubstraction.setEnabled(false);
        panel.add(dialogSubstraction);

        dialogMultiplication = new JButton("×");
        dialogMultiplication.setFont(new Font("Arial", Font.PLAIN, 20));
        dialogMultiplication.setBounds(470, 160, 25, 25);
        dialogMultiplication.setBorder(BorderFactory.createLineBorder(new Color(10,100,255),2,true));
        dialogMultiplication.setEnabled(false);
        panel.add(dialogMultiplication);

        dialogDividing = new JButton("÷");
        dialogDividing.setFont(new Font("Arial", Font.PLAIN, 20));
        dialogDividing.setBounds(510, 160, 25, 25);
        dialogDividing.setBorder(BorderFactory.createLineBorder(new Color(10,100,255),2,true));
        dialogDividing.setEnabled(false);
        panel.add(dialogDividing);

        dialogTitleInstruction = new JLabel("<html><body style='text-align: center'>Instrukcja:", SwingConstants.CENTER);
        dialogTitleInstruction.setFont(new Font("Arial", Font.CENTER_BASELINE, 16));
        dialogTitleInstruction.setBounds(10, 90, dialogWidth/4, 35);
        panel.add(dialogTitleInstruction);

        dialogInstruction = new JLabel("<html><body style='text-align: center'>1.Wybierz jedną z funkcji matematycznych i uzupełnij ją odpowiednimi liczbami.<br>2.Wciśnij znak + obok danej funkcji, aby dodać daną unkcję do wzoru funkcji.<br>3.Wybierz jeden z operatorów arytmetycznych.<br>4.Dodawaj naprzemiennie stworzone funkcje oraz operatory arytmetyczne.<br>5.Zakończ wciskając przycisk Akceptuj lub wyczyść wzór funkcji wciskając przycisk Resetuj i twórz wzór funkcji od nowa!", SwingConstants.CENTER);
        dialogInstruction.setFont(new Font("Arial", Font.CENTER_BASELINE, 10));
        dialogInstruction.setBounds(10, 75, dialogWidth/4, 300);
        panel.add(dialogInstruction);

        dialogReset = new JButton("Resetuj pola");
        dialogReset.setFont(new Font("Arial", Font.BOLD, 16));
        dialogReset.setBounds(dialogWidth/8, 360, dialogWidth/3, 25);
        dialogReset.setOpaque(true);
        dialogReset.setContentAreaFilled(true);
        dialogReset.setBorderPainted(false);
        dialogReset.setFocusPainted(false);
        dialogReset.setBackground(new Color(10,100,255));
        dialogReset.setForeground(Color.white);
        panel.add(dialogReset);

        dialogAccept = new JButton("Akceptuj");
        dialogAccept.setFont(new Font("Arial", Font.BOLD, 16));
        dialogAccept.setBounds(dialogWidth/2+dialogWidth/15, 360, dialogWidth/3, 25);
        dialogAccept.setOpaque(true);
        dialogAccept.setContentAreaFilled(true);
        dialogAccept.setBorderPainted(false);
        dialogAccept.setFocusPainted(false);
        dialogAccept.setBackground(new Color(10,100,255));
        dialogAccept.setForeground(Color.white);
        panel.add(dialogAccept);

        add(panel);

        dialogAddPolyminale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(dialogPrefixPolynominale.getText().compareTo("") != 0 && dialogPostfixPolynominale.getText().compareTo("") != 0 && Integer.parseInt(dialogPrefixPolynominale.getText()) != 0){
                    if(Integer.parseInt(dialogPrefixPolynominale.getText()) == 1 && Integer.parseInt(dialogPostfixPolynominale.getText()) > 1){
                        function[0] = function[0] + "x<sup>" + dialogPostfixPolynominale.getText() + "</sup>";
                    }
                    else if(Integer.parseInt(dialogPostfixPolynominale.getText()) == 0){
                        function[0] = function[0] + dialogPrefixPolynominale.getText();
                    }
                    else if(Integer.parseInt(dialogPostfixPolynominale.getText()) == 1 && Integer.parseInt(dialogPrefixPolynominale.getText()) != 1){
                        function[0] = function[0] + dialogPrefixPolynominale.getText() + "x";
                    }
                    else if(Integer.parseInt(dialogPrefixPolynominale.getText()) == 1 && Integer.parseInt(dialogPostfixPolynominale.getText()) == 1){
                        function[0] = function[0] + "x";
                    }
                    else{
                        function[0] = function[0] + dialogPrefixPolynominale.getText() + "x<sup>" + dialogPostfixPolynominale.getText() + "</sup>";
                    }
                    dialogFunction.setText("<html><body style='text-align: center'>" + function[0] + "</body></html>");
                    mathFunction = mathFunction + dialogPrefixPolynominale.getText() + "*x^" + dialogPostfixPolynominale.getText();
                    isOperand=true;
                    checkControls();
                    resetFields();
                }
            }
        });

        dialogAddSquare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dialogPrefixSquare.getText().compareTo("") != 0 && dialogPostfixSquare.getText().compareTo("") != 0 && Integer.parseInt(dialogPrefixSquare.getText()) != 0) {
                    if(Integer.parseInt(dialogPrefixSquare.getText()) == 1 && Integer.parseInt(dialogPostfixSquare.getText()) > 1){
                        function[0] = function[0] +  "√" + dialogPostfixSquare.getText() + "x";
                    }
                    else if(Integer.parseInt(dialogPostfixSquare.getText()) == 0){
                        function[0] = function[0] + dialogPrefixSquare.getText();
                    }
                    else if(Integer.parseInt(dialogPostfixSquare.getText()) == 1 && Integer.parseInt(dialogPrefixSquare.getText()) != 1){
                        function[0] = function[0] + dialogPrefixSquare.getText() + "√x";
                    }
                    else if(Integer.parseInt(dialogPrefixSquare.getText()) == 1 && Integer.parseInt(dialogPostfixSquare.getText()) == 1){
                        function[0] = function[0] + "√x";
                    }
                    else{
                        function[0] = function[0] + dialogPrefixSquare.getText() + "√" + dialogPostfixSquare.getText() + "x";
                    }
                    dialogFunction.setText("<html><body style='text-align: center'>" + function[0] + "</body></html>");
                    mathFunction = mathFunction + dialogPrefixSquare.getText() + "*sqrt(x*" + dialogPostfixSquare.getText() + ")";
                    isOperand = true;
                    checkControls();
                    resetFields();
                }
            }
        });

        dialogAddSinus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dialogPrefixSinus.getText().compareTo("") != 0 && dialogPostfixSinus.getText().compareTo("") != 0 && Integer.parseInt(dialogPrefixSinus.getText()) != 0) {
                    if(Integer.parseInt(dialogPrefixSinus.getText()) == 1 && Integer.parseInt(dialogPostfixSinus.getText()) > 1){
                        function[0] = function[0] +  "sin(" + dialogPostfixSinus.getText() + "x)";
                    }
                    else if(Integer.parseInt(dialogPostfixSinus.getText()) == 0){
                        function[0] = function[0] + dialogPrefixSinus.getText();
                    }
                    else if(Integer.parseInt(dialogPostfixSinus.getText()) == 1 && Integer.parseInt(dialogPrefixSinus.getText()) != 1){
                        function[0] = function[0] + dialogPrefixSinus.getText() + "sin(x)";
                    }
                    else if(Integer.parseInt(dialogPrefixSinus.getText()) == 1 && Integer.parseInt(dialogPostfixSinus.getText()) == 1){
                        function[0] = function[0] + "sin(x)";
                    }
                    else{
                        function[0] = function[0] + dialogPrefixSinus.getText() + "sin(" + dialogPostfixSinus.getText() + "x)";
                    }
                    dialogFunction.setText("<html><body style='text-align: center'>" + function[0] + "</body></html>");
                    mathFunction = mathFunction + dialogPrefixSinus.getText() + "*sin(x*" + dialogPostfixSinus.getText() + ")";
                    isOperand = true;
                    checkControls();
                    resetFields();
                }
            }
        });

        dialogAddCosinus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dialogPrefixCosinus.getText().compareTo("") != 0 && dialogPostfixCosinus.getText().compareTo("") != 0 && Integer.parseInt(dialogPrefixCosinus.getText()) != 0) {
                    if(Integer.parseInt(dialogPrefixCosinus.getText()) == 1 && Integer.parseInt(dialogPostfixCosinus.getText()) > 1){
                        function[0] = function[0] +  "cos(" + dialogPostfixCosinus.getText() + "x)";
                    }
                    else if(Integer.parseInt(dialogPostfixCosinus.getText()) == 0){
                        function[0] = function[0] + dialogPrefixCosinus.getText();
                    }
                    else if(Integer.parseInt(dialogPostfixCosinus.getText()) == 1 && Integer.parseInt(dialogPrefixCosinus.getText()) != 1){
                        function[0] = function[0] + dialogPrefixCosinus.getText() + "cos(x)";
                    }
                    else if(Integer.parseInt(dialogPrefixCosinus.getText()) == 1 && Integer.parseInt(dialogPostfixCosinus.getText()) == 1){
                        function[0] = function[0] + "cos(x)";
                    }
                    else{
                        function[0] = function[0] + dialogPrefixCosinus.getText() + "cos(" + dialogPostfixCosinus.getText() + "x)";
                    }
                    dialogFunction.setText("<html><body style='text-align: center'>" + function[0] + "</body></html>");
                    mathFunction = mathFunction + dialogPrefixCosinus.getText() + "*cos(x*" + dialogPostfixCosinus.getText() + ")";
                    isOperand = true;
                    checkControls();
                    resetFields();
                }
            }
        });

        dialogAddTangens.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dialogPrefixTangens.getText().compareTo("") != 0 && dialogPostfixTangens.getText().compareTo("") != 0 && Integer.parseInt(dialogPrefixTangens.getText()) != 0) {
                    if(Integer.parseInt(dialogPrefixTangens.getText()) == 1 && Integer.parseInt(dialogPostfixTangens.getText()) > 1){
                        function[0] = function[0] +  "tan(" + dialogPostfixTangens.getText() + "x)";
                    }
                    else if(Integer.parseInt(dialogPostfixTangens.getText()) == 0){
                        function[0] = function[0] + dialogPrefixTangens.getText();
                    }
                    else if(Integer.parseInt(dialogPostfixTangens.getText()) == 1 && Integer.parseInt(dialogPrefixTangens.getText()) != 1){
                        function[0] = function[0] + dialogPrefixTangens.getText() + "tan(x)";
                    }
                    else if(Integer.parseInt(dialogPrefixTangens.getText()) == 1 && Integer.parseInt(dialogPostfixTangens.getText()) == 1){
                        function[0] = function[0] + "tan(x)";
                    }
                    else{
                        function[0] = function[0] + dialogPrefixTangens.getText() + "tan(" + dialogPostfixTangens.getText() + "x)";
                    }                    
                    dialogFunction.setText("<html><body style='text-align: center'>" + function[0] + "</body></html>");
                    mathFunction = mathFunction + dialogPrefixTangens.getText() + "*tan(x*" + dialogPostfixTangens.getText() + ")";
                    isOperand = true;
                    checkControls();
                    resetFields();
                }
            }
        });

        dialogAddCotangens.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dialogPrefixCotangens.getText().compareTo("") != 0 && dialogPostfixCotangens.getText().compareTo("") != 0 && Integer.parseInt(dialogPrefixCotangens.getText()) != 0) {
                    if(Integer.parseInt(dialogPrefixCotangens.getText()) == 1 && Integer.parseInt(dialogPostfixCotangens.getText()) > 1){
                        function[0] = function[0] +  "ctg(" + dialogPostfixCotangens.getText() + "x)";
                    }
                    else if(Integer.parseInt(dialogPostfixCotangens.getText()) == 0){
                        function[0] = function[0] + dialogPrefixCotangens.getText();
                    }
                    else if(Integer.parseInt(dialogPostfixCotangens.getText()) == 1 && Integer.parseInt(dialogPrefixCotangens.getText()) != 1){
                        function[0] = function[0] + dialogPrefixCotangens.getText() + "ctg(x)";
                    }
                    else if(Integer.parseInt(dialogPrefixCotangens.getText()) == 1 && Integer.parseInt(dialogPostfixCotangens.getText()) == 1){
                        function[0] = function[0] + "ctg(x)";
                    }
                    else{
                        function[0] = function[0] + dialogPrefixCotangens.getText() + "ctg(" + dialogPostfixCotangens.getText() + "x)";
                    }
                    dialogFunction.setText("<html><body style='text-align: center'>" + function[0] + "</body></html>");
                    mathFunction = mathFunction + dialogPrefixCotangens.getText() + "*cot(x*" + dialogPostfixCotangens.getText() + ")";
                    isOperand = true;
                    checkControls();
                    resetFields();
                }
            }
        });

        dialogAdding.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                function[0] = function[0] + "+" ;
                dialogFunction.setText("<html><body style='text-align: center'>" + function[0] + "</body></html>");
                mathFunction = mathFunction + "+";
                isOperand=false;
                checkControls();
            }
        });

        dialogSubstraction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                function[0] = function[0] + "-" ;
                dialogFunction.setText("<html><body style='text-align: center'>" + function[0] + "</body></html>");
                mathFunction = mathFunction + "-";
                isOperand=false;
                checkControls();
            }
        });

        dialogMultiplication.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                function[0] = function[0] + "×" ;
                dialogFunction.setText("<html><body style='text-align: center'>" + function[0] + "</body></html>");
                mathFunction = mathFunction + "*";
                isOperand=false;
                checkControls();
            }
        });

        dialogDividing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                function[0] = function[0] + "÷" ;
                dialogFunction.setText("<html><body style='text-align: center'>" + function[0] + "</body></html>");
                mathFunction = mathFunction + "/";
                isOperand=false;
                checkControls();
            }
        });

        dialogReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isOperand = false;
                checkControls();
                resetFields();
                function[0] = "f(x)=";
                dialogFunction.setText("<html><body style='text-align: center'>f(x)=.................................");
            }
        });

        dialogAccept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }


    private void checkControls() {
        if(isOperand){
            dialogAddPolyminale.setEnabled(false);
            dialogAddSquare.setEnabled(false);
            dialogAddSinus.setEnabled(false);
            dialogAddCosinus.setEnabled(false);
            dialogAddTangens.setEnabled(false);
            dialogAddCotangens.setEnabled(false);
            dialogAdding.setEnabled(true);
            dialogSubstraction.setEnabled(true);
            dialogMultiplication.setEnabled(true);
            dialogDividing.setEnabled(true);
        }
        else{
            dialogAddPolyminale.setEnabled(true);
            dialogAddSquare.setEnabled(true);
            dialogAddSinus.setEnabled(true);
            dialogAddCosinus.setEnabled(true);
            dialogAddTangens.setEnabled(true);
            dialogAddCotangens.setEnabled(true);
            dialogAdding.setEnabled(false);
            dialogSubstraction.setEnabled(false);
            dialogMultiplication.setEnabled(false);
            dialogDividing.setEnabled(false);
        }
    }

    private void resetFields() {
        dialogPrefixPolynominale.setText("");
        dialogPostfixPolynominale.setText("");
        dialogPrefixSquare.setText("");
        dialogPostfixSquare.setText("");
        dialogPrefixSinus.setText("");
        dialogPostfixSinus.setText("");
        dialogPrefixCosinus.setText("");
        dialogPostfixCosinus.setText("");
        dialogPrefixTangens.setText("");
        dialogPostfixTangens.setText("");
        dialogPrefixCotangens.setText("");
        dialogPostfixCotangens.setText("");

    }

}