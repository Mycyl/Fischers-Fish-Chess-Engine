import java.util.ArrayList;

/**
 * Pieces class represents the pieces on the chess board and their properties.
 * @author Michael Madrid
 * 
 */

public class Pieces {

    /**
     * Static variable indicating the starting index of the white king on the chess board
     */
    public static final int WHITE_KING_START_INDEX = 60;
    /**
     * Static variable indicating the starting index of the black king on the chess board
     */
    public static final int BLACK_KING_START_INDEX = 4;

    /**
     * Private constructor to prevent instantiation of the Pieces class
     */
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

    /**
     * A method used to indicate whether the parameter (int piece) is white
     * @param piece     the piece to be checked
     * <p>
     * @return          the piece is white
     */
    public static boolean isWhite (int piece) {
        return sameColor(piece, White);
    }

    /**
     * A method used to indicate whether the move is a king capture move
     * @param move      the move to be checked
     * @param board     the current board state
     * <p>
     * @return          the move is a king capture move
     */
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

    /**
     * A method used to check if the king is free to move in the direction of the rook while caslting
     * @param board     the current board state
     * @param kingIndex the index of the king to be checked
     * @param rookIndex the index of the rook to be checked
     * <p>
     * @return          the king is free to move in the direction of the rook while castling
     */
    public static boolean emptyBetween (Board board, int kingIndex, int rookIndex) {
        int startIndex = (kingIndex > rookIndex) ? 1 : 4;
        int endIndex = (kingIndex > rookIndex) ? 4 : 6;
        for (int i = startIndex; i < endIndex; i++) {
            int targetIndex = kingIndex + DirectionOffsets.dirOffsetsKingCastle[i];
            if (isEmpty(targetIndex, board)) {
                return false;
            }
        }
        return true;
    }

    /**
     * A method used to check if the king has moved during the game
     * @param allMovesTaken  the list of all moves taken during the game
     * @param color         the color of the king to be checked
     * <p>
     * @return          the king has moved during the game
     */
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

    /**
     * A method used to check if the rook has moved during the game
     * @param allMovesTaken  the list of all moves taken during the game
     * @param index        the index of the rook to be checked
     * <p>
     * @return          the rook has moved during the game
     */
    public static boolean rookMoved (ArrayList<int[]> allMovesTaken, int index) {
        for (int[] move : allMovesTaken) {
            if (move[0] == index) {
                return true;
            }
        }
        return false;
    }

    /**
     * A method used to check if the castling move is available for the king and rook at the specified indices
     * @param rookMoved     the rook has moved during the game (true) or not (false)
     * @param kingMoved     the king has moved during the game (true) or not (false)
     * @param kingIndex     the index of the king to be checked
     * @param rookIndex     the index of the rook to be checked
     * @param board         the current board state
     * <p>
     * @return              the castling move is available for the king and rook at the specified indices
     */
    public static boolean castlingAvailable (boolean rookMoved, boolean kingMoved, int kingIndex, int rookIndex, Board board) { // check if the rook is in the right position to castle
        if (!rookMoved && !kingMoved && emptyBetween(board, kingIndex, rookIndex)) { // make legal not pseudo legal
            return true;
        }
        return false;
    }

    /**
     * A method used to check if the piece at the specified index is pinned to the king
     * @param reverseRayKingList       the list of all reverse rays from the king to the piece at the specified index
     * @param index                    the index of the piece to be checked
     * @param board                    the current board state
     * <p>
     * @return                         the piece at the specified index is pinned to the king
     */
    public static boolean isPinnedPiece (ArrayList<ArrayList<ArrayList<Integer>>> reverseRayKingList, int index, Board board) {
        for (ArrayList<ArrayList<Integer>> directionList : reverseRayKingList) {
            if (directionList.size() > 1) {
                if (directionList.get(0).get(0) == index) {
                    return true;
                }
            }
        }
        return false; // NEEDS TO BE APPLIED AND TESTED
    }

    /**
     * A method used to check if the move is an en passant move
     * @param color    the color of the piece to be checked
     * @param move     the move to be checked
     * @param board    the current board state
     * <p>
     * @return          the move is an en passant move
     */
    public static boolean isEnPassantMove (int color, int[] move, Board board) { // OPTIMIZE
        if (Moves.enPassantMoveList.size() > 0) {
            for (ArrayList<Integer> moveList : Moves.enPassantMoveList) { // search algorithm
                if (moveList.get(0) == move[0] && moveList.get(1) == move[1]) {
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
