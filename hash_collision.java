import java.io.*;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class value{


    Set<String> dests;
    private String source;
    HashMap<String,value> hmcol;
    public value(String source,Set<String> dests) {

        this.source = source;
        this.dests=dests;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }
}


public class TwoLevelHash {

    public static void main(String[] args) throws IOException {
        Map<String,Integer> srcCount = new HashMap<>();
        int n_estimate = 0;
        String File = "/home/tanya/traffic.txt";

        FileReader fileReader =
                new FileReader(File);


        BufferedReader bufferedReader =
                new BufferedReader(fileReader);


        HashMap<String, value> hm = new HashMap<>();
        Map<String,List<String>> inputMap = new HashMap<String,List<String>>();
        try {
            inputMap = InputParser.parseInputData(File);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }








        for (Map.Entry<String, List<String>> enty : inputMap.entrySet()) {
            String src=enty.getKey();
            List<String> value = enty.getValue();
            int size = value.size();

            while(size>0) {

                String dest = value.get(size - 1);

                boolean flag = false;
                value destinations;

                if (src == null || dest == null) {
                    continue;
                }

                for (String entry : hm.keySet()) {

                    if (entry.hashCode() == src.hashCode()) {
                        flag = true;
                        if (entry.equals(src)) {
                            Set<String> d = hm.get(src).dests;
                            d.add(dest);

                        } else {
                            System.out.println("collision");
                            destinations = hm.get(entry);
                            if (destinations.hmcol == null) {
                                destinations.hmcol = new HashMap<>();
                                destinations.dests = new HashSet<>();
                            }
                            if (!destinations.hmcol.containsKey(src)) {
                                destinations.hmcol.put(src, new value(src, new HashSet()));
                            }
                            destinations.dests.add(dest);
                        }

                    }


                }
                if (!flag) {
                    Set<String> d = new HashSet<String>();
                    d.add(dest);
                    hm.put(src, new value(src, d));
                }
                size--;
            }
        }
        for(String entry : hm.keySet()){
            value mapValue = hm.get(entry);
            if(mapValue.hmcol!=null){
                for(String collision : mapValue.hmcol.keySet()){
                    srcCount.put(collision,mapValue.hmcol.get(collision).dests.size());
                }
            }
            srcCount.put(entry, mapValue.dests.size());
        }
        FileWriter fw = new FileWriter("out10.txt");
        BufferedWriter bw = new BufferedWriter(fw);
//        bw.write("hello");
        bw.write("SrcIp : Count" );
        bw.newLine();
        for(String unique : srcCount.keySet()){
            bw.write(unique);
            bw.write(" ");
            bw.write(Integer.toString(srcCount.get(unique)));
            bw.newLine();

            System.out.println("SrcIp: " + unique + " Count = " + srcCount.get(unique));


        }
        bw.flush();
        bw.close();
    }
}
