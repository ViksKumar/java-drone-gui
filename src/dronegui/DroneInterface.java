package dronegui;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.*;
import javafx.scene.input.MouseEvent;
import java.io.File;
import javax.swing.JFileChooser;

public class DroneInterface extends Application {
	private MyCanvas mc;
	private AnimationTimer timer;								// timer used for animation
	private VBox rtPane;										// vertical box for putting info
	private DroneArena arena;

	/**
	 * function to show in a box ABout the programme
	 */
	private void showAbout() {
	    Alert alert = new Alert(AlertType.INFORMATION);				// define box type
	    alert.setTitle("About");									// set title
	    alert.setHeaderText(null);
	    alert.setContentText("This Application contains VK's Drone GUI made 12/2020 with JavaFX ");			// give text
	    alert.showAndWait();										// show box and wait for user to close
	}
	
	
	 //function to show the drone colours in a window
	 private void showReference() {
	    Alert alert = new Alert(AlertType.INFORMATION);				
	    alert.setTitle("Drone References");									
	    alert.setHeaderText("Drone Colour Key for Reference: ");
	    alert.setContentText("FriendlyDrone: Green\nPredatorDrone: Red\nHuggerDrone: Blue\nHazardDrone: Yellow");
	    alert.showAndWait();										
	}
	
	//function to warn user about load issue
	 private void showLoadError() {
	    Alert alert = new Alert(AlertType.INFORMATION);				
	    alert.setTitle("Error");									
	    alert.setHeaderText("Loading Error");
	    alert.setContentText("The file you selected does not exist, please try again");
	    alert.showAndWait();										
	}
	
	//function to warn user about save issue
	 private void showSaveError() {
	    Alert alert = new Alert(AlertType.INFORMATION);				
	    alert.setTitle("Error");								
	    alert.setHeaderText("Saving Error");
	    alert.setContentText("There was an error saving your file, please try again");
	    alert.showAndWait();										
	}
	
	 //function to inform user save was a success. 
	private void showSaveSuccess() {
	    Alert alert = new Alert(AlertType.INFORMATION);				// define what box is
	    alert.setTitle("File Saved");									// say is About
	    alert.setHeaderText(null);
	    alert.setContentText("Your arena was saved sucessfully!");
	    alert.showAndWait();										// show box and wait for user to close
	}
	
	//function to inform user save was a success. 
		private void showLoadSuccess() {
		    Alert alert = new Alert(AlertType.INFORMATION);				// define what box is
		    alert.setTitle("File loaded");									// say is About
		    alert.setHeaderText(null);
		    alert.setContentText("Your arena was loaded sucessfully!");
		    alert.showAndWait();										// show box and wait for user to close
		}
	
	void setMouseEvents (Canvas canvas) {
	       canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, 		// for MOUSE PRESSED event
	    	       new EventHandler<MouseEvent>() {
	    	           @Override
	    	           public void handle(MouseEvent e) {
	    	        	   	arena.addHazard(e.getX(), e.getY());	// place hazard
	  		            	drawWorld();							// redraw world
	  		            	drawStatus();
	    	           }
	    	       });
	}
	/**
	 * set up the menu of commands for the GUI
	 * @return the menu bar
	 */
	MenuBar setMenu() {
		MenuBar menuBar = new MenuBar();						// create main menu
	
		Menu mFile = new Menu("File");							// add File main menu
		MenuItem mExit = new MenuItem("Exit");					// sub menu item Exit
		mExit.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {					// action on exit is
	        	timer.stop();									// stop timer
		        System.exit(0);									// exit program
		    }
		});
		MenuItem mSave = new MenuItem("Save");					// sub menu item Save
		mSave.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {					
	        	saveFile();								  		// save file							
		    }
		});
		MenuItem mLoad = new MenuItem("Load");					// sub menu item Load
		mLoad.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {					
	        	loadFile();								  		// load file 							
		    }
		});
		mFile.getItems().addAll(mExit, mSave, mLoad);			// add exit save and load to submenu 
											
		Menu mHelp = new Menu("Help");							// create Help menu
		MenuItem mAbout = new MenuItem("About");				// add About sub menu item
		mAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	showAbout();									// show about
            }	
		});
		MenuItem mReference = new MenuItem("Drone Reference");				// create reference menu
		mReference.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	showReference();									// show drone reference key
            }	
		});
		mHelp.getItems().addAll(mAbout, mReference);			// add About and reference to Help main item
		menuBar.getMenus().addAll(mFile, mHelp);				// set main menu with File, Help, Reference
		return menuBar;											// return the menu
	}

	/**
	 * set up the horizontal box for the bottom with relevant buttons
	 * @return
	 */
	private HBox setButtons() {
	    Button btnStart = new Button("Start");					// button for starting
	    btnStart.setOnAction(new EventHandler<ActionEvent>() {	
	        @Override
	        public void handle(ActionEvent event) {
	        	timer.start();									// start the timer
	       }
	    });

	    Button btnStop = new Button("Pause");					// button for stop
	    btnStop.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	timer.stop();									// action to stop the timer
	       }
	    });

	    Button fAdd = new Button("Friendly Drone");				// button for adding Friendly
	    fAdd.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	arena.addFriendlyDrone();						// action to add friendly
	           	drawWorld();
	       }
	    });
	    
	    Button hAdd = new Button("Hugger Drone");				// button for adding Hugger
	    hAdd.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	arena.addHuggerDrone();							// action to add Hugger
	           	drawWorld();
	       }
	    });
	    
	    Button pAdd = new Button("Predator Drone");				// button for adding predator
	    pAdd.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	arena.addPredator();							// action to add Predator
	           	drawWorld();
	       }
	    });
	    														// add these buttons + labels to a HBox
	    return new HBox(new Label("Run: "), btnStart, btnStop, new Label("Add: "), fAdd, hAdd, pAdd);
	}

	
	private void saveFile() {
		  
		JFileChooser chooser = new JFileChooser();						//Declaring File chooser
	   											
	   int returnValue = chooser.showSaveDialog(null);					//If saving option is true
	   	if (returnValue == JFileChooser.APPROVE_OPTION) {
	   		File selectedFile = chooser.getSelectedFile();					//pass selected file to variable
	   			if(selectedFile.canWrite()) {									//Checking if writing permission is true
	   				try {														//Try func to catch exceptions
	   					ObjectOutputStream saver = new ObjectOutputStream(new FileOutputStream(chooser.getSelectedFile()));
	   					saver.writeObject(arena);
	   					saver.close();
	   					showSaveSuccess();			//Output display to user if successful
	   				}
	   				catch(Exception ex){											//Exception catch
	   					showSaveError();											//Show error message
	   					ex.printStackTrace();												
	   				}
	   			} else {
	   					try {	// method for creating new file if it didn't exist before						
	   						ObjectOutputStream saver = new ObjectOutputStream(new FileOutputStream(selectedFile)); 
	   						saver.writeObject(arena);
	   						saver.close();
	   						showSaveSuccess();
	   						} catch(Exception ex) {
	   							showSaveError();
	   							ex.printStackTrace();
	   						}				
	   					}
	   	} 
	}

	
	private void loadFile() {
		  JFileChooser chooser = new JFileChooser();						//Declaring File chooser
		  																
		  System.out.println("Select file to load");
	   int returnValue = chooser.showOpenDialog(null);				//show open dialog
	   if (returnValue == JFileChooser.APPROVE_OPTION) {
		   File selectedFile = chooser.getSelectedFile();					//select file to load
		   if(selectedFile.canRead()) {									// check if file can be read
			   try {														
				   ObjectInputStream loader = new ObjectInputStream(new FileInputStream(chooser.getSelectedFile()));
				   arena = (DroneArena) loader.readObject();				//import file into arena
				   loader.close();
				   drawWorld();
				   showLoadSuccess();			//Confirm load
			   	}
			   	catch(Exception ex){														
			   	 showLoadError();			//Show error
			   		ex.printStackTrace();												
			   	}
		   } 
	   } else {
	      	 showLoadError();
	   }
	}
	
	/** 
	 * draw the world with drone in it
	 */
	public void drawWorld () {
	 	mc.clearCanvas();				
	 	arena.drawArena(mc);
	}
	
	/**
	 * show where drone is, in pane on right
	 */
	public void drawStatus() {
		rtPane.getChildren().clear();					// clear rtpane
		ArrayList<String> allDs = arena.describeAll();
		for (String s : allDs) {
			Label l = new Label(s); 		// turn string with description into a label
			rtPane.getChildren().add(l);	// add label	
		}	
	}


	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("VK's Drone Application");
	    BorderPane bp = new BorderPane();
	    bp.setPadding(new Insets(10, 20, 10, 20));

	    bp.setTop(setMenu());											// put menu at the top

	    Group root = new Group();										// create group with canvas
	    Canvas canvas = new Canvas( 400, 500 );
	    root.getChildren().add( canvas );
	    bp.setLeft(root);												// load canvas to left area
	
	    mc = new MyCanvas(canvas.getGraphicsContext2D(), 400, 500);

	    setMouseEvents(canvas);											// set up mouse events

	    arena = new DroneArena(400, 500);								// set up arena
	    drawWorld();
	    
	    timer = new AnimationTimer() {									// set up timer
	        public void handle(long currentNanoTime) {					// and its action when on
	        		arena.checkDrones();								// check the angle of all drones
		            arena.adjustDrones();								// move all drones
		            drawWorld();										// redraw the world
		            drawStatus();										// indicate where drones are
	        }
	    };

	    rtPane = new VBox();											// set vBox on right to list items
		rtPane.setAlignment(Pos.TOP_LEFT);								// set alignment
		rtPane.setPadding(new Insets(5, 75, 75, 5));					// padding
 		bp.setRight(rtPane);											// add rtPane to borderpane right
		  
	    bp.setBottom(setButtons());										// set bottom pane with buttons

	    Scene scene = new Scene(bp, 700, 600);							// set overall scene
        bp.prefHeightProperty().bind(scene.heightProperty());
        bp.prefWidthProperty().bind(scene.widthProperty());

        primaryStage.setScene(scene);
        primaryStage.show();
	  

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    Application.launch(args);			// launch the GUI

	}

}