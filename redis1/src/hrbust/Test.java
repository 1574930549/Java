package hrbust;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.net.URI;

import redis.clients.jedis.Jedis;

public class Test {
	
	
	
    public static void main2( String[] args )throws Exception
    {
//    	Jedis jedis = new Jedis("localhost",6379);
    	
    	Jedis jedis = new Jedis(new URI("redis://127.0.0.1:6379"));
    	
//    	jedis.set("hrbust", "12");
    	String value = jedis.get("hrbust");
    	jedis.incr("hrbust");
    	System.out.println(value);
     }
    


}
