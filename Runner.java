public class Runner {
    public static void main(String[] args) {
        Board board = new Board(Board.getStartingFen());
        System.out.println(board.getPosition());
        for (int number : Pieces.moveData(24)) {
            System.out.println(number);
        }
    }
}
