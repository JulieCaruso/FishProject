package com.example.fishapi.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.TextInputEditText;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Department implements Parcelable {

    @Expose
    private String name;
    @Expose
    private String address;
    @Expose
    @SerializedName("employeenb")
    private int employeeNb;
    @Expose
    private int id;

    public static final Creator<Department> CREATOR = new Creator<Department>() {
        @Override
        public Department createFromParcel(Parcel in) {
            return new Department(in);
        }

        @Override
        public Department[] newArray(int size) {
            return new Department[size];
        }
    };

    public Department(String name, String address, int employeeNb, int id) {
        this.name = name;
        this.address = address;
        this.employeeNb = employeeNb;
        this.id = id;
    }

    protected Department(Parcel in) {
        this.name = in.readString();
        this.address = in.readString();
        this.employeeNb = in.readInt();
        this.id = in.readInt();
    }

    public Department(TextInputEditText nameInput, TextInputEditText addressInput, TextInputEditText employeeNbInput) {
        this.name = nameInput.getText().toString();
        this.address = addressInput.getText().toString();
        String employeeNbString = employeeNbInput.getText().toString();
        try {
            this.employeeNb = Integer.parseInt(employeeNbString);
        } catch (NumberFormatException e) {
            this.employeeNb = 0;
        }
        this.id = -1;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getEmployeeNb() {
        return employeeNb;
    }

    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.name);
        out.writeString(this.address);
        out.writeInt(this.employeeNb);
        out.writeInt(this.id);
    }

    @Override
    public String toString() {
        return getName();
    }
}
