import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.zip.CRC32;
import java.util.Random;


public class BloomFilter{
    public static final double DEFAULT_FPP = 0.05;

    public BitSet bitSet;
    public int k;
    public int m;
    public static double fpp;
    public long n;



    public BloomFilter(long maxNumEntries) {
        this(maxNumEntries, DEFAULT_FPP);
    }
    public BloomFilter(long maxNumEntries, double fpp) {
        assert maxNumEntries > 0 : "maxNumEntries should be > 0";
        assert fpp > 0.0 && fpp < 1.0 : "False positive percentage should be > 0.0 & < 1.0";
        this.fpp = fpp;
        this.n = maxNumEntries;
        this.m = optimalNumOfBits(maxNumEntries, fpp);

        this.k = optimalNumOfHashFunctions(maxNumEntries, m);

        this.bitSet = new BitSet(m);
    }

    public static double getfpr(long n, long m,int k) {

        return (Math.pow(1 - Math.exp(-k * (n / (double) m)), k));
    }

    public static int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
    }

    static int optimalNumOfBits(long n, double p) {
        if (p == 0) {
            p = Double.MIN_VALUE;
        }
        return (int) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
    }







    public static void main(String[] args) throws IOException {


        String File = "/home/tanya/traffic.txt";

        HashMap<Integer, Integer> resultGraph = new HashMap<Integer, Integer>(); //for graph
        Map<String, List<String>> inputMap = new HashMap<String, List<String>>();
        try {
            inputMap = InputParser.parseInputData(File);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BloomFilter bf = new BloomFilter(2529020);




        Set<String> set = new HashSet<String>();
        HashSet<String> s=new HashSet<String>();
        int flag=1;

        for (Map.Entry<String, List<String>> entry : inputMap.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            int size = value.size();
            while(size>0){
               String a= value.get(size-1);
System.out.println(key+"  "+a);

                set.add(key + "~" + a);

                if(flag%100==0)
                {
                    s.add(key+"~"+a);
                    flag++;
                }
                size--;
                flag++;
            }

        }
        for (String member : set) {
            bf.addString(member);

        }








        long a = 0;
        long b=0;
        int fp=0;
        int tn = 0;
        for (int i = 0; i < 30000; i++) {


            Random rand = new Random(123);
            int memberLen = rand.nextInt(200);


            String randomString=Integer.toString(memberLen);
            boolean result=bf.testString(randomString);
            if (!set.contains(randomString)) {
                if (result == true) {

                    fp++;

                } else {

                    tn++;
                }
            }


        }


int fn=0;

        for (String member : s) {
           boolean result= bf.testString(member);
            if(result==false)
                fn++;

        }


        System.out.println("**Genrate random seeds for hash function**");
        System.out.println("**Genrate random seeds for hash function**");
        System.out.println("Number of elements encoded: " + 2529020);
        System.out.println("Number of elements tested: " + 30000);
        System.out.println("Optimal Bloom FIlter Size: " + bf.m);
        System.out.println("Number of hash functions: k= " + bf.k);
        System.out.println("Theoretical false positive ratio: " + DEFAULT_FPP);
        System.out.println("Actual false positive ratio:" + fp/(fp+tn));
        System.out.println("The actual false negative probability is "+fn);










    }


        public void addString(String val1) {



            long hash64 = Murmur3.hash64(val1.getBytes());
            int hash1 = (int) hash64;
            int hash2 = (int) (hash64 >>> 32);



            for (int i = 1; i <= k; i++) {
                int combinedHash = hash1+(i*hash2);
                // hashcode should be positive, flip all the bits if it's negative
                if (combinedHash < 0) {
                    combinedHash = ~combinedHash;
                }
                int pos = combinedHash % m;
                bitSet.set(pos);

            }
        }

        public boolean testString(String val1) {


            long hash64 = Murmur3.hash64(val1.getBytes());
            int hash1 = (int) hash64;
            int hash2 = (int) (hash64 >>> 32);


            for (int i = 1; i <= k; i++) {
                int combinedHash = hash1 + (i * hash2);

                if (combinedHash < 0) {
                    combinedHash = ~combinedHash;
                }
                int pos = combinedHash % m;
                if (!bitSet.get(pos)) {
                    return false;
                }
            }
            return true;
        }



    }
