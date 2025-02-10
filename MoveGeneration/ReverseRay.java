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

        public static ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> reverseRayKingList (int color, Board board) { // maybe split up the method into checking for orthogonal and diagonal pieces

            ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> reverseRayKingList = new ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>>();
            
            ArrayList<ArrayList<ArrayList<Integer>>> slidingReverseRay = generateSlidingReverseRay(color, board);
            ArrayList<ArrayList<ArrayList<Integer>>> knightReverseRay = generateKnightReverseRay(color, board);
            ArrayList<ArrayList<ArrayList<Integer>>> pawnReverseRay = generatePawnReverseRay(color, board);
            ArrayList<ArrayList<ArrayList<Integer>>> kingReverseRay = generateKingReverseRay(color, board);

            if (slidingReverseRay.size() > 0) {
                reverseRayKingList.add(slidingReverseRay);
            }
            if (knightReverseRay.size() > 0) {
                reverseRayKingList.add(knightReverseRay);
            }
            if (pawnReverseRay.size() > 0) {
                reverseRayKingList.add(pawnReverseRay);
            }
            if (kingReverseRay.size() > 0) {
                reverseRayKingList.add(kingReverseRay);
            }

            return reverseRayKingList;
        }

        private static ArrayList<ArrayList<ArrayList<Integer>>> generateSlidingReverseRay(int color, Board board) {

            int kingIndex = (color == Pieces.White) ? board.getIndexOfWhiteKing() : board.getIndexOfBlackKing();
            int opposingColor = (color == Pieces.White) ? Pieces.Black : Pieces.White;
            int[] dirOffsetsSliding = DirectionOffsets.dirOffsetsSliding;
            int[] moveData = Pieces.moveData(kingIndex);
            ArrayList<ArrayList<ArrayList<Integer>>> slidingMoves = new ArrayList<ArrayList<ArrayList<Integer>>>();
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
                if (directionList.size() > 0 ) { // HSFHGSDHJGFGH
                    slidingMoves.add(directionList);
                }
            }
            return slidingMoves;
        }

        private static ArrayList<ArrayList<ArrayList<Integer>>> generateKnightReverseRay(int color, Board board) {

            int kingIndex = (color == Pieces.White) ? board.getIndexOfWhiteKing() : board.getIndexOfBlackKing();
            int[] dirOffsets = DirectionOffsets.dirOffsetsKnight;
            ArrayList<ArrayList<ArrayList<Integer>>> knightReverseRay = new ArrayList<ArrayList<ArrayList<Integer>>>();

            ArrayList<ArrayList<Integer>> knightMoves = new ArrayList<ArrayList<Integer>>();

            for (int directionIndex = 0; directionIndex < dirOffsets.length; directionIndex++) {
                int targetIndex = kingIndex + dirOffsets[directionIndex];
                ArrayList<Integer> move = new ArrayList<Integer>();
                if (!Pieces.isEmpty(targetIndex, board)) {
                    int pieceTesting = board.getPositionMap().get(targetIndex);
                    if (!Pieces.sameColor(pieceTesting, color) && Pieces.isKnight(pieceTesting)) {
                        move.add(targetIndex);
                        move.add(pieceTesting);
                    }
                }
                if (move.size() > 0 && isValidMoveReverseRayKing(kingIndex, targetIndex, directionIndex, board)) {
                    knightMoves.add(move);
                }
                
            }
            if (knightMoves.size() > 0) {
                knightReverseRay.add(knightMoves);
            }
            return knightReverseRay;

        }

        private static ArrayList<ArrayList<ArrayList<Integer>>> generatePawnReverseRay(int color, Board board) {

            int kingIndex = (color == Pieces.White) ? board.indexFromPiece(-Pieces.King) : board.indexFromPiece(Pieces.King);

            int[] dirOffsets = DirectionOffsets.dirOffsetsPawn;
            int sevenOffsetIndex = 2; int nineOffsetIndex = 0;
            int dirMultiplier = (color == Pieces.White) ? -1 : 1;
            int sevenOffset = dirOffsets[sevenOffsetIndex] * dirMultiplier; int nineOffset = dirOffsets[nineOffsetIndex] * dirMultiplier;

            int targetIndexSeven = kingIndex + sevenOffset;
            int targetIndexNine = kingIndex + nineOffset;

            ArrayList<ArrayList<ArrayList<Integer>>> pawnMovesReverseRay = new ArrayList<ArrayList<ArrayList<Integer>>>();
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
                pawnMovesReverseRay.add(pawnMoves);
            }

            return pawnMovesReverseRay;

        }

        private static ArrayList<ArrayList<ArrayList<Integer>>> generateKingReverseRay(int color, Board board) {
            
            int kingIndex = (color == Pieces.White) ? board.getIndexOfWhiteKing() : board.getIndexOfBlackKing();
            int[] dirOffsets = DirectionOffsets.dirOffsetsKing;

            ArrayList<ArrayList<ArrayList<Integer>>> kingReverseRay = new ArrayList<ArrayList<ArrayList<Integer>>>();
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
                kingReverseRay.add(kingMoves);
            }

            return kingReverseRay;
        }

        public static boolean isValidMoveReverseRayKing (int startingIndex, int targetIndex, int dirOffsetIndex, Board board) {

            if (targetIndex < 0 || targetIndex > 63) {return false;}

            int pieceTesting = board.getPositionMap().get(targetIndex);
            System.out.println("KingIndex: " + startingIndex);
            int colorUp = Pieces.sameColor(board.getPositionMap().get(startingIndex), Pieces.White) ? Pieces.White : Pieces.Black; 
            if (Pieces.sameColor(pieceTesting, colorUp)) {return false;}

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
