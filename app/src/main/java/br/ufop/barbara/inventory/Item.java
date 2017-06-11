package br.ufop.barbara.inventory;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by George on 23/05/2017.
 */

public class Item implements Parcelable, Serializable {

    private int code;
    private String description;
    private Date dateInventory;
    private String location;
    private String status;

    public Item(int code, String description, Date dateInventory, String location, String status) {
        this.code = code;
        this.description = description;
        this.dateInventory = dateInventory;
        this.location = location;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateInventory() {
        return dateInventory;
    }

    public void setDateInventory(Date dateInventory) {
        this.dateInventory = dateInventory;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    protected Item(Parcel in) {
        code  = in.readInt();
        description = in.readString();
        dateInventory  = (java.util.Date) in.readSerializable();
        location = in.readString();
        status = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in.readInt(), in.readString(), new Date(in.readLong()), in.readString(), in.readString());
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };


    public int getImage(){

        Calendar c = Calendar.getInstance();
        SimpleDateFormat ano = new SimpleDateFormat("yyyy");
        int anodata =  Integer.parseInt(ano.format(getDateInventory()));
        int anoatual = c.get(Calendar.YEAR);
        if((anodata - anoatual) >1 ){
            return R.drawable.warning;
        }

        else{
            return R.drawable.check;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(description);
        dest.writeString(String.valueOf(dateInventory));
        dest.writeString(location);
        dest.writeString(status);
    }
}
