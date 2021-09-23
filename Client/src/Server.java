package mordekaiser;

import java.net.*;
import java.lang.Runnable;

public class Server
{
    public static void main ( String [] args )
    {
        try
        {
            DatagramSocket socket = new DatagramSocket ( 9999 );
            /* 3个服务器并行工作 */
            MultiSocket s1 = new MultiSocket ( "1", socket );
            MultiSocket s2 = new MultiSocket ( "2", socket );
            MultiSocket s3 = new MultiSocket ( "3", socket );
            s1.start();
            s2.start();
            s3.start();
        }
        catch ( Exception e )
        {
            e.printStackTrace ( );
        }
    }
}

class MultiSocket implements Runnable
{
    private static byte wantToAck = 1; // 下一个想要获得的分组的序列号
    private Thread t;
    private String number;
    private DatagramSocket sharedSocket;
    MultiSocket ( String n, DatagramSocket s )
    {
        number = n;
        sharedSocket = s;
    }

    public void start ( )
    {
        System.out.println ( "启动" + number + "号服务器。" );
        if ( t == null )
        {
            t = new Thread ( this, number );
            t.start ( );
        }
    }
    /* 乱序处理同步方法 */
    public synchronized void luanXuChuLi ( byte b )
    {
        try
        {
            if ( ( b - 48 ) == wantToAck )
            {
                String message = "ACK" + ( b - 48 );
                byte [] ack = message.getBytes ( );
                DatagramPacket ackmessage = new DatagramPacket ( ack, ack.length, InetAddress.getLocalHost ( ), 8888 );
                sharedSocket.send ( ackmessage );
                wantToAck++;
            }
            else
            {
                String message = "ACK" + ( wantToAck - 1 );
                byte [] ack = message.getBytes ( );
                DatagramPacket ackmessage = new DatagramPacket ( ack, ack.length, InetAddress.getLocalHost ( ), 8888 );
                sharedSocket.send ( ackmessage );
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    public void run ( )
    {
        try
        {
            while ( true )
            {
                byte [] content = new byte[1024];
                DatagramPacket rcv = new DatagramPacket ( content, content.length );
                sharedSocket.receive ( rcv );
                byte [] data = rcv.getData ( );
                int length = rcv.getLength ( );
                System.out.println ( "server" + number + "：收到一个分组，信息为“" + new String ( data, 0, length ) + "”。");
                luanXuChuLi ( data[0] );
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
}
