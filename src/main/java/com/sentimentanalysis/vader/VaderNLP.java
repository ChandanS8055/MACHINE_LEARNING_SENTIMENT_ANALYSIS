package com.sentimentanalysis.vader;

import java.io.InputStream;
import opennlp.tools.postag.POSModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetector;

public class VaderNLP
{
    private SentenceDetector sentenceDetector;
    private POSTaggerME posTagger;
    private Tokenizer tokenizer;
    
    public VaderNLP() {
        this.sentenceDetector = null;
        this.posTagger = null;
    }
    
    public List<List<Token>> parse(final String text) throws IOException {
        if (text != null) {
            final List<List<Token>> sentenceList = new ArrayList<List<Token>>();
            final String[] sentenceArray = this.getSentences(text);
            String[] array;
            for (int length = (array = sentenceArray).length, j = 0; j < length; ++j) {
                final String sentenceStr = array[j];
                final List<Token> sentence = new ArrayList<Token>();
                final String[] words = this.getTokens(sentenceStr);
                final String[] posTags = this.getTags(words);
                String[] array2;
                for (int length2 = (array2 = posTags).length, k = 0; k < length2; ++k) {
                    final String s = array2[k];
                }
                if (words.length != posTags.length) {
                    throw new IOException("unmatched words / posTags in nlp-parser");
                }
                for (int i = 0; i < words.length; ++i) {
                    sentence.add(new Token(words[i], posTags[i]));
                }
                sentenceList.add(sentence);
            }
            return sentenceList;
        }
        return null;
    }
    
    private String[] getSentences(final String text) {
        return this.sentenceDetector.sentDetect(text);
    }
    
    private String[] getTokens(final String sentence) {
        return this.tokenizer.tokenize(sentence);
    }
    
    private String[] getTags(final String[] tokens) {
        return this.posTagger.tag(tokens);
    }
    
    public void init() throws IOException {
        Throwable t = null;
        try {
            final InputStream modelIn = this.getClass().getResourceAsStream("en-sent.bin");
            try {
                if (modelIn == null) {
                    throw new IOException("resource en-sent.bin not found in classpath");
                }
                final SentenceModel sentenceModel = new SentenceModel(modelIn);
                this.sentenceDetector = (SentenceDetector)new SentenceDetectorME(sentenceModel);
            }
            finally {
                if (modelIn != null) {
                    modelIn.close();
                }
            }
        }
        finally {
            if (t == null) {
                final Throwable exception = null;
                t = exception;
            }
            else {
                final Throwable exception = null;
                if (t != exception) {
                    t.addSuppressed(exception);
                }
            }
        }
        Throwable t2 = null;
        try {
            final InputStream modelIn = this.getClass().getResourceAsStream("en-token.bin");
            try {
                if (modelIn == null) {
                    throw new IOException("resource en-sent.bin not found in classpath");
                }
                final TokenizerModel tokenizerModel = new TokenizerModel(modelIn);
                this.tokenizer = (Tokenizer)new TokenizerME(tokenizerModel);
            }
            finally {
                if (modelIn != null) {
                    modelIn.close();
                }
            }
        }
        finally {
            if (t2 == null) {
                final Throwable exception2 = null;
                t2 = exception2;
            }
            else {
                final Throwable exception2 = null;
                if (t2 != exception2) {
                    t2.addSuppressed(exception2);
                }
            }
        }
        Throwable t3 = null;
        try {
            final InputStream modelIn = this.getClass().getResourceAsStream("en-pos-maxent.bin");
            try {
                if (modelIn == null) {
                    throw new IOException("resource en-sent.bin not found in classpath");
                }
                final POSModel posModel = new POSModel(modelIn);
                this.posTagger = new POSTaggerME(posModel);
            }
            finally {
                if (modelIn != null) {
                    modelIn.close();
                }
            }
        }
        finally {
            if (t3 == null) {
                final Throwable exception3 = null;
                t3 = exception3;
            }
            else {
                final Throwable exception3 = null;
                if (t3 != exception3) {
                    t3.addSuppressed(exception3);
                }
            }
        }
    }
}

