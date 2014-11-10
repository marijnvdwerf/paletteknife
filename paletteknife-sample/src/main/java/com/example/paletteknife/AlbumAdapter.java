package com.example.paletteknife;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class AlbumAdapter extends BaseAdapter {

    private static final Album[] ALBUMS = new Album[]{
            new Album("Pharrell Williams", "GIRL",
                    "UjdjwxH_6ap0K4bNFBCEOWA9fzGEkMpilFO0WxQdct20wEIqEIiDE7g07HXErt-VJ6UNRzcmpg.jpg"),
            new Album("One Republic", "Native",
                    "caZtabSZ4IST5xWwQc4689UHCZb49qyOlRkFA2PvUrKCQwFFvLLnZua-StVYf0C1Xi9mQOE1MUA.jpg"),
            new Album("Ellie Goulding", "Halcyon Days",
                    "h-Y_jcPLfjdEnq7mxHbw1oSn2V7X0b-9dGauYg-wOqLA-OMxo8dpDjb7dLCkSLGCID5r-C48.jpg"),
            new Album("Kodaline", "In a Perfect World",
                    "eedK8aCs2EfLUnjKfIRYtuMvZ7sJk6D0iscMSrXRwZGPwYrSfz32sfPXYF6bgOUdRHr8Niwf.jpg"),
            new Album("The Strokes", "Comedown Machine",
                    "2uPQcYYEivUCKYA8DuQ3OkDLzZWZJtNBdrJy4G8RhoDP_gPL8kNzUzJQWTW4WgYQJDn40Ykz49I.jpg"),
            new Album("Rhye", "Woman",
                    "BeiheXKP2r8hCiP3--QsmhdBz1NL9rGEiuGROmuEL86sV5-9D1NMmX9nQjTmHaIRws2SGpHJQg.jpg"),
            new Album("Fitz & The Tantrums", "More Than Just A Dream",
                    "OxaoIKHg1vkxGT4jCsW2F1OIf0Qg2RNLWnl-SD03_RRTcVVY-ls2PbIvdG4FNcvHaRl0AQcwmP8.jpg"),
            new Album("Jamie Lidell", "Jamie Lidell",
                    "uNsL42gush4ZGHC2X5tATZCCQCVhbbgSQIWXF8nPiE12rWmjAd0D8X-UOhLZST6JHIJPlu-z0A.jpg"),
            new Album("Foals", "Holy Fire",
                    "you_s2CtLintOhuaPqeDkwlma76tVY6GFROrAloPg_A00Vws9uDHy7u4Nfzb8V0aNuxQBbTwKw.jpg"),
            new Album("Janelle Monáe", "The Electric Lady",
                    "qjFkaOPDeoPciRCubI41XsYPHowK4Ch6s6wF4PGWypiY0Jto9tR-dULIq2LnrxLiJoTDVHiR.jpg")
    };

    @Override
    public int getCount() {
        return ALBUMS.length;
    }

    @Override
    public Album getItem(int position) {
        return ALBUMS[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final AlbumViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tile_album, parent, false);
            viewHolder = new AlbumViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (AlbumViewHolder) convertView.getTag();
        }

        Album album = getItem(position);
        Bitmap albumCover = AssetUtils.getBitmapFromAsset(viewHolder.itemView.getContext(), album.getCoverPath());
        viewHolder.cover.setImageBitmap(albumCover);
        viewHolder.name.setText(album.name);
        viewHolder.artist.setText(album.artist);

        onInflatedView(viewHolder, albumCover);

        return viewHolder.itemView;
    }

    public class AlbumViewHolder {
        public final View itemView;
        public final TextView name;
        public final TextView artist;
        public final ImageView cover;
        public final View textContainer;

        public AlbumViewHolder(View itemView) {
            this.itemView = itemView;
            this.cover = (ImageView) itemView.findViewById(R.id.album_cover);
            this.name = (TextView) itemView.findViewById(R.id.album_name);
            this.artist = (TextView) itemView.findViewById(R.id.album_artist);
            this.textContainer = itemView.findViewById(R.id.album_textcontainer);
        }
    }

    public abstract void onInflatedView(AlbumViewHolder albumViewHolder, Bitmap albumCover);
}
