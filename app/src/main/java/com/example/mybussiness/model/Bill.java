package com.example.mybussiness.model;

import java.util.Date;

public class Bill {
    int _iD;
    String _name;
    String _description;
    double _amount;
    Date _expirationDate;
    Boolean _repeat;
    int _userId;

    public Bill(String _name, String _description, double _amount, Date _expirationDate, Boolean _repeat, int _userId) {
        this._name = _name;
        this._description = _description;
        this._amount = _amount;
        this._expirationDate = _expirationDate;
        this._repeat = _repeat;
        this._userId = _userId;
    }


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

    public int get_userId() {
        return _userId;
    }

    public void set_userId(int _userId) {
        this._userId = _userId;
    }
}
