package com.learn.parser;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Tokenizator {
    String previousWord = "";

    String r1Regex = "(?<!^)\\b";
    String r2Regex = "(?=(\\b\\w+(?:\\s+\\w+){1}\\b))";
    String r3Regex = "(?=(\\b\\w+(?:\\s+\\w+){2}\\b))";


    Map<String, Map<String, Integer>> frequencyChain = new HashMap<>();
    Map<String, Integer> wordFrequencyMap = new HashMap<>();
    Map<String, Double>  wordFrequencyProbabilityMap = new HashMap<>();

    public Tokenizator(){}

    public Map<String, Map<String, Integer>> getFrequencyChain(){
        return frequencyChain;
    }

    public Double getProbabilityFrequency(String word){
        return wordFrequencyProbabilityMap.get(word);
    }

    public void parse(Path path) throws IOException {
        Files.lines(Paths.get("test.txt"), Charset.defaultCharset())
                .forEach(it->parseLine(it));
        updateProbabilityFrequency();
    }

    private void parseLine(String line){
        Arrays.stream(line.split(r3Regex))
                .filter(it->!it.equals(" "))
                .forEach(it->addToStatisticsChain(it));
    }

    private void updateProbabilityFrequency(){
        long n = wordFrequencyMap.entrySet().stream().count();
        wordFrequencyMap.entrySet().stream().forEach(it->{
           double probability = (double)it.getValue()/n;
           wordFrequencyProbabilityMap.put(it.getKey(), probability);
        });
    }

    private void addToStatisticsChain(String word){
        updateWordFrequencyMap(word);
        updateMarkovChain(word);
        if (isEndCharacter(word)) {
            previousWord = "";
        } else {
            previousWord = word;
        }
    }

    private void updateWordFrequencyMap(String word){
        Integer frequency = wordFrequencyMap.get(word);
        if(frequency == null){
            wordFrequencyMap.put(word, 1);
        } else {
            wordFrequencyMap.put(word, ++frequency);
        }
    }

    private void updateMarkovChain(String word){
        Map<String, Integer> statistics = frequencyChain.get(previousWord);
        if(statistics == null){
            statistics = new HashMap<>();
            statistics.put(word, 1);
            frequencyChain.put(previousWord, statistics);
        } else {
            Integer value = statistics.get(word);
            if(value == null){
                statistics.put(word, 1);
            } else {
                statistics.put(word, ++value);
            }
        }
    }

    private Stream getEndCharacters(){
        return Stream.of(".", "?", "!");
    }

    private Stream getApostrophe(){
        return Stream.of("'", "\"", "(", "-");
    }

    public boolean isEndCharacter(String str){
        return !isWord(str) && getEndCharacters()
                .anyMatch(it -> str.contains((CharSequence) it));
    }

    public boolean isApostrophe(String str){
        return getApostrophe().anyMatch(it->str.contains((CharSequence) it));
    }


    public boolean isWord(String str){
        return str.matches("\\w+");
    }

    public void convertMatrix(){
        frequencyChain.entrySet().stream().forEach(it->{
            String word = it.getKey();
            it.getValue().entrySet().stream().forEach(seq -> {
                String parity = seq.getKey();
            });
        });
    }
}
