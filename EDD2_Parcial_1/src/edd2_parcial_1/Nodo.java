package edd2_parcial_1;

public class Nodo {

    private String dato;
    private Nodo der;
    private Nodo izq;
    private int Fe;

    public Nodo(String dato) {
        this.dato = dato;
        this.der = null;
        this.izq = null;
        this.Fe = 0;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public Nodo getDer() {
        return der;
    }

    public void setDer(Nodo der) {
        this.der = der;
    }

    public Nodo getIzq() {
        return izq;
    }

    public void setIzq(Nodo izq) {
        this.izq = izq;
    }

    public int getFe() {
        return Fe;
    }

    public void setFe(int Fe) {
        this.Fe = Fe;
    }
}
