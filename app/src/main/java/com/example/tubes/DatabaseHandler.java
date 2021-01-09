package com.example.tubes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 8;
    private final static String DATABASE_NAME = "db_filmku";
    private final static String TABLE_FILM = "t_film";
    private final static String KEY_ID_FILM = "ID_Film";
    private final static String KEY_JUDUL = "Judul";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_CAPTION = "Caption";
    private final static String KEY_TGL = "Tanggal";
    private final static String KEY_GENRE = "Genre";
    private final static String KEY_RATING = "Rating";
    private final static String KEY_SINOPSIS = "Sinopsis";
    private final static String KEY_LINK = "Link";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private Context context;

    public DatabaseHandler(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_FILM = "CREATE TABLE " + TABLE_FILM
                + "(" + KEY_ID_FILM + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUL + " TEXT, " + KEY_GAMBAR + " TEXT, "
                + KEY_CAPTION + " TEXT, " + KEY_TGL + " DATE, "
                + KEY_GENRE + " TEXT, " + KEY_RATING + " TEXT, "
                + KEY_SINOPSIS + " TEXT, " + KEY_LINK + " TEXT);";

        db.execSQL(CREATE_TABLE_FILM);
        inisialisasiFilmAwal(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_FILM;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void tambahFilm(Film dataFilm ) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_CAPTION, dataFilm.getCaption());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_RATING, dataFilm.getRating());
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis());
        cv.put(KEY_LINK, dataFilm.getLink());

        db.insert(TABLE_FILM, null, cv);
        db.close();
    }

    public void tambahFilm(Film dataFilm, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_CAPTION, dataFilm.getCaption());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_RATING, dataFilm.getRating());
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis());
        cv.put(KEY_LINK, dataFilm.getLink());

        db.insert(TABLE_FILM, null, cv);
    }

    public void editFilm(Film dataFilm ) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_CAPTION, dataFilm.getCaption());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_RATING, dataFilm.getRating());
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis());
        cv.put(KEY_LINK, dataFilm.getLink());

        db.update(TABLE_FILM, cv, KEY_ID_FILM + "=?", new String[]{String.valueOf(dataFilm.getIdFilm())});
        db.close();
    }

    public void hapusFilm(int idFilm) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_FILM, KEY_ID_FILM + "=?", new String[]{String.valueOf(idFilm)});
        db.close();
    }

    public ArrayList<Film> getAllFilm() {
        ArrayList<Film> dataFilm = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FILM;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if (csr.moveToFirst()) {
            do {
                Date tempDate = new Date();
                try {
                    tempDate = sdFormat.parse(csr.getString(4));
                } catch (ParseException er) {
                    er.printStackTrace();
                }

                Film tempFilm = new Film(
                        csr.getInt(0),
                        csr.getString(1),
                        csr.getString(2),
                        csr.getString(3),
                        tempDate,
                        csr.getString(5),
                        csr.getString(6),
                        csr.getString(7),
                        csr.getString(8)
                );

                dataFilm.add(tempFilm);
            }while (csr.moveToNext());
        }
        return dataFilm;
    }

    private String storeImageFile(int id) {
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inisialisasiFilmAwal(SQLiteDatabase db) {
        int idFilm = 0;
        Date tempDate = new Date();

        // Menambah Film 1
        try {
            tempDate = sdFormat.parse("23/03/2012");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Film film1 = new Film(
                idFilm,
                "The Raid : Redemption (2011)",
                storeImageFile(R.drawable.film1),
                "Poster Film The Raid : Redemption",
                tempDate,
                "Crime, Action, Thriller",
                "7,6/10",
                "Jauh di jantung daerah kumuh Jakarta terletak sebuah rumah yang aman tak tertembus untuk pembunuh paling \n" +
                        "\n" +
                        "berbahaya di dunia dan gangster. Sampai saat ini, blok apartemen kumuh telah dianggap tak tersentuh bahkan yang \n" +
                        "\n" +
                        "paling berani dari polisi. Berjubah bawah penutup pra-fajar kegelapan dan keheningan, tim swat elit bertugas \n" +
                        "\n" +
                        "merampok rumah aman untuk mencatat raja obat bius terkenal yang berjalan itu. Tapi ketika kesempatan pertemuan \n" +
                        "\n" +
                        "dengan spotter pukulan penutup mereka dan berita tentang serangan mereka mencapai gembong narkotika, lampu bangunan dipotong dan semua pintu keluar diblokir. Terdampar di lantai enam dengan tidak ada jalan keluar, \n" +
                        "\n" +
                        "unit harus berjuang mereka melalui terburuk untuk bertahan hidup misi mereka di kota ini. Dibintangi sensasi seni bela diri Indonesia Iko Uwais. \n",
                "https://www.youtube.com/watch?v=m6Q7KnXpNOg"
        );
        tambahFilm(film1, db);
        idFilm++;

        // Data Film 2
        tempDate = new Date();
        try {
            tempDate = sdFormat.parse("28/09/2017");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Film film2 = new Film(
                idFilm,
                "Pengabdi Setan (2017",
                storeImageFile(R.drawable.film2),
                "Poster Film Pengabdi Setan",
                tempDate,
                "Horror, Thriller",
                "6,6/10",
                "Rini tinggal di pinggiran kota Jakarta di sebuah rumah tua milik neneknya, Rahma Saidah.\n" +
                        "\n" +
                        "Mereka tinggal bersama ibunya, ayahnya, dan tiga adik laki-lakinya yakni Tony, Bondi, dan Ian.\n" +
                        "\n" +
                        "Saat itu keuangan keluarga mereka mulai menipis, sementara ibunya punya riwayat penyakit yang cukup parah sehingga uang tabungan mereka habis untuk pengobatan.\n" +
                        "\n" +
                        "Ibu Rini sulit menggerakan tubuhnya dan hanya berbaring di tempat tidur, ia hanya menggunakan bantuan lonceng/bel dari tempat tidurnya untuk meminta bantuan.\n" +
                        "\n" +
                        "Suatu ketika ibu Rini ditemukan jatuh di lantai kamar mandi dan meninggal dunia.\n" +
                        "\n" +
                        "Di sebuah pemakaman, keluarga Rini dikenalkan dengan seorang Ustaz dan putranya yang bernama Hendra.\n" +
                        "\n" +
                        "Kematian Ibunya tersebut mengawali teror di keluarga Rini. Mereka kerap melihat orang yang menyerupai mendiang ibunya.\n" +
                        "\n" +
                        "Suatu ketika Bondi menemukan neneknya meninggal di sumur.\n" +
                        "\n" +
                        "Rini menemukan sebuah surat yang ditujukan ke Budiman Syailendra dan Rini pun pergi mengantarkan surat itu ke rusun tempat tinggal Budiman bersama Hendra yang berada jauh dari rumahnya.\n" +
                        "\n" +
                        "Saat bertemu dengan Budiman, Rini mengetahui sebuah fakta mengenai ibunya.\n" +
                        "\n" +
                        "Budiman mengatakan kepada Rini bahwa Mawarni adalah seorang yang tidak bisa mempunyai anak.\n" +
                        "\n" +
                        "Karena itu, Mawarni pun dikatakan telah mengikuti sebuah sekte pemuja setan demi mendapatkan keturunan.\n" +
                        "\n" +
                        "Di rumahnya, Bondi mulai kerasukan dan hendak menyakiti adiknya, Ian.\n" +
                        "\n" +
                        "Teror terus berlangsung dan mereka mendatangkan sang Ustad untuk meminta bantuan.\n" +
                        "\n" +
                        "Suatu ketika, Budiman menelpon Hendra untuk datang ke rumah susun tempatnya tinggal.\n" +
                        "\n" +
                        "Hendra mendapat penjelasan terbaru dari Budiman, namun naas, di perjalanan pulang Hendra mengalami kecelakaan dan meninggal dunia.\n" +
                        "\n" +
                        "Ayah Rini yang tadinya sedang berada di kota, pulang ke rumah.\n" +
                        "\n" +
                        "Di malam itu banyak kejadian–kejadian mistis dan menakutkan yang terjadi di rumah mereka.\n" +
                        "\n" +
                        "Akhirnya, mereka sekeluarga berencana untuk pindah rumah.\n" +
                        "\n" +
                        "Di saat sudah siap untuk pindah, dan menunggu mobil yang akan mengangkut mereka, namun tak ada yang datang hingga malam tiba.\n" +
                        "\n" +
                        "Menunggu mobil yang tak kunjung datang, mereka pun tertidur.\n" +
                        "\n" +
                        "Tiba-tiba Rini terbangun dan teringat artikel Budiman yang dibawa Hendra, kemudian ia menceritakannya ke Tony.\n" +
                        "\n" +
                        "Malam itu, sekelompok mayat hidup berkumpul di depan rumah mereka.\n" +
                        "\n" +
                        "Sang Ustaz yang mencoba menolong keluarga ini juga diketahui ditusuk mati oleh para pengabdi setan tersebut.\n" +
                        "\n" +
                        "Di sana Ian kemudian berjalan ke kerumuman mayat hidup bersama Mawarni, ibunya.\n" +
                        "\n" +
                        "Mayat hidup memenuhi seisi rumah mereka.\n" +
                        "\n" +
                        "Akhirnya Budiman sampai di rumah Rini dan membawa keluarganya Rini pergi.\n" +
                        "\n" +
                        "Satu tahun kemudian, keluarga Rini telah menetap di sebuah rumah susun dan mereka dihampiri seorang tetangga yang memberikan mereka rantang makanan.\n" +
                        "\n" +
                        "Kemudian tetangga tersebut kembali ke rusunnya, di sana dia berbincang ke Batara dan terungkaplah rahasia yang selama ini tersembunyi. \n",
                "https://www.youtube.com/watch?v=0hSptYxWB3E"

        );
        tambahFilm(film2, db);
        idFilm++;

        // Data Film 3
        tempDate = new Date();
        try {
            tempDate = sdFormat.parse("11/12/2018");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Film film3 = new Film(
                idFilm,
                "Milly & Mamet : Ini Bukan Cinta & Rangga (2018)",
                storeImageFile(R.drawable.film3),
                "Poster Film Milly & Mamet : Ini Bukan Cinta & Rangga",
                tempDate,
                "Family, Comedy",
                "7,3/10",
                "Mamet yang sekarang tidak sama dengan Mamet ketika ia masih remaja 16 tahun yang lalu. Mamet tampak lebih gagah, meskipun tidak bisa menyembunyikan wajah blo’onnya. Ia kini sudah dewasa dan siap hidup selayaknya laki-laki.\n" +
                        "\n" +
                        "Sementara itu, Milly sudah siap menunjukkan dirinya sebagai wanita dewasa, meskipun tetap saja, kebiasaannya yang “tulalit” tidak bisa dihilangkan.\n" +
                        "\n" +
                        "16 tahun yang lalu, keduanya adalah sosok yang berlawanan. Mamet yang lugu, habis jadi bulan-bulanan Milly dan gengnya bersama Cinta, Karmen, Alya dan Maura. Kini, Mamet dan Milly dipertemukan kembali, di bawah malam yang sunyi, diantara temaramnya cahaya malam, karena mobil legendaris Mamet mogok di tengah jalan.\n" +
                        "\n" +
                        "Dari sinilah kisah Milly dan Mamet di mulai. Ada terjal-terjal kecil yang melingkupi kisah pasangan ini. Semuanya dibalut dengan dengan cinta dan tentu saja jenaka. Bukan sekadar komedi asal-asalan, namun, kisah Milly dan Mamet yang sangat dekat bagi kehidupan setiap orang. \n",
                "https://www.youtube.com/watch?v=zDbWeQP7N6w"

        );
        tambahFilm(film3, db);
        idFilm++;

        // Data Film 3
        tempDate = new Date();
        try {
            tempDate = sdFormat.parse("11/07/2019");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Film film4 = new Film(
                idFilm,
                "Dua Garis Biru (2019",
                storeImageFile(R.drawable.film4),
                "Poster Film Dua Garis Biru",
                tempDate,
                "Drama, Love",
                "8/10",
                "Film ini bercerita tentang sosok Bima, anak remaja yang duduk dibangku SMA dan memiliki banyak sahabat. Dia hidup di lingkungan keluarga yang damai dan saling mendukung.\n" +
                        "\n" +
                        "Namun, dalam perjalanannya, Bima dan pacaranya Dara kebablasan. Dara pun hamil. Mereka dihantui rasa takut dan berniat untuk menggugurkan kandungannya.\n" +
                        "\n" +
                        "Saat pelajaran olahraga Dara tidak sengaja keceplosan dan menyebutkan bahwa dirinya memilki bayi dalam perutnya. Hal ini membuat siswa dan gurunya kaget. PIhak sekolah pun memanggil kedua orang tua Bima dan Dara ke sekolah. Pada scene ini lah emosi pemain dan penonton mulai dimainkan.\n" +
                        "\n" +
                        "Kedua orang tua Bima dan Dara tidak tahu harus berbuat apa selain kecewa dengan apa yang mereka lakukan. Dan Bima harus bertanggung jawab dengan semua yang sudah dilakukan.\n" +
                        "\n" +
                        "Berjalannya waktu kedua orang tua Bima dan Dara mulai menerima keadaan walau pun masih merasa sangat kecewa. Hingga akhirnya Bima dan Dara memutuskan untuk menikah di usia muda.\n" +
                        "\n" +
                        "Bima bekerja di tempat ayah Dara untuk menambah biaya persalinan. Emosi pemain dan penonton dimainkan kembali saat Bima sibuk bermain game di ponselnya seperti remaja pada umumnya. Padahal Dara yang sedang hamil sensitif perilaku Bima. Terjadilah pertengkaran kecil yang membuat keduanya harus pisah rumah untuk sementara.\n" +
                        "\n" +
                        "Bima dan Dara bertahan sampai bayi dalam dalam kandungan lahir. Namun, kesedihan masih menyelimuti Dara ketika rahim Dara harus diangkat karena ada masalah dirahimnya dan membuat orang tua Dara merasakan kesedihan untuk kesekian kalinya.\n",
                "https://www.youtube.com/watch?v=b0NS7FP1loU"
        );
        tambahFilm(film4, db);
    }
}
