package um.edu.uy.tads.stack;

import um.edu.uy.List.Node;


public interface MyStack<T> {
    void pop() throws EmptyStackException;
    Node<T> top() throws EmptyStackException;
    void push(T element);
    boolean isEmpty();
    void makeEmpty() throws EmptyStackException;

    void print();
}

