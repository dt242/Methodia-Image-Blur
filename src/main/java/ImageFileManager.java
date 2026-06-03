import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageFileManager {

    public static BufferedImage load(String filePath) throws IOException {
        File file = new File(filePath);
        BufferedImage image = ImageIO.read(file);
        if (image == null) {
            throw new IOException("Unsupported format: " + filePath);
        }
        return image;
    }

    public static void save(BufferedImage image, String filePath, String format) throws IOException {
        File file = new File(filePath);
        ImageIO.write(image, format, file);
    }
}