package cz.czechitas.java2webapps.ukol2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {
    //    řešení z lekce - seznam obrázků z unsplash.com
//    deklarace proměnné se seznamem obrázků
//    seznam obrázků vytvořím jen jednou, proto z něho udělám field
//    final proto, že seznam obrázků nebudu měnit
    private final List<String> images;
    //    deklarace proměnné pro seznam citátů
    private final List<String> citaty;
    //    deklarace proměnné random
    private final Random random;

    //  konstruktor
    // IOException - když tam soubor není, můžu si to dovolit, protože vím, že tam je. Kdyby tam nebyl, tak by
//    aplikace nefungovala
//    seznamy citátů, obrázků i generátor náhodných čísel přidávám do konstruktoru, protože je používám jen jednou,
//    není potřeba, aby se vytvářely znovu při každém požadavku z prohlížeče
    public MainController() throws IOException {
        //    vytvořím seznam obrázků
        images = Arrays.asList("sHrGEGt6Xe4", "lnrzs7M_DXU", "KzyQrf6qAz4");
//      načtu seznam citátů z textového souboru
        citaty = readAllLines("citaty.txt");
//        vytvořím objekt random
        random = new Random();
    }

    //    deklaruji a inicializuji proměnnou generatorNahodnychCisel
//    Proč je to v příkladu s kostkou v konstruktoru? Protože stačí, když se vytvoří jen jednou (tj. při vytvoření instance
//    MainController
//    Random generatorNahodnychCisel = new Random();
    // náhodné číslo se tvořím až v metodě zobrazCitat, jinak mi to nefungovalo (měla jsem to dát do konstruktoru)
    //int nahodneCislo = generatorNahodnychCisel.nextInt(5) + 1;

    //    Různé způsoby pro tvorbu prázdného listu:
//    Z knížky Java (Roubalová 2015): ArrayList<String> seznamTextu = new ArrayList();
//    Z webu https://beginnersbook.com/2013/12/java-arraylist-get-method-example/:
//    ArrayList<String> seznamTextu = new ArrayList<String>();
//    Z kurzu Java1 (tento způsob je nejlepší, má obecnější využití)
//    List<String> seznamTextu = new ArrayList<>();
//    metoda pro výpis citátů
//    !metody add a get u Listu mi nefungovaly, pokud kód nebyl uvnitř metody vratCitat (mělo to být v konstruktoru)
//    public String vratCitat(int cisloCitatu) {
////      seznam citátů
//        List<String> seznamTextu = new ArrayList<>();
//        seznamTextu.add("Debugging /de·bugh·ing/ (verb): The Classic Mystery Game where you are the detective, the victim, and the murderer.");
//        seznamTextu.add("A user interface is like a joke. If you have to explain it, it's not that good.");
//        seznamTextu.add("To replace programmers with robots, clients will have to accurately describe what they want. We're safe.");
//        seznamTextu.add("I have a joke on programming but it only works on my computer.");
//        seznamTextu.add("99 little bugs in the code, 99 bugs in the code. Take one down, patch it around. 127 little bugs in the code…");
//        return seznamTextu.get(cisloCitatu);
//    }


    @GetMapping("/")
    public ModelAndView zobrazCitat() {
//      řešení z lekce - použití obrázku z unsplash.com
//      výběr náhodného obrázku ze seznamu definovaném v konstruktoru
        String obrazek = String.format("https://source.unsplash.com/%s", images.get(random.nextInt(images.size())));
//        String obrazek = String.format("https://source.unsplash.com/%s", images.get(1));

//        int nahodneCislo = generatorNahodnychCisel.nextInt(4) + 1;

        ModelAndView result = new ModelAndView("index");
//        moje řešení: obrázek stažení do images a očíslovaný 1-5
//        result.addObject("obrazek", "background-image: url('/images/photo-" +
//                nahodneCislo + ".jpg');");
        result.addObject("bodyStyle", String.format("background-image: url(%s);", obrazek));
//        moje řešení
//        result.addObject("text", vratCitat(nahodneCislo));
//        řesení z lekce
        result.addObject("citat", citaty.get(random.nextInt(citaty.size())));
        return result;
    }

    //    další metody jsou až za těmi, co vrací ModelAndView
//    kód pro načítání citátů z textového souboru
    private static List<String> readAllLines(String resource) throws IOException {
        //Soubory z resources se získávají pomocí classloaderu. Nejprve musíme získat aktuální classloader.
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        //Pomocí metody getResourceAsStream() získáme z classloaderu InpuStream, který čte z příslušného souboru.
        //Následně InputStream převedeme na BufferedRead, který čte text v kódování UTF-8
        try (InputStream inputStream = classLoader.getResourceAsStream(resource);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            //Metoda lines() vrací stream řádků ze souboru. Pomocí kolektoru převedeme Stream<String> na List<String>.
            return reader
                    .lines()
                    .collect(Collectors.toList());
        }
    }

}
