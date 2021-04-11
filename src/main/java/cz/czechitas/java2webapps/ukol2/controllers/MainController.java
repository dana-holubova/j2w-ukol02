package cz.czechitas.java2webapps.ukol2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class MainController {

    //    deklaruji a inicializuji proměnnou generatorNahodnychCisel
//    Proč je to v příkladu s kostkou v konstruktoru?
    Random generatorNahodnychCisel = new Random();
    // náhodné číslo se tvořím až v metodě zobrazCitat, jinak mi to nefungovalo
    //int nahodneCislo = generatorNahodnychCisel.nextInt(4) + 1;

//    Různé způsoby pro tvorbu prázdného listu:
//    Z knížky Java (Roubalová 2015): ArrayList<String> seznamTextu = new ArrayList();
//    Z webu https://beginnersbook.com/2013/12/java-arraylist-get-method-example/:
//    ArrayList<String> seznamTextu = new ArrayList<String>();
//    Z kurzu Java1: List<String> seznamTextu = new ArrayList<>();
//    metoda pro výpis citátů
//    !metody add a get u Listu mi nefungovaly, pokud kód nebyl uvnitř metody vratCitat
    public String vratCitat(int cisloCitatu) {
//      seznam citátů
        List<String> seznamTextu = new ArrayList<>();
        seznamTextu.add("Debugging /de·bugh·ing/ (verb): The Classic Mystery Game where you are the detective, the victim, and the murderer.");
        seznamTextu.add("A user interface is like a joke. If you have to explain it, it's not that good.");
        seznamTextu.add("To replace programmers with robots, clients will have to accurately describe what they want. We're safe.");
        seznamTextu.add("I have a joke on programming but it only works on my computer.");
        seznamTextu.add("99 little bugs in the code, 99 bugs in the code. Take one down, patch it around. 127 little bugs in the code…");
        return seznamTextu.get(cisloCitatu);
    }

    @GetMapping("/")
    public ModelAndView zobrazCitat() {

        int nahodneCislo = generatorNahodnychCisel.nextInt(4) + 1;

        ModelAndView result = new ModelAndView("index");
        result.addObject("obrazek", "background-image: url('/images/photo-" +
                nahodneCislo + ".jpg');");
        result.addObject("text", vratCitat(nahodneCislo));
        return result;
    }

}
