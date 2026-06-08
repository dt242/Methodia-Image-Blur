import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            test("images/input/small.jpg", "images/output/blurred-brightness-small.jpg", "jpg");
            test("images/input/big.jpg", "images/output/blurred-brightness-big.jpg", "jpg");
            test("images/input/small.png", "images/output/blurred-brightness-small.png", "png");
            test("images/input/small-trans.png", "images/output/blurred-brightness-small-trans.png", "png");
            test("images/input/small.avif", "images/output/blurred-brightness-from-avif.png", "png");
            test("images/input/small.webp", "images/output/blurred-brightness-from-webp.png", "png");
            test("images/input/small-trans.webp", "images/output/blurred-brightness-from-webp-trans.png", "png");

        } catch (IOException e) {
            System.out.println("Image load error: " + e.getMessage());
        }
    }

    public static void test(String input, String output, String format) throws IOException {
        BufferedImage image = ImageFileManager.load(input);
        ImageFilter filter = new AverageBrightnessBlurFilter(3);
        BufferedImage resultImage = filter.apply(image);
        ImageFileManager.save(resultImage, output, format);
    }
}