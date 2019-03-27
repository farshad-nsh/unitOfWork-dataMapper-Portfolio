package com.farshad.infrastructure.NAVContext;

public class Statement {

    private String name;  //farshadBond

    private String type; //asset or liabilities

    private double value;

    public String getName(String name) {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
