package pl.sggw.Rest;

import pl.sggw.Library.Biblioteka;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Json {
    public static void serialize(Biblioteka biblioteka) throws IOException {
        String json = biblioteka.toString();
        BufferedWriter wr = new BufferedWriter(new FileWriter("json.json"));
        wr.write(json);
        wr.close();
    }
    public static Biblioteka deserialize() throws IOException {
        Biblioteka baza = new Biblioteka();
        String json = "";
        String CurrentLine = "";
        boolean opener = true;

        long id = 1;
        String title = "";
        String author = "";

        BufferedReader reader = new BufferedReader(new FileReader("json.json"));
        while((CurrentLine = reader.readLine()) != null)
        {
            if(CurrentLine.contains("{"))
            {
                if(opener) { opener = false; continue; }
                else
                {
                    while(!(CurrentLine = reader.readLine()).contains("}")){
                        if(CurrentLine.charAt(4) == 'n') title = CurrentLine.substring(13,CurrentLine.length()-2);
                        else if(CurrentLine.charAt(4) == 'a') author = CurrentLine.substring(13,CurrentLine.length()-1);
                    }
                    baza.DodajDane(id++,title,author);
                }
            }
        }
        return baza;
    }
}
