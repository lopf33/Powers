public class GridController {
	
	private int[][] grid;
	private int sizeX, sizeY;

	public static void main(String[] args) {
		GridController gridC = new GridController(4,4);
		gridC.grid[1][0] = 2;
		gridC.grid[1][1] = 2;
		gridC.grid[1][2] = 2;

		gridC.display();
		gridC.move(Direction.right);
		gridC.add(Direction.right);
		gridC.move(Direction.right);
		gridC.display();
	}

	public GridController(int sizeX, int sizeY) {
		grid = new int[sizeY][sizeX];
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}

	public void display(){
		for(int[] row : grid) {
			for(int num : row) {
				System.out.print(num + " ");
			}
			System.out.println();
 		}
 		System.out.println("-------------");
	}

	public void add(Direction dir) {
		if(dir == Direction.up) {
			for(int x = 0; x < sizeX; x++) {
				for(int y = 0; y < sizeY; y++) {
					if(y < sizeY-1 && grid[y][x] == grid[y+1][x]) {
						grid[y][x] = grid[y][x] + grid[y+1][x];
						grid[y+1][x] = 0;
					}
				}
			}
		}
		else if(dir == Direction.down) {
			for(int x = 0; x < sizeX; x++) {
				for(int y = sizeY-1; y >= 0; y--) {
					if(y > 0 && grid[y][x] == grid[y-1][x]) {
						grid[y][x] = grid[y][x] + grid[y-1][x];
						grid[y-1][x] = 0;
					}
				}
			}
		}
		else if(dir == Direction.left) {
			for(int y = 0; y < sizeY; y++) {
				for(int x = 0; x < sizeX; x++) {
					if(x < sizeX-1 && grid[y][x] == grid[y][x+1]) {
						grid[y][x] = grid[y][x] + grid[y][x+1];
						grid[y][x+1] = 0;
					}
				}
			}
		}
		else if(dir == Direction.right) {
			for(int y = 0; y < sizeY; y++) {
				for(int x = sizeX-1; x >= 0; x--) {
					if(x > 0 && grid[y][x] == grid[y][x-1]) {
						grid[y][x] = grid[y][x] + grid[y][x-1];
						grid[y][x-1] = 0;
					}
				}
			}
		}
	}

	public void move(Direction dir) {
		if(dir == Direction.up) {
			for(int x = 0; x < sizeX; x++) {
				int space = 0;
				for(int y = 0; y < sizeY; y++) {
					if(grid[y][x] != 0 && space > 0) {
						grid[y-space][x] = grid[y][x];
						grid[y][x] = 0;
					}
					else if(grid[y][x] == 0) {
						space++;
					}
				}
			}
		}
		else if(dir == Direction.down) {
			for(int x = 0; x < sizeX; x++) {
				int space = 0;
				for(int y = sizeY-1; y >= 0; y--) {
					if(grid[y][x] != 0 && space > 0) {
						grid[y+space][x] = grid[y][x];
						grid[y][x] = 0;
					}
					else if(grid[y][x] == 0) {
						space++;
					}
				}
			}
		}
		else if(dir == Direction.left) {
			for(int y = 0; y < sizeY; y++) {
				int space = 0;
				for(int x = 0; x < sizeX; x++) {
					if(grid[y][x] != 0 && space > 0) {
						grid[y][x-space] = grid[y][x];
						grid[y][x] = 0;
					}
					else if(grid[y][x] == 0) {
						space++;
					}
				}
			}
		}
		else if(dir == Direction.right) {
			for(int y = 0; y < sizeY; y++) {
				int space = 0;
				for(int x = sizeX-1; x >= 0; x--) {
					if(grid[y][x] != 0 && space > 0) {
						grid[y][x+space] = grid[y][x];
						grid[y][x] = 0;
					}
					else if(grid[y][x] == 0) {
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

