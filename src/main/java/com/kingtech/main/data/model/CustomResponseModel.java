package com.kingtech.main.data.model;

public class CustomResponseModel<T> {
    private final String message;
    private final T data;

    public CustomResponseModel(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
