import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SerialMatrix {
    static long startTime = 0;

    private static void tick(){
        startTime = System.currentTimeMillis();
    }
    private static float tock(){
        return (System.currentTimeMillis() - startTime) / 1000.000f;
    }

    public static void main(String[] args) throws Exception {
        Serial matrix = new Serial();
        matrix.makeGrid(args[0]);
        tick();
        matrix.findBasins();
        System.out.println("Time Taken: "+tock());
        write(args[1],matrix.basins);
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
}
