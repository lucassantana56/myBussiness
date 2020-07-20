package com.example.mybussiness.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    int _iD;
    String _name;
    Float _balance;

    public User(String _name, Float _balance) {
        this._name = _name;
        this._balance = _balance;
    }

    public User() {
    }

    protected User(Parcel in) {
        _iD = in.readInt();
        _name = in.readString();
        if (in.readByte() == 0) {
            _balance = null;
        } else {
            _balance = in.readFloat();
        }
    }

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

    public int get_iD() {
        return _iD;
    }

    public void set_iD(int _iD) {
        this._iD = _iD;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public Float get_balance() {
        return _balance;
    }

    public void set_balance(Float _balance) {
        this._balance = _balance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_name);
        dest.writeFloat(_balance);
    }
}
