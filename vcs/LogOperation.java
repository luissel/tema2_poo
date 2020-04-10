package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;
import java.util.ArrayList;

public final class LogOperation extends VcsOperation {
    public LogOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    public int execute(Vcs vcs) {
        // Afisez id-ul si mesajul fiecarui commit din branchul curent
        Branch currentBranch = vcs.getCurrentBranch();
        int cnt = 0;

        for (Commit c : currentBranch.getCommits()) {
            vcs.getOutputWriter().write("Commit id: " + c.getId() + "\n");
            vcs.getOutputWriter().write("Message: " + c.getMessage() + "\n");

            if (cnt != currentBranch.getCommits().size() - 1) {
                vcs.getOutputWriter().write("\n");
            }

            cnt++;
        }

        return ErrorCodeManager.OK;
    }
}
