import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class ParallelMatrix {
    /**
     * Class containing methods that execute the task of searching a grid of values for basins.
     * @author Sanele Hlongwane
     * @version 1.0
     * @since   2020-08-23
     */

    public static float[][] matrix;
    public static boolean[][] booleanMatrix;
    public static float[] oneDMatrix;
    public static int rows,columns;
    public static ArrayList<String> basins;
    static long startTime = 0;

    private static void tick(){
        startTime=System.currentTimeMillis();
    }

    public static float tock(){
        return (System.currentTimeMillis()-startTime)/1000.0f;
    }

    public static void main(String[] args) throws IOException {
        /**
         * main method that creates threads and invokes the Fork-Join framework to perform the search using parallelism
         */
        makeGrid(args[0]);
        makeOneDMatrix(matrix);
        tick();
        Parallel mainTask = new Parallel(matrix, booleanMatrix, oneDMatrix,0, oneDMatrix.length,columns,rows);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(mainTask);
        float time = tock();

        findBasins();
        System.out.println("Time taken: "+ tock());
        write(args[1], basins);
        System.out.println("Processors used: "+pool.getPoolSize());
    }

    public static void makeGrid(String fileName) throws FileNotFoundException{
        /**
         * method that creates 2D matrix that houses values parsed from a file. The sixe of the matrix is given in the
         * first line of the file and the elements of the matrix in the second line.
         */
        Scanner sc = new Scanner(new BufferedReader(new FileReader(fileName)));
        String [] dim = sc.nextLine().trim().split(" ");
        rows = Integer.parseInt(dim[0]);
        columns = Integer.parseInt(dim[1]);
        matrix = new float[rows][columns];
        booleanMatrix = new boolean[rows][columns];
        String[] line = sc.nextLine().split(" ");
        int counter=0;
        for (int i = 0; i < matrix.length ; i++) {
            for (int j = 0; j < matrix[i].length ; j++) {
                matrix[i][j] = Float.parseFloat(line[counter]);
                booleanMatrix[i][j] = false;
                counter++;
            }
        }
    }

    public static void makeOneDMatrix(float[][] matrix){
        /**
         * Method that creates a 1D matrix using the 2D matrix created from the makeGrid() method.
         */

        float[] out = new float[matrix.length * matrix[0].length];
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[i].length; j++){
                out[i + (j * matrix.length)] = matrix[i][j];
            }
            oneDMatrix = out;
        }
    }
    public static void write (String filename, ArrayList<String>x) throws IOException {
        BufferedWriter outputWriter;
        outputWriter = new BufferedWriter(new FileWriter(filename));
        outputWriter.write(x.size()+"\n");
        for (String s : x) {
            outputWriter.write(s + "\n");
        }
        outputWriter.flush();
        outputWriter.close();
    }
    public static void findBasins(){
        /**
         * Method that finds basins from a 2D boolean matrix and adds their indexes to an Arraylist before printing out
         * the the position of the basins.
         */

        basins = new ArrayList<>();
        int basinCounter=0;
        for (int i = 0; i < booleanMatrix.length ; i++) {
            for (int j = 0; j < booleanMatrix[i].length ; j++) {
                if (booleanMatrix[i][j]){
                    basinCounter++;
                    basins.add(i+" "+j);
                }
            }
        }

    }


}
