package com.microsoft.example;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Main {

	private static Jedis jedisClient = null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		jedisClient = new Jedis("192.168.100.106",6379);
		jedisClient.append("tokunaga", "1111");
		JedisPool pool = new JedisPool(new JedisPoolConfig(),"192.168.100.106");
		try(Jedis jedis = pool.getResource()){
//			jedis.set("foo", "bar");
//			String foobar = jedis.get("foo");
//			jedis.zadd("sose",9,"car");
//			jedis.zadd("sose",0,"bike");
			Set<String> sose = jedis.zrange("sose",0,-2);
			Long tmp = jedis.llen("mylist");
			 List<String> out = null;
			    try {
			      out = jedis.blpop(0, "mylist");
			    }catch(Exception e){
			    	
			    }
			  for (String a: out){
				  System.out.println(a);
			  }
//			for(String tmp:sose){
//				System.out.println(tmp);
//			}
		}catch (Exception e){
			
		}
	}

}
