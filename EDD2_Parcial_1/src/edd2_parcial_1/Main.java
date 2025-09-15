package edd2_parcial_1;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        ArrayList<String> preOrden = new ArrayList<>(Arrays.asList("a","b","d","g","h","k","e","c","f","i","j"));
        ArrayList<String> enOrden  = new ArrayList<>(Arrays.asList("g","d","k","h","b","e","a","c","i","f","j"));

        Arbol arbol = new Arbol();


        Nodo raiz = arbol.SolucionParcial(enOrden, preOrden);
        System.out.println("Árbol reconstruido:");
        arbol.TreePrinter(raiz);
    }
}
