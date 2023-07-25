package com.sentimentanalysis.dao;

import java.util.List;
import com.sentimentanalysis.pojo.TwitterHandle;

public interface TwitterHandleDAO
{
    void create(final TwitterHandle p0) throws Exception;
    
    List<TwitterHandle> getTwitterHandlesByUser(final String p0) throws Exception;
    
    void delete(final String p0, final String p1) throws Exception;
}
