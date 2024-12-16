public class Pieces {

    public static int Empty = 0;
    public static int Pawn = 1;
    public static int Rook = 2;
    public static int Knight = 3;
    public static int Bishop = 4;
    public static int Queen = 5;
    public static int King = 6;
    
    public static int Black = 1;
    public static int White = -1;

    public static boolean isWhite(char symbol) {
        if (Character.isUpperCase(symbol)) {
            return true;
        }
        return false;
    }
}
