import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;




public class InputParserspread {

    public static Map<String,Integer> inputMapspread = new HashMap<String,Integer>();

    public static Map<String,Integer> parseData(String fileName) throws IOException

    {
        String line;


        FileReader fileReader =
                new FileReader(fileName);


        BufferedReader bufferedReader =
                new BufferedReader(fileReader);
        int flag=1;
        String c;

        String src="";
        String t="";
        int des;
        bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(line);
            String destinations="";


                if(st.hasMoreTokens())
                    src = st.nextToken();
                if(st.hasMoreTokens())
                    destinations = st.nextToken();
                int dest=Integer.parseInt(destinations);


                    inputMapspread.put(src, dest);


        }


        bufferedReader.close();
        return inputMapspread;


    }






}



