/*
 * Created on 26.10.2003
 *
 */
package de.fhflensburg.xmleditor.editor.partitions.rules;

import org.eclipse.jface.text.rules.ICharacterScanner;

/**
 * @author user
 *
 */
public class TestCharacterScanner implements ICharacterScanner {

	private String val;
	private int index = 0;
	
	public TestCharacterScanner(String val)  {
		this.val = val;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.rules.ICharacterScanner#getLegalLineDelimiters()
	 */
	public char[][] getLegalLineDelimiters() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.rules.ICharacterScanner#getColumn()
	 */
	public int getColumn() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.rules.ICharacterScanner#read()
	 */
	public int read() {
		if (index == val.length()) {
			return ICharacterScanner.EOF;
		} else {
			return val.charAt(index++);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.rules.ICharacterScanner#unread()
	 */
	public void unread() {
		if (index>0) {
			index--;
		}
	}

}
