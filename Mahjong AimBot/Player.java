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
    public void fillHand(){ //  в будущем должна быть проверка на открытую руку и остаток тайлов в раунде
        if(hand.size()<14){ //  проверка на открытую руку через "&& openHand == false", затем через else if также проверяется количество тайлов в открытой руке (не считая кана).
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
    private ArrayList<String> manInHand = new ArrayList<String>();      // манзу в руке
    private ArrayList<String> pinInHand = new ArrayList<String>();      // пинзу в руке
    private ArrayList<String> souInHand = new ArrayList<String>();      // соузу в руке
    private ArrayList<String> eastInHand = new ArrayList<String>();     // восток в руке
    private ArrayList<String> southInHand = new ArrayList<String>();    // юг в руке
    private ArrayList<String> westInHand = new ArrayList<String>();     // запад в руке
    private ArrayList<String> northInHand = new ArrayList<String>();    // север в руке
    private ArrayList<String> whiteInHand = new ArrayList<String>();    // белые драконы в руке
    private ArrayList<String> greenInHand = new ArrayList<String>();    // зелёные драконы в руке
    private ArrayList<String> redInHand = new ArrayList<String>();      // красные драконы в руке
    private ArrayList<String> mentsuInHand = new ArrayList<String>();   // ментсу составленные из выше перечисленного
    private ArrayList<String> pairInHand = new ArrayList<String>();     // пары в руке
    private int pairCounter = 7; // счётчик для повторных пар
    private int pair = 0;        // пары
    private int mentsu = 0;      // готовые триплеты/последовательности
    private int tileCounter = 0; // универсальный счётчик
    public void checkHand(){
        for(int i = 0; i<pairCounter+1;i++){
            manInHand.clear(); pinInHand.clear(); souInHand.clear(); mentsu = 0; mentsuInHand.clear(); pair = 0; pairInHand.clear();
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
                if(manInHand.size()>=2){ // ПОИСК ДУБЛЕЙ!! Нужно поменять на поиск ВСЕХ мастей
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
                for(int j = 0; j<manInHand.size();j++){             // нужно изменить на поиск по .contains() и сбор через .indexOf(). Текущая версия не видит дальше +-1 и поэтому есть ошибки.
                    tileCounter=0;
                    for(String tile: manInHand){
                        if(manInHand.size()>=3){
                            System.out.println((char)(tile.charAt(tile.length()-1)+1));
                            System.out.println(manInHand.indexOf(tile));
                            tileCounter++;
                            if(tileCounter+1<manInHand.size() // анти проверка на 10й тайл
                            && tile.charAt(tile.length()-1)==(char)((manInHand.get(tileCounter).charAt(tile.length()-1))-1) // проверка, что следующий тайл на один меньше
                            && tile.charAt(tile.length()-1)==(char)((manInHand.get(tileCounter+1).charAt(tile.length()-1))-2)){ // проверка, что последующий тайл на два меньше
                                System.out.println("Adding tiles: " + manInHand.get(tileCounter-1) + ", " + manInHand.get(tileCounter) + ", " + manInHand.get(tileCounter+1));
                                mentsuInHand.add(manInHand.get(tileCounter-1));
                                mentsuInHand.add(manInHand.get(tileCounter));
                                mentsuInHand.add(manInHand.get(tileCounter+1));
                                manInHand.remove(tileCounter+1);
                                manInHand.remove(tileCounter);
                                manInHand.remove(tileCounter-1);
                                mentsu++; // отбираем тайлы в руку ментзу и забираем из основной для следующий вычислений.
                                System.out.println("non mentsu: \nMAN: " + manInHand.size() + manInHand + "\nPIN: " + pinInHand.size() + pinInHand + "\nSOU: "+ souInHand.size() + souInHand + "\nMENTSU: " + mentsu + mentsuInHand + "\nPAIR: " + pair + pairInHand);
                                break;    // также здесь можно будет добавить проверки на то, что собрано для вычислений Яку, например инт на шунтсу для пинфу на примере ментсу
                            } 
                        }

                }
                if(mentsu == 4 && pair == 1){
                    j = 24;
                    System.out.println("TSUMO READY");
            }
            }
        }
        System.out.println("non mentsu: \nMAN: " + manInHand.size() + manInHand + "\nPIN: " + pinInHand.size() + pinInHand + "\nSOU: "+ souInHand.size() + souInHand + "\nMENTSU: " + mentsu + mentsuInHand + "\nPAIR: " + pair + pairInHand);
        System.out.println("end");
        
    }
}
