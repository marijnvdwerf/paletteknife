package com.example.paletteknife.panic;

import android.graphics.Color;
import android.support.v7.graphics.ColorHistogram;
import android.support.v7.graphics.ColorUtils;
import android.support.v7.graphics.Palette.Swatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PanicForegroundQuantizeStrategy extends PanicBaseQuantizeStrategy {

    private final int _backgroundColor;

    public PanicForegroundQuantizeStrategy(int backgroundColor) {
        _backgroundColor = backgroundColor;
    }

    @Override
    public List<Swatch> quantize(ColorHistogram histogram) {
        List<Swatch> sortedColors = new ArrayList<Swatch>();
        boolean findDarkTextColor = !isColorDark(_backgroundColor);

        for (int i = 0; i < histogram.getNumberOfColors(); i++) {
            int count = histogram.getColorCounts()[i];
            int color = histogram.getColors()[i];

            color = colorWithMinimumSaturation(color, .15f);

            if (isColorDark(color) == findDarkTextColor) {
                sortedColors.add(new Swatch(color, count));
            }
        }

        Swatch primaryColor = null;
        Swatch secondaryColor = null;

        for (Swatch curColor : sortedColors) {
            if (primaryColor == null) {
                if (isContrastingColor(curColor.getRgb(), _backgroundColor)) {
                    primaryColor = curColor;
                }
            } else {
                if (isContrastingColor(curColor.getRgb(), _backgroundColor)
                        && areColorsDistinct(curColor.getRgb(), primaryColor.getRgb())) {
                    secondaryColor = curColor;
                    break;
                }
            }

        }

        if (primaryColor == null) {
            return null;
        }

        if (secondaryColor == null) {
            return Arrays.asList(primaryColor);
        }

        return Arrays.asList(primaryColor, secondaryColor);
    }

    private boolean isContrastingColor(int foreground, int background) {
        return ColorUtils.calculateContrast(foreground, background) > 1.6;
        // W3C recommends 3:1 ratio, but that filters too many colors
    }

    private int colorWithMinimumSaturation(int rgb, float minSaturation) {
        float[] hsl = new float[3];
        ColorUtils.RGBtoHSL(Color.red(rgb), Color.green(rgb), Color.blue(rgb), hsl);

        if (hsl[1] < minSaturation) {
            hsl[1] = minSaturation;
            return ColorUtils.HSLtoRGB(hsl);
        }

        return rgb;
    }

    private static boolean isColorDark(int color) {
        double luminance = ColorUtils.calculateLuminance(color);

        if (luminance < 0.5) {
            return true;
        }

        return false;
    }

    private static boolean areColorsDistinct(int color, int compareColor) {
        final int treshold = (int) (255 * .25);

        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);

        int r1 = Color.red(compareColor);
        int g1 = Color.green(compareColor);
        int b1 = Color.blue(compareColor);

        if (Math.abs(r - r1) > treshold ||
                Math.abs(g - g1) > treshold ||
                Math.abs(b - b1) > treshold) {

            // check for grays, prevent multiple gray colors
            int graysTreshold = 8;
            if (Math.abs(r - g) < graysTreshold && Math.abs(r - b) < graysTreshold
                    && Math.abs(r1 - g1) < graysTreshold && Math.abs(r1 - b1) < graysTreshold) {
                return false;
            }

            return true;
        }

        return false;
    }
}
