import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class FilterFactory {

    private static final Map<String, Function<List<String>, ImageFilter>> registry = new HashMap<>();

    static {
        registry.put("boxblur", params -> new BoxBlurFilter(extractIntStrict(params, 0, "radius")));
        registry.put("averagebrightnessblur", params -> new GrayscaleBrightnessBlurFilter(extractIntStrict(params, 0, "radius")));
        registry.put("colorbrightnessblur", params -> new ColorPreservingBrightnessBlurFilter(extractIntStrict(params, 0, "radius")));
        registry.put("crop", params -> new CropFilter(
                extractIntStrict(params, 0, "x"),
                extractIntStrict(params, 1, "y"),
                extractIntStrict(params, 2, "width"),
                extractIntStrict(params, 3, "height")
        ));
    }

    private static int extractIntStrict(List<String> params, int index, String paramName) {
        if (params == null || params.size() <= index) {
            throw new IllegalArgumentException("Missing mandatory parameter '" + paramName + "'!");
        }
        try {
            return Integer.parseInt(params.get(index));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid parameter values '" + paramName + "': " + params.get(index));
        }
    }

    public static boolean isFilter(String name) {
        return registry.containsKey(name.toLowerCase().trim());
    }

    public static ImageFilter createFilter(String name, List<String> parameters) {
        String filterKey = name.toLowerCase().trim();
        if (!isFilter(filterKey)) {
            throw new IllegalArgumentException("Unknown filter: " + name);
        }
        return registry.get(filterKey).apply(parameters);
    }

    public static void registerFilter(String name, Function<List<String>, ImageFilter> constructor) {
        registry.put(name.toLowerCase().trim(), constructor);
    }
}