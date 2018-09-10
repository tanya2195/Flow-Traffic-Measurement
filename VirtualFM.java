
import java.io.*;
import java.math.BigInteger;
import java.util.*;


public class VirtualFM {

    public static void main(String args[]) throws FileNotFoundException, IOException {

        final int m = 100000;
        final int s = 64;
        int mainarray[] = new int[64];

        String file = "/home/tanya/traffic.txt";

        int[] random_array = new int[64];
        FileWriter fw=new FileWriter("out99.txt");
        BufferedWriter bw=new BufferedWriter(fw);


        Random rand2 = new Random();
        for (int i = 0; i < 64; i++)

        {
            random_array[i] = rand2.nextInt(Integer.MAX_VALUE) + 1;
        }


        Map<String, List<String>> inputMap = new HashMap<String, List<String>>();

        Map<String, Integer> inputMapS = new HashMap<String, Integer>();
        try {
            inputMap = InputParser.parseInputData(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        for (Map.Entry<String, List<String>> entry : inputMap.entrySet()) {
            List<String> value = entry.getValue();
            String key = entry.getKey();

            Iterator iterator = value.iterator();
            int size = value.size();
            int d = key.hashCode();
            while (iterator.hasNext()) {

                String destination = (String) iterator.next();


                int dest = destination.hashCode();
                int index = (int) (d ^ random_array[dest % 64]) % m;
                int k = geometric(dest);
                if(index>0 && index <m)
                mainarray[index] = mainarray[index] | (1 << k);


            }


            int arr[] = new int[64];
            int sum=0;
            for (int i = 0; i < 64; i++) {
                if((d^random_array[i])%m>0)
                arr[i] = mainarray[(d ^ random_array[i]) % m];
                String r=Integer.toString(arr[i]);
               int lz= CountLeadingZeros(r);
               sum=sum+lz;
            }

            double phi = (1 / 0.77351) * 64;

            double n = Math.pow(2, sum/64);

bw.write(key);
bw.write("  ");
bw.write(Double.toString(n));


        }

    }



    public static int CountLeadingZeros(String s) {
        int count = 0;
        int strlen = s.length();
        if (strlen > 5) {
            String newStr = new StringBuilder(s.substring(0, strlen - 5)).reverse().toString();
            for (int i = 0; i < newStr.length(); i++) {
                if (newStr.charAt(i) == '1') {
                    count++;
                } else
                    break;
            }
            count++;
        }
        return count;
    }

    public static int geometric(int dest) {

        int zeroes = Integer.numberOfLeadingZeros(dest);

return  zeroes;
    }



}