package android.app.mia;

import android.content.Context;
import android.graphics.Bitmap;
import java.util.ArrayList;
import java.util.List;

public class MiaMdmPolicyManager
{
  private List<String> list = new ArrayList();
  private String s = null;
  
  public MiaMdmPolicyManager(Context paramContext) {}
  
  public List<String> APPIPWhiteListRead()
  {
    return null;
  }
  
  public boolean AddAppWhiteRule(String paramString)
  {
    return false;
  }
  
  public boolean ClearAppRules()
  {
    return false;
  }
  
  public boolean ClearURLListRules()
  {
    return false;
  }
  
  public boolean SetEnable(boolean paramBoolean)
  {
    return false;
  }
  
  public boolean allowBluetoothDataTransfer(boolean paramBoolean)
  {
    return false;
  }
  
  public List<String> appWhiteListRead()
  {
    return this.list;
  }
  
  public boolean appWhiteListWrite(List<String> paramList)
  {
    return false;
  }
  
  public void controlApp(String paramString, boolean paramBoolean) {}
  
  public void deleteLockPattern(int paramInt) {}
  
  public String getBtStatus()
  {
    return this.s;
  }
  
  public Bitmap getMiaScreen()
  {
    return null;
  }
  
  public String getWifiStatus()
  {
    return this.s;
  }
  
  public void killApplicationProcess(String paramString) {}
  
  public void masterClearInbulitSD() {}
  
  public void mobileData(boolean paramBoolean) {}
  
  public void setBackKey(boolean paramBoolean) {}
  
  public void setCamera(boolean paramBoolean) {}
  
  public boolean setGps(boolean paramBoolean)
  {
    return false;
  }
  
  public void setHomeKey(boolean paramBoolean) {}
  
  public void setNavigaBar(boolean paramBoolean) {}
  
  public void setOTA(boolean paramBoolean) {}
  
  public void setPowerLongPressKey(boolean paramBoolean) {}
  
  public void setPowerSingleClickKey(boolean paramBoolean) {}
  
  public void setRecentKey(boolean paramBoolean) {}
  
  public void setTFcard(boolean paramBoolean) {}
  
  public void setUsbOnlyCharging(boolean paramBoolean) {}
  
  public void setVoiceSize(boolean paramBoolean) {}
  
  public void setVolumedownKey(boolean paramBoolean) {}
  
  public void setVolumeupKey(boolean paramBoolean) {}
  
  public void setWifiProxy(boolean paramBoolean) {}
  
  public void shutDown() {}
  
  public void silentInstall(String paramString) {}
  
  public void silentUnInstall(String paramString) {}
  
  public boolean updateSystemTime(String paramString)
  {
    return false;
  }
  
  public List<String> urlBlackListRead()
  {
    return null;
  }
  
  public boolean urlBlackListWrite(List<String> paramList)
  {
    return true;
  }
  
  public boolean urlSetEnable(boolean paramBoolean)
  {
    return false;
  }
  
  public List<String> urlWhiteListRead()
  {
    return this.list;
  }
  
  public boolean urlWhiteListWrite(List<String> paramList)
  {
    return false;
  }
}
