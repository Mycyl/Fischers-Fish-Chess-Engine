import java.util.ArrayList;
import java.util.HashMap;

public class Legalization {
    
    public static boolean isKingInCheck (int color, Board board) {
        ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> reverseRayKingList = ReverseRay.reverseRayKingList(color, board);
        for (ArrayList<ArrayList<ArrayList<Integer>>> pieceTypeMoveList : reverseRayKingList) {
            for (ArrayList<ArrayList<Integer>> dirList : pieceTypeMoveList) {
                if (dirList.size() == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean movePutsKingInCheck (int color, int[] move, Board board) { // might have to change reverse ray layout instead of a staic int[] array
        Board tempBoard = new Board(board.positionToFEN(board.getPositionMap()));
        tempBoard.setPosition(Game.updatePosition(tempBoard.getPositionMap(), move));
        return isKingInCheck(color, tempBoard);
    }

}
