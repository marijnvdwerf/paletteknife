package nl.marijnvdwerf.paletteknife;


import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.graphics.ColorHistogram;
import android.support.v7.graphics.Palette;

import java.util.List;

public class PaletteKnife {
    private final ResizeStrategy _resizeStrategy;
    private final QuantizeStrategy _quantizeStrategy;

    private PaletteKnife(ResizeStrategy resizeStrategy, QuantizeStrategy quantizeStrategy) {
        this._resizeStrategy = resizeStrategy;
        _quantizeStrategy = quantizeStrategy;
    }

    public Palette generatePalette(Bitmap bitmap) {
        checkBitmapParam(bitmap);

        // First we'll scale down the bitmap
        Bitmap scaledBitmap = _resizeStrategy.resize(bitmap);

        int width = scaledBitmap.getWidth();
        int height = scaledBitmap.getHeight();
        int pixels[] = new int[width * height];
        scaledBitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        // If created a new bitmap, recycle it
        if (scaledBitmap != bitmap) {
            scaledBitmap.recycle();
        }

        // Now generate a histogram from the Bitmap pixels
        ColorHistogram colorHistogram = new ColorHistogram(pixels);

        // Extract the colors
        List<Palette.Swatch> colors = _quantizeStrategy.quantize(colorHistogram);
        return new Palette(colors);
    }

    public AsyncTask<Bitmap, Void, Palette> generatePaletteAsync(final Bitmap bitmap, final PaletteAsyncListener listener) {
        checkBitmapParam(bitmap);
        checkAsyncListenerParam(listener);

        return AsyncTaskCompat.executeParallel(
                new AsyncTask<Bitmap, Void, Palette>() {
                    @Override
                    protected Palette doInBackground(Bitmap... params) {
                        return generatePalette(params[0]);
                    }

                    @Override
                    protected void onPostExecute(Palette colorExtractor) {
                        listener.onGenerated(colorExtractor);
                    }
                }, bitmap);
    }

    private static void checkBitmapParam(Bitmap bitmap) {
        if (bitmap == null) {
            throw new IllegalArgumentException("bitmap can not be null");
        }
        if (bitmap.isRecycled()) {
            throw new IllegalArgumentException("bitmap can not be recycled");
        }
    }

    private static void checkAsyncListenerParam(PaletteAsyncListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("listener can not be null");
        }
    }

    public static class Builder {
        QuantizeStrategy _quantizeStrategy;
        ResizeStrategy _resizeStrategy;

        public Builder setQuantizeStrategy(QuantizeStrategy strategy) {
            _quantizeStrategy = strategy;

            return this;
        }

        public Builder setResizeStrategy(ResizeStrategy strategy) {
            _resizeStrategy = strategy;

            return this;
        }

        public PaletteKnife build() {
            if (_resizeStrategy == null) {
                _resizeStrategy = new ScaleResizeStrategy();
            }

            if (_quantizeStrategy == null) {
                _quantizeStrategy = new GoogleQuantizeStrategy();
            }

            return new PaletteKnife(_resizeStrategy, _quantizeStrategy);
        }
    }

    /**
     * Listener to be used with {@link #generatePaletteAsync(Bitmap, PaletteAsyncListener)}
     */
    public interface PaletteAsyncListener {

        /**
         * Called when the {@link Palette} has been generated.
         */
        void onGenerated(Palette palette);
    }

    public interface QuantizeStrategy {
        public List<Palette.Swatch> quantize(ColorHistogram histogram);
    }

    public interface ResizeStrategy {
        public Bitmap resize(Bitmap bitmap);
    }
}
