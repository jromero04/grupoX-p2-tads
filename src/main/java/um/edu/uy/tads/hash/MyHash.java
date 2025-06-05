package um.edu.uy.tads.hash;


import um.edu.uy.tads.hash.Exceptions.InvalidHashKey;

public interface MyHash<K,T> {
    int hashFunction(K key);

    void add(K key, T value);

    void remove(K key) throws InvalidHashKey;

    T search(K key) throws InvalidHashKey;

    boolean checkCapacity();

    void reSize();

    void reOrganize();

    void printHash();

    Node<K, T>[] getArray();


}
