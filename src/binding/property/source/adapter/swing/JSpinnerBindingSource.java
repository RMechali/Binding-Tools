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

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import binding.property.source.adapter.AbstractBindingSourceAdapter;

/**
 * A spinner binding source (adapted to be used as any other binding source)
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class JSpinnerBindingSource extends AbstractBindingSourceAdapter<JSpinner> implements ChangeListener {

	/**
	 * Constructor
	 * @param eventSource : component that is at binding source
	 */
	public JSpinnerBindingSource(JSpinner eventSource) {
		super(eventSource);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void startListening(JSpinner eventSource) {
		eventSource.addChangeListener(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void stopListening(JSpinner eventSource) {
		eventSource.removeChangeListener(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getInitialValue() {
		return getEventSource().getValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		// retrieve the current value
		Object value = getEventSource().getValue();
		// fire update
		setAdaptedProperty(value);
	}

}
