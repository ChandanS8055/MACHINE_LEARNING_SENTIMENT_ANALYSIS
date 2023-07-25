package com.sentimentanalysis.tweets;

import twitter4j.QueryResult;
import twitter4j.TwitterException;
import java.util.Collection;
import twitter4j.Query;
import java.util.Iterator;
import twitter4j.ResponseList;
import twitter4j.Twitter;
import java.util.ArrayList;
import twitter4j.Paging;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.Status;
import java.util.List;

public class TweetsExtractor
{
    public static List<Status> getTweetsByHandle(final String handle, final int maxcount) throws Exception {
        final ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey("C6cUT1Spwd4GRAPZcUH7gr4qL").setOAuthConsumerSecret("tUG5W0eAmouq7vw1UZxulu1C24sgBn563Z5amnWNgw4ARiqenH").setOAuthAccessToken("407651040-79M1aoKKDhPlTP8LBLMypAI4TfInD2ZBEzRI6hkf").setOAuthAccessTokenSecret("70K6CII1YseeOVXxLugPdxj42EkfJOPQk6jAkYQTL5rxN").setTweetModeExtended(true);
        final Twitter twitter = new TwitterFactory(cb.build()).getInstance();
        final Paging paging = new Paging();
        paging.setCount(maxcount);
        final ResponseList<Status> result = (ResponseList<Status>)twitter.getUserTimeline(handle, paging);
        final List<Status> response = new ArrayList<Status>();
        for (final Status s : result) {
            response.add(s);
        }
        return response;
    }
    
    public static List<Status> getTweets(final String topic, final int num, final int mode) throws TwitterException {
        final ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey("C6cUT1Spwd4GRAPZcUH7gr4qL").setOAuthConsumerSecret("tUG5W0eAmouq7vw1UZxulu1C24sgBn563Z5amnWNgw4ARiqenH").setOAuthAccessToken("407651040-79M1aoKKDhPlTP8LBLMypAI4TfInD2ZBEzRI6hkf").setOAuthAccessTokenSecret("70K6CII1YseeOVXxLugPdxj42EkfJOPQk6jAkYQTL5rxN").setTweetModeExtended(true);
        final Twitter twitter = new TwitterFactory(cb.build()).getInstance();
        final Query query = new Query(topic);
        final int numberOfTweets = num;
        long lastID = Long.MAX_VALUE;
        final ArrayList<Status> tweets = new ArrayList<Status>();
        if (mode == 0) {
            query.setResultType(Query.ResultType.mixed);
        }
        else if (mode == 1) {
            query.setResultType(Query.ResultType.popular);
        }
        else {
            query.setResultType(Query.ResultType.recent);
        }
        QueryResult result = twitter.search(query);
        while (tweets.size() < numberOfTweets && result.getTweets().size() > 0) {
            if (numberOfTweets - tweets.size() > 100) {
                query.setCount(100);
            }
            else {
                query.setCount(numberOfTweets - tweets.size());
            }
            try {
                result = twitter.search(query);
                tweets.addAll(result.getTweets());
                for (final Status t : tweets) {
                    if (t.getId() < lastID) {
                        lastID = t.getId();
                    }
                }
            }
            catch (TwitterException te) {
                System.out.println("Couldn't connect: " + te);
            }
            query.setMaxId(lastID - 1L);
        }
        return tweets;
    }
    
    public static void main(final String[] args) throws Exception {
        final List<Status> tweets = getTweets("Virat Kohli", 10, -1);
        for (final Status t : tweets) {
            System.out.println(t.getText());
            System.out.println("-------------------------------------------------------------------------------------------------------");
        }
    }
}