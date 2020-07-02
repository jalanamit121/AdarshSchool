package com.winbee.adarshsardarshahar.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BannerModel implements Serializable {

@SerializedName("Name")
@Expose
private String name;
@SerializedName("File")
@Expose
private String file;
@SerializedName("Log")
@Expose
private String log;
@SerializedName("Status")
@Expose
private String status;
@SerializedName("Id")
@Expose
private String id;

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getFile() {
return file;
}

public void setFile(String file) {
this.file = file;
}

public String getLog() {
return log;
}

public void setLog(String log) {
this.log = log;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

}