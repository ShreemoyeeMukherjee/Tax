package com.sm.tax.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

@Component
public class AppUtil {
public static String PATH = "src\\main\\resources\\static\\uploads\\";
   // public static String PATH = Paths.get("src", "main", "resources", "static", "uploads").toAbsolutePath().toString() + "/";

    public static String get_photo_upload_path(String fileName, String folder_name, long tax_id) throws IOException {
        System.out.println("1");
        String path = PATH + tax_id + "\\" + folder_name;
        System.out.println("Raw path"+path);
        System.out.println(Paths.get(path));
        Files.createDirectories(Paths.get(path));
        System.out.println("Directory created at: " + Paths.get(path).toAbsolutePath());
        return new File(path).getAbsolutePath() + "\\" + fileName;

    }
    public static Resource getFileAsResource(long tax_id, String folder_name, String file_name) throws IOException {
        System.out.println("hi");
        String location = PATH + tax_id + "\\" + folder_name + "\\" + file_name;
        System.out.println("LOcation is"+location);
        File file = new File(location);
        if (file.exists()) {
            Path path = Paths.get(file.getAbsolutePath());
            return new UrlResource(path.toUri());
        } else {
            return null;
        }

    }
}
