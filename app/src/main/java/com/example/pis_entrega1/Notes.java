package com.example.pis_entrega1;

import java.util.Date;

public class Notes {
    private Date date;
    private String name;
    private Date deathLine;

    public Notes(long date, String name) {
        Date d = new Date(date * 1000);
        this.date = d;
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(long date) {
        Date d = new Date(date * 1000);
        this.date = d;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDeathLine() {
        return deathLine;
    }

    public void setDeathLine(Date deathLine) {
        this.deathLine = deathLine;
    }
}
