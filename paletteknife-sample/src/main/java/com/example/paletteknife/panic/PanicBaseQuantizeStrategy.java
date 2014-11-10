package com.example.paletteknife.panic;

import android.support.v7.graphics.Palette;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.marijnvdwerf.paletteknife.PaletteKnife;

public abstract class PanicBaseQuantizeStrategy implements PaletteKnife.QuantizeStrategy{

    private static final Comparator<Palette.Swatch> SWATCH_POPULATION_COMPARATOR
            = new Comparator<Palette.Swatch>() {
        @Override
        public int compare(Palette.Swatch lhs, Palette.Swatch rhs) {
            return rhs.getPopulation() - lhs.getPopulation();
        }
    };

    protected void sortColors(List<Palette.Swatch> colors) {
        Collections.sort(colors, SWATCH_POPULATION_COMPARATOR);
    }
}
