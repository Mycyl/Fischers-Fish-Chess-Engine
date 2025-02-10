import java.util.ArrayList;
import java.util.Map;

public class Game {
    
    public static ArrayList<int[]> allMovesTaken = new ArrayList<int[]>();
    //public static boolean kingMoved = Pieces.kingMoved(allMovesTaken, colorUp)

    public static void addMove (int[] move) {
        allMovesTaken.add(move);
    }

    public static Map<Integer, Integer> updatePosition (Map<Integer, Integer> positionMap, int[] move) {
        int startingIndex = move[0];
        int endingIndex = move[1];
        if(positionMap.containsKey(startingIndex)) {
            int piece = positionMap.get(startingIndex);
            positionMap.remove(startingIndex);
            positionMap.put(endingIndex, piece);
        }
        if (Moves.discardList.size() > 0) {
            for (int i = 0; i < Moves.discardIndexList.size(); i++) {
                int index = Moves.discardIndexList.get(i);
                positionMap.remove(index);
            }
        }
        return positionMap;
    }

    public static int[] getLastMove () {
        return allMovesTaken.get(allMovesTaken.size() - 1);
    }

    public static boolean isDoublePush (int pawnIndex) {
        int[] LastMove = getLastMove();
        return Math.abs(LastMove[0] - LastMove[1]) == 16 && LastMove[1] == pawnIndex;
    }

}
