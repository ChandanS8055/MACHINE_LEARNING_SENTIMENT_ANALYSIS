package com.sentimentanalysis.pojo;

import java.sql.Timestamp;

public class TwitterHandle
{
    private String handle;
    private String user;
    private Timestamp entry_time;
    
    public String getHandle() {
        return this.handle;
    }
    
    public void setHandle(final String handle) {
        this.handle = handle;
    }
    
    public String getUser() {
        return this.user;
    }
    
    public void setUser(final String user) {
        this.user = user;
    }
    
    public Timestamp getEntry_time() {
        return this.entry_time;
    }
    
    public void setEntry_time(final Timestamp entry_time) {
        this.entry_time = entry_time;
    }
}