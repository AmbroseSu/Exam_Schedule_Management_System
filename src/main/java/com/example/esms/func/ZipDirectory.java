package com.example.esms.func;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipDirectory {
    public void zipDirectory(File inputDir, File outputZipFile) {
    outputZipFile.getParentFile().mkdirs();

    String inputDirPath = inputDir.getAbsolutePath();
    byte[] buffer = new byte[1024];

    FileOutputStream fileOs = null;
    ZipOutputStream zipOs = null;
		try {
        List<File> allFiles = this.listChildFiles(inputDir);
        fileOs = new FileOutputStream(outputZipFile);
        zipOs = new ZipOutputStream(fileOs);
        for (File file : allFiles) {
            String filePath = file.getAbsolutePath();
            String entryName = filePath.substring(inputDirPath.length() + 1);
            ZipEntry ze = new ZipEntry(entryName);
            zipOs.putNextEntry(ze);
            FileInputStream fileIs = new FileInputStream(filePath);
            int len;
            while ((len = fileIs.read(buffer)) > 0) {
                zipOs.write(buffer, 0, len);
            }
            fileIs.close();
        }
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        closeQuite(zipOs);
        closeQuite(fileOs);
    }
}
    private void closeQuite(OutputStream out) {
        try {
            out.close();
        } catch (Exception e) {
        }
    }

    private List<File> listChildFiles(File dir) throws IOException {
        List<File> allFiles = new ArrayList<>();

        File[] childFiles = dir.listFiles();
        for (File file : childFiles) {
            if (file.isFile()) {
                allFiles.add(file);
            } else {
                List<File> files = this.listChildFiles(file);
                allFiles.addAll(files);
            }
        }
        return allFiles;
    }
}
