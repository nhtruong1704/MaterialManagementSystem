package com.example.materialmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.materialmanagementsystem.presentation.model.Material;
import com.example.materialmanagementsystem.presentation.retrofit.APIUtils;
import com.example.materialmanagementsystem.presentation.retrofit.DataClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminExportMaterialActivity extends AppCompatActivity {

    String materialId, materialName, materialNo, materialCusName, materialCusPhone, materialExDate, materialAmo, materialUnPri, materialTotalCost, materialTotalAmo;
    
    
    private Spinner snAdMatList;
    
    
    private EditText edtAdMatExNo, edtAdMatExCusName, edtAdMatExCusPhone, edtAdMatExExDate, edtAdMatExAmo, edtAdMatExUnPri, edtAdMatExTotalCost;
    private Button btnAdMatExSave, btnAdMatExExit;
    private ImageButton imBtnAdMatExDelExDate;
    private ImageView ivAdMatExExit;


    //for date of birth
    final Calendar calendar = Calendar.getInstance();

    ArrayList<Material> materialArr;
    ArrayList<String> materialNameArr;

    int posN, amo, total_amo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_export_material);

        //Connect layout
        initUI();

        //Get Data from server
        getData();

        //Set On View
        snAdMatList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = String.valueOf(position);
                posN = Integer.parseInt(pos);
                materialId = materialArr.get(posN).getMatId();
                materialName = materialArr.get(posN).getMatName();
                materialNo = materialArr.get(posN).getMatNo();
                materialTotalAmo = materialArr.get(posN).getMatAmo();
                edtAdMatExNo.setText(materialNo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edtAdMatExAmo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!isEmptyEditText(edtAdMatExAmo)) {
                    amo = Integer.parseInt(edtAdMatExAmo.getText().toString());
                    total_amo = Integer.parseInt(materialTotalAmo);
                    if(amo > total_amo) {
                        edtAdMatExAmo.setError("Quantity is not enough");
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //Button Delete Export Date
        imBtnAdMatExDelExDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtAdMatExExDate.setText("");
            }
        });

        //Set click text view Date
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        edtAdMatExExDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AdminExportMaterialActivity.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        //Button Exit
        btnAdMatExExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //ImageView Exit
        ivAdMatExExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edtAdMatExAmo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!isEmptyEditText(edtAdMatExAmo) && !isEmptyEditText(edtAdMatExUnPri)) {
                    edtAdMatExTotalCost.setText(String.valueOf(Integer.parseInt(edtAdMatExAmo.getText().toString()) * Integer.parseInt(edtAdMatExUnPri.getText().toString())));
                }
                else {
                    edtAdMatExTotalCost.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edtAdMatExUnPri.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!isEmptyEditText(edtAdMatExAmo) && !isEmptyEditText(edtAdMatExUnPri)) {
                    edtAdMatExTotalCost.setText(String.valueOf(Integer.parseInt(edtAdMatExAmo.getText().toString()) * Integer.parseInt(edtAdMatExUnPri.getText().toString())));
                }
                else {
                    edtAdMatExTotalCost.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //Button Save
        btnAdMatExSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(amo > total_amo) {
                    edtAdMatExAmo.setError("Quantity is not enough");
                }

                if (isEmptyEditText(edtAdMatExCusName)) {
                    edtAdMatExCusName.setError("Please enter customer's name");
                }
                materialCusName = edtAdMatExCusName.getText().toString();
                materialCusPhone = edtAdMatExCusPhone.getText().toString();
                materialExDate = edtAdMatExExDate.getText().toString();
                materialAmo = edtAdMatExAmo.getText().toString();
                materialUnPri = edtAdMatExUnPri.getText().toString();
                materialTotalCost = String.valueOf(Integer.parseInt(edtAdMatExAmo.getText().toString()) * Integer.parseInt(edtAdMatExUnPri.getText().toString()));
                if (materialCusName.length() > 0 && amo <= total_amo) {
                    if(amo == total_amo) {
                        DataClient dataClient = APIUtils.getData();
                        retrofit2.Call<String> callback = dataClient.DeleteMaterialData(materialId);
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String res = response.body();
                                if (res.trim().equals("MATERIAL_DELETED_SUCCESSFUL")) {
                                    //
                                } else {
                                    Log.d("Delete Err", res.trim());
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.d("Error Retrofit response", t.getMessage());
                            }
                        });
                    }
                    if(amo < total_amo) {

                        String materialResult = String.valueOf(total_amo - amo);

                        DataClient dataClient = APIUtils.getData();
                        retrofit2.Call<String> callback = dataClient.UpdateMaterialData(materialId, materialResult);
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String res = response.body();
                                if (res.trim().equals("MATERIAL_UPDATED_SUCCESSFUL")) {
                                    //
                                } else {
                                    Log.d("Err", res.trim());
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.d("Error Retrofit response", t.getMessage());
                            }
                        });
                    }
                    uploadInfo();
                }

            }
        });
    }


    private void materialArrToNameStringArrMaterial() {
        materialNameArr = new ArrayList<>();
        for (int i = 0; i < materialArr.size(); i++) {
            materialNameArr.add(materialArr.get(i).getMatName());
        }
    }

    private void getData() {
        DataClient dataClient = APIUtils.getData();
        retrofit2.Call<List<Material>> callback = dataClient.AdminViewAllMaterialData();
        callback.enqueue(new Callback<List<Material>>() {
            @Override
            public void onResponse(Call<List<Material>> call, Response<List<Material>> response) {
                materialArr = (ArrayList<Material>) response.body();
                if (materialArr.size() > 0) {
                    materialArrToNameStringArrMaterial();
                    //Adapter Spinner
                    adapterSpinner();
                }
            }

            @Override
            public void onFailure(Call<List<Material>> call, Throwable t) {
                Log.d("Error load all stu", t.getMessage());
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

    private void adapterSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, materialNameArr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snAdMatList.setAdapter(adapter);
    }
    
    private void uploadInfo() {
        DataClient insertData = APIUtils.getData();
        Call<String> callback = insertData.AdminExMaterialData(materialName, materialNo, materialCusName, materialCusPhone, materialExDate, materialAmo, materialUnPri, materialTotalCost);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                Log.d("Ad Mat Ex Info", result);
                if (result.trim().equals("EX_MATERIAL_SUCCESSFUL")) {
                    Toast.makeText(AdminExportMaterialActivity.this, "Material " + materialName + " Added Successful", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Error Ad Mat Ex Info", t.getMessage());
            }
        });
    }


    //Label for date of birth
    private void updateLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edtAdMatExExDate.setText(sdf.format(calendar.getTime()));
    }

    private void initUI() {
        ivAdMatExExit = findViewById(R.id.iv_ad_mat_ex_exit);
        snAdMatList = findViewById(R.id.sn_ex_mat_list);

        edtAdMatExNo = findViewById(R.id.edt_ad_mat_ex_no);
        edtAdMatExCusName = findViewById(R.id.edt_ad_mat_ex_cus);
        edtAdMatExCusPhone = findViewById(R.id.edt_ad_mat_ex_phone);
        edtAdMatExExDate = findViewById(R.id.edt_ad_mat_ex_date);
        edtAdMatExAmo = findViewById(R.id.edt_ad_mat_ex_amo);
        edtAdMatExUnPri = findViewById(R.id.edt_ad_mat_ex_price);
        edtAdMatExTotalCost = findViewById(R.id.edt_ad_mat_ex_total_cost);

        btnAdMatExSave = findViewById(R.id.btn_ad_mat_ex_save);
        btnAdMatExExit = findViewById(R.id.btn_ad_mat_ex_exit);
        imBtnAdMatExDelExDate = findViewById(R.id.im_btn_ad_mat_ex_del_dob);
    }
}