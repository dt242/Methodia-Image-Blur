package io;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class ImageFileManager {

    public static BufferedImage load(String path) throws IOException {
        BufferedImage image;
        if (path.toLowerCase().startsWith("http://") || path.toLowerCase().startsWith("https://")) {
            URL url = URI.create(path).toURL();
            image = ImageIO.read(url);
        } else {
            File file = new File(path);
            image = ImageIO.read(file);
        }
        if (image == null) {
            throw new IOException("Unsupported format or cannot read image from: " + path);
        }
        return image;
    }

    public static void save(BufferedImage image, String filePath, String format) throws IOException {
        File file = new File(filePath);
        boolean success = ImageIO.write(image, format, file);
        if (!success) {
            System.out.println("No Writer found for: " + format + ". File not saved!");
        }
    }
}