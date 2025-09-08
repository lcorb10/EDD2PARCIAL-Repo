import java.util.*;

/**
 * Nodo: representa un nodo del AVL.
 * - guarda dato, hijos (izq/der) y altura (altura del subárbol, similar a "fe" en tu código original).
 */
class Nodo {
    private int dato;
    private Nodo izq;
    private Nodo der;
    private int altura;

    public Nodo(int dato) {
        this.dato = dato;
        this.izq = null;
        this.der = null;
        this.altura = 1; // nuevo nodo -> altura 1
    }

    public int getDato() { return dato; }
    public void setDato(int dato) { this.dato = dato; }

    public Nodo getIzq() { return izq; }
    public void setIzq(Nodo izq) { this.izq = izq; }

    public Nodo getDer() { return der; }
    public void setDer(Nodo der) { this.der = der; }

    public int getAltura() { return altura; }
    public void setAltura(int altura) { this.altura = altura; }
}

/**
 * ArbolAVL: implementación AVL (inserción/eliminación balanceadas) +
 * TODOS los métodos "extra" y las impresiones especiales que tuvimos.
 */
public class ArbolAVL {
    private Nodo raiz;

    public Nodo getRaiz() { return raiz; }

    // -------------------- utilidades internas --------------------

    /** Devuelve la altura de un nodo (0 si es null). */
    public int alturaNodo(Nodo nodo) {
        return nodo == null ? 0 : nodo.getAltura();
    }

    /** Altura (profundidad máxima) del subárbol (útil para TreePrinter). */
    public static int alturaArbolStatic(Nodo n) {
        if (n == null) return 0;
        return Math.max(alturaArbolStatic(n.getIzq()), alturaArbolStatic(n.getDer())) + 1;
    }

    /** Factor de equilibrio: altura(izq) - altura(der). */
    public int factorEquilibrio(Nodo nodo) {
        if (nodo == null) return 0;
        return alturaNodo(nodo.getIzq()) - alturaNodo(nodo.getDer());
    }

    private int max(int a, int b) { return (a > b) ? a : b; }

    // -------------------- rotaciones AVL --------------------

    /** Rotación derecha (y es la raíz desembozada). */
    public Nodo rotacionDerecha(Nodo y) {
        Nodo x = y.getIzq();
        Nodo temp = x.getDer();

        x.setDer(y);
        y.setIzq(temp);

        // actualizar alturas
        y.setAltura(Math.max(alturaNodo(y.getIzq()), alturaNodo(y.getDer())) + 1);
        x.setAltura(Math.max(alturaNodo(x.getIzq()), alturaNodo(x.getDer())) + 1);

        System.out.println("Rotación Derecha sobre nodo " + y.getDato());
        return x;
    }

    /** Rotación izquierda (y es la raíz desembozada). */
    public Nodo rotacionIzquierda(Nodo y) {
        Nodo x = y.getDer();
        Nodo temp = x.getIzq();

        x.setIzq(y);
        y.setDer(temp);

        // actualizar alturas
        y.setAltura(Math.max(alturaNodo(y.getIzq()), alturaNodo(y.getDer())) + 1);
        x.setAltura(Math.max(alturaNodo(x.getIzq()), alturaNodo(x.getDer())) + 1);

        System.out.println("Rotación Izquierda sobre nodo " + y.getDato());
        return x;
    }

    // -------------------- inserción AVL --------------------

    /** Inserta un valor (balanceado). */
    public void insertar(int valor) {
        raiz = insertarRec(raiz, valor);
    }

    private Nodo insertarRec(Nodo nodo, int valor) {
        if (nodo == null) return new Nodo(valor);

        if (valor < nodo.getDato()) nodo.setIzq(insertarRec(nodo.getIzq(), valor));
        else if (valor > nodo.getDato()) nodo.setDer(insertarRec(nodo.getDer(), valor));
        else return nodo; // duplicados no permitidos

        // actualizar altura
        nodo.setAltura(Math.max(alturaNodo(nodo.getIzq()), alturaNodo(nodo.getDer())) + 1);

        // balance
        int balance = factorEquilibrio(nodo);

        // LL
        if (balance > 1 && valor < nodo.getIzq().getDato()) return rotacionDerecha(nodo);
        // RR
        if (balance < -1 && valor > nodo.getDer().getDato()) return rotacionIzquierda(nodo);
        // LR
        if (balance > 1 && valor > nodo.getIzq().getDato()) {
            nodo.setIzq(rotacionIzquierda(nodo.getIzq()));
            return rotacionDerecha(nodo);
        }
        // RL
        if (balance < -1 && valor < nodo.getDer().getDato()) {
            nodo.setDer(rotacionDerecha(nodo.getDer()));
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    // -------------------- eliminación AVL --------------------

    /** Elimina un valor (balanceado). */
    public void eliminar(int valor) {
        raiz = deleteRec(raiz, valor);
    }

    private Nodo deleteRec(Nodo root, int valor) {
        if (root == null) return root;

        if (valor < root.getDato()) root.setIzq(deleteRec(root.getIzq(), valor));
        else if (valor > root.getDato()) root.setDer(deleteRec(root.getDer(), valor));
        else {
            // caso 0/1 hijos
            if (root.getIzq() == null) return root.getDer();
            else if (root.getDer() == null) return root.getIzq();

            // 2 hijos -> sucesor en orden (mínimo del subárbol derecho)
            int sucesor = minValue(root.getDer());
            root.setDato(sucesor);
            root.setDer(deleteRec(root.getDer(), sucesor));
        }

        // actualizar altura y balancear
        root.setAltura(Math.max(alturaNodo(root.getIzq()), alturaNodo(root.getDer())) + 1);
        int balance = factorEquilibrio(root);

        // LL
        if (balance > 1 && factorEquilibrio(root.getIzq()) >= 0) return rotacionDerecha(root);
        // LR
        if (balance > 1 && factorEquilibrio(root.getIzq()) < 0) {
            root.setIzq(rotacionIzquierda(root.getIzq()));
            return rotacionDerecha(root);
        }
        // RR
        if (balance < -1 && factorEquilibrio(root.getDer()) <= 0) return rotacionIzquierda(root);
        // RL
        if (balance < -1 && factorEquilibrio(root.getDer()) > 0) {
            root.setDer(rotacionDerecha(root.getDer()));
            return rotacionIzquierda(root);
        }

        return root;
    }

    /** Devuelve el mínimo valor en un subárbol. */
    public int minValue(Nodo node) {
        int min = node.getDato();
        while (node.getIzq() != null) {
            node = node.getIzq();
            min = node.getDato();
        }
        return min;
    }

    /** Devuelve el máximo valor en un subárbol. */
    public int maxValue(Nodo node) {
        int max = node.getDato();
        while (node.getDer() != null) {
            node = node.getDer();
            max = node.getDato();
        }
        return max;
    }

    // -------------------- Recorridos --------------------

    /** InOrden: imprime ordenado. (wrapper) */
    public void inOrden() { inOrdenRec(raiz); System.out.println(); }
    /** InOrden recursivo aceptando nodo. */
    public void inOrdenRec(Nodo nodo) {
        if (nodo == null) return;
        inOrdenRec(nodo.getIzq());
        System.out.print(nodo.getDato() + " ");
        inOrdenRec(nodo.getDer());
    }

    /** PreOrden (wrapper). */
    public void preOrden() { preOrdenRec(raiz); System.out.println(); }
    public void preOrdenRec(Nodo nodo) {
        if (nodo == null) return;
        System.out.print(nodo.getDato() + " ");
        preOrdenRec(nodo.getIzq());
        preOrdenRec(nodo.getDer());
    }

    /** PostOrden (wrapper). */
    public void postOrden() { postOrdenRec(raiz); System.out.println(); }
    public void postOrdenRec(Nodo nodo) {
        if (nodo == null) return;
        postOrdenRec(nodo.getIzq());
        postOrdenRec(nodo.getDer());
        System.out.print(nodo.getDato() + " ");
    }

    // -------------------- Impresiones especiales --------------------

    /** Imprime árbol con sangría (visual, rotado). */
    public void imprimirArbol() { imprimirArbolRec(raiz, 0); }
    private void imprimirArbolRec(Nodo nodo, int nivel) {
        if (nodo == null) return;
        imprimirArbolRec(nodo.getDer(), nivel + 1);
        // crear indentación segura (sin usar String.repeat para compatibilidad si se desea)
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nivel * 4; i++) sb.append(' ');
        System.out.println(sb.toString() + nodo.getDato());
        imprimirArbolRec(nodo.getIzq(), nivel + 1);
    }

    /** Imprime por niveles (BFS), con saltos de línea por nivel. */
    public void imprimirPorNiveles() {
        if (raiz == null) return;
        Queue<Nodo> q = new LinkedList<>();
        q.add(raiz);
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                Nodo cur = q.poll();
                System.out.print(cur.getDato() + " ");
                if (cur.getIzq() != null) q.add(cur.getIzq());
                if (cur.getDer() != null) q.add(cur.getDer());
            }
            System.out.println();
        }
    }

    /** Imprime nodos del nivel k (raíz = nivel 0). */
    public void imprimirNivel(int k) { imprimirNivelRec(raiz, k); System.out.println(); }
    private void imprimirNivelRec(Nodo nodo, int k) {
        if (nodo == null) return;
        if (k == 0) { System.out.print(nodo.getDato() + " "); return; }
        imprimirNivelRec(nodo.getIzq(), k - 1);
        imprimirNivelRec(nodo.getDer(), k - 1);
    }

    /** Imprime árbol usando matriz (TreePrinter similar al original). */
    public void treePrinter() {
        int h = alturaArbolStatic(raiz);
        if (h == 0) { System.out.println("(árbol vacío)"); return; }
        int cols = getCols(h);
        int[][] M = new int[h][cols];
        for (int i = 0; i < h; i++) Arrays.fill(M[i], 0);
        printTreeMatrix(M, raiz, cols/2, 0, h);
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < cols; j++) {
                if (M[i][j] == 0) System.out.print("   ");
                else System.out.printf("%2d ", M[i][j]);
            }
            System.out.println();
        }
    }

    // helper para treePrinter: número de columnas = 2^h -1
    private int getCols(int h) {
        if (h <= 1) return 1;
        return getCols(h - 1) * 2 + 1;
    }

    private void printTreeMatrix(int[][] M, Nodo root, int col, int row, int height) {
        if (root == null) return;
        M[row][col] = root.getDato();
        if (height <= 1) return;
        int offset = (int)Math.pow(2, height - 2);
        printTreeMatrix(M, root.getIzq(), col - offset, row + 1, height - 1);
        printTreeMatrix(M, root.getDer(), col + offset, row + 1, height - 1);
    }

    // -------------------- búsquedas y utilidades --------------------

    /** Buscar (wrapper) */
    public boolean buscar(int valor) { return buscarRec(raiz, valor); }
    private boolean buscarRec(Nodo nodo, int valor) {
        if (nodo == null) return false;
        if (valor == nodo.getDato()) return true;
        return valor < nodo.getDato() ? buscarRec(nodo.getIzq(), valor) : buscarRec(nodo.getDer(), valor);
    }

    /** Suma de todos los nodos (recursivo). */
    public int sumaDeNodos() { return sumaDeNodosRec(raiz); }
    private int sumaDeNodosRec(Nodo nodo) {
        if (nodo == null) return 0;
        return nodo.getDato() + sumaDeNodosRec(nodo.getIzq()) + sumaDeNodosRec(nodo.getDer());
    }

    /** Cuenta nodos, hojas, internos, nodos con un solo hijo */
    public int contarNodos() { return contarNodosRec(raiz); }
    private int contarNodosRec(Nodo nodo) {
        if (nodo == null) return 0;
        return 1 + contarNodosRec(nodo.getIzq()) + contarNodosRec(nodo.getDer());
    }

    public int contarHojas() { return contarHojasRec(raiz); }
    private int contarHojasRec(Nodo nodo) {
        if (nodo == null) return 0;
        if (nodo.getIzq() == null && nodo.getDer() == null) return 1;
        return contarHojasRec(nodo.getIzq()) + contarHojasRec(nodo.getDer());
    }

    public int contarInternos() { return contarInternosRec(raiz); }
    private int contarInternosRec(Nodo nodo) {
        if (nodo == null || (nodo.getIzq() == null && nodo.getDer() == null)) return 0;
        return 1 + contarInternosRec(nodo.getIzq()) + contarInternosRec(nodo.getDer());
    }

    public int contarUnHijo() { return contarUnHijoRec(raiz); }
    private int contarUnHijoRec(Nodo nodo) {
        if (nodo == null) return 0;
        int c = ((nodo.getIzq() == null && nodo.getDer() != null) || (nodo.getIzq() != null && nodo.getDer() == null)) ? 1 : 0;
        return c + contarUnHijoRec(nodo.getIzq()) + contarUnHijoRec(nodo.getDer());
    }

    /** Cuenta nodos pares */
    public int contarPares() { return contarParesRec(raiz); }
    private int contarParesRec(Nodo nodo) {
        if (nodo == null) return 0;
        int c = (nodo.getDato() % 2 == 0) ? 1 : 0;
        return c + contarParesRec(nodo.getIzq()) + contarParesRec(nodo.getDer());
    }

    /** Suma de hojas */
    public int sumaHojas() { return sumaHojasRec(raiz); }
    private int sumaHojasRec(Nodo nodo) {
        if (nodo == null) return 0;
        if (nodo.getIzq() == null && nodo.getDer() == null) return nodo.getDato();
        return sumaHojasRec(nodo.getIzq()) + sumaHojasRec(nodo.getDer());
    }

    /** Encontrar mayor/menor (BST property) */
    public int encontrarMayorElemento() { if (raiz == null) throw new NoSuchElementException("árbol vacío"); return maxValue(raiz); }
    public int encontrarMenorElemento() { if (raiz == null) throw new NoSuchElementException("árbol vacío"); return minValue(raiz); }

    // -------------------- niveles y rangos --------------------

    /** Nivel (profundidad) de un valor, devuelve -1 si no existe. */
    public int nivelDeNodo(int valor) { return nivelDeNodoRec(raiz, valor, 0); }
    private int nivelDeNodoRec(Nodo nodo, int valor, int nivel) {
        if (nodo == null) return -1;
        if (nodo.getDato() == valor) return nivel;
        int izq = nivelDeNodoRec(nodo.getIzq(), valor, nivel + 1);
        if (izq != -1) return izq;
        return nivelDeNodoRec(nodo.getDer(), valor, nivel + 1);
    }

    /** Imprime nodos dentro de un rango [min,max]. (usa BST) */
    public void imprimirEnRango(int min, int max) { imprimirEnRangoRec(raiz, min, max); System.out.println(); }
    private void imprimirEnRangoRec(Nodo nodo, int min, int max) {
        if (nodo == null) return;
        if (nodo.getDato() > min) imprimirEnRangoRec(nodo.getIzq(), min, max);
        if (nodo.getDato() >= min && nodo.getDato() <= max) System.out.print(nodo.getDato() + " ");
        if (nodo.getDato() < max) imprimirEnRangoRec(nodo.getDer(), min, max);
    }

    /** Contar/sumar en nivel k (raíz nivel 0). */
    public int contarEnNivel(int k) { return contarEnNivelRec(raiz, k); }
    private int contarEnNivelRec(Nodo nodo, int nivel) {
        if (nodo == null) return 0;
        if (nivel == 0) return 1;
        return contarEnNivelRec(nodo.getIzq(), nivel - 1) + contarEnNivelRec(nodo.getDer(), nivel - 1);
    }
    public int sumaEnNivel(int k) { return sumaEnNivelRec(raiz, k); }
    private int sumaEnNivelRec(Nodo nodo, int nivel) {
        if (nodo == null) return 0;
        if (nivel == 0) return nodo.getDato();
        return sumaEnNivelRec(nodo.getIzq(), nivel - 1) + sumaEnNivelRec(nodo.getDer(), nivel - 1);
    }

    // -------------------- comparaciones --------------------

    /** Verifica si dos árboles (subárboles) son idénticos. */
    public boolean sonIdenticos(Nodo n1, Nodo n2) {
        if (n1 == null && n2 == null) return true;
        if (n1 == null || n2 == null) return false;
        return n1.getDato() == n2.getDato() &&
               sonIdenticos(n1.getIzq(), n2.getIzq()) &&
               sonIdenticos(n1.getDer(), n2.getDer());
    }

    /** Verifica si n2 es espejo de n1. */
    public boolean sonEspejo(Nodo n1, Nodo n2) {
        if (n1 == null && n2 == null) return true;
        if (n1 == null || n2 == null) return false;
        return n1.getDato() == n2.getDato() &&
               sonEspejo(n1.getIzq(), n2.getDer()) &&
               sonEspejo(n1.getDer(), n2.getIzq());
    }

    // -------------------- caminos / ancestros / distancia --------------------

    /** Imprime ancestros de valor (de la raíz hacia el padre). Devuelve true si encontró */
    public boolean imprimirAncestros(int valor) {
        return imprimirAncestrosRec(raiz, valor);
    }
    private boolean imprimirAncestrosRec(Nodo nodo, int valor) {
        if (nodo == null) return false;
        if (nodo.getDato() == valor) return true;
        if (imprimirAncestrosRec(nodo.getIzq(), valor) || imprimirAncestrosRec(nodo.getDer(), valor)) {
            System.out.print(nodo.getDato() + " ");
            return true;
        }
        return false;
    }

    /** Imprime el camino desde la raíz hasta 'valor' (si existe). */
    public boolean imprimirCamino(int valor) {
        List<Integer> path = new ArrayList<>();
        if (!caminoRec(raiz, valor, path)) return false;
        for (int v : path) System.out.print(v + " ");
        return true;
    }
    private boolean caminoRec(Nodo nodo, int valor, List<Integer> path) {
        if (nodo == null) return false;
        path.add(nodo.getDato());
        if (nodo.getDato() == valor) return true;
        if (valor < nodo.getDato() && caminoRec(nodo.getIzq(), valor, path)) return true;
        if (valor > nodo.getDato() && caminoRec(nodo.getDer(), valor, path)) return true;
        path.remove(path.size() - 1);
        return false;
    }

    /** Lowest Common Ancestor aprovechando propiedad BST. */
    private Nodo lca(Nodo nodo, int n1, int n2) {
        if (nodo == null) return null;
        if (n1 < nodo.getDato() && n2 < nodo.getDato()) return lca(nodo.getIzq(), n1, n2);
        if (n1 > nodo.getDato() && n2 > nodo.getDato()) return lca(nodo.getDer(), n1, n2);
        return nodo;
    }
    private int distanciaDesde(Nodo nodo, int valor) {
        if (nodo == null) throw new NoSuchElementException("valor no encontrado para distancia");
        if (nodo.getDato() == valor) return 0;
        if (valor < nodo.getDato()) return 1 + distanciaDesde(nodo.getIzq(), valor);
        else return 1 + distanciaDesde(nodo.getDer(), valor);
    }
    /** Distancia (en aristas) entre n1 y n2 */
    public int distanciaEntreNodos(int n1, int n2) {
        Nodo anc = lca(raiz, n1, n2);
        if (anc == null) throw new NoSuchElementException("No existe ancestro común (valores no en árbol)");
        return distanciaDesde(anc, n1) + distanciaDesde(anc, n2);
    }

    // -------------------- estructura del árbol --------------------

    /** Comprueba si es completo (todos los niveles completos excepto el último, que está a la izquierda). */
    public boolean esCompleto() {
        int total = contarNodos();
        return esCompletoRec(raiz, 0, total);
    }
    private boolean esCompletoRec(Nodo nodo, int index, int totalNodos) {
        if (nodo == null) return true;
        if (index >= totalNodos) return false;
        return esCompletoRec(nodo.getIzq(), 2 * index + 1, totalNodos) &&
               esCompletoRec(nodo.getDer(), 2 * index + 2, totalNodos);
    }

    /** Comprueba si es perfecto (todas las hojas al mismo nivel y todos los internos con 2 hijos). */
    public boolean esPerfecto() {
        int h = alturaArbolStatic(raiz);
        return esPerfectoRec(raiz, h, 0);
    }
    private boolean esPerfectoRec(Nodo nodo, int altura, int nivel) {
        if (nodo == null) return true;
        if (nodo.getIzq() == null && nodo.getDer() == null) return (altura == nivel + 1);
        if (nodo.getIzq() == null || nodo.getDer() == null) return false;
        return esPerfectoRec(nodo.getIzq(), altura, nivel + 1) &&
               esPerfectoRec(nodo.getDer(), altura, nivel + 1);
    }

    /** Comprueba recursivamente si todo nodo cumple |FB| <= 1 (útil como comprobación). */
    public boolean esBalanceado() { return esBalanceadoRec(raiz); }
    private boolean esBalanceadoRec(Nodo nodo) {
        if (nodo == null) return true;
        int fb = factorEquilibrio(nodo);
        return Math.abs(fb) <= 1 && esBalanceadoRec(nodo.getIzq()) && esBalanceadoRec(nodo.getDer());
    }

    // -------------------- otras operaciones --------------------

    /** Invierte (espejo) el árbol. */
    public void invertir() { invertirRec(raiz); }
    private void invertirRec(Nodo nodo) {
        if (nodo == null) return;
        Nodo tmp = nodo.getIzq();
        nodo.setIzq(nodo.getDer());
        nodo.setDer(tmp);
        invertirRec(nodo.getIzq());
        invertirRec(nodo.getDer());
    }

    /** Diámetro del árbol (número de nodos en el camino más largo). */
    public int diametro() { return diametroRec(raiz); }
    private int diametroRec(Nodo nodo) {
        if (nodo == null) return 0;
        int lAlt = alturaArbolStatic(nodo.getIzq());
        int rAlt = alturaArbolStatic(nodo.getDer());
        int diamIzq = diametroRec(nodo.getIzq());
        int diamDer = diametroRec(nodo.getDer());
        return Math.max(lAlt + rAlt + 1, Math.max(diamIzq, diamDer));
    }

    // -------------------- getters/ wrappers auxiliares --------------------

    public int contarNodos(Nodo nodo) { return contarNodosRec(nodo); }
    public int contarHojas(Nodo nodo) { return contarHojasRec(nodo); }
    public void imprimirPorNiveles(Nodo nodo) { imprimirPorNiveles(); }
    public void imprimirArbol(Nodo nodo) { imprimirArbol(); }
    public void inOrden(Nodo nodo) { inOrdenRec(nodo); System.out.println(); }
    public void preOrden(Nodo nodo) { preOrdenRec(nodo); System.out.println(); }
    public void postOrden(Nodo nodo) { postOrdenRec(nodo); System.out.println(); }

    // -------------------- programa de prueba (main) --------------------
    public static void main(String[] args) {
        ArbolAVL avl = new ArbolAVL();

        // Insertar varios nodos (se balanceará automáticamente)
        int[] elementos = { 8, 3, 1, 6, 4, 7, 10, 14, 13, 5, 2 };
        for (int v : elementos) avl.insertar(v);

        System.out.println("== Árbol (sangría) ==");
        avl.imprimirArbol();

        System.out.println("\n== TreePrinter (matriz) ==");
        avl.treePrinter();

        System.out.println("\n== Recorridos ==");
        System.out.print("InOrden: "); avl.inOrden();
        System.out.print("PreOrden: "); avl.preOrden();
        System.out.print("PostOrden: "); avl.postOrden();

        System.out.println("\n== Por niveles ==");
        avl.imprimirPorNiveles();

        System.out.println("\n== Propiedades y consultas ==");
        System.out.println("Contar nodos: " + avl.contarNodos());
        System.out.println("Contar hojas: " + avl.contarHojas());
        System.out.println("Contar internos: " + avl.contarInternos());
        System.out.println("Contar un hijo: " + avl.contarUnHijo());
        System.out.println("Suma nodos: " + avl.sumaDeNodos());
        System.out.println("Suma hojas: " + avl.sumaHojas());
        System.out.println("Mayor: " + avl.encontrarMayorElemento());
        System.out.println("Menor: " + avl.encontrarMenorElemento());
        System.out.println("¿Contiene 7?: " + avl.buscar(7));
        System.out.println("Nivel de 7: " + avl.nivelDeNodo(7));
        System.out.println("¿Es completo?: " + avl.esCompleto());
        System.out.println("¿Es perfecto?: " + avl.esPerfecto());
        System.out.println("¿Es balanceado (check)?: " + avl.esBalanceado());
        System.out.println("Diametro: " + avl.diametro());

        System.out.println("\n== Caminos y distancias ==");
        System.out.print("Camino a 13: "); if (!avl.imprimirCamino(13)) System.out.println("(no encontrado)"); else System.out.println();
        System.out.print("Ancestros de 13: "); if (!avl.imprimirAncestros(13)) System.out.println("(no encontrado)"); else System.out.println();
        System.out.println("Distancia entre 4 y 7: " + avl.distanciaEntreNodos(4, 7));

        System.out.println("\n== Eliminar 10 y mostrar árbol ==");
        avl.eliminar(10);
        avl.imprimirArbol();
        System.out.println("\nInOrden tras eliminar 10: "); avl.inOrden();

        System.out.println("\n== Invertir (hacer espejo) y mostrar ==");
        avl.invertir();
        avl.imprimirArbol();
    }
}
