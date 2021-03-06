package com.yusuf.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yusuf.myapplication.model.Smartphone;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton btnTambahTransaksi;
    ImageButton btnChangeUserName;
    ListView lvDaftarTransaksi;
    TextView txNoData, txUsername, txSaldo;
    com.yusuf.myapplication.DaftarSmartphoneAdapter adapter;
    List<Smartphone> daftarTransaksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inisialisasiView();
        loadDataTransaksi();
        setupListview();
    }

    private void inisialisasiView() {
        btnTambahTransaksi = findViewById(R.id.btn_add_transaksi);
        btnTambahTransaksi.setOnClickListener(view -> bukaFormTambahTransaksi());
        btnChangeUserName = findViewById(R.id.btn_change_username);
        btnChangeUserName.setOnClickListener(view -> changeUserName());
        lvDaftarTransaksi = findViewById(R.id.lv_transaksi);
        txNoData = findViewById(R.id.tx_nodata);
        txUsername = findViewById(R.id.tx_user_name);
        txUsername.setText(SharedPreferenceUtility.getUserName(this));
        txSaldo = findViewById(R.id.tx_saldo);
    }

    private void setupListview() {
        adapter = new com.yusuf.myapplication.DaftarSmartphoneAdapter(this, daftarTransaksi);
        lvDaftarTransaksi.setAdapter(adapter);
    }

    private void loadDataTransaksi() {
        daftarTransaksi = SharedPreferenceUtility.getAllTransaksi(this);
        double saldo = 0;
        if (daftarTransaksi.size()>0) {
            txNoData.setVisibility(View.GONE);
            // hitung saldo
        }
    }

    private void refreshListView() {
        adapter.clear();
        loadDataTransaksi();
        adapter.addAll(daftarTransaksi);
    }

    private void bukaFormTambahTransaksi() {
        Intent intent = new Intent(this, com.yusuf.myapplication.FormSmartphoneActivity.class);
        startActivity(intent);
    }

    private void changeUserName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ganti namaa");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferenceUtility.saveUserName(getApplicationContext(),input.getText().toString());
                Toast.makeText(getApplicationContext(),"Nama user berhasil disimpan",Toast.LENGTH_SHORT).show();
                txUsername.setText(SharedPreferenceUtility.getUserName(getApplicationContext()));
            }
        });
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshListView();
    }
}