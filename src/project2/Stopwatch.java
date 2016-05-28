
package project2;

public class Stopwatch { 

     private long startTime = 0;
  private long stopTime = 0;
  private boolean running = false;


  public void start() {
    this.startTime = System.currentTimeMillis();
    this.running = true;
  }


  public void stop() {
    this.stopTime = System.currentTimeMillis();
    this.running = false;
  }




    public double elapsedTime() {
    double elapsed;
    if (running) {
      elapsed = ((System.currentTimeMillis() - startTime));
    }
    else {
      elapsed = ((stopTime - startTime));
    }
    return elapsed;
    }

} 