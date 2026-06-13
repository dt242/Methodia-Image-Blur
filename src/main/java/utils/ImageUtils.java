package utils;

public class ImageUtils {
    public static boolean isInside(int x, int y, int width, int height) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}