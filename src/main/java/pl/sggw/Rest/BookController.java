package pl.sggw.Rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import pl.sggw.Library.Biblioteka;

@RestController
public class BookController {
    private final AtomicLong counter = new AtomicLong();
    private String kod = "";
    private Biblioteka baza;
    private boolean isloaded = false;


    @GetMapping("/books.html")
    public String books() throws IOException {
        counter.set(0);
        if(!isloaded) {
            baza = Json.deserialize();
            isloaded = true;
        }

        if(baza.biblioteka.isEmpty())
        {
            kod = "<html>\n" +
                    "  <head>\n" +
                    "    <script type=\"text/javascript\">\n" +
                    "      alert(\"Brak książek\");\n" +
                    "    </script>\n" +
                    "  </head>\n" +
                    "  <body>\n" +
                    "  </body>\n" +
                    "</html>";
            return kod;
        }
        else
        {
            kod = "<html>\n" +
                    "<head>\n" +
                    "<meta charset=\"UTF-8\">\n" +
                    "<title>RestService</title>\n" +
                    "<meta name=\"keywords\" content=\"HTML, CSS, JavaScript\">\n" +
                    "<style>\n" +
                    "table,\n" +
                    "table td {\n" +
                    "border: 1px solid #cccccc;\n" +
                    "}\n" +
                    "td {\n" +
                    "height: 60px;\n" +
                    "width: 160px;\n" +
                    "text-align: center;\n" +
                    "vertical-align: middle;\n" +
                    "}\n" +
                    "</style>\n" +
                    "</head>\n" +
                    "<body>";
            kod += "<table border='0' width=100%>";
            for(int i = 0; i < baza.biblioteka.size(); i++) {
                kod += "<tr><td>id: " + counter.incrementAndGet() + "</td>";
                kod += "<td>nazwa: " + baza.biblioteka.get(i).GetName() + "</td>";
                kod += "<td>autor: " + baza.biblioteka.get(i).GetAuthor() + "</td></tr>";
            }
            kod += "</table>";
            kod += "</body>";
            return kod;
        }
    }
    @GetMapping("/manage.html")
    public String manage()
    {
        kod = "<html>\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<title>RestService</title>\n" +
                "<meta name=\"keywords\" content=\"HTML, CSS, JavaScript\">\n" +
                "</head>\n" +
                "<body>";
        kod += "<form action='/clearBooksAction' method='post'>";
        kod += "<input type='submit' value='Submit'>";
        return kod;
    }

    @PostMapping("clearBooksAction")
    public String clearbooksaction() throws IOException {
        baza = new Biblioteka();
        kod = "<html><head>";
        kod += "<script type='text/javascript'>";
        kod += "location.replace('http://localhost:8080/books.html')";
        kod += "</script>";
        kod += "</head></html>";

        Json.serialize(baza);

        return kod;
    }

    @GetMapping("addBook.html")
    public String addbook() throws IOException {
        kod = "<html>\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<title>RestService</title>\n" +
                "<meta name=\"keywords\" content=\"HTML, CSS, JavaScript\">\n" +
                "<style>\n" +
                ".mdiv {\n" +
                "border: 2px outset black;\n" +
                "text-align: center;\n" +
                "height: 247px;\n" +
                "width: 200px;\n" +
                "margin: auto;\n" +
                "}\n" +
                ".minidiv {\n" +
                "border: 1px solid black;\n" +
                "text-align: center;\n" +
                "height: 60px;\n" +
                "width: 200px;\n" +
                "margin: auto;\n" +
                "}\n" +
                "#pop\n" +
                "{\n" +
                "width: 60px;\n" +
                "height: 30px;\n" +
                "background-color: white;\n" +
                "border: 2px solid #e7e7e7\n" +
                "}\n" +
                "#pop:hover\n"+
                "{\n"+
                "background-color: #e7e7e7;\n"+
                "}\n"+
                "</style>\n" +
                "</head>\n" +
                "<body>";
        kod += "<form action='/updateBooksAction' method='post'><div class='mdiv'>";

        kod += "<div class = 'minidiv'>";
        kod += "<div>Title: </div>";
        kod += "<div>" + "<input type='text' name='title'/>" + "</div>";
        kod += "</div>";

        kod += "<div class = 'minidiv'>";
        kod += "<div>AuthorName: </div>";
        kod += "<div>" + "<input type='text' name='authorName'/>" + "</div>";
        kod += "</div>";

        kod += "<div class = 'minidiv'>";
        kod += "<div>AuthorSurname: </div>";
        kod += "<div>" + "<input type='text' name='authorSurname'/>" + "</div>";
        kod += "</div>";

        kod += "<div class = 'minidiv'>";
        kod += "<div align='center'>" + "<input type='submit' value='Prześlij' id='pop'>" + "</div>";
        kod += "</div>";

        kod += "</div></form></body></html>";

        return kod;
    }

    @PostMapping("updateBooksAction")
    public String updatebooksaction(@RequestParam(value = "id", defaultValue = "-1") String id,
                                    @RequestParam(value = "title", defaultValue = "") String title,
                                    @RequestParam(value = "authorName", defaultValue = "") String name,
                                    @RequestParam(value = "authorSurname", defaultValue = "") String surname) throws IOException {
        if(id.equals("-1")) baza.DodajDane(counter.incrementAndGet(), title, name + " " + surname);
        else
        {
            try
            {
                Integer.parseInt(id);
            }
            catch(UnsupportedOperationException exception)
            {
                exception.printStackTrace();
            }
            baza.biblioteka.get(Integer.parseInt(id) - 1).SetName(title);
            baza.biblioteka.get(Integer.parseInt(id) - 1).SetAuthor(name + " " + surname);
        }
        kod = "<html><head>";
        kod += "<script type='text/javascript'>";
        kod += "location.replace('http://localhost:8080/books.html')";
        kod += "</script>";
        kod += "</head></html>";

        Json.serialize(baza);

        return kod;
    }

    @GetMapping("updateBook.html")
    public String updatebooksaction(@RequestParam(value = "id", defaultValue = "") String id) {

        kod = "<html>\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<title>RestService</title>\n" +
                "<meta name=\"keywords\" content=\"HTML, CSS, JavaScript\">\n" +
                "<style>\n" +
                ".mdiv {\n" +
                "border: 2px outset black;\n" +
                "text-align: center;\n" +
                "height: 309px;\n" +
                "width: 200px;\n" +
                "margin: auto;\n" +
                "}\n" +
                ".minidiv {\n" +
                "border: 1px solid black;\n" +
                "text-align: center;\n" +
                "height: 60px;\n" +
                "width: 200px;\n" +
                "margin: auto;\n" +
                "}\n" +
                "#pop\n" +
                "{\n" +
                "width: 60px;\n" +
                "height: 30px;\n" +
                "background-color: white;\n" +
                "border: 2px solid #e7e7e7\n" +
                "}\n" +
                "#pop:hover\n"+
                "{\n"+
                "background-color: #e7e7e7;\n"+
                "}\n"+
                "</style>\n" +
                "</head>\n" +
                "<body>";
        kod += "<form action='/updateBooksAction' method='post' class='mdiv'><div>";

        kod += "<div class = 'minidiv'>";
        kod += "<div>id: " + id + "</div>";
        kod += "<div>" + "<input type='hidden' name='id' value="+String.format("'%s'",id)+"/>"  + "</div>";
        kod += "</div>";

        kod += "<div class = 'minidiv'>";
        kod += "<div>Title: </div>";
        kod += "<div>" + "<input type='text' name='title'/>" + "</div>";
        kod += "</div>";

        kod += "<div class = 'minidiv'>";
        kod += "<div>AuthorName: </div>";
        kod += "<div>" + "<input type='text' name='authorName'/>" + "</div>";
        kod += "</div>";

        kod += "<div class = 'minidiv'>";
        kod += "<div>AuthorSurname: </div>";
        kod += "<div>" + "<input type='text' name='authorSurname'/>" + "</div>";
        kod += "</div>";

        kod += "<div class = 'minidiv'>";
        kod += "<div>" + "<input type='submit' value='Prześlij' id='pop'>" + "</div>";
        kod += "</div>";

        kod += "</div></form></body></html>";
        return kod;
    }
}
