package com.eatmicco.gamecontrollerclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Miko on 6/25/2016.
 */
public class ClientUdp implements Runnable
{
    private InetAddress localAddress;
    private int remotePort;
    private DatagramSocket socket;
    private String message = null;

    ClientUdp(InetAddress localAddress, int remotePort)
    {
        this.localAddress = localAddress;
        this.remotePort = remotePort;
    }

    public void send(String message)
    {
        synchronized (this) {
            this.message = message;
        }
    }

    public void run() {
        try
        {
            socket = new DatagramSocket();
            while (true)
            {
                if (message != null)
                {
                    byte[] buffer = message.getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer, message.length(), this.localAddress, this.remotePort);
                    try
                    {
                        socket.send(packet);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        message = null;
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            socket.close();
        }
    }
}
