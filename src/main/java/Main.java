import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            String filterName = "grayscale-brightness";
            Map<String, String> params = Map.of("radius", "3");
            test("images/input/small.jpg", "images/output/factory-test-small.jpg", "jpg", filterName, params);

        } catch (IOException e) {
            System.out.println("Image load error: " + e.getMessage());
        }
    }

    public static void test(String input, String output, String format, String filterName, Map<String, String> params) throws IOException {
        BufferedImage image = ImageFileManager.load(input);
        ImageFilter filter = FilterFactory.createFilter(filterName, params);
        BufferedImage resultImage = filter.apply(image);
        ImageFileManager.save(resultImage, output, format);
        System.out.println("Successfully applied : " + filterName + " With parameters: " + params);
    }
}