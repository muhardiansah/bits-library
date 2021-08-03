package com.example.bitslibrary.Models;

public class DaftarPinjamanResponse {
    private DaftarPinjamanDataResponse[] data;
    private String message;
    private boolean status;

    public DaftarPinjamanDataResponse[] getData() {
        return data;
    }

    public void setData(DaftarPinjamanDataResponse[] data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
