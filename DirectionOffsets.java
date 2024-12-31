import java.util.HashMap;
import java.util.Map;

public class DirectionOffsets {
    
    private DirectionOffsets() {}

    /**
     * A list witholding integer values that represent the orthoganal 
     * and diagonal offsets that would be applied to a piece's index to 
     * move it in a certain direction.
     */
    public static int[] dirOffsetsSliding = {8, -8, -1, 1, 7, -7, 9, -9};

    /**
     * A list witholding integer values that represent the offsets that 
     * would be applied to a pawn's index to move it in a certain direction.
     */
    public static int[] dirOffsetsPawn = {9, 8, 7, 16};

    /**
     * A list witholding integer values that represent the offsets that 
     * would be applied to a knight's index to move it in a certain direction.
     */
    public static int[] dirOffsetsKnight = {-17, -15, -10, -6, 6, 10, 15, 17};

    /**
     * A list witholding integer values that represent the offsets that 
     * would be applied to a king's index to move it in a certain direction.
     */
    public static int[] dirOffsetsKing = {-9, -8, -7, -1, 1, 7, 8, 9};

    public static int[] dirOffsetsKingCastle = {-4, -3, -2, -1, 1, 2, 3}; // The first and last offsets are the rook's position, the ones in between are the pieces in between

    public static int[] dirOffsetsMoveCastleKing = {2, -2};

    public static int[] dirOffsetsMoveCastleRook = {-2, 3};

    /**
     * A dictionary that holds the valid delta values for a knight piece
     * to move in a certain direction.
     * <p>
     * Key: The offset value from dirOffsetsKnight
     * Value: The valid delta values for the knight piece to move in a certain direction [deltaFile, deltaRank]
     */
    public static Map<Integer, int[]> knightValidDeltaDictionary = new HashMap<Integer, int[]>(
        Map.ofEntries (
            Map.entry(-17, new int[] {-1, -2}),
            Map.entry(-15, new int[] {1, -2}),
            Map.entry(-10, new int[] {-2, -1}),
            Map.entry(-6, new int[] {2, -1}),
            Map.entry(6, new int[] {-2, 1}),
            Map.entry(10, new int[] {2, 1}),
            Map.entry(15, new int[] {-1, 2}),
            Map.entry(17, new int[] {1, 2})
        )
    );

    public static Map<Integer, int[]> pawnValidDeltaDictionary = new HashMap<Integer, int[]> (
        Map.ofEntries (
            Map.entry(-16, new int[] {0, -2}),
            Map.entry(-9, new int[] {-1, -1}),
            Map.entry(-8, new int[] {0, -1}),
            Map.entry(-7, new int[] {1, -1}),
            Map.entry(16,  new int[] {0, 2}),
            Map.entry(7, new int[] {-1, 1}),
            Map.entry(8, new int[] {0, 1}),
            Map.entry(9, new int[] {1, 1})
            
        )
    );

    public static Map<Integer, int[]> kingValidDeltaDictionary = new HashMap<Integer, int[]> (
        Map.ofEntries (
            Map.entry(-9, new int[] {-1, -1}),
            Map.entry(-8, new int[] {0, -1}),
            Map.entry(-7, new int[] {1, -1}),
            Map.entry(-1, new int[] {-1, 0}),
            Map.entry(1, new int[] {1, 0}),
            Map.entry(7, new int[] {-1, 1}),
            Map.entry(8, new int[] {0, 1}),
            Map.entry(9, new int[] {1, 1})
        )
    );

    public static Map<Integer, Integer> startingRankPawn = new HashMap<Integer, Integer> (
        Map.ofEntries (
            Map.entry(Pieces.Black, 1),
            Map.entry(Pieces.White, 6)
        )
    );

    public static Map<Integer, Integer> triggerRank = new HashMap<Integer, Integer> (
        Map.ofEntries (
            Map.entry(Pieces.Black, 4),
            Map.entry(Pieces.White, 3)
        )
    );

    

}
