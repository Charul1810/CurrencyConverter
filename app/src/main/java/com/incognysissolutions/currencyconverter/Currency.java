package com.incognysissolutions.currencyconverter;

/**
 * Created by CHARUL on 23-01-2018.
 */

public class Currency {


    int id;
        String imgurl,currencycode,symbol,currencyname,value,defaultcurrencycode,datetime;


    public Currency() {
    }

    public Currency(String currencycode, String value, String defaultcurrencycode, String datetime) {
        this.currencycode = currencycode;
        this.value = value;
        this.defaultcurrencycode = defaultcurrencycode;
        this.datetime = datetime;
    }

    public Currency(int id, String imgurl, String currencycode, String symbol, String currencyname, String value, String defaultcurrencycode, String datetime) {
        this.id = id;
        this.imgurl = imgurl;
        this.currencycode = currencycode;
        this.symbol = symbol;
        this.currencyname = currencyname;
        this.value = value;
        this.defaultcurrencycode = defaultcurrencycode;
        this.datetime = datetime;
    }

    public Currency(String imgurl, String currencycode, String symbol, String currencyname, String value, String defaultcurrencycode, String datetime) {
        this.imgurl = imgurl;
        this.currencycode = currencycode;
        this.symbol = symbol;
        this.currencyname = currencyname;
        this.value = value;
        this.defaultcurrencycode = defaultcurrencycode;
        this.datetime = datetime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getCurrencycode() {
        return currencycode;
    }

    public void setCurrencycode(String currencycode) {
        this.currencycode = currencycode;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCurrencyname() {
        return currencyname;
    }

    public void setCurrencyname(String currencyname) {
        this.currencyname = currencyname;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDefaultcurrencycode() {
        return defaultcurrencycode;
    }

    public void setDefaultcurrencycode(String defaultcurrencycode) {
        this.defaultcurrencycode = defaultcurrencycode;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
