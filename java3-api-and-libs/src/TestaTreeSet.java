package src;

import java.util.*;

public class TestaTreeSet {
  public static void main(String[] args) {
    TreeSet<Integer> tree = new TreeSet<Integer>();

    int max = 50;

    for(int i = 0; i < max; i++) {
      tree.add(i);
    }

    Iterator<Integer> treeInterator = tree.descendingIterator();
    while(treeInterator.hasNext()) {
      System.out.println(treeInterator.next());
    }

  }
}
