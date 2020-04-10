package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;
import java.util.ArrayList;

public final class RollbackOperation extends VcsOperation {
    public RollbackOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    public int execute(Vcs vcs) {
        // Goleste stagingul si actualizeaza sistemul de fisiere cu cel al
        // ultimului commit dat
        vcs.clearStaging();
        vcs.setActiveSnapshot(vcs.getHead().getSnapshot().cloneFileSystem());

        return ErrorCodeManager.OK;
    }
}
