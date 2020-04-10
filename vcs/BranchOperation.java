package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;
import java.util.ArrayList;

public final class BranchOperation extends VcsOperation {
    public BranchOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    public int execute(Vcs vcs) {
        // Daca exista deja un branch cu acelasi nume, returneaza eroare
        // in caz contrar, se creaza branch ul respectiv
        for (Branch b : vcs.getBranches()) {
            if (b.getName().equals(this.operationArgs.get(1))) {
                return ErrorCodeManager.VCS_BAD_CMD_CODE;
            }
        }

        Branch newBranch = new Branch(this.operationArgs.get(1));
        newBranch.setSnapshot(vcs.getActiveSnapshot().cloneFileSystem());
        vcs.addBranch(newBranch);

        return ErrorCodeManager.OK;
    }
}
