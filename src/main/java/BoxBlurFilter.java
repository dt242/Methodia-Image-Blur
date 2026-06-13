public class BoxBlurFilter extends BaseRadiusFilter {
    private long sumA, weightedR, weightedG, weightedB;
    private int validPixels;

    public BoxBlurFilter(int radius) {
        super(radius);
    }

    @Override
    protected void resetAccumulator() {
        sumA = 0; weightedR = 0; weightedG = 0; weightedB = 0; validPixels = 0;
    }

    @Override
    protected void accumulate(int r, int g, int b, int a) {
        sumA += a;
        weightedR += (long) r * a;
        weightedG += (long) g * a;
        weightedB += (long) b * a;
        validPixels++;
    }

    @Override
    protected int resolvePixel(int centerRGB) {
        int avgA = validPixels == 0 ? 0 : (int) (sumA / validPixels);
        int avgR = 0, avgG = 0, avgB = 0;
        if (sumA > 0) {
            avgR = (int) (weightedR / sumA);
            avgG = (int) (weightedG / sumA);
            avgB = (int) (weightedB / sumA);
        }
        return (avgA << 24) | (avgR << 16) | (avgG << 8) | avgB;
    }
}