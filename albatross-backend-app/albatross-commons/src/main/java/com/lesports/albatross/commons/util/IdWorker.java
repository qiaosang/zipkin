package com.lesports.albatross.commons.util;

import java.net.InetAddress;

/**
 * Snowflake
 */
public class IdWorker {

    private final static long TWEPOCH = 1288834974657L; // 2010-11-04 09:42:54
    private final static long WORKER_ID_BITS = 5L;
    private final static long DATACENTER_ID_BITS = 5L;
    private final static long MAX_WORKER_ID = -1L ^ (-1L << WORKER_ID_BITS);
    private final static long MAX_DATACENTER_ID = -1L ^ (-1L << DATACENTER_ID_BITS);
    private final static long SEQUENCE_BITS = 12L;
    private final static long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private final static long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    private final static long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;
    private final static long SEQUENCE_MASK = -1L ^ (-1L << SEQUENCE_BITS);
    private static long staticLastTimestamp = -1L;
    private static long staticSequence = 0L;

    private long workerId;
    private long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public IdWorker(long workerId, long datacenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
            throw new IllegalArgumentException(
                    String.format("datacenter Id can't be greater than %d or less than 0", MAX_DATACENTER_ID));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public static synchronized long nextStaticId() {
        long timestamp = System.currentTimeMillis();
        if (timestamp < staticLastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                            staticLastTimestamp - timestamp));
        }
        if (staticLastTimestamp == timestamp) {
            staticSequence = (staticSequence + 1) & SEQUENCE_MASK;
            if (staticSequence == 0) {
                while (timestamp <= staticLastTimestamp) {
                    timestamp = System.currentTimeMillis();
                }
            }
        } else {
            staticSequence = 0L;
        }
        staticLastTimestamp = timestamp;
        Integer workerId = getWorkerIdFromHostName();
        return ((timestamp - TWEPOCH) << TIMESTAMP_LEFT_SHIFT) | (workerId << WORKER_ID_SHIFT) | staticSequence;
    }

    private static Integer getWorkerIdFromHostName() { // 默认容器命名规则是以数字结尾，取出容器尾部的数字作为workerId, FIXME:test_vm_0
        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            if (null == hostName || "" == hostName) return 0;
            char tmp = hostName.charAt(hostName.length() - 1);
            if (true == Character.isDigit(tmp)) return new Integer("" + tmp);
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }

    public static void main(String[] args) {
        // IdWorker idWorker = new IdWorker(0, 0);
        // for (int i = 0; i < 10; i++) {
        // System.out.println(idWorker.nextId());
        // }
        for (int i = 0; i < 10000; i++) {
            // System.out.println();
            Long tmp = IdWorker.nextStaticId();
            if (tmp > 0) System.out.println(tmp);
        }

        System.out.println("".equals(null));
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format(
                    "Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;

        return ((timestamp - TWEPOCH) << TIMESTAMP_LEFT_SHIFT) | (datacenterId << DATACENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT) | sequence;
    }
}
