import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board {

    private static final String INITIAL_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    private String fenString;
    private ArrayList<Integer> position;
    

    public Board (String fenString) {
        this.fenString = fenString;
        position = new ArrayList<Integer>();
        parseFenString();
    }

    public ArrayList<Integer> getPosition () {
        return position;
    }

    public static String startingFen () {
        return INITIAL_FEN;
    }

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
            if (!(symbol == '/')) {
                if (Character.isDigit(symbol)) {
                    for (int i = 0; i < Character.getNumericValue(symbol); i++) {
                        position.add(Pieces.Empty);
                    }
                } else {
                    int whiteOrBlack;
                    if (Pieces.isWhite(symbol)) {
                        whiteOrBlack = Pieces.White;
                    } else {
                        whiteOrBlack = Pieces.Black;
                    }
                    position.add(pieceTypeFromFENMAP.get(Character.toLowerCase(symbol)) * whiteOrBlack);
                }
            }
        }
    }


}
