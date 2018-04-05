import MatirxMath.DiogonalMatrixSolving;
import Types.Matrix;

/**
 * Created by Михаил on 05.05.2017.
 */
public class Test {
    public static void main(String[] args) {

        float[][] mat = {
                {3,	1,	2,	5,	6},
                {2,	5,	3,	3,	4},
                {5,	6,	5,	8,	10},
                {1,	8,	4,	3,	5},
                {8,	5,	7,	2,	5},
                {8,	5,	7,	2,	6}



        };

        float[][] freeP = {
                {1},
                {2},
                {3},
                {7},
                {9},
                {9.01f}
        };
        Matrix matrix = new Matrix(mat, freeP);
        float eps = 0.00001f;
        DiogonalMatrixSolving mm = new DiogonalMatrixSolving(matrix, eps);
        mm.solve();
    }

}