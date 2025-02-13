import java.util.ArrayList;

/**
 * Legalization class is responsible for checking if a move is legal.
 * @author Michael Madrid
 * 
 */

public class Legalization {
    
    /**
     * Checks if the king is in check. This is done by checking if the king is in 
     * the line of sight of any of the opponent's pieces.
     * @param color The color of the king to check.
     * @param board The current board state.
     * @return True if the king is in check, false otherwise.
     * <p>
     * @see ReverseRay#reverseRayKingList(int, Board)
     */
    public static boolean isKingInCheck (int color, Board board) {
        Board tempBoard = new Board(board.positionToFEN(board.getPositionMap()));
        ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> reverseRayKingList = ReverseRay.reverseRayKingList(color, tempBoard);
        for (ArrayList<ArrayList<ArrayList<Integer>>> pieceTypeMoveList : reverseRayKingList) {
            for (ArrayList<ArrayList<Integer>> dirList : pieceTypeMoveList) {
                if (dirList.size() == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a move puts the king in check. This is done by making the move on 
     * a deep copy of the board and checking if the king is in check.
     * @param color The color of the king to check.
     * @param move The move to check.
     * @param board The current board state.
     * @return True if the move puts the king in check, false otherwise.
     * <p>
     * @see #isKingInCheck(int, Board)
     */
    public static boolean movePutsKingInCheck (int color, int[] move, Board board) {
        Board tempBoard = new Board(board.positionToFEN(board.getPositionMap()));
        tempBoard.setPosition(Game.updatePosition(tempBoard.getPositionMap(), move));
        return isKingInCheck(color, tempBoard);
    }

}
