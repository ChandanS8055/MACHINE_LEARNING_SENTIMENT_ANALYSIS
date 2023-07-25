package com.sentimentanalysis.vader;

import java.util.Iterator;
import java.util.List;

public class Token
{
    private String value;
    private String posTag;
    private double wordScore;
    
    public Token() {
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.value) + ":" + this.posTag;
    }
    
    public static String tokenListToString(final List<Token> tokenList) {
        if (tokenList != null) {
            final StringBuilder sb = new StringBuilder();
            for (final Token token : tokenList) {
                sb.append(token.getValue()).append(" ");
            }
            return sb.toString();
        }
        return "";
    }
    
    public Token(final String value, final String posTag) {
        this.value = value;
        this.posTag = posTag;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public void setValue(final String value) {
        this.value = value;
    }
    
    public String getPosTag() {
        return this.posTag;
    }
    
    public void setPosTag(final String posTag) {
        this.posTag = posTag;
    }
    
    public double getWordScore() {
        return this.wordScore;
    }
    
    public void setWordScore(final double wordScore) {
        this.wordScore = wordScore;
    }
}