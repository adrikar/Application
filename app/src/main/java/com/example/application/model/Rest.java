package com.example.application.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Rest implements Parcelable {
    private String name, address,website,phone,openHours, restId;

    public Rest() {
    }

    protected Rest(Parcel in) {
        name = in.readString();
        address = in.readString();
        website = in.readString();
        phone = in.readString();
        openHours = in.readString();
        restId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(website);
        dest.writeString(phone);
        dest.writeString(openHours);
        dest.writeString(restId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Rest> CREATOR = new Creator<Rest>() {
        @Override
        public Rest createFromParcel(Parcel in) {
            return new Rest(in);
        }

        @Override
        public Rest[] newArray(int size) {
            return new Rest[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpenHours() {
        return openHours;
    }

    public void setOpenHours(String openHours) {
        this.openHours = openHours;
    }

    public String getRestId() {
        return restId;
    }

    public void setRestId(String restId) {
        this.restId = restId;
    }
}