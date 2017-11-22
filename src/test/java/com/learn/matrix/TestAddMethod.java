package com.learn.matrix;

import com.learn.matrix.exceptions.MatrixDimensionsOutOfBoundOnAdd;
import com.learn.matrix.exceptions.MatrixIndexOutOfBoundException;
import com.learn.matrix.impl.Matrix;
import org.junit.Test;

public class TestAddMethod {
    @Test
    public void testAddMethod() throws MatrixIndexOutOfBoundException, MatrixDimensionsOutOfBoundOnAdd {
        Matrix<Integer> matrix1 = Matrix.fromString(new String[]{
            "0,1,2",
            "3,4,5",
            "7,3,4"
        }, Integer.class, it-> Integer.parseInt(it));

        Matrix<Integer> matrix2 = Matrix.fromString(new String[]{
                "1,1,1",
                "1,1,1",
                "1,1,1"
        }, Integer.class, it-> Integer.parseInt(it));

        Matrix<Integer> matrix3 = matrix1.add(matrix2, (x,y) -> x+y);

        matrix1.print();
        matrix3.print();
    }
}
