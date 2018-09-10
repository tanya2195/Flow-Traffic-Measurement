

import java.io.BufferedWriter;
import java.io.FileNotFoundException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.zip.CRC32;




public class VirtualBitmap {

    public final static int MEGABITSETSIZE = 1024000;
    public final static int PERFLOW_BITSIZE = 500;

    public static BitSet mega_bitset = new BitSet(MEGABITSETSIZE);



    public static long[] random_array = new long[PERFLOW_BITSIZE];



    public static void main(String[] args) throws IOException {
        Random rand2 = new Random();
        for(int i = 0;i<PERFLOW_BITSIZE;i++)

        {
            random_array[i] = rand2.nextInt(Integer.MAX_VALUE) + 1;
        }



        String File = "/home/tanya/traffic.txt";
        String file="/home/tanya/Downloads/traffic_spread.txt";


        HashMap<Integer, Integer> resultGraph = new HashMap<Integer, Integer>(); //for graph
        Map<String, List<String>> inputMap = new HashMap<String, List<String>>();

        Map<String, Integer> inputMapS = new HashMap<String,Integer>();
        try {
            inputMap = InputParser.parseInputData(File);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            inputMap = InputParser.parseInputData(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }





        FileWriter fw=new FileWriter("out1.txt");
        BufferedWriter bw=new BufferedWriter(fw);




        for (Map.Entry<String, List<String>> entry : inputMap.entrySet()  ) {




                List<String> value = entry.getValue();


                String key = entry.getKey();
                String s = "";
                for (int i = 0; i < key.length(); i++) {
                    if (key.charAt(i) == '.') {
                        continue;
                    } else
                        s = s + key.charAt(i);

                }
                long fid = Long.parseLong(s);

                BitSet perflow_bitset = new BitSet(PERFLOW_BITSIZE);


                Iterator iterator = value.iterator();
                int size = value.size();
                int n = size; // for graph
                ArrayList perflow_indices = new ArrayList(size);

                while (iterator.hasNext()) {

                    String destination = (String) iterator.next();

                    int d = destination.hashCode();

                    int perflow_index = d % PERFLOW_BITSIZE;


                    if (perflow_index > 0) {

                        perflow_bitset.set(perflow_index);

                        int Bindex = (int) (fid ^ random_array[perflow_index]) % MEGABITSETSIZE;


                        if (Bindex > 0)
                            mega_bitset.set(Bindex);
                    }
                }

                int zero = 0;


                for (int i = 0; i < PERFLOW_BITSIZE; i++) {
                    if (!perflow_bitset.get(i))
                        zero++;


                }
                System.out.println(zero);


                double Vm = (double) (zero) / PERFLOW_BITSIZE;

                double Vn = (double) (MEGABITSETSIZE - mega_bitset.cardinality()) / MEGABITSETSIZE;
                int estimate = (int) (Math.round((PERFLOW_BITSIZE * Math.log(Vn)) - (PERFLOW_BITSIZE * Math.log(Vm))));

                bw.write(key);
                String g = Integer.toString(estimate);
                bw.write("   ");


                bw.write(g + "\n");

                perflow_bitset.clear();



        }

    }

}