import java.util.HashMap;
import java.util.Map;

/**
 * Direction Offsets help aid other classes in calculations pertaining to move generation
 * @author Michael Madrid
 */

public class DirectionOffsets {
    
    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private DirectionOffsets() {}

    /**
     * A list witholding integer values that represent the orthoganal 
     * and diagonal offsets that would be applied to a piece's index to 
     * move it in a certain direction.
     */
    public static int[] dirOffsetsSliding = {8, -8, -1, 1, 7, -7, 9, -9}; // first 4 are orthogonal, last 4 are diagonal

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

    /**
     * A list witholding integer values that represent the offsets that
     * would be applied to a king's index to move it in a certain direction
     * for castling. The first and last offsets are the rook's position, the ones in between are the pieces in between.
     * Used to check the pieces in between the king and the rook for castling.
     */
    public static int[] dirOffsetsKingCastle = {-4, -3, -2, -1, 1, 2, 3};

    /**
     * The offsets that would be applied to a king's index to move it in a certain direction for castling.
     */
    public static int[] dirOffsetsMoveCastleKing = {2, -2};

    /**
     * The offsets that would be applied to a rook's index to move it in a certain direction for castling.
     */
    public static int[] dirOffsetsMoveCastleRook = {-2, 3};

    /**
     * A list witholding integer values that represent the offsets that would be applied to a king's index to move it in a certain direction for reverse ray.
     * The first 8 indexes are the same as dirOffsetsSliding, the last 8 are the same as dirOffsetsKnight.
     */
    public static int[] dirOffsetsReverseRay = {8, -8, -1, 1, 7, -7, 9, -9, -17, -15, -10, -6, 6, 10, 15, 17}; 

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

    /**
     * A dictionary that holds the valid delta values for a pawn piece to move in a certain direction.
     * <p>
     * Key: The offset value from dirOffsetsPawn or dirOffsetsKingCastle
     * Value: The valid delta values for the pawn piece to move in a certain direction [deltaFile, deltaRank]
     */
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

    /**
     * A dictionary that holds the valid delta values for a king piece to move in a certain direction.
     * <p>
     * Key: The offset value from dirOffsetsKing
     * Value: The valid delta values for the king piece to move in a certain direction [deltaFile, deltaRank]
     */
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

    /**
     * A dictionary that holds the starting rank for a pawn piece.
     * <p>
     * Key: The color of the pawn piece (Pieces.White or Pieces.Black)
     * Value: The starting rank for the pawn piece (1 or 6)
     */
    public static Map<Integer, Integer> startingRankPawn = new HashMap<Integer, Integer> (
        Map.ofEntries (
            Map.entry(Pieces.Black, 1),
            Map.entry(Pieces.White, 6)
        )
    );

    /**
     * A dictionary that holds the rank for a pawn piece to capture En Passant.
     * <p>
     * Key: The color of the pawn piece (Pieces.White or Pieces.Black)
     * Value: The rank for the pawn piece to capture En Passant (4 or 5)
     */
    public static Map<Integer, Integer> triggerRank = new HashMap<Integer, Integer> (
        Map.ofEntries (
            Map.entry(Pieces.Black, 4),
            Map.entry(Pieces.White, 3)
        )
    );

    /**
     * A dictionary that holds the piece type from an integer value.
     * <p>
     * Key: The integer value of the piece type (Pieces.Pawn * Pieces.White, Pieces.Rook * Pieces.White, etc.)
     * Value: The string value of the piece type ("P", "R", "N", "B", "Q", "K", "p", "r", "n", "b", "q", "k")
     */
    public static Map<Integer, String> pieceTypeFromInt = new HashMap<Integer, String> (
        Map.ofEntries (
            Map.entry(Pieces.Pawn * Pieces.White, "P"),
            Map.entry(Pieces.Rook * Pieces.White, "R"),
            Map.entry(Pieces.Knight * Pieces.White, "N"),
            Map.entry(Pieces.Bishop * Pieces.White, "B"),
            Map.entry(Pieces.Queen * Pieces.White, "Q"),
            Map.entry(Pieces.King * Pieces.White, "K"),
            Map.entry(Pieces.Pawn * Pieces.Black, "p"),
            Map.entry(Pieces.Rook * Pieces.Black, "r"),
            Map.entry(Pieces.Knight * Pieces.Black, "n"),
            Map.entry(Pieces.Bishop * Pieces.Black, "b"),
            Map.entry(Pieces.Queen * Pieces.Black, "q"),
            Map.entry(Pieces.King * Pieces.Black, "k")
        )
    );

    /**
     * A dictionary that relates the direction offset to the piece type that can move in that direction.
     * <p>
     * Key: The direction offset from dirOffsetsSliding or dirOffsetsKnight
     * Value: The piece type that can move in that direction (Pieces.Rook, Pieces.Bishop, Pieces.Knight, Pieces.Queen)
     */
    public static Map<Integer, int[]> reverseRayKingDirPieceMap = new HashMap<Integer, int[]> (
        Map.ofEntries (
            Map.entry(8, new int[] {Pieces.Rook, Pieces.Queen}),
            Map.entry(-8, new int[] {Pieces.Rook, Pieces.Queen}),
            Map.entry(-1, new int[] {Pieces.Rook, Pieces.Queen}),
            Map.entry(1, new int[] {Pieces.Rook, Pieces.Queen}),
            Map.entry(7, new int[] {Pieces.Queen, Pieces.Bishop}),
            Map.entry(-7, new int[] {Pieces.Queen, Pieces.Bishop}),
            Map.entry(9, new int[] {Pieces.Queen, Pieces.Bishop}),
            Map.entry(-9, new int[] {Pieces.Queen, Pieces.Bishop}),
            Map.entry(-17, new int[] {Pieces.Knight}),
            Map.entry(-15, new int[] {Pieces.Knight}),
            Map.entry(-10, new int[] {Pieces.Knight}),
            Map.entry(-6, new int[] {Pieces.Knight}),
            Map.entry(6, new int[] {Pieces.Knight}),
            Map.entry(10, new int[] {Pieces.Knight}),
            Map.entry(15, new int[] {Pieces.Knight}),
            Map.entry(17, new int[] {Pieces.Knight})
        )
    );

}
