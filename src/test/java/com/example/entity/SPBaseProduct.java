package com.example.entity;

import com.example.annotation.FieldFrom;

public class SPBaseProduct {

    @FieldFrom(role ="Sales Engineer")
    private String integralSolution;

    public String getIntegralSolution() {
        return integralSolution;
    }

    public void setIntegralSolution(String integralSolution) {
        this.integralSolution = integralSolution;
    }

    @Override
    public String toString() {
        return "SPBaseProduct{" +
                "integralSolution='" + integralSolution + '\'' +
                '}';
    }
}
