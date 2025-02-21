import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * Game class to handle the game logic and state.
 * This class is responsible for managing the game state, including the list of all moves taken in the game.
 * @author Michael Madrid
 */

public class Game {
    
    /**
     * Constructor for the Game class. Private to prevent instantiation.
     */
    private Game () {}

    /**
     * Static variable indicating all moves taken in the game.
     */
    public static ArrayList<int[]> allMovesTaken = new ArrayList<int[]>();

    /**
     * Method to add a move to the list of all moves taken in the game.
     */
    public static void addMove (int[] move) {
        allMovesTaken.add(move);
    }

    /**
     * Method to update the position of a piece on the board.
     * @param positionMap The current position map of the board.
     * @param move The move to be made.
     * @return The updated position map of the board.
     */
    public static Map<Integer, Integer> updatePosition (Map<Integer, Integer> positionMap, int[] move) {
        int startingIndex = move[0];
        int endingIndex = move[1];
        if(positionMap.containsKey(startingIndex)) {
            if (Moves.enPassantDiscardMap.size() > 0) {
                if (isEnPassant(move)) {
                    System.out.println(Moves.enPassantDiscardMap);
                    System.out.println(move);
                    System.out.println("Removing Index " + Moves.enPassantDiscardMap.get(Moves.enPassantDiscardMap.keySet().iterator().next()));
                    positionMap.remove(Moves.enPassantDiscardMap.get(Moves.enPassantDiscardMap.keySet().iterator().next()));
                }
            }    
            int piece = positionMap.get(startingIndex);
            positionMap.remove(startingIndex);
            positionMap.put(endingIndex, piece);
        }

        return positionMap;
    }

    private static boolean isEnPassant (int[] move) {
        return Arrays.equals(move, Moves.enPassantDiscardMap.keySet().iterator().next());
    }


    /**
     * Method to get the last move taken in the game.
     * @return The last move taken in the game.
     */
    public static int[] getLastMove () {
        return allMovesTaken.get(allMovesTaken.size() - 1);
    }

    /**
     * Method to check if the last move was a double push.
     */
    public static boolean isDoublePush (int pawnIndex) {
        int[] LastMove = getLastMove();
        return Math.abs(LastMove[0] - LastMove[1]) == 16 && LastMove[1] == pawnIndex;
    }

}
