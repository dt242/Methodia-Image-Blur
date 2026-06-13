public class GrayscaleBrightnessBlurFilter extends BaseRadiusFilter {
    private long sumA, weightedBrightness;
    private int validPixels;

    public GrayscaleBrightnessBlurFilter(int radius) {
        super(radius);
    }

    @Override
    protected void resetAccumulator() {
        sumA = 0; weightedBrightness = 0; validPixels = 0;
    }

    @Override
    protected void accumulate(int r, int g, int b, int a) {
        int brightness = (r + g + b) / 3;
        sumA += a;
        weightedBrightness += (long) brightness * a;
        validPixels++;
    }

    @Override
    protected int resolvePixel(int centerRGB) {
        int avgA = validPixels == 0 ? 0 : (int) (sumA / validPixels);
        int avgBrightness = 0;
        if (sumA > 0) {
            avgBrightness = (int) (weightedBrightness / sumA);
        }
        return (avgA << 24) | (avgBrightness << 16) | (avgBrightness << 8) | avgBrightness;
    }
}