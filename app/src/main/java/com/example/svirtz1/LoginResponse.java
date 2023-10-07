package com.example.svirtz1;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }
}

