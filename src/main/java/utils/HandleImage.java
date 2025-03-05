package utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;

public class HandleImage {

    public static String saveImage(Part imagePart, HttpServletRequest request) throws Exception {
        if (imagePart == null || imagePart.getSize() == 0) {
            return null; 
        }

        String fileName = imagePart.getSubmittedFileName();
        if (fileName == null || fileName.trim().isEmpty()) {
            return null;
        }

        String fileExtension = "";
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0) {
            fileExtension = fileName.substring(dotIndex);
        }

        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        String uploadDir = request.getServletContext().getRealPath("/image/Dishes");
        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        File file = new File(uploadPath, uniqueFileName);
        try ( InputStream input = imagePart.getInputStream();  FileOutputStream output = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        }

        return "image/Dishes/" + uniqueFileName;
    }
}
