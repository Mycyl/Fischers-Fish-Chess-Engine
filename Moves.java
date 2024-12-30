import java.util.ArrayList;
import java.util.Arrays;

public class Moves {
    
    private static ArrayList<int[]> allMovesTaken = new ArrayList<int[]>(); // update this

    private Moves () {}

    private static boolean isValidMove (int startingIndex, int targetIndex, int dirOffsetIndex, Board board) { // maybe make this more generally applicable

        if (targetIndex < 0 || targetIndex > 63) {return false;}

        int pieceTesting = board.getPosition().get(startingIndex);
        int colorUp = Pieces.sameColor(pieceTesting, Pieces.White) ? Pieces.White : Pieces.Black; 

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

        } else if (Pieces.isPawn(pieceTesting)) {

            offsetTesting = DirectionOffsets.dirOffsetsPawn[dirOffsetIndex];
            int dirMultiplier = (colorUp == Pieces.White) ? -1 : 1;
            validDelta = DirectionOffsets.pawnValidDeltaDictionary.get(offsetTesting * dirMultiplier);

        } else if (Pieces.isKing(pieceTesting)) {

            offsetTesting = DirectionOffsets.dirOffsetsKing[dirOffsetIndex];
            validDelta = DirectionOffsets.kingValidDeltaDictionary.get(offsetTesting);

        }

        return Arrays.equals(testingDelta, validDelta);
    }

    public static ArrayList<ArrayList<ArrayList<Integer>>> generatePseudoLegalMoves (Board board) {
        //boolean kingMoved = Game.kingMoved;
        ArrayList<ArrayList<ArrayList<Integer>>> allMoves = new ArrayList<ArrayList<ArrayList<Integer>>>();
        for (int i = 0; i < 64; i++) {
            int colorUp = Pieces.sameColor(board.getPosition().get(i), Pieces.White) ? Pieces.White : Pieces.Black; 
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

    public static ArrayList<ArrayList<Integer>> generatePawnMoves (int startingIndex, Board board) {

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

        int colorUp = Pieces.sameColor(board.getPosition().get(startingIndex), Pieces.White) ? Pieces.White : Pieces.Black; 

        for (int i = 0; i < DirectionOffsets.dirOffsetsKnight.length; i++) {

            int targetIndex = startingIndex + DirectionOffsets.dirOffsetsKnight[i];
            ArrayList<Integer> move = new ArrayList<Integer>();
            move.add(startingIndex);
            move.add(targetIndex);

            if (isValidMove(startingIndex, targetIndex, i, board)) {
                if (!Pieces.sameColor(board.getPosition().get(targetIndex), colorUp) || Pieces.isEmpty(board.getPosition().get(targetIndex))) {
                    knightMoves.add(move);
                }
            }
        }

        return knightMoves;
    }

    public static ArrayList<ArrayList<Integer>> generateKingMoves (int startingIndex, Board board) {

        ArrayList<ArrayList<Integer>> kingMoves = new ArrayList<ArrayList<Integer>>();

        int colorUp = Pieces.sameColor(board.getPosition().get(startingIndex), Pieces.White) ? Pieces.White : Pieces.Black; 

        for (int i = 0; i < DirectionOffsets.dirOffsetsKing.length; i++) {

            int targetIndex = startingIndex + DirectionOffsets.dirOffsetsKing[i];
            ArrayList<Integer> move = new ArrayList<Integer>();
            move.add(startingIndex);
            move.add(targetIndex);

            

            if (isValidMove(startingIndex, targetIndex, i, board)) {
                if (!Pieces.sameColor(board.getPosition().get(targetIndex), colorUp) || Pieces.isEmpty(board.getPosition().get(targetIndex))) {
                    kingMoves.add(move);
                }
            }
        }

        return kingMoves;
    }

    public static ArrayList<ArrayList<ArrayList<Integer>>> generateCastlingMoves (int startingIndex, Board board) { // To be implemented
        ArrayList<ArrayList<ArrayList<Integer>>> castlingMoves = new ArrayList<ArrayList<ArrayList<Integer>>>();
        int colorUp = Pieces.sameColor(board.getPosition().get(startingIndex), Pieces.White) ? Pieces.White : Pieces.Black;
        boolean kingMoved = Pieces.kingMoved(Game.allMovesTaken, colorUp);

        if (kingMoved) {
            return castlingMoves;
        }

        int[] dirOffsetsKingCastle = DirectionOffsets.dirOffsetsKingCastle;

        int rookOneIndex = startingIndex + dirOffsetsKingCastle[0];
        int rookTwoIndex = startingIndex + dirOffsetsKingCastle[dirOffsetsKingCastle.length - 1];

        boolean rookOneMoved = Pieces.rookMoved(Game.allMovesTaken, rookOneIndex);
        boolean rookTwoMoved = Pieces.rookMoved(Game.allMovesTaken, rookTwoIndex);

        boolean[] rookMoved = {rookOneMoved, rookTwoMoved};
        int[] rookIndexes = {rookOneIndex, rookTwoIndex};

        for (int i = 0; i < rookMoved.length; i++) {
            if (Pieces.castlingAvailable(rookMoved[i], kingMoved, startingIndex, rookIndexes[i], board)) {

                int[] dirOffsetsMoveCastleKing = DirectionOffsets.dirOffsetsMoveCastleKing;
                int[] dirOffsetsMoveCastleRook = DirectionOffsets.dirOffsetsMoveCastleRook;

                ArrayList<ArrayList<Integer>> castlingMove = new ArrayList<ArrayList<Integer>>();
                ArrayList<Integer> kingMove = new ArrayList<Integer>();
                ArrayList<Integer> rookMove = new ArrayList<Integer>();

                kingMove.add(startingIndex);
                rookMove.add(rookIndexes[i]);

                int kingTargetOffset = (startingIndex > rookIndexes[i]) ?  dirOffsetsMoveCastleKing[1] : dirOffsetsMoveCastleKing[0];
                int rookTargetOffset = (startingIndex > rookIndexes[i]) ? dirOffsetsMoveCastleRook[1] : dirOffsetsMoveCastleRook[0];

                int targetKingIndex = startingIndex + kingTargetOffset;
                int rookTargetIndex = rookIndexes[i] + rookTargetOffset;

                kingMove.add(targetKingIndex);
                rookMove.add(rookTargetIndex);

                castlingMove.add(kingMove);
                castlingMove.add(rookMove);

                castlingMoves.add(castlingMove); // check if placed right
            }
        }
        return castlingMoves;
    }

    public static ArrayList<ArrayList<Integer>> generateEnPassantMoves (int startingIndex, Board board, ArrayList<ArrayList<Integer>> allMovesTaken) { // To be implemented
        ArrayList<ArrayList<Integer>> enPassantMoves = new ArrayList<ArrayList<Integer>>();
        return enPassantMoves;
    }
}
