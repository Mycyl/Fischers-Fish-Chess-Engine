
public class Pieces {

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
     * A list witholding integer values that represent the orthoganal 
     * and diagonal offsets that would be applied to a piece's index to 
     * move it in a certain direction.
     */
    public static int[] dirOffsets = {8, -8, -1, 1, 7, -7, 9, -9};

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

    public static boolean isPieceType (int piece, int type) {
        return (Math.abs(piece) == type);
    }

    public static boolean sameColor (int piece1, int color) {
        return (piece1 * color > 0);
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
