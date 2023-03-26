import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

// A class that instatiates a Board object that has starting values leading to a solvable Sudoku board.

public class Board{

	private int[][] board; // The board on which the game transpires, here is where the changes players make are held.
	private int[][] original; // A board that keeps track of the beginning board.
	
    // A constructor that creates a new Board object with values up to the argument num being provided in the starting game.
    // The constructor also checks to make that the Board object create has a board with values that lead to a solution.
	public Board( int num ){
		int[][] tempBoard;
		int[][] secondTempBoard;
		
		do {
			int[] nums = createVals( num );
			tempBoard = new int[ 9 ][ 9 ];
			generateIndex( tempBoard, nums, 0 );
			secondTempBoard = newBoard( tempBoard );
		}while( !solve( secondTempBoard ) );
		
        board = newBoard( tempBoard );
        original = newBoard( tempBoard );
	}	
	
    // Function for setting a specified row y and column x to an int x.
	public void setNum( int num, int y, int x ) {
		board[ y ][ x ] = num;
	}
	
    // Function for getting a specified row y and column x. Returns an int value.
	public int getNum( int y, int x ) {
		return board[ y ][ x ];
	}

    // Function for getting a specified row y and column x from the original starting board. Returns an int value.
    public int getStarters( int row, int col ){
        return original[ row ][ col ];
    }
	
    // Method for checking whether there are other values of the same value in a given row y, column x, and a square calculated
    // by the values provided in y and x.
	public boolean isValid() {
		for ( int i = 0; i < 9; i++ ) {
			Set< Integer > set = new HashSet<>();
			for ( int j = 0; j < 9; j++ ) {
				if ( !set.add( board[ i ][ j ] ) )
					return false;
			}
		}
		
		for ( int i = 0; i < 9; i++ ) {
			Set < Integer > set = new HashSet<>();
			for ( int j = 0; j < 9; j++ ) {
				if( !set.add( board[ j ][ i ] ) )
					return false;
			}
		}
		
		for ( int row = 0; row < 9; row += 3 ) {
			for ( int col = 0; col < 9; col += 3 ) {
				for ( int i = row; i < row + 3; i++ ) {
					Set< Integer > set = new HashSet<>();
					for ( int j = col; j < col + 3; j++ ) {
						if( !set.add( board[ i ][ j ] ) )
							return false;
					}
				}
			}
		}
		
		return true;
	}

    // Function for checking whether a given gameBoard is solvable.
	public boolean solve( int[][] gameBoard ) {
		for( int i = 0; i < 9; i++ ) {
			for ( int j = 0; j < 9; j++ ) {
				if ( gameBoard[ i ][ j ] == 0 ) {
					for ( int num = 1; num <= 9; num++ ) {
						if ( isUsable( i, j, num, gameBoard ) ) {
							gameBoard[ i ][ j ] = num;
							
							if( solve( gameBoard ) )
								return true;
							else
								gameBoard[ i ][ j ] = 0;
						}
					}
					
					return false;
				}
			}
		}
		
		return true;
	}

    // Function that solves the board that's instantiated as the matrix original.
    public boolean solve(){
		for( int i = 0; i < 9; i++ ) {
			for ( int j = 0; j < 9; j++ ) {
				if ( board[ i ][ j ] == 0 ) {
					for ( int num = 1; num <= 9; num++ ) {
						if ( isUsable( i, j, num, board ) ) {
							board[ i ][ j ] = num;
							
							if( solve( board ) )
								return true;
							else
								board[ i ][ j ] = 0;
						}
					}
					
					return false;
				}
			}
		}
		
		return true;
    }
	
    // Function that checks whether a given value is unique in a row y, column x, and square( x, y ) in a gameBoard.
	public boolean isUsable( int row, int col, int val, int[][] gameBoard ) {
		for ( int i = 0; i < 9; i++ ) {
			if ( gameBoard[ row ][ i ] == val )
				return false;
		}
		
		for ( int i = 0; i < 9; i++ ) {
			if ( gameBoard[ i ][ col ] == val )
				return false;
		}
		
		int squareY, squareX;
		if ( row < 3 )
			squareY = 0;
		else if ( row < 6 )
			squareY = 3;
		else
			squareY = 6;
		
		if ( col < 3 )
			squareX = 0;
		else if ( col < 6 )
			squareX = 3;
		else
			squareX = 6;
		
		for ( int i = squareY; i < squareY + 3; i++ ) {
			for ( int j = squareX; j < squareX + 3; j++ ) {
				if ( gameBoard[ i ][ j ] == val )
					return false;
			}
		}
		
		return true;
	}
	
    // Function that generates random indexes into which an attempt is made to enter the value at nums[ currNum ].
	public void generateIndex( int[][] gameBoard, int[] nums, int currNum ) {
		if( currNum == nums.length)
			return;
		
		for( int i = 0; i < 9; i++ ) {
			for( int j = 0; j < 9; j++ ) {
				if( gameBoard[ i ][ j ] == 0 ) {
					if( isUsable( i, j, nums[ currNum ], gameBoard ) ) {
						gameBoard[ i ][ j ] = nums[ currNum ];
						currNum++;
						generateIndex( gameBoard, nums, currNum );
						return;
					}
				}
			}
		}
		
		return;
	}
	
    // Function that creates a deepcopy of a given matrix.
	public int[][] newBoard( int[][] gameBoard ) {
		int[][] temp = new int[ 9 ][ 9 ];
		
		for ( int i = 0; i < 9; i++ ) {
			temp[ i ] = Arrays.copyOf( gameBoard[ i ], 9 );
		}
		
		return temp;
	}

    // Function that generates random values of an amount on num.
	public int[] createVals( int num ) {
		int[] temp = new int[ num ];
		int[] count = new int[ 9 ];
		int rand;
		
		for( int i = 0; i < num; i++ ) {
			rand = ( int )( Math.random() * 9 ) + 1;
			count[ rand - 1 ] += 1;
			if( count[ rand - 1 ] == 9 )
				continue;
			
			temp[ i ] = rand;
		}
		
		return temp;
	}
	
    // Method that solves the original board that was set when the game began.
	public void solveBoard() {
		board = newBoard( original );
	}
	
}
