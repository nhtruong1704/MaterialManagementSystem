package com.example.materialmanagementsystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.materialmanagementsystem.presentation.model.Material;
import com.example.materialmanagementsystem.presentation.retrofit.APIUtils;
import com.example.materialmanagementsystem.presentation.retrofit.DataClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminImportMaterialActivity extends AppCompatActivity {
    
    String materialName, materialNo, materialDis, materialPhone, materialImDate, materialAmo, materialUnPri, materialTotalCost;

    private EditText edtAdMatAddName, edtAdMatAddNo, edtAdMatAddDis, edtAdMatAddPhone, edtAdMatAddImDate, edtAdMatAddAmo, edtAdMatAddUnPri, edtAdMatAddTotalCost;
    private Button btnAdMatAddSave, btnAdMatAddExit;
    private ImageButton imBtnAdMatAddDelDOB;
    private ImageView ivAdMatAddExit;
    

    //for date of birth
    final Calendar calendar = Calendar.getInstance();

    ArrayList<Material> materialArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_import_material);

        //Connect layout
        initUI();

        //Button Delete Import Date 
        imBtnAdMatAddDelDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtAdMatAddImDate.setText("");
            }
        });

        //Set click text view Date of birth
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        edtAdMatAddImDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AdminImportMaterialActivity.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        

        //Button Exit
        btnAdMatAddExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //ImageView Exit
        ivAdMatAddExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edtAdMatAddAmo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!isEmptyEditText(edtAdMatAddAmo) && !isEmptyEditText(edtAdMatAddUnPri)) {
                    edtAdMatAddTotalCost.setText(String.valueOf(Integer.parseInt(edtAdMatAddAmo.getText().toString()) * Integer.parseInt(edtAdMatAddUnPri.getText().toString())));
                }
                else {
                    edtAdMatAddTotalCost.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edtAdMatAddUnPri.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!isEmptyEditText(edtAdMatAddAmo) && !isEmptyEditText(edtAdMatAddUnPri)) {
                    edtAdMatAddTotalCost.setText(String.valueOf(Integer.parseInt(edtAdMatAddAmo.getText().toString()) * Integer.parseInt(edtAdMatAddUnPri.getText().toString())));
                }
                else {
                    edtAdMatAddTotalCost.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //Button Save
        btnAdMatAddSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmptyEditText(edtAdMatAddName)) {
                    edtAdMatAddName.setError("Please enter material's name");
                }

                materialName = edtAdMatAddName.getText().toString();
                materialNo = edtAdMatAddNo.getText().toString();
                materialDis = edtAdMatAddDis.getText().toString();
                materialPhone = edtAdMatAddPhone.getText().toString();
                materialImDate = edtAdMatAddImDate.getText().toString();
                materialAmo = edtAdMatAddAmo.getText().toString();
                materialUnPri = edtAdMatAddUnPri.getText().toString();
                if(!isEmptyEditText(edtAdMatAddAmo) && !isEmptyEditText(edtAdMatAddUnPri)) {
                    materialTotalCost = String.valueOf(Integer.parseInt(edtAdMatAddAmo.getText().toString()) * Integer.parseInt(edtAdMatAddUnPri.getText().toString()));
                }

                if (materialName.length() > 0 ) {
                        uploadInfo();
                }

            }
        });
    }



    private boolean isEmptyEditText(EditText editText) {
        String str = editText.getText().toString();
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        return false;
    }

    private void uploadInfo() {
        DataClient insertData = APIUtils.getData();
        Call<String> callback = insertData.AdminAddMaterialData(materialName, materialNo, materialDis, materialPhone, materialImDate, materialAmo, materialUnPri, materialTotalCost);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                Log.d("Ad Mat Add Info", result);
                if (result.trim().equals("ADD_MATERIAL_SUCCESSFUL")) {
                    Toast.makeText(AdminImportMaterialActivity.this, "Material " + materialName + " Added Successful", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Error Ad Mat Add Info", t.getMessage());
            }
        });
    }


    //Label for date of birth
    private void updateLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edtAdMatAddImDate.setText(sdf.format(calendar.getTime()));
    }

    private void initUI() {
        ivAdMatAddExit = findViewById(R.id.iv_ad_mat_add_exit);

        edtAdMatAddName = findViewById(R.id.edt_ad_mat_add_name);
        edtAdMatAddNo = findViewById(R.id.edt_ad_mat_add_no);
        edtAdMatAddDis = findViewById(R.id.edt_ad_mat_add_dis);
        edtAdMatAddPhone = findViewById(R.id.edt_ad_mat_add_phone);
        edtAdMatAddImDate = findViewById(R.id.edt_ad_mat_add_date);
        edtAdMatAddAmo = findViewById(R.id.edt_ad_mat_add_amo);
        edtAdMatAddUnPri = findViewById(R.id.edt_ad_mat_add_price);
        edtAdMatAddTotalCost = findViewById(R.id.edt_ad_mat_add_total_cost);

        btnAdMatAddSave = findViewById(R.id.btn_ad_mat_add_save);
        btnAdMatAddExit = findViewById(R.id.btn_ad_mat_add_exit);
        imBtnAdMatAddDelDOB = findViewById(R.id.im_btn_ad_mat_add_del_dob);
    }
}