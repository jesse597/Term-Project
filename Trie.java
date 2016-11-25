import java.util.ArrayList;

public class Trie extends Tree<String> {

    //Uses super constructor to set root, and set root element to blank
    public Trie (Node<String> r) {
        super(r);
        root.element = "";
    }

    //Adds a word to the trie by adding the letter to the trie if not present
    public void addWord (final String s) {
        Node<String> parent = root;
        for (int i = 0; i < s.length() - 1; i++) {
            for (final Node<String> n : parent.getChildren()) {
                if (n.getElement().equals(s.charAt(i))) {
                    parent = n;
                    break;
                }
            }
            parent = addChild(parent, new Node(s.charAt(i), parent));
        }
    }

    //Finds all words that could end with entered characters (modify to only return best 3)
    public ArrayList<String> findAll (final String s) {
        final ArrayList<String> words = new ArrayList<String>();
        Node<String> current = root;
        for (int i = 0; i < s.length() - 1; i++) {
            for (final Node<String> n : current.getChildren()) {
                if (n.getElement().equals(s.charAt(i))) {
                    current = n;
                    break;
                }
            }
        }
        
        preOrder (current, words);
        return words;
    }

    //Traverses through all nodes, and adds a word to the words arraylist if the node is external
    public void preOrder (final Node<String> v, final ArrayList<String> words) {
        if (v.isExternal()) {
            constructWord (v, words);
        }
        for (final Node<String> w : v.getChildren()) {
            preOrder(w, words);
        }
    }

    //Construct a word to add to words arraylist by adding each letter up to the root
    public void constructWord (final Node<String> v, final ArrayList<String> words) {
        Node<String> current = v;
        String word = "";
        while (!current.isRoot()) {
            word = current.getElement() + word;
            current = current.getParent();
        }
        words.add(word);
    }
}
