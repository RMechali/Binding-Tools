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
package binding.property.source.chained;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import binding.property.PropertyBindingLink;
import binding.property.source.PropertyBindingSource;
import binding.property.source.object.BeanBindingSource;
import binding.property.target.PropertyBindingTarget;

/**
 * A chained binding source can be compose with many binding source. When any
 * value nullifies, the final value is null (avoids you to test it all over the
 * chain). However, such source needs every elements of the chain to be a normal
 * property (ie : add / remove property change listener available, property
 * getter available too)
 * 
 * Copyright 2011, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class ChainedBindingSource implements PropertyBindingSource {

    /** The property change support **/
    private final PropertyChangeSupport changeSupport;

    /** The current value **/
    private Object currentValue;

    /** Is currently initializing? **/
    private boolean initialization;

    /** List of chained  properties names **/
    private final List<String> propertyChain;

    /** List of current binding links used to perform the whole chain **/
    private final List<PropertyBindingLink> dynamicLinks;

    /**
     * Constructor
     * 
     * @param highLevelBean : the high level bean
     * @param propertyChain : the list of bound properties that will be listened to until
     *            final value (example : my value is 
     *            myModel.getSelectedObject().getValue().getColor() then I call
     *            ChainedBindingSource(myModel,"selectedObject","value","color")
     *            where every of the objects define addPropertyChangeListener)
     */
    public ChainedBindingSource(Object highLevelBean, String... propertyChain) {
        this(highLevelBean, Arrays.asList(propertyChain));
    }

    /**
     * Constructor
     * 
     * @param highLevelBean : the high level bean
     * @param propertyChain : list of properties that will be binded (see constructor
     *            above for more detail)
     */
    public ChainedBindingSource(Object highLevelBean, List<String> propertyChain) {
        if (propertyChain == null || propertyChain.isEmpty()) {
            throw new IllegalArgumentException(
                    "The chained properties list can not be null");
        }
        changeSupport = new PropertyChangeSupport(this);

        // will be defining here the list of bean binding elements that we need
        // to run dynamically
        initialization = true;
        this.propertyChain = propertyChain;
        dynamicLinks = new ArrayList<PropertyBindingLink>();
        for (int i = 0; i < propertyChain.size(); i++) {
            final int index = i;
            dynamicLinks.add(new PropertyBindingLink(null,
                                                     new PropertyBindingTarget() {

                @Override
                public void updateTarget(Object newValue) {
                    setChainValue(index + 1, newValue);
                }
            }));
        }
        initialization = false;

        // now we create the highest level binding link onto source element
        // (highLevelBean)
        setChainValue(0, highLevelBean);
    }

    /**
     * Sets the chain value for the chain index as parameter
     * 
     * @param index : chain index
     * @param newValue : new value for that index
     */
    protected void setChainValue(int index, Object newValue) {
        if (initialization) {
            // dynamic list is not ready
            return;
        }
        if (index < dynamicLinks.size()) {
            PropertyBindingLink iLevelBindingLink = dynamicLinks.get(index);
            if (newValue != null) {
                iLevelBindingLink.setBindingSource(new BeanBindingSource(
                        newValue, propertyChain.get(index)));
            }
            else {
                iLevelBindingLink.setBindingSource(null);
            }
        }
        else {
            // the final link value is produced
            setCurrentValue(newValue);
        }
    }

    /**
     * Setter -
     * 
     * @param currentValue : the currentValue to set
     */
    public void setCurrentValue(Object currentValue) {
        Object oldValue = this.currentValue;
        this.currentValue = currentValue;
        changeSupport.firePropertyChange("currentValue", oldValue,
                                         this.currentValue);
    }

    /**
     * {@inherit}
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    /**
     * {@inherit}
     */
    @Override
    public Object getInitialValue() {
        return currentValue;
    }

    /**
     * {@inherit}
     */
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
}
