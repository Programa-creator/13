package org.example;
import java.io.File;

public class FileData {
    private String name;
    private long size;
    private String path;

    public FileData(String path) {
        File file = new File(path);
        this.name = file.getName();
        this.size = file.length();
        this.path = file.getAbsolutePath();
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public String getPath() {
        return path;
    }
}

