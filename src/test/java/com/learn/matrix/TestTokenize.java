package com.learn.matrix;

import com.learn.ai.bot.SentenceBot;
import com.learn.ai.polygraph.Polygraph;
import com.learn.parser.MarkovChain;
import com.learn.parser.Tokenizator;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

public class TestTokenize {
    @Test
    public void test(){
        String sentence = "A man123, a     plan,... a canal — Panama!!!";
        String[] parts = sentence.split("(?<!^)\\b");
        for (String part : parts)
            System.out.println('"' + part + "\" (" + (part.matches("\\w+") ? "word" : "punctuation") + ")");
    }


//    @Test
//    public void test1() throws IOException {
//        Tokenizator tokenizator = new Tokenizator();
//        tokenizator.parse(Paths.get("test.txt"));
//        MarkovChain chain = new MarkovChain(tokenizator);
//        Polygraph analizator = new Polygraph(chain);
//        Double result = analizator.analyze(Paths.get("textToAnalyze.txt"));
//        System.out.println(result);
//
//        String str = "";
//    }

    @Test
    public void test2() throws IOException {
        Tokenizator tokenizator = new Tokenizator();
        tokenizator.parse(Paths.get("test.txt"));
        MarkovChain chain = new MarkovChain(tokenizator);
        SentenceBot bot = new SentenceBot(chain);
        bot.generateSentence("The ");
        String str = "";
    }
}
