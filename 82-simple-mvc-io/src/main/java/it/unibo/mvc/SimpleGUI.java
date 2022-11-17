package it.unibo.mvc;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUI {

    private static final String TITLE = "My first java graphical interface";
    private static final int PROPORTION = 3;

    private final JFrame frame = new JFrame(TITLE);

    /**
     * Creates a new SimpleGUI.
     */
    public SimpleGUI() {
        final JPanel panel = new JPanel(new BorderLayout());
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JTextArea area = new JTextArea();
        panel.add(area, BorderLayout.CENTER);
        final JButton save = new JButton("Save");
        panel.add(save, BorderLayout.SOUTH);

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    final Controller c = new Controller();
                    c.writeOnFile(area.getText());
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(frame, e1, "Error", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace(); //NOPMD: allowed because this is an exercise
                }
            }
        });
    }

    private void display() {
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int screenWidth = (int) screen.getWidth();
        final int screenHeight = (int) screen.getHeight();
        frame.setSize(screenWidth / PROPORTION, screenHeight / PROPORTION);

        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    /**
     * Launches the application.
     *
     * @param args ignored
     */
    public static void main(final String[] args) {
        new SimpleGUI().display();
    }
}
