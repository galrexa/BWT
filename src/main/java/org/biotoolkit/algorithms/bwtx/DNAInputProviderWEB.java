package org.biotoolkit.algorithms.bwtx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Sequence from a website taking a scanner input of an accession number
 */
public class DNAInputProviderWEB{
    static public String DNAfromWeb(String urlString){
        String result="";
        // Access the page
        try{
            // Create a URL for the desired page
            URL url=new URL(urlString);
            // Read all the text returned by the server
            BufferedReader in=new BufferedReader(new InputStreamReader(url.openStream()));
            String str;
            while ((str=in.readLine())!=null){
                // str is one line of text; readLine() strips the newline
                // character(s)
                result+=str;
            }
            in.close();
        }catch (MalformedURLException e){
        }catch (IOException e){
        }
        return result;
    }
}
/**
 * Then use the java.util.regex to match the data that you want to get from the
 * page. and parse it into your labels. Don't forget to put all this in a thread
 * with a while(true) loop, and a sleep(some_time) to have second by second
 * informations.
 */
