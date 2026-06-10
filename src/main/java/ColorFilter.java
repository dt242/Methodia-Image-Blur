import java.awt.image.BufferedImage;

public class ColorFilter implements ImageFilter {
    private final String color;

    public ColorFilter(String color) {
        if (color == null || (!color.equalsIgnoreCase("red") && !color.equalsIgnoreCase("green") && !color.equalsIgnoreCase("blue"))) {
            throw new IllegalArgumentException("Color must be 'red', 'green', or 'blue'. Received: " + color);
        }
        this.color = color.toLowerCase();
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int type = image.getColorModel().hasAlpha() ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
        BufferedImage outputImage = new BufferedImage(width, height, type);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int a = (rgb >> 24) & 0xFF;
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;
                if (color.equals("red")) { g = 0; b = 0; }
                else if (color.equals("green")) { r = 0; b = 0; }
                else if (color.equals("blue")) { r = 0; g = 0; }
                int newRgb = (a << 24) | (r << 16) | (g << 8) | b;
                outputImage.setRGB(x, y, newRgb);
            }
        }
        return outputImage;
    }
}