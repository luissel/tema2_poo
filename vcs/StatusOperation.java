package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;
import java.util.ArrayList;
import java.util.List;

public final class StatusOperation extends VcsOperation {
    public StatusOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    public int execute(Vcs vcs) {
        //afisez branchul curent si elementele din staging
        List<String> staging = vcs.getStaging();

        vcs.getOutputWriter().write("On branch: " + vcs.getCurrentBranch().getName() + "\n");
        vcs.getOutputWriter().write("Staged changes:" + "\n");

        if (!staging.isEmpty()) {
            for (String s : staging) {
                vcs.getOutputWriter().write("\t" + s);
                vcs.getOutputWriter().write("\n");
            }
        }

        return ErrorCodeManager.OK;
    }
}
