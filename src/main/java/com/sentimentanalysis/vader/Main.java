package com.sentimentanalysis.vader;

import java.util.Iterator;
import java.util.List;

public class Main
{
    public static void main(final String[] args) throws Exception {
        final String fileText = new String("Hello How are you. Hope you are doing good. Yesterday I was very happy because I talked to you. But today you are not answering my call. I am too sad.");
        final Vader vader = new Vader();
        vader.init();
        final VaderNLP vaderNLP = new VaderNLP();
        vaderNLP.init();
        final List<List<Token>> sentenceList = (List<List<Token>>)vaderNLP.parse(fileText);
        for (final List<Token> sentence : sentenceList) {
            final VScore vaderScore = vader.analyseSentence((List)sentence);
            System.out.println("sentence:" + Token.tokenListToString((List)sentence));
            System.out.println("Vader score:" + vaderScore.toString());
        }
    }
}