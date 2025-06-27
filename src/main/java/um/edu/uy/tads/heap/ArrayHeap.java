package um.edu.uy.tads.heap;

public class ArrayHeap<T extends Comparable<T>> implements ArrayHeap_interface<T> {

    private T[] heap;
    private int size;
    private boolean isMaxHeap;
    private static final int default_capacity = 16;

    public ArrayHeap(boolean isMaxHeap) {
        this(default_capacity, isMaxHeap);
    }

    public ArrayHeap(int capacidad, boolean isMaxHeap) {
        heap = (T[]) new Comparable[capacidad + 1];
        size = 0;
        this.isMaxHeap = isMaxHeap;
    }

    private void swap(int a, int b){
        T temp = heap[a];
        heap[a] = heap[b];
        heap[b] = temp;
    }

    private boolean compare(T hijo, T padre){
        return isMaxHeap ? hijo.compareTo(padre) > 0 :hijo.compareTo(padre)<0;
    }


    @Override
    public void insert(T elemento) {
        if (size + 1 >= heap.length){
            resize();
        }
        size++;
        heap[size] = elemento;
        heapUp(size);
    }

    private void heapUp(int i){
        while (i>1) {
            int padre = i / 2;
            if (compare(heap[i], heap[padre])) {
                swap(i, padre);
                i = padre;
            } else {
                break;
            }
        }
    }

    @Override
    public T delete() {
        if (size == 0) return null;

        T raiz = heap[1];
        heap[1] = heap[size];
        size--;
        heapDown(1);
        return raiz;
    }

    private void heapDown(int i){
        while (2*i <= size){
            int hijoIzq = 2 * i;
            int hijoDer = 2 * i + 1;
            int hijoMayor = hijoIzq;

            if (hijoDer <= size && compare(heap[hijoDer], heap[hijoIzq])){
                hijoMayor = hijoDer;
            }

            if (compare(heap[hijoMayor], heap[i])){
                swap(i, hijoMayor);
                i = hijoMayor;
            } else{
                break;
            }
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Heap: ");
        for (int i = 1; i <= size; i++) {
            sb.append(heap[i]).append(" ");
        }
        return sb.toString();
    }

    public T peek() {
        if (size == 0) {
            return null;
        }
        return heap[1];
    }

    private void resize() {
        int nuevaCapacidad = heap.length * 2;
        T[] nuevoHeap = (T[]) new Comparable[nuevaCapacidad];
        for (int i = 1; i <= size; i++) {
            nuevoHeap[i] = heap[i];
        }
        heap = nuevoHeap;
    }

    public T get(int i) {                  //cambiar nombre de i?
        return heap[i];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
