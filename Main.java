import java.awt.Color;
import paintingcanvas.canvas.Canvas;
import paintingcanvas.drawable.*;
import java.util.*;

class Main {
  private static Canvas canvas;
  
  public static void main(String[] args)   
  {  
    canvas = new Canvas(1000, 1200, "Tessellation Project");
    
    // Draw a single tile for testing
    //Z piece = new Z(1, 1, 0, 20);
    //piece.draw();

    // Draw the tessellation:
    Tessellation tess = new Tessellation(23, 16, 0, 0);
    tess.createTess();
    // canvas.sleep(2);
    // tess.changeColorInRow(Color.blue);
    // canvas.sleep(2);
    // tess.setRandomColor();
    // canvas.sleep(2);
    // tess.diagonalColors(Color.cyan, Color.gray);
    // canvas.sleep(2);
    // tess.magentaGradient();
    // canvas.sleep(2);
    // tess.animateRow(1);
    // tess.eraseRightToLeft();
    // canvas.sleep(2);
    // tess = new Tessellation(5, 10);
    // tess.eraseRandom();

    // Optional extension
    // tess = new Tessellation(5, 10);
    // tess.eraseSpiral();

  }

  public static Canvas getCanvas() 
  {
    return canvas;
  }
}