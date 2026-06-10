import java.awt.image.BufferedImage;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        args = new String[]{"images/input/small.jpg", "crop", "150", "40", "400", "300", "boxblur", "3", "colorfilter", "red"};
        if (args.length < 2) {
            System.out.println("Usage: java Main <imagepath> <filter1> [params...] <filter2> [params...]");
            return;
        }

        try {
            String imagePath = args[0];
            BufferedImage image = ImageFileManager.load(imagePath);
            List<ImageFilter> filters = CommandLineParser.parseFilters(args);

            for (ImageFilter filter : filters) {
                image = filter.apply(image);
            }

            String extension = imagePath.substring(imagePath.lastIndexOf(".") + 1);
            String outputPath = "images/output/pipeline-result." + extension;
            ImageFileManager.save(image, outputPath, extension);
            System.out.println("Successfully applied " + filters.size() + " filters! Result: " + outputPath);

        } catch (Exception e) {
            System.out.println("Image load error: " + e.getMessage());
        }
    }
}