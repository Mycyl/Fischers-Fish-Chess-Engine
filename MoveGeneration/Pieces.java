import java.util.ArrayList;

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

    public static boolean moveIsKingCapture (int[] move, Board board) {
        return !isEmpty(board.getPositionMap().get(move[1]), board) && isKing(board.getPositionMap().get(move[1]));
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
            if (directionList.size() > 1) {
                if (directionList.get(0).get(0) == index) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean kingMovePutsKingInCheck (int color, int[] move, Board board) { // OPTIMIZE
        Board tempBoard = new Board(board.positionToFEN(board.getPositionMap()));
        tempBoard.setPosition(Game.updatePosition(tempBoard.getPositionMap(), move));
        Moves.generatePseudoLegalMoves(tempBoard);
        return isKingInCheck(color, tempBoard);
    }

    public static boolean isKingInCheck (int color, Board board) { // OPTIMIZE
        int kingIndex = (color == White) ? board.indexFromPiece(-King) : board.indexFromPiece(King);
        ArrayList<ArrayList<ArrayList<Integer>>> oppositeMoveList = (color == White) ? Moves.blackMoveList : Moves.whiteMoveList;
        for (ArrayList<ArrayList<Integer>> Piece : oppositeMoveList){
            for (ArrayList<Integer> Move : Piece) {
                if (Move.get(1) == kingIndex) {
                    return true;
                }
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
