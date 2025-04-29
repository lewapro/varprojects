import java.util.ArrayList;
import java.util.Arrays;

public class Tiles {
    private ArrayList<String> tiles = new ArrayList<>();
        Tiles(){
            tiles = new ArrayList<>(Arrays.asList(
                "a_man1","a_man1","a_man1","a_man1","a_man2","a_man2","a_man2","a_man2","a_man3","a_man3","a_man3","a_man3","a_man4","a_man4","a_man4","a_man4","a_man5","a_man5","a_man5","a_man5","a_man6","a_man6","a_man6","a_man6","a_man7","a_man7","a_man7","a_man7","a_man8","a_man8","a_man8","a_man8","a_man9","a_man9","a_man9","a_man9",
                     "b_pin1","b_pin1","b_pin1","b_pin1","b_pin2","b_pin2","b_pin2","b_pin2","b_pin3","b_pin3","b_pin3","b_pin3","b_pin4","b_pin4","b_pin4","b_pin4","b_pin5","b_pin5","b_pin5","b_pin5","b_pin6","b_pin6","b_pin6","b_pin6","b_pin7","b_pin7","b_pin7","b_pin7","b_pin8","b_pin8","b_pin8","b_pin8","b_pin9","b_pin9","b_pin9","b_pin9",
                     "c_sou1","c_sou1","c_sou1","c_sou1","c_sou2","c_sou2","c_sou2","c_sou2","c_sou3","c_sou3","c_sou3","c_sou3","c_sou4","c_sou4","c_sou4","c_sou4","c_sou5","c_sou5","c_sou5","c_sou5","c_sou6","c_sou6","c_sou6","c_sou6","c_sou7","c_sou7","c_sou7","c_sou7","c_sou8","c_sou8","c_sou8","c_sou8","c_sou9","c_sou9","c_sou9","c_sou9",
                     "d_east","d_east","d_east","d_east","e_south","e_south","e_south","e_south","f_west","f_west","f_west","f_west","g_north","g_north","g_north","g_north",
                     "h_whitedragon","h_whitedragon","h_whitedragon","h_whitedragon","i_greendragon","i_greendragon","i_greendragon","i_greendragon","j_reddragon","j_reddragon","j_reddragon","j_reddragon"
                ));
        }
    public ArrayList<String> tiles(){
        return tiles;
    }
    public int size(){
        return tiles.size();
    }
    public String get(int index){
        return tiles.get(index);
    }
    public void remove(int index){
        tiles.remove(index);
    }
    
    // ------------------------- DEADWALL
    private ArrayList<String> deadWall = new ArrayList<>(14);
    public void fillDeadWall(){
        while (deadWall.size()<14){
            int i;
            i = (int)(Math.random() * tiles.size());
            deadWall.add(tiles.get(i));
            tiles.remove(i);
        }
        deadWall.sort(null);
    }
    public ArrayList<String> deadWall(){
        return deadWall;
    }
    public int sizeDeadWall(){
        return deadWall.size();
    }
    public String getDeadWall(int index){
        return deadWall.get(index);
    }
}
