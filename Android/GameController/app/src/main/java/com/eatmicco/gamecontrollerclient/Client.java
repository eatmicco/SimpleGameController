package com.eatmicco.gamecontrollerclient;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Miko on 6/24/2016.
 */
public class Client extends AsyncTask<Void, Void, Void>
{
    String dstAddress;
    int dstPort;
    TextView textResponse;

    Client(String addr, int port)
    {
        dstAddress = addr;
        dstPort = port;
    }

    @Override
    protected Void doInBackground(Void... arg0)
    {
        Socket socket = null;

        try
        {
            //socket = new Socket(dstAddress, dstPort);
            socket = new Socket(InetAddress.getByName(dstAddress), dstPort);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
            byte[] buffer = new byte[1024];

            int bytesRead;
            InputStream inputStream = socket.getInputStream();

            Log.d(MainActivity.TAG, "This is blocking");

            // notice: inputStream.read() will block if no data return
            while ((bytesRead = inputStream.read(buffer)) != -1)
            {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (socket != null)
            {
                try
                {
                    socket.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            Log.d(MainActivity.TAG, "Finishing Task");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result)
    {
        super.onPostExecute(result);
    }
}
