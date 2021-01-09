package com.example.tubes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;

public class TampilActivity extends AppCompatActivity {

    private ImageView imgFilm;
    private TextView tvJudul, tvTanggal, tvCaption, tvGenre, tvRating, tvSinopsis;
    private String linkFilm;
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil);

        imgFilm = findViewById(R.id.iv_film);
        tvJudul = findViewById(R.id.tv_judul);
        tvCaption = findViewById(R.id.tv_caption);
        tvTanggal = findViewById(R.id.tv_tanggal);
        tvGenre = findViewById(R.id.tv_genre);
        tvRating = findViewById(R.id.tv_rating);
        tvSinopsis = findViewById(R.id.tv_sinopsis);

        Intent terimaData = getIntent();
        tvJudul.setText(terimaData.getStringExtra("JUDUL"));
        tvCaption.setText(terimaData.getStringExtra("CAPTION"));
        tvTanggal.setText(terimaData.getStringExtra("TANGGAL"));
        tvGenre.setText(terimaData.getStringExtra("GENRE"));
        tvRating.setText(terimaData.getStringExtra("RATING"));
        tvSinopsis.setText(terimaData.getStringExtra("SINOPSIS"));
        String imgLocation = terimaData.getStringExtra("GAMBAR");

        try {
            File file = new File(imgLocation);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            imgFilm.setImageBitmap(bitmap);
            imgFilm.setContentDescription(imgLocation);
        } catch (FileNotFoundException er) {
            er.printStackTrace();
            Toast.makeText(this, "Gagal Mengambil Gambar dari Media Penyimpanan", Toast.LENGTH_SHORT).show();
        }

        linkFilm = terimaData.getStringExtra("LINK");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tampil_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.item_bagikan) {
            Intent bagikanFilm = new Intent(Intent.ACTION_SEND);
            bagikanFilm.putExtra(Intent.EXTRA_SUBJECT, tvJudul.getText().toString());
            bagikanFilm.putExtra(Intent.EXTRA_TEXT, linkFilm);
            bagikanFilm.setType("text/plain");
            startActivity(Intent.createChooser(bagikanFilm, "Bagikan Film"));
        }
        return super.onOptionsItemSelected(item);
    }
}
