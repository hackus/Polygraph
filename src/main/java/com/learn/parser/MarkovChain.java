package com.learn.parser;

import java.util.*;
import java.util.function.Consumer;

public class MarkovChain {

    final SortedMap<String, SortedMap<String, Double>> markovChain = new TreeMap<>();
    final Tokenizator tokenizator;

    public MarkovChain(Tokenizator tokenizator){
        this.tokenizator = tokenizator;
        prepareMarkovChain();
    }

    public Tokenizator getTokenizator(){
        return tokenizator;
    }

    public Double getProbability(String previousWord, String word){
        Map<String, Double> value = markovChain.get(previousWord);
        if(value == null){
            return 0d;
        }
        Double val = value.get(word);
        if(value == null){
            return 0d;
        }
        return val;
    }

    private void prepareMarkovChain(){
        tokenizator.getFrequencyChain().forEach((a,b) -> {
            final Integer[] n = {0};

            Map<String, Double> sequence = ChainUtils.addKeyToChain(a, markovChain);

            b.forEach((c,d) -> {
                n[0] += d;
            });

            b.forEach((c,d) -> {
                Double probability = (double)1/ n[0] *d;
                sequence.put(c, probability);
            });
        });
    }

    public String getNextWord(String word){
        if(tokenizator.isEndCharacter(word)) return null;
        Double random = Math.random();
        Double [] prev = {0d};
        String [] foundWord = {""};
        markovChain.get(word)
                .entrySet()
                .stream()
                .forEachOrdered(it -> {
                    if((prev[0] < random) && (random <= (prev[0] + it.getValue()))) {
                        foundWord[0] = it.getKey();
                    }
                    prev[0] = prev[0] + it.getValue();
                });
        return foundWord[0];
    }
}
