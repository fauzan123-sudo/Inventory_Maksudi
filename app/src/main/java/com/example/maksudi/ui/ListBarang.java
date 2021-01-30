package com.example.maksudi.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.maksudi.R;
import com.example.maksudi.data.AdapterBarang;
import com.example.maksudi.data.Server;
import com.example.maksudi.data.SingletonGambar;
import com.example.maksudi.data.model.Data;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListBarang extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    FloatingActionButton fab;
    ListView list;
    SwipeRefreshLayout swipe;
    List<Data> itemList = new ArrayList<>();
    AdapterBarang adapter;
    int success;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    TextView idnya,id1;
    String id;

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String url_select 	 = Server.URL + "select.php";
    public static final String url_insert 	 = Server.URL + "insert.php";
    public static final String url_edit 	 = Server.URL + "edit.php";
    public static final String url_update 	 = Server.URL + "update.php";
    public static final String url_delete 	 = Server.URL + "delete.php";

    public static final String TAG_NamaBarang       = "nama_barang";
    public static final String TAG_JenisBarang      = "jenis_barang";
    public static final String TAG_Harga            = "harga";
    public static final String TAG_Gambar           = "gambar";
    public static final String TAG_Barcode          = "barcode";
    public static final String TAG_JumlahBarang     = "jumlah";
    public static final String TAG_Tanggal          = "tanggal";
    public static final String TAG_SUCCESS          = "success";
    public static final String TAG_MESSAGE          = "message";
    public static final String TAG_ID               = "id";

    String tag_json_obj = "json_obj_req";

    ImageView editGambar;
    EditText editJenisBarang,editHargaBarang;
    EditText editJumlahBarang;
    TextView editTanggalBarang;
    TextView editScanBarcode;
    EditText editNamaBarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_barang);

        fab     =  findViewById(R.id.fab_add);
        swipe   =  findViewById(R.id.swipe_refresh_layout);
        list    =  findViewById(R.id.list_barang);
        idnya   =  findViewById(R.id.id);

        // todo untuk mengisi data dari JSON ke dalam adapter
        adapter = new AdapterBarang(this, itemList);

        list.setAdapter(adapter);
        list.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(ListBarang.this, EditBarang.class);
            intent.putExtra(TAG_ID, itemList.get(i).getId());
            startActivity(intent);
        });

        // todo menamilkan widget refresh
        swipe.setOnRefreshListener(this);

        swipe.post(() -> {
            swipe.setRefreshing(true);
            itemList.clear();
            adapter.notifyDataSetChanged();
            callVolley();
        });

        // todo fungsi floating action button memanggil form biodata
        fab.setOnClickListener(view -> {
            Intent r = new Intent(this,TambahBarang.class);
           startActivity(r);
        });

        // todo listview ditekan lama akan menampilkan dua pilihan edit atau delete data
        list.setOnItemLongClickListener((parent, view, position, id) -> {
            final String idx = itemList.get(position).getId();

            final CharSequence[] dialogitem = {"Edit", "Delete"};
            dialog = new AlertDialog.Builder(ListBarang.this);
            dialog.setCancelable(true);
            dialog.setItems(dialogitem, (dialog, which) -> {
                // TODO Auto-generated method stub
                switch (which) {
                   case 0:
                     edit(idx);
                     break;
                   case 1:
                     delete(idx);
                     break;
                }
            }).show();
            return false;
        });
    }

    @Override
    public void onRefresh() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        callVolley();
    }

    // todo untuk mengosongi edittext pada form
//    private void kosong(){
//        txt_id.setText(null);
//        txt_nama.setText(null);
//        txt_alamat.setText(null);
//    }

    // todo untuk menampilkan dialog form biodata
    @SuppressLint("InflateParams")
    private void DialogForm(String idx, String namaBarang, String jenisBarang, Integer jumlahBarang,
                            String tanggal, String barcode, Integer hargaBarang,String gambarBarang, String Button) {
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_barang, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Form Barang");

        id1                 =  dialogView.findViewById(R.id.id1);
        editGambar          =  dialogView.findViewById(R.id.tambahGambar1);
        editHargaBarang     =  dialogView.findViewById(R.id.hargaBarang1);
        editJenisBarang     =  dialogView.findViewById(R.id.jenisBarang1);
        editJumlahBarang    =  dialogView.findViewById(R.id.jumlahBarang1);
        editTanggalBarang   =  dialogView.findViewById(R.id.tanggal1);
        editScanBarcode     =  dialogView.findViewById(R.id.scanBarcode1);
        editNamaBarang      =  dialogView.findViewById(R.id.namaBarang1);
//        gambarBarang        =  dialogView.findViewById(R.id.tambahGambar1);

//        if (!idx.isEmpty()){
//            id1.setText(idx);
//            Picasso.get().load(strImage).into(profile_image);
//            editJenisBarang.setText(jenisBarang);
//            editJumlahBarang.setText(jumlahBarang);
//            editHargaBarang.setText(hargaBarang);
//            editTanggalBarang.setText(tanggal);
//            editScanBarcode.setText(barcode);
//            editNamaBarang.setText(namaBarang);
//        } else {
//            kosong();
        }

//        dialog.setPositiveButton(button, (dialog, which) -> {
//            id      = txt_id.getText().toString();
//
//
//            simpan_update();
//            dialog.dismiss();
//        });
//
//        dialog.setNegativeButton("BATAL", (dialog, which) -> {
//            dialog.dismiss();
//            kosong();
//        });
//
//        dialog.show();
    //}

    // todo fungsi untuk menyimpan atau update
//    private void simpan_update() {
//        String url;
//        // jika id kosong maka simpan, jika id ada nilainya maka update
//        if (id.isEmpty()){
//            url = url_insert;
//        } else {
//            url = url_update;
//        }
//
//        StringRequest strReq = new StringRequest(Request.Method.POST, url, response -> {
//            Log.d(TAG, "Response: " + response);
//
//            try {
//                JSONObject jObj = new JSONObject(response);
//                success = jObj.getInt(TAG_SUCCESS);
//
//                // Cek error node pada json
//                if (success == 1) {
//                    Log.d("Add/update", jObj.toString());
//
//                    callVolley();
////                    kosong();
//
//                    Toast.makeText(this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
//                    adapter.notifyDataSetChanged();
//
//                } else {
//                    Toast.makeText(this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
//                }
//            } catch (JSONException e) {
//                // JSON error
//                e.printStackTrace();
//            }
//
//        }, error -> {
//            Log.e(TAG, "Error: " + error.getMessage());
//            Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                // Posting parameters ke post url
//                Map<String, String> params = new HashMap<String, String>();
//                // jika id kosong maka simpan, jika id ada nilainya maka update
//                if (id.isEmpty()){
//                    params.put("nama", nama);
//                    params.put("alamat", alamat);
//                } else {
//                    params.put("id", id);
//                    params.put("nama", nama);
//                    params.put("alamat", alamat);
//                }
//
//                return params;
//            }
//
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(strReq);
//    }

    // todo untuk menampilkan semua data pada listview
    private void callVolley(){
        itemList.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        // membuat request JSON
        JsonArrayRequest jArr = new JsonArrayRequest(url_select, response -> {
            Log.d(TAG, response.toString());

            // Parsing json
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject obj = response.getJSONObject(i);

                    Data item = new Data();

                    item.setId(obj.getString(TAG_ID));
                    item.setBarcode(obj.getString(TAG_Barcode));
                    item.setGambarBarang(obj.getString(TAG_Gambar));
                    item.setHargaBarang(obj.getInt(TAG_Harga));
                    item.setJenisBarang(obj.getString(TAG_JenisBarang));
                    item.setNamaBarang(obj.getString(TAG_NamaBarang));
                    item.setTanggal(obj.getString(TAG_Tanggal));
                    item.setJumlahBarang(obj.getInt(TAG_JumlahBarang));

                    // menambah item ke array
                    itemList.add(item);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // notifikasi adanya perubahan data pada adapter
            adapter.notifyDataSetChanged();

            swipe.setRefreshing(false);
        }, error -> {
            VolleyLog.d(TAG, "Error: " + error.getMessage());
            swipe.setRefreshing(false);
        });

        // menambah request ke request queue
        SingletonGambar.getInstance().addToRequestQueue(jArr);
    }

    //todo fungsi untuk get edit data
    private void edit(final String idx){
        Intent i = new Intent(this,EditBarang.class);
        i.putExtra("id",idx);
        startActivity(i);
//        StringRequest strReq = new StringRequest(Request.Method.POST, url_edit, response -> {
//            Log.d(TAG, "Response: " + response);
//
//            try {
//                JSONObject jObj = new JSONObject(response);
//                success = jObj.getInt(TAG_SUCCESS);
//
//                // Cek error node pada json
//                if (success == 1) {
//                    Log.d("get edit data", jObj.toString());
//                    String idx1          = jObj.getString(TAG_ID);
//                    String namaBarang    = jObj.getString(TAG_NamaBarang);
//                    String jenisBarang   = jObj.getString(TAG_JenisBarang);
//                    Integer jumlahBarang = jObj.getInt(TAG_JumlahBarang);
//                    String tanggal       = jObj.getString(TAG_Tanggal);
//                    String barcode       = jObj.getString(TAG_Barcode);
//                    Integer hargaBarang  = jObj.getInt(TAG_Harga);
//                    String gambarBarang  = jObj.getString(TAG_Gambar);
//
//
//                    Intent i = new Intent(this,EditBarang.class);
//                    i.putExtra("id",idx1);
//                    i.putExtra("nama_barang",namaBarang);
//                    i.putExtra("jenis_barang",jenisBarang);
//                    i.putExtra("jumlah_barang",jumlahBarang);
//                    i.putExtra("tanggal",tanggal);
//                    i.putExtra("barcode",barcode);
//                    i.putExtra("harga_barang",hargaBarang);
//                    i.putExtra("gambar_barang",gambarBarang);
//                    startActivity(i);
//                    adapter.notifyDataSetChanged();
//
//                } else {
//                    Toast.makeText(this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
//                }
//            } catch (JSONException e) {
//                // JSON error
//                e.printStackTrace();
//            }
//
//        }, error -> {
//            Log.e(TAG, "Error: " + error.getMessage());
//            Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                // Posting parameters ke post url
//                Map<String, String> params = new HashMap<>();
//                params.put("id", idx);
//
//                return params;
//            }
//
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(strReq);
    }

    // todo fungsi untuk menghapus
    private void delete(final String idx){
        StringRequest strReq = new StringRequest(Request.Method.POST, url_delete, response -> {
            Log.d(TAG, "Response: " + response);

            try {
                JSONObject jObj = new JSONObject(response);
                success = jObj.getInt(TAG_SUCCESS);

                // Cek error node pada json
                if (success == 1) {
                    Log.d("delete", jObj.toString());

                    callVolley();

                    Toast.makeText(this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    adapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                // JSON error
                e.printStackTrace();
            }

        }, error -> {
            Log.e(TAG, "Error: " + error.getMessage());
            Toast.makeText(ListBarang.this, error.getMessage(), Toast.LENGTH_LONG).show();
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<>();
                params.put("id", idx);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strReq);
    }

}