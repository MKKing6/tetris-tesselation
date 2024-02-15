import java.awt.Color;
import paintingcanvas.canvas.Canvas;
import paintingcanvas.drawable.*;
import java.util.*;

// Write your Tessellation Class below:
class Tessellation {
  private ArrayList<Tetromino> tesselation = new ArrayList<Tetromino>();
  private ArrayList<ArrayList<Integer>> randPiece = new ArrayList<ArrayList<Integer>>();
  private int width;
  private int height;
  public int[][] grid;
  private int up;
  private int left;
  private int x;
  private int y;
  
  public Tessellation(int width, int height, int x, int y) {
    this.width = width;
    this.height = height;
    this.up = 0;
    this.left = 0;
    this.x = x;
    this.y = y;
    grid = new int[height][width];
  }

  public void draw() {
    for (int i = 0; i < tesselation.size(); i++) {
      tesselation.get(i).draw(x, y);
    }
  }

  public void createTess() {
    int x = 0;
    while (tesselation.size() < width * height / 4) {
      validCreate();
      topLeft();
      x++;
    }
    System.out.println(x);
    draw();
  }

  public void topLeft() {
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (grid[i][j] == 0) {
          up = i;
          left = j;
          return;
        }
      }
    }
  }

  private int[] shuffleArray(int[] array) {
    int index, temp;
    Random random = new Random();
    for (int i = array.length - 1; i > 0; i--) {
      index = random.nextInt(i + 1);
      temp = array[index];
      array[index] = array[i];
      array[i] = temp;
    }
    return array;
  }

  public void validCreate() {
    if (randPiece.size() == tesselation.size()) {
      ArrayList<Integer> pieceArr = new ArrayList<Integer>();
      for (int i = 1; i <= 7; i++) {
        pieceArr.add(i);
      }
      Collections.shuffle(pieceArr);
      randPiece.add(pieceArr);
    }
    
    while (!randPiece.get(randPiece.size() - 1).isEmpty()) {
      int[] rotArr = shuffleArray(new int[] {0, 1, 2, 3});
      create(randPiece.get(randPiece.size() - 1).get(0), rotArr[0], 20);
      randPiece.get(randPiece.size() - 1).remove(0);
      for (int j = 0; j <= rotArr.length; j++) {
        if (!tesselation.get(tesselation.size() - 1).check(grid)) {
          if (j == rotArr.length) break;
          tesselation.get(tesselation.size() - 1).setRot(rotArr[j]);
        }
        else {
          tesselation.get(tesselation.size() - 1).draw(x, y);
          return;
        }
      }
      tesselation.remove(tesselation.size() - 1);
    }
    randPiece.remove(randPiece.size() - 1);
    for (int i = 0; i < 4; i++) {
      tesselation.get(tesselation.size() - 1).image[i].erase();
    }
    tesselation.get(tesselation.size() - 1).removeGrid(grid);
    tesselation.remove(tesselation.size() - 1);
    topLeft();
  }

  public void create(int piece, int rot, int px) {
    switch(piece) {
      case 1:
        tesselation.add(new Z(left, up, rot % 2, px));
        break;
      case 2:
        tesselation.add(new L(left, up, rot, px));
        break;
      case 3:
        tesselation.add(new O(left, up, 0, px));
        break;
      case 4:
        tesselation.add(new S(left, up, rot % 2, px));
        break;
      case 5:
        tesselation.add(new I(left, up, rot % 2, px));
        break;
      case 6:
        tesselation.add(new J(left, up, rot, px));
        break;
      case 7:
        tesselation.add(new T(left, up, rot, px));
        break;
    }
  }
}