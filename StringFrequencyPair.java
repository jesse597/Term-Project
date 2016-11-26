
public class StringFrequencyPair {
    private String word;
    private int frequency;

    public StringFrequencyPair (final String s, final int f) {
        word = s;
        frequency = f;
    }

    public int getFrequency () {
        return frequency;
    }
    
    public String getWord () {
        return word;
    }
}
