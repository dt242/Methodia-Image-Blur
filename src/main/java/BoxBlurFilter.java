import java.awt.image.BufferedImage;

public class BoxBlurFilter implements ImageFilter {
    private final int radius;

    public BoxBlurFilter(int radius) {
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
                long weightedR = 0;
                long weightedG = 0;
                long weightedB = 0;
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
                            sumA += a;
                            weightedR += (long) r * a;
                            weightedG += (long) g * a;
                            weightedB += (long) b * a;
                            validPixels++;
                        }
                    }
                }

                int avgA = (int) (sumA / validPixels);
                int avgR = 0;
                int avgG = 0;
                int avgB = 0;
                if (sumA > 0) {
                    avgR = (int) (weightedR / sumA);
                    avgG = (int) (weightedG / sumA);
                    avgB = (int) (weightedB / sumA);
                }
                int blurredRGB = (avgA << 24) | (avgR << 16) | (avgG << 8) | avgB;
                outputImage.setRGB(x, y, blurredRGB);
            }
        }

        return outputImage;
    }
}