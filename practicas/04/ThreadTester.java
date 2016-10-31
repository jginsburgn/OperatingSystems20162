//Los arreglos se inicializan en orden invertido para que los resultados de cada entrada coincidan
public class ThreadTester {
  static int[] a = new int[100];
  static int[] b = new int[100];
  static int[] c = new int[100];
  public static void main(String[] args) {
    for (int i = 0; i < 100; i++) {
      a[i] = (i+1);
      b[i] = 100-i;
      c[i] = 0;
      //System.out.println(a[i] + ":" + b[i] + ":" + c[i]);
    }
    ElementAdder threads[] = new ElementAdder[100];
    for (int i = 0; i < 100; i++) {
      threads[i] = new ElementAdder(i, a, b, c);
      threads[i].start();
    }
    for (int i = 0; i < 100; i++) {
      System.out.println(c[i]);
    }
  }
}
