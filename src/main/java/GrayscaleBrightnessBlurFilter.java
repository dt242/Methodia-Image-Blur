import java.awt.image.BufferedImage;

public class GrayscaleBrightnessBlurFilter implements ImageFilter {
    private final int radius;

    public GrayscaleBrightnessBlurFilter(int radius) {
        if (radius < 0) {
            throw new IllegalArgumentException("Radius must be a positive number!");
        }
        this.radius = radius;
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int type = image.getColorModel().hasAlpha() ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
        BufferedImage outputImage = new BufferedImage(width, height, type);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                long sumA = 0;
                long weightedBrightness = 0;
                int validPixels = 0;

                for (int ky = -radius; ky <= radius; ky++) {
                    for (int kx = -radius; kx <= radius; kx++) {
                        int neighborX = x + kx;
                        int neighborY = y + ky;
                        if (ImageUtils.isInside(neighborX, neighborY, width, height)) {
                            int pixelRGB = image.getRGB(neighborX, neighborY);
                            int a = (pixelRGB >> 24) & 0xFF;
                            int r = (pixelRGB >> 16) & 0xFF;
                            int g = (pixelRGB >> 8) & 0xFF;
                            int b = pixelRGB & 0xFF;
                            int brightness = (r + g + b) / 3;
                            sumA += a;
                            weightedBrightness += (long) brightness * a;
                            validPixels++;
                        }
                    }
                }

                int avgA = (int) (sumA / validPixels);
                int avgBrightness = 0;
                if (sumA > 0) {
                    avgBrightness = (int) (weightedBrightness / sumA);
                }
                int blurredRGB = (avgA << 24) | (avgBrightness << 16) | (avgBrightness << 8) | avgBrightness;
                outputImage.setRGB(x, y, blurredRGB);
            }
        }

        return outputImage;
    }
}