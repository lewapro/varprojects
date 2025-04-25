import java.util.ArrayList;

public class Player {
 //   private boolean dealer;
    private ArrayList<String> tiles;
    private ArrayList<String> hand = new ArrayList<String>(14);
    Player(ArrayList<String> tiles){
        this.tiles = tiles;
    }
    public void fillHandStart(){
        while(hand.size()<13){
            int rand;
            rand = (int)(Math.random() * tiles.size());
            hand.add(tiles.get(rand));
            tiles.remove(rand);
        }
        hand.sort(null);
    }
    public void fillHand(){
        if(hand.size()<14){
        int rand;
        rand = (int)(Math.random() * tiles.size());
        hand.add(tiles.get(rand));
        tiles.remove(rand);
        }
    }
    public ArrayList<String> getHand(){
        return hand;
    }
    // ------- DEBUG
    public void forceHand(String tiles){
        String[] cutTiles = tiles.split(",");
        hand.removeAll(hand);
        for(String cutTile: cutTiles){
            hand.add(cutTile.strip());
        }
        hand.sort(null);
    }
    // Пока что есть вариант такой: после получении руки все тайлы разделяются на свои личные ArrayList - man pin sou east south... затем проверяется их длина и если она больше трёх, то идёт
    // последующий поиск правильности комбинации (3 одинаковых (kōtsu) или 3 последовательных(shuntsu)), после того как счётчик групп(mentsu) доходит до 4, идёт поиск дубля. 
    // Пока что есть проблема в том, что я тупой и думаю, что если вдруг в начале он посчитает ментзу не там где надо.
    private ArrayList<String> manInHand = new ArrayList<String>();
    private ArrayList<String> pinInHand = new ArrayList<String>();
    private ArrayList<String> souInHand = new ArrayList<String>();
    private ArrayList<String> eastInHand = new ArrayList<String>();
    private ArrayList<String> southInHand = new ArrayList<String>();
    private ArrayList<String> westInHand = new ArrayList<String>();
    private ArrayList<String> northInHand = new ArrayList<String>();
    private ArrayList<String> whiteInHand = new ArrayList<String>();
    private ArrayList<String> greenInHand = new ArrayList<String>();
    private ArrayList<String> redInHand = new ArrayList<String>();
    private ArrayList<String> mentsuInHand = new ArrayList<String>();
    private ArrayList<String> pairInHand = new ArrayList<String>();
    private int pairCounter = 0;
    private int pair = 0;
    private int mentsu = 0;
    private int tileCounter = 0;
    public void checkHand(){
        for(String tile: hand){
            if(tile.contains("man")){
                manInHand.add(tile);
            } 
            else if(tile.contains("pin")){
                pinInHand.add(tile);
            }
            else if(tile.contains("sou")){
                souInHand.add(tile);
            }
            }
            System.out.println("Hand has: \n" + manInHand + " man, \n" + pinInHand + " pin, \n" + souInHand + " sou.");
            if(manInHand.size()>=2){ // ПОИСК ДУБЛЕЙ!!
                for(String tile: manInHand){
                    tileCounter++;
                    System.out.println("looking for a double! " + manInHand.get(tileCounter-1+pairCounter) + " " + manInHand.get(tileCounter+pairCounter));
                    if(manInHand.get(tileCounter-1+pairCounter).charAt(tile.length()-1)==manInHand.get(tileCounter+pairCounter).charAt(tile.length()-1)){
                        System.out.println("there is a double! " + (tileCounter-1+pairCounter) + " " + (tileCounter+pairCounter));
                        pairInHand.add(manInHand.get(tileCounter+pairCounter));
                        pairInHand.add(manInHand.get(tileCounter-1+pairCounter));
                        manInHand.remove(tileCounter+pairCounter);
                        manInHand.remove(tileCounter-1+pairCounter);
                        pair+=1;
                        pairCounter = tileCounter+1;
                        break;
                    }
                    
                }
            }
             for(int i = 0; i<manInHand.size();i++){
                tileCounter=0;
                for(String tile: manInHand){
                    if(manInHand.size()>=3){
                        tileCounter++;
                        if(tile.charAt(tile.length()-1)==(char)((manInHand.get(tileCounter).charAt(tile.length()-1))-1)
                        && tile.charAt(tile.length()-1)==(char)((manInHand.get(tileCounter+1).charAt(tile.length()-1))-2)){
                            System.out.println("succsex");
                            mentsuInHand.add(manInHand.get(tileCounter-1));
                            mentsuInHand.add(manInHand.get(tileCounter));
                            mentsuInHand.add(manInHand.get(tileCounter+1));
                            manInHand.remove(tileCounter+1);
                            manInHand.remove(tileCounter);
                            manInHand.remove(tileCounter-1);
                            mentsu++;
                            break;
                        } // аут оф баундс почему то
                    }

            }
        }
        System.out.println("non mentsu: \nMAN: " + manInHand.size() + manInHand + "\nPIN: " + pinInHand.size() + pinInHand + "\nSOU: "+ souInHand.size() + souInHand + "\nMENTSU: " + mentsu + mentsuInHand + "\nPAIR: " + pair + pairInHand);

        
    }
}
