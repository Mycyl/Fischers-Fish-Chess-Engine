import java.util.ArrayList;
import java.util.Arrays;

public class ReverseRay {

        private ReverseRay() {}

        private static boolean inList (int piece, int[] list) {
            for (int i = 0; i < list.length; i++) {
                if (list[i] == piece) {
                    return true;
                }
            }
            return false;
        }

        public static ArrayList<ArrayList<ArrayList<Integer>>> reverseRayKingListWhite =  new ArrayList<ArrayList<ArrayList<Integer>>>(); // might cause errors
        public static ArrayList<ArrayList<ArrayList<Integer>>> reverseRayKingListBlack = new ArrayList<ArrayList<ArrayList<Integer>>>();

        public static void updateReverseRayKingList (int color, Board board) { // maybe split up the method into checking for orthogonal and diagonal pieces

            reverseRayKingListWhite.clear();
            reverseRayKingListBlack.clear();

            ArrayList<ArrayList<ArrayList<Integer>>> reverseRayKingList = (color == Pieces.White) ? reverseRayKingListWhite : reverseRayKingListBlack;

            generateSlidingReverseRay(reverseRayKingList, color, board);
            generateKnightReverseRay(reverseRayKingList, color, board);
            generatePawnReverseRay(reverseRayKingList, color, board);
            generateKingReverseRay(reverseRayKingList, color, board);
        }

        private static void generateSlidingReverseRay(ArrayList<ArrayList<ArrayList<Integer>>> reverseRayKingList, int color, Board board) {

            int kingIndex = (color == Pieces.White) ? Board.indexFromPiece(-Pieces.King) : Board.indexFromPiece(Pieces.King);
            int opposingColor = (color == Pieces.White) ? Pieces.Black : Pieces.White;
            int[] dirOffsetsSliding = DirectionOffsets.dirOffsetsSliding;
            int[] moveData = Pieces.moveData(kingIndex);
            for (int directionIndex = 0; directionIndex < dirOffsetsSliding.length; directionIndex++) {

                int friendlyPiecesCounted = 0;
                int nonCheckingPieces = 0;
                boolean enemyPieceEncountered = false;

                ArrayList<ArrayList<Integer>> directionList = new ArrayList<ArrayList<Integer>>();

                for (int n = 0; n < moveData[directionIndex]; n++) {

                    int squareAmount = moveData[directionIndex];
                    

                    if (friendlyPiecesCounted > 1) {
                        directionList.clear();
                        break;
                    }

                    if (nonCheckingPieces > 0) {
                        directionList.clear();
                        break;
                    }

                    int[] possibleSlidingPiecesPutKingInCheck = (directionIndex/4 == 0) ? new int[] {Pieces.Rook * opposingColor, Pieces.Queen * opposingColor} : new int[] {Pieces.Queen * opposingColor, Pieces.Bishop * opposingColor};
                    int targetIndex = kingIndex + dirOffsetsSliding[directionIndex] * (n + 1);

                    ArrayList<Integer> indexAndPiece = new ArrayList<Integer>();

                    if (!Pieces.isEmpty(targetIndex, board)) {
                        int pieceAtTarget = board.getPositionMap().get(targetIndex);
                        if (Pieces.sameColor(pieceAtTarget, color)) {
                            indexAndPiece.add(targetIndex);
                            indexAndPiece.add(pieceAtTarget);
                            friendlyPiecesCounted++;
                        } else {
                            if (inList(pieceAtTarget, possibleSlidingPiecesPutKingInCheck)) {
                                if (friendlyPiecesCounted == 0 && nonCheckingPieces == 0) {
                                    directionList.clear();
                                }
                                indexAndPiece.add(targetIndex);
                                indexAndPiece.add(pieceAtTarget);
                                enemyPieceEncountered = true;
                            } else {
                                nonCheckingPieces++;
                            }
                        }
                    } else {
                        indexAndPiece.add(targetIndex);
                        indexAndPiece.add(Pieces.Empty);
                    }

                    directionList.add(indexAndPiece);

                    if (enemyPieceEncountered) {
                        break;
                    } else {
                        if (n == squareAmount - 1) {
                            directionList.clear();
                        }
                    }

                }

                if (directionList.size() > 0) {
                    reverseRayKingList.add(directionList);
                }
            }
        }

        private static void generateKnightReverseRay(ArrayList<ArrayList<ArrayList<Integer>>> reverseRayKingList, int color, Board board) {

            int kingIndex = (color == Pieces.White) ? Board.indexFromPiece(-Pieces.King) : Board.indexFromPiece(Pieces.King);
            int[] dirOffsets = DirectionOffsets.dirOffsetsKnight;

            ArrayList<ArrayList<Integer>> knightMoves = new ArrayList<ArrayList<Integer>>();

            for (int directionIndex = 0; directionIndex < dirOffsets.length; directionIndex++) {
                int targetIndex = kingIndex + dirOffsets[directionIndex];
                ArrayList<Integer> move = new ArrayList<Integer>();
                if (!Pieces.isEmpty(targetIndex, board)) {
                    int pieceTesting = board.getPositionMap().get(targetIndex);
                    if (!Pieces.sameColor(pieceTesting, color)) {
                        move.add(targetIndex);
                        move.add(pieceTesting);
                    }
                }
                if (move.size() > 0 && isValidMoveReverseRayKing(kingIndex, targetIndex, directionIndex, board)) {
                    knightMoves.add(move);
                }
            }
            if (knightMoves.size() > 0) {
                reverseRayKingList.add(knightMoves);
            }

        }

        private static void generatePawnReverseRay(ArrayList<ArrayList<ArrayList<Integer>>> reverseRayKingList, int color, Board board) {

            int kingIndex = (color == Pieces.White) ? Board.indexFromPiece(-Pieces.King) : Board.indexFromPiece(Pieces.King);

            int[] dirOffsets = DirectionOffsets.dirOffsetsPawn;
            int sevenOffsetIndex = 2; int nineOffsetIndex = 0;
            int dirMultiplier = (color == Pieces.White) ? -1 : 1;
            int sevenOffset = dirOffsets[sevenOffsetIndex] * dirMultiplier; int nineOffset = dirOffsets[nineOffsetIndex] * dirMultiplier;

            int targetIndexSeven = kingIndex + sevenOffset;
            int targetIndexNine = kingIndex + nineOffset;

            ArrayList<ArrayList<Integer>> pawnMoves = new ArrayList<ArrayList<Integer>>();

            if (!Pieces.isEmpty(targetIndexSeven, board)) {
                ArrayList<Integer> move = new ArrayList<Integer>();
                int pieceOnTarget = board.getPositionMap().get(targetIndexSeven);
                if (pieceOnTarget == Pieces.Pawn && !Pieces.sameColor(pieceOnTarget, color) && isValidMoveReverseRayKing(kingIndex, targetIndexSeven, sevenOffsetIndex, board)) {
                    move.add(targetIndexSeven);
                    move.add(pieceOnTarget);
                    if (move.size() > 0) {
                        pawnMoves.add(move);
                    }
                }
            }

            if (!Pieces.isEmpty(targetIndexNine, board)) {
                ArrayList<Integer> move = new ArrayList<Integer>();
                int pieceOnTarget = board.getPositionMap().get(targetIndexNine);
                if (pieceOnTarget == Pieces.Pawn && !Pieces.sameColor(pieceOnTarget, color) && isValidMoveReverseRayKing(kingIndex, targetIndexNine, nineOffsetIndex, board)) {
                    move.add(targetIndexNine);
                    move.add(pieceOnTarget);
                    pawnMoves.add(move);

                }
            }

            if (pawnMoves.size() > 0) {
                reverseRayKingList.add(pawnMoves);
            }

        }

        private static void generateKingReverseRay(ArrayList<ArrayList<ArrayList<Integer>>> reverseRayKingList, int color, Board board) {
            
            int kingIndex = (color == Pieces.White) ? Board.indexFromPiece(-Pieces.King) : Board.indexFromPiece(Pieces.King);
            int[] dirOffsets = DirectionOffsets.dirOffsetsKing;

            ArrayList<ArrayList<Integer>> kingMoves = new ArrayList<ArrayList<Integer>>();

            for (int directionIndex = 0; directionIndex < dirOffsets.length; directionIndex++) {
                ArrayList<Integer> move = new ArrayList<Integer>();
                int targetIndex = kingIndex + dirOffsets[directionIndex];
                if (!Pieces.isEmpty(targetIndex, board)) {
                    int pieceAtTarget = board.getPositionMap().get(targetIndex);
                    if (pieceAtTarget == Pieces.King && !Pieces.sameColor(pieceAtTarget, color) && isValidMoveReverseRayKing(kingIndex, targetIndex, directionIndex, board)) {
                        move.add(targetIndex);
                        move.add(pieceAtTarget);
                        kingMoves.add(move);
                    }
                }
            }

            if (kingMoves.size() > 0) {
                reverseRayKingList.add(kingMoves);
            }
        }

        public static boolean isValidMoveReverseRayKing (int startingIndex, int targetIndex, int dirOffsetIndex, Board board) {

            if (targetIndex < 0 || targetIndex > 63) {return false;}

            int pieceTesting = board.getPositionMap().get(targetIndex);
            int colorUp = Pieces.sameColor(board.getPositionMap().get(startingIndex), Pieces.White) ? Pieces.White : Pieces.Black; 

            int startingIndexFile = startingIndex % 8;
            int startingIndexRank = startingIndex / 8;

            int targetIndexFile = targetIndex % 8;
            int targetIndexRank = targetIndex / 8;

            int deltaFile = targetIndexFile - startingIndexFile;
            int deltaRank = targetIndexRank - startingIndexRank;
            int[] testingDelta = {deltaFile, deltaRank};

            int offsetTesting;
            int[] validDelta = {0, 0};

            if (Pieces.isKnight(pieceTesting)) {

                offsetTesting = DirectionOffsets.dirOffsetsKnight[dirOffsetIndex];
                validDelta = DirectionOffsets.knightValidDeltaDictionary.get(offsetTesting);

            } else if (Pieces.isPawn(pieceTesting)) {

                offsetTesting = DirectionOffsets.dirOffsetsPawn[dirOffsetIndex];
                System.out.println(offsetTesting);
                int dirMultiplier = (colorUp == Pieces.White) ? -1 : 1;
                validDelta = DirectionOffsets.pawnValidDeltaDictionary.get(offsetTesting * dirMultiplier);

            } else if (Pieces.isKing(pieceTesting)) {

                offsetTesting = DirectionOffsets.dirOffsetsKing[dirOffsetIndex];
                validDelta = DirectionOffsets.kingValidDeltaDictionary.get(offsetTesting);

            }

            return Arrays.equals(testingDelta, validDelta);
        }
}
