



import java.io.*;

import java.util.*;

public class InputParser {

   public static Map<String,List<String>> inputMap = new HashMap<String,List<String>>();

    public static Map<String,List<String>> parseInputData(String fileName) throws IOException

    {
        String line;


        FileReader fileReader =
                new FileReader(fileName);


        BufferedReader bufferedReader =
                new BufferedReader(fileReader);
        int flag=1;
        String c;
        String dest="";
        String src="";
        String t="";
bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(line);
            List<String> destinations;
            while (st.hasMoreTokens() ) {

                if(st.hasMoreTokens())
                src = st.nextToken();
               if(st.hasMoreTokens())
                 dest = st.nextToken();

                if (inputMap.containsKey(src)) {

                    destinations = inputMap.get(src);
                    destinations.add(dest);
                    inputMap.put(src, destinations);
                } else {

                    destinations = new ArrayList<String>();
                    destinations.add(dest);
                    inputMap.put(src, destinations);
                }

                if(st.hasMoreTokens())
                     t=st.nextToken();
            }
        }


        bufferedReader.close();
return inputMap;


}






    }

































