import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {

    private static boolean isRunning = false;
    private static boolean isInfinite = false;
    public static boolean isNotStop = true;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Clicker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.setAlwaysOnTop(true);
        JPanel panel = new JPanel();

        JTextField inputDelayBeforeStart = new JTextField("3", 10);
        JTextField inputCountClick = new JTextField("10", 10);
        JTextField inputDelay = new JTextField("100",10);
        JCheckBox infiniteCheckBox = new JCheckBox("Infinite");
        JCheckBox alwaysOnTOpCheckBox = new JCheckBox("Always On top", true);
        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        stopButton.setEnabled(false);


        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    System.out.println("Pressed ENTER");
                    isNotStop = false;
                }
                return false;
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRunning) {
                    if (infiniteCheckBox.isSelected() && (inputCountClick.getText().isEmpty() || !isNumeric(inputCountClick.getText()))) {
                        inputCountClick.setText("1");
                    }
                    if (inputCountClick.getText().isEmpty() || inputDelay.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please enter values for Count clicks and Delay.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (!isNumeric(inputCountClick.getText()) || !isNumeric(inputDelay.getText())) {
                        JOptionPane.showMessageDialog(null, "Please enter valid numeric values for Count clicks and Delay.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!isNotStop) {
                        isNotStop = true;
                    }

                        isRunning = true;
                        new Thread(() -> {
                            System.out.println("Start button clicked!");
                            startButton.setEnabled(false);
                            inputCountClick.setEnabled(false);
                            inputDelay.setEnabled(false);
                            inputDelayBeforeStart.setEnabled(false);
                            infiniteCheckBox.setEnabled(false);


                            try {
                                Thread.sleep(Integer.parseInt(inputDelayBeforeStart.getText()) * 1000);
                            } catch (InterruptedException ex) {
                                throw new RuntimeException(ex);
                            }

                            stopButton.setEnabled(true);
                            Clicker.clicker(Integer.parseInt(inputCountClick.getText()), Integer.parseInt(inputDelay.getText()), infiniteCheckBox.isSelected());

                            isRunning = false;
                            isNotStop = true;

                            inputDelay.setEnabled(true);
                            inputDelayBeforeStart.setEnabled(true);
                            infiniteCheckBox.setEnabled(true);
                            if (!infiniteCheckBox.isSelected()) {
                                inputCountClick.setEnabled(true);
                            }
                            startButton.setEnabled(true);
                            stopButton.setEnabled(false);

                        }).start();
                }
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isNotStop = false;
            }
        });

        infiniteCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isEnabled = infiniteCheckBox.isSelected();
                inputCountClick.setEnabled(!isEnabled);
            }
        });

        alwaysOnTOpCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isEnabled = alwaysOnTOpCheckBox.isSelected();
                frame.setAlwaysOnTop(isEnabled);
            }
        });

        panel.add(new JLabel("Count clicks:"));
        panel.add(inputCountClick);
        panel.add(new JLabel("Delay:"));
        panel.add(inputDelay);
        panel.add(infiniteCheckBox);

        panel.add(startButton);
        panel.add(stopButton);

        panel.add(new JLabel("Delay before start (Sec):"));
        panel.add(inputDelayBeforeStart, BorderLayout.SOUTH);
        panel.add(alwaysOnTOpCheckBox);

        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(500, 200);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
    }

    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?"); // Регулярное выражение для числа
    }
}
