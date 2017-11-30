package de.berufsschule_freising.powers.applogic;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.Random;

public class GridController {

    private int[][] grid;
    private ArrayList<Point> emptyPoints;
    private int sizeX, sizeY;

    public GridController(int sizeX, int sizeY) {
        grid = new int[sizeX][sizeY];
        emptyPoints = new ArrayList();
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        for(int x = 0; x < grid.length; x++) {
            for(int y = 0; x < grid[x].length; y++) {
                grid[x][y] = 0;
            }
        }
        scanEmpty();
    }

    public void generateItem() {
        Random rand = new Random();
        int pointId = rand.nextInt(emptyPoints.size());
        grid[emptyPoints.get(pointId).x][emptyPoints.get(pointId).y] = 2;
        scanEmpty();
    }

    public void action(Direction direction) {
        add(direction);
        move(direction);
        scanEmpty();
    }

    public int itemAt(int x, int y) {
        return grid[x][y];
    }

    public int[][] getGrid() {
        return this.grid;
    }

    public int getSizeX() {
        return this.sizeX;
    }

    public int getSizeY() {
        return this.sizeY;
    }

    private void scanEmpty() {
        for(int y = 0; y < grid.length; y++) {
            for(int x = 0; x < grid[y].length; x++) {
                emptyPoints.add(new Point(x,y));
            }
        }
    }

    private void add(Direction dir) {
        if (dir == Direction.up) {
            for (int x = 0; x < sizeX; x++) {
                for (int y = 0; y < sizeY; y++) {
                    if (y < sizeY - 1 && grid[x][y] == grid[x][y + 1]) {
                        grid[x][y] = grid[x][y] + grid[x][y + 1];
                        grid[x][y + 1] = 0;
                    }
                }
            }
        } else if (dir == Direction.down) {
            for (int x = 0; x < sizeX; x++) {
                for (int y = sizeY - 1; y >= 0; y--) {
                    if (y > 0 && grid[x][y] == grid[x][y - 1]) {
                        grid[x][y] = grid[x][y] + grid[x][y - 1];
                        grid[x][y - 1] = 0;
                    }
                }
            }
        } else if (dir == Direction.left) {
            for (int y = 0; y < sizeY; y++) {
                for (int x = 0; x < sizeX; x++) {
                    if (x < sizeX - 1 && grid[x][y] == grid[x + 1][y]) {
                        grid[x][y] = grid[x][y] + grid[x + 1][y];
                        grid[x + 1][y] = 0;
                    }
                }
            }
        } else if (dir == Direction.right) {
            for (int y = 0; y < sizeY; y++) {
                for (int x = sizeX - 1; x >= 0; x--) {
                    if (x > 0 && grid[x][y] == grid[x - 1][y]) {
                        grid[x][y] = grid[x][y] + grid[x - 1][y];
                        grid[x - 1][y] = 0;
                    }
                }
            }
        }
    }

    private void move(Direction dir) {
        if (dir == Direction.up) {
            for (int x = 0; x < sizeX; x++) {
                int space = 0;
                for (int y = 0; y < sizeY; y++) {
                    if (grid[x][y] != 0 && space > 0) {
                        grid[x][y - space] = grid[x][y];
                        grid[x][y] = 0;
                    } else if (grid[x][y] == 0) {
                        space++;
                    }
                }
            }
        } else if (dir == Direction.down) {
            for (int x = 0; x < sizeX; x++) {
                int space = 0;
                for (int y = sizeY - 1; y >= 0; y--) {
                    if (grid[x][y] != 0 && space > 0) {
                        grid[x][y + space] = grid[x][y];
                        grid[x][y] = 0;
                    } else if (grid[x][y] == 0) {
                        space++;
                    }
                }
            }
        } else if (dir == Direction.left) {
            for (int y = 0; y < sizeY; y++) {
                int space = 0;
                for (int x = 0; x < sizeX; x++) {
                    if (grid[x][y] != 0 && space > 0) {
                        grid[x - space][y] = grid[x][y];
                        grid[x][y] = 0;
                    } else if (grid[x][y] == 0) {
                        space++;
                    }
                }
            }
        } else if (dir == Direction.right) {
            for (int y = 0; y < sizeY; y++) {
                int space = 0;
                for (int x = sizeX - 1; x >= 0; x--) {
                    if (grid[x][y] != 0 && space > 0) {
                        grid[x + space][y] = grid[x][y];
                        grid[x][y] = 0;
                    } else if (grid[x][y] == 0) {
                        space++;
                    }
                }
            }
        }
    }

    public enum Direction {
        up,
        down,
        left,
        right
    }
}

