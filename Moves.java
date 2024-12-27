import java.util.ArrayList;
import java.util.Arrays;

public class Moves {
    
    private Moves () {}

    private static boolean isValidMove (int startingIndex, int targetIndex, int dirOffsetIndex, Board board) { // maybe make this more generally applicable

        int pieceTesting = board.getPosition().get(startingIndex);

        int startingIndexFile = startingIndex % 8;
        int startingIndexRank = startingIndex / 8;

        int targetIndexFile = targetIndex % 8;
        int targetIndexRank = targetIndex / 8;

        int deltaFile = targetIndexFile - startingIndexFile;
        int deltaRank = targetIndexRank - startingIndexRank;
        int[] testingDelta = {deltaFile, deltaRank};

        int offsetTesting;
        int[] validDelta = {0, 0};

        if (Pieces.isKnight(pieceTesting)) {

            offsetTesting = DirectionOffsets.dirOffsetsKnight[dirOffsetIndex];
            validDelta = DirectionOffsets.knightValidDeltaDictionary.get(offsetTesting);

        } else if (Pieces.isPawn(pieceTesting) && Pieces.isWhite(pieceTesting)) { // check isWhite function

            offsetTesting = DirectionOffsets.dirOffsetsSliding[dirOffsetIndex];
            //validDelta = 

        } // figure out pawn validation offset

        return Arrays.equals(testingDelta, validDelta);
    }

    public static ArrayList<ArrayList<ArrayList<Integer>>> generateMoves (Board board) {
        ArrayList<ArrayList<ArrayList<Integer>>> allMoves = new ArrayList<ArrayList<ArrayList<Integer>>>();
        for (int i = 0; i < 64; i++) {
            if (board.getPosition().get(i) == Pieces.Empty) {
                continue;
            }
            if (Pieces.isSlidingPiece(board.getPosition().get(i))) {
                if (Moves.generateSlidingMoves(i, board).size() > 0) {
                    allMoves.add(Moves.generateSlidingMoves(i, board));
                }
            } else if (Pieces.isPawn(board.getPosition().get(i))) {
                if (Moves.generatePawnMoves(i, board).size() > 0) {
                    allMoves.add(Moves.generatePawnMoves(i, board));
                }
            } else if (Pieces.isKing(board.getPosition().get(i))) {
                if (Moves.generateKingMoves(i, board).size() > 0) {
                    allMoves.add(Moves.generateKingMoves(i, board));
                }
            } else if (Pieces.isKnight(board.getPosition().get(i))) {
                if (Moves.generateKnightMoves(i, board).size() > 0) {
                    allMoves.add(Moves.generateKnightMoves(i, board));
                }
            }
        }
        return allMoves;
    }

    /**
     * Generates pseudo legal sliding moves for the piece at the specified index on the board
     * @see                         Pieces#isPieceType(int, int)
     * @see                         Pieces#sameColor(int, int)
     * @see                         Pieces#moveData(int)
     * @see                         Board#getPosition()
     * @see                         DirectionOffsets#dirOffsets   
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

                int targetIndex = startingIndex + DirectionOffsets.dirOffsetsSliding[directionIndex] * (n + 1);
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

    public static ArrayList<ArrayList<Integer>> generatePawnMoves (int startingIndex, Board board) { // account for indexes with overflow

        int colorUp = Pieces.sameColor(board.getPosition().get(startingIndex), Pieces.White) ? Pieces.White : Pieces.Black; 
        int dirMultiplier = (colorUp == Pieces.White) ? -1 : 1;

        ArrayList<ArrayList<Integer>> pawnMoves = new ArrayList<ArrayList<Integer>>();

        for (int i = 0; i < DirectionOffsets.dirOffsetsPawn.length; i++) {
            
            int targetIndex = startingIndex + DirectionOffsets.dirOffsetsPawn[i] * dirMultiplier;
            ArrayList<Integer> move = new ArrayList<Integer>();
            move.add(startingIndex);
            move.add(targetIndex);

            if (isValidMove(startingIndex, targetIndex, i, board)) {
                if (i % 2 == 0) {
                    if (Pieces.isCapturable(targetIndex, colorUp, board)) {
                        pawnMoves.add(move);
                    }
                } else {
                    if (Pieces.isEmpty(board.getPosition().get(targetIndex))) {
                        pawnMoves.add(move);
                    }
                }
            }
        }
        return pawnMoves;
    }

    public static ArrayList<ArrayList<Integer>> generateKnightMoves (int startingIndex, Board board) {
        ArrayList<ArrayList<Integer>> knightMoves = new ArrayList<ArrayList<Integer>>();
        return knightMoves;
    }

    public static ArrayList<ArrayList<Integer>> generateKingMoves (int startingIndex, Board board) {
        ArrayList<ArrayList<Integer>> kingMoves = new ArrayList<ArrayList<Integer>>();
        return kingMoves;
    }

    public static ArrayList<ArrayList<Integer>> generateCastlingMoves (int startingIndex, Board board) {
        ArrayList<ArrayList<Integer>> castlingMoves = new ArrayList<ArrayList<Integer>>();
        return castlingMoves;
    }

    public static ArrayList<ArrayList<Integer>> generateEnPassantMoves (int startingIndex, Board board) {
        ArrayList<ArrayList<Integer>> enPassantMoves = new ArrayList<ArrayList<Integer>>();
        return enPassantMoves;
    }
}
