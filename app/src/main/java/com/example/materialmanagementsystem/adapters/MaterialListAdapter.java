package com.example.materialmanagementsystem.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.materialmanagementsystem.R;
import com.example.materialmanagementsystem.interfaces.ItemClickListener;
import com.example.materialmanagementsystem.presentation.model.Material;
import com.example.materialmanagementsystem.presentation.retrofit.APIUtils;
import com.example.materialmanagementsystem.presentation.retrofit.DataClient;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MaterialListAdapter extends RecyclerView.Adapter<MaterialListAdapter.MaterialViewHolder> {
    //Form for adapter
    Context context;
    ArrayList<Material> materialArr;

    public MaterialListAdapter(Context context, ArrayList<Material> materialArr) {
        this.context = context;
        this.materialArr = materialArr;
    }

    @NonNull
    @Override
    public MaterialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_material, parent, false);
        return new MaterialViewHolder(itemView);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MaterialViewHolder holder, int position) {
        Material material = materialArr.get(position);

        String materialName = material.getMatName();
        String materialNo = material.getMatNo();
        String materialAmount = material.getMatAmo();



        holder.tvMatName.setText("Material Name: " + materialName);
        holder.tvMatAmount.setText("Material Amount: " + materialAmount);
        holder.tvMatNo.setText("Material ID: " + materialNo);

        //Click for RecycleView
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setIcon(R.drawable.ic_baseline_delete_24);
                    builder.setTitle("Delete this material");
                    builder.setMessage("Are you sure want to delete material " + material.getMatName() + "?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DataClient dataClient = APIUtils.getData();
                            retrofit2.Call<String> callback = dataClient.DeleteMaterialData(material.getMatId());
                            callback.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    String res = response.body();
                                    if (res.trim().equals("MATERIAL_DELETED_SUCCESSFUL")) {
                                        Toast.makeText(context.getApplicationContext(), "Deleted material " + material.getMatName() + " Successfully", Toast.LENGTH_SHORT).show();

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
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {
//                    Intent intent = new Intent(view.getContext(), AdminMaterialViewProfileActivity.class);
//
//                    Bundle bundle = new Bundle();
//
//                    bundle.putParcelableArrayList("STUDENT_DATA_ARRAY", materialArr);
//                    bundle.putInt("STUDENT_DATA_POSITION", position);
//                    intent.putExtra("STUDENT_DATA_FROM_STUDENT_ADAPTER_TO_AD_STU_VIEW_PROFILE", bundle);
//                    view.getContext().startActivity(intent);
//                    ((Activity) view.getContext()).finish();

                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return materialArr == null ? 0 : materialArr.size();
    }




    //Data ViewHolder class
    public static class MaterialViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView tvMatName, tvMatNo, tvMatAmount;

        ItemClickListener itemClickListener;

        public MaterialViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMatName = itemView.findViewById(R.id.tv_mat_name);
            tvMatNo = itemView.findViewById(R.id.tv_mat_no);
            tvMatAmount = itemView.findViewById(R.id.tv_mat_amount);

            //Turn On Click for RecycleView
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        //onClick for RecycleView
        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }

        //onLongClick for RecycleView
        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
            return true;
            //return false; // if not use
        }
    }
}
