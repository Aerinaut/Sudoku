import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

public class Board{
	
	Board( int num ){
		int[][] tempBoard;
		int[][] secondTempBoard;
		
		do {
			int[] nums = createVals( num );
			tempBoard = new int[ 9 ][ 9 ];
			generateIndex( tempBoard, nums, 0 );
			secondTempBoard = newBoard( tempBoard );
		}while( !solve( secondTempBoard ) );
		
		for( int i = 0; i < 9; i++ ) {
			for( int j = 0; j < 9; j++ ) {
				if( tempBoard[ i ][ j ] != 0 )
					starters[ i ][ j ] = true;
			}
		}
		
		board = newBoard( tempBoard );
		original = newBoard( tempBoard );
	}
	
	public int[][] board;
	public boolean[][] starters = new boolean[ 9 ][ 9 ];
	public int[][] original;
	
	public void setNum( int num, int y, int x ) {
		board[ y ][ x ] = num;
	}
	
	public int getNum( int y, int x ) {
		return board[ y ][ x ];
	}
	
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
	
	private boolean isUsable( int row, int col, int val, int[][] gameBoard ) {
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
	
	private int[][] newBoard( int[][] gameBoard ) {
		int[][] temp = new int[ 9 ][ 9 ];
		
		for ( int i = 0; i < 9; i++ ) {
			temp[ i ] = Arrays.copyOf( gameBoard[ i ], 9 );
		}
		
		return temp;
	}
	
	private int[] createVals( int num ) {
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
	
	public void solveBoard() {
		board = newBoard( original );
	}
	
}