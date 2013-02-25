/**
 * 
 */
package org.simplestructruedata.entities;

import java.util.Vector;

/**
 * @author Jean Villete
 *
 */
public class SSDSetCharacter {

    private Vector string = new Vector();

    public SSDSetCharacter() {
    }

    public void add(Character character) {
        this.string.addElement(character);
    }

    public void add(char character) {
        this.string.addElement(new Character(character));
    }

    public void clear() {
            this.string.removeAllElements();
    }

    public String getString() {
        char[] string = new char[this.string.size()];
        for (int i = 0; i < this.string.size(); i ++) {
            char character = ((Character) this.string.elementAt(i)).charValue();
            string[i] = character;
        }
        return new String(string);
    }
	
}
