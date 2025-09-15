package edd2_parcial_1;

import java.util.ArrayList;

public class Arbol {


    public Nodo SolucionParcial(ArrayList<String> recorridoEnOrden, ArrayList<String> recorridoPreOrden) {
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
                    matriz[i][j] = matriz[i][0]; 
                } else {
                    matriz[i][j] = "     ";
                }
            }
        }

        String raizValor = recorridoPreOrden.get(0);
        Nodo raiz = new Nodo(raizValor);

 
        raiz.setIzq(agregarIzq(matriz, recorridoEnOrden, recorridoPreOrden, 0, recorridoEnOrden.indexOf(raizValor) - 1));
        raiz.setDer(agregarDer(matriz, recorridoEnOrden, recorridoPreOrden, recorridoEnOrden.indexOf(raizValor) + 1, recorridoEnOrden.size() - 1));

        return raiz;
    }

    private Nodo agregarIzq(String[][] matriz, ArrayList<String> inOrden, ArrayList<String> preOrden, int inInicio, int inFin) {
        if (inInicio > inFin) return null;

        for (int i = 1; i < matriz.length; i++) {
            for (int j = inInicio + 1; j <= inFin + 1; j++) {
                if (!matriz[i][j].equals("     ")) {
                    Nodo n = new Nodo(matriz[i][j]);
                    int pos = inOrden.indexOf(matriz[i][j]);
                    n.setIzq(agregarIzq(matriz, inOrden, preOrden, inInicio, pos - 1));
                    n.setDer(agregarDer(matriz, inOrden, preOrden, pos + 1, inFin));
                    return n;
                }
            }
        }
        return null;
    }

    private Nodo agregarDer(String[][] matriz, ArrayList<String> inOrden, ArrayList<String> preOrden, int inInicio, int inFin) {
        if (inInicio > inFin) return null;

        for (int i = 1; i < matriz.length; i++) {
            for (int j = inInicio + 1; j <= inFin + 1; j++) {
                if (!matriz[i][j].equals("     ")) {
                    Nodo n = new Nodo(matriz[i][j]);
                    int pos = inOrden.indexOf(matriz[i][j]);
                    n.setIzq(agregarIzq(matriz, inOrden, preOrden, inInicio, pos - 1));
                    n.setDer(agregarDer(matriz, inOrden, preOrden, pos + 1, inFin));
                    return n;
                }
            }
        }
        return null;
    }



    public static int getcol(int h) {
        if (h == 1) return 1;
        return getcol(h - 1) + getcol(h - 1) + 1;
    }

    public static void printTree(String[][] M, Nodo root, int col, int row, int height) {
        if (root == null) return;
        M[row][col] = root.getDato();
        printTree(M, root.getIzq(), col - (int) Math.pow(2, height - 2), row + 1, height - 1);
        printTree(M, root.getDer(), col + (int) Math.pow(2, height - 2), row + 1, height - 1);
    }

    public void TreePrinter(Nodo raiz) {
        int h = altura(raiz);
        int col = getcol(h);
        String[][] M = new String[h][col];

        
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < col; j++) {
                M[i][j] = "  ";
            }
        }

      
        printTree(M, raiz, col / 2, 0, h);

      
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(M[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int altura(Nodo nodo) {
        if (nodo == null) return 0;
        return 1 + Math.max(altura(nodo.getIzq()), altura(nodo.getDer()));
    }
}
