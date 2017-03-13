package com.example.fishapi.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
    @SerializedName("departmentid")
    private Integer departmentId;
    @Expose
    private String token;
    @Expose
    private Integer id;

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

    public User(String firstname, String lastname, String username, String password, String sex, int departmentId, String token, int id) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.departmentId = departmentId;
        this.token = token;
        this.id = id;
    }

    private User(Parcel in) {
        this.firstname = in.readString();
        this.lastname = in.readString();
        this.username = in.readString();
        this.password = in.readString();
        this.sex = in.readString();
        this.departmentId = in.readInt();
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

    public int getDepartmentId() {
        return departmentId;
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
        out.writeInt(this.departmentId);
        out.writeString(this.token);
        out.writeInt(this.id);
    }

    public static String encryptSHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] textBytes = text.getBytes("UTF-8");
        md.update(textBytes, 0, textBytes.length);
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int twoHalves = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (twoHalves++ < 1);
        }
        return buf.toString();
    }
}
