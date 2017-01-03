class ThreeNPlusOne{
  public static void main(String[] args){
    int n = 42;
    int c = 1;
    while(n != 1){
      System.out.print(n);
      System.out.print(" > ");
      if(n % 2 == 0){
        n = n/2;
      } else {
        n = 3*n + 1;
      }
      c++;
    }
    System.out.print(n);
    System.out.println("\nCycle Length : " + c);
  }
}
