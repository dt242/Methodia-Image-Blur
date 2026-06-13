package filters;

import java.awt.Color;

public class ColorPreservingBrightnessBlurFilter extends BaseRadiusFilter {
    private long sumA;
    private double weightedBrightness;
    private int validPixels;
    private final float[] hsbBuffer = new float[3];

    public ColorPreservingBrightnessBlurFilter(int radius) {
        super(radius);
    }

    @Override
    protected void resetAccumulator() {
        sumA = 0; weightedBrightness = 0.0; validPixels = 0;
    }

    @Override
    protected void accumulate(int r, int g, int b, int a) {
        Color.RGBtoHSB(r, g, b, hsbBuffer);
        float brightness = hsbBuffer[2];
        sumA += a;
        weightedBrightness += (double) brightness * a;
        validPixels++;
    }

    @Override
    protected int resolvePixel(int centerRGB) {
        int avgA = validPixels == 0 ? 0 : (int) (sumA / validPixels);
        float avgBrightness = 0.0f;
        if (sumA > 0) {
            avgBrightness = (float) (weightedBrightness / sumA);
        }
        int cr = (centerRGB >> 16) & 0xFF;
        int cg = (centerRGB >> 8) & 0xFF;
        int cb = centerRGB & 0xFF;
        Color.RGBtoHSB(cr, cg, cb, hsbBuffer);
        int blendedRGB = Color.HSBtoRGB(hsbBuffer[0], hsbBuffer[1], avgBrightness);
        return (avgA << 24) | (blendedRGB & 0x00FFFFFF);
    }
}