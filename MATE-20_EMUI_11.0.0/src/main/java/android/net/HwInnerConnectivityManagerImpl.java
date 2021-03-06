package android.net;

import android.os.SystemProperties;
import android.telephony.HwTelephonyManagerInner;
import android.telephony.SubscriptionManager;
import android.util.Log;
import android.util.SparseIntArray;
import com.android.internal.telephony.HuaweiTelephonyConfigs;
import com.android.internal.telephony.HwConnectivityManagerConstants;
import com.huawei.displayengine.IDisplayEngineService;
import java.util.HashMap;
import java.util.Map;

public class HwInnerConnectivityManagerImpl implements HwConnectivityManagerConstants, HwInnerConnectivityManager {
    private static final String TAG = "HwInnerConnectivityManagerImpl";
    private static HwInnerConnectivityManagerImpl mInstance = new HwInnerConnectivityManagerImpl();

    public static HwInnerConnectivityManagerImpl getDefault() {
        return mInstance;
    }

    public boolean isHwFeature(String feature) {
        Log.d(TAG, "isHwFeature: for feature = " + feature);
        if (getFeature(feature)[1] != null) {
            return true;
        }
        return false;
    }

    /* JADX DEBUG: Can't convert new array creation: APUT found in different block: 0x005e: APUT  
      (r0v2 'result' java.lang.String[] A[D('result' java.lang.String[])])
      (0 ??[int, short, byte, char])
      (r6v1 'str' java.lang.String A[D('str' java.lang.String)])
     */
    public String[] getFeature(String str) {
        if (str != null) {
            String[] result = new String[2];
            String reqSub = null;
            if (str.equals("enableMMS_sub1")) {
                str = "enableMMS";
                reqSub = String.valueOf(0);
            } else if (str.equals("enableMMS_sub2")) {
                str = "enableMMS";
                reqSub = String.valueOf(1);
            } else if (HuaweiTelephonyConfigs.isChinaTelecom() && str.equals("enableSUPL")) {
                reqSub = String.valueOf(HwTelephonyManagerInner.getDefault().getDefault4GSlotId());
            } else if ("enableHIPRI_sub1".equals(str)) {
                str = "enableHIPRI";
                reqSub = String.valueOf(0);
            } else if ("enableHIPRI_sub2".equals(str)) {
                str = "enableHIPRI";
                reqSub = String.valueOf(1);
            }
            result[0] = str;
            result[1] = reqSub;
            return result;
        }
        throw new IllegalArgumentException("getFeature() received null string");
    }

    public boolean checkHwFeature(String feature, NetworkCapabilities networkCapabilities, int networkType) {
        Log.d(TAG, "startUsingNetworkFeature: for feature = " + feature);
        String[] result = getFeature(feature);
        String feature2 = result[0];
        String reqSubId = result[1];
        if (reqSubId == null) {
            return false;
        }
        Log.d(TAG, "networkCapabilities setNetworkSpecifier reqSubId = " + reqSubId);
        networkCapabilities.setNetworkSpecifier(new StringNetworkSpecifier(reqSubId));
        if (isDualCellDataForHipri(networkType, feature2, reqSubId)) {
            networkCapabilities.setDualCellData("true");
        }
        return true;
    }

    public String getNetworkTypeNameEx(int type) {
        switch (type) {
            case 38:
                return "MOBILE_BIP0";
            case 39:
                return "MOBILE_BIP1";
            case 40:
                return "MOBILE_BIP2";
            case 41:
                return "MOBILE_BIP3";
            case 42:
                return "MOBILE_BIP4";
            case 43:
                return "MOBILE_BIP5";
            case 44:
                return "MOBILE_BIP6";
            case 45:
                return "MOBILE_XCAP";
            case 46:
                return "WIFI_MMS";
            case 47:
                return "WIFI_XCAP";
            case 48:
                return "MOBILE_INTERNAL_DEFAULT";
            default:
                return Integer.toString(type);
        }
    }

    public boolean isNetworkTypeMobileEx(int networkType) {
        switch (networkType) {
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case IDisplayEngineService.DE_ACTION_MOTION_SWAP /* 54 */:
                return true;
            case 46:
            case 47:
            default:
                return false;
        }
    }

    public boolean isNetworkTypeWifiEx(int networkType) {
        if (networkType == 46 || networkType == 47) {
            return true;
        }
        return false;
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0083 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0084  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x008e  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x009c  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00a3  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00aa  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00b1  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00b8  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00bf  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00c6  */
    public NetworkCapabilities networkCapabilitiesForFeatureEx(HwCustConnectivityManager mCust, String feature) {
        char c;
        int hashCode = feature.hashCode();
        if (hashCode != -2106358591) {
            if (hashCode != -595311794) {
                if (hashCode != 1893314653) {
                    switch (hashCode) {
                        case 1892665450:
                            if (feature.equals("enableBIP0")) {
                                c = 0;
                                break;
                            }
                            break;
                        case 1892665451:
                            if (feature.equals("enableBIP1")) {
                                c = 1;
                                break;
                            }
                            break;
                        case 1892665452:
                            if (feature.equals("enableBIP2")) {
                                c = 2;
                                break;
                            }
                            break;
                        case 1892665453:
                            if (feature.equals("enableBIP3")) {
                                c = 3;
                                break;
                            }
                            break;
                        case 1892665454:
                            if (feature.equals("enableBIP4")) {
                                c = 4;
                                break;
                            }
                            break;
                        case 1892665455:
                            if (feature.equals("enableBIP5")) {
                                c = 5;
                                break;
                            }
                            break;
                        case 1892665456:
                            if (feature.equals("enableBIP6")) {
                                c = 6;
                                break;
                            }
                            break;
                    }
                    switch (c) {
                        case 0:
                            return ConnectivityManager.networkCapabilitiesForType(38);
                        case 1:
                            return ConnectivityManager.networkCapabilitiesForType(39);
                        case 2:
                            return ConnectivityManager.networkCapabilitiesForType(40);
                        case 3:
                            return ConnectivityManager.networkCapabilitiesForType(41);
                        case 4:
                            return ConnectivityManager.networkCapabilitiesForType(42);
                        case 5:
                            return ConnectivityManager.networkCapabilitiesForType(43);
                        case 6:
                            return ConnectivityManager.networkCapabilitiesForType(44);
                        case 7:
                            return ConnectivityManager.networkCapabilitiesForType(45);
                        case '\b':
                            return ConnectivityManager.networkCapabilitiesForType(48);
                        case '\t':
                            if (mCust != null) {
                                return mCust.networkCapabilitiesForEimsType(15);
                            }
                            return null;
                        default:
                            return null;
                    }
                } else if (feature.equals("enableXCAP")) {
                    c = 7;
                    switch (c) {
                    }
                }
            } else if (feature.equals("enableEmergency")) {
                c = '\t';
                switch (c) {
                }
            }
        } else if (feature.equals("enableInternalDefault")) {
            c = '\b';
            switch (c) {
            }
        }
        c = 65535;
        switch (c) {
        }
    }

    public Map<String, Integer> inferLegacyTypeForNetworkCapabilitiesEx(NetworkCapabilities netCap, HwCustConnectivityManager mCust, SparseIntArray sLegacyTypeToTransport, SparseIntArray sLegacyTypeToCapability) {
        Map<String, Integer> resultMap = new HashMap<>();
        String type = null;
        int result = -1;
        if (netCap.hasCapability(25)) {
            type = "enableBIP0";
            result = 38;
        } else if (netCap.hasCapability(26)) {
            type = "enableBIP1";
            result = 39;
        } else if (netCap.hasCapability(27)) {
            type = "enableBIP2";
            result = 40;
        } else if (netCap.hasCapability(28)) {
            type = "enableBIP3";
            result = 41;
        } else if (netCap.hasCapability(29)) {
            type = "enableBIP4";
            result = 42;
        } else if (netCap.hasCapability(30)) {
            type = "enableBIP5";
            result = 43;
        } else if (netCap.hasCapability(31)) {
            type = "enableBIP6";
            result = 44;
        } else if (netCap.hasCapability(9)) {
            type = "enableXCAP";
            result = 45;
        } else if (netCap.hasCapability(32)) {
            type = "enableInternalDefault";
            result = 48;
        } else if (mCust != null && mCust.canHandleEimsNetworkCapabilities(netCap)) {
            type = "enableEmergency";
            result = 15;
            sLegacyTypeToTransport.put(15, 0);
            sLegacyTypeToCapability.put(15, 10);
        }
        resultMap.put(type, Integer.valueOf(result));
        return resultMap;
    }

    public int legacyTypeForNetworkCapabilitiesEx(NetworkCapabilities netCap, HwCustConnectivityManager mCust) {
        if (netCap.hasCapability(25)) {
            return 38;
        }
        if (netCap.hasCapability(26)) {
            return 39;
        }
        if (netCap.hasCapability(27)) {
            return 40;
        }
        if (netCap.hasCapability(28)) {
            return 41;
        }
        if (netCap.hasCapability(29)) {
            return 42;
        }
        if (netCap.hasCapability(30)) {
            return 43;
        }
        if (netCap.hasCapability(31)) {
            return 44;
        }
        if (netCap.hasCapability(9)) {
            if (netCap.hasTransport(1)) {
                return 47;
            }
            if (netCap.hasTransport(0)) {
                return 45;
            }
        }
        if (netCap.hasCapability(32)) {
            return 48;
        }
        if (mCust == null || !mCust.canHandleEimsNetworkCapabilities(netCap)) {
            return -1;
        }
        return 15;
    }

    public void setLegacyTypeToTransportEx(SparseIntArray legacyTypeToTransport) {
        legacyTypeToTransport.put(38, 0);
        legacyTypeToTransport.put(39, 0);
        legacyTypeToTransport.put(40, 0);
        legacyTypeToTransport.put(41, 0);
        legacyTypeToTransport.put(42, 0);
        legacyTypeToTransport.put(43, 0);
        legacyTypeToTransport.put(44, 0);
        legacyTypeToTransport.put(45, 0);
        legacyTypeToTransport.put(46, 1);
        legacyTypeToTransport.put(47, 1);
        legacyTypeToTransport.put(48, 0);
    }

    public void setLegacyTypeToCapabilityEx(SparseIntArray legacyTypeToCapability) {
        legacyTypeToCapability.put(38, 25);
        legacyTypeToCapability.put(39, 26);
        legacyTypeToCapability.put(40, 27);
        legacyTypeToCapability.put(41, 28);
        legacyTypeToCapability.put(42, 29);
        legacyTypeToCapability.put(43, 30);
        legacyTypeToCapability.put(44, 31);
        legacyTypeToCapability.put(45, 9);
        legacyTypeToCapability.put(46, 0);
        legacyTypeToCapability.put(47, 9);
        legacyTypeToCapability.put(48, 32);
    }

    private boolean isDualCellDataForHipri(int networkType, String feature, String subId) {
        if (!SystemProperties.getBoolean("ro.hwpp.dual_cell_data", false) || !"enableHIPRI".equals(feature) || networkType != 0 || subId == null || ((!subId.equals(String.valueOf(0)) && !subId.equals(String.valueOf(1))) || subId.equals(String.valueOf(SubscriptionManager.getDefaultDataSubscriptionId())))) {
            return false;
        }
        return true;
    }
}
