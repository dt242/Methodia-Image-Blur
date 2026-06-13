package filters;

import utils.ImageUtils;

import java.awt.image.BufferedImage;

public abstract class BaseRadiusFilter implements ImageFilter {
    protected final int radius;

    public BaseRadiusFilter(int radius) {
        if (radius < 0) {
            throw new IllegalArgumentException("Radius must be a positive number!");
        }
        this.radius = radius;
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage outputImage = createBlankOutputImage(image, width, height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int newRgb = processSinglePixel(image, x, y, width, height);
                outputImage.setRGB(x, y, newRgb);
            }
        }

        return outputImage;
    }

    private BufferedImage createBlankOutputImage(BufferedImage image, int width, int height) {
        int type = image.getColorModel().hasAlpha() ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
        return new BufferedImage(width, height, type);
    }

    private int processSinglePixel(BufferedImage image, int x, int y, int width, int height) {
        resetAccumulator();
        accumulateNeighbors(image, x, y, width, height);
        int centerRGB = image.getRGB(x, y);
        return resolvePixel(centerRGB);
    }

    private void accumulateNeighbors(BufferedImage image, int x, int y, int width, int height) {
        for (int ky = -radius; ky <= radius; ky++) {
            for (int kx = -radius; kx <= radius; kx++) {
                int neighborX = x + kx;
                int neighborY = y + ky;
                if (ImageUtils.isInside(neighborX, neighborY, width, height)) {
                    extractAndAccumulateColors(image, neighborX, neighborY);
                }
            }
        }
    }

    private void extractAndAccumulateColors(BufferedImage image, int x, int y) {
        int pixelRGB = image.getRGB(x, y);
        int a = (pixelRGB >> 24) & 0xFF;
        int r = (pixelRGB >> 16) & 0xFF;
        int g = (pixelRGB >> 8) & 0xFF;
        int b = pixelRGB & 0xFF;
        accumulate(r, g, b, a);
    }

    protected abstract void resetAccumulator();
    protected abstract void accumulate(int r, int g, int b, int a);
    protected abstract int resolvePixel(int centerRGB);
}