import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class test1{
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("114.55.40.20",80);
        System.out.println(socket.isConnected());
        //发送的http数据
        StringBuilder requestData = new StringBuilder();
        //header
        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "text/html;charset=utf-8");
        header.put("Connection", "keep-alive");
        header.put("Host", "114.55.40.20");
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:53.0) Gecko/20100101 Firefox/53.0");
        //开始构造 GET 后面的请求路径全写
        requestData.append("GET http://www.weather.com.cn、 HTTP/1.1\r\n");
        for (Map.Entry<String, String> headerEntry : header.entrySet()) {
            requestData.append(headerEntry.getKey() + ": " + headerEntry.getValue() + "\r\n");
        }
        //结束请求头要加\r\n
        requestData.append("\r\n");
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(requestData.toString().getBytes());
        outputStream.flush();
        InputStream inputStream = socket.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] response = new byte[1024];
        int len = -1;
        while((len = inputStream.read(response)) != -1){
            baos.write(response,0,len);
            System.out.println(1);
        }
        socket.close();
        System.out.println("response:");
        System.out.println(new String(baos.toByteArray()));
    }
}
