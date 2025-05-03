package com.arturfrimu.slidingwindowcounter;

import com.arturfrimu.RateLimiter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SlidingWindowCounterRateLimiter implements RateLimiter {
    private final int maxRequests;
    private final long windowSizeInMillis;
    private final int slotCount;
    private final long slotSizeInMillis;
    private final ConcurrentMap<String, SlotWindow> windows = new ConcurrentHashMap<>();

    private static class SlotWindow {
        long windowStart;
        long[] slots;
    }

    public SlidingWindowCounterRateLimiter(int maxRequests, long windowSizeInMillis, int slotCount) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInMillis;
        this.slotCount = slotCount;
        this.slotSizeInMillis = windowSizeInMillis / slotCount;
    }

    @Override
    public boolean allowRequest(String key) {
        long now = System.currentTimeMillis();
        SlotWindow w = windows.computeIfAbsent(key, id -> {
            SlotWindow sw = new SlotWindow();
            sw.windowStart = now;
            sw.slots = new long[slotCount];
            return sw;
        });
        synchronized (w) {
            long elapsed = now - w.windowStart;
            if (elapsed >= windowSizeInMillis) {
                w.windowStart = now;
                for (int i = 0; i < slotCount; i++) w.slots[i] = 0;
                elapsed = 0;
            }
            int currentSlot = (int) (elapsed / slotSizeInMillis);
            w.slots[currentSlot]++;
            long sum = 0;
            for (long c : w.slots) sum += c;
            return sum <= maxRequests;
        }
    }
}

