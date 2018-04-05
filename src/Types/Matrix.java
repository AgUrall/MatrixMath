package Types;

import java.util.Arrays;

/**
 * Created by Михаил on 01.05.2017.
 */

//class for containing matrix.
//float[][] matrix - left part of matrix;
//float[][] freePart - right part of matrix;
//bool canCalculate - If matrix have no solution it is false;
//bool manySolutions - If matrix have many solutions it is true;
//int sizeY - Y size of marix;
//int size - size of basis;
//int sizeX - X size of matrix;
//int sizeOfFreePart - X size of free part;
//int[] WCX - numbers of columns;


public class Matrix {
    private float[][] matrix;
    private float[][] freePart;

    private boolean canCalculate;


    private boolean manySolutions;
/*
    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }
*/
    private int sizeY;
    private int size;
    private int sizeX;
    private int sizeOfFreePart;

    public void setWCX(int x1, int x2) {

        int tmp = this.WCX[x1];
        this.WCX[x1] = this.WCX[x2];
        this.WCX[x2] = tmp;
/*
        this.WCX[x1] += this.WCX[x2];
        this.WCX[x2] = this.WCX[x1] - this.WCX[x2];
        this.WCX[x1] -= this.WCX[x2];
        //this.WCX[x2] *= -1;*/

    }

    private int[] WCX;

    public Matrix(float[][] matrix, float[][] freePart) throws IllegalArgumentException {
        if(matrix.length != freePart.length) {
            this.canCalculate = false;
            throw new IllegalArgumentException("free part is smaller then matrix");
        }
        if(matrix == null) throw new IllegalArgumentException("NPE. matrix");
        if(freePart == null) throw new IllegalArgumentException("NPE. free part");
        else this.canCalculate = true;
        this.matrix = matrix;
        this.freePart = freePart;
        this.sizeY = matrix.length;
        this.sizeX = matrix[0].length;
        this.sizeOfFreePart = freePart[0].length;
        WCX = new int [sizeX];
        for(int i = 0 ; i < sizeX;i++) WCX[i]=i+1;
    }

    public Matrix print()
    {
        System.err.println(Arrays.toString(WCX));
        for(int i = 0 ; i < sizeY;i++) {
            System.err.print(Arrays.toString(matrix[i]));
            System.err.println(Arrays.toString(freePart[i]));
        }
        return this;
    }

    public int getSizeOfFreePart() {
        return sizeOfFreePart;
    }
/*
    public Matrix setSizeOfFreePart(int sizeOfFreePart) {
        this.sizeOfFreePart = sizeOfFreePart;
        return this;
    }*/


    public float[][] getMatrix() {
        return matrix;
    }

    public Matrix setMatrix(float[][] matrix) {
        this.matrix = matrix;
        this.sizeY = matrix.length;
        this.sizeX = matrix[0].length;
        return this;
    }

    public float[][] getFreePart() {
        return freePart;
    }

    public Matrix setFreePart(float[][] freePart) {
        this.freePart = freePart;
        this.sizeOfFreePart = freePart[0].length;
        return  this;
    }

    public boolean isCanCalculate() {
        return canCalculate;
    }

    public Matrix setCanCalculate(boolean canCalculate) {
        if(!canCalculate)this.canCalculate = canCalculate;
        return  this;
    }

    public int getSize() {
        return size;
    }

    public Matrix setSize(int size) {
        this.size = size;
        return this;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public boolean isManySolutions() {
        return manySolutions;
    }

    public Matrix setManySolutions(boolean manySolutions) {
       if(manySolutions) this.manySolutions = manySolutions;
        return this;
    }


}
