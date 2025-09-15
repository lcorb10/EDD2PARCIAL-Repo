/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejerciciopracticaparcial;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
       ArrayList<String> preOrden = new ArrayList<>(Arrays.asList("a","b","d","g","h","k","e","c","f","i","j"));
        ArrayList<String> enOrden  = new ArrayList<>(Arrays.asList("g","d","k","h","b","e","a","c","i","f","j"));
        Arbol arbol = new Arbol();
        /*Arbol arbol2 = new Arbol();
        arbol.agregarNodo(8);
        arbol.agregarNodo(3);
        arbol.agregarNodo(1);
        arbol.agregarNodo(20);
        arbol.agregarNodo(10);
        arbol.agregarNodo(5);
        arbol.agregarNodo(4);
        arbol2.agregarNodo(8);
        arbol2.agregarNodo(3);
        arbol2.agregarNodo(1);
        arbol2.agregarNodo(20);
        arbol2.agregarNodo(10);
        arbol2.agregarNodo(5);
        arbol2.agregarNodo(4);
       */
       // boolean resultado = arbol.VerificarArbol(arbol.getRaiz(), arbol2.getRaiz());
       // System.out.println(resultado);
//boolean misterio = arbol.sonIguales(arbol.getRaiz(), arbol2.getRaiz());
        //System.out.println(misterio);
//arbol.preOrdenRecursivo(arbol.getRaiz());
  arbol.SolucionParcial(enOrden, preOrden);
    
    }

}
