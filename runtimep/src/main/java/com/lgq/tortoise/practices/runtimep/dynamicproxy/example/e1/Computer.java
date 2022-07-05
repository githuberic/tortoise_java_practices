package com.lgq.tortoise.practices.runtimep.dynamicproxy.example.e1;

/**
 * @author lgq
 */
public class Computer {
    private Device device;

    public Device start() {
        System.out.println("Start Computer");
        device.run();
        return device;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
