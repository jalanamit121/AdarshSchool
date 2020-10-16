package com.winbee.adarshsardarshahar.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PurchasedMainModel implements Serializable {

    @SerializedName("User_id")
    @Expose
    private String user_id;
    @SerializedName("Purchased")
    @Expose
    private Boolean purchased;
    @SerializedName("CourseData")
    @Expose
    private CourseDatum[] courseData;
    @SerializedName("Error")
    @Expose
    private Boolean error;
    @SerializedName("Error_Message")
    @Expose
    private String error_Message;
    @SerializedName("Error_Code")
    @Expose
    private String error_Code;

    public PurchasedMainModel(String user_id, Boolean purchased, CourseDatum[] courseData, Boolean error, String error_Message, String error_Code) {
        this.user_id = user_id;
        this.purchased = purchased;
        this.courseData = courseData;
        this.error = error;
        this.error_Message = error_Message;
        this.error_Code = error_Code;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Boolean getPurchased() {
        return purchased;
    }

    public void setPurchased(Boolean purchased) {
        this.purchased = purchased;
    }

    public CourseDatum[] getCourseData() {
        return courseData;
    }



    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getError_Message() {
        return error_Message;
    }

    public void setError_Message(String error_Message) {
        this.error_Message = error_Message;
    }

    public String getError_Code() {
        return error_Code;
    }

    public void setError_Code(String error_Code) {
        this.error_Code = error_Code;
    }

}
