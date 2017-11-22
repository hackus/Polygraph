package com.learn.ai.bot;

import com.learn.parser.MarkovChain;

public class SentenceBot {

    final MarkovChain chain;
    public SentenceBot(MarkovChain chain){
        this.chain = chain;
    }

    public void generateSentence(String word){
        String nextWord = "";
        String sentence = word;
        while((nextWord = chain.getNextWord(word)) != null){
            sentence += (isSpaceRequired(word, nextWord) ? " " : "") + nextWord;
            word = nextWord;
        }

        System.out.println(sentence);
    }

    private boolean isSpaceRequired(String priviousWord, String nextWord){
        return chain.getTokenizator().isWord(nextWord)
                ? ((chain.getTokenizator().isApostrophe(priviousWord)) ? false : true)
                : false;
    }
}
