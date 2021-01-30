package com.example.maksudi.data;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.maksudi.R;
import com.example.maksudi.data.model.Data;

import java.util.List;

public class AdapterBarang extends BaseAdapter {
    private final Activity activity;
    private LayoutInflater inflater;
    private final List<Data> items;
    ImageLoader imageLoader;

    public AdapterBarang(Activity activity, List<Data> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int location) {
        return items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_barang, null);
        if (imageLoader == null)
            imageLoader = SingletonGambar.getInstance().getImageLoader();

        NetworkImageView gambar =  convertView.findViewById(R.id.gambar);
        TextView namaBarang     =  convertView.findViewById(R.id.namaBarang);
        TextView rp             =  convertView.findViewById(R.id.rp);
        TextView jenisBarang    =  convertView.findViewById(R.id.jenisBarang);
        TextView tanggal        =  convertView.findViewById(R.id.tanggal);
        TextView barcode        =  convertView.findViewById(R.id.barcode);
        TextView stok           =  convertView.findViewById(R.id.stok);
        TextView id             =  convertView.findViewById(R.id.id);

        Data data = items.get(position);


        gambar.setImageUrl(data.getGambarBarang(),imageLoader);
        namaBarang.setText(data.getNamaBarang());
        rp.setText(String.valueOf(data.getHargaBarang()));
        jenisBarang.setText(data.getJenisBarang());
        tanggal.setText(data.getTanggal());
        barcode.setText(data.getBarcode());
        stok.setText(String.valueOf(data.getJumlahBarang()));
        id.setText(data.getId());

        return convertView;
    }

}
