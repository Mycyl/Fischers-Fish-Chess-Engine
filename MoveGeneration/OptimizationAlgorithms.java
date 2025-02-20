import java.util.ArrayList;

public class OptimizationAlgorithms {
    
    public static void insertionSortPieceList(ArrayList<String> words) {
        for (int i = 1; i < words.size(); i++) {
            int j = i;
            while (!(words.get(j - 1).compareTo(words.get(j)) < 0)) {
                swap (words, j - 1, j);
                j--;
                if (j == 0) {
                    break;
                }
            }
        }
}


    private static void swap (ArrayList<String> elements, int swap1, int swap2) {
        String temp = elements.get(swap1);
        elements.set(swap1, elements.get(swap2));
        elements.set(swap2, temp);
    }

    public boolean pieceInArray(String word, ArrayList<String> pieces) { // binary search

        int leftIdx = 0;   
        int rightIdx = pieces.size() - 1; 
     
        while (!(leftIdx > rightIdx)) { 
            int middleIdx = (leftIdx + rightIdx) / 2;  
            String wordAtMid = pieces.get(middleIdx);
            if (word.compareTo(wordAtMid) == 0) {
                return true;
            } else if (word.compareTo(wordAtMid) > 0) {
                leftIdx = middleIdx + 1;
            } else if (word.compareTo(wordAtMid) < 0) {
                rightIdx = middleIdx - 1;
            }
        }
        return false;
     }
     
     


}
