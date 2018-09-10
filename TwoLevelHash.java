import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TwoLevelHash
{

    public static void main(String[] args) throws IOException {

        int n_estimate = 0;


        String File = "/home/tanya/traffic.txt";

        //  HashMap<Integer,Integer> resultGraph = new HashMap<Integer,Integer>(); //for graph
        Map<String, List<String>> inputMap = new HashMap<String, List<String>>();
        try {
            inputMap = InputParser.parseInputData(File);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FileWriter fw = new FileWriter("out3.txt");

        BufferedWriter bw=new BufferedWriter(fw);


        HashMap<String, HashSet<String>> Twolevelhash = new HashMap<String, HashSet<String>>();

        for (Map.Entry<String, List<String>> entry : inputMap.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();

            int n=value.size();

            for(int i=0;i<n;i++){


                String dest =value.get(i);

                if (Twolevelhash.containsKey(key)) {

                    Twolevelhash.get(key).add(dest);
                } else {
                    HashSet<String> list = new HashSet<String>();
                    list.add(dest);
                    Twolevelhash.put(key, list);

                }

            }
            int size = Twolevelhash.get(key).size();
            String s=Integer.toString(size);
            bw.write(key);
            bw.write("   ");

            bw.write(s+"\n");


        }


    }}
