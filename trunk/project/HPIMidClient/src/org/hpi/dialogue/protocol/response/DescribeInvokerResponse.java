/**
 * 
 */
package org.hpi.dialogue.protocol.response;

import org.hpi.dialogue.protocol.common.HPIDialogueConstants;
import org.hpi.dialogue.protocol.entities.Executable;
import org.hpi.dialogue.protocol.entities.Invoker;
import org.hpi.dialogue.protocol.entities.Parameter;
import org.simplestructruedata.entities.SSDObjectArray;
import org.simplestructruedata.entities.SSDObjectLeaf;
import org.simplestructruedata.entities.SSDObjectNode;

/**
 * @author Jean Villete
 *
 */
public class DescribeInvokerResponse extends Response {

    private static final long serialVersionUID = 4164109673082824380L;

    private Invoker						invoker;

    public DescribeInvokerResponse(Invoker invoker, String message, Status status) {
        super(message, status);
        this.invoker = invoker;
    }

    public String getServiceName() {
        return HPIDialogueConstants.DESCRIBE_INVOKER_SERVICE;
    }

    public SSDObjectNode getSSDParameters() {
        SSDObjectNode parameters = new SSDObjectNode(HPIDialogueConstants.PARAMETERS);

        SSDObjectNode ssdInvoker = new SSDObjectNode(HPIDialogueConstants.INVOKER);
        ssdInvoker.addAttribute(new SSDObjectLeaf(HPIDialogueConstants.ID, invoker.getId()));
        ssdInvoker.addAttribute(new SSDObjectLeaf(HPIDialogueConstants.DESCRIPTION, invoker.getDescription()));

        SSDObjectArray ssdExecutables = new SSDObjectArray(HPIDialogueConstants.EXECUTABLES);
        if (invoker.getExecutables() != null && invoker.getExecutables().size() > 0) {
            for (int i = 0; i < invoker.getExecutables().size(); i++) {
                Executable executable = (Executable) invoker.getExecutables().elementAt(i);
                SSDObjectNode ssdExecutable = new SSDObjectNode(HPIDialogueConstants.EXECUTABLE);
                ssdExecutable.addAttribute(new SSDObjectLeaf(HPIDialogueConstants.CANONICAL_PATH, executable.getCanonicalPath()));

                SSDObjectArray ssdParemeters = new SSDObjectArray(HPIDialogueConstants.PARAMETERS);
                if (executable.getParameters() != null && executable.getParameters().size() > 0) {
                    for (int p = 0; p < executable.getParameters().size(); p ++) {
                        Parameter parameter = (Parameter) executable.getParameters().elementAt(p);
                        SSDObjectNode ssdParemeter = new SSDObjectNode(HPIDialogueConstants.PARAMETER);
                        ssdParemeter.addAttribute(new SSDObjectLeaf(HPIDialogueConstants.KEY, parameter.getKey()));
                        ssdParemeter.addAttribute(new SSDObjectLeaf(HPIDialogueConstants.VALUE, parameter.getValue()));

                        ssdParemeters.addElement(ssdParemeter);
                    }
                }

                ssdExecutable.addAttribute(ssdParemeters);
                ssdExecutables.addElement(ssdExecutable);
            }
        }

        ssdInvoker.addAttribute(ssdExecutables);
        parameters.addAttribute(ssdInvoker);
        return parameters;
    }

    // GETTERS AND SETTERS //
    public Invoker getInvoker() {
        return invoker;
    }

}
