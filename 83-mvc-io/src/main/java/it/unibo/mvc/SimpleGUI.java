package it.unibo.mvc;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;


/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUI {

    private static final String TITLE = "SimpleGUI";
    private static final int PROPORTION = 2;

    private final JFrame frame = new JFrame(TITLE);
    private final Controller controller;

    /**
     * Creates a new {@link SimpleGUI}.
     * 
     * @param c the controller
     */
    @SuppressFBWarnings(
        value = {"EI_EXPOSE_REP2"},
        justification = "The controller is projected to work in this way."
    )
    public SimpleGUI(final Controller c) {
        this.controller = c;

        final JPanel panel = new JPanel(new BorderLayout());
        final JTextField field = new JTextField();
        panel.add(field, BorderLayout.NORTH);
        final JTextArea area = new JTextArea();
        area.setEditable(false);
        panel.add(area, BorderLayout.CENTER);
        final JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.LINE_AXIS));
        panel.add(southPanel, BorderLayout.SOUTH);
        final JButton print = new JButton("Print");
        southPanel.add(print);
        final JButton show = new JButton("Show history");
        southPanel.add(show);
        this.frame.setContentPane(panel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*
         * Handlers
         */
        print.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                SimpleGUI.this.controller.setNextString(field.getText());
                SimpleGUI.this.controller.printCurrentString();
            }
        });
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final StringBuilder allText = new StringBuilder();
                final List<String> history = SimpleGUI.this.controller.getHistory();
                for (final String text : history) {
                    allText.append(text);
                    allText.append('\n');
                }
                if (!history.isEmpty()) {
                    allText.deleteCharAt(allText.length() - 1);
                }
                area.setText(allText.toString());
            }
        });

        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int screenWidth = (int) screen.getWidth();
        final int screenHeight = (int) screen.getHeight();
        this.frame.setSize(screenWidth / PROPORTION, screenHeight / PROPORTION);
        this.frame.setLocationByPlatform(true);
    }

    private void display() {
        this.frame.setVisible(true);
    }

    /**
     * Launches the application.
     * 
     * @param args ingored
     */
    public static void main(final String[] args) {
        new SimpleGUI(new SimpleController()).display();
    }
}
