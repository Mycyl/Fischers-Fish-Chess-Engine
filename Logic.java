public class Logic {
    
    public Logic () {}

    public void start () {
        Board board = new Board("rnbqkbnr/pppppppp/8/3Q4/8/8/PPPPPPPP/RNBQKBNR");
        System.out.println(board.getPosition());
        for (int i = 0; i < 64; i++) {
            if (board.getPosition().get(i) == Pieces.Empty) {
                continue;
            }
            System.out.println(i);
            if (Pieces.isSlidingPiece(board.getPosition().get(i))) {
                System.out.println(Moves.generateSlidingMoves(i, board));
            }
        }
    }
}