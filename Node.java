
import java.util.ArrayList;

/*
 * Defines node with setters and getters for its element, parent,
 * list of children, and frequency
 */
public class Node<E> {
    private E element;
    private Node<E> parent;
    private ArrayList<Node<E>> children;
    private int frequency;
    private boolean isEndOfWord;
    public Node(final E e, final Node<E> p) {
        element = e;
        parent = p;
        children = new ArrayList<Node<E>>();
    }

    public final ArrayList<Node<E>> getChildren () {
        return children;
    }

    public final void setParent (final Node<E> p) {
        parent = p;
    }

    public final Node<E> getParent () {
        return parent;
    }

    public final void setElement (final E e) {
        element = e;
    }

    public final E getElement () {
        return element;
    }

    public boolean isRoot () {
        return parent == null;
    }

    public boolean isExternal () {
        return children.size() == 0;
    }

    public void incrementFrequency () {
        frequency++;
    }
    
    public int getFrequency () {
        return frequency;
    }
    
    public boolean isEndOfWord () {
        return isEndOfWord;
    }
    
    public void setEndOfWord (boolean e) {
        isEndOfWord = e;
    }
}