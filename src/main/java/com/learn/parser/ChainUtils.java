package com.learn.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;

public class ChainUtils<T, E> {

    public static <T, E> Map<T, E> addKeyToChain(T key, Map<T, Map<T, E>> chain){
        Map<T, E> sequence = chain.get(key);
        if(sequence == null){
            chain.put(key, new HashMap<>());
            sequence = chain.get(key);
        }
        return sequence;
    }

    public static <T, E> SortedMap<T, E> addKeyToChain(T key, SortedMap<T, SortedMap<T, E>> chain){
        SortedMap<T, E> sequence = chain.get(key);
        if(sequence == null){
            chain.put(key, new TreeMap<>());
            sequence = chain.get(key);
        }
        return sequence;
    }

    private static <T, E> void addValueToSequence(Map<T, E> sequence, T sequenceKey, Function<E, E> function){
        sequence.put(sequenceKey, function.apply(sequence.get(sequenceKey)));
    }
}
