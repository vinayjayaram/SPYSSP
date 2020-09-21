package com.vj.yoga.spyss.model;

import com.google.gson.annotations.SerializedName;

public class BranchList {

    @SerializedName("id")
    private Integer id;
    @SerializedName("branchName")
    private String branchName;
    @SerializedName("contactNumber")
    private String contactNumber;
    @SerializedName("branchTimings")
    private String branchTimings;
    @SerializedName("branchType")
    private String branchType;
    @SerializedName("branchAddress")
    private String branchAddress;
    @SerializedName("latitude")
    private Double latitude;
    @SerializedName("longitude")
    private Double longitude;
    @SerializedName("placeId")
    private String placeId;

    public BranchList(Integer id, String branchName, String contactNumber, String branchTimings, String branchType, String branchAddress, Double latitude, Double longitude, String placeId) {
        this.id = id;
        this.branchName = branchName;
        this.contactNumber = contactNumber;
        this.branchTimings = branchTimings;
        this.branchType = branchType;
        this.branchAddress = branchAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.placeId = placeId;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getBranchTimings() {
        return branchTimings;
    }

    public void setBranchTimings(String branchTimings) {
        this.branchTimings = branchTimings;
    }

    public String getBranchType() {
        return branchType;
    }

    public void setBranchType(String branchType) {
        this.branchType = branchType;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }



}
