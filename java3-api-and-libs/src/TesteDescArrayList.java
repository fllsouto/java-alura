package src;

import java.util.*;

public class TesteDescArrayList {
  public static void main(String[] args) {
    ArrayList<Integer> list = new ArrayList<>();

    int max = 50;

    for(int i = 0; i < max; i++) {
      list.add(i);
    }
    Collections.sort(list);
    Collections.reverse(list);
    System.out.println(list);
  }
}
