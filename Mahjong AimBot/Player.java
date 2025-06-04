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
    private ArrayList<Integer> manInHand = new ArrayList<Integer>();      // манзу в руке
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
    private int pairCounter = 0;//7 // счётчик для повторных пар
    private int pair = 0;        // пары
    private int mentsu = 0;      // готовые триплеты/последовательности
    private int tileCounter = 0; // универсальный счётчик
    private int doubleCheck = 1;
    public void checkHand(){
        for(int i = 0; i<pairCounter+1;i++){
            manInHand.clear(); pinInHand.clear(); souInHand.clear(); mentsu = 0; mentsuInHand.clear(); pair = 0; pairInHand.clear();
            for(String tile: hand){
                if(tile.contains("man")){
                    manInHand.add(Integer.parseInt(tile.substring(tile.length()-1)));
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
                    if(doubleCheck>=1){
                        System.out.println("Double check initiated!!!!!!!!!!!!!!!!!");
                        System.out.println(doubleCheck + "!!!!!!!!");
                        doubleCheck-=1;
                    for(Integer tile: manInHand){
                        tileCounter++;
                        System.out.println("looking for a double! " + manInHand.get(tileCounter-1+pairCounter) + " " + manInHand.get(tileCounter+pairCounter));
                        if(manInHand.get(tileCounter-1+pairCounter).equals(manInHand.get(tileCounter+pairCounter))){
                            System.out.println("there is a double! " + (manInHand.get(tileCounter+pairCounter) + " " + (manInHand.get(tileCounter-1+pairCounter))));
                            pairInHand.add("a_man" + (manInHand.get(tileCounter+pairCounter)));
                            pairInHand.add("a_man" + (manInHand.get(tileCounter-1+pairCounter)));
                            manInHand.remove(tileCounter+pairCounter);
                            manInHand.remove(tileCounter-1+pairCounter);
                            pair+=1;
                            pairCounter = tileCounter+1;
                            doubleCheck-=1;
                            if(pairInHand.isEmpty()){
                                doubleCheck+=2;
                                break;
                            }
                            break;
                        }
                    }
                    }
                }
                if(pair!=1){
                    doubleCheck+=1;
                } else
                for(int j = 0; j<14;j++){             // нужно изменить на поиск по .contains() и сбор через .indexOf(). Текущая версия не видит дальше +-1 и поэтому есть ошибки.
                    tileCounter=-1;
                    for(tileCounter++; tileCounter!=manInHand.size()-2;tileCounter++){
                        if(manInHand.size()>=3){
                            int tile = manInHand.get(tileCounter);
                            if(tileCounter<manInHand.size()){
                                if(manInHand.contains(tile) && manInHand.contains(tile+1) && manInHand.contains(tile+2)){
                                    System.out.println("TILE1: " + (tile) + " TILE2: " + (tile+1) + " TILE3: " + (tile+2));
                                    mentsu++;
                                    mentsuInHand.add("a_man" + manInHand.get(manInHand.indexOf(tile)));
                                    mentsuInHand.add("a_man" + manInHand.get(manInHand.indexOf(tile+1)));
                                    mentsuInHand.add("a_man" + manInHand.get(manInHand.indexOf(tile+2)));
                                    manInHand.remove(manInHand.indexOf(tile+2));
                                    manInHand.remove(manInHand.indexOf(tile+1));
                                    manInHand.remove(manInHand.indexOf(tile));
                                    System.out.println("MENTSU: " + mentsu + mentsuInHand);
                                    System.out.println(manInHand);
                                    tileCounter-=3;
                                    break;
                                } 

                            } 
                                
                            /*&& tile.charAt(tile.length()-1)==(char)((manInHand.get(tileCounter).charAt(tile.length()-1))-1) // проверка, что следующий тайл на один меньше
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
                                break;    // также здесь можно будет добавить проверки на то, что собрано для вычислений Яку, например инт на шунтсу для пинфу на примере ментсу */
                            //} 
                        }

                }
                System.out.println(mentsu + ":" + pair);
                if(mentsu != 4 && pair != 1){
                    System.out.println("tets");
            }
                
            }
        }
        System.out.println("non mentsu: \nMAN: " + manInHand.size() + manInHand + "\nPIN: " + pinInHand.size() + pinInHand + "\nSOU: "+ souInHand.size() + souInHand + "\nMENTSU: " + mentsu + mentsuInHand + "\nPAIR: " + pair + pairInHand);
        System.out.println("end");
        
    }
}
