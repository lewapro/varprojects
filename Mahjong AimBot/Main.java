

public class Main {
    public static void main(String[] args) {
       Tiles tiles = new Tiles();
        Player player1 = new Player(tiles.tiles());
        Player player2 = new Player(tiles.tiles());
        Player player3 = new Player(tiles.tiles());
        Player player4 = new Player(tiles.tiles());
        
        
        
        
        tiles.fillDeadWall();
        System.out.println("Dead wall contains " + tiles.deadWall());
        System.out.println("Revealed dora = " + tiles.getDeadWall(0));

        
        player1.fillHandStart();
        player2.fillHandStart();
        player3.fillHandStart();
        player4.fillHandStart();
        player1.forceHand("a_man1, a_man2, a_man3, a_man3, a_man4, a_man4, a_man5, a_man5, a_man6, a_man7, a_man7, a_man7, a_man8, a_man9");
        System.out.println("Player 1: " + player1.getHand());
        // ----


        
        player1.getHand();
        player1.checkHand();
    } 
}
