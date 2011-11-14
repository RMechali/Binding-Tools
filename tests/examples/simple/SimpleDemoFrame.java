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
package examples.simple;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import binding.BindingTools;
import binding.property.source.adapter.swing.JFTFBindingSource;
import binding.property.source.adapter.swing.JSliderBindingSource;
import binding.property.source.adapter.swing.JSpinnerBindingSource;

/**
 * A proof of concept that can be used as tutorial for project new comers. The
 * example is based on common Swing components.
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class SimpleDemoFrame extends JFrame {

    /** Default UID **/
    private static final long serialVersionUID = 1L;

    /** Slider for the demo **/
    private JSlider slider;

    /** Spinner for the demo **/
    private JSpinner spinner;

    /** Formatted text field for the demo **/
    private JFormattedTextField formattedTextField;

    /** Progress bar for the demo **/
    private JProgressBar progressBar;

    /** Close button **/
    private JButton closeButton;

    /** Reset value button **/
    private JButton resetValueButton;

    /**
     * Constructor
     */
    public SimpleDemoFrame() {
        super("Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // create graphical components
        createComponents();
        // create frame content
        createContent();
        // install behavior control (demonstrates binding API)
        installBehavior();
        pack();
    }

    /**
     * Create graphical components used to demonstrate binding
     */
    private void createComponents() {
        slider = new JSlider(JSlider.VERTICAL, 0, 100, 0);
        spinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        formattedTextField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        formattedTextField.setColumns(8);
        progressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);

        closeButton = new JButton();
        resetValueButton = new JButton();
    }

    /**
     * Creates the frame content (display components). You should not read this
     * method if you are trying to learn binding tools API (this is pure Swing
     * presentation code)
     */
    private void createContent() {

        // creates the frame content
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.insets = new Insets(10, 10, 0, 0);

        // Display a slider, a spinner, a formatted text field and a
        // JProgressBar

        // a - slider
        constraints.gridx = 2;
        constraints.gridheight = 4;
        constraints.weightx = 0.7;
        constraints.anchor = GridBagConstraints.LINE_END;
        constraints.fill = GridBagConstraints.VERTICAL;
        add(slider, constraints);

        // decorative labels
        constraints.weightx = 0.0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridheight = 1;
        constraints.gridx = 3;
        // max label
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        add(new JLabel("100"), constraints);
        // min label
        constraints.gridy = 4;
        constraints.insets = new Insets(10, 10, 0, 0);
        constraints.anchor = GridBagConstraints.SOUTHWEST;
        add(new JLabel("0"), constraints);

        // b - spinner
        constraints.weighty = 0.0;
        constraints.gridy = 2;
        constraints.gridx = 0;
        add(spinner, constraints);
        // decorative label
        constraints.gridy = 1;
        add(new JLabel("Spinner"), constraints);

        // c - text field
        constraints.gridy = 2;
        constraints.gridx = 1;
        add(formattedTextField, constraints);

        // decorative label
        constraints.gridy = 1;
        add(new JLabel("Formatted field"), constraints);

        // d - progress bar
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        add(progressBar, constraints);

        // decorative label
        constraints.gridy = 3;
        constraints.weighty = 0.0;
        add(new JLabel("Progress bar"), constraints);

        // e - buttons
        constraints.weighty = 0.0;
        constraints.gridy = 6;
        constraints.gridwidth = 4;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.add(resetValueButton);
        buttonPanel.add(closeButton);
        constraints.insets = new Insets(10, 0, 5, 0);
        add(buttonPanel, constraints);

        // Header decoration label (I'd better use a Swing X label but I do not
        // want to import libraries for a demonstration)
        constraints.gridy = 0;
        constraints.insets = new Insets(0, 0, 0, 0);
        JLabel banner = new JLabel(
                "<html><b>Binding tools demo</b><br>Edit the property values here : all the views are binding the central<br> property value that you edit through controllers.</html>");
        banner.setBackground(Color.white);
        banner.setOpaque(true);
        banner.setBorder(new CompoundBorder(new MatteBorder(0, 0, 1, 0,
                                                            Color.black), new EmptyBorder(new Insets(5, 10, 8, 10))));
        add(banner, constraints);

    }

    /**
     * Installs behavior using the binding tools.
     */
    private void installBehavior() {

        // A - create the editable bean that we use as model (we could bind all
        // controllers together but this would be a bit messy...)
        final EditedObject editedObject = new EditedObject();

        // B - install view updates : from the editable object to the graphical
        // representation
        installViews(editedObject);

        // C - Create the binding link from the controller to the edited model.
        installControllers(editedObject);

        // D - Create an external controller : the reset button
        resetValueButton.setAction(new AbstractAction("Reset") {

            /** Default UID **/
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                // set the model value to 50
                editedObject.setEditedProperty(50);
            }
        });

        // E - Adding a simple quit action
        closeButton.setAction(new AbstractAction("Close") {

            /** Default UID **/
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * Install views (model displayers)
     * 
     * @param editedObject : edited Object
     */
    private void installViews(final EditedObject editedObject) {
        // note : the link creation will also initialize the view content
        // 1 slider (from edited object to slider value)
        BindingTools.createBinding(editedObject, EditedObject.EDITED_PROPERTY,
                                   slider, "value");
        // 2 spinner (from edited object to slider value)
        BindingTools.createBinding(editedObject, EditedObject.EDITED_PROPERTY,
                                   spinner, "value");
        // 3 text field (from edited object to text field value)
        BindingTools.createBinding(editedObject, EditedObject.EDITED_PROPERTY,
                                   formattedTextField, "value");
        // 4 progress bar (from edited object to slider value)
        BindingTools.createBinding(editedObject, EditedObject.EDITED_PROPERTY,
                                   progressBar, "value");
    }

    /**
     * Install controllers (model updaters)
     * 
     * @param editedObject : edited Object
     */
    private void installControllers(final EditedObject editedObject) {
        // Swing rarely allows binding fire property change on the view property
        // (what is really incoherent...) So we use here the appropriated
        // binding source
        // 1 slider (from slider state to edited model property value)
        BindingTools.createBinding(new JSliderBindingSource(slider),
                                   editedObject, EditedObject.EDITED_PROPERTY);
        // 2 spinner (from spinner state to edited model property value)
        BindingTools.createBinding(new JSpinnerBindingSource(spinner),
                                   editedObject, EditedObject.EDITED_PROPERTY);
        // 3 formatted text field (needed conversion / restriction on
        // [0-100]). If the number is out of the range, other views should
        // receive the range bounds (and, as some of them act as controller, set
        // back the bound value in the text field)
        BindingTools.createBinding(new JFTFBindingSource(formattedTextField) {

            @Override
            protected Object convert(Object value) {
                if (value == null) {
                    return null;
                }
                // we wait here for a number (the API can not provide it as the
                // text field formats are not generic...)
                int currentValue = ((Number) value).intValue();
                if (currentValue < 0) {
                    return 0;
                }
                if (currentValue > 100) {
                    return 100;
                }
                return currentValue;
            }
        }, editedObject, EditedObject.EDITED_PROPERTY);
    }

    /**
     * Demo main method : shows the main frame
     * 
     * @param args : application parameters (useless)
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

                SimpleDemoFrame swingDemoFrame = new SimpleDemoFrame();
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
