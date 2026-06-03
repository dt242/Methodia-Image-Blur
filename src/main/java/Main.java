import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            test("small.jpg", "blurred-small.jpg", "jpg");
            test("small.png", "blurred-small.png", "png");
            test("small.avif", "blurred-small.avif", "avif");
            test("small.webp", "blurred-small.webp", "webp");
            test("big.jpg", "blurred-big.jpg", "jpg");

        } catch (IOException e) {
            System.out.println("Image load error: " + e.getMessage());
        }
    }

    public static void test(String input, String output, String format) throws IOException {
        BufferedImage image = ImageFileManager.load(input);
        ImageFilter filter = new BoxBlurFilter(2);
        BufferedImage resultImage = filter.apply(image);
        ImageFileManager.save(resultImage, output, format);
    }
}