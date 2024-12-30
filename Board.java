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
    public ArrayList<Integer> getPosition () {
        return position;
    }

    public void setPosition (ArrayList<Integer> position) {
        this.position = position;
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

        for (char symbol : fenCharacters) {
            if (symbol != '/') {
                if (Character.isDigit(symbol)) {
                    for (int i = 0; i < Character.getNumericValue(symbol); i++) {
                        position.add(Pieces.Empty);
                    }
                } else {
                    int whiteOrBlack = Pieces.isWhite(symbol) ? Pieces.White : Pieces.Black;
                    position.add(pieceTypeFromFENMAP.get(Character.toLowerCase(symbol)) * whiteOrBlack);
                }
            }
        }

    }
}