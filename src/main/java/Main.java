import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            testBox("images/input/small.jpg", "images/output/blurred-box-small.jpg", "jpg");
//            testBox("images/input/big.jpg", "images/output/blurred-box-big.jpg", "jpg");
            testBox("images/input/small.png", "images/output/blurred-box-small.png", "png");
            testBox("images/input/small-trans.png", "images/output/blurred-box-small-trans.png", "png");
            testBox("images/input/small.avif", "images/output/blurred-box-from-avif.png", "png");
//            testBox("images/input/small.webp", "images/output/blurred-box-from-webp.png", "png");
//            testBox("images/input/small-trans.webp", "images/output/blurred-box-from-webp-trans.png", "png");

            testBrightnessGrayscale("images/input/small.jpg", "images/output/blurred-grayscale-small.jpg", "jpg");
//            testBrightnessGrayscale("images/input/big.jpg", "images/output/blurred-grayscale-big.jpg", "jpg");
            testBrightnessGrayscale("images/input/small.png", "images/output/blurred-grayscale-small.png", "png");
            testBrightnessGrayscale("images/input/small-trans.png", "images/output/blurred-grayscale-small-trans.png", "png");
            testBrightnessGrayscale("images/input/small.avif", "images/output/blurred-grayscale-from-avif.png", "png");
//            testBrightness("images/input/small.webp", "images/output/blurred-grayscale-from-webp.png", "png");
//            testBrightness("images/input/small-trans.webp", "images/output/blurred-grayscale-from-webp-trans.png", "png");

            testBrightnessPreserveColor("images/input/small.jpg", "images/output/blurred-preserve-small.jpg", "jpg");
//            testBrightnessPreserveColor("images/input/big.jpg", "images/output/blurred-preserve-big.jpg", "jpg");
            testBrightnessPreserveColor("images/input/small.png", "images/output/blurred-preserve-small.png", "png");
            testBrightnessPreserveColor("images/input/small-trans.png", "images/output/blurred-preserve-small-trans.png", "png");
            testBrightnessPreserveColor("images/input/small.avif", "images/output/blurred-preserve-from-avif.png", "png");
//            testBrightnessPreserveColor("images/input/small.webp", "images/output/blurred-preserve-from-webp.png", "png");
//            testBrightnessPreserveColor("images/input/small-trans.webp", "images/output/blurred-preserve-from-webp-trans.png", "png");


        } catch (IOException e) {
            System.out.println("Image load error: " + e.getMessage());
        }
    }

    public static void testBox(String input, String output, String format) throws IOException {
        BufferedImage image = ImageFileManager.load(input);
        ImageFilter filter = new BoxBlurFilter(3);
        BufferedImage resultImage = filter.apply(image);
        ImageFileManager.save(resultImage, output, format);
    }

    public static void testBrightnessGrayscale(String input, String output, String format) throws IOException {
        BufferedImage image = ImageFileManager.load(input);
        ImageFilter filter = new GrayscaleBrightnessBlurFilter(3);
        BufferedImage resultImage = filter.apply(image);
        ImageFileManager.save(resultImage, output, format);
    }

    public static void testBrightnessPreserveColor(String input, String output, String format) throws IOException {
        BufferedImage image = ImageFileManager.load(input);
        ImageFilter filter = new ColorPreservingBrightnessBlurFilter(3);
        BufferedImage resultImage = filter.apply(image);
        ImageFileManager.save(resultImage, output, format);
    }
}