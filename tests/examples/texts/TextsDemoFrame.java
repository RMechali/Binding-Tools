/** 
 * This file is part of Binding Tools project.
 *
 * Binding Tools is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of 
 * the License, or (at your option) any later version.
 *
 * Binding Tools is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * and GNU Lesser General Public License along with Binding Tools project.
 * If not, see <http://www.gnu.org/licenses/>.
 **/
package examples.texts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import binding.BindingTools;
import binding.property.source.adapter.swing.JTextComponentSource;

/**
 * Example for text components.
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class TextsDemoFrame extends JFrame {

    /** Default UID **/
    private static final long serialVersionUID = 1L;

    /** Demo text area **/
    private JTextArea textArea;

    /** Demo text field **/
    private JTextField textField;

    /**
     * Constructor
     */
    public TextsDemoFrame() {
        super("Texts demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create components
        createComponents();

        // create frame content
        createContent();

        // install behavior control
        installBehavior();

        pack();
    }

    /**
     * Creates text components
     */
    private void createComponents() {
        textField = new JTextField();
        textArea = new JTextArea();
    }

    /**
     * Creates the frame content
     */
    private void createContent() {
        setLayout(new BorderLayout(0, 4));

        // add the text area
        textArea.setRows(10);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // add the text field
        add(textField, BorderLayout.SOUTH);

        // add the demo banner
        JLabel banner = new JLabel(
                "<html><b>Binding tools demo</b><br>Edit the text area : the text field will<br>get updated at same time.</html>");
        banner.setBackground(Color.white);
        banner.setOpaque(true);
        banner.setBorder(new CompoundBorder(new MatteBorder(0, 0, 1, 0,
                                                            Color.black), new EmptyBorder(new Insets(5, 10, 8, 10))));
        add(banner, BorderLayout.NORTH);
    }

    /**
     * Install frame behavior
     */
    private void installBehavior() {
        // for example, we bind here the text area in the text field. We can
        // not produce a reciprocal binding here because Swing refuses changes
        // while handling a document event
        BindingTools.createBinding(new JTextComponentSource(textArea),
                                   textField, "text");
    }

    /**
     * Demo main method : shows the main frame
     * 
     * @param args
     *            : application parameters (useless)
     */
    public static void main(String[] args) {
        // invoke in Swing thread
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                // display using the system look and feel
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }
                catch (Exception e) {
                    System.err.println(
                            "Could not install system look and feel. Switching for default Java look and feel : " + e);
                }

                TextsDemoFrame swingDemoFrame = new TextsDemoFrame();
                // center the frame on the screen
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                Dimension windowSize = swingDemoFrame.getSize();
                swingDemoFrame.setLocation(new Point(
                        (screenSize.width - windowSize.width) / 2,
                        (screenSize.height - windowSize.height) / 2));

                swingDemoFrame.setVisible(true);
            }
        });
    }
}
