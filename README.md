# README — Estructura de Datos II (Árboles)

> Guía de estudio basada en el pensum y en las operaciones que trabajaste en clase/código (Java). Se enfoca en: árboles binarios generales, ABB/BST, AVL, Árboles B y operaciones típicas de parcial.

---

## 1) Conceptos base

**Árbol binario**: estructura jerárquica con un nodo raíz; cada nodo tiene hasta dos hijos: izquierdo y derecho.

**ABB / BST**: árbol binario con orden: para cada nodo `x`, todo en su subárbol izquierdo < `x`, y todo en el derecho > `x`. (Sin duplicados por convenio usual; si se permiten, define política: por ejemplo, iguales al derecho.)

**Nodo**: contiene un `dato` (int en tu proyecto), y referencias `izq`, `der`. (Opcional: `padre`.)

**Ruta/Path**: secuencia de nodos desde la raíz hacia un nodo.

**Nivel / Profundidad**: número de aristas desde la raíz hasta el nodo (si la raíz está en nivel 0).

**Altura de un nodo**: longitud del camino más largo desde ese nodo hasta una hoja. Altura de un árbol = altura de la raíz. (Convención usada aquí: árbol vacío tiene altura 0; algunas fuentes usan -1.)

**Tamaño**: número total de nodos.

**Ancho máximo**: máximo número de nodos en un mismo nivel.

**Hojas**: nodos sin hijos.

**LCA (Lowest Common Ancestor)**: ancestro común más bajo (más profundo) entre dos nodos.

**Parentescos**:

* **Hermano**: comparte el mismo padre.
* **Tío**: hermano del padre.
* **Primos**: hijos de hermanos (mismo nivel, distintos padres hermanos).

---

## 2) Recorridos (traversals)

* **Preorden**: visita → izquierda → derecha. Útil para clonar/serializar estructura.
* **Inorden**: izquierda → visita → derecha. En BST produce la **secuencia ordenada**.
* **Postorden**: izquierda → derecha → visita. Útil para borrar/liberar o evaluar subárboles antes del padre.
* **Por niveles (BFS)**: de arriba hacia abajo, izquierda a derecha. Útil para comprobar propiedades por nivel (ancho máximo, completo, etc.).

**Salida esperada**: normalmente se imprimen los datos separados por espacio o se acumulan en listas según lo pida el ejercicio.

---

## 3) Propiedades estructurales

* **Árbol lleno (full)**: cada nodo tiene 0 o 2 hijos.
* **Árbol completo (complete)**: todos los niveles llenos salvo el último, que se llena de izquierda a derecha.
* **Árbol perfecto**: lleno y con todas las hojas al mismo nivel.
* **Balanceado por altura**: para cada nodo, |altura(izq) − altura(der)| ≤ 1 (definición tipo AVL).

**Funciones típicas**: `altura`, `tamaño`, `contarHojas`, `anchoMáximo`, `esCompleto`, `esLleno`, `esPerfecto`, `esBalanceadoPorAltura`.

---

## 4) ABB / BST — Operaciones típicas

### Búsqueda

* Comparar el `dato` buscado con el nodo actual.
* Ir a la izquierda si es menor; derecha si es mayor; parar si es igual.
* Complejidad: O(h), donde h es la altura (O(log n) si está balanceado; O(n) si degenerado).

### Inserción

* Igual a la búsqueda, insertando en el primer hueco `null` al final del camino.
* Política usual: **no duplicados** (si igual, no insertas). Documentar si los envías a la derecha/izquierda en caso de permitirlos.

### Eliminación (3 casos)

1. **Hoja**: se elimina y se pone `null` en el enlace del padre.
2. **Un hijo**: el padre enlaza directamente con el único hijo del nodo eliminado.
3. **Dos hijos**: reemplazar el valor por su **sucesor** (mínimo del derecho) **o** por su **predecesor** (máximo del izquierdo) y luego eliminar ese nodo auxiliar en el subárbol correspondiente.

### Mínimo / Máximo

* **Mínimo**: ir todo a la **izquierda** desde la raíz/entrada del subárbol.
* **Máximo**: ir todo a la **derecha**.

### Sucesor del **nodo**

* Si el nodo tiene **hijo derecho**: el sucesor es el **mínimo del subárbol derecho**.
* Si **no** tiene derecho: es el **primer ancestro** para el que el nodo está en su **lado izquierdo**. Sin puntero a padre, se obtiene recorriendo desde la raíz con un **candidato** (primer mayor) mientras se compara con el dato del nodo objetivo.

### Predecesor del **nodo**

* Si el nodo tiene **hijo izquierdo**: el predecesor es el **máximo del subárbol izquierdo**.
* Si **no** tiene izquierdo: es el **primer ancestro** para el que el nodo está en su **lado derecho**. Sin padre, se obtiene desde la raíz manteniendo un **candidato** (último menor).

### Validar BST

* Comprobar que todos los nodos cumplen el rango `(min, max)` heredado: al bajar a la izquierda, `max` se vuelve el valor del nodo; a la derecha, `min` se vuelve el valor del nodo. Alternativa: comprobar que el **inorden** esté estrictamente creciente (si no hay duplicados).

### Operaciones adicionales

* `k-ésimo menor` (inorden con contador), `rango [a,b]`, `floor` (mayor ≤ x), `ceil` (menor ≥ x), `convertir a lista doblemente enlazada` por inorden, `fusionar dos BST` (merge de inordenes y reconstrucción balanceada).

---

## 5) LCA (Lowest Common Ancestor)

* **En BST**: usa el orden. Si ambos valores son menores que el actual, baja a la izquierda; si ambos mayores, a la derecha; en otro caso el actual es el LCA.
* **En árbol binario general**: busca recursivamente en ambos subárboles; si recibes resultados en los dos lados, el actual es el LCA; si solo en uno, propaga ese; si el actual coincide con alguno de los buscados, retorna el actual.

**Usos**: distancia entre nodos, relaciones de parentesco (primos), rutas, etc.

---

## 6) Isomorfismo e identidad de árboles

* **Árboles idénticos**: misma **estructura** y mismos **valores** en cada posición correspondiente.
* **Árboles isomorfos**:

  * **Estructural**: misma forma si se permiten **flips** (intercambiar hijos izq/der en algunos nodos). Usado sin considerar datos.
  * **Con datos**: igual que el anterior pero exigiendo coincidencia de valores en las posiciones estructuralmente equivalentes.

**Nota**: comparar solo listas de recorridos (inorden+preorden) distingue identidad si **no hay duplicados**; con duplicados puede fallar. La comparación recursiva nodo a nodo es más robusta.

---

## 7) Parentescos: ancestros, hermano, tío, primos

* **Ancestros de `x`**: nodos en la ruta desde la raíz hasta el padre de `x` (inclusive, según definición).
* **Hermano de `x`**: el otro hijo del mismo padre.
* **Tío de `x`**: hermano del padre de `x`.
* **Primos de `x`**: hijos de los tíos de `x` (mismo nivel que `x`).

**Observaciones**: sin puntero a padre, se obtienen recorriendo desde la raíz y guardando la ruta.

---

## 8) Árboles AVL (autobalanceados)

**Idea**: mantener |FB| ≤ 1 en todos los nodos, donde **FB (factor de balance)** = altura(izq) − altura(der).

**Rotaciones**:

* **LL**: rotación simple a la **derecha**.
* **RR**: rotación simple a la **izquierda**.
* **LR**: rotación **izquierda** en el hijo izquierdo + **derecha** en el nodo.
* **RL**: rotación **derecha** en el hijo derecho + **izquierda** en el nodo.

**Inserción/Eliminación**: se hacen como en BST y luego se **rebalancea** aplicando la rotación del caso detectado con FB.

**Complejidad**: O(log n) en búsqueda, inserción y eliminación (por mantener altura O(log n)).

---

## 9) Árboles B (B-Tree)

**Uso**: índices en disco / bases de datos. Generalizan el BST permitiendo **múltiples claves por nodo** y **más de dos hijos**. Mantienen baja altura para lecturas en bloque.

**Parámetro**: `t` (grado mínimo).

* Cada nodo (excepto la raíz) tiene entre `t−1` y `2t−1` claves, y entre `t` y `2t` hijos.
* Todas las hojas están a la **misma profundidad**.

**Operaciones**:

* **Búsqueda**: lineal/binaria dentro del nodo; baja por el hijo adecuado. O(t · h), con `h` muy pequeña.
* **Inserción**: si el nodo hijo está **lleno** al bajar, se **divide (split)** primero: se promueve la clave media al padre y el nodo se parte en dos. Luego se inserta donde corresponda.
* **Eliminación**: varios casos. Si se elimina en interno, se reemplaza por predecesor/sucesor; si un hijo queda con **menos de `t−1` claves**, se **toma** de un hermano (borrow) o se **fusiona** (merge). La altura se mantiene.

**Validación**: comprobar rangos de claves por nodo, ordenamiento interno, cantidad mínima/máxima de claves/hijos según `t`, y altura uniforme de hojas.

---

## 10) Complejidades resumidas

| Estructura            | Búsqueda  | Inserción | Eliminación | Notas                                      |
| --------------------- | --------- | --------- | ----------- | ------------------------------------------ |
| Árbol binario general | O(n)      | O(n)      | O(n)        | Sin orden, depende del recorrido/ubicación |
| BST (no balanceado)   | O(h)      | O(h)      | O(h)        | h puede ser O(n) en el peor caso           |
| AVL                   | O(log n)  | O(log n)  | O(log n)    | Requiere rotaciones y mantener alturas     |
| Árbol B               | O(logₘ n) | O(logₘ n) | O(logₘ n)   | m≈2t (número de hijos), altura muy baja    |

---

## 11) Casos borde y errores comunes

* **Confundir mínimo del subárbol** con **sucesor**: el mínimo del subárbol derecho **sí** es el sucesor; pero si no hay derecho, hay que ir a **ancestros**.
* **Modificar punteros al buscar**: las búsquedas **no** cambian `izq/der`.
* **Altura/nivel**: decide y documenta si la raíz tiene nivel 0 y altura del vacío es 0 o -1; usa la misma convención en todo.
* **Comparar solo recorridos para identidad** con duplicados: puede fallar; mejor comparar recursivamente estructura+datos.
* **Validar BST** solo localmente: hay que respetar el **rango global** heredado, no solo comparar con el padre.
* **Eliminar en BST** sin cubrir los 3 casos: provoca pérdidas o desorden del árbol.

---

## 12) Checklist de práctica para el parcial

* Recorridos: pre/in/post recursivos e **iterativos** (stack/queue) y por **niveles**.
* Propiedades: `altura`, `tamaño`, `contarHojas`, `anchoMáximo`, `esCompleto`, `esLleno`, `esPerfecto`, `esBalanceadoPorAltura`.
* BST: `insertar`, `buscar`, `eliminar` (3 casos), `mínimo/máximo`, `sucesor/predecesor`, `validarBST`, `k-ésimo menor`, `rango`, `floor/ceil`.
* LCA: versión BST y versión general.
* Parentescos: hermano, **tío**, primos; ruta y ancestros.
* Identidad e isomorfismo (con y sin flips, con y sin datos).
* AVL: cálculo de FB, 4 casos de rotación, insertar/eliminar con rebalanceo.
* Árbol B: significado de `t`, split, borrow, merge, y validación.

---

## 13) Glosario rápido

* **FB (factor de balance)**: altura(izq) − altura(der); en AVL debe estar en {−1, 0, 1}.
* **Split** (B-Tree): dividir un nodo lleno, promoviendo la clave media al padre.
* **Borrow** (B-Tree): tomar una clave de un hermano para subir el tamaño del nodo por debajo del mínimo.
* **Merge** (B-Tree): fusionar dos nodos hermanos cuando ninguno puede prestar y el padre lo permite.
* **LCA**: ancestro común más bajo.
* **Full/Complete/Perfect**: lleno/completo/perfecto (ver §3).

---

## 14) Mini‑quiz (auto‑chequeo)

1. ¿Cuál es la salida del inorden de un BST y para qué sirve?
2. Explica los 3 casos de eliminación en BST.
3. ¿Cómo obtienes el **sucesor** de un nodo sin hijo derecho?
4. Diferencia entre árbol **completo** y **perfecto**.
5. ¿Qué garantiza un AVL respecto a la altura?
6. En un Árbol B con grado mínimo `t`, ¿cuántas claves puede tener un nodo interno?
7. Define LCA y da un método para hallarlo en BST.
8. ¿Por qué comparar solo inorden y preorden puede fallar con duplicados?
9. ¿Qué es el **tío** de un nodo y cómo lo obtienes sin puntero a padre?
10. ¿Qué significa **validar** que un árbol es BST?

---

> Sugerencia final de estudio: practica cada operación con árboles pequeños dibujados a mano; confirma pasos con recorridos (especialmente al eliminar) y anota **casos borde** (nodo máximo/mínimo, un solo hijo, árbol vacío, árbol degenerado).
