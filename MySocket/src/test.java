import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
public class test {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("restapi.amap.com", 80);
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    String line = null;
                    try {
                        while ((line = bufferedReader.readLine()) != null) {
                            System.out.println("接收数据 : " + line);
                        }
                    }
                    catch (IOException e) {
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