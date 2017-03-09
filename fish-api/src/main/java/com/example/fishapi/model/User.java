package com.example.fishapi.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class User implements Parcelable {

    @Expose
    private String firstname;
    @Expose
    private String lastname;
    @Expose
    private String username;
    @Expose
    private String password;
    @Expose
    private String sex;
    @Expose
    private Department department;
    @Expose
    private String token;
    @Expose
    private int id;

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User(String firstname, String lastname, String username, String password, String sex, Department department, String token, int id) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.department = department;
        this.token = token;
        this.id = id;
    }

    private User(Parcel in) {
        this.firstname = in.readString();
        this.lastname = in.readString();
        this.username = in.readString();
        this.password = in.readString();
        this.sex = in.readString();
        this.department = in.readParcelable(Department.class.getClassLoader());
        this.token = in.readString();
        this.id = in.readInt();
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSex() {
        return sex;
    }

    public Department getDepartment() {
        return department;
    }

    public String getToken() {
        return token;
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
        out.writeString(this.firstname);
        out.writeString(this.lastname);
        out.writeString(this.username);
        out.writeString(this.password);
        out.writeString(this.sex);
        out.writeParcelable(this.department, flags);
        out.writeString(this.token);
        out.writeInt(this.id);
    }
}
