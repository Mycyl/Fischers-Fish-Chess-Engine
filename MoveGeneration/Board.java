import java.util.HashMap;
import java.util.Map;

/**
 * Board is the class that helps create an encoded version of the position of the chess board;
 * One interceptable to the chess algorithm
 * @author Michael Madrid
 * 
 */

public class Board {

    /**
     * The final static variable representing the initial position of the 
     * chess board as a Forsyth Edwards Notation String.
     */
    private static final String INITIAL_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    /**
     * The variable that represents the Forsyth Edwards Notation String to 
     * be set by the constructor when instantiating an object
     */
    private String fenString;

    /**
     * The variable that represents the position of the chess board as a
     * HashMap of Integers that represents the color and type of piece at the index.
     * The index is the key and the value is the piece type and color.
     */
    public Map<Integer, Integer> positionMap = new HashMap<Integer, Integer> ();

    /**  
     * Constructor:
     * The constructor that creates a Board object for chess board encoding
     * <p>
     * @param fenString The standard format of encoding a chess 
     *                  board position
     * **/    
    public Board (String fenString) {
        this.fenString = fenString;
        parseFenString();
    }

    /**
     * Overloaded Constructor:
     * The constructor that creates a Board object for chess board encoding from another Board object.
     * <p>
     * @param other The Board object to copy from.
     */
    public Board(Board other) {
        // Deep copy of positionMap 
        this.positionMap = new HashMap<>(other.getPositionMap()); 
        // Deep copy of other relevant fields (e.g., turn, castling rights, etc.)
        // ...
    }

    /**
     * Getter Method:
     * Returns the final string that represents the initial position of the board, 
     * formatted as a FEN string.
     * <p> 
     * @return          the final variable string representing 
     *                  the initial position of the board
     */
    public static String getStartingFen () {
        return INITIAL_FEN;
    }

    /**
     * Getter Method:
     * Returns the position as an ArrayList which is populated with Integers that 
     * represents the color and type of piece
     * <p>
     * 
     * @return          an ArrayList populated with Integers of 
     *                  which represents the color and type of 
     *                  the piece
     */
    public Map<Integer, Integer> getPositionMap () {
        return positionMap;
    }

    /**
     * Setter Method:
     * <p>
     * @param positionMap the position map to set
     */
    public void setPosition (Map<Integer, Integer> positionMap) {
        this.positionMap = positionMap;
    }

    /**
     * Getter Method:
     * Returns the index of the piece on the board given the rank and file.
     * <p>
     * @param rank the rank of the piece on the board (0-7)
     * @param file the file of the piece on the board (0-7)
     * @return the index of the piece on the board given the rank and file.
     */
    public static int getIndexFromRankAndFile (int rank, int file) {
        return rank * 8 + file;
    }

    /**
     * Converts the position Map to a FEN String.
     * <p>
     * @param position the position Map to convert to a FEN String.
     * @return the FEN String representation of the position Map.
     */
    public String positionToFEN (Map<Integer, Integer> position) {
        StringBuilder FEN = new StringBuilder();
        int emptyCounter = 0;
        for (int i = 0; i < 64; i++) {
            if (!positionMap.containsKey(i)) {
                emptyCounter++;
            } else {
                if (emptyCounter != 0) {
                    FEN.append(emptyCounter);
                    emptyCounter = 0;
                }
                FEN.append(DirectionOffsets.pieceTypeFromInt.get(position.get(i)));
            }
            if (i % 8 == 7) {
                if (emptyCounter != 0) {
                    FEN.append(emptyCounter);
                    emptyCounter = 0;
                }
                if (i != 63) {
                    FEN.append("/");
                }
            }
        }
        return FEN.toString();
    }

    /**
     * Getter Method:
     * Returns the piece at the given index.
     * <p>
     * @param index the index of the piece to get.
     * @return the piece at the given index.
     */
    public int pieceFromIndex (int index) {
        return positionMap.get(index);
    }

    /**
     * Gets the index of the given piece.
     * <p>
     * @param piece the piece to get the index of.
     * @return the index of the given piece.
     */
    public int indexFromPiece (int piece) { // apply a search algorithm
        for (Map.Entry<Integer, Integer> entry : positionMap.entrySet()) {
            if (entry.getValue() == piece) {
                return entry.getKey();
            }
        }
        return -1;
    }

    /**
     * Getter Method:
     * Returns the index of the white king.
     * <p>
     * @return the index of the white king.
     */
    public int getIndexOfWhiteKing () {
        for (Map.Entry<Integer, Integer> entry : positionMap.entrySet()) {
            if (entry.getValue() == -Pieces.King) {
                return entry.getKey();
            }
        }
        System.out.println(positionMap);
        return -1;
    }

    /**
     * Returns the index of the black king.
     * <p>
     * @return the index of the black king.
     */
    public int getIndexOfBlackKing () {
        for (Map.Entry<Integer, Integer> entry : positionMap.entrySet()) {
            if (entry.getValue() == Pieces.King) {
                return entry.getKey();
            }
        }
        System.out.println(positionMap);

        return -1;
    }

    /**
     * Returns true if the piece is not on the board.
     * <p>
     * @param piece the piece to check.
     * @return true if the piece is not on the board.
     */
    public boolean pieceNotOnBoard (int piece) {
        return (indexFromPiece(piece) == -1);
    }

    /**
     * Instantiation Method:
     * Updates the position ArrayList, to be called in the constructor to populate 
     * the variable ArrayList<Integer> position with the encoded integers for each piece type
     * <p>
     * 
     * @see #Board(String)
     * @see Pieces#isWhite(char)
     * @see Character#getNuericValue(char)
     * @see Character#isDigit(char)
     * @see String#toCharArray()
     * @see String#split(String)
     */
    private void parseFenString () {

        Map<Character, Integer> pieceTypeFromFENMAP = new HashMap<Character, Integer>(
            Map.ofEntries(
                Map.entry('p', Pieces.Pawn), Map.entry('r', Pieces.Rook), 
                Map.entry('n', Pieces.Knight), Map.entry('b', Pieces.Bishop), 
                Map.entry('q', Pieces.Queen), Map.entry('k', Pieces.King)
            )
        );

        String[] FENList = fenString.split(" ");
        String isolateFen = FENList[0];
        char[] fenCharacters = isolateFen.toCharArray();
        int index = 0;

        for (char symbol : fenCharacters) {
            if (symbol != '/') {
                if (Character.isDigit(symbol)) {
                    index += Character.getNumericValue(symbol);
                } else {
                    int whiteOrBlack = Pieces.isWhite(symbol) ? Pieces.White : Pieces.Black;
                    int piece = pieceTypeFromFENMAP.get(Character.toLowerCase(symbol)) * whiteOrBlack;
                    positionMap.put(index, piece);
                    index++;
                }
            }
        }

    }
}