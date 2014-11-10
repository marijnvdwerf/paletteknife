package com.example.paletteknife;

import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;

import nl.marijnvdwerf.paletteknife.PaletteKnife;

public class SupportLibraryAlbumAdapter extends AlbumAdapter {

    private static final PaletteKnife SUPPORT_LIBRARY_PALETTE_KNIFE = new PaletteKnife.Builder()
            .build();

    @Override
    public void onInflatedView(final AlbumViewHolder viewHolder, Bitmap albumCover) {
        SUPPORT_LIBRARY_PALETTE_KNIFE
                .generatePaletteAsync(albumCover, new PaletteKnife.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Palette.Swatch swatch = palette.getVibrantSwatch();
                        if (swatch == null) {
                            return;
                        }

                        viewHolder.textContainer.setBackgroundColor(swatch.getRgb());
                    }
                });

    }
}
