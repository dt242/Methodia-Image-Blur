import filters.ImageFilter;
import io.CommandLineParser;
import io.ImageFileManager;

import java.awt.image.BufferedImage;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        java Main images/input/small.jpg crop 150 50 400 300 boxblur 4 colorfilter green
//        java Main https://img.magnific.com/free-photo/beautiful-lake-mountains_395237-44.jpg?semt=ais_hybrid&w=740&q=80 crop 30 30 500 300 boxblur 3 colorfilter red

        if (args.length < 2) {
            System.out.println("Usage: java Main <imagepath_or_url> <filter1> [params...] <filter2> [params...]");
            return;
        }

        try {
            String imagePath = args[0];
            BufferedImage image = ImageFileManager.load(imagePath);
            List<ImageFilter> filters = CommandLineParser.parseFilters(args);

            for (ImageFilter filter : filters) {
                image = filter.apply(image);
            }

            String outputPath = "images/output/pipeline-result.png";
            ImageFileManager.save(image, outputPath, "png");
            System.out.println("Successfully applied " + filters.size() + " filters! Result: " + outputPath);

        } catch (Exception e) {
            System.out.println("Execution error: " + e.getMessage());
        }
    }
}