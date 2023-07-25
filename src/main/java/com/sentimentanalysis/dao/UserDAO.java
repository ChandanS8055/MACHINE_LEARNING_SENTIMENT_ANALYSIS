package com.sentimentanalysis.dao;

import com.sentimentanalysis.pojo.User;
import java.util.List;

public interface UserDAO
{
    List<String> getAllUsers() throws Exception;
    
    void register(final User p0) throws Exception;
    
    User getUserDetails(final String p0, final String p1) throws Exception;
    
    User getUserDetails(final String p0) throws Exception;
    
    void updateProfile(final User p0) throws Exception;
    
    void deleteProfile(final String p0) throws Exception;
    
    boolean changePassword(final String p0, final String p1, final String p2) throws Exception;
    
    String forgotPassword(final String p0) throws Exception;
    
    boolean adminlogin(final String p0, final String p1) throws Exception;
}
