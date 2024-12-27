import java.util.ArrayList;

public class Moves {
    
    private Moves () {}

    /**
     * Generates pseudo legal sliding moves for the piece at the specified index on the board
     * @see                         Pieces#isPieceType(int, int)
     * @see                         Pieces#sameColor(int, int)
     * @see                         Pieces#dirOffsets   
     * @see                         Pieces#moveData(int)
     * @see                         Board#getPosition()
     * <p>
     * @param startingIndex         the index of the piece to generate moves for
     * @param board                 the current board state
     * @return                      an ArrayList of ArrayLists of Integers representing 
     *                              the moves, where the Integers in each ArrayList are 
     *                              the indexes (Starting Index, Ending Index)
     */
    public static ArrayList<ArrayList<Integer>> generateSlidingMoves (int startingIndex, Board board) { // Account for pawns + test for castling & en passant + test queen + rook

        int pieceAtIndex = board.getPosition().get(startingIndex);
        int startingDirIndex = (Pieces.isPieceType(pieceAtIndex, Pieces.Bishop)) ? 4 : 0; // moveDirOffsets for Bishop starts at 4 (Diagonal moves)
        int endingDirIndex = (Pieces.isPieceType(pieceAtIndex, Pieces.Rook)) ? 4 : 8; // moveDirOffsets for Rook ends at 4 (Orthagonal moves)

        ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
        int colorUp = Pieces.sameColor(board.getPosition().get(startingIndex), Pieces.White) ? Pieces.White : Pieces.Black; 

        for (int directionIndex = startingDirIndex; directionIndex < endingDirIndex; directionIndex++) {
            for (int n = 0; n < Pieces.moveData(startingIndex)[directionIndex]; n++) {

                int targetIndex = startingIndex + Pieces.dirOffsets[directionIndex] * (n + 1);
                int pieceAtTarget = board.getPosition().get(targetIndex);

                if (Pieces.sameColor(pieceAtTarget, colorUp) && !Pieces.isEmpty(pieceAtTarget)) {
                    break;
                }

                ArrayList<Integer> move = new ArrayList<Integer>();
                move.add(startingIndex);
                move.add(targetIndex);
                moves.add(move);

                if (!Pieces.sameColor(board.getPosition().get(targetIndex), colorUp) && !Pieces.isEmpty(pieceAtTarget)) {
                    break;
                }
            }
        }
        return moves;
    }

}
