package com.learn.matrix.impl;

import com.learn.matrix.exceptions.ExceptionMessages;
import com.learn.matrix.exceptions.MatrixDimensionsOutOfBoundOnAdd;
import com.learn.matrix.exceptions.MatrixIndexOutOfBoundException;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Matrix<T> {
    final int height;
    final int width;
    final T[][] matrix;

    public Matrix(int width, int height) {
        matrix = (T[][]) new Object[width][height];
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void put(int i, int j, T value) throws MatrixIndexOutOfBoundException {
        checkInexes(i, j);
        matrix[i][j] = value;
    }

    public T get(int i, int j) throws MatrixIndexOutOfBoundException {
        checkInexes(i, j);
        return matrix[i][j];
    }

    public Matrix<T> add(Matrix<T> matrixToAdd, BiFunction<T, T, T> function) throws MatrixDimensionsOutOfBoundOnAdd, MatrixIndexOutOfBoundException {
        checkDimensionsOnAdd(matrixToAdd);
        Matrix<T> added = new Matrix<T>(width, height);
        for(int i=0;i<height;i++) {
            for (int j = 0; j < width; j++) {
                added.put(i,j, function.apply(matrixToAdd.get(i,j), this.get(i,j)));
            }
        }
        return added;
    }

    public void print(){
        System.out.println();
        Arrays.asList(matrix).stream();
        for(int i=0;i<height;i++){
            String line = "";
            for(int j=0;j<width;j++){
                line +=matrix[i][j] + ",";
            }
            System.out.println(line.substring(0, line.length()-1));
        }
    }

    public static <E> Matrix fromString(String[] strings, Class<E> clazz, Function<String, E> conversionFunction) throws MatrixIndexOutOfBoundException {
        int resultWidth = strings[0].split("\\s*,\\s*").length;
        int resultHeight = strings.length;
        Matrix<E> result = new Matrix<>(resultWidth, resultHeight);
        for(int i=0;i<resultHeight;i++){
            String[] values = strings[i].split("\\s*,\\s*");
            for(int j=0;j<resultWidth;j++){
                result.put(i, j, conversionFunction.apply(values[j]));
            }
        }
        return result;
    }

    private void checkDimensionsOnAdd(Matrix<T> second) throws MatrixDimensionsOutOfBoundOnAdd {
        if(second.getWidth() != width){
            throw new MatrixDimensionsOutOfBoundOnAdd(String.format("Width %d not equal to %d", second.getWidth(), width));
        }
        if(second.getHeight() != height){
            throw new MatrixDimensionsOutOfBoundOnAdd(String.format("Height %d not equal to %d", second.getHeight(), width));
        }
    }

    private void checkInexes(int i, int j) throws MatrixIndexOutOfBoundException {
        if (i > width) {
            throw new MatrixIndexOutOfBoundException(String.format(ExceptionMessages.indexBiggerThanWidth, i, width));
        }
        if (i > width) {
            throw new MatrixIndexOutOfBoundException(String.format(ExceptionMessages.indexBiggerThanWidth, j, height));
        }
    }
}
