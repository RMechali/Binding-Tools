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
package junit.property;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import junit.framework.Assert;

import org.junit.Test;

import binding.property.PropertyBindingLink;
import binding.property.source.chained.ChainedBindingSource;
import binding.property.target.PropertyBindingTarget;

/**
 * Test for the chained property binding
 * 
 * Copyright 2011, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class ChainedPropertyBindingTest {

    /** Current recepted value **/
    private Object receptedValue;

    /**
     * Test for chain binding
     */
    @Test
    public void chainTest() {
        TestModel model = new TestModel();

        new PropertyBindingLink(new ChainedBindingSource(model,
                                                         "currentSelection", "currentName"),
                                new PropertyBindingTarget() {

            @Override
            public void updateTarget(Object newValue) {
                receptedValue = newValue;
            }
        });
        // awaiting null
        Assert.assertEquals(null, receptedValue);

        // setting the first chain element
        TestSelection testSelection1 = new TestSelection();
        model.setCurrentSelection(testSelection1);

        // no update
        Assert.assertEquals(null, receptedValue);

        testSelection1.setCurrentName("my.test.1");
        // new value : my.test.1
        Assert.assertEquals("my.test.1", receptedValue);

        // changing model selection
        TestSelection testSelection2 = new TestSelection();
        testSelection2.setCurrentName("my.test.2");
        model.setCurrentSelection(testSelection2);
        Assert.assertEquals("my.test.2", receptedValue);

        // verifying terminated links
        testSelection1.setCurrentName("changed.test.1");
        Assert.assertNotSame("changed.test1", receptedValue);

        // verifying link termination
        model.setCurrentSelection(null);
        Assert.assertEquals(null, receptedValue);
    }

    /**
     * Test model class
     * 
     * 
     * Copyright 2011, Raphael Mechali <br>
     * Distributed under Lesser GNU General Public License (LGPL)
     */
    protected class TestModel {

        /** Change support **/
        private final PropertyChangeSupport support = new PropertyChangeSupport(
                this);

        /** Current selection **/
        private TestSelection currentSelection = null;

        /**
         * Getter -
         * 
         * @return the currentSelection
         */
        public TestSelection getCurrentSelection() {
            return currentSelection;
        }

        /**
         * Setter -
         * 
         * @param currentSelection
         *            the currentSelection to set
         */
        public void setCurrentSelection(TestSelection currentSelection) {
            TestSelection oldValue = this.currentSelection;
            this.currentSelection = currentSelection;
            support.firePropertyChange("currentSelection", oldValue,
                                       this.currentSelection);
        }

        /**
         * Delegate method.
         * 
         * @param propertyName
         *            : property name
         * @param listener
         *            : listener
         * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String,
         *      java.beans.PropertyChangeListener)
         */
        public void addPropertyChangeListener(String propertyName,
                                              PropertyChangeListener listener) {
            support.addPropertyChangeListener(propertyName, listener);
        }

        /**
         * Delegate method.
         * 
         * @param propertyName
         *            : property name
         * @param listener
         *            : listener
         * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String,
         *      java.beans.PropertyChangeListener)
         */
        public void removePropertyChangeListener(String propertyName,
                                                 PropertyChangeListener listener) {
            support.removePropertyChangeListener(propertyName, listener);
        }
    }

    /**
     * Test selection
     * 
     * Copyright 2011, Raphael Mechali <br>
     * Distributed under Lesser GNU General Public License (LGPL)
     */
    public class TestSelection {

        /** Change support **/
        private final PropertyChangeSupport support = new PropertyChangeSupport(
                this);

        /** The name **/
        private String currentName;

        /**
         * Getter -
         * 
         * @return the currentName
         */
        public String getCurrentName() {
            return currentName;
        }

        /**
         * Setter -
         * 
         * @param currentName
         *            the currentName to set
         */
        public void setCurrentName(String currentName) {
            String oldValue = this.currentName;
            this.currentName = currentName;
            support.firePropertyChange("currentName", oldValue,
                                       this.currentName);
        }

        /**
         * Delegate method.
         * 
         * @param propertyName
         *            : property name
         * @param listener
         *            : listener
         * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String,
         *      java.beans.PropertyChangeListener)
         */
        public void addPropertyChangeListener(String propertyName,
                                              PropertyChangeListener listener) {
            support.addPropertyChangeListener(propertyName, listener);
        }

        /**
         * Delegate method.
         * 
         * @param propertyName
         *            : property name
         * @param listener
         *            : listener
         * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String,
         *      java.beans.PropertyChangeListener)
         */
        public void removePropertyChangeListener(String propertyName,
                                                 PropertyChangeListener listener) {
            support.removePropertyChangeListener(propertyName, listener);
        }
    }
}
