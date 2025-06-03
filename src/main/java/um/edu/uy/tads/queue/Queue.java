package um.edu.uy.tads.queue;


import um.edu.uy.tads.List.Node;

public class Queue<T> implements MyQueue<T> {
    Node<T> base;
    Node<T> top;
    int size;

    public Queue() {
        this.base = null;
        this.top = null;
    }

    // ingresa un elemento en la cola de la lista
    @Override
    public void enqueue(T element){
        Node<T> nuevo = new Node<T>(element);
        if(base == null){
            base = nuevo;
            top = nuevo;
        } else {
            top.setNext(nuevo);
            top = nuevo;
            top.setNext(null);
        }
        size++;

    }


    //elimina un elemento en la cabeza (frente) de la lista.
    @Override
    public T dequeue () throws EmptyQueueException {
        if (isEmpty()){
            throw new EmptyQueueException();
        }

        T value = base.getValue(); // Guardamos el valor para devolverlo
        base = base.getNext(); // Avanzamos la base al siguiente nodo

        if (base == null){
            top = null;
        }
        size--;
        return value;

    }

    public boolean isEmpty(){
        return base == null;
    }

}
