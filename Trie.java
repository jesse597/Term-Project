import java.util.ArrayList;

public class Trie extends Tree<String> {

    //Uses super constructor to set root, and set root element to blank
    public Trie (Node<String> r) {
        super(r);
        root.setElement("");
    }

    //Adds a word to the trie by adding the letter to the trie if not present
    public void addWord (final String s, boolean isFromMessages) {
        Node<String> parent = root;
        for (int i = 0; i < s.length(); i++) {
            boolean letterFound = false;
            for (final Node<String> n : parent.getChildren()) {
                if (n.getElement().charAt(0) == s.charAt(i)) {
                    parent = n;
                    letterFound = true;
                    break;
                }
            }
            if (!letterFound) {
                parent = addChild(parent, new Node(((Character) s.charAt(i)).toString(), parent));
            }
        }
        parent.setEndOfWord(true);
        if (isFromMessages) {
            parent.incrementFrequency();
        }
    }

    //Finds all words that could end with entered characters (modify to only return best 3)
    public String[] findGuesses (final String s) {
        final SortedPriorityQueue<Integer, String> words = new SortedPriorityQueue();
        Node<String> current = root;
        for (int i = 0; i < s.length(); i++) {
            for (final Node<String> n : current.getChildren()) {
                if (n.getElement().charAt(0) == s.charAt(i)) {
                    current = n;
                    break;
                }
            }
        }
        
        postOrder (current, words);
        final String[] guesses = new String[3];
        for (int i = Math.min(2, words.size() - 1); i >= 0 ; i--) {
            guesses[i] = words.removeMin().getValue();
        }
        return guesses;
    }

    //Traverses through all nodes, and adds a word to the words arraylist if the node is external
    public void postOrder (final Node<String> v, final SortedPriorityQueue words) {
        for (final Node<String> w : v.getChildren()) {
            postOrder(w, words);
        }

        if (v.isEndOfWord()) {
            constructWord (v, words);
        }
    }

    //Construct a word to add to words arraylist by adding each letter up to the root
    public void constructWord (final Node<String> v, final SortedPriorityQueue<Integer, String> words) {
        final int frequency = v.getFrequency();
        Node<String> current = v;
        String word = "";
        while (!current.isRoot()) {
            word = current.getElement() + word;
            current = current.getParent();
        }

        if (words.size() < 3) {
            words.insert(frequency, word);
        } else if (frequency >= (Integer) words.min().getKey()) {
            words.removeMin();
            words.insert(frequency, word);
        }
    }
}
