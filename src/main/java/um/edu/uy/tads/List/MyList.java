package um.edu.uy.tads.List;

public interface MyList<T> {
    void add(T value);
    void addFirst(T value);

    T remove(int position);//falta agregar que tire una exception
    T removeValue(T value);
    T removeLast();

    T get(int index);
    T getValue(T value);

    boolean contains(T value);

    void printList();

    int size();

    void add(int index, T value);
}
