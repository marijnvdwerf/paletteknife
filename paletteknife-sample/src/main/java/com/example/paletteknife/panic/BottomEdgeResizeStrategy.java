package com.example.paletteknife.panic;

import android.graphics.Bitmap;

import nl.marijnvdwerf.paletteknife.PaletteKnife;

public class BottomEdgeResizeStrategy implements PaletteKnife.ResizeStrategy {

    private static final int CROP_STRIP_HEIGHT = 1;
    private static final int SCALE_SIZE = 100;

    @Override
    public Bitmap resize(Bitmap bitmap) {
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, SCALE_SIZE, SCALE_SIZE, false);
        Bitmap croppedBitmap = Bitmap.createBitmap(scaledBitmap, 0, SCALE_SIZE - CROP_STRIP_HEIGHT,
                SCALE_SIZE, CROP_STRIP_HEIGHT);
        scaledBitmap.recycle();

        return croppedBitmap;
    }
}
