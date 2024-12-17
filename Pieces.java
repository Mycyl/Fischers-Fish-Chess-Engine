
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

    private static int[] moveDataArrayList;

    public static boolean isWhite(char symbol) {
        if (Character.isUpperCase(symbol)) {
            return true;
        }
        return false;
    }

    public static int[] moveData (int index) { // ranks - rows // files - columns
        int rank = index / 8;
        int file = index % 8;

        int numN = rank;
        int numS = 7- rank;
        int numE = 7 - file;
        int numW = file;
        int numNE = Math.min(numN, numE);
        int numSE = Math.min(numS, numE);
        int numSW = Math.min(numS, numW);
        int numNW = Math.min(numN, numW);

        moveDataArrayList = new int[] {
            numN,
            numS,
            numE,
            numW,
            numNE,
            numSE,
            numSW,
            numNW
        };

        return moveDataArrayList;
    }
}
