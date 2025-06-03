package um.edu.uy.tads.stack;


import um.edu.uy.List.Node;

public class Stack<T> implements MyStack<T> {
    Node<T> base;
    Node<T> top; //ultimo de arriba
    int size;

    public Stack() {
        this.base = null;
        this.top = null;
        size = 0;
    }

    // elimina el elemento que esta en el top de la pila
    @Override
    public void pop() throws EmptyStackException {
        if (isEmpty()){
            throw new EmptyStackException();
        }
        /* si base y top son iguales es porq tengo solo un elemento en
        mi pila entonces borro ambos */
        if(base == top){
            base = null;
            top = null;
        }
        // si hay mas de un nodo, lo siguiente:
        else {
            Node<T> temp = base;
            // recorro desde la base hasta el anterior de top
            while(temp.getNext() != top) {
                temp = temp.getNext();  //temp se mueve al siguiente nodo en cada iteracion
            }
            // al salir del while temp es el nodo junto antes de top
            // temp se convierte en el nuevo top
            top = temp;
            top.setNext(null); //
        }

        size--;
    }

    // obtengo el elemento top
    @Override
    public Node<T> top() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return top;
    }

    // inserto nuevo valor
    @Override
    public void push(T element){
        Node<T> nuevo = new Node<T>(element);
        if (base == null){
            base = nuevo;
            top = nuevo;
        } else {
            top.setNext(nuevo);
            top = nuevo;
            top.setNext(null);
        }
        size++;
    }

    @Override
    public boolean isEmpty() {
        return base == null;
    }

    @Override
    public void makeEmpty() throws EmptyStackException {
        while (!isEmpty()){
            this.pop();
        }
        size = 0;
    }
    @Override
    public void print() {
        Node<T> temp = base;
        while (temp != null) {
            System.out.println(temp.getValue());
            temp = temp.getNext();
        }
    }

    public void printBase() {
        System.out.println(base.getValue());
    }

    public void printTop() {
        System.out.println(top.getValue());
    }
}