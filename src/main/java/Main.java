import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            File inputFile = new File("test-image.jpg");
            BufferedImage image = ImageIO.read(inputFile);
            ImageFilter filter = new BoxBlurFilter(2);
            BufferedImage outputImage = filter.apply(image);
            File outputFile = new File("blurred-image.jpg");
            ImageIO.write(outputImage, "jpg", outputFile);

        } catch (IOException e) {
            System.out.println("Image load error: " + e.getMessage());
        }
    }
}