package nl.marijnvdwerf.paletteknife;

import android.support.v7.graphics.ColorCutQuantizer;
import android.support.v7.graphics.ColorHistogram;
import android.support.v7.graphics.Palette.Swatch;

import java.util.List;

public class GoogleQuantizeStrategy implements PaletteKnife.QuantizeStrategy {

    private static final int DEFAULT_CALCULATE_NUMBER_COLORS = 16;
    private int _numColors;

    public GoogleQuantizeStrategy() {
        this(DEFAULT_CALCULATE_NUMBER_COLORS);
    }

    public GoogleQuantizeStrategy(int numColors) {
        if (numColors < 1) {
            throw new IllegalArgumentException("numColors must be 1 of greater");
        }

        _numColors = numColors;
    }

    @Override
    public List<Swatch> quantize(ColorHistogram histogram) {
        ColorCutQuantizer quantizer = new ColorCutQuantizer(histogram, _numColors);
        return quantizer.getQuantizedColors();
    }
}
