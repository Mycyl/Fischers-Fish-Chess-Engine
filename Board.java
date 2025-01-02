import java.util.ArrayList;
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
     * The variable that represent the current position of the chess board 
     * encoded with the variables specified in the Pieces class
     */
    private ArrayList<Integer> position;

    /**  
     * Constructor:
     * The constructor that creates a Board object for chess board encoding
     * <p>
     * @param fenString The standard format of encoding a chess 
     *                  board position
     * **/    
    public Board (String fenString) {
        this.fenString = fenString;
        position = new ArrayList<Integer>();
        parseFenString();
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

    public void setPosition (Map<Integer, Integer> positionMap) {
        this.positionMap = positionMap;
    }

    public static int getIndexFromRankAndFile (int rank, int file) {
        return rank * 8 + file;
    }

    public static String positionToFEN (Map<Integer, Integer> position) {
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

    public static int pieceFromIndex (int index) {
        return positionMap.get(index);
    }

    public static int indexFromPiece (int piece) { // apply a search algorithm
        for (Map.Entry<Integer, Integer> entry : positionMap.entrySet()) {
            if (entry.getValue() == piece) {
                return entry.getKey();
            }
        }
        return -1;
    }

    public static boolean pieceNotOnBoard (int piece) {
        return (indexFromPiece(piece) == -1);
    }


    public static Map<Integer, Integer> positionMap = new HashMap<Integer, Integer> ();

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
                    positionMap.put(index, pieceTypeFromFENMAP.get(Character.toLowerCase(symbol)) * whiteOrBlack);
                    index++;
                }
            }
        }

    }
}