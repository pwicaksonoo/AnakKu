package com.example.anakku.models;

import java.util.Date;

public class ActivityAnak {
    private String name;
    private Date created;

    public ActivityAnak(){ };

    public ActivityAnak(String name, Date created) {
        super();

        this.name = name;
        this.created = created;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
