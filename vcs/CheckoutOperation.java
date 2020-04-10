package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;
import java.util.ArrayList;

public final class CheckoutOperation extends VcsOperation {
    public CheckoutOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    public int execute(Vcs vcs) {
        final int size = 3;

        // Daca exista elemente in staging, returnez eroare
        if (!vcs.getStaging().isEmpty()) {
            return ErrorCodeManager.VCS_STAGED_OP_CODE;
        }

        // Iterez in lista de commituri a branch ului curent pana il gasesc pe
        // cel cu id ul primit in comanda checkout. Odata gasit, setez sistemul
        // de fisiere al vcs ului cu cel al commit ului gasit si sterg
        // commit urile de dupa acesta.
        if (this.operationArgs.size() == size) {
            Integer id = Integer.valueOf(this.operationArgs.get(2));
            int position = 0;

            for (Commit c : vcs.getCurrentBranch().getCommits()) {
                if (id == c.getId()) {
                    vcs.setHead(c);
                    vcs.removeCommits(position + 1);
                    vcs.setActiveSnapshot(c.getSnapshot());

                    return ErrorCodeManager.OK;
                }
                position++;
            }

            return ErrorCodeManager.VCS_BAD_PATH_CODE;
        }

        // In cazul in care comanda primeste ca parametru un branch, parcurg
        // lista de branch uri. Daca il gasesc pe cel dorit, il setez ca
        // currentBranch. Altfel, returnez eroare.
        for (Branch b : vcs.getBranches()) {
            if (b.getName().equals(this.operationArgs.get(1))) {
                vcs.setCurrentBranch(b);
                vcs.setActiveSnapshot(b.getSnapshot().cloneFileSystem());

                return ErrorCodeManager.OK;
            }
        }

        return ErrorCodeManager.VCS_BAD_CMD_CODE;
    }
}
