public class Logic {
    
    public Logic () {}

    public void start () {
        Board board = new Board("rnbqkbnr/pppppppp/8/3Q4/8/8/PPPPPPPP/RNBQKBNR");
        System.out.println(board.getPosition());
        System.out.println();
        System.out.println(Moves.generateMoves(board)); 
    }
}