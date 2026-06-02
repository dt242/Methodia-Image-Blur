import java.awt.image.BufferedImage;

public class BoxBlurFilter implements ImageFilter {
    private int radius;

    public BoxBlurFilter(int radius) {
        this.radius = radius;
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage outputImage = new BufferedImage(width, height, image.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int sumR = 0, sumG = 0, sumB = 0;
                int validPixels = 0;

                for (int ky = -radius; ky <= radius; ky++) {
                    for (int kx = -radius; kx <= radius; kx++) {
                        int neighborX = x + kx;
                        int neighborY = y + ky;
                        if (ImageUtils.isInside(neighborX, neighborY, width, height)) {
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

        return outputImage;
    }
}