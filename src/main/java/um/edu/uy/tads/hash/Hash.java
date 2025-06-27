package um.edu.uy.tads.hash;

import um.edu.uy.tads.List.MyLinkedList;
import um.edu.uy.tads.List.MyList;
import um.edu.uy.tads.hash.Exceptions.InvalidHashKey;

import java.util.Arrays;

public class Hash<K, T> implements MyHash<K, T> {
    private Node<K, T>[] array;
    private int size, counter;
    private static final int DEFAULT_SIZE = 16; // Tamaño inicial por defecto

    public Hash() {
        this.size = DEFAULT_SIZE;
        this.counter = 0;
        array = new Node[size];
        Arrays.fill(array, null);
    }

    @Override
    public int getSize() {
        return size;
    }

    public int getCounter() {
        return counter;
    }

    @Override
    public int hashFunction(K key) {
        int hash = 5381;
        String keyStr = key.toString();
        for (int i = 0; i < keyStr.length(); i++) {
            hash = ((hash << 5) + hash) + keyStr.charAt(i); // hash * 33 + c
        }
        return Math.abs(hash % size);
    }

    @Override
    public void add(K key, T value) {
        if (checkCapacity()) {
            reSize();
        }
        int index = findSlot(key);
        if (array[index] == null) {
            array[index] = new Node<>(key, value);
            counter++;
        } else {
            array[index] = new Node<>(key, value); // reemplaza valor si ya existe la key
        }

    }

    @Override
    public void remove(K key) throws InvalidHashKey {
        int index = findSlotForKey(key);
        if (array[index] == null) {
            throw new InvalidHashKey();
        }
        array[index] = null;
        counter--;
        reOrganize();
    }

    @Override
    public T search(K key) throws InvalidHashKey {
        int index = findSlotForKey(key);
        if (array[index] == null) {
            throw new InvalidHashKey();
        }
        return array[index].getValue();
    }

    @Override
    public boolean checkCapacity() {
        return counter >= size * 0.60;
    }

    @Override
    public void reSize() {
        int newSize = getNextPrime(size * 2);
        Node<K, T>[] oldArray = array;
        array = new Node[newSize];
        size = newSize;
        counter = 0;
        for (Node<K, T> node : oldArray) {
            if (node != null) {
                add(node.getKey(), node.getValue());
            }
        }
    }

    @Override
    public void reOrganize() {
        Node<K, T>[] oldArray = array;
        array = new Node[size];
        counter = 0;
        for (Node<K, T> node : oldArray) {
            if (node != null) {
                add(node.getKey(), node.getValue());
            }
        }
    }

    private int findSlot(K key) {
        int index = hashFunction(key);
        while (array[index] != null && !array[index].getKey().equals(key)) {
            index = (index + 1) % size;
        }
        return index;
    }

    private int findSlotForKey(K key) throws InvalidHashKey {
        int index = hashFunction(key);
        int startIndex = index;
        while (array[index] != null && !array[index].getKey().equals(key)) {
            index = (index + 1) % size;
            if (index == startIndex) {
                throw new InvalidHashKey();
            }
        }
        return index;
    }

    private int getNextPrime(int number) {
        while (true) {
            if (isPrime(number)) {
                return number;
            }
            number++;
        }
    }

    private boolean isPrime(int number) {
        if (number <= 1) return false;
        if (number <= 3) return true;
        if (number % 2 == 0 || number % 3 == 0) return false;
        for (int i = 5; i * i <= number; i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) return false;
        }
        return true;
    }

    @Override
    public void printHash() {
        for (int i = 0; i < size; i++) {
            if (array[i] != null) {
                System.out.println(array[i].getValue() + " " + array[i].getKey());
            } else {
                System.out.println("-");
            }
        }
    }

    @Override
    public boolean contains(K key) {
        try {
            int index = findSlotForKey(key);
            return array[index] != null;
        } catch (InvalidHashKey e) {
            return false;
        }
    }

    @Override
    public Node<K, T>[] getArray() {
        return array;
    }

    public void replace(K key, T value) throws InvalidHashKey {
        int index = findSlotForKey(key);
        if (array[index] == null) {
            throw new InvalidHashKey();
        }
        array[index] = new Node<>(key, value);
    }

    @Override
    public MyList<T> getValues() {
        MyList<T> lista = new MyLinkedList<>();
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                lista.add(array[i].getValue());
            }
        }
        return lista;
    }

    @Override
    public MyList<K> keys() {
        MyList<K> listaClaves = new MyLinkedList<>();
        for (Node<K, T> nodo : array) {
            if (nodo != null) {
                listaClaves.add(nodo.getKey());
            }
        }
        return listaClaves;
    }

}
