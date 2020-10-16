package com.winbee.adarshsardarshahar.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubmittedAssignment implements Serializable {

    @SerializedName("Assignment")
    @Expose
    private Boolean assignment;
    @SerializedName("Assignment_Data")
    @Expose
    private SubmittedDatum[] assignment_Data;
    @SerializedName("Error")
    @Expose
    private Boolean error;
    @SerializedName("Error_Message")
    @Expose
    private String error_Message;
    @SerializedName("Error_Code")
    @Expose
    private String error_Code;

    public SubmittedAssignment(Boolean assignment, SubmittedDatum[] assignment_Data, Boolean error, String error_Message, String error_Code) {
        this.assignment = assignment;
        this.assignment_Data = assignment_Data;
        this.error = error;
        this.error_Message = error_Message;
        this.error_Code = error_Code;
    }

    public Boolean getAssignment() {
        return assignment;
    }

    public void setAssignment(Boolean assignment) {
        this.assignment = assignment;
    }

    public SubmittedDatum[] getAssignment_Data() {
        return assignment_Data;
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