import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Play extends Application {
	
	private Group root;
	private Scene scene;
	private Board game;
	private double xCoord, yCoord;
	private boolean cur;
	private int xNum, yNum;
	private boolean startOfGame = true;
	
	public static void main( String[] args ) {
		launch( args );
	}
	
	@Override
	public void start( Stage stage ) throws Exception {
		root = new Group();
		scene = new Scene( root, 910, 950, Color.BLACK );
		if( startOfGame ) {
			startGame( stage, root );
		}
		if( !cur && !startOfGame ) {
			redraw( stage, root );
		}
		else if ( cur && !startOfGame ){
			onClickRedraw( stage, root );
		}
		
		stage.setScene( scene );
		stage.show();
	}
	
	
	private void updateVals( double xCoordinate, double yCoordinate, int rows, int cols ) {
		xCoord = xCoordinate;
		yCoord = yCoordinate;
		cur = true;
		xNum = cols;
		yNum = rows;
	}
	
	
	public void updateBoard( int num ) {
		game.setNum( num, yNum, xNum );
		cur = false;
	}
	
	
	private void startGame( Stage stage, Group newRoot ) {
		if( startOfGame ) {
			Button easy = new Button( "Easy" );
			easy.setMaxSize( 200, 50 );
			easy.setMinSize( 200, 50 );
			easy.relocate( 75, 425 );
			easy.setFont( new Font( 30 ) );
			easy.setOnMouseClicked( ( e ) -> {
				final int rand = ( int )( Math.random() * 6 ) + 30;
				game = new Board( rand );
				startOfGame = false;
				try {
					start( stage );
				} catch ( Exception exception ) {
					System.out.println( exception.toString() );
				}
			});
			
			newRoot.getChildren().add( easy );
			
			Button medium = new Button( "Medium" );
			medium.setMaxSize( 200, 50 );
			medium.setMinSize( 200, 50 );
			medium.relocate( 375, 425 );
			medium.setFont( new Font( 30 ) );
			medium.setOnMouseClicked( ( e ) -> {
				final int rand = ( int )( Math.random() * 5 ) + 25;
				game = new Board( rand );
				startOfGame = false;
				try {
					start( stage );
				} catch ( Exception exception ) {
					System.out.println( exception.toString() );
				}
			});
			
			newRoot.getChildren().add( medium );
			
			Button hard = new Button( "Hard" );
			hard.setMaxSize( 200, 50 );
			hard.setMinSize( 200, 50 );
			hard.relocate( 675, 425 );
			hard.setFont( new Font( 30 ) );
			hard.setOnMouseClicked( ( e ) -> {
				final int rand = ( int )( Math.random() * 5 ) + 20;
				game = new Board( rand );
				startOfGame = false;
				try {
					start( stage );
				} catch( Exception exception ) {
					System.out.println( exception.toString() );
				}
			});
			
			newRoot.getChildren().add( hard );
		}
	}
	
	
	private void redraw( Stage stage, Group newRoot ) {
		double x = 50;
		double y = 50;
		
		for( int rows = 0; rows < 9; rows++ ) {
			for( int cols = 0; cols < 9; cols++ ) {
				Button button = new Button( Integer.toString( game.getNum( rows, cols ) ) );
				button.setMaxSize( 90, 90 );
				button.setMinSize( 90, 90 );
				button.relocate( x, y );
				button.setFont( new Font( 30 ) );
				
				final int ROWS = rows;
				final int COLS = cols;
				
				button.setOnMouseClicked( ( e ) -> {
					if( game.starters[ ROWS ][ COLS ] ) {
					}
					else {
						updateVals( button.getLayoutX(), button.getLayoutY(), ROWS, COLS );
						try {
							start( stage );
						} catch ( Exception exception ) {
							System.out.println( exception.toString() );
						}
					}
			
				});
				root.getChildren().add( button );
				
				x += 90;
			}
			x = 50;
			y += 90;
		}
		
		Button solve = new Button( "Solve" );
		solve.setMaxSize( 200, 50 );
		solve.setMinSize( 200, 50 );
		solve.relocate( 355, 880 );
		solve.setFont( new Font( 30 ) );
		solve.setOnMouseClicked( ( e ) -> {
			game.solveBoard();
			game.solve( game.board );
			try {
				start( stage );
			} catch( Exception exception ) {
				System.out.println( exception.toString() );
			}
		}); 
		
		root.getChildren().add( solve );
	}
	
	
	private void onClickRedraw( Stage stage, Group newRoot ) {
		double x = 50;
		double y = 50;
		
		for( int rows = 0; rows < 9; rows++ ) {
			for( int cols = 0; cols < 9; cols++ ) {
				if( x == xCoord && yCoord == y ) {
					x += 90;
					continue;
				}
				
				Button button = new Button( Integer.toString( game.getNum( rows, cols ) ) );
				button.setMaxSize( 90, 90 );
				button.setMinSize( 90, 90 );
				button.relocate( x, y );
				button.setFont( new Font( 30 ) );
				
				final int ROWS = rows;
				final int COLS = cols;
				
				button.setOnMouseClicked( ( e ) -> {
					if( game.starters[ ROWS ][ COLS ] ) {
					}
					else {
						updateVals( button.getLayoutX(), button.getLayoutY(), ROWS, COLS );
						try {
							start( stage );
						} catch ( Exception exception ) {
							System.out.println( exception.toString() );
						}
					}
				});
				
				root.getChildren().add( button );
				x += 90;
			}
			
			x = 50;
			y += 90;
		}
		
		x = xCoord;
		y = yCoord;
		int count = 1;
		
		for( int rows = 0; rows < 3; rows++ ) {
			for( int cols = 0; cols < 3; cols++ ) {
				Button button = new Button( Integer.toString( count ) );
				button.setMinSize( 30, 30 );
				button.setMaxSize( 30, 30 );
				button.relocate( x, y );
				button.setFont( new Font( 10 ) );
				button.setOnMouseClicked( ( e ) -> {
					updateBoard( Integer.parseInt( button.getText() ) );
					try {
						start( stage );
					} catch( Exception exception ) {
						System.out.println( exception.toString() );
					}
				});
				root.getChildren().add( button );
				
				x += 30;
				count++;
			}
			
			x = xCoord;
			y += 30;
		}
		
		Button solve = new Button( "Solve" );
		solve.setMaxSize( 200, 50 );
		solve.setMinSize( 200, 50 );
		solve.relocate( 355, 880 );
		solve.setFont( new Font( 30 ) );
		solve.setOnMouseClicked( ( e ) -> {
			game.solveBoard();
			game.solve( game.board );
			try {
				start( stage );
			} catch( Exception exception ) {
				System.out.println( exception.toString() );
			}
		}); 
		
		root.getChildren().add( solve );
	}
	
}
