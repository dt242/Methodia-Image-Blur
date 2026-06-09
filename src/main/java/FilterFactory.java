import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class FilterFactory {

    private static final Map<String, Function<Map<String, String>, ImageFilter>> registry = new HashMap<>();

    static {
        registry.put("box-blur", params -> new BoxBlurFilter(extractRadius(params)));
        registry.put("grayscale-brightness", params -> new GrayscaleBrightnessBlurFilter(extractRadius(params)));
        registry.put("color-brightness", params -> new ColorPreservingBrightnessBlurFilter(extractRadius(params)));
    }

    private static int extractRadius(Map<String, String> params) {
        if (params != null && params.containsKey("radius")) {
            return Integer.parseInt(params.get("radius"));
        }
        return 0;
    }

    public static ImageFilter createFilter(String name, Map<String, String> parameters) {
        String filterKey = name.toLowerCase().trim();
        if (!registry.containsKey(filterKey)) {
            throw new IllegalArgumentException("Unknown filter: " + name);
        }
        return registry.get(filterKey).apply(parameters);
    }

    public static void registerFilter(String name, Function<Map<String, String>, ImageFilter> constructor) {
        registry.put(name.toLowerCase().trim(), constructor);
    }
}