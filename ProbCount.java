
import java.io.BufferedWriter;
import java.io.FileNotFoundException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;






public class ProbCount {

    final static int ORIG_SIZE=1500;
    int [] bit_array= new int [ORIG_SIZE];


    public static void main(String[] args) throws IOException {

        int n_estimate = 0;
        double Vs = 0;
        ProbCount pc = new ProbCount();
        String File = "/home/tanya/traffic.txt";

        HashMap<Integer,Integer> resultGraph = new HashMap<Integer,Integer>(); //for graph
        Map<String,List<String>> inputMap = new HashMap<String,List<String>>();
        try {
            inputMap = InputParser.parseInputData(File);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FileWriter fw=new FileWriter("out.txt");
        BufferedWriter bw=new BufferedWriter(fw);

        for (Map.Entry<String, List<String>> entry : inputMap.entrySet()) {
            String key=entry.getKey();
            List<String> value = entry.getValue();
            int size = value.size();
            int n = size; // for graph
            while(size>0){
                pc.set_bitarray(value.get(size-1));
                size--;
            }
            Vs =(double) pc.count_zeroes() / ORIG_SIZE;
            n_estimate = (int) (-1 * ORIG_SIZE * java.lang.Math.log(Vs));
            bw.write(key);
            String g=Integer.toString(n_estimate);
bw.write("   ");
            bw.write(g+"\n");




            int n_esti = n_estimate;  // for graph
            pc.reset_bitarray();
            resultGraph.put(n, n_esti);
        }
        for(Map.Entry<Integer, Integer> entry : resultGraph.entrySet()){  // for graph
            int key = entry.getKey();
            int value = entry.getValue();
            System.out.println("n: "+key+" n^: "+value);
        }

    }



    void reset_bitarray()
    {
        for(int i = 0; i < ORIG_SIZE; i++) {
            bit_array[i] = 0;
        }
    }
    void set_bitarray(String value)
    {

        CRC32 crc = new CRC32();
        long m_crc32;
        crc.update(value.getBytes());
        m_crc32 = (int)(crc.getValue() % ORIG_SIZE);
        if (m_crc32<0) m_crc32=0;
        bit_array[(int)m_crc32] = 1;
    }

    int count_zeroes()
    {
        int count = 0;
        for(int i = 0; i < ORIG_SIZE; i++) {
            if(bit_array[i] == 0) {
                count++;
            }
        }
        return count;
    }




}
