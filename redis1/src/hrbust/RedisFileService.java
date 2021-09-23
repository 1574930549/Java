package hrbust;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;

import redis.clients.jedis.Jedis;

public class RedisFileService {
	
    public static void main( String[] args )throws Exception{
    	//set
    	Jedis jedis = new Jedis(new URI("redis://127.0.0.1:6379"));
    	byte[] fb = fileToBytes(new File("123.pdf"));
    	jedis.set("123.pdf".getBytes(), fb);
    	
    	//get
//    	Jedis jedis = new Jedis(new URI("redis://127.0.0.1:6379"));
//    	byte[] fb = jedis.get("123.pdf".getBytes());
//    	bytesToFile(fb,new File("123.pdf"));
//    	jedis.close();
    	
    }
    
    
    public static byte[] objectToBytes(Object object)throws Exception{
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	ObjectOutputStream oos = new ObjectOutputStream(bos);
    	oos.writeObject(object);
    	byte[] fb = bos.toByteArray();
    	oos.close();
    	return fb;
		
	}
    
    public static Object byteToObject(byte[] bytes)throws Exception{
    	ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
    	ObjectInputStream ois = new ObjectInputStream(bis);
    	Object result = ois.readObject();
    	ois.close();
    	return result;
    	
    }
    
	
	
	public static byte[] fileToBytes(File file)throws Exception{
    	FileInputStream fis = new FileInputStream(file);
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	byte[] bytes  = new byte[1024];
    	int len = 0;
    	while((len =fis.read(bytes)) > 0){
    		bos.write(bytes, 0, len);
    	}
    	byte[] fb = bos.toByteArray();
    	fis.close();
    	bos.close();
    	return fb;
		
	}
	
	public static void bytesToFile(byte[] fb,File file)throws Exception{
		ByteArrayInputStream bis = new ByteArrayInputStream(fb);
    	FileOutputStream fos = new FileOutputStream(file);
    	byte[] bytes  = new byte[1024];
    	int len = 0;
    	while((len =bis.read(bytes)) > 0){
    		fos.write(bytes, 0, len);
    	}

    	fos.close();
    	bis.close();
	}

}
