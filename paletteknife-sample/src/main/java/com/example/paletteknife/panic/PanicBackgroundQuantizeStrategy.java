package com.example.paletteknife.panic;

import android.graphics.Color;
import android.support.v7.graphics.ColorHistogram;
import android.support.v7.graphics.Palette;
import android.support.v7.graphics.Palette.Swatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PanicBackgroundQuantizeStrategy extends PanicBaseQuantizeStrategy {


    @Override
    public List<Palette.Swatch> quantize(ColorHistogram histogram) {
        List<Swatch> sortedColors = new ArrayList<Swatch>(histogram.getNumberOfColors());
        for (int i = 0; i < histogram.getNumberOfColors(); i++) {
            int count = histogram.getColorCounts()[i];
            int color = histogram.getColors()[i];
            sortedColors.add(new Palette.Swatch(color, count));
        }

        sortColors(sortedColors);

        Swatch proposedEdgeColor = sortedColors.get(0);

        if (isColorBackOrWhite(proposedEdgeColor)) {
            // want to choose color over black/white so we keep looking

            for (int i = 1; i < sortedColors.size(); i++) {
                Swatch nextProposedColor = sortedColors.get(0);

                if (((double) nextProposedColor.getPopulation() / (double) proposedEdgeColor
                        .getPopulation()) > .3) {
                    // make sure the second choice color is 30% as common as the first choice

                    if (!isColorBackOrWhite(nextProposedColor)) {
                        proposedEdgeColor = nextProposedColor;
                        break;
                    }
                } else {
                    // reached color threshold less than 30% of the original proposed edge color so bail
                    break;
                }
            }
        }

        return Arrays.asList(proposedEdgeColor);
    }

    private static boolean isColorBackOrWhite(Swatch swatch) {
        int rgbColor = swatch.getRgb();
        int r = Color.red(rgbColor);
        int g = Color.green(rgbColor);
        int b = Color.blue(rgbColor);

        if (r > 232 && g > 232 && b > 232) {
            return true; // white
        }

        if (r < 23 && g < 23 && b < 23) {
            return true; // black
        }

        return false;
    }
}
