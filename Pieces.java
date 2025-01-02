import java.util.ArrayList;
import java.util.Arrays;

public class Pieces {

    public static final int WHITE_KING_START_INDEX = 60;
    public static final int BLACK_KING_START_INDEX = 4;

    private Pieces () {}

    /** 
     * Static variable indicating an empty space on the chess 
     * board position index
    */
    public static int Empty = 0;

    /** 
     * Static variable indicating a pawn is present on the chess 
     * board position index
    */
    public static int Pawn = 1;

    /** 
     * Static variable indicating a rook is present on the chess 
     * board position index
    */
    public static int Rook = 2;

    /** 
     * Static variable indicating a knight is present on the chess 
     * board position index
    */
    public static int Knight = 3;

    /** 
     * Static variable indicating a bishop is present on the chess 
     * board position index
    */
    public static int Bishop = 4;

    /** 
     * Static variable indicating a queen is present on the chess 
     * board position index
    */
    public static int Queen = 5;

    /** 
     * Static variable indicating a king is present on the chess board 
     * position index
    */
    public static int King = 6;
    
    /** 
     * Static variable indicating that the piece on the specified index 
     * is of color type black
    */
    public static int Black = 1;

    /** 
     * Static variable indicating that the piece on the specified index 
     * is of color type white
    */
    public static int White = -1;

    /** 
     * A list witholding integer values that represent computed data 
     * about the position of a piece on a certain index
     */
    private static int[] moveDataArrayList;

    /** 
     * A method used to indicate whether the piece is white (true) or black (false)
     * <p>
     * @return piece is white (true); piece is black (false)
     */
    public static boolean isWhite(char symbol) {
        return (Character.isUpperCase(symbol));
    }

    /**
     * A method used to indicate whether the parameter (int piece) is of type (int type)
     * @param piece     the piece to be checked
     * @param type      the type to be checked
     * <p>
     * @return          the piece is of the type
     */
    public static boolean isPieceType (int piece, int type) {
        return (Math.abs(piece) == type);
    }

    /**
     * A method used to indicate whether the parameter (int piece1) is of the same color as the parameter (int color)
     * @param piece1    the piece to be checked
     * @param color     the color to be checked
     * <p>
     * @return          the piece is of the same color
     */
    public static boolean sameColor (int piece1, int color) {
        return (piece1 * color > 0);
    }

    public static boolean isWhite (int piece) {
        return sameColor(piece, White);
    }

    /**
     * A method used to indicate whether the parameter (int piece) is empty
     * @param piece     the piece to be checked
     * <p>
     * @return          the piece is empty
     */
    public static boolean isEmpty (int index, Board board) {
        return board.getPositionMap().get(index) == null;
    }

    /**
     * A method used to indicate whether the parameter (int piece) is a sliding piece
     * @param piece     the piece to be checked
     * <p>
     * @return          the piece is a sliding piece
     */
    public static boolean isSlidingPiece (int piece) {
        return (Math.abs(piece) == Rook || Math.abs(piece) == Bishop || Math.abs(piece) == Queen);
    }

    /**
     * A method used to indicate whether the parameter (int piece) is a pawn
     * @param piece     the piece to be checked
     * <p>
     * @return          the piece is a pawn
     */
    public static boolean isPawn (int piece) {
        return (Math.abs(piece) == Pawn);
    }

    /**
     * A method used to indicate whether the parameter (int piece) is a king
     * @param piece     the piece to be checked
     * <p>
     * @return          the piece is a king
     */
    public static boolean isKing (int piece) {
        return (Math.abs(piece) == King);
    }

    /**
     * A method used to indicate whether the parameter (int piece) is a knight
     * @param piece     the piece to be checked
     * <p>
     * @return          the piece is a knight
     */
    public static boolean isKnight (int piece) {
        return (Math.abs(piece) == Knight);
    }

    /**
     * A method used to indicate whether the parameter piece at the target index is capturable
     * @param index     the index of the piece to be checked
     * @param colorUp   the color of the player who is taking the piece
     * @param board     the current board state
     * <p>
     * @return          the piece at the target index is capturable
     */
    public static boolean isCapturable (int index, int colorUp, Board board) {
        if (!isEmpty(index, board) && !sameColor(board.getPositionMap().get(index), colorUp)) {
            return true;
        }
        return false;
    }

    public static boolean emptyBetween (Board board, int kingIndex, int rookIndex) {
        int startIndex = (kingIndex > rookIndex) ? 1 : 4;
        int endIndex = (kingIndex > rookIndex) ? 4 : 6;
        for (int i = startIndex; i < endIndex; i++) {
            int targetIndex = kingIndex + DirectionOffsets.dirOffsetsKingCastle[i];
            if (isEmpty(board.getPositionMap().get(targetIndex), board)) {
                return false;
            }
        }
        return true;
    }

    public static boolean kingMoved (ArrayList<int[]> allMovesTaken, int color) { // allMovesTaken [[original, new], [original, new], [original, new]]
        for (int[] move : allMovesTaken) {
            if (color == White && move[0] == WHITE_KING_START_INDEX) {
                return true;
            } else if (color == Black && move[0] == BLACK_KING_START_INDEX) {
                return true;
            }
        }
        return false;
    }

    public static boolean rookMoved (ArrayList<int[]> allMovesTaken, int index) {
        for (int[] move : allMovesTaken) {
            if (move[0] == index) {
                return true;
            }
        }
        return false;
    }

    public static boolean castlingAvailable (boolean rookMoved, boolean kingMoved, int kingIndex, int rookIndex, Board board) { // check if the rook is in the right position to castle
        if (!rookMoved && !kingMoved && emptyBetween(board, kingIndex, rookIndex)) { // make legal not pseudo legal
            return true;
        }
        return false;
    }

    public static boolean isPinnedPiece (ArrayList<ArrayList<ArrayList<Integer>>> reverseRayKingList, int index, Board board) {
        for (ArrayList<ArrayList<Integer>> directionList : reverseRayKingList) {
            if (directionList.size() == 2) {
                if (directionList.get(0).get(0) == index) {
                    return true;
                }
            }
        }
        return false;
    }

    public static ArrayList<ArrayList<ArrayList<Integer>>> reverseRayKingList (int color, Board board) {

        int kingIndex = (color == White) ? Board.indexFromPiece(-King) : Board.indexFromPiece(King);
        ArrayList<ArrayList<ArrayList<Integer>>> reverseRayKingList = new ArrayList<ArrayList<ArrayList<Integer>>>();
        int[] moveDataKing = moveData(kingIndex);

        for (int directionIndex = 0; directionIndex < DirectionOffsets.dirOffsetsReverseRay.length; directionIndex++) {

            ArrayList<ArrayList<Integer>> directionList = new ArrayList<ArrayList<Integer>>();

            if (directionIndex / 8 == 0) {
                // sliding pieces
                int friendlyPiecesCounted = 0;
                int emptySquaresCounted = 0;
                boolean enemyPieceEncountered = false;

                for (int n = 0; n < moveDataKing[directionIndex]; n++) {

                    if (n == 0) { // do pawns and kings here TO DO

                        int sevenIndex = (color == White) ? 5 : 4;
                        int nineIndex = (color == White) ? 7 : 6;
                        int targetIndexSeven = kingIndex + DirectionOffsets.dirOffsetsSliding[sevenIndex];
                        int targetIndexNine = kingIndex + DirectionOffsets.dirOffsetsSliding[nineIndex];
                        int pawnLookingFor = (color == White) ? Pawn : -Pawn;


                        int targetIndex = kingIndex + DirectionOffsets.dirOffsetsSliding[directionIndex];
                        if (!isEmpty(targetIndex, board)) {
                            int pieceAtTarget = board.getPositionMap().get(targetIndex);
                            if (sameColor(pieceAtTarget, color)) {
                                friendlyPiecesCounted++;
                            }
                        }
                    }
                    
                    if (friendlyPiecesCounted > 1) {
                        directionList.clear();
                        break;
                    }

                    int targetIndex = kingIndex + DirectionOffsets.dirOffsetsSliding[directionIndex] * (n + 1);

                    if (!isEmpty(targetIndex, board)) {
                        int pieceAtTarget = board.getPositionMap().get(targetIndex);
                        if (sameColor(pieceAtTarget, color)) {
                            friendlyPiecesCounted++;
                        }
                    }

                    ArrayList<Integer> move = new ArrayList<Integer>();

                    if (!isEmpty(targetIndex, board)) {
                        int pieceAtTarget = board.getPositionMap().get(targetIndex);
                        move.add(targetIndex);
                        move.add(pieceAtTarget);
                    } else {
                        emptySquaresCounted++;
                        move.add(targetIndex);
                        move.add(Empty);
                    }

                    if (move.size() > 0) {
                        directionList.add(move);
                    }

                    if (!isEmpty(targetIndex, board) && !sameColor(board.getPositionMap().get(targetIndex), color)) {
                        enemyPieceEncountered = true;
                        break;
                    }

                    if (emptySquaresCounted + friendlyPiecesCounted == moveDataKing[directionIndex] && !enemyPieceEncountered) {
                        directionList.clear();
                        break;
                    }
                }

            } else { // NEEDS WORK

                ArrayList<Integer> move = new ArrayList<Integer>();
                int targetIndex = kingIndex + DirectionOffsets.dirOffsetsReverseRay[directionIndex];
                if (!isEmpty(targetIndex, board)) {  
                    int pieceAtTarget = board.getPositionMap().get(targetIndex);
                    move.add(targetIndex);
                    move.add(pieceAtTarget);
                }
                int dirOffsetIndexTesting = directionIndex - 8;

                if (move.size() > 0 && isKnight(move.get(1))) {
                    if (isValidMoveReverseRayKing(kingIndex, targetIndex, dirOffsetIndexTesting, board)) {
                        if (!isEmpty(targetIndex, board) && !sameColor(board.getPositionMap().get(targetIndex), color)) {
                            directionList.add(move);
                        }
                    }
                }
            }
            if (directionList.size() > 0) {
                reverseRayKingList.add(directionList);
            }
        }
        return reverseRayKingList;
    }

    public static boolean isValidMoveReverseRayKing (int startingIndex, int targetIndex, int dirOffsetIndex, Board board) {

        if (targetIndex < 0 || targetIndex > 63) {return false;}

        int pieceTesting = board.getPositionMap().get(targetIndex);
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

    public static boolean kingMovePutsKingInCheck (int color, int[] move, Board board) { // OPTIMIZE
        Board tempBoard = new Board(Board.positionToFEN(board.getPositionMap()));
        tempBoard.setPosition(Game.updatePosition(tempBoard.getPositionMap(), move));
        Moves.generatePseudoLegalMoves(tempBoard);
        return isKingInCheck(color, tempBoard);
    }

    public static boolean isKingInCheck (int color, Board board) { // OPTIMIZE
        int kingIndex = (color == White) ? Board.indexFromPiece(-King) : Board.indexFromPiece(King);
        ArrayList<ArrayList<Integer>> oppositeMoveList = (color == White) ? Moves.blackMoveList : Moves.whiteMoveList;
        for (ArrayList<Integer> Move : oppositeMoveList) {
            if (Move.get(1) == kingIndex) {
                return true;
            }
        }
        return false;
    }

    /**
     * ranks = rows
     * files = columns
     * @param index
     * @return computed move data of a piece at specified index
     */
    public static int[] moveData (int index) { 
        
        int rank = index / 8;
        int file = index % 8;

        int numN = rank;
        int numS = 7 - rank;
        int numE = 7 - file;
        int numW = file;
        int numNE = Math.min(numN, numE);
        int numSE = Math.min(numS, numE);
        int numSW = Math.min(numS, numW);
        int numNW = Math.min(numN, numW);

        moveDataArrayList = new int[] {
            numS, // Offset -8
            numN, // Offset 8
            numW, // Offset 1
            numE, // Offset -1
            numSW, // Offset -7
            numNE, // Offset 9
            numSE, // Offset 7
            numNW // Offset -9
        };

        return moveDataArrayList;
    }
}
