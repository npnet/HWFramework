package com.huawei.android.content;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IOnPrimaryClipGetedListener extends IInterface {
    void dispatchPrimaryClipGet() throws RemoteException;

    public static class Default implements IOnPrimaryClipGetedListener {
        @Override // com.huawei.android.content.IOnPrimaryClipGetedListener
        public void dispatchPrimaryClipGet() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IOnPrimaryClipGetedListener {
        private static final String DESCRIPTOR = "com.huawei.android.content.IOnPrimaryClipGetedListener";
        static final int TRANSACTION_dispatchPrimaryClipGet = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IOnPrimaryClipGetedListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IOnPrimaryClipGetedListener)) {
                return new Proxy(obj);
            }
            return (IOnPrimaryClipGetedListener) iin;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode != 1) {
                return null;
            }
            return "dispatchPrimaryClipGet";
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code == 1) {
                data.enforceInterface(DESCRIPTOR);
                dispatchPrimaryClipGet();
                return true;
            } else if (code != 1598968902) {
                return super.onTransact(code, data, reply, flags);
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        /* access modifiers changed from: private */
        public static class Proxy implements IOnPrimaryClipGetedListener {
            public static IOnPrimaryClipGetedListener sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.huawei.android.content.IOnPrimaryClipGetedListener
            public void dispatchPrimaryClipGet() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(1, _data, null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().dispatchPrimaryClipGet();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IOnPrimaryClipGetedListener impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IOnPrimaryClipGetedListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
