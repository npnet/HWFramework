package com.huawei.nb.searchmanager.emuiclient;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.huawei.nb.searchmanager.emuiclient.IEmuiSearchServiceCall;
import com.huawei.nb.searchmanager.emuiclient.connect.RemoteServiceConnection;
import com.huawei.nb.searchmanager.emuiclient.connect.ServiceConnectCallback;
import com.huawei.nb.searchmanager.emuiclient.query.bulkcursor.BulkCursorDescriptorEx;
import java.util.List;

public class SearchServiceProxy implements ISearchClient {
    private static final String SEARCH_SERVICE_ACTION = "com.huawei.nb.searchmanager.service.SearchService.START";
    private static final String TAG = "SearchServiceProxy";
    private static final long TIMEOUT_MILLISECONDS = 5000;
    private volatile long callbackTimeout;
    private String callingPkgName;
    private volatile ServiceConnectCallback connectCallback = null;
    private final RemoteServiceConnection dsConnection;
    private volatile boolean hasBinded;
    private volatile boolean hasConnected;
    private final Object locker;
    private volatile IEmuiSearchServiceCall searchService = null;

    public SearchServiceProxy(Context context) {
        this.dsConnection = new RemoteServiceConnection(context, SEARCH_SERVICE_ACTION);
        this.callingPkgName = context.getPackageName();
        this.locker = new Object();
        this.hasConnected = false;
        this.hasBinded = false;
        this.callbackTimeout = TIMEOUT_MILLISECONDS;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void invokeConnectCallback(boolean connected) {
        if (connected) {
            if (this.connectCallback != null) {
                this.connectCallback.onConnect();
            }
        } else if (this.connectCallback != null) {
            this.connectCallback.onDisconnect();
        }
    }

    public boolean connect() {
        return connect(null);
    }

    public boolean connect(ServiceConnectCallback callback) {
        synchronized (this.locker) {
            if (this.hasBinded) {
                return true;
            }
            this.connectCallback = callback;
            if (this.searchService != null) {
                invokeConnectCallback(true);
            }
            this.hasBinded = this.dsConnection.open(new RemoteServiceConnection.OnConnectListener() {
                /* class com.huawei.nb.searchmanager.emuiclient.SearchServiceProxy.AnonymousClass1 */

                @Override // com.huawei.nb.searchmanager.emuiclient.connect.RemoteServiceConnection.OnConnectListener
                public void onConnect(IBinder binder) {
                    if (binder != null) {
                        SearchServiceProxy.this.searchService = IEmuiSearchServiceCall.Stub.asInterface(binder);
                        SearchServiceProxy.this.hasConnected = true;
                        SearchServiceProxy.this.invokeConnectCallback(true);
                        Log.i(SearchServiceProxy.TAG, "Succeed to connect");
                    }
                }

                @Override // com.huawei.nb.searchmanager.emuiclient.connect.RemoteServiceConnection.OnConnectListener
                public void onDisconnect() {
                    SearchServiceProxy.this.searchService = null;
                    SearchServiceProxy.this.hasConnected = false;
                    SearchServiceProxy.this.hasBinded = false;
                    SearchServiceProxy.this.invokeConnectCallback(false);
                    Log.w(SearchServiceProxy.TAG, "Connection to is broken down");
                }
            });
            if (!this.hasBinded) {
                Log.e(TAG, "Failed to open connection");
            }
            return this.hasBinded;
        }
    }

    public boolean disconnect() {
        synchronized (this.locker) {
            if (this.hasBinded) {
                invokeConnectCallback(false);
                this.dsConnection.close();
                this.searchService = null;
                this.hasBinded = false;
                this.hasConnected = false;
                Log.i(TAG, "close connection");
            } else {
                Log.i(TAG, "Connection has been closed already");
            }
        }
        return true;
    }

    public boolean hasConnected() {
        return this.hasConnected;
    }

    public boolean isBinded() {
        return this.hasBinded;
    }

    public void setExecutionTimeout(long timeout) {
        this.callbackTimeout = timeout;
    }

    @Override // com.huawei.nb.searchmanager.emuiclient.ISearchClient
    public void executeDBCrawl(String pkgName, List<String> ids, int op) {
        if (this.searchService == null) {
            Log.e(TAG, "Failed to executeDBCrawl, error: searchService is null.");
            return;
        }
        try {
            this.searchService.executeDBCrawl(pkgName, ids, op, this.callingPkgName);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to executeDBCrawl " + e.getMessage());
        }
    }

    @Override // com.huawei.nb.searchmanager.emuiclient.ISearchClient
    public BulkCursorDescriptorEx executeSearch(String pkgName, String queryString, List<String> fieldList) {
        if (this.searchService == null) {
            Log.e(TAG, "Failed to executeSearch, error: searchService is null.");
            return null;
        }
        try {
            return this.searchService.executeSearch(pkgName, queryString, fieldList, this.callingPkgName);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to executeSearch " + e.getMessage());
            return null;
        }
    }

    @Override // com.huawei.nb.searchmanager.emuiclient.ISearchClient
    public void clearUserIndexSearchData(String pkgName, int userId) {
        if (this.searchService == null) {
            Log.e(TAG, "Failed to clearUserIndexSearchData, error: searchService is null.");
            return;
        }
        try {
            this.searchService.executeClearData(pkgName, userId, this.callingPkgName);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to executeClearData " + e.getMessage());
        }
    }
}
