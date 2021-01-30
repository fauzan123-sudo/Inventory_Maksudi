package com.example.maksudi.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.maksudi.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.maksudi.ui.ListBarang.url_edit;

public class EditBarang extends AppCompatActivity  {

    TextView id,tanggal,barcode;
    EditText jenisBarang,hargaBarang,jumlahBarang,namaBarang;
    ImageView gambarBarang;

    private static final String TAG = EditBarang.class.getSimpleName();
    private static final String TAG_NamaBarang       = "nama";
    private static final String TAG_JenisBarang      = "jenis";
    private static final String TAG_Harga            = "harga";
    private static final String TAG_Gambar           = "gambar";
    private static final String TAG_Barcode          = "barcode";
    private static final String TAG_JumlahBarang     = "jumlah";
    private static final String TAG_Tanggal          = "tanggal";

    private static final String TAG_ID               = "id";

    String id_news;
    ProgressBar pg;
    int success;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_barang);

        id          = findViewById(R.id.id1);
        namaBarang  = findViewById(R.id.namaBarang1);
        jumlahBarang= findViewById(R.id.jumlahBarang1);
        jenisBarang = findViewById(R.id.jenisBarang1);
        tanggal     = findViewById(R.id.tanggal1);
        barcode     = findViewById(R.id.scanBarcode1);
        hargaBarang = findViewById(R.id.hargaBarang1);
        gambarBarang= findViewById(R.id.tambahGambar1);
        id_news = getIntent().getStringExtra(TAG_ID);

        pg = findViewById(R.id.progressBar);

        tampilDataedit(id_news);
        progressDialog = new ProgressDialog(this);

}
    private void tampilDataedit(String id_news) {
        StringRequest strReq = new StringRequest(Request.Method.POST, url_edit,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Response " + response);

                try {
                    JSONObject obj = new JSONObject(response);
                    success = obj.getInt("success");

                    if (success == 1) {
                        String idx1         = obj.getString("id");
                        String namaBarang1  = obj.getString("nama");
                        String jenisBarang1 = obj.getString("jenis");
                        int jumlahBarang1   = obj.getInt("jumlah");
                        String tanggal1     = obj.getString("tanggal");
                        String barcode1     = obj.getString("barcode");
                        int hargaBarang1    = obj.getInt("harga");
                        String gambarBarang1 = obj.getString("gambar");

                        id.setText(idx1);
                        namaBarang.setText(namaBarang1);
                        jumlahBarang.setText(String.valueOf(jumlahBarang1));
                        jenisBarang.setText(jenisBarang1);
                        Log.e(TAG, "jenis " + jenisBarang);
                        tanggal.setText(tanggal1);
                        barcode.setText(barcode1);
                        hargaBarang.setText(String.valueOf(hargaBarang1));
                        Picasso.get().load(gambarBarang1).into(gambarBarang);
                    }
                    if (success == 0) {
                        Toast.makeText(getApplicationContext(), "sukses 0", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Ada error", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, error -> {
            Log.e(TAG, "Detail News Error: " + error.getMessage());
            Toast.makeText(EditBarang.this,
                    error.getMessage(), Toast.LENGTH_LONG).show();
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to post url
                Map<String, String> params = new HashMap<>();
                params.put("id", id_news);

                return params;
            }

        };

        // Adding request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strReq);
    }
}


