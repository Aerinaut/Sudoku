/**
 * @author Aerinaut
 * This project creates a sudoku board in 3 different difficulties; easy, medium, and hard. It also of course allows one to 
 * play a game of Sudoku.
 */

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Play extends Application {
	
	private Group root; // A Group object into which all the given nodes are accrued.
	private Scene scene; // A Scene object on which the nodes in root are set.
	private Board game; // A Board object that begins the game and in which most of the game transpires.
	private double xCoord, yCoord; // Coordinates for a button in the scene that has been clicked.
	private boolean cur; // A boolean value for keeping track of whether a button of a value Board.board[ y ][ x ] is selected.
	private int xNum, yNum; // Coordinates of a selected Board.board[ y ][ x ].
	private boolean startOfGame = true; // Boolean value for checking whether the game has just begun.
	
    // Function for starting the JavaFX start function.
	public static void main( String[] args ) {
		launch( args );
	}
	
    // A function for drawing and redrawing the scene with whatever nodes are in root in a given moment.
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
		
        stage.setResizable( false );
		stage.setScene( scene );
		stage.show();
	}
	
    // Function for updating which button in game.board[ x ][ y ] has been selected and whether one of these has been selected.
	public void updateVals( double xCoordinate, double yCoordinate, int rows, int cols ) {
		xCoord = xCoordinate;
		yCoord = yCoordinate;
		cur = true;
		xNum = cols;
		yNum = rows;
	}
	
    // A function for updating game.board[ x ][ y ] with a value num after this has been selected.
	public void updateBoard( int num ) {
		game.setNum( num, yNum, xNum );
		cur = false;
	}
	
    // A function that sets the nodes for the beginning landscape of the game.
	public void startGame( Stage stage, Group newRoot ) {
		if( startOfGame ) {

			Button easy = difficulty( 75, 425, "Easy", stage );	
			newRoot.getChildren().add( easy );
			
			Button medium = difficulty( 375, 425, "Medium", stage );
			newRoot.getChildren().add( medium );
			
			Button hard = difficulty( 672, 425, "Hard", stage );
			newRoot.getChildren().add( hard );
		}
	}
	
    // A function that updates the nodes in root then after a button of value game.board[ x ][ y ] has been selected.	
	public void redraw( Stage stage, Group newRoot ) {
		double x = 50;
		double y = 50;
		
		for( int rows = 0; rows < 9; rows++ ) {
			for( int cols = 0; cols < 9; cols++ ) {
                Button button = newButton( rows, cols, x, y, Integer.toString( game.getNum( rows, cols ) ), stage );
				root.getChildren().add( button );
				
				x += 90;
			}
			x = 50;
			y += 90;
		}
		
		Button solve = solveButton( stage );
        Button newGame = newGameButton( stage );
        Button reset = resetButton( stage );
		
        root.getChildren().add( reset );
		root.getChildren().add( solve );
        root.getChildren().add( newGame );
	}
	
    // A function that updates game.board as well as calls the start function to show what changes were made to game.board.	
	public void onClickRedraw( Stage stage, Group newRoot ) {
		double x = 50;
		double y = 50;
		
		for( int rows = 0; rows < 9; rows++ ) {
			for( int cols = 0; cols < 9; cols++ ) {
				if( x == xCoord && yCoord == y ) {
					x += 90;
					continue;
				}
				
				Button button = newButton( rows, cols, x, y, Integer.toString( game.getNum( rows, cols ) ), stage );	
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
				Button button = smallButton( rows, cols, x, y, Integer.toString( count ), stage );
				root.getChildren().add( button );

				x += 30;
				count++;
			}
			
			x = xCoord;
			y += 30;
		}
		
		Button solve = solveButton( stage );
        Button newGame = newGameButton( stage );
        Button reset = resetButton( stage );
		
		root.getChildren().add( solve );
        root.getChildren().add( newGame );
        root.getChildren().add( reset );
	}

    public void newGame(){
        startOfGame = true;
    }

    public void resetGame(){
        for( int i = 0; i < 9; i++ ){
            for( int j = 0; j < 9; j++ ){
                game.setNum( game.getStarters( i, j ), i, j );
            }
        }
    }

    public Button newButton( int rows, int cols, double x, double y, String num, Stage stage ){
        Button button = new Button( num );
        button.setMaxSize( 90, 90 );
        button.setMinSize( 90, 90 );
        button.relocate( x, y );
        button.setFont( new Font( 30 ) );

        final int ROWS = rows;
        final int COLS = cols;

        button.setOnMouseClicked( ( e ) -> {
            if( game.getStarters( ROWS, COLS ) != 0 ){
            }
            else{
                updateVals( button.getLayoutX(), button.getLayoutY(), ROWS, COLS );
                try{
                    start( stage );
                } catch ( Exception exception ){
                    System.out.println( exception.toString() );
                }
            }
        });

        return button;
    }

    public Button solveButton( Stage stage ){
        Button button = new Button( "Solve" );
        button.setMaxSize( 200, 50 );
        button.setMinSize( 200, 50 );
        button.relocate( 100, 880 );
        button.setFont( new Font( 30 ) );
        button.setOnMouseClicked( ( e ) -> {
            game.solveBoard();
            game.solve();
            try{
                start( stage );
            } catch( Exception exception ){
                System.out.println( exception.toString() );
            }
        });

        return button;
    }

    public Button newGameButton( Stage stage ){
        Button button = new Button( "New Game" );
        button.setMaxSize( 300, 50 );
        button.setMinSize( 300, 50 );
        button.relocate( 305, 880 );
        button.setFont( new Font( 30 ) );
        button.setOnMouseClicked( ( e ) -> {
            newGame();
            try{
                start( stage );
            } catch( Exception exception ){
                System.out.println( exception.toString() );
            }
        });

        return button;
    } 

    public Button resetButton( Stage stage ){
        Button button = new Button( "Reset" );
        button.setMaxSize( 200, 50 );
        button.setMinSize( 200, 50 );
        button.relocate( 610, 880 );
        button.setFont( new Font( 30 ) );
        button.setOnMouseClicked( ( e ) -> {
            resetGame();
            try{
                start( stage );
            } catch( Exception exception ){
                System.out.println( exception.toString() );
            }
        });

        return button;
    }

    public Button smallButton( int rows, int cols, double x, double y, String num, Stage stage ){
        Button button = new Button( num );
        button.setMinSize( 30, 30 );
        button.setMaxSize( 30, 30 );
        button.relocate( x, y );
        button.setFont( new Font( 10 ) );
        button.setOnMouseClicked( ( e ) -> {
            updateBoard( Integer.parseInt( button.getText() ) );
            try{
                start( stage );
            } catch( Exception exception ){
                System.out.println( exception.toString() );
            }
        });

        return button;
    }

    public Button difficulty( double x, double y, String difficulty, Stage stage ){
        int num;
        if( difficulty == "Easy" ){
            num = 30;
        } else if( difficulty == "Medium" ){
            num = 25;
        } else {
            num = 20;
        }

        Button button = new Button( difficulty );
        button.setMaxSize( 200, 75 );
        button.setMinSize( 200, 75 );
        button.relocate( x, y );
        button.setFont( new Font( 30 ) );
        button.setOnMouseClicked( ( e ) -> {
            final int RAND = ( int )( Math.random() * 6 ) + num;
            game = new Board( RAND );
            startOfGame = false;
            try{
                start( stage );
            } catch( Exception exception ) {
                System.out.println( exception.toString() );
            }
        });

        return button;
    }
}

