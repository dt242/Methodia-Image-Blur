import javax.imageio.ImageIO;
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
            int radius = 2;
            System.out.println("Radius " + radius);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int sumR = 0, sumG = 0, sumB = 0;
                    int validPixels = 0;

                    for (int ky = -radius; ky <= radius; ky++) {
                        for (int kx = -radius; kx <= radius; kx++) {
                            int neighborX = x + kx;
                            int neighborY = y + ky;
                            if (neighborX >= 0 && neighborX < width && neighborY >= 0 && neighborY < height) {
                                int pixelRGB = image.getRGB(neighborX, neighborY);
                                int r = (pixelRGB >> 16) & 0xFF;
                                int g = (pixelRGB >> 8) & 0xFF;
                                int b = pixelRGB & 0xFF;
                                sumR += r;
                                sumG += g;
                                sumB += b;
                                validPixels++;
                            }
                        }
                    }

                    int avgR = sumR / validPixels;
                    int avgG = sumG / validPixels;
                    int avgB = sumB / validPixels;
                    int blurredRGB = (255 << 24) | (avgR << 16) | (avgG << 8) | avgB;
                    outputImage.setRGB(x, y, blurredRGB);
                }
            }

            File outputFile = new File("blurred-image.jpg");
            ImageIO.write(outputImage, "jpg", outputFile);
        } catch (IOException e) {
            System.out.println("Image load error: " + e.getMessage());
        }
    }
}