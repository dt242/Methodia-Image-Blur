import java.awt.Color;
import java.awt.image.BufferedImage;

public class ColorPreservingBrightnessBlurFilter implements ImageFilter {
    private final int radius;

    public ColorPreservingBrightnessBlurFilter(int radius) {
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
        float[] hsbBuffer = new float[3];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                long sumA = 0;
                double weightedBrightness = 0.0;
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
                            Color.RGBtoHSB(r, g, b, hsbBuffer);
                            float brightness = hsbBuffer[2];
                            sumA += a;
                            weightedBrightness += (double) brightness * a;
                            validPixels++;
                        }
                    }
                }

                int avgA = (int) (sumA / validPixels);
                float avgBrightness = 0.0f;
                if (sumA > 0) {
                    avgBrightness = (float) (weightedBrightness / sumA);
                }
                int centerRGB = image.getRGB(x, y);
                int cr = (centerRGB >> 16) & 0xFF;
                int cg = (centerRGB >> 8) & 0xFF;
                int cb = centerRGB & 0xFF;
                float[] centerHSB = Color.RGBtoHSB(cr, cg, cb, null);
                int blendedRGB = Color.HSBtoRGB(centerHSB[0], centerHSB[1], avgBrightness);
                int finalRGB = (avgA << 24) | (blendedRGB & 0x00FFFFFF);
                outputImage.setRGB(x, y, finalRGB);
            }
        }

        return outputImage;
    }
}