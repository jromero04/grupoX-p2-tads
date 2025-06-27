package um.edu.uy.tads.heap;

public interface ArrayHeap_interface<T extends Comparable<T>> {
    void insert(T elemento);

    T delete();

    int size();

    String toString();

    T peek();

    boolean isEmpty();
}
