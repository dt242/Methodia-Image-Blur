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
        int actualWidth = Math.min(cropWidth, image.getWidth() - x);
        int actualHeight = Math.min(cropHeight, image.getHeight() - y);
        if (actualWidth <= 0 || actualHeight <= 0) {
            throw new IllegalArgumentException("Crop area is outside the image!");
        }
        BufferedImage cropped = image.getSubimage(x, y, actualWidth, actualHeight);
        BufferedImage copy = new BufferedImage(cropped.getWidth(), cropped.getHeight(), image.getType());
        copy.getGraphics().drawImage(cropped, 0, 0, null);
        return copy;
    }
}