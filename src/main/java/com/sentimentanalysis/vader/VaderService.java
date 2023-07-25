package com.sentimentanalysis.vader;

import com.sentimentanalysis.dao.TwitterKeywordDAO;
import com.sentimentanalysis.pojo.TwitterKeyword;
import com.sentimentanalysis.daoimpl.TwitterKeywordDAOImpl;
import java.util.Iterator;
import java.util.List;
import com.sentimentanalysis.dao.TwitterHandleDAO;
import twitter4j.Status;
import com.sentimentanalysis.tweets.TweetsExtractor;
import com.sentimentanalysis.pojo.TwitterHandle;
import com.sentimentanalysis.daoimpl.TwitterHandleDAOImpl;
import java.util.LinkedHashMap;
import java.util.Map;

public class VaderService
{
    public Map<String, Map<String, Map<String, VScore>>> analyzeTwitterHandles(final String email) throws Exception {
        final Map<String, Map<String, Map<String, VScore>>> result = new LinkedHashMap<String, Map<String, Map<String, VScore>>>();
        final TwitterHandleDAO tDao = new TwitterHandleDAOImpl();
        final List<TwitterHandle> handles = tDao.getTwitterHandlesByUser(email);
        for (final TwitterHandle handle : handles) {
            final List<Status> tweets = TweetsExtractor.getTweetsByHandle(handle.getHandle(), 100);
            final Map<String, Map<String, VScore>> vscore_result = new LinkedHashMap<String, Map<String, VScore>>();
            for (final Status status : tweets) {
                vscore_result.put(status.getText(), this.analyzeText(status.getText()));
            }
            result.put(handle.getHandle(), vscore_result);
        }
        return result;
    }
    
    public Map<String, Map<String, Map<String, VScore>>> analyzeTwitterKeywords(final String email) throws Exception {
        final Map<String, Map<String, Map<String, VScore>>> result = new LinkedHashMap<String, Map<String, Map<String, VScore>>>();
        final TwitterKeywordDAO tDao = new TwitterKeywordDAOImpl();
        final List<TwitterKeyword> keywords = tDao.getTwitterKeywordsByUser(email);
        for (final TwitterKeyword keyword : keywords) {
            final List<Status> tweets = TweetsExtractor.getTweets(keyword.getKeyword(), 100, 2);
            final Map<String, Map<String, VScore>> vscore_result = new LinkedHashMap<String, Map<String, VScore>>();
            for (final Status status : tweets) {
                vscore_result.put(status.getText(), this.analyzeText(status.getText()));
            }
            result.put(keyword.getKeyword(), vscore_result);
        }
        return result;
    }
    
    public Map<String, VScore> analyzeText(final String str) throws Exception {
        final Map<String, VScore> result = new LinkedHashMap<String, VScore>();
        final String fileText = new String(str);
        final Vader vader = new Vader();
        vader.init();
        final VaderNLP vaderNLP = new VaderNLP();
        vaderNLP.init();
        final List<List<Token>> sentenceList = vaderNLP.parse(fileText);
        for (final List<Token> sentence : sentenceList) {
            final VScore vaderScore = vader.analyseSentence(sentence);
            result.put(Token.tokenListToString(sentence), vaderScore);
        }
        return result;
    }
}
