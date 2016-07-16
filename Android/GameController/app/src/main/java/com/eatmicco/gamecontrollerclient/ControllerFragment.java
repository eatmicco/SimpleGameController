package com.eatmicco.gamecontrollerclient;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Miko on 7/16/2016.
 */
public class ControllerFragment extends Fragment
{
    private ControllerFragmentListener _listener;

    private Button _upButton, _downButton, _leftButton, _rightButton, _backButton;
    private ClientUdp _udpClient;
    private Thread _netThread;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_controller, container, false);

        _upButton = (Button)view.findViewById(R.id.upButton);
        _downButton = (Button)view.findViewById(R.id.downButton);
        _leftButton = (Button)view.findViewById(R.id.leftButton);
        _rightButton = (Button)view.findViewById(R.id.rightButton);
        _backButton = (Button)view.findViewById(R.id.backButton);

        _upButton.setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _udpClient.send("UP");
            }
        }));

        _downButton.setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _udpClient.send("DOWN");
            }
        }));

        _leftButton.setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _udpClient.send("LEFT");
            }
        }));

        _rightButton.setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _udpClient.send("RIGHT");
            }
        }));

        _backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _listener.onBackButtonPressed();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof ControllerFragmentListener)
        {
            _listener = (ControllerFragmentListener)context;
        }
        else
        {
            throw new ClassCastException(context.toString() + " must implement ControllerFragment.ControllerFragmentListener");
        }
    }

    public void connect(String ipAddress, int port)
    {
        if (ipAddress == null || ipAddress.isEmpty())
        {
            return;
        }

        InetAddress address = null;
        try
        {
            address = InetAddress.getByName(ipAddress);
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }

        _udpClient = new ClientUdp(address, port);
        _netThread = new Thread(_udpClient);
        _netThread.start();
        _udpClient.send("Connected");
    }

    public interface ControllerFragmentListener
    {
        void onBackButtonPressed();
    }
}
