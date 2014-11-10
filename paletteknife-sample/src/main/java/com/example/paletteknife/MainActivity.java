package com.example.paletteknife;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;


public class MainActivity extends Activity {

    private GridView _grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _grid = (GridView) findViewById(R.id.grid);
        _grid.setAdapter(new AlbumAdapter());
    }

}
