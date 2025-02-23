import java.util.Scanner;

public class Logic {
    
    public Logic () {}

    public void start () {
        Scanner scan = new Scanner(System.in);
        Board board = new Board(Board.getStartingFen());
        System.out.println(board.getPositionMap());
        System.out.println();
        Moves.generatePseudoLegalMoves(board);
        System.out.println("-----------------------------");
        System.out.println("Black Moves: " + Moves.blackMoveList);
        System.out.println("-----------------------------");
        System.out.println();
        System.out.println("-----------------------------");
        System.out.println("White Moves: " + Moves.whiteMoveList);
        System.out.println("-----------------------------");
        System.out.println();
        System.out.println("-----------------------------");


        int response = 0;
        while (response != -1) {
            // System.out.println("White Moves:  " + Moves.whiteMoveList);
            // System.out.println("Black Moves:  " + Moves.blackMoveList);
            System.out.println(board.getPositionMap());
            System.out.println("FEN STRING: " + board.positionToFEN(board.getPositionMap()));
            int[] move = new int[2];
            System.out.println();
            System.out.println(Moves.enPassantDiscardMap);
            System.out.print("Enter a Starting Index: -1 to exit: ");
            response = scan.nextInt();
            scan.nextLine();
            System.out.print("Enter an Ending Index: ");
            int endingIndex = scan.nextInt();
            scan.nextLine();
            move[0] = response;
            move[1] = endingIndex;
            Game.addMove(move);
            board.setPosition(Game.updatePosition(board.getPositionMap(), move));

            Moves.generatePseudoLegalMoves(board);
            // ReverseRay.updateReverseRayKingList(Pieces.White, board, false); // this needs to be run before generate moves
            // ReverseRay.updateReverseRayKingList(Pieces.Black, board, false);

            //Moves.splitPseudoLegalMovesByColor(board);
            //System.out.println("Black Moves:  " + Moves.blackMoveList);
            System.out.println("-----------------------------");
            System.out.println("Black Moves: " + Moves.blackMoveList);
            System.out.println("-----------------------------");
            System.out.println();
            System.out.println("-----------------------------");
            System.out.println("White Moves: " + Moves.whiteMoveList);
            System.out.println("-----------------------------");
            System.out.println();
            System.out.println("-----------------------------");
            // System.out.println("White King in Check: " + Legalization.isKingInCheck(Pieces.White, false));
            // System.out.println("Black King in Check: " + Legalization.isKingInCheck(Pieces.Black, false));
            System.out.println("Discard List" + Moves.discardIndexList);
            System.out.println("-----------------------------");
            System.out.println("Castling List: " + Moves.castlingMoveList);
            System.out.println("-----------------------------");
            System.out.println("Reverse Ray List for White King: " + ReverseRay.reverseRayKingList(Pieces.White, board));
            
            // System.out.println("White King: " + Moves.generateCastlingMoves(Pieces.WHITE_KING_START_INDEX, board));
            // System.out.println("Black King: " + Moves.generateCastlingMoves(Pieces.BLACK_KING_START_INDEX, board));
            //System.out.println("All possible moves: " + Moves.generatePseudoLegalMoves (board));
            // System.out.println("Psuedo Legal Moves Available: " + Game.moveCounter(Moves.generatePseudoLegalMoves(board)));

        }
        scan.close();

    }
}