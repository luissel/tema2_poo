package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;

public final class CommitOperation extends VcsOperation {
    public CommitOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    public int execute(Vcs vcs) {
        if (vcs.getStaging().isEmpty()) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        }

        //se creaza un commit nou cu sistemul de fisiere modificat
        Commit commit = new Commit(this.operationArgs.get(1), vcs.getActiveSnapshot().
                                    cloneFileSystem());

        vcs.getCurrentBranch().setSnapshot(commit.getSnapshot().cloneFileSystem());
        vcs.clearStaging();
        vcs.getCurrentBranch().addCommit(commit);
        //setez headul pe commitul nou
        vcs.setHead(commit);

        return ErrorCodeManager.OK;
    }
}
