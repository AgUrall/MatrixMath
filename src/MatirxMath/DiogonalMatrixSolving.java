package MatirxMath;

import Types.Matrix;

/**
 * Created by Михаил on 01.05.2017.
 */
public class DiogonalMatrixSolving implements IMatrixSolving {
    private Matrix _matrix;
    private float _eps;

    public DiogonalMatrixSolving(Matrix matrix, float eps) {
        this._matrix = matrix;
        this._eps = eps;
    }

    public float getEps() {
        return _eps;
    }

    // solve matrix by convertsion to diogonal form. With choosin bigger element.
    @Override
    public void solve() {
        _matrix.print();
        toDiagonalForm(_matrix);
    }

    @Override
    public Matrix getMatrix() {
        return _matrix;
    }

    private Matrix toDiagonalForm(Matrix mat) {
        System.err.println("start firstgo");
        mat = firstGo(mat);
        mat.print();
        mat = checkSolution(mat);
        if(mat.isCanCalculate()) {
            System.err.println(mat.getSize() + " " + mat.getSizeY());
            mat = moveOfFreePart(mat);
            //mat.print();

            mat = backGo(mat);
            System.err.println("start backgo");
            mat.print();

            System.err.println(mat.isCanCalculate());
            if (mat.isCanCalculate()) {
                mat = toEform(mat);
                System.err.println("toEform");
                mat.print();
            }
        }
        else System.err.println("No solution");
        return mat;
    }

    private Matrix sort(float mat[][],int border,Matrix matr,float[][] freepart) {
        int xMax = border,yMax = border;
        for(int y= border; y < mat.length;y++)
        {
            for(int x = border; x < mat[border].length;x++)
            {
                if( Math.abs(mat[y][x]) > Math.abs(mat[yMax][xMax]) )
                {
                    yMax = y;
                    xMax = x;
                }
            }
        }

        float tmp;
        matr.setWCX(border,xMax);
        for(int y= border; y < mat.length; y++)
        {
            tmp = mat[y][xMax];
            mat[y][xMax] = mat[y][border];
            mat[y][border] = tmp;
        }


        for(int x = border; x < mat[border].length; x++)
        {

            tmp = mat[border][x];
            mat[border][x] = mat[yMax][x];
            mat[yMax][x] = tmp;
        }
        for(int x = 0; x < freepart[border].length; x++)
        {

            tmp = freepart[border][x];
            freepart[border][x] = freepart[yMax][x];
            freepart[yMax][x] = tmp;
        }
        matr.setFreePart( freepart);
        matr.setMatrix(mat);
        return  matr;
    }
    //check if float is smaller then epsilon;
    private float checkZero(float num)
    {
        return Math.abs(num ) <= this._eps ? 0 : num;
    }

    // first turn of matrix calculation, convert matrix to diagonal form
    // Matrix mat - matrix to convert
    // return converted Types.Matrix

    private Matrix firstGo(Matrix mat)
    {
        float mainMatr[][] = mat.getMatrix();

        float freePart[][] = mat.getFreePart();
        float coif;
        int i;
        int Xsize = mat.getSizeX();
        int Ysize = mat.getSizeY();
        int smallerSize = Ysize <= Xsize ? Ysize : Xsize;
        for(i = 0; i < smallerSize ; i++)
        {

            sort(mainMatr,i,mat,freePart);
            mainMatr = mat.getMatrix();
            freePart = mat.getFreePart();
            mat.print();
            System.err.println();
            float mainUnit = mainMatr[i][i];

            if (Math.abs(mainUnit) <= _eps){
                /*
                matrix.setMatrix(mainMatr);
                matrix.setFreePart(freePart);
                matrix.setCanCalculate(false);
                return mat;
                */
                break;
            }

            for(int y = i+1; y < Ysize; y++)
            {
                coif = mainMatr[y][i] / mainUnit;
                if(checkZero(mainMatr[y][i])!=0) {
                    for (int x = 0; x < Xsize; x++) {
                        mainMatr[y][x] -= mainMatr[i][x] * coif;
                        mainMatr[y][x] = checkZero(mainMatr[y][x]);
                    }

                    for (int x = 0; x < mat.getSizeOfFreePart(); x++) {
                        freePart[y][x] -= freePart[i][x] * coif;
                        freePart[y][x] = checkZero(freePart[y][x]);
                    }
                }
            }
        }
        /*
        if(smallerSize < Ysize)
        {
            float mainUnit = mainMatr[smallerSize - 1][smallerSize -1];

            for(int y = smallerSize ; y < Ysize;y++) {
                coif = mainMatr[y][Xsize] / mainUnit;
                if (checkZero(mainMatr[y][Xsize]) != 0) {

                        mainMatr[y][Xsize] -= mainMatr[smallerSize - 1][Xsize] * coif;
                        mainMatr[y][Xsize] = checkZero(mainMatr[y][Xsize]);


                    for (int x = 0; x < mat.getSizeOfFreePart(); x++) {
                        freePart[y][x] -= freePart[smallerSize - 1][x] * coif;
                        freePart[y][x] = checkZero(freePart[y][x]);
                    }
                }
            }
        }
        */
        int size = 0;
        //System.err.println("!!!" + smallerSize);
        do {
            size++;
        }while (size < smallerSize && Math.abs(mainMatr[size][size])>_eps);
        System.err.println(size);
        mat.setSize(size);
        mat.setMatrix(mainMatr);
        mat.setFreePart(freePart);


        return mat;
    }

    // back turn of matrix calculation, conversion matrix to diagonal form
    // Marix mat - matrix to convert
    // return converted Types.Matrix
    private Matrix backGo(Matrix mat)
    {
        float mainMatr[][] = mat.getMatrix();
        float freePart[][] = mat.getFreePart();
        float coif;
        int FPsize = mat.getSizeOfFreePart();
        int Ysize = mat.getSizeY();
        int size = mat.getSize();


        for(int i = size-1; i >= 0; i--)
        {
            float mainUnit = mainMatr[i][i];

            if(mainUnit != 0) for(int y = i - 1; y >= 0 ; y--)
            {
                coif = mainMatr[y][i] / mainUnit;
                for(int x = i ; x < mat.getSizeX();x++)
                {
                    mainMatr[y][x] -= mainMatr[i][x] * coif;
                    mainMatr[y][x] = checkZero(mainMatr[y][x]);
                }
                for(int x = 0; x < FPsize; x++)
                {
                    freePart[y][x] -= freePart[i ][x] * coif;
                    freePart[y][x] = checkZero(freePart[y][x]);
                }
            }
        }

        mat.setMatrix(mainMatr);
        mat.setFreePart(freePart);

        return mat;
    }

    // move columns that are not part of basis to the free part.
    // Marix mat - matrix to convert
    // return converted Types.Matrix
    private Matrix moveOfFreePart(Matrix mat) {
        if ( mat.getSizeX() > mat.getSize()  ) {
            float[][] freePart = new float[mat.getSize()][mat.getSizeOfFreePart() + mat.getSizeX() - mat.getSize()];

            float[][] fp = mat.getFreePart();
            for (int y = 0; y < mat.getSize(); y++)
                for (int x = 0; x < mat.getSizeOfFreePart(); x++) {
                    freePart[y][x] = fp[y][x];
                }


            int delt = mat.getSize() - mat.getSizeOfFreePart();
            float[][] mainPart = mat.getMatrix();
            for (int y = 0; y < mat.getSize(); y++) {
                for (int x = mat.getSize(); x < mat.getSizeX(); x++) {

                    freePart[y][x - delt] = mainPart[y][x];
                }
            }

            float[][] matrix = new float[mat.getSize()][mat.getSize()];
            for (int y = 0; y < mat.getSize(); y++)
                for (int x = 0; x < mat.getSize(); x++)
                    matrix[y][x] = mainPart[y][x];


            //mat.setSizeX(mat.getSize());
            //mat.setSizeY(mat.getSize());
            //mat.setSizeOfFreePart(freePart.length);
            mat.setMatrix(matrix);
            mat.setFreePart(freePart);

        }
        if(mat.getSizeY() > mat.getSize()) mat = deletZero(mat);
        return mat;
    }


    // turn basis of matrix to E form
    // Marix mat - matrix to convert
    // return converted Types.Matrix
    private Matrix toEform(Matrix mat) {
        float[][] mainPart;
        float[][] freePart;
        mainPart = mat.getMatrix();
        freePart = mat.getFreePart();
        int size = mat.getSize();
        int sizeOfFP = mat.getSizeOfFreePart();
        for(int i = 0 ; i < size ;i++) {
            for(int x = 0; x < sizeOfFP;x++){
                freePart[i][x]/= mainPart[i][i];
            }
            mainPart[i][i] = 1;
        }
        mat.setFreePart(freePart);
        mat.setMatrix(mainPart);
        return mat;
    }

    // check if matrix haave a solution. if there is no solution set matrix parametr havesolution = false;
    // Marix mat - matrix to convert
    // return converted Types.Matrix
    private Matrix checkSolution( Matrix mat)
    {
        float[][] freePart;
        freePart = mat.getFreePart();
        int sizeY = mat.getSizeY();
        int sizeX = mat.getSizeX();
        int size = mat.getSize();

        for(int y = 0 ; y < sizeY;y++) {
            boolean f = true;
            for (int x = 0 ; x < sizeX;x++)
                if (mat.getMatrix()[y][x] != 0 ) f = false;
                if(f && Math.abs(freePart[y][0]) > _eps) { mat.setCanCalculate(false);
            return mat;}
        }
        return  mat;
    }

    private Matrix deletZero(Matrix mat){
        float[][] mainPart = mat.getMatrix();
        float[][] matrix = new float[mat.getSize()][mat.getSize()];
        for (int y = 0; y < mat.getSize(); y++)
            for (int x = 0; x < mat.getSize(); x++)
                matrix[y][x] = mainPart[y][x];


        mat.setMatrix(matrix);
        return mat;
        /*
        int sizeY = mat.getSizeY();
        int sizeX = mat.getSizeX();
        int sizeX = mat.getSizeX();
        float[][] matrix = mat.getMatrix();
        float[][] freepart = mat.getFreePart();
        boolean haveMore = true;
        boolean lineIsZero;
        boolean freepartIsZero;
        for(int y = sizeY; y > 0 && haveMore; y--)
        {
            lineIsZero = true;
            for(int x = 0;x < sizeX ; x++)if(Math.abs(matrix[y][x]) > eps)lineIsZero = false;
            if(lineIsZero)
            {
                freepartIsZero = true;
                for(int x = 0;x < sizeX ; x++)if(Math.abs(matrix[y][x]) > eps)freepartIsZero = false;
            }
        }
        */

    }

}
