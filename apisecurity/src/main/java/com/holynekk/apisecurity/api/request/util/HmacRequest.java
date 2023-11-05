package com.holynekk.apisecurity.api.request.util;

public class HmacRequest {

    private String fullName;

    private String city;

    private String gender;

    private int amount;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullname) {
        this.fullName = fullname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "HmacRequest{" +
                "fullname='" + fullName + '\'' +
                ", city='" + city + '\'' +
                ", gender='" + gender + '\'' +
                ", amount=" + amount +
                '}';
    }

}
