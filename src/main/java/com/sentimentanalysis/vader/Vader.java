package com.sentimentanalysis.vader;

import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Vader
{
    private static final double B_INCR = 0.293;
    private static final double B_DECR = -0.293;
    private static final double ALPHA = 15.0;
    private static final double c_INCR = 0.733;
    private static final int idiomMaxSize = 5;
    private static final String[] NEGATE;
    private static final String[] BoosterIncrementList;
    private static final String[] BoosterDecreaseList;
    private static final Map<String, Double> boosterMap;
    private static final Map<String, Double> idiomMap;
    private Map<String, Double> moodSet;
    private HashSet<String> negatedSet;
    
    static {
        NEGATE = new String[] { "aint", "arent", "cannot", "cant", "couldnt", "darent", "didnt", "doesnt", "ain't", "aren't", "can't", "couldn't", "daren't", "didn't", "doesn't", "dont", "hadnt", "hasnt", "havent", "mightnt", "mustnt", "neither", "don't", "hadn't", "hasn't", "haven't", "isn't", "isnt", "mightn't", "mustn't", "neednt", "needn't", "never", "none", "nope", "nor", "not", "nothing", "nowhere", "oughtnt", "shant", "shouldnt", "uhuh", "wasnt", "werent", "oughtn't", "shan't", "shouldn't", "uh-uh", "wasn't", "weren't", "without", "wont", "wouldnt", "won't", "wouldn't", "rarely", "seldom", "despite" };
        BoosterIncrementList = new String[] { "absolutely", "amazingly", "awfully", "completely", "considerably", "decidedly", "deeply", "effing", "enormously", "entirely", "especially", "exceptionally", "extremely", "fabulously", "flipping", "flippin", "fricking", "frickin", "frigging", "friggin", "fully", "fucking", "greatly", "hella", "highly", "hugely", "incredibly", "intensely", "majorly", "more", "most", "particularly", "purely", "quite", "really", "remarkably", "so", "substantially", "thoroughly", "totally", "tremendously", "uber", "unbelievably", "unusually", "utterly", "very" };
        BoosterDecreaseList = new String[] { "almost", "barely", "hardly", "just enough", "kind of", "kinda", "kindof", "kind-of", "less", "little", "marginally", "occasionally", "partly", "scarcely", "slightly", "somewhat", "sort of", "sorta", "sortof", "sort-of" };
        boosterMap = new HashMap<String, Double>();
        idiomMap = new HashMap<String, Double>();
    }
    
    public VScore analyseSentence(final List<Token> sentence) {
        if (sentence != null) {
            final boolean isCapsDifferential = this.isAllCAPDifferential(sentence);
            List<Double> sentiments = new ArrayList<Double>();
            int i = 0;
            final List<Token> snt = this.filterPunctuation(sentence);
            for (final Token item : snt) {
                double v = 0.0;
                final String itemLowercase = item.getValue().toLowerCase();
                if ((i + 1 < snt.size() && itemLowercase.equals("kind") && this.wordInSentenceEquals(snt, i + 1, "of")) || Vader.boosterMap.containsKey(itemLowercase)) {
                    sentiments.add(v);
                    ++i;
                }
                else {
                    if (this.moodSet.containsKey(itemLowercase)) {
                        v = this.moodSet.get(itemLowercase);
                        if (isCapsDifferential && this.isUpper(item.getValue())) {
                            if (v > 0.0) {
                                v += 0.733;
                            }
                            else {
                                v -= 0.733;
                            }
                        }
                        final double nScalar = -0.74;
                        if (i > 0 && !this.moodSetContainsSentenceIndex(snt, i - 1)) {
                            final double s1 = this.scalarIncDec(snt.get(i - 1).getValue(), v, isCapsDifferential);
                            v += s1;
                        }
                        if (i > 1 && !this.moodSetContainsSentenceIndex(snt, i - 2)) {
                            final double s2 = this.scalarIncDec(snt.get(i - 2).getValue(), v, isCapsDifferential);
                            v += s2 * 0.95;
                            if (this.wordInSentenceEquals(snt, i - 2, "never") && (this.wordInSentenceEquals(snt, i - 1, "so") || this.wordInSentenceEquals(snt, i - 1, "this"))) {
                                v *= 1.5;
                            }
                            else if (this.negated(snt, i - 2)) {
                                v *= nScalar;
                            }
                        }
                        if (i > 2 && !this.moodSetContainsSentenceIndex(snt, i - 3)) {
                            final double s3 = this.scalarIncDec(snt.get(i - 3).getValue(), v, isCapsDifferential);
                            v += s3 * 0.9;
                            if (this.wordInSentenceEquals(snt, i - 3, "never") && (this.wordInSentenceEquals(snt, i - 2, "so") || this.wordInSentenceEquals(snt, i - 2, "this") || this.wordInSentenceEquals(snt, i - 1, "so") || this.wordInSentenceEquals(snt, i - 1, "this"))) {
                                v *= 1.25;
                            }
                            else if (this.negated(snt, i - 3)) {
                                v *= nScalar;
                            }
                            final StringBuilder idiom = new StringBuilder();
                            for (int index = 0; index < 5 && index < snt.size(); ++index) {
                                idiom.append(this.getLcaseWordAt(snt, index + i));
                                final String idiomStr = idiom.toString();
                                if (Vader.idiomMap.containsKey(idiomStr)) {
                                    v = Vader.idiomMap.get(idiomStr);
                                }
                                if (Vader.boosterMap.containsKey(idiomStr)) {
                                    v -= 0.293;
                                }
                                idiom.append(" ");
                            }
                        }
                        if (i > 1 && !this.moodSetContainsSentenceIndex(snt, i - 1) && this.wordInSentenceEquals(snt, i - 1, "least")) {
                            if (!this.wordInSentenceEquals(snt, i - 2, "at") && !this.wordInSentenceEquals(snt, i - 2, "very")) {
                                v *= nScalar;
                            }
                        }
                        else if (i > 0 && !this.moodSetContainsSentenceIndex(snt, i - 1) && this.wordInSentenceEquals(snt, i - 1, "least")) {
                            v *= nScalar;
                        }
                    }
                    sentiments.add(v);
                    ++i;
                }
            }
            if (snt.size() == sentiments.size()) {
                for (int j = 0; j < snt.size(); ++j) {
                    snt.get(j).setWordScore(sentiments.get(j));
                }
            }
            int butIndex = -1;
            for (int k = 0; k < sentence.size(); ++k) {
                final Token t = sentence.get(k);
                if (t.getValue().equals("but") || t.getValue().equals("BUT")) {
                    butIndex = k;
                    break;
                }
            }
            if (butIndex >= 0) {
                final List<Double> newSentiments = new ArrayList<Double>();
                for (int l = 0; l < sentiments.size(); ++l) {
                    if (l < butIndex) {
                        newSentiments.add(sentiments.get(l) * 0.5);
                    }
                    else if (l > butIndex) {
                        newSentiments.add(sentiments.get(l) * 1.5);
                    }
                    else {
                        newSentiments.add(sentiments.get(l));
                    }
                }
                sentiments = newSentiments;
            }
            double sum = 0.0;
            for (final double value : sentiments) {
                sum += value;
            }
            int epCount = 0;
            for (final Token t2 : sentence) {
                if (t2.getValue().equals("!")) {
                    ++epCount;
                }
            }
            if (epCount > 4) {
                epCount = 4;
            }
            final double emAmplifier = epCount * 0.292;
            if (sum > 0.0) {
                sum += emAmplifier;
            }
            else if (sum < 0.0) {
                sum -= emAmplifier;
            }
            int qmCount = 0;
            for (final Token t3 : sentence) {
                if (t3.getValue().equals("?")) {
                    ++qmCount;
                }
            }
            double qmAmplifier = 0.0;
            if (qmCount > 1) {
                if (qmCount <= 3) {
                    qmAmplifier = qmCount * 0.18;
                }
                else {
                    qmAmplifier = 0.96;
                }
                if (sum > 0.0) {
                    sum += qmAmplifier;
                }
                else if (sum < 0.0) {
                    sum -= qmAmplifier;
                }
            }
            final double compound = this.normalize(sum);
            double posSum = 0.0;
            double negSum = 0.0;
            double neutralCount = 0.0;
            for (final double sentimentScore : sentiments) {
                if (sentimentScore > 0.0) {
                    posSum = posSum + sentimentScore + 1.0;
                }
                if (sentimentScore < 0.0) {
                    negSum = negSum + sentimentScore - 1.0;
                }
                if (sentimentScore == 0.0) {
                    ++neutralCount;
                }
            }
            if (posSum > Math.abs(negSum)) {
                posSum = posSum + qmAmplifier + emAmplifier;
            }
            else if (posSum < Math.abs(negSum)) {
                negSum -= qmAmplifier + emAmplifier;
            }
            final double total = posSum + Math.abs(negSum) + neutralCount;
            if (total > 0.0) {
                posSum = Math.abs(posSum / total);
                negSum = Math.abs(negSum / total);
                neutralCount = Math.abs(neutralCount / total);
            }
            else {
                posSum = 0.0;
                negSum = 0.0;
                neutralCount = 0.0;
            }
            return new VScore(posSum, neutralCount, negSum, compound);
        }
        return new VScore();
    }
    
    private boolean moodSetContainsSentenceIndex(final List<Token> sentence, final int index) {
        if (index >= 0 && index < sentence.size()) {
            final Token t = sentence.get(index);
            return this.moodSet.containsKey(t.getValue().toLowerCase());
        }
        return false;
    }
    
    private boolean wordInSentenceEquals(final List<Token> sentence, final int index, final String wordStr) {
        if (index >= 0 && index < sentence.size()) {
            final Token t = sentence.get(index);
            return t.getValue().compareToIgnoreCase(wordStr) == 0;
        }
        return false;
    }
    
    private String getLcaseWordAt(final List<Token> sentence, final int index) {
        if (index >= 0 && index < sentence.size()) {
            final Token t = sentence.get(index);
            return t.getValue().toLowerCase();
        }
        return "";
    }
    
    public void init() throws IOException {
        this.moodSet = new HashMap<String, Double>();
        Throwable t = null;
        try {
            final InputStream vaderIn = this.getClass().getResourceAsStream("vader_sentiment_lexicon.txt");
            try {
                if (vaderIn == null) {
                    throw new IOException("vader_sentiment_lexicon.txt not found on class-path");
                }
                final String vaderLexicon = new String(IOUtils.toByteArray(vaderIn));
                if (vaderLexicon.length() > 0) {
                    String[] split;
                    for (int length = (split = vaderLexicon.split("\n")).length, i = 0; i < length; ++i) {
                        final String line = split[i];
                        final String[] items = line.split("\t");
                        if (items.length > 2) {
                            this.moodSet.put(items[0].trim(), Double.parseDouble(items[1].trim()));
                        }
                    }
                }
            }
            finally {
                if (vaderIn != null) {
                    vaderIn.close();
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
        String[] boosterIncrementList;
        for (int length2 = (boosterIncrementList = Vader.BoosterIncrementList).length, j = 0; j < length2; ++j) {
            final String incr = boosterIncrementList[j];
            Vader.boosterMap.put(incr, 0.293);
        }
        String[] boosterDecreaseList;
        for (int length3 = (boosterDecreaseList = Vader.BoosterDecreaseList).length, k = 0; k < length3; ++k) {
            final String decr = boosterDecreaseList[k];
            Vader.boosterMap.put(decr, -0.293);
        }
        Throwable t2 = null;
        try {
            final InputStream vaderIdiomsIn = this.getClass().getResourceAsStream("vader_idioms.txt");
            try {
                if (vaderIdiomsIn == null) {
                    throw new IOException("vader_idioms.txt not found on class-path");
                }
                final String vaderIdiomContent = new String(IOUtils.toByteArray(vaderIdiomsIn));
                if (vaderIdiomContent.length() > 0) {
                    String[] split2;
                    for (int length4 = (split2 = vaderIdiomContent.split("\n")).length, l = 0; l < length4; ++l) {
                        final String line = split2[l];
                        final String[] items = line.split(",");
                        if (items.length == 2) {
                            Vader.idiomMap.put(items[0].trim(), Double.parseDouble(items[1].trim()));
                        }
                    }
                }
            }
            finally {
                if (vaderIdiomsIn != null) {
                    vaderIdiomsIn.close();
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
        this.negatedSet = new HashSet<String>();
        String[] negate;
        for (int length5 = (negate = Vader.NEGATE).length, n = 0; n < length5; ++n) {
            final String str = negate[n];
            this.negatedSet.add(str);
        }
    }
    
    private boolean negated(final List<Token> sentence, final int index) {
        if (sentence != null) {
            final Token t = sentence.get(index);
            final String lcaseWord = t.getValue().toLowerCase();
            if (this.negatedSet.contains(lcaseWord)) {
                if (index + 1 < sentence.size()) {
                    final String lcaseWord2 = sentence.get(index + 1).getValue().toLowerCase();
                    if (lcaseWord2.equals("know") || lcaseWord2.equals("take") || lcaseWord2.equals("feel") || lcaseWord2.equals("like") || lcaseWord2.equals("want") || lcaseWord2.equals("wanna")) {
                        return false;
                    }
                }
                return true;
            }
            if (lcaseWord.contains("n't")) {
                return true;
            }
            if (lcaseWord.equals("least") && index > 0 && this.wordInSentenceEquals(sentence, index - 1, "at")) {
                return true;
            }
        }
        return false;
    }
    
    private double normalize(final double score) {
        return score / Math.sqrt(score * score + 15.0);
    }
    
    private boolean isUpper(final String str) {
        if (str != null) {
            char[] charArray;
            for (int length = (charArray = str.toCharArray()).length, i = 0; i < length; ++i) {
                final char ch = charArray[i];
                if (ch >= 'a' && ch <= 'z') {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    private boolean isAllCAPDifferential(final List<Token> sentence) {
        if (sentence != null) {
            int countAllCaps = 0;
            for (final Token t : sentence) {
                if (this.isUpper(t.getValue())) {
                    ++countAllCaps;
                }
            }
            final int capsDifferential = sentence.size() - countAllCaps;
            return capsDifferential > 0 && capsDifferential < sentence.size();
        }
        return false;
    }
    
    private double scalarIncDec(final String word, final double valence, final boolean isCapsDifferential) {
        double scalar = 0.0;
        final String lcase = word.toLowerCase();
        if (Vader.boosterMap.containsKey(lcase)) {
            scalar = Vader.boosterMap.get(lcase);
            if (valence < 0.0) {
                scalar *= -1.0;
            }
            if (this.isUpper(word) && isCapsDifferential) {
                if (valence > 0.0) {
                    scalar += 0.733;
                }
                else {
                    scalar -= 0.733;
                }
            }
        }
        return scalar;
    }
    
    private List<Token> filterPunctuation(final List<Token> sentence) {
        if (sentence != null) {
            final List<Token> newSentence = new ArrayList<Token>();
            for (final Token t : sentence) {
                if (t.getValue().length() > 1) {
                    newSentence.add(t);
                }
            }
            return newSentence;
        }
        return null;
    }
}
