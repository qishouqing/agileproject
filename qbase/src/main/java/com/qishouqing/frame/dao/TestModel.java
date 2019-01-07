package com.qishouqing.frame.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TestModel {



    @Id(autoincrement = true)
    private Long ids;


    private String id;

    private String string;

    @Generated(hash = 1950452015)
    public TestModel(Long ids, String id, String string) {
        this.ids = ids;
        this.id = id;
        this.string = string;
    }

    public TestModel(String id, String string) {
        this.id = id;
        this.string = string;
    }

    @Generated(hash = 1568142977)
    public TestModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getString() {
        return this.string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Long getIds() {
        return this.ids;
    }

    public void setIds(Long ids) {
        this.ids = ids;
    }

    
    

}
