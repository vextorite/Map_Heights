import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Class containing all methods needed to operate on the 2D array to locate elements classified as basins
 */
public class Serial {
    public float[][] data;
    public float[] neighbors;
    public float[] differences;
    public  ArrayList<String> basins;
    int rows,columns;

    public void makeGrid(String filename) throws FileNotFoundException {
        /**
         * Method that creates a 2D matrix using data parsed from the given file
         */
        Scanner sc = new Scanner(new BufferedReader(new FileReader(filename)));
        String [] dim = sc.nextLine().trim().split(" ");
        this.rows = Integer.parseInt(dim[0]);
        this.columns = Integer.parseInt(dim[1]);
        data = new float[rows][columns];
        String[] line = sc.nextLine().split(" ");
        int counter = 0;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = Float.parseFloat(line[counter]);
                counter++;
            }
        }
    }


    public Boolean isBasin(int row, int column){
        /**
         * Method that checks if the element at the given position is a basin. Returns a boolean.
         */
        neighbors[0] = data[row - 1][column];
        neighbors[1] = data[row][column - 1];
        neighbors[2] = data[row - 1][column - 1];
        neighbors[3] = data[row + 1][column];
        neighbors[4] = data[row][column + 1];
        neighbors[5] = data[row + 1][column + 1];
        neighbors[6] = data[row + 1][column - 1];
        neighbors[7] = data[row - 1][column + 1];
        float hole = (float) (data[row][column] + 0.01);
        return (neighbors[0] >= hole) && (neighbors[1] >= hole) && (neighbors[2] >= hole) &&
                (neighbors[3] >= hole) && (neighbors[4] >= hole) && (neighbors[5] >= hole) &&
                (neighbors[6] >= hole) && (neighbors[7] >= hole);
    }

    public void findBasins(){
        /**
         * Method that traverses through the 2D array and adds the elements classified as basins to an ArrayList
         */
        basins = new ArrayList<>(1);
        neighbors = new float[8];
        differences = new float[8];

        for (int i = 1; i < data.length-1; i++) {

            for (int j = 1; j < data[i].length - 1; j++) {

                neighbors[0] = data[i - 1][j];
                neighbors[1] = data[i][j - 1];
                neighbors[2] = data[i - 1][j - 1];
                neighbors[3] = data[i + 1][j];
                neighbors[4] = data[i][j + 1];
                neighbors[5] = data[i + 1][j + 1];
                neighbors[6] = data[i + 1][j - 1];
                neighbors[7] = data[i - 1][j + 1];
                float hole = (float) (data[i][j] + 0.01);
                if ((neighbors[0] >= hole) && (neighbors[1] >= hole) && (neighbors[2] >= hole) &&
                        (neighbors[3] >= hole) && (neighbors[4] >= hole) && (neighbors[5] >= hole) &&
                        (neighbors[6] >= hole) && (neighbors[7] >= hole)) {
                    basins.add(i + " " + j);
                }
            }
        }

    }
}
