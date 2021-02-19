package ru.job4j.concurrent.synchronize;

import java.io.*;

public class ParseFile {
    private final File file;

    public ParseFile(File file)  {
        this.file = file;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() throws IOException {
        StringBuilder sb = new StringBuilder();
        try (InputStream i = new FileInputStream(file)) {
            int data;
            while ((data = i.read()) != -1) {
                sb.append(data);
            }
        }
        return sb.toString();
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        StringBuilder sb = new StringBuilder();
        try (InputStream i = new FileInputStream(file)) {
            int data;
            while ((data = i.read()) > 0) {
                if (data < 0x80) {
                    sb.append(data);
                }
            }
        }
        return sb.toString();
    }

    public synchronized void saveContent(String content) throws IOException {
        try (PrintWriter printWriter = new PrintWriter(new FileOutputStream(file))) {
            printWriter.write(content);
        }
    }

}