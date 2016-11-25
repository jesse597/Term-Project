
import java.util.LinkedList;

/*
 * Defines node with setters and getters for its element, parent,
 * list of children, and depth
 */
public class Node<E> {
    E element;
    Node<E> parent;
    LinkedList<Node<E>> children;
    int depth;
    public Node(final E e, final Node<E> p) {
        element = e;
        parent = p;
        children = new LinkedList<Node<E>>();
    }

    public final LinkedList<Node<E>> getChildren () {
        return children;
    }

    public final void setParent (final Node<E> p) {
        parent = p;
    }

    public final Node<E> getParent () {
        return parent;
    }

    public final E getElement () {
        return element;
    }

    public boolean isRoot () {
        return getParent() == null;
    }

    public boolean isExternal () {
        return getChildren() == null;
    }
}
