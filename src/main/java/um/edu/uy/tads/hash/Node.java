package um.edu.uy.tads.hash;
import java.util.Objects;

public class Node<K, T> {
    private T value;
    private K key;

    public Node(K key, T value) {
        this.value = value;
        this.key = key;
    }
    public T getValue() {
        return value;
    }

    public K getKey() {
        return key;
    }

    // comparamos por clase
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node<?, ?> node)) return false;
        return Objects.equals(key, node.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}

