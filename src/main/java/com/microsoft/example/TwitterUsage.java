package com.microsoft.example;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterUsage {
	public static void main(String[] args) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
//        this.consumerKey = "343S5AjUmtRcPLSUIpeafSLYX";
//		this.consumerSecret = "BNfMdgOzaB3RcDSAz3mVUL6Hkp7IYMi6D4GdXflw5rmNjCoMYl";
//		this.accessToken = "130407825-IGFKg04hYHQEOINYXMYTcjs8cTRQ4HxLkJOMmQUC";
//		this.accessTokenSecret = "6NjEPE59s7wWb31SORxKqv9IR4QdwQ4uY4aG3befUaZQO";
        cb.setOAuthConsumerKey("pZaijdzlpEszPfhLE7YWid6nB");
        cb.setOAuthConsumerSecret("dWBdUVV3LnfueizekmXfR73rHYTK4XVhmuqjIcuYYdsU9rkKtb");
        cb.setOAuthAccessToken("130407825-zCZRtTA4XbiyUgmxsaAi4HNuZfDOaaPNCpYmeFIu");
        cb.setOAuthAccessTokenSecret("fhjdxNGdkUmz9CLhxfBW6t1wQ18CT7I9gjhjisdR3k2Wk");

        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

        StatusListener listener = new StatusListener() {

            @Override
            public void onException(Exception arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrubGeo(long arg0, long arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStatus(Status status) {

                // gets Username
                String username = status.getUser().getScreenName();
                System.out.println(username);
//                String profileLocation = user.getLocation();
//                System.out.println(profileLocation);
                long tweetId = status.getId(); 
                System.out.println(tweetId);
                String content = status.getText();
                System.out.println(content +"\n");

            }

            @Override
            public void onTrackLimitationNotice(int arg0) {
                // TODO Auto-generated method stub

            }



			@Override
			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub
				
			}

//			@Override
//			public void onStatus(Status status) {
//				// TODO Auto-generated method stub
//				
//			}

        };
        FilterQuery fq = new FilterQuery();
    
        String keywords[] = {"ireland"};

        fq.track(keywords);

        twitterStream.addListener(listener);
        twitterStream.filter(fq);  

    }
}
