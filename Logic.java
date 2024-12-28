public class Logic {
    
    public Logic () {}

    public void start () {
        Board board = new Board("P7/8/8/8/8/6p1/7P/8");
        System.out.println(board.getPosition());
        System.out.println();
        System.out.println(Moves.generateMoves(board)); 
    }
}