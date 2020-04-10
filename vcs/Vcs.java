package vcs;

import filesystem.FileSystemOperation;
import filesystem.FileSystemSnapshot;
import utils.AbstractOperation;
import utils.OutputWriter;
import utils.Visitor;
import java.util.ArrayList;
import java.util.List;

public final class Vcs implements Visitor {
    private final OutputWriter outputWriter;
    private FileSystemSnapshot activeSnapshot;
    private List<String> staging;
    private Branch masterBranch;
    private List<Branch> branches;
    private Branch currentBranch;
    private Commit head;

    /**
     * Vcs constructor.
     *
     * @param outputWriter the output writer
     */
    public Vcs(OutputWriter outputWriter) {
        this.outputWriter = outputWriter;
    }

    /**
     * Does initialisations.
     */
    public void init() {
        this.activeSnapshot = new FileSystemSnapshot(outputWriter);

        //TODO other initialisations
        this.staging = new ArrayList<String>();
        this.branches = new ArrayList<Branch>();
        masterBranch = new Branch("master");
        FileSystemSnapshot snapshot = activeSnapshot.cloneFileSystem();
        Commit firstCommit = new Commit("First commit", snapshot);

        masterBranch.addCommit(firstCommit);
        masterBranch.setSnapshot(snapshot);
        currentBranch = masterBranch;
        branches.add(masterBranch);
        head = firstCommit;
    }

    /**
     * Visits a file system operation.
     *
     * @param fileSystemOperation the file system operation
     * @return the return code
     */
    public int visit(FileSystemOperation fileSystemOperation) {
        return fileSystemOperation.execute(this.activeSnapshot);
    }

    /**
     * Visits a vcs operation.
     *
     * @param vcsOperation the vcs operation
     * @return return code
     */
    @Override
    public int visit(VcsOperation vcsOperation) {
        //TODO
        return vcsOperation.execute(this);
    }

    //TODO methods through which vcs operations interact with this

    //adaug in staging modificarile facute
    public void track(AbstractOperation operation, String type) {
        if (type.toLowerCase().equals("touch")) {
            staging.add("Created file " + operation.getOperationArgs().get(1));
        }

        if (type.toLowerCase().equals("makedir")) {
            staging.add("Created directory " + operation.getOperationArgs().get(1));
        }

        if (type.toLowerCase().equals("writetofile")) {
            staging.add("Added \"" + operation.getOperationArgs().get(1) +  "\" to file "
                            + operation.getOperationArgs().get(0));
        }

        if (type.toLowerCase().equals("remove")) {
            staging.add("Removed " + operation.getOperationArgs().get(1));
        }
    }

    public List<String> getStaging() {
        return staging;
    }

    public OutputWriter getOutputWriter() {
        return outputWriter;
    }

    public Branch getCurrentBranch() {
        return currentBranch;
    }

    public void addBranch(Branch b) {
        this.branches.add(b);
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setCurrentBranch(Branch currentBranch) {
        this.currentBranch = currentBranch;
    }

    public void clearStaging() {
        this.staging.clear();
    }

    public FileSystemSnapshot getActiveSnapshot() {
        return activeSnapshot;
    }

    public void setActiveSnapshot(FileSystemSnapshot activeSnapshot) {
        this.activeSnapshot = activeSnapshot;
    }

    public void setHead(Commit head) {
        this.head = head;
    }

    public Commit getHead() {
        return head;
    }

    public void removeCommits(int position) {
        this.currentBranch.removeCommits(position);
    }
}
