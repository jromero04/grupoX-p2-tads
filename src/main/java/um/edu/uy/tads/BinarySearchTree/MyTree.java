package um.edu.uy.tads.BinarySearchTree;

import um.edu.uy.tads.List.MyList;
import um.edu.uy.tads.queue.EmptyQueueException;
import um.edu.uy.tads.stack.EmptyStackException;


public interface MyTree<K, T> {
    T find(K key);
    void insert (K key, T data, K parentKey);
    void delete (K key);

    int size();
    int countLeaf();
    int countCompleteElements();

    MyList<K> inOrder();
    MyList<K> preOrder();
    MyList<K> postOrder();

    MyList<K> levelOrder() throws EmptyQueueException;

    void loadPostFijaExpression(String sPostFija) throws EmptyStackException;

}
