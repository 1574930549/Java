package mordekaiser;

import java.net.*;

public class Client
{
    public static void main ( String [] args )
    {
        try
        {
            /* 设置分组，格式遵循：序号.内容 */
            String [] group = new String[7];
            group[1] = "1.i am group1";
            group[2] = "2.i am group2";
            group[3] = "3.i am group3";
            group[4] = "4.i am group4";
            group[5] = "5.i am group5";
            group[6] = "6.i am group6";
            /* 建立UDP传输技术的对象 */
            DatagramSocket socket = new DatagramSocket ( 8888 );
            /* 对应分组是否经历过第一次传输 */
            boolean [] send = { false, false, false, false, false, false, false };
            byte [] message = new byte[1024];
            /* 为每个分组建立UDP传输包 */
            DatagramPacket [] packet = new DatagramPacket[7];
            message = group[1].getBytes ( );
            packet[1] = new DatagramPacket ( message, message.length, InetAddress.getLocalHost ( ), 9999 );
            message = group[2].getBytes ( );
            packet[2] = new DatagramPacket ( message, message.length, InetAddress.getLocalHost ( ), 9999 );
            message = group[3].getBytes ( );
            packet[3] = new DatagramPacket ( message, message.length, InetAddress.getLocalHost ( ), 9999 );
            message = group[4].getBytes ( );
            packet[4] = new DatagramPacket ( message, message.length, InetAddress.getLocalHost ( ), 9999 );
            message = group[5].getBytes ( );
            packet[5] = new DatagramPacket ( message, message.length, InetAddress.getLocalHost ( ), 9999 );
            message = group[6].getBytes ( );
            packet[6] = new DatagramPacket ( message, message.length, InetAddress.getLocalHost ( ), 9999 );
            /* 窗口大小初始化为3，发送前三组*/
            socket.send ( packet[1] );
            send[1] = true;
            System.out.println ( "client：发送了pakcet1。");
            socket.send ( packet[2] );
            send[2] = true;
            System.out.println ( "client：发送了pakcet2。");
            socket.send ( packet[3] );
            send[3] = true;
            System.out.println ( "client：发送了pakcet3。");
            /* first为指定滑动窗口第一个序列位置 */
            int first = 1;
            /* ack标识是否收到了第6组的ack */
            boolean  ack = false;
            /* 用来记录重复ack的次数 */
            int repeat = 0;
            socket.setSoTimeout( 5000 );

            while ( ack == false )
            {
                try
                {
                    byte [] content = new byte[1024];
                    /* 创建一个用来接收ack信息的包 */
                    DatagramPacket rcv = new DatagramPacket ( content, content.length );
                    socket.receive ( rcv );
                    byte [] data = rcv.getData ( );
                    int length = rcv.getLength ( );
                    System.out.println ( "client：收到了一个ack，内容是“" + new String ( data, 0, length ) + "”。" );
                    if ( ( data[3] - 48 ) >= first ) // 收到正确的ack
                    {
                        if ( ( data[3] - 48 ) == 6 )
                        {
                            ack = true;
                        }
                        /* 对窗口进行滑动 */
                        first = ( data[3] - 48 ) + 1;
                        repeat = 0;
                        if ( first >= 4 )
                        {
                            for ( int i = first; i <= 6; i++ )
                            {
                                if ( send[i] == false )
                                {
                                    socket.send ( packet[i] );
                                    send[i] = true;
                                    System.out.println ( "client：发送了pakcet" + i + "。" );
                                }
                            }
                        }
                        else
                        {
                            for ( int i = first; i <= first + 2; i++ )
                            {
                                if ( send[i] == false )
                                {
                                    socket.send ( packet[i] );
                                    send[i] = true;
                                    System.out.println ( "client：发送了pakcet" + i + "。" );
                                }
                            }
                        }
                    }
                    else if ( ( data[3] - 48 ) == ( first - 1 ) ) // 收到可能重复的ack
                    {
                        repeat++;
                        if ( repeat >= 2 )
                        {
                            if ( first >= 4 )
                            {
                                for ( int i = first; i <= 6; i++ )
                                {
                                    socket.send ( packet[i] );
                                    System.out.println ( "client：收到过多重复ack，重传了pakcet" + i + "。" );
                                }
                            }
                            else
                            {
                                for ( int i = first; i <= first + 2; i++ )
                                {
                                    socket.send ( packet[i] );
                                    System.out.println ( "client：收到过多重复ack，重传了pakcet" + i + "。" );
                                }
                            }
                            repeat = 0;
                        }
                    }
                    else System.out.println ( "client：丢弃该ack，不做处理" ); // 收到非正确的ack
                }
                catch ( SocketTimeoutException e ) // 严重超时，捕获并处理异常
                {
                    if ( first >= 4 )
                    {
                        for ( int i = first; i <= 6; i++ )
                        {
                            socket.send ( packet[i] );
                            System.out.println ( "client：发生严重超时异常，重传了pakcet" + i + "。" );
                        }
                    }
                    else
                    {
                        for ( int i = first; i <= first + 2; i++ )
                        {
                            socket.send ( packet[i] );
                            System.out.println ( "client：发生严重超时异常，重传了pakcet" + i + "。" );
                        }
                    }
                    repeat = 0;
                }
            }
            socket.close ( );
        }
        catch ( Exception e )
        {
            e.printStackTrace ( );
        }
    }
}
