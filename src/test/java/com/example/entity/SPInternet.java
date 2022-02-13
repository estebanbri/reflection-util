package com.example.entity;

public class SPInternet extends SPBaseProduct {

    private String bandwidth;

    public String getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(String bandwidth) {
        this.bandwidth = bandwidth;
    }

    @Override
    public String toString() {
        return "SPInternet{" +
                "bandwidth='" + bandwidth + '\'' +
                '}';
    }
}
