package com.android.settingpad;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.Settings;

import android.support.v7.preference.SwitchPreferenceCompat;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;

import java.util.List;

import static com.android.settingpad.MyApplication.getMdm;


public class settingFragment extends PreferenceFragmentCompat {
    boolean a;
    settingFragment(){
        this.a = true;
    }
    settingFragment(boolean a ){
        this.a = a;
    }

    public void onCreatePreferences(Bundle savedInstanceState,String s) {
        //从xml文件加载选项
        if (a){
            addPreferencesFromResource(R.layout.fragment_setting);
        }else {
        addPreferencesFromResource(R.layout.fragment_setting);
        }
    }

    @Override
    public boolean onPreferenceTreeClick(android.support.v7.preference.Preference preference) {
        SwitchPreferenceCompat sp;
        switch (preference.getKey()){
            case "WIFI":
                sp = (SwitchPreferenceCompat)findPreference("WIFI");
                getMdm().controlWiFiProxy(sp.isChecked());
                break;
            case "Bluetooth":
                sp = (SwitchPreferenceCompat)findPreference("Bluetooth");
                getMdm().controlBluetooth(sp.isChecked());
                break;
            case "Firewall":
                sp = (SwitchPreferenceCompat)findPreference("Firewall");
                getMdm().controlFireWall(sp.isChecked());
                Log.i("CalendarSync", "onPreferenceTreeClick: ");
                break;
            case "usb":
                sp = (SwitchPreferenceCompat)findPreference("usb");
                getMdm().controlUSb(sp.isChecked());
                break;
            case "otg":
                sp = (SwitchPreferenceCompat)findPreference("otg");
                try {
                    getMdm().controlUSb(!sp.isChecked());
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case "TfCard":
                sp = (SwitchPreferenceCompat)findPreference("TfCard");
                getMdm().controlTFCard(!sp.isChecked());
                break;
            case "BluetoothManager":
                startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
                break;
            case "AppManager":
                startActivity(new Intent(Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS));
                break;
            case "StrongManager":
                startActivity(new Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS));
                break;
            case "SafeManager":
                startActivity(new Intent(Settings.ACTION_SECURITY_SETTINGS));
                break;
        }
        return super.onPreferenceTreeClick(preference);
    }


}

