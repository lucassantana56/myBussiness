package com.example.mybussiness.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Bill implements Parcelable {

    long _iD;
    String _name;
    String _description;
    double _amount;
    Date _expirationDate;
    Boolean _repeat;
    long _userId;

    public Bill(String _name, String _description, double _amount, Date _expirationDate, Boolean _repeat, int _userId) {
        this._name = _name;
        this._description = _description;
        this._amount = _amount;
        this._expirationDate = _expirationDate;
        this._repeat = _repeat;
        this._userId = _userId;
    }

    public Bill(long _iD, String _name, String _description, double _amount, Date _expirationDate, Boolean _repeat, int _userId) {
        this._iD = _iD;
        this._name = _name;
        this._description = _description;
        this._amount = _amount;
        this._expirationDate = _expirationDate;
        this._repeat = _repeat;
        this._userId = _userId;
    }


    protected Bill(Parcel in) {
        _iD = in.readInt();
        _name = in.readString();
        _description = in.readString();
        _amount = in.readDouble();
        byte tmp_repeat = in.readByte();
        _repeat = tmp_repeat == 0 ? null : tmp_repeat == 1;
        _userId = in.readInt();
    }

    public Bill() {
    }


    public static final Creator<Bill> CREATOR = new Creator<Bill>() {
        @Override
        public Bill createFromParcel(Parcel in) {
            return new Bill(in);
        }

        @Override
        public Bill[] newArray(int size) {
            return new Bill[size];
        }
    };

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public double get_amount() {
        return _amount;
    }

    public void set_amount(double _amount) {
        this._amount = _amount;
    }

    public Date get_expirationDate() {
        return _expirationDate;
    }

    public void set_expirationDate(Date _expirationDate) {
        this._expirationDate = _expirationDate;
    }

    public Boolean get_repeat() {
        return _repeat;
    }

    public void set_repeat(Boolean _repeat) {
        this._repeat = _repeat;
    }

    public long get_userId() {
        return _userId;
    }

    public void set_userId(long _userId) {
        this._userId = _userId;
    }


    public long get_iD() {
        return _iD;
    }

    public void set_iD(int _iD) {
        this._iD = _iD;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_name);
        dest.writeString(_description);
        dest.writeDouble(_amount);
        dest.writeByte((byte) (_repeat == null ? 0 : _repeat ? 1 : 2));
        dest.writeLong(_userId);
    }
}
