package com.android.settingpad.utill;

import android.bluetooth.BluetoothAdapter;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.Method;


public class BluetoothSoldier
{
    private static BluetoothSoldier bluetoothSoldier;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    public static BluetoothSoldier getInstance()
    {
        try
        {
            if (bluetoothSoldier == null) {
                bluetoothSoldier = new BluetoothSoldier();
            }
            BluetoothSoldier localBluetoothSoldier = bluetoothSoldier;
            return localBluetoothSoldier;
        }
        finally {}
    }

    public boolean enable(){
        BluetoothAdapter.getDefaultAdapter().enable();
        return true;
    }
}
