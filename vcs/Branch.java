package vcs;

import filesystem.FileSystemSnapshot;

import java.util.ArrayList;
import java.util.List;

public final class Branch {
    private List<Commit> commits;
    private String name;
    private FileSystemSnapshot snapshot;

    public Branch(String name) {
        this.name = name;
        commits = new ArrayList<Commit>();
    }

    public void addCommit(Commit c) {
        commits.add(c);
    }

    public List<Commit> getCommits() {
        return commits;
    }
    public String getName() {
        return name;
    }

    public FileSystemSnapshot getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(FileSystemSnapshot snapshot) {
        this.snapshot = snapshot;
    }

    // sterg commiturile incepand cu pozitia data ca parametru
    public void removeCommits(int position) {
        for (int i = position; i < this.commits.size(); i++) {
            commits.remove(i);
        }
    }
}
