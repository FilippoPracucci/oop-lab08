package it.unibo.mvc;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUIWithFileChooser {

    private static final String TITLE = "My second Java graphical interface";
    private static final int PROPORTION = 3;

    private final JFrame frame = new JFrame(TITLE);

    /**
     * Creates a new SimpleGuiWithFileChooser.
     */
    public SimpleGUIWithFileChooser() {
        final JPanel mainPanel = new JPanel(new BorderLayout());
        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JTextArea area = new JTextArea();
        mainPanel.add(area, BorderLayout.CENTER);
        final JButton save = new JButton("Save");
        mainPanel.add(save, BorderLayout.SOUTH);

        final JPanel upperPanel = new JPanel(new BorderLayout());
        mainPanel.add(upperPanel, BorderLayout.NORTH);
        final JTextField field = new JTextField();
        upperPanel.add(field, BorderLayout.CENTER);
        field.setEditable(false);
        final Controller controller = new Controller();
        field.setText(controller.getCurrentPath());
        final JButton browse = new JButton("Browse...");
        upperPanel.add(browse, BorderLayout.LINE_END);
        /*
         * Handlers
         */
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    controller.writeOnFile(area.getText());
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(frame, e1, "Error", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace(); //NOPMD: allowed because this is an exercise
                }
            }
        });
        browse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final JFileChooser fileChooser = new JFileChooser("Choose where to save");
                fileChooser.setSelectedFile(controller.getCurrentFile());
                final int result = fileChooser.showSaveDialog(frame);
                switch (result) {
                    case JFileChooser.APPROVE_OPTION :
                        controller.setCurrentFile(fileChooser.getSelectedFile());
                        field.setText(controller.getCurrentPath());
                        break;
                    case JFileChooser.CANCEL_OPTION :
                        break;
                    default :
                        JOptionPane.showMessageDialog(frame, result, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void display() {
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int screenWidth = (int) screen.getWidth();
        final int screenHeight = (int) screen.getHeight();
        this.frame.setSize(screenWidth / PROPORTION, screenHeight / PROPORTION);

        this.frame.setLocationByPlatform(true);
        this.frame.setVisible(true);
    }
    /**
     * Launches the application.
     * 
     * @param args ignored
     */
    public static void main(final String[] args) {
        new SimpleGUIWithFileChooser().display();
    }
}
