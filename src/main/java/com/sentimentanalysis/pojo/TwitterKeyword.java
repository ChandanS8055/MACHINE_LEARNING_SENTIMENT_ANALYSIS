package com.sentimentanalysis.pojo;

import java.sql.Timestamp;

public class TwitterKeyword
{
    private String keyword;
    private String user;
    private Timestamp entry_time;
    
    public String getKeyword() {
        return this.keyword;
    }
    
    public void setKeyword(final String keyword) {
        this.keyword = keyword;
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
