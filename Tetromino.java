import java.awt.Color;
import paintingcanvas.canvas.Canvas;
import paintingcanvas.drawable.*;
import java.util.*;

class Tetromino {
  protected int x;
  protected int y;
  protected int rot;
  protected int px;
  protected int piece;
  protected Image[] image = new Image[4]; 
  protected int[][][] pieces;
  protected int[][] currentRot;
  
  public Tetromino(int x, int y, int rot, int px) {
    this.x = x;
    this.y = y;
    this.rot = rot;
    this.px = px;
  }

  public void setRot(int rot) {
    this.rot = rot;
  }

  public boolean check(int[][] grid) {
    if (canFit(grid)) {
      addGrid(grid);
      if (!holeChecker(grid)) {
        removeGrid(grid);
        return false;
      } 
      if (!splitCheck(grid)) {
        removeGrid(grid);
        return false;
      } 
      return true;
    }
    return false;
  }

  public void draw(int drawX, int drawY) {
    return;
  }

  public void addGrid(int[][] grid) {
    for (int i = 0; i < 4; i++) {
      grid[y + currentRot[i][1]][x + currentRot[i][0]] = piece;
    }
  }

  public void removeGrid(int[][] grid) {
    for (int i = 0; i < 4; i++) {
      grid[y + currentRot[i][1]][x + currentRot[i][0]] = 0;
    }
  }

  private boolean splitCheck(int[][] grid) {
    for (int i = 0; i < 4; i++) {
      if (y + currentRot[i][1] == grid.length - 1) {
        int split = 0;
        for (int c = y; c < grid.length; c++) {
          if (piece == 1 && rot == 1) {
            for (int r = x - 1; r >= 0; r--) {
              if (grid[c][r] == 0) split++;
            }
          }
          else {
            for (int r = x; r >= 0; r--) {
              if (grid[c][r] == 0) split++;
            }
          }
        }
        if (split % 4 == 0) {
          return true;
        }
        return false;
      }
    }
    return true;
  }

  private boolean holeChecker(int[][] grid) {
    for (int i = 0; i < 4; i++) {
      if (holeCheckR(grid, x + currentRot[i][0] + 1, y + currentRot[i][1])) {
        removeGrid(grid);
        return false;
      }
      if (holeCheckL(grid, x + currentRot[i][0] - 1, y + currentRot[i][1])) {
        removeGrid(grid);
        return false;
      }
    }
    if (rot == 0 && piece == 6) {
      if (holeCheckR(grid, x + currentRot[0][0] + 2, y + currentRot[0][1])) {
        removeGrid(grid);
        return false;
      }
    }
    return true;
  }

  public boolean holeCheckR(int[][] grid, int checkX, int checkY) {
    if (checkX >= grid[0].length || grid[checkY][checkX] != 0) return false;
    int ct = 0;
    if (checkY + 1 >= grid.length) return false;
    while (grid[checkY + 1][checkX + ct] != 0) {
      if (checkX + ct + 1 >= grid[0].length || grid[checkY][checkX + ct + 1] != 0) return true;
      ct++;
    }
    return false;
  }

  public boolean holeCheckL(int[][] grid, int checkX, int checkY) {
    if (checkX < 0 || grid[checkY][checkX] != 0) return false;
    int ct = 0;
    if (checkY + 1 >= grid.length) return false;
    while (grid[checkY + 1][checkX - ct] != 0) {
      if (checkX - ct - 1 < 0 || grid[checkY][checkX - ct - 1] != 0) return true;
      ct++;
    }
    return false;
  }

  public boolean canFit(int[][] grid) {
    for (int i = 0; i < 4; i++) {
      if (x + currentRot[i][0] < 0 || x + currentRot[i][0] >= grid[0].length) return false;
      if (y + currentRot[i][1] < 0 || y + currentRot[i][1] >= grid.length) return false;
      if (grid[y + currentRot[i][1]][x + currentRot[i][0]] != 0) {
        return false;
      }
    }
    return true;
  }
}

class Z extends Tetromino {
  public Z(int x, int y, int rot, int px) {
    super(x, y, rot, px);
    piece = 1;
    this.rot = rot % 2;
    pieces = new int[][][] {
      {{0, 0}, {1, 0}, {1, 1}, {2, 1}},
      {{0, 0}, {0, 1}, {-1, 1}, {-1, 2}}
    };
    currentRot = pieces[this.rot];
  }

  public void draw(int drawX, int drawY) {
    for (int i = 0; i < 4; i++) {
      image[i] = new Image((drawX + x + currentRot[i][0]) * px + px/2, (drawY + y + currentRot[i][1]) * px + px/2, "Z.png");
      image[i].setScale((double)px/30, (double)px/30);
    }
  }

  public void setRot(int rot) {
    this.rot = rot % 2;
    currentRot = pieces[this.rot];
  }
}

class L extends Tetromino {
  public L(int x, int y, int rot, int px) {
    super(x, y, rot, px);
    piece = 2;
    pieces = new int[][][] {
      {{0, 0}, {0, 1}, {-1, 1}, {-2, 1}},
      {{0, 0}, {0, 1}, {0, 2}, {1, 2}},
      {{0, 0}, {1, 0}, {2, 0}, {0, 1}},
      {{0, 0}, {1, 0}, {1, 1}, {1, 2}}
    };
    currentRot = pieces[this.rot];
  }

  public void draw(int drawX, int drawY) {
    for (int i = 0; i < 4; i++) {
      image[i] = new Image((drawX + x + currentRot[i][0]) * px + px/2, (drawY + y + currentRot[i][1]) * px + px/2, "L.png");
      image[i].setScale((double)px/30, (double)px/30);
    }
  }

  public void setRot(int rot) {
    this.rot = rot;
    currentRot = pieces[this.rot];
  }
}

class O extends Tetromino {
  public O(int x, int y, int rot, int px) {
    super(x, y, rot, px);
    this.rot = 0;
    piece = 3;
    pieces = new int[][][] {
      {{0, 0}, {1, 0}, {0, 1}, {1, 1}}
    };
    currentRot = pieces[this.rot];
  }

  public void draw(int drawX, int drawY) {
    for (int i = 0; i < 4; i++) {
      image[i] = new Image((drawX + x + currentRot[i][0]) * px + px/2, (drawY + y + currentRot[i][1]) * px + px/2, "O.png");
      image[i].setScale((double)px/30, (double)px/30);
    }
  }

  public void setRot(int rot) {
    this.rot = 0;
    currentRot = pieces[this.rot];
  }
}

class S extends Tetromino {
  public S(int x, int y, int rot, int px) {
    super(x, y, rot, px);
    this.rot = rot % 2;
    piece = 4;
    pieces = new int[][][] {
      {{1, 0}, {0, 0}, {0, 1}, {-1, 1}},
      {{0, 0}, {0, 1}, {1, 1}, {1, 2}}
    };
    currentRot = pieces[this.rot];
  }

  public void draw(int drawX, int drawY) {
    for (int i = 0; i < 4; i++) {
      image[i] = new Image((drawX + x + currentRot[i][0]) * px + px/2, (drawY + y + currentRot[i][1]) * px + px/2, "S.png");
      image[i].setScale((double)px/30, (double)px/30);
    }
  }

  public void setRot(int rot) {
    this.rot = rot % 2;
    currentRot = pieces[this.rot];
  }
}

class I extends Tetromino {
  public I(int x, int y, int rot, int px) {
    super(x, y, rot, px);
    this.rot = rot % 2;
    piece = 5;
    pieces = new int[][][] {
      {{0, 0}, {1, 0}, {2, 0}, {3, 0}},
      {{0, 0}, {0, 1}, {0, 2}, {0, 3}}
    };
    currentRot = pieces[this.rot];
  }

  public void draw(int drawX, int drawY) {
    for (int i = 0; i < 4; i++) {
      image[i] = new Image((drawX + x + currentRot[i][0]) * px + px/2, (drawY + y + currentRot[i][1]) * px + px/2, "I.png");
      image[i].setScale((double)px/30, (double)px/30);
    }
  }

  public void setRot(int rot) {
    this.rot = rot % 2;
    currentRot = pieces[this.rot];
  }
}

class J extends Tetromino {
  public J(int x, int y, int rot, int px) {
    super(x, y, rot, px);
    piece = 6;
    pieces = new int[][][] {
      {{0, 0}, {0, 1}, {1, 1}, {2, 1}},
      {{1, 0}, {0, 0}, {0, 1}, {0, 2}},
      {{0, 0}, {1, 0}, {2, 0}, {2, 1}},
      {{0, 0}, {0, 1}, {0, 2}, {-1, 2}}
    };
    currentRot = pieces[this.rot];
  }

  public void draw(int drawX, int drawY) {
    for (int i = 0; i < 4; i++) {
      image[i] = new Image((drawX + x + currentRot[i][0]) * px + px/2, (drawY + y + currentRot[i][1]) * px + px/2, "J.png");
      image[i].setScale((double)px/30, (double)px/30);
    }
  }

  public void setRot(int rot) {
    this.rot = rot;
    currentRot = pieces[this.rot];
  }
}

class T extends Tetromino {
  public T(int x, int y, int rot, int px) {
    super(x, y, rot, px);
    piece = 7;
    pieces = new int[][][] {
      {{0, 0}, {-1, 1}, {0, 1}, {1, 1}},
      {{0, 0}, {0, 1}, {1, 1}, {0, 2}},
      {{0, 0}, {1, 0}, {2, 0}, {1, 1}},
      {{0, 0}, {0, 1}, {-1, 1}, {0, 2}}
    };
    currentRot = pieces[this.rot];
  }

  public void draw(int drawX, int drawY) {
    for (int i = 0; i < 4; i++) {
      image[i] = new Image((drawX + x + currentRot[i][0]) * px + px/2, (drawY + y + currentRot[i][1]) * px + px/2, "T.png");
      image[i].setScale((double)px/30, (double)px/30);
    }
  }

  public void setRot(int rot) {
    this.rot = rot;
    currentRot = pieces[this.rot];
  }
}
