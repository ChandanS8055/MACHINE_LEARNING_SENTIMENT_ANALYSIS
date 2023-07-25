package com.sentimentanalysis.vader;

import java.text.DecimalFormat;

public class VScore
{
    private double positive;
    private double neutral;
    private double negative;
    private double compound;
    
    public VScore() {
    }
    
    public VScore(final double positive, final double neutral, final double negative, final double compound) {
        this.positive = positive;
        this.neutral = neutral;
        this.negative = negative;
        this.compound = compound;
    }
    
    @Override
    public String toString() {
        final DecimalFormat df3 = new DecimalFormat("#.###");
        final DecimalFormat df4 = new DecimalFormat("#.####");
        return "{'neg': " + df3.format(this.negative) + ", 'neu': " + df3.format(this.neutral) + ", 'pos': " + df3.format(this.positive) + ", 'compound': " + df4.format(this.compound) + "}";
    }
    
    public double getPositive() {
        return this.positive;
    }
    
    public double getNeutral() {
        return this.neutral;
    }
    
    public double getNegative() {
        return this.negative;
    }
    
    public double getCompound() {
        return this.compound;
    }
}
