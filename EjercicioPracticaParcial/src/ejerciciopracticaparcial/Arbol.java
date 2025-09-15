/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejerciciopracticaparcial;

import java.util.ArrayList;

public class Arbol {

    private Nodo raiz;

    public Nodo getRaiz() {
        return raiz;
    }

    public void agregarNodo(int dato) {
        Nodo nodo = new Nodo(dato);
        if (raiz == null) {
            raiz = nodo;
        } else {
            agregarNodoRecursivo(raiz, nodo);
        }
    }

    public void agregarNodoRecursivo(Nodo nodo1, Nodo nodo2) {
        if (nodo2.getDato() < nodo1.getDato()) {
            if (nodo1.getIzq() == null) {
                nodo1.setIzq(nodo2);
            } else {
                agregarNodoRecursivo(nodo1.getIzq(), nodo2);
            }
        } else {
            if (nodo1.getDer() == null) {
                nodo1.setDer(nodo2);
            } else {
                agregarNodoRecursivo(nodo1.getDer(), nodo2);
            }
        }
    }

    public void imprimir(Nodo nodo, int nivel) {
        if (nodo != null) {
            imprimir(nodo.getDer(), nivel + 1);
            for (int i = 0; i < nivel; i++) {
                System.out.print("                ");
            }
            System.out.println(nodo.getDato());
            imprimir(nodo.getIzq(), nivel + 1);
        }
    }

    public void preOrdenRecursivo(Nodo nodo) {
        if (nodo == null) {
            return;
        }
        System.out.print(nodo.getDato());
        preOrdenRecursivo(nodo.getIzq());
        preOrdenRecursivo(nodo.getDer());
    }

    public void enOrdenRecursivo(Nodo nodo) {
        if (nodo == null) {
            return;
        }
        enOrdenRecursivo(nodo.getIzq());
        System.out.print(nodo.getDato());
        // enOrdenRecursivo(nodo); duda 
        enOrdenRecursivo(nodo.getDer());
    }

    public void postOrdenRecursivo(Nodo nodo) {
        if (nodo == null) {
            return;
        }
        postOrdenRecursivo(nodo.getIzq());
        postOrdenRecursivo(nodo.getDer());
        System.out.print(nodo.getDato());
    }

    public int sumaDeNodos(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return nodo.getDato() + sumaDeNodos(nodo.getIzq()) + sumaDeNodos(nodo.getDer());
    }

    public int encontrarAltura(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return Math.max(encontrarAltura(nodo.getIzq()), encontrarAltura(nodo.getDer())) + 1;
    }

    public int encontrarNivelArbol(Nodo nodo) {
        if (nodo == null) {
            return -1;
        }
        return Math.max(encontrarNivelArbol(nodo.getIzq()), encontrarNivelArbol(nodo.getDer())) + 1;
    }

    public int nivelDeNodo(Nodo nodo, int dato) { // Sirve si es un arbol binario de busqueda
        Nodo aux = nodo;
        int nivel = 0;

        while (aux != null) {
            if (dato == aux.getDato()) {
                return nivel;
            }
            if (dato < aux.getDato()) {
                aux = aux.getIzq();
            } else {
                aux = aux.getDer();
            }
            nivel++;
        }
        return -1; // no encontrado
    }

    public int encontrarMayorElemento(Nodo nodo) {
        if (nodo.getDer() == null) {
            return nodo.getDato();
        }
        return encontrarMayorElemento(nodo.getDer());

    }

    public int encontrarMenorElemento(Nodo nodo) {
        if (nodo.getIzq() == null) {
            return nodo.getDato();
        }
        return encontrarMenorElemento(nodo.getIzq());
    }

    public Nodo insertarNodo(Nodo nodo, int dato) {
        if (nodo == null) {
            return new Nodo(dato);
        }
        if (dato < nodo.getDato()) {
            nodo.setIzq(insertarNodo(nodo.getIzq(), dato));

        } else if (dato > nodo.getDato()) {
            nodo.setDer(insertarNodo(nodo.getDer(), dato));

        } else {
            return nodo; // caso en el que ambos sean iguales, asi evitar que se inserte un duplicado
        }
        return nodo; // devuelve todo el arbol  con el nuevo nodo 
    }

    public Nodo eliminar(Nodo nodo, int dato) {
        if (nodo == null) {
            return null;
        }

        if (dato < nodo.getDato()) {
            nodo.setIzq(eliminar(nodo.getIzq(), dato));
            return nodo;
        } else if (dato > nodo.getDato()) {
            nodo.setDer(eliminar(nodo.getDer(), dato));
            return nodo;
        } else {
            // Caso hoja
            if (nodo.getIzq() == null && nodo.getDer() == null) {
                return null;
            }

            // Caso un hijo
            if (nodo.getIzq() == null && nodo.getDer() != null) {
                return nodo.getDer();
            }
            if (nodo.getIzq() != null && nodo.getDer() == null) {
                return nodo.getIzq();
            }

            // Caso sucesor
            Nodo sucesor = encontrarUltimoIzquierda(nodo.getDer());
            nodo.setDato(sucesor.getDato());
            nodo.setDer(eliminar(nodo.getDer(), sucesor.getDato()));
            return nodo;
        }
    }

// Eliminacion Hoja
    public Nodo eliminacionElementoHoja(Nodo nodo, int dato) {
        return eliminar(nodo, dato);
    }

// Eliminacion 1 hijo
    public Nodo eliminacionElementoUnHijo(Nodo nodo, int dato) {
        return eliminar(nodo, dato);
    }

// Eliminacion por Sucesor
    public Nodo eliminacionElementoPorSucesor(Nodo nodo, int dato) {
        return eliminar(nodo, dato);
    }

    // Eliminacion por Predecesor
    public Nodo eliminacionElementoPorPredecesor(Nodo nodo, int dato) {
        if (nodo == null) {
            return null;
        }

        if (dato < nodo.getDato()) {
            nodo.setIzq(eliminacionElementoPorPredecesor(nodo.getIzq(), dato));
            return nodo;
        } else if (dato > nodo.getDato()) {
            nodo.setDer(eliminacionElementoPorPredecesor(nodo.getDer(), dato));
            return nodo;
        } else {
            // Caso hoja
            if (nodo.getIzq() == null && nodo.getDer() == null) {
                return null;
            }

            // Caso un hijo
            if (nodo.getIzq() == null && nodo.getDer() != null) {
                return nodo.getDer();
            }
            if (nodo.getIzq() != null && nodo.getDer() == null) {
                return nodo.getIzq();
            }

            // Caso dos hijos y eliminacion por predecesor
            Nodo pred = encontrarUltimoDerecho(nodo.getIzq());
            nodo.setDato(pred.getDato());
            nodo.setIzq(eliminacionElementoPorPredecesor(nodo.getIzq(), pred.getDato()));
            return nodo;
        }
    }

    public Nodo encontrarUltimoIzquierda(Nodo nodo) {  // Encuentra el sucesor 
        while (nodo != null && nodo.getIzq() != null) {
            nodo = nodo.getIzq();
        }
        return nodo;
    }

    public Nodo encontrarUltimoDerecho(Nodo nodo) { // Encuentra el predecesor
        while (nodo != null && nodo.getDer() != null) {
            nodo = nodo.getDer();
        }
        return nodo;
    }

    // Funcion verifica si dos arboles son identicos en informacion
    public boolean VerificarArbol(Nodo nodo1, Nodo nodo2) {
        ArrayList<Integer> in1 = new ArrayList<>();
        ArrayList<Integer> pre1 = new ArrayList<>();
        ArrayList<Integer> in2 = new ArrayList<>();
        ArrayList<Integer> pre2 = new ArrayList<>();

        verificarArbolRecorridoEnOrden(nodo1, in1);
        verificarArbolRecorridoPreOrden(nodo1, pre1);

        verificarArbolRecorridoEnOrden(nodo2, in2);
        verificarArbolRecorridoPreOrden(nodo2, pre2);

        return in1.equals(in2) && pre1.equals(pre2);
    }

    public void verificarArbolRecorridoEnOrden(Nodo nodo, ArrayList<Integer> lista) {
        if (nodo == null) {
            return;
        }
        verificarArbolRecorridoEnOrden(nodo.getIzq(), lista);
        lista.add(nodo.getDato());
        verificarArbolRecorridoEnOrden(nodo.getDer(), lista);
    }

    public void verificarArbolRecorridoPreOrden(Nodo nodo, ArrayList<Integer> lista) {
        if (nodo == null) {
            return;
        }
        lista.add(nodo.getDato());
        verificarArbolRecorridoPreOrden(nodo.getIzq(), lista);
        verificarArbolRecorridoPreOrden(nodo.getDer(), lista);
    }

    public boolean isomorfos(Nodo a, Nodo b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }

        if (a.getDato() != b.getDato()) {
            return false;
        }

        boolean sinFlip = isomorfos(a.getIzq(), b.getIzq()) && isomorfos(a.getDer(), b.getDer()); // intercambia los hijos para verificar si se cumple que sea isomorfo
        boolean conFlip = isomorfos(a.getIzq(), b.getDer()) && isomorfos(a.getDer(), b.getIzq());
        return sinFlip || conFlip;
    }

    public Nodo encontrarNodo(Nodo nodo, int dato) {
        if (nodo == null) {
            return null;
        }
        if (nodo.getDato() == dato) {
            return nodo;
        }
        if (dato < nodo.getDato()) {
            return encontrarNodo(nodo.getIzq(), dato);
        } else {
            return encontrarNodo(nodo.getDer(), dato);
        }
    }

    public Nodo encontrarSucesor(Nodo raiz, int dato) {
        if (raiz == null) {
            return null;
        }

        // 1) Debe existir el nodo (sucesor del nodo, no del valor).
        Nodo x = encontrarNodo(raiz, dato);
        if (x == null) {
            return null;
        }

        // 2) Caso con hijo derecho: mínimo del derecho.
        if (x.getDer() != null) {
            return encontrarUltimoIzquierda(x.getDer()); // mínimo del subárbol derecho
        }

        // 3) Caso sin derecho: ancestro sucesor (candidato al bajar desde la raíz).
        Nodo actual = raiz;
        Nodo candidato = null;
        while (actual != null) {
            if (dato < actual.getDato()) {
                candidato = actual;      // posible sucesor
                actual = actual.getIzq();
            } else if (dato > actual.getDato()) {
                actual = actual.getDer();
            } else {
                break; // llegamos a x
            }
        }
        return candidato; // null si x es el máximo
    }
// Predecesor del *nodo* con valor 'dato' en un ABB.
// - Si el nodo existe y tiene hijo izquierdo → máximo del subárbol izquierdo.
// - Si no tiene izquierdo → ancestro predecesor: el último nodo "menor" encontrado al bajar desde la raíz.
// - Si el nodo es el mínimo del árbol o no existe → null.

    public Nodo encontrarPredecesor(Nodo raiz, int dato) {
        if (raiz == null) {
            return null;
        }

        // 1) Debe existir el nodo (predecesor del nodo, no del valor).
        Nodo x = encontrarNodo(raiz, dato);
        if (x == null) {
            return null;
        }

        // 2) Caso con hijo izquierdo: máximo del izquierdo.
        if (x.getIzq() != null) {
            return encontrarUltimoDerecho(x.getIzq()); // tu helper de "máximo del subárbol"
        }

        // 3) Caso sin izquierdo: ancestro predecesor (candidato al bajar desde la raíz).
        Nodo actual = raiz;
        Nodo candidato = null;
        while (actual != null) {
            if (dato > actual.getDato()) {
                candidato = actual;        // posible predecesor (menor que 'dato')
                actual = actual.getDer();
            } else if (dato < actual.getDato()) {
                actual = actual.getIzq();
            } else {
                break; // llegamos a x
            }
        }
        return candidato; // null si x es el mínimo
    }

    public void SolucionParcial(ArrayList<String> recorridoEnOrden, ArrayList<String> recorridoPreOrden) {
        int n = recorridoEnOrden.size();
        int m = recorridoPreOrden.size();

        String matriz[][] = new String[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            matriz[i][0] = recorridoPreOrden.get(i - 1);
        }
        for (int j = 1; j <= m; j++) {
            matriz[0][j] = recorridoEnOrden.get(j - 1);
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (matriz[i][0].equals(matriz[0][j])) {
                    matriz[i][j] = "  " + matriz[i][0] + "  ";
                } else {
                    matriz[i][j] = "     ";
                }
            }
        }

        for (int j = 0; j <= m; j++) {
            matriz[0][j] = "";
        }
        for (int i = 0; i <= n; i++) {
            matriz[i][0] = "";
        }  // Elimina el encabezado

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println(); // imprime un salto de linea 
        }
    }

}
