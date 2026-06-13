package filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CropFilter implements ImageFilter {
    private final int x;
    private final int y;
    private final int cropWidth;
    private final int cropHeight;

    public CropFilter(int x, int y, int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Height and width must be positive numbers!");
        }
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Coordinates x and y cannot be negative!");
        }
        this.x = x;
        this.y = y;
        this.cropWidth = width;
        this.cropHeight = height;
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        if (x + cropWidth > image.getWidth() || y + cropHeight > image.getHeight()) {
            throw new IllegalArgumentException("Crop area exceeds the boundaries of the original image!");
        }
        BufferedImage cropped = image.getSubimage(x, y, cropWidth, cropHeight);
        BufferedImage copy = new BufferedImage(cropped.getWidth(), cropped.getHeight(), image.getType());
        Graphics2D g2d = copy.createGraphics();

        try {
            g2d.drawImage(cropped, 0, 0, null);
        } finally {
            g2d.dispose();
        }

        return copy;
    }
}