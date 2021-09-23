import java.net.*;
import java.io.*;
public class URLSender {
    /**     * @param args */
    public static void main(String[] args) throws IOException {
        try {
            Socket socket = new Socket("www.nwu.edu.cn", 80);
            boolean autoflush = true;
            PrintWriter out = new PrintWriter(socket.getOutputStream(), autoflush);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //send an HTTP request to the web server
            out.println("GET / HTTP/1.1");
            out.println("Host: nwu.edu.cn");
            out.println("Connection: Close");
            out.println();
            //read the response
            boolean loop = true;
            StringBuffer sb = new StringBuffer(8096);
            while (loop) {
                if (in.ready()) {
                    int i = 0;
                    while (i != -1) {
                        i = in.read();
                        sb.append((char) i);
                    }
                    loop = false;
                }
                //Thread.currentThread().sleep(50);
            }
            //display the response to the out console
            System.out.println(sb.toString());
            socket.close();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: Victest.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for " + "the connection to: Victest.");
            System.exit(1);
        }
    }
}
