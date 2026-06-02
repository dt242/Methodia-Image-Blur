import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            File inputFile = new File("test-image.jpg");
            BufferedImage image = ImageIO.read(inputFile);
            int width = image.getWidth();
            int height = image.getHeight();
            System.out.println("Ширина: " + width + " px");
            System.out.println("Височина: " + height + " px");
            int pixelRGB = image.getRGB(0, 0);
            Color color = new Color(pixelRGB);

            System.out.println("Пиксел (0,0) цветове:");
            System.out.println("R: " + color.getRed());
            System.out.println("G: " + color.getGreen());
            System.out.println("B: " + color.getBlue());
        } catch (IOException e) {
            System.out.println("Грешка при зареждане на изображението: " + e.getMessage());
        }
    }
}