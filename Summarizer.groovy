import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextArea
import javax.swing.UIManager
import java.awt.BorderLayout
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor;
int getLevenshteinDistance(String s, String t) {
    // degenerate cases
    if (s == t) return 0;
    if (s.length() == 0) return t.length();
    if (t.length() == 0) return s.length();

    // create two work vectors of integer distances
    int[] v0 = new int[t.length() + 1];
    int[] v1 = new int[t.length() + 1];

    // initialize v0 (the previous row of distances)
    // this row is A[0][i]: edit distance for an empty s
    // the distance is just the number of characters to delete from t
    for (int i = 0; i < v0.length; i++)
        v0[i] = i;

    for (int i = 0; i < s.length(); i++) {
        // calculate v1 (current row distances) from the previous row v0

        // first element of v1 is A[i+1][0]
        //   edit distance is delete (i+1) chars from s to match empty t
        v1[0] = i + 1;

        // use formula to fill in the rest of the row
        for (int j = 0; j < t.length(); j++) {
            int cost = (s[i] == t[j]) ? 0 : 1;
            v1[j + 1] = Collections.min([v1[j] + 1, v0[j + 1] + 1, v0[j] + cost]);
        }

        // copy v1 (current row) to v0 (previous row) for next iteration
        for (int j = 0; j < v0.length; j++)
            v0[j] = v1[j];
    }
    return v1[t.length()];
}

List getBigrams(String s) {
    bigrams = []
    chars = s.toCharArray()
    for (int i = 0; i < chars.length - 1; i++) {
        bigrams << new String((char[]) chars[i, i + 1].toArray(new char[2]))
    }
    return bigrams
}

def getGramSimilarity(List bigram1, List bigram2) {
    int length = bigram1.size() + bigram2.size()
    int hitcount = 0
    bigram1.each { g1 ->
        bigram2.each {
            g2 ->
                if (g1 == g2 && g1.length() != 0) {
                    hitcount++
                }
        }

    }
    return (float) 2 * hitcount * 100 / length
}

def getBigramSimilarity(String s1, String s2) {
    return getGramSimilarity(getBigrams(s1), getBigrams(s2))
}

def getWordSimilarity(String sent1, String sent2) {
    return getGramSimilarity(Arrays.asList(sent1.trim().split(' ')), Arrays.asList(sent2.trim().split(' ')))
}

// Start from here .....
String data = null
try {
    data = (String) Toolkit.getDefaultToolkit()
            .getSystemClipboard().getData(DataFlavor.stringFlavor);

}catch (Throwable e){
    e.printStackTrace()
    System.exit(-1)
}

readLines = data.split("[.]|[?]|[!]")


Set stopWords = new HashSet(Arrays.asList(new File('/home/subh/stopwords.txt').text.split('\n')))


sentences = []
readLines.each {
    words = it.trim().split(' ')
    if (words.length <= 6 || words.length >= 60) {
        sentences << ''//add nothing, if I simply miss this statement , I am screwing up the index
        return
    }
    sentences << it
}


println "Number of statements:${sentences.size()}"
//Before we calculate the Levenstein distance , we need to remove the stop words for accuracy .
prunedSentences = []
sentences.each {
    StringBuilder sb = new StringBuilder()
    it.trim().split(' ').each { word ->
        word = word.toLowerCase()
        if (word.trim().length() > 0 && !stopWords.contains(word)) {
            sb.append(word).append(' ')
        }
    }
    prunedSentences << sb.toString()
}

//We need a matrix with 3 columns and n rows
matrix = []
//N-square complexity comparision of statements to get levenstein distance
docSet = new HashSet<>()
for (int i = 0; i < prunedSentences.size(); i++) {
    for (int j = 0; j < prunedSentences.size(); j++) {

        if (i != j) {
            if (!docSet.contains(Integer.toString(i) + Integer.toString(j))) {
                //matrix << new Tuple(i,j,getLevenshteinDistance(prunedSentences.get(i),prunedSentences.get(j)))
                similarity = getWordSimilarity(prunedSentences.get(i),prunedSentences.get(j))
                matrix << new Tuple(i,j,similarity)
                matrix << new Tuple(j,i,similarity)
                docSet << Integer.toString(i) + Integer.toString(j)
                docSet << Integer.toString(j) + Integer.toString(i)
            }

        }
    }
}
//now create a hashmap by summing all leveinstein values for each statement
map = [:]

matrix.each { it ->
    int sum = 0
    if (map.get(it[0]) != null) {
        sum = map.get(it[0]) + it[2]
    }
    map.put(it[0], sum)

}
values = new ArrayList(map.values())
Collections.sort(values)

Set stmts = new HashSet()
values.reverse().subList(0, (int) (map.size() * 0.2) + 1).each { it ->

    map.each { entry ->
        if (entry.value == it) {
            stmts.add(sentences[entry.key])
        }
    }
}

sb = new StringBuilder()
int i = 1
stmts.each {
    sb.append(i).append('. ').append(it.trim()).append('.\n')
    i++
}
UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
JFrame frame = new JFrame("Summary")
JPanel panel = new JPanel(new BorderLayout())
JTextArea textArea = new JTextArea(sb.toString(),20,100)
textArea.setEditable(false)
textArea.setLineWrap(true)

textArea.setFont(textArea.getFont().deriveFont(18f));
panel.add(new JScrollPane(textArea))
frame.contentPane.add(panel,BorderLayout.CENTER)
frame.pack()
frame.setVisible(true)
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)