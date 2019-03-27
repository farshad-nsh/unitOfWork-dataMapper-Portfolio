package com.farshad.domain;


import org.springframework.stereotype.Component;

/**
 * receiver
 */
@Component
public class Share {

    public void buy(double v) {
        System.out.println(v+" share is bought!");
    }

    public void sell(double v){
        System.out.println(v+" share is sold!");
    }

}