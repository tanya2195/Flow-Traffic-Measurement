import java.io.*;

import java.util.*;

public class FileMerge {
    String line;
    public static void merge(String fileName) throws IOException {


        FileReader fileReader = new FileReader(fileName);

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        FileReader fr = new FileReader("/home/tanya/Downloads/traffic_spread.txt");


        FileWriter fw=new FileWriter("output.txt");
        BufferedWriter bw=new BufferedWriter(fw);


        BufferedReader br = new BufferedReader(fileReader);

        bufferedReader.readLine();
        String line = "";
        String l = "";
        String src="";
        String dest="";
        String d="";

        while ((line = bufferedReader.readLine()) != null && (l = br.readLine())!=null) {
            StringTokenizer st = new StringTokenizer(line);
            StringTokenizer st1 = new StringTokenizer(l);

            if(st.hasMoreTokens())
                src = st.nextToken();
            if(st.hasMoreTokens())
               st.nextToken();
            if(st.hasMoreTokens())
                dest=st.nextToken();

            if(st1.hasMoreTokens())
                  st1.nextToken();
            if(st1.hasMoreTokens())
              d=  st1.nextToken();

            bw.write(src);
            bw.write("   ");
            bw.write(dest);
            bw.write("  ");
            bw.write(d);
            bw.write("\n");

        }
        bw.flush();
        bw.close();
    }
}
