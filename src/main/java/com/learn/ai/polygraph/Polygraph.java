package com.learn.ai.polygraph;

import com.learn.parser.MarkovChain;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static java.lang.Math.log;

public class Polygraph {
    final MarkovChain chain;

    public Polygraph(MarkovChain chain){
        this.chain = chain;
    }

    public Double analyze(Path path) throws IOException {
        Double value = Files.lines(Paths.get("textToAnalyze.txt"), Charset.defaultCharset())
                .mapToDouble(it->traverseMarkovChain(it))
                .reduce(0d, (a, b) -> a + b);

        return Math.pow(Math.E, value);
    }

    public Double traverseMarkovChain(String line){
        final String[] previous = {""};
        return Arrays.stream(line.split("(?<!^)\\b"))
                .filter(it->!it.equals(" "))
                .mapToDouble(it->{
                    Double value = chain.getProbability(previous[0], it);
                    previous[0] = it;
                    return value;
                })
                .reduce(0d, (a,b) -> a + log(b));
    }
}
