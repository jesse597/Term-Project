
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Tree<E> {

    Node<E> root;

    public Tree(final Node<E> r) {
        root = r;
    }

    //Adds a specified child node to a specified parent node
    //Returns childNode for use in Trie.addWord
    public final Node<Character> addChild (final Node<E> parentNode, final Node<Character> childNode) {
        final int index = childNode.getElement() - 97;
        parentNode.getChildren()[index] = childNode;
        return childNode;
    }

    //Gets children linked list from a node; refers to the Node method getChildren
    public final Node<Character>[] getChildren (final Node<E> node) {
        return node.getChildren();
    }
}
