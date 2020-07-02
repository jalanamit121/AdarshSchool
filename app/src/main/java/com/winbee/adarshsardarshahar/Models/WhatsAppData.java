package com.winbee.adarshsardarshahar.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WhatsAppData {

    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("org_id")
    @Expose
    private String org_id;
    @SerializedName("whatsapp_mobile")
    @Expose
    private String whatsapp_mobile;
    @SerializedName("Enable_WhatsApp")
    @Expose
    private String enable_WhatsApp;
    @SerializedName("Success")
    @Expose
    private Boolean success;
    @SerializedName("Message")
    @Expose
    private Object message;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOrg_id() {
        return org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }

    public String getWhatsapp_mobile() {
        return whatsapp_mobile;
    }

    public void setWhatsapp_mobile(String whatsapp_mobile) {
        this.whatsapp_mobile = whatsapp_mobile;
    }

    public String getEnable_WhatsApp() {
        return enable_WhatsApp;
    }

    public void setEnable_WhatsApp(String enable_WhatsApp) {
        this.enable_WhatsApp = enable_WhatsApp;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

}