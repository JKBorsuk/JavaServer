package pl.sggw.Library;
import java.util.ArrayList;

public class Biblioteka {
    public ArrayList<Ksiazka> biblioteka = new ArrayList<>();
    String NazwaPliku;

    public Biblioteka()
    {
        biblioteka = new ArrayList<Ksiazka>();
    }

    public void DodajDane(long I, String N, String A)
    {
        biblioteka.add(new Ksiazka(I,N,A));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{\n\t\"Ksiazki\": [\n");
        for(int i = 0; i < biblioteka.size(); i++)
        {
            sb.append(biblioteka.get(i));
            if(i < biblioteka.size() - 1) sb.append(",\n");
            else if(i == biblioteka.size() - 1) sb.append('\n');
        }
        sb.append("\t]\n}");
        return sb.toString();
    }
}
