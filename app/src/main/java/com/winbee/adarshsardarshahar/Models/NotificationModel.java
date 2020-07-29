package com.winbee.adarshsardarshahar.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotificationModel implements Serializable {

@SerializedName("Sender")
@Expose
private String sender;
@SerializedName("Bucket")
@Expose
private String bucket;
@SerializedName("Message")
@Expose
private String message;
@SerializedName("Department")
@Expose
private String department;
@SerializedName("Priority")
@Expose
private String priority;
@SerializedName("Type")
@Expose
private String type;
@SerializedName("Date")
@Expose
private String date;

public String getSender() {
return sender;
}

public void setSender(String sender) {
this.sender = sender;
}

public String getBucket() {
return bucket;
}

public void setBucket(String bucket) {
this.bucket = bucket;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public String getDepartment() {
return department;
}

public void setDepartment(String department) {
this.department = department;
}

public String getPriority() {
return priority;
}

public void setPriority(String priority) {
this.priority = priority;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getDate() {
return date;
}

public void setDate(String date) {
this.date = date;
}

}