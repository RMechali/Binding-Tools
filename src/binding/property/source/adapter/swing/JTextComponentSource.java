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

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import binding.property.source.adapter.AbstractBindingSourceAdapter;

/**
 * A text component binding source (adapted to be used as any other binding
 * source)
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class JTextComponentSource extends
		AbstractBindingSourceAdapter<JTextComponent> implements
		DocumentListener {

	/**
	 * Constructor
	 * 
	 * @param eventSource
	 *            : event source
	 */
	public JTextComponentSource(JTextComponent eventSource) {
		super(eventSource);
	}

	/**
	 * {@inherit}
	 */
	@Override
	protected void startListening(JTextComponent eventSource) {
		// listen to the document
		eventSource.getDocument().addDocumentListener(this);
	}

	/**
	 * {@inherit}
	 */
	@Override
	protected void stopListening(JTextComponent eventSource) {
		// listen to the document
		eventSource.getDocument().removeDocumentListener(this);
	}

	/**
	 * {@inherit}
	 */
	@Override
	public Object getInitialValue() {
		return getEventSource().getText();
	}

	/**
	 * {@inherit}
	 */
	@Override
	public void changedUpdate(DocumentEvent e) {
		// update using the current text value
		update();
	}

	/**
	 * {@inherit}
	 */
	@Override
	public void insertUpdate(DocumentEvent e) {
		// update using the current text value
		update();
	}

	/**
	 * {@inherit}
	 */
	@Override
	public void removeUpdate(DocumentEvent e) {
		// update using the current text value
		update();
	}

	/**
	 * Updates the adapted property
	 */
	private void update() {
		setAdaptedProperty(getEventSource().getText());
	}

}
