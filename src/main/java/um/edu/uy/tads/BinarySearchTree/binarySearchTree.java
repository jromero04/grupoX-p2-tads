package um.edu.uy.tads.BinarySearchTree;


import um.edu.uy.tads.List.MyLinkedList;
import um.edu.uy.tads.List.MyList;
import um.edu.uy.tads.queue.EmptyQueueException;
import um.edu.uy.tads.queue.Queue;
import um.edu.uy.tads.stack.EmptyStackException;
import um.edu.uy.tads.stack.Stack;

public class binarySearchTree<K extends Comparable<K>, T> implements MyTree<K, T> {
    private Node<K, T> root;

    public Node<K, T> getRoot() {
        return root;
    }

    public binarySearchTree() {
        this.root = null;
    }

    @Override
    public T find(K key) {
        return findRecursive(root, key);
    }

    private T findRecursive(Node<K, T> current, K key) {
        // Caso base
        if (current == null) {
            return null; // Arbol vacío o ya recorri todo el camino donde deveria estar no se encontró la clave
        }
        int comparison = key.compareTo(current.getKey());
        // Son iguales, entonces:
        if (comparison == 0) {
            return current.getData(); // Clave encontrada
        }
        // Es menor, entonces voy al nodo izquierdo
        else if (comparison < 0) {
            return findRecursive(current.getLeftChild(), key); // Buscar en el subárbol izquierdo
        }
        // Es mayor, entonces voy al nodo derecho
        else {
            return findRecursive(current.getRightChild(), key); // Buscar en el subárbol derecho
        }
    }

    @Override
    public void insert(K key, T data, K parentKey) {
        root = insertRecursive(root, key, data);
    }

    private Node<K, T> insertRecursive(Node<K, T> current, K key, T data) {
        // Caso base: si llego a la posicion null es el lugar correcto para insertar
        if (current == null) {
            return new Node<>(key, data); // Creo un nuevo nodo
        }
        int comparison = key.compareTo(current.getKey());
        if (comparison < 0) {
            current.setLeftChild(insertRecursive(current.getLeftChild(), key, data)); // Insertar en el subárbol izquierdo
        } else if (comparison > 0) {
            current.setRightChild(insertRecursive(current.getRightChild(), key, data)); // Insertar en el subárbol derecho
        }
        return current; // Retornar el nodo actual
    }

    @Override
    public void delete(K key) {
        root = deleteRecursive(root, key);
    }

    private Node<K, T> deleteRecursive(Node<K, T> current, K key) {
        // Caso base
        if (current == null) {
            return null; // Nodo no encontrado
        }
        int comparison = key.compareTo(current.getKey());
        if (comparison < 0) {
            current.setLeftChild(deleteRecursive(current.getLeftChild(), key)); // Buscar en el subárbol izquierdo
        } else if (comparison > 0) {
            current.setRightChild(deleteRecursive(current.getRightChild(), key)); // Buscar en el subárbol derecho
        } else {
            // Nodo encontrado - las keys son iguales
            if (current.getLeftChild() == null && current.getRightChild() == null) {
                return null; // Caso 1: Nodo hoja
            }
            // Caso 2: Un solo hijo (derecho)
            else if (current.getLeftChild() == null) {
                return current.getRightChild();
            }
            // Caso 2: Un solo hijo (izquierdo)
            else if (current.getRightChild() == null) {
                return current.getLeftChild();
            } else {
                // Caso 3: Dos hijos
                /* Reemplazar el dato con el del nodo menor del subárbol derecho (el nodo que está mas a
                la derecha del subárbol) y remover dicho nodo
                 */
                Node<K, T> smallestNode = findSmallestNode(current.getRightChild());
                // Reemplazamos la clave y los datos del nodo actual con los del sucesor
                current.setKey(smallestNode.getKey());
                current.setData(smallestNode.getData());
                // Elimino el sucesor original del subarbol derecho
                current.setRightChild(deleteRecursive(current.getRightChild(), smallestNode.getKey())); // Eliminar el nodo más pequeño del subárbol derecho
            }
        }
        return current;
    }


    private Node<K, T> findSmallestNode(Node<K, T> current) {
        // Solamente a la izq. porq las claves mas chicas estan a la izq.
        while (current.getLeftChild() != null) {
            current = current.getLeftChild();
        }
        // Cuando no hay mas hijos izquierdos, encontre el nodo mas chico
        return current;
    }

    // Comienzo ejercicio 2
    // Cuento los nodos del arbol
    @Override
    public int size() {
        return sizeRecursive(root);
    }

    private int sizeRecursive(Node<K, T> node) {
        if (node == null) {
            return 0; // Árbol vacío no tiene nodos
        }
        // Cuento el nodo actual (1) + los nodos izq. + los nodos derechos
        return 1 + sizeRecursive(node.getLeftChild()) + sizeRecursive(node.getRightChild());
    }


    // Cuento la cantidad de hojas
    @Override
    public int countLeaf() {
        return countLeafRecursive(root);
    }

    private int countLeafRecursive(Node<K, T> node) {
        if (node == null) {
            return 0; // No hay hojas en un árbol vacío
        }
        if (node.getLeftChild() == null && node.getRightChild() == null) {
            return 1; // Nodo hoja encontrado
        }
        // Sumar hojas de subárbol izquierdo y derecho
        return countLeafRecursive(node.getLeftChild()) + countLeafRecursive(node.getRightChild());
    }


    // Cuento los nodos que tienen ambos hijos no nulos
    @Override
    public int countCompleteElements() {
        return countCompleteElementsRecursive(root);
    }

    private int countCompleteElementsRecursive(Node<K, T> node) {
        if (node == null) {
            return 0; // No hay nodos completos en un árbol vacío
        }
        int count = 0;
        if (node.getLeftChild() != null && node.getRightChild() != null) {
            count = 1; // Nodo con ambos hijos no nulos
        }
        return count + countCompleteElementsRecursive(node.getLeftChild()) + countCompleteElementsRecursive(node.getRightChild());
    }

    /// Comienzo ejercicio 3
    @Override
    public MyList<K> inOrder() {
        MyLinkedList<K> result = new MyLinkedList<>();
        inOrderRecursive(root, result);
        return result;
    }

    private void inOrderRecursive(Node<K, T> node, MyLinkedList<K> result) {
        if (node == null) {
            return;
        }
        // Recorrer subárbol izquierdo
        inOrderRecursive(node.getLeftChild(), result);
        // Visitar la raíz
        result.addLast(node.getKey());
        // Recorrer subárbol derecho
        inOrderRecursive(node.getRightChild(), result);
    }

    @Override
    public MyList<K> preOrder() {
        MyLinkedList<K> result = new MyLinkedList<>();
        preOrderRecursive(root, result);
        return result;
    }

    private void preOrderRecursive(Node<K, T> node, MyLinkedList<K> result) {
        if (node == null) {
            return;
        }
        // Visitar la raíz
        result.addLast(node.getKey());
        // Recorrer subárbol izquierdo
        preOrderRecursive(node.getLeftChild(), result);
        // Recorrer subárbol derecho
        preOrderRecursive(node.getRightChild(), result);
    }

    @Override
    public MyList<K> postOrder() {
        MyLinkedList<K> result = new MyLinkedList<>();
        postOrderRecursive(root, result);
        return result;
    }

    private void postOrderRecursive(Node<K, T> node, MyLinkedList<K> result) {
        if (node == null) {
            return;
        }
        // Recorrer subárbol izquierdo
        postOrderRecursive(node.getLeftChild(), result);
        // Recorrer subárbol derecho
        postOrderRecursive(node.getRightChild(), result);
        // Visitar la raíz
        result.addLast(node.getKey());
    }

    ///  Ejercicio 4
    @Override
    public MyList<K> levelOrder() throws EmptyQueueException {
        MyLinkedList<K> result = new MyLinkedList<>();
        if (root == null) {
            return result; // Si el árbol está vacío, retorno una lista vacía
        }

        Queue<Node<K, T>> queue = new Queue<>();
        queue.enqueue(root); // Encolar la raíz

        while (!queue.isEmpty()) {
            Node<K, T> current = queue.dequeue(); // Desencolar el nodo actual
            result.addLast(current.getKey()); // Agregar la clave del nodo a la lista de resultados

            // Encolar los hijos izquierdo y derecho si no son nulos
            if (current.getLeftChild() != null) {
                queue.enqueue(current.getLeftChild());
            }
            if (current.getRightChild() != null) {
                queue.enqueue(current.getRightChild());
            }
        }

        return result; // Retornar la lista con el recorrido por nivel
    }


    ///  Ejercicio 5
    @Override
    public void loadPostFijaExpression(String sPostFija) throws EmptyStackException {
        Stack<Node<String, String>> stack = new Stack<>();

        for (char ch : sPostFija.toCharArray()) {
            String symbol = String.valueOf(ch);

            if (esOperando(symbol)) {
                // Crear nodo hoja
                Node<String, String> nodo = new Node<>(symbol, symbol);
                stack.push(nodo);
            } else if (esOperador(symbol)) {
                // Validar que haya suficientes operandos en la pila
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException("Expresión posfija inválida: faltan operandos.");
                }
                Node<String, String> right = stack.top().getValue(); // hijo derecho
                stack.pop();

                if (stack.isEmpty()) {
                    throw new IllegalArgumentException("Expresión posfija inválida: faltan operandos.");
                }
                Node<String, String> left = stack.top().getValue(); // hijo izquierdo
                stack.pop();

                // Crear nodo operador y setear hijos
                Node<String, String> operador = new Node<>(symbol, symbol);
                operador.setLeftChild(left);
                operador.setRightChild(right);

                // Apilar nuevo subárbol
                stack.push(operador);
            } else {
                throw new IllegalArgumentException("Símbolo inválido en la expresión: " + symbol);
            }
        }

        // Validar que solo quede un nodo en la pila
        if (stack.isEmpty() || stack.top().getNext() != null) {
            throw new IllegalArgumentException("Expresión posfija inválida: demasiados operandos.");
        }

        // El último nodo en la pila es la raíz del árbol
        this.root = (Node<K, T>) stack.top().getValue();
    }

    private boolean esOperando(String s) {
        return s.matches("[a-zA-Z0-9]"); // Letras o dígitos
    }

    private boolean esOperador(String s) {
        return s.matches("[+\\-*/]"); // Operadores aritméticos básicos
    }

}