package com.eatmicco.gamecontrollerclient;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements SettingFragment.SettingFragmentListener, ControllerFragment.ControllerFragmentListener
{
    public static final String TAG = "GameControllerClient";

    private SettingFragment _settingFragment;
    private ControllerFragment _controllerFragment;
    private ArrayList<Fragment> _fragmentList = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _settingFragment = new SettingFragment();
        _controllerFragment = new ControllerFragment();
        _fragmentList.add(_settingFragment);
        _fragmentList.add(_controllerFragment);

        displayFragment(_settingFragment);
    }

    protected void displayFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        for (Fragment f : _fragmentList)
        {
            if (f.isAdded())
            {
                fragmentTransaction.hide(f);
            }
        }

        if (fragment.isAdded())
        {
            fragmentTransaction.show(fragment);
        }
        else
        {
            fragmentTransaction.add(R.id.placeholder, fragment);
        }
        fragmentTransaction.commit();
    }

    public void onConnectButtonPressed(String ipAddress, int port)
    {
        displayFragment(_controllerFragment);
        _controllerFragment.connect(ipAddress, port);
    }

    public void onBackButtonPressed()
    {
        displayFragment(_settingFragment);
    }
}
