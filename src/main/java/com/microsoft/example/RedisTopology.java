package com.microsoft.example;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class RedisTopology {

	public static void main(String[] args) throws Exception {
		 TopologyBuilder builder = new TopologyBuilder();
//		 ("192.168.100.106",6379);
		 RedisQueueSpout spout = new RedisQueueSpout("192.168.100.106", 6379,"mylist");
		 builder.setSpout("spout", spout, 5);
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
