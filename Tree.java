
import java.util.LinkedList;
import java.util.Queue;

public class Tree<E> {

    Node<E> root;

    public Tree(final Node<E> r) {
        root = r;
    }

    //Prints the node and all its children in preorder
    public final void preOrderPrint (final Node<E> node) {
        System.out.print(" " + node.getElement());
        for (final Node<E> child : node.getChildren()) {
            preOrderPrint(child);
        }
    }

    /*
     * Searches level by level for a node with the specified name,
     * starting from a specified node
     */
    public final Node<E> breadthFirstSearch (final Node<E> node, final String name) {
        final Queue<Node<E>> q = new LinkedList<Node<E>>();
        q.add(node);
        boolean found = false;
        while (!found && q.size() > 0) {
            final Node<E> p = q.remove();
            if (p.getElement().equals(name)) {
                found = true;
                return p;
            }
            if (!found) {
                for (final Node<E> child : p.getChildren()) {
                    q.add(child);
                }
            }
        }

        return null;
    }

    //Adds a specified child node to a specified parent node
    //Returns childNode for use in Trie.addWord
    public final Node<E> addChild (final Node<E> parentNode, final Node<E> childNode) {
        parentNode.getChildren().add(childNode);
        return childNode;
    }

    //Gets children linked list from a node; refers to the Node method getChildren
    public final LinkedList<Node<E>> getChildren (final Node<E> node) {
        return node.getChildren();
    }

    //Gets parent node from a child node; refers to the Node method getParent
    public final Node<E> getParent (final Node<E> node) {
        return node.getParent();
    }
}
