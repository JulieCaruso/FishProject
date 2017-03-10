package com.example.fishapi.model;

import android.os.Parcel;
import android.os.Parcelable;

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

    public static List<Department> getDepartments() {
        List<Department> departments = new ArrayList<>();
        departments.add(new Department("ADM", "5 rue du truc", 25, 1));
        departments.add(new Department("MDA", "5 rue du chouette", 0, 3));
        departments.add(new Department("DMA", "5 rue du machin", 1, 2));
        departments.add(new Department("DAM", "5 rue du cho", 8, 2));
        departments.add(new Department("MAD", "5 rue du chou", 55, 88));
        departments.add(new Department("MMM", "5 rue du c", 8, 1));
        departments.add(new Department("AMD", "5 rue du ch", 0, 3));
        return departments;
    }

    @Override
    public String toString() {
        return getName();
    }
}
