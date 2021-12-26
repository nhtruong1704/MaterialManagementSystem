package com.example.materialmanagementsystem.presentation.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Material implements Parcelable {
    @SerializedName("MatId")
    @Expose
    private String matId;
    
    @SerializedName("MatName")
    @Expose
    private String matName;

    @SerializedName("MatNo")
    @Expose
    private String matNo;
    
    @SerializedName("MatDis")
    @Expose
    private String matDis;

    @SerializedName("MatPhone")
    @Expose
    private String matPhone;

    @SerializedName("MatImDate")
    @Expose
    private String matImDate;

    @SerializedName("MatAmo")
    @Expose
    private String matAmo;


    @SerializedName("MatUnPri")
    @Expose
    private String matUnPri;

    @SerializedName("MatTotalCost")
    @Expose
    private String matTotalCost;
    
    protected Material(Parcel in) {
        matId = in.readString();
        matName = in.readString();
        matNo = in.readString();
        matDis = in.readString();
        matPhone = in.readString();
        matImDate = in.readString();
        matAmo = in.readString();
        matUnPri = in.readString();
        matTotalCost = in.readString();
    }

    public static final Parcelable.Creator<Material> CREATOR = new Parcelable.Creator<Material>() {
        @Override
        public Material createFromParcel(Parcel in) {
            return new Material(in);
        }

        @Override
        public Material[] newArray(int size) {
            return new Material[size];
        }
    };

    public String getMatId() {
        return matId;
    }

    public void setMatId(String matId) {
        this.matId = matId;
    }

    public String getMatName() {
        return matName;
    }

    public void setMatName(String matName) {
        this.matName = matName;
    }

    public String getMatDis() {
        return matDis;
    }

    public void setMatDis(String matDis) {
        this.matDis = matDis;
    }

    public String getMatPhone() {
        return matPhone;
    }

    public void setMatPhone(String matPhone) {
        this.matPhone = matPhone;
    }

    public String getMatImDate() {
        return matImDate;
    }

    public void setMatImDate(String matImDate) {
        this.matImDate = matImDate;
    }

    public String getMatAmo() {
        return matAmo;
    }

    public void setMatAmo(String matAmo) {
        this.matAmo = matAmo;
    }

    public String getMatUnPri() {
        return matUnPri;
    }

    public void setMatUnPri(String matUnPri) {
        this.matUnPri = matUnPri;
    }

    public String getMatTotalCost() {
        return matTotalCost;
    }

    public void setMatTotalCost(String matTotalCost) {
        this.matTotalCost = matTotalCost;
    }

    public String getMatNo() {
        return matNo;
    }

    public void setMatNo(String matNo) {
        this.matNo = matNo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(matId);
        dest.writeString(matName);
        dest.writeString(matNo);
        dest.writeString(matDis);
        dest.writeString(matPhone);
        dest.writeString(matImDate);
        dest.writeString(matAmo);
        dest.writeString(matUnPri);
        dest.writeString(matTotalCost);
    }
}
