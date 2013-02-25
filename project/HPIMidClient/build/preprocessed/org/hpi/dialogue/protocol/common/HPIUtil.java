/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpi.dialogue.protocol.common;

/**
 *
 * @author carrefour
 */
public class HPIUtil {
    
    public static String StringReplace(String base, String toReplace) {
        if (base == null || base.length() < 1) {
            return base;
        }
        int indexToReplace = base.indexOf(toReplace);
        if (indexToReplace >= 0) {
            return base.substring(0, indexToReplace) + base.substring(indexToReplace + toReplace.length(), base.length());
        }
        return base;
    }
    
    public static boolean isStringOk(String string) {
        return string != null && string.length() > 0;
    }
}
