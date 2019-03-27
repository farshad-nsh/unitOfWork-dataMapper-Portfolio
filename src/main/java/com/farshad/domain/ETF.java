package com.farshad.domain;



import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "etf")
public class ETF  implements Serializable {

    @Id
    @Column(name="etfid")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int etfId;


    @Column
    private String name;

    @Column
    private String type;

    @Column
    private double value;

    public ETF(){

    }

    public String getName() {
        return name;
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

    public int getEtfId() {
        return etfId;
    }
}
