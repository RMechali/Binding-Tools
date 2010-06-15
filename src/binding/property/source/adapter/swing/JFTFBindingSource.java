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
package binding.property.source.adapter.swing;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFormattedTextField;

import binding.property.source.adapter.AbstractBindingSourceAdapter;

/**
 * A JFormattedTextField binding source (adapted to be used as any other binding
 * source). The user must specify the conversion method according with the
 * format and the awaited type.
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public abstract class JFTFBindingSource extends
		AbstractBindingSourceAdapter<JFormattedTextField> implements
		PropertyChangeListener {

	/**
	 * Constructor
	 * 
	 * @param eventSource
	 *            : event source
	 */
	public JFTFBindingSource(JFormattedTextField eventSource) {
		super(eventSource);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void startListening(JFormattedTextField eventSource) {
		eventSource.addPropertyChangeListener("value", this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void stopListening(JFormattedTextField eventSource) {
		eventSource.addPropertyChangeListener("value", this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getInitialValue() {
		return convert(getEventSource().getValue());
	}

	/**
	 * Convert the current formatted text field value to the value that should
	 * be propagated (actually the link target could do it too, but there is no
	 * reason for the target to depend on the source API problems!)
	 * 
	 * @param value : current formatted text field value (can be null)
	 * @return - the converted object (an integer, a double, and so on... according with the type you expect this binding source to propagate)
	 */
	protected abstract Object convert(Object value);

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// update the adapted property to fire the event change
		setAdaptedProperty(convert(evt.getNewValue()));		
	}
}
