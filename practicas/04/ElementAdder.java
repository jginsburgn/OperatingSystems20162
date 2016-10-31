class ElementAdder extends Thread
{
  private int processNumber = -1;
  private int[] a, b, c;

  public ElementAdder(int n, int[] newA, int[] newB, int[] newC){
    processNumber = n;
    a = newA;
    b = newB;
    c = newC;
  }

  public void run()
  {
    c[processNumber] = a[processNumber] + b[processNumber];
  }
}
