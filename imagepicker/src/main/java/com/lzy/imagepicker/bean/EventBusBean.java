package com.lzy.imagepicker.bean;

public class EventBusBean {
    private String type;
    private String mString;

    public EventBusBean(String type, String mString) {
        this.type = type;
        this.mString = mString;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getmString() {
        return mString;
    }

    public void setmString(String mString) {
        this.mString = mString;
    }
}
