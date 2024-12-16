public class Runner {
    public static void main(String[] args) {
        String fenString = "rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2";
        Board board = new Board(fenString);
        System.out.println(board.getPosition());
        
    }
}
