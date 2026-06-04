import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            test("small.jpg", "blurred-small.jpg", "jpg");
            test("big.jpg", "blurred-big.jpg", "jpg");
            test("small.png", "blurred-small.png", "png");
            test("small-trans.png", "blurred-small-trans.png", "png");
            test("small.avif", "blurred-from-avif.png", "png");
            test("small.webp", "blurred-from-webp.png", "png");
            test("small-trans.webp", "blurred-from-webp-trans.png", "png");

        } catch (IOException e) {
            System.out.println("Image load error: " + e.getMessage());
        }
    }

    public static void test(String input, String output, String format) throws IOException {
        BufferedImage image = ImageFileManager.load(input);
        ImageFilter filter = new BoxBlurFilter(3);
        BufferedImage resultImage = filter.apply(image);
        ImageFileManager.save(resultImage, output, format);
    }
}