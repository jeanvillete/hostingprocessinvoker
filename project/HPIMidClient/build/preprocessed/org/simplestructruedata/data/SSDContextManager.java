/**
 * 
 */
package org.simplestructruedata.data;

import java.util.Enumeration;
import java.util.Vector;

import org.simplestructruedata.commons.SSDDefaultConstants;
import org.simplestructruedata.commons.SSDUtils;
import org.simplestructruedata.entities.SSDObject;
import org.simplestructruedata.entities.SSDObjectArray;
import org.simplestructruedata.entities.SSDObjectLeaf;
import org.simplestructruedata.entities.SSDObjectNode;
import org.simplestructruedata.entities.SSDSetCharacter;
import org.simplestructruedata.exception.SSDException;
import org.simplestructruedata.exception.SSDIllegalArgument;

/**
 * @author Jean Villete
 *
 */
public class SSDContextManager {
	
    private Vector		heap = new Vector();
    private int			currentReference = 0;

    public class SSDRootObject extends SSDObjectNode {
        public SSDRootObject() {
            super(SSDDefaultConstants.ROOT_OBJECT);
        }
    }

    private SSDContextManager() {
        this.addToHeap(new SSDRootObject());
    }

    public static SSDContextManager build() {
        return build("{}");
    }

    public static SSDContextManager build(String dataBase) {
        if (dataBase == null || dataBase.length() < 1) {
                throw new SSDIllegalArgument("the argument dataBase is null or is empty");
        }
        dataBase = dataBase.trim();
        if (dataBase.charAt(0) != SSDDefaultConstants.OPENS_BRACES) {
            throw new SSDException("The string base must starts with:" + SSDDefaultConstants.OPENS_BRACES);
        } else {
            SSDContextManager ctx = new SSDContextManager();

            SSDSetCharacter string = new SSDSetCharacter();
            for (int i = 1; i < dataBase.length(); i++) {
                char currentChar = dataBase.charAt(i);
                if (SSDUtils.isReservedCharacter(currentChar)) {
                    // special escapade character
                    if (currentChar == SSDDefaultConstants.ESCAPE) {
                        currentChar = dataBase.charAt(++i);
                        string.add(currentChar);
                        continue;
                    }
                    // special assignment or comma characters
                    if (currentChar == SSDDefaultConstants.ASSIGN || currentChar == SSDDefaultConstants.COMMA) {
                        currentChar = dataBase.charAt(++i);
                    }
                    // special opens or closes braces characters
                    if (currentChar == SSDDefaultConstants.OPENS_BRACES) {
                        if (ctx.getCurrentObject() instanceof SSDObjectArray) {
                            SSDObjectArray objectArray = (SSDObjectArray)ctx.getCurrentObject();
                            ctx.addToHeap(new SSDObjectNode(objectArray.getNextIdentifier()));
                        } else {
                            ctx.addToHeap(new SSDObjectNode(string.getString()));
                        }
                        string.clear();
                        continue;
                    } else if (currentChar == SSDDefaultConstants.CLOSES_BRACES) {
                        ctx.closeObject();
                        string.clear();
                        continue;
                    }
                    // special opens or closes brackets characters
                    if (currentChar == SSDDefaultConstants.OPENS_BRACKETS) {
                        if (ctx.getCurrentObject() instanceof SSDObjectArray) {
                            SSDObjectArray objectArray = (SSDObjectArray)ctx.getCurrentObject();
                            ctx.addToHeap(new SSDObjectArray(objectArray.getNextIdentifier()));
                        } else {
                            ctx.addToHeap(new SSDObjectArray(string.getString()));
                        }
                        string.clear();
                        continue;
                    } else if (currentChar == SSDDefaultConstants.CLOSES_BRACKETS) {
                        ctx.closeObject();
                        string.clear();
                        continue;
                    }
                    // special quotation marks character
                    if (currentChar == SSDDefaultConstants.QUOTATION_MARKS) {
                        if (ctx.getCurrentObject() instanceof SSDObjectLeaf) {
                            SSDObjectLeaf objectLeaf = (SSDObjectLeaf) ctx.getCurrentObject();
                            objectLeaf.setValue(string.getString());
                            ctx.closeObject();
                        } else if (ctx.getCurrentObject() instanceof SSDObjectArray) {
                            SSDObjectArray objectArray = (SSDObjectArray)ctx.getCurrentObject();
                            ctx.addToHeap(new SSDObjectLeaf(objectArray.getNextIdentifier()));
                        } else {
                            ctx.addToHeap(new SSDObjectLeaf(string.getString()));
                        }
                        string.clear();
                        continue;
                    }
                }
                string.add(currentChar);
            }
            return ctx;
        }
    }

    private void addToHeap(SSDObject ssdObject) {
        this.heap.insertElementAt(ssdObject, this.currentReference++);
    }

    private SSDObject getCurrentObject() {
        return (SSDObject) this.heap.elementAt(this.currentReference-1);
    }

    private SSDObject getAboveObject() {
        return (SSDObject) this.heap.elementAt(this.currentReference-2);
    }

    private void closeObject() {
        if (this.getCurrentObject() instanceof SSDRootObject) {
            return;
        }
        SSDObject object = this.getAboveObject();
        if (object instanceof SSDObjectNode) {
            SSDObjectNode nodeObject = (SSDObjectNode) object;
            nodeObject.addAttribute(this.getCurrentObject());
        } else if (object instanceof SSDObjectArray) {
            SSDObjectArray objectArray = (SSDObjectArray) object;
            objectArray.addElement(this.getCurrentObject());
        } else throw new SSDException("Invalid type to this point");
        this.currentReference--;
    }

    public SSDRootObject getRootObject() {
        if (this.heap.size() > 1) {
            int currentSize = this.heap.size();
            for (int i = 1; i < currentSize; i++ ) {
                this.heap.removeElementAt(currentSize - i);
            }
        }
        return (SSDRootObject) this.heap.elementAt(0);
    }

    public String toString() {
        return this.toString(false);
    }

    public String toString(boolean format) {
        return this.toString(this.getRootObject(), null, "", new Formatting(format));
    }

    private String toString(SSDObject object, String identifier, String currentTab, Formatting formatting) {
            StringBuffer returning = new StringBuffer();
            if (identifier != null && !(identifier.length() < 1)) {
                    returning.append(object.getIdentifier() + " = ");
            }

            if (object instanceof SSDObjectLeaf) {
                SSDObjectLeaf objectLeaf = (SSDObjectLeaf)object;
                returning.append("\"");
                returning.append(SSDUtils.formatEscapes(objectLeaf.getValue()));
                returning.append("\"" + formatting.getNewLine());
            } else if (object instanceof SSDObjectNode) {
                SSDObjectNode objectNode = (SSDObjectNode)object;
                returning.append("{" + formatting.getNewLine());
                Vector attributes = new Vector();
                Enumeration oldAttributes = objectNode.getAttributes();
                while (oldAttributes.hasMoreElements()) {
                    attributes.addElement(oldAttributes.nextElement());
                }

                int attributeSize = attributes.size();
                if (attributeSize > 0) {
                    for (int i = 0; i < attributeSize; i++) {
                        String nextTab = currentTab + formatting.getTabulator();
                        returning.append(nextTab);
                        if (i > 0) {
                            returning.append(", ");
                        }
                        SSDObject attribute = (SSDObject) attributes.elementAt(i);
                        returning.append(this.toString(attribute, attribute.getIdentifier(), nextTab, formatting));
                    }
                }
                returning.append(currentTab);
                returning.append("}" + formatting.getNewLine());
            } else if (object instanceof SSDObjectArray) {
                SSDObjectArray objectArray = (SSDObjectArray)object;
                returning.append("[" + formatting.getNewLine());
                Vector elements = objectArray.getElements();
                for (int i = 0; i < elements.size(); i++ ) {
                    String nextTab = currentTab + formatting.getTabulator();
                    returning.append(nextTab);
                    if (i > 0) {
                        returning.append(", ");
                    }
                    returning.append(this.toString((SSDObject)elements.elementAt(i), null, nextTab, formatting));
                }
                returning.append(currentTab);
                returning.append("]" + formatting.getNewLine());
            } else throw new SSDException("Invalid type to this point");
            return returning.toString();
    }

    private class Formatting {
        private char tabulator = '\t';
        private char newLine = '\n';
        Formatting(boolean setFormat) {
            if (!setFormat) {
                tabulator = ' ';
                newLine = ' ';
            }
        }
        char getTabulator() {
            return tabulator;
        }
        char getNewLine() {
            return newLine;
        }
    }
}
