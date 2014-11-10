package nl.marijnvdwerf.paletteknife;

import android.graphics.Bitmap;

public class ScaleResizeStrategy implements PaletteKnife.ResizeStrategy {

    private static final int CALCULATE_BITMAP_MIN_DIMENSION = 100;

    @Override
    public Bitmap resize(Bitmap bitmap) {
        int minDimension = Math.min(bitmap.getWidth(), bitmap.getHeight());
        if (minDimension <= CALCULATE_BITMAP_MIN_DIMENSION) {
            return bitmap;
        } else {
            float scaleRatio = CALCULATE_BITMAP_MIN_DIMENSION / (float) minDimension;
            return Bitmap.createScaledBitmap(bitmap, Math.round((float) bitmap.getWidth() * scaleRatio), Math.round((float) bitmap.getHeight() * scaleRatio), false);
        }
    }
}
