package com.android.internal.telephony.metrics;

import android.os.SystemClock;
import com.android.internal.telephony.nano.TelephonyProto;
import com.android.internal.telephony.separated.metrics.TelephonyMetrics;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicInteger;

public class InProgressSmsSession {
    private static final int MAX_EVENTS = 20;
    public final Deque<TelephonyProto.SmsSession.Event> events;
    private boolean mEventsDropped = false;
    private long mLastElapsedTimeMs;
    private AtomicInteger mNumExpectedResponses = new AtomicInteger(0);
    public final int phoneId;
    public final long startElapsedTimeMs;
    public final int startSystemTimeMin;

    public void increaseExpectedResponse() {
        this.mNumExpectedResponses.incrementAndGet();
    }

    public void decreaseExpectedResponse() {
        this.mNumExpectedResponses.decrementAndGet();
    }

    public int getNumExpectedResponses() {
        return this.mNumExpectedResponses.get();
    }

    public boolean isEventsDropped() {
        return this.mEventsDropped;
    }

    public InProgressSmsSession(int phoneId2) {
        this.phoneId = phoneId2;
        this.events = new ArrayDeque();
        this.startSystemTimeMin = TelephonyMetrics.roundSessionStart(System.currentTimeMillis());
        this.startElapsedTimeMs = SystemClock.elapsedRealtime();
        this.mLastElapsedTimeMs = this.startElapsedTimeMs;
    }

    public void addEvent(SmsSessionEventBuilder builder) {
        addEvent(SystemClock.elapsedRealtime(), builder);
    }

    public synchronized void addEvent(long timestamp, SmsSessionEventBuilder builder) {
        if (this.events.size() >= MAX_EVENTS) {
            this.events.removeFirst();
            this.mEventsDropped = true;
        }
        builder.setDelay(TelephonyMetrics.toPrivacyFuzzedTimeInterval(this.mLastElapsedTimeMs, timestamp));
        this.events.add(builder.build());
        this.mLastElapsedTimeMs = timestamp;
    }
}
