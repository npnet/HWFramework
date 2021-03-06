package android.view;

public final class SurfaceSession {
    private long mNativeClient;

    private static native long nativeCreate();

    private static native long nativeCreateScoped(long j);

    private static native void nativeDestroy(long j);

    private static native void nativeKill(long j);

    public SurfaceSession() {
        this.mNativeClient = nativeCreate();
    }

    public SurfaceSession(Surface root) {
        this.mNativeClient = nativeCreateScoped(root.mNativeObject);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        try {
            if (this.mNativeClient != 0) {
                nativeDestroy(this.mNativeClient);
            }
        } finally {
            super.finalize();
        }
    }

    public void kill() {
        nativeKill(this.mNativeClient);
    }
}
