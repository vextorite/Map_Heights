import java.util.concurrent.RecursiveAction;

public class Parallel extends RecursiveAction{
    /**
     * Class containing the actual implementation of the Fork-Join framework needed to parallelize the task of finding basins.
     * @author Sanele Hlongwane
     * @version 1.0
     * @since   2020-08-23
     */
    float[][] matrix;
    float[] oneDMatrix;
    boolean[][] booleanMatrix;
    int SEQUENTIAL_CUTOFF = 9000;
    int lo,hi;
    int rows,columns;
    int x,y=0;

    public Parallel(float[][] matrix, boolean[][] booleanMatrix, float [] oneDMatrix, int lo, int hi, int columns, int rows ){
        /**
         * Constructor initializing the variables in this instance of the object.
         */
        this.matrix = matrix;
        this.booleanMatrix = booleanMatrix;
        this.oneDMatrix = oneDMatrix;
        this.lo=lo;
        this.hi=hi;
        this.columns=columns;
        this.rows=rows;
    }

    public void posTransform(int pos){
        /**
         * Method that transforms the positions of an element in the 1D matrix to the 2D matrix.
         */
        x =(pos%(rows*columns))/columns;
        y = pos % (columns);
    }


    @Override
    protected void compute() {
        /**
         * Overriding the compute method inherited from RecursiveAction. The base case loops throughout the array left
         */
        if (hi-lo < SEQUENTIAL_CUTOFF){
            for (int i = lo; i < hi ; i++){
                posTransform(i);
                try{
                    if ((matrix[x-1][y-1]> matrix[x][y]+0.01) && (matrix[x-1][y]> matrix[x][y]+0.01) &&
                            (matrix[x-1][y+1]> matrix[x][y]+0.01) && (matrix[x][y-1]> matrix[x][y]+0.01) &&
                            (matrix[x][y+1]> matrix[x][y]+0.01) && (matrix[x+1][y-1]> matrix[x][y]+0.01) &&
                            (matrix[x+1][y]> matrix[x][y]+0.01) && (matrix[x+1][y+1]> matrix[x][y]+0.01)){
                        booleanMatrix[x][y]=true;
                    }
                }
                catch(ArrayIndexOutOfBoundsException ignored){
                }
            }

        }else{
            int middle = (lo+hi)/2;
            Parallel subtask1 = new Parallel(matrix, booleanMatrix, oneDMatrix, lo, middle, columns, rows);
            Parallel subtask2 = new Parallel(matrix, booleanMatrix, oneDMatrix, middle, hi, columns, rows);
            invokeAll(subtask1,subtask2);
        }

    }
}
