package um.edu.uy.tads.List;


public class MyLinkedList<T> implements MyList<T> {
    private Node<T> head; // primer elemetno de la lista
    private Node<T> last;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.last = null;
        size = 0;
    }

    /* el metodo add simplemente agrega al final, lo hago private para luego usarlo al
     implementar stack queue
      */
    @Override
    public void add(T value) {
        addLast(value);
    }


    @Override
    public void addFirst(T value) {
        Node<T> newNode = new Node<T>(value);

        if (head == null) {
            head = newNode;
            last = newNode;
        } else {
            newNode.setNext(head);
            head = newNode;
        }
        size++;
    }


    @Override
    public T remove(int position) {
        if (position < 0 || position >=size){
            throw new IndexOutOfBoundsException();
        }

        T value;

        if (position == 0){
            value = head.getValue();
            head = head.getNext();
        } else {
            Node<T> current = head;
            for (int i=0; i<position -1; i++){
                current = current.getNext();
            }

            Node<T> nodeToRemove = current.getNext();
            value = nodeToRemove.getValue();
            current.setNext(nodeToRemove.getNext());
        }

        size--;
        return value;
    }

    @Override
    public T removeValue(T value){
        if (head == null) return null;

        //si esta en el primer nodo
        if (head.getValue().equals(value)){
            T removedValue = head.getValue();
            head = head.getNext();
            size--;
            return removedValue;
        }

        //buscando el nodo ant. que lo tiene
        Node<T> current = head;
        while (current.getNext() !=null && !current.getNext().getValue().equals(value)){
            current = current.getNext();
        }

        //no lo encontro
        if (current.getNext() == null) return null;

        //lo encontro
        Node<T> nodeToRemove = current.getNext();
        T removedValue = nodeToRemove.getValue();
        current.setNext(nodeToRemove.getNext());
        size--;

        return removedValue;
    }

    @Override
    public T removeLast(){
        if (head == null) return null;

        if (head.getNext() == null){
            T value = head.getValue();
            head = null;
            size--;
            return value;
        }

        Node<T> current = head;
        while (current.getNext().getNext() != null){
            current = current.getNext();
        }

        T value = current.getNext().getValue();
        current.setNext(null);
        size--;
        return value;
    }

    @Override
    //metodo obtener el elemento en dicha posición
    public T get(int index) {
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }

        Node<T> current = head;
        for (int i =0; i<index; i++){
            current = current.getNext();
        }

        return current.getValue();
    }

    @Override
    public T getValue(T value){
        Node<T> current = head;

        while (current!=null){
            if (current.getValue().equals(value)){
                return current.getValue();
            }
            current = current.getNext();
        }

        return null; //si no lo encontro
    }


    @Override
    // Ejercicio 2
    public boolean contains(T value) {
        Node<T> temp = head;

        while (temp != null) {
            if (temp.getValue().equals(value)) {
                return true;
            }

            temp = temp.getNext();

        }
        return false;
    }


    @Override
    public void printList() {
        Node<T> temp = head;
        while (temp != null){
            System.out.println(temp.getValue());
            temp = temp.getNext();
        }
    }

    @Override
    public String toString(){
        String result = "";
        Node<T> current = head;

        while (current != null){
            result += current.getValue() + " ";
            current = current.getNext();
        }

        return result.trim();
    }

    @Override
    public int size() {
        return size;
    }

    public void addLast(T value) {
        if (head == null) {
            head = new Node<T>(value);
            this.last = this.head;
        } else {
            Node<T> newNode = new Node<T>(value);
            last.setNext(newNode);
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(int index, T value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> newNode = new Node<>(value);

        if (index == 0) {
            newNode.setNext(head);
            head = newNode;
            if (size == 0) {
                last = newNode;
            }
        } else {
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            newNode.setNext(current.getNext());
            current.setNext(newNode);

            if (newNode.getNext() == null) {
                last = newNode;
            }
        }

        size++;
    }

}
