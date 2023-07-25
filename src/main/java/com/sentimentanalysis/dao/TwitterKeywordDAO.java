package com.sentimentanalysis.dao;

import java.util.List;
import com.sentimentanalysis.pojo.TwitterKeyword;

public interface TwitterKeywordDAO
{
    void create(final TwitterKeyword p0) throws Exception;
    
    List<TwitterKeyword> getTwitterKeywordsByUser(final String p0) throws Exception;
    
    void delete(final String p0, final String p1) throws Exception;
}