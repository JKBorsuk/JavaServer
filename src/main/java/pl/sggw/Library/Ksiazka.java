package pl.sggw.Library;

import java.util.Objects;

public class Ksiazka {

    private long id;
    private String N,A;

    public Ksiazka(long id, String N, String A)
    {
        this.id = id;
        this.N = N;
        this.A = A;
    }
    public String GetName(){ return N; }
    public String GetAuthor(){ return A; }
    public void SetName(String Name) { N = Name; }
    public void SetAuthor(String Author) { A = Author; }

    @Override
    public int hashCode() {
        return Objects.hash(id, N, A);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\t\t{\n");
        sb.append("\t\t\t\"id\":").append(" " + id + ",\n");
        sb.append("\t\t\t\"nazwa\":").append(String.format(" \"%s\",\n",N));
        sb.append("\t\t\t\"autor\":").append(String.format(" \"%s\"\n",A));
        sb.append("\t\t}");
        return sb.toString();
    }
}
