package com.eatmicco.gamecontrollerclient;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Miko on 7/16/2016.
 */
public class SettingFragment extends Fragment
{
    private SettingFragmentListener _listener;
    private EditText _ipAddressEditText;
    private EditText _portEditText;
    private Button _connectButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        _ipAddressEditText = (EditText)view.findViewById(R.id.addressEditText);
        _portEditText = (EditText)view.findViewById(R.id.portEditText);

        _connectButton = (Button)view.findViewById(R.id.connectButton);
        _connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String port = _portEditText.getText().toString();
                _listener.onConnectButtonPressed(_ipAddressEditText.getText().toString(), Integer.parseInt(port));
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof SettingFragmentListener)
        {
            _listener = (SettingFragmentListener)context;
        }
        else
        {
            throw new ClassCastException(context.toString() + " must implement SettingFragment.SettingFragmentListener");
        }
    }

    public interface SettingFragmentListener
    {
        void onConnectButtonPressed(String ipAddress, int port);
    }
}
