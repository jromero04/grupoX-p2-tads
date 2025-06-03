package um.edu.uy.tads.List;

public class Node <T> {
    private T value;
    private Node<T> next;

    // Creo el constructor
    public Node(T value){
        this.value = value;
        this.next = null;
    }


    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
}
