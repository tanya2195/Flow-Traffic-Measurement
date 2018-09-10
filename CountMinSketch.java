import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;

public class CountMinSketch {

    public final static int w = 1000;
    public final static int d = 3;

    public static int[][] multiset;


    public static void set(int keyhash1, int keyhash2) {


        for (int i = 1; i <= d; i++) {
            int combinedHash = keyhash1 + (i * keyhash2);

            if (combinedHash < 0) {
                combinedHash = ~combinedHash;
            }
            int pos = combinedHash % w;

            multiset[i - 1][pos] += 1;
        }
    }

    public static void setString(String val1, String val2) {
        CRC32 crc = new CRC32();
        long m_crc32;

        crc.update(val1.getBytes());
        m_crc32 = (int)(crc.getValue());
        if (m_crc32<0) m_crc32=0;

        int h1 = (int) m_crc32;
        crc.update(val2.getBytes());
        m_crc32=(int)(crc.getValue());
        if(m_crc32<0) m_crc32=0;



        //int h1=val1.hashCode()
        //int h2=val2.hashcode()
        int h2 = (int) m_crc32;

        set(h1, h2);
    }

    public static int getEstimatedCount(String val1, String val2) {
        CRC32 crc = new CRC32();
        long m_crc32;

        crc.update(val1.getBytes());
        m_crc32 = (int)(crc.getValue());
        if (m_crc32<0) m_crc32=0;

        int hash1 = (int) m_crc32;
        crc.update(val2.getBytes());
        m_crc32=(int)(crc.getValue());
        if(m_crc32<0) m_crc32=0;


        int hash2 = (int) m_crc32;

        int min = Integer.MAX_VALUE;
        for (int i = 1; i <= d; i++) {
            int combinedHash = hash1 + (i * hash2);

            if (combinedHash < 0) {
                combinedHash = ~combinedHash;
            }
            int pos = combinedHash % w;
            min = Math.min(min, multiset[i - 1][pos]);
        }

        return min;
    }


    public static void main(String[] args) throws IOException {


        multiset = new int[d][w];
        int n_estimate = 0;

        String File = "/home/tanya/traffic.txt";

        HashMap<Integer, Integer> resultGraph = new HashMap<Integer, Integer>(); //for graph
        Map<String, List<String>> inputMap = new HashMap<String, List<String>>();
        try {
            inputMap = InputParser.parseInputData(File);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FileWriter fw = new FileWriter("out4.txt");
        BufferedWriter bw = new BufferedWriter(fw);

        for (Map.Entry<String, List<String>> entry : inputMap.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            int size = value.size();

            Iterator iterator = value.iterator();

            while (iterator.hasNext()) {
                String dest = (String)iterator.next();
                setString(key,dest);
             n_estimate=   getEstimatedCount(key,dest);
             String g=Integer.toString(n_estimate);
             bw.write(key+"   ");
             bw.write(dest+"   ");
bw.write(g+"\n");

            }


        }
    }
}