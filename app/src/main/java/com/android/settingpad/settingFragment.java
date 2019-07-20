package com.android.settingpad;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import java.util.List;
import com.android.settingpad.MyApplication;
import com.android.settingpad.utill.BluetoothSoldier;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.widget.EditText;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
                getMdm().setWifiProxy(!sp.isChecked());
                break;
            case "Bluetooth":
                sp = (SwitchPreferenceCompat)findPreference("Bluetooth");
                getMdm().allowBluetoothDataTransfer(!sp.isChecked());
                if (sp.isChecked()){
                    BluetoothSoldier.getInstance().enable();
                }else {

                }
                break;
            case "Firewall":
                sp = (SwitchPreferenceCompat)findPreference("Firewall");
                MyApplication.getMdm().wr
                break;
            case "usb":
                sp = (SwitchPreferenceCompat)findPreference("usb");
                getMdm().setUsbOnlyCharging(!sp.isChecked());
                break;
            case "otg":
                sp = (SwitchPreferenceCompat)findPreference("otg");
                try {
                    getMdm().setUsbOnlyCharging(!sp.isChecked());
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case "TfCard":
                sp = (SwitchPreferenceCompat)findPreference("TfCard");
                getMdm().setTFcard(!sp.isChecked());
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

