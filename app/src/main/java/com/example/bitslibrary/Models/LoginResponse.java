package com.example.bitslibrary.Models;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    public String apikey_account;
    private String message;
    private boolean status;
    private long usr_id;

    public String getApikey_account() {
        return apikey_account;
    }

    public void setApikey_account(String apikey_account) {
        this.apikey_account = apikey_account;
    }

    public String getMessage() { return message; }
    public void setMessage(String value) { this.message = value; }

    public boolean getStatus() { return status; }
    public void setStatus(boolean value) { this.status = value; }

    public long getUsr_id() {
        return usr_id;
    }

    public void setUsr_id(long usr_id) {
        this.usr_id = usr_id;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "apikey_account='" + apikey_account + '\'' +
                ", message='" + message + '\'' +
                ", status=" + status +
                ", usr_id=" + usr_id +
                '}';
    }
}
