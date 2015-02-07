package com.microsoft.example;

import com.microsoft.*;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

//import com.microsoft.example.RandomSentenceSpout;

public class WordCountTopology {

  //Entry point for the topology
  public static void main(String[] args) throws Exception {
  //Used to build the topology
    TopologyBuilder builder = new TopologyBuilder();
    //Add the spout, with a name of 'spout'
    //and parallelism hint of 5 executors
    
    
//    cb.setOAuthConsumerKey("pZaijdzlpEszPfhLE7YWid6nB");
//    cb.setOAuthConsumerSecret("dWBdUVV3LnfueizekmXfR73rHYTK4XVhmuqjIcuYYdsU9rkKtb");
//    cb.setOAuthAccessToken("130407825-zCZRtTA4XbiyUgmxsaAi4HNuZfDOaaPNCpYmeFIu");
//    cb.setOAuthAccessTokenSecret("fhjdxNGdkUmz9CLhxfBW6t1wQ18CT7I9gjhjisdR3k2Wk");
    
    String consumerkey = "pZaijdzlpEszPfhLE7YWid6nB";
    String consumerSecret = "dWBdUVV3LnfueizekmXfR73rHYTK4XVhmuqjIcuYYdsU9rkKtb";
    String accessToken ="130407825-zCZRtTA4XbiyUgmxsaAi4HNuZfDOaaPNCpYmeFIu";
    String accessTokenSecret = "fhjdxNGdkUmz9CLhxfBW6t1wQ18CT7I9gjhjisdR3k2Wk";
    String[] specifyKeyWords={"hello","ruby"};
    TwitterSampleSpout spout = new TwitterSampleSpout(consumerkey,consumerSecret,accessToken,accessTokenSecret,specifyKeyWords);
    
//    this.consumerKey = "343S5AjUmtRcPLSUIpeafSLYX";
//	this.consumerSecret = "BNfMdgOzaB3RcDSAz3mVUL6Hkp7IYMi6D4GdXflw5rmNjCoMYl";
//	this.accessToken = "130407825-IGFKg04hYHQEOINYXMYTcjs8cTRQ4HxLkJOMmQUC";
//	this.accessTokenSecret = "6NjEPE59s7wWb31SORxKqv9IR4QdwQ4uY4aG3befUaZQO";
    builder.setSpout("spout", spout, 5);
    //Add the SplitSentence bolt, with a name of 'split'
    //and parallelism hint of 8 executors
    //shufflegrouping subscribes to the spout, and equally distributes
    //tuples (sentences) across instances of the SplitSentence bolt
   
    builder.setBolt("split", new SplitSentence(), 8).shuffleGrouping("spout");
    //Add the counter, with a name of 'count'
    //and parallelism hint of 12 executors
    //fieldsgrouping subscribes to the split bolt, and
    //ensures that the same word is sent to the same instance (group by field 'word')
    builder.setBolt("count", new WordCount(), 12).fieldsGrouping("split", new Fields("word"));

    //new configuration
    Config conf = new Config();
    conf.setDebug(true);

    //If there are arguments, we are running on a cluster
    if (args != null && args.length > 0) {
      //parallelism hint to set the number of workers
      conf.setNumWorkers(3);
      //submit the topology
      StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
    }
    //Otherwise, we are running locally
    else {
      //Cap the maximum number of executors that can be spawned
      //for a component to 3
      conf.setMaxTaskParallelism(3);
      //LocalCluster is used to run locally
      LocalCluster cluster = new LocalCluster();
      //submit the topology
      cluster.submitTopology("word-count", conf, builder.createTopology());
      //sleep
      Thread.sleep(10000);
      //shut down the cluster
      cluster.shutdown();
    }
  }
}
