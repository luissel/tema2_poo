package vcs;

import filesystem.FileSystemSnapshot;
import utils.IDGenerator;

public final class Commit {
    private String message;
    private int id;
    private IDGenerator generator;
    private FileSystemSnapshot snapshot;

    public Commit(String message, FileSystemSnapshot snapshot) {
        this.message = message;
        this.id = generator.generateCommitID();
        this.snapshot = snapshot;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public FileSystemSnapshot getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(FileSystemSnapshot snapshot) {
        this.snapshot = snapshot;
    }
}
