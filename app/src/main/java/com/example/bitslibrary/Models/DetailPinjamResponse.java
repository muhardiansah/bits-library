package com.example.bitslibrary.Models;

public class DetailPinjamResponse {
    private DataPinjamResponse data;
    private String message;
    private boolean status;

    public DataPinjamResponse getData() { return data; }
    public void setData(DataPinjamResponse value) { this.data = value; }

    public String getMessage() { return message; }
    public void setMessage(String value) { this.message = value; }

    public boolean getStatus() { return status; }
    public void setStatus(boolean value) { this.status = value; }
}
