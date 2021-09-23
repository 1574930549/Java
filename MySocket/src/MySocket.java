import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;

public class MySocket {

    public static void main(String[] args) throws IOException {
        //如果需要进行https的请求只需要换成如下一句（https的默认端口为443，http默认端口为80）
        //Socket socket = SSLSocketFactory.getDefault().createSocket("www.baidu.com", 443);
        Socket socket = new Socket("restapi.amap.com", 80);

        //获取输入流，即从服务器获取的数据
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //获取输出流，即我们写出给服务器的数据
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        //使用一个线程来进行读取服务器的响应
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    String line = null;
                    try {
                        while ((line = bufferedReader.readLine()) != null) {
                            System.out.println("接收数据 : " + line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        bufferedWriter.write("GET /v3/weather/weatherInfo?city=哈尔滨&key=13cb58f5884f9749287abbead9c658f2 HTTP/1.1\r\n");
        bufferedWriter.write("Host: restapi.amap.com\r\n\r\n");
        bufferedWriter.flush();

    }

}