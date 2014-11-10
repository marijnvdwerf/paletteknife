package com.example.paletteknife;

import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;

import com.example.paletteknife.panic.BottomEdgeResizeStrategy;
import com.example.paletteknife.panic.PanicBackgroundQuantizeStrategy;
import com.example.paletteknife.panic.PanicForegroundQuantizeStrategy;

import nl.marijnvdwerf.paletteknife.PaletteKnife;

public class PanicAlbumAdapter extends AlbumAdapter {

    private static final PaletteKnife CUSTOM_PALETTE_KNIFE = new PaletteKnife.Builder()
            .setResizeStrategy(new BottomEdgeResizeStrategy())
            .setQuantizeStrategy(new PanicBackgroundQuantizeStrategy())
            .build();

    @Override
    public void onInflatedView(final AlbumAdapter.AlbumViewHolder viewHolder, final Bitmap albumCover) {
        CUSTOM_PALETTE_KNIFE.generatePaletteAsync(albumCover, new PaletteKnife.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                final Palette.Swatch backgroundSwatch = palette.getSwatches().get(0);
                int backgroundColor = backgroundSwatch.getRgb();
                viewHolder.textContainer.setBackgroundColor(backgroundColor);

                new PaletteKnife.Builder()
                        .setQuantizeStrategy(new PanicForegroundQuantizeStrategy(backgroundColor))
                        .build()
                        .generatePaletteAsync(albumCover, new PaletteKnife.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                if (palette.getSwatches().size() == 0) {
                                    viewHolder.name.setTextColor(backgroundSwatch.getBodyTextColor());
                                    return;
                                }

                                int primaryColor = palette.getSwatches().get(0).getRgb();
                                int secondaryColor = primaryColor;
                                if (palette.getSwatches().size() > 1) {
                                    secondaryColor = palette.getSwatches().get(1).getRgb();
                                }
                                viewHolder.name.setTextColor(primaryColor);
                                viewHolder.artist.setTextColor(secondaryColor);
                            }
                        });
            }
        });
    }
}
