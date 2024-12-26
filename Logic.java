public class Logic {
    
    public Logic () {}

    public void start () {
        Board board = new Board(Board.getStartingFen());
        System.out.println(board.getPosition());
        for (int i = 0; i < 64; i++) {
            System.out.println(i);
            System.out.println(Moves.generateSlidingMoves(i, board));
        }
    }
}