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
            BufferedImage outputImage = new BufferedImage(width, height, image.getType());

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int sumR = 0, sumG = 0, sumB = 0;
                    int validPixels = 0;

                    for (int ky = -1; ky <= 1; ky++) {
                        for (int kx = -1; kx <= 1; kx++) {
                            int neighborX = x + kx;
                            int neighborY = y + ky;
                            if (neighborX >= 0 && neighborX < width && neighborY >= 0 && neighborY < height) {
                                int pixelRGB = image.getRGB(neighborX, neighborY);
                                Color color = new Color(pixelRGB);
                                sumR += color.getRed();
                                sumG += color.getGreen();
                                sumB += color.getBlue();
                                validPixels++;
                            }
                        }
                    }

                    int avgR = sumR / validPixels;
                    int avgG = sumG / validPixels;
                    int avgB = sumB / validPixels;
                    Color blurredColor = new Color(avgR, avgG, avgB);
                    outputImage.setRGB(x, y, blurredColor.getRGB());
                }
            }

            File outputFile = new File("blurred-image.jpg");
            ImageIO.write(outputImage, "jpg", outputFile);
        } catch (IOException e) {
            System.out.println("Image load error: " + e.getMessage());
        }
    }
}