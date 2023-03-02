package org.example;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileNavigator {
    private String rootPath;
    private Map<String, List<FileData>> filesMap;

    public FileNavigator(String rootPath) {
        this.rootPath = rootPath;
        this.filesMap = new HashMap<>();
    }

    public void navigate() {
        File directory = new File(rootPath);
        if (!directory.isDirectory()) {
            System.out.println("Invalid directory path!");
            return;
        }
        navigateDirectory(directory);
    }

    private void navigateDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    addFile(file.getAbsolutePath());
                } else if (file.isDirectory()) {
                    navigateDirectory(file);
                }
            }
        }
    }

    public void addFile(String filePath) {
        FileData fileData = new FileData(filePath);
        String path = fileData.getPath();
        List<FileData> fileList = filesMap.get(path);
        if (fileList == null) {
            fileList = new ArrayList<>();
            filesMap.put(path, fileList);
        }
        fileList.add(fileData);
    }

    public List<FileData> getFiles(String path) {
        return filesMap.get(path);
    }

    public List<FileData> listFiles(String path) {
        List<FileData> fileList = filesMap.get(path);
        if (fileList == null) {
            fileList = new ArrayList<>();
        }
        File directory = new File(path);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String filePath = file.getAbsolutePath();
                    boolean fileExists = false;
                    for (FileData fileData : fileList) {
                        if (fileData.getPath().equals(filePath)) {
                            fileExists = true;
                            break;
                        }
                    }
                    if (!fileExists) {
                        FileData fileData = new FileData(filePath);
                        fileList.add(fileData);
                    }
                }
            }
        }
        return fileList;
    }
    public List<FileData> find(String path) {
        List<FileData> fileList = filesMap.get(path);
        if (fileList == null) {
            fileList = new ArrayList<>();
        }
        return fileList;
    }
    public List<FileData> filterBySize(long size) {
        List<FileData> filteredList = new ArrayList<>();
        for (List<FileData> fileList : filesMap.values()) {
            for (FileData fileData : fileList) {
                if (fileData.getSize() <= size) {
                    filteredList.add(fileData);
                }
            }
        }
        return filteredList;
    }
    public void remove(String path) {
        if (filesMap.containsKey(path)) {
            filesMap.remove(path);
            System.out.println("Files with path '" + path + "' have been removed.");
        } else {
            System.out.println("No files found with path '" + path + "'.");
        }
    }
    public static void main(String[] args) {
        String path = "D:\\BODYA\\Hillel\\Projects\\DZ13\\files";
        FileNavigator navigator = new FileNavigator(path);
        navigator.navigate();

        List<FileData> fileList = navigator.listFiles(path);
        if (fileList != null) {
            System.out.println("Files in " + path + ":");
            for (FileData fileData : fileList) {
                System.out.println("Name: " + fileData.getName() +
                        ", Size: " + fileData.getSize() +
                        ", Path: " + fileData.getPath());
            }
        } else {
            System.out.println("No files found in " + path);
        }

        String pathToFind = "D:\\BODYA\\Hillel\\Projects\\DZ13\\files\\1.txt";
        List<FileData> foundFiles = navigator.find(pathToFind);
        if (foundFiles != null) {
            if (foundFiles.isEmpty()) {
                System.out.println("No files found in " + pathToFind);
            } else {
                System.out.println("Files found in " + pathToFind + ":");
                for (FileData fileData : foundFiles) {
                    System.out.println("Name: " + fileData.getName() +
                            ", Size: " + fileData.getSize() +
                            ", Path: " + fileData.getPath());
                }
            }
        } else {
            System.out.println("Invalid path: " + pathToFind);
        }

        String newFilePath = "D:\\BODYA\\Hillel\\Projects\\DZ13\\files\\new_file.txt";
        navigator.addFile(newFilePath);
        fileList = navigator.getFiles(newFilePath);
        if (fileList != null) {
            System.out.println("\nFiles in " + newFilePath + ":");
            for (FileData fileData : fileList) {
                System.out.println("Name: " + fileData.getName() +
                        ", Size: " + fileData.getSize() +
                        ", Path: " + fileData.getPath());
            }
        }else {
                System.out.println("No files found in " + newFilePath);
            }

        long maxSize = 1024;
        List<FileData> filteredFiles = navigator.filterBySize(maxSize);
        if (filteredFiles.isEmpty()) {
            System.out.println("No files found with size less than or equal to " + maxSize + " bytes");
        } else {
            System.out.println("Files found with size less than or equal to " + maxSize + " bytes:");
            for (FileData fileData : filteredFiles) {
                System.out.println("Name: " + fileData.getName() +
                        ", Size: " + fileData.getSize() +
                        ", Path: " + fileData.getPath());
            }
        }
        String pathToRemove = "D:\\BODYA\\Hillel\\Projects\\DZ13\\files\\new_file.txt";
        navigator.remove(pathToRemove);
    }
}
