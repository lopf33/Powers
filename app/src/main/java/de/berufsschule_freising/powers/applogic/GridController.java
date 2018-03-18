package de.berufsschule_freising.powers.applogic;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import de.berufsschule_freising.powers.interfaces.GridDataInteract;
import de.berufsschule_freising.powers.interfaces.GridDataSupplier;

public class GridController implements GridDataSupplier, GridDataInteract {

    private int[][] grid;
    private ArrayList<Point> emptyPoints;
    private int sizeX, sizeY;

    public GridController(int sizeX, int sizeY) {
        grid = new int[sizeX][sizeY];
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        for(int x = 0; x < grid.length; x++) {
            for(int y = 0; y < grid[x].length; y++) {
                grid[x][y] = 0;
            }
        }
        emptyPoints = new ArrayList<>();
    }

    @Override
    public void generateItem() {
        scanEmpty();
        Random rand = new Random();
        int pointId = rand.nextInt(emptyPoints.size());
        if(rand.nextInt(9) < 1)
        {
            grid[emptyPoints.get(pointId).x][emptyPoints.get(pointId).y] = 4;
        }
        else {
            grid[emptyPoints.get(pointId).x][emptyPoints.get(pointId).y] = 2;
        }
    }

    @Override
    public void action(Direction direction) {
        action(direction, grid);
    }

    private void action(Direction direction, int[][] grid)
    {
        move(direction, grid);
        add(direction, grid);
        move(direction, grid);
    }

    @Override
    public int itemAt(int x, int y) {
        return grid[x][y];
    }

    @Override
    public boolean isGameOver() {

        int[][] temp = new int[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            temp[i] = grid[i].clone();
        }
        action(Direction.up, temp);
        if(Arrays.deepEquals(temp, grid)) {
            action(Direction.down, temp);
            if(Arrays.deepEquals(temp, grid)) {
                action(Direction.left, temp);
                if(Arrays.deepEquals(temp, grid)) {
                    action(Direction.right, temp);
                    if(Arrays.deepEquals(temp, grid)) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    @Override
    public int[][] getGrid() {
        return this.grid;
    }

    @Override
    public int getSizeX() {
        return this.sizeX;
    }

   @Override
    public int getSizeY() {
        return this.sizeY;
    }

    private void scanEmpty() {

        emptyPoints.clear();
        for(int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                if (grid[x][y] == 0) {
                    emptyPoints.add(new Point(x, y));
                }
            }
        }

    }

    private void add(Direction dir, int[][] grid) {
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

    private void move(Direction dir, int[][] grid) {
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

