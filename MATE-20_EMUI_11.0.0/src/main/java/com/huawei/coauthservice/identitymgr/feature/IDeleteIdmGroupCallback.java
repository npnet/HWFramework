package com.huawei.coauthservice.identitymgr.feature;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IDeleteIdmGroupCallback extends IInterface {
    void onFailed(int i) throws RemoteException;

    void onSuccess(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IDeleteIdmGroupCallback {
        private static final String DESCRIPTOR = "com.huawei.coauthservice.identitymgr.feature.IDeleteIdmGroupCallback";
        static final int TRANSACTION_onFailed = 2;
        static final int TRANSACTION_onSuccess = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDeleteIdmGroupCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IDeleteIdmGroupCallback)) {
                return new Proxy(obj);
            }
            return (IDeleteIdmGroupCallback) iin;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code == 1) {
                data.enforceInterface(DESCRIPTOR);
                onSuccess(data.readInt());
                reply.writeNoException();
                return true;
            } else if (code == 2) {
                data.enforceInterface(DESCRIPTOR);
                onFailed(data.readInt());
                reply.writeNoException();
                return true;
            } else if (code != 1598968902) {
                return super.onTransact(code, data, reply, flags);
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IDeleteIdmGroupCallback {
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

            @Override // com.huawei.coauthservice.identitymgr.feature.IDeleteIdmGroupCallback
            public void onSuccess(int result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(result);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.huawei.coauthservice.identitymgr.feature.IDeleteIdmGroupCallback
            public void onFailed(int reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(reason);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
