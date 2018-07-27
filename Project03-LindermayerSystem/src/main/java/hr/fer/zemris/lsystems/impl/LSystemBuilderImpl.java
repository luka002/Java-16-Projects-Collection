package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.beans.VetoableChangeSupport;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * Class that represents implementation of Lyndermayer system.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	/** Dictionary made of key: symbol and value: action to be made */
	private Dictionary registeredCommands;
	/** Dictionary made of key: symbol and value: production */
	private Dictionary registeredActions;
	/** Length of turtle movement */
	private double unitLength;
	/** Length of turtle movement scaled */ 
	private double unitLengthDegreeScaler;
	/** Initial point of movement */
	private Vector2D origin;
	/** Initial angle of direction */
	private double angle;
	/** Starting string */
	private String axiom;
	
	/**
	 * Default constructor setting default values.
	 */
	public LSystemBuilderImpl() {
		registeredCommands = new Dictionary();
		registeredActions = new Dictionary();
		unitLength = 0.1;
		unitLengthDegreeScaler = 1;
		origin = new Vector2D(0, 0);
		angle = 0;
		axiom = "";
	}

	/**
	 * Method that builds Lyndermayer system.
	 */
	@Override
	public LSystem build() {
		
		/**
		 * Class that represents implementation of Lyndermayer system.
		 * 
		 * @author Luka Grgić
		 * @version 1.0
		 */
		class Builder implements LSystem {

			/**
			 * Method that draws defined system.
			 */
			@Override
			public void draw(int level, Painter painter) {
				Context context = new Context();
				
				TurtleState state = new TurtleState(origin.copy(), new Vector2D(1, 0).rotated(angle),
						Color.BLACK, unitLength*(Math.pow(unitLengthDegreeScaler, level)));
				
				context.pushState(state);
				
				String generatedString = generate(level);
				char currentCommand;
				
				for (int i = 0; i < generatedString.length(); i++) {
					currentCommand = generatedString.charAt(i);
					String[] action;
					
					try {
						action = ((String) registeredCommands.get(currentCommand)).split("\\s+");
					} catch (Exception e) {
						continue;
					}
					
					switch (action[0]) {
						case "draw":
							double scale = Double.parseDouble(action[1]);
							
							DrawCommand draw = new DrawCommand(scale);
							draw.execute(context, painter);
							break;
	
						case "rotate":
							double angle = Double.parseDouble(action[1]);
							
							RotateCommand rotate = new RotateCommand(angle);
							rotate.execute(context, painter);
							break;
						
						case "push":
							PushCommand push = new PushCommand();
							push.execute(context, painter);
							break;
							
						case "pop":
							PopCommand pop = new PopCommand();
							pop.execute(context, painter);
							break;
							
						case "color":
							ColorCommand color = new ColorCommand(Color.decode("#" + action[1]));
							color.execute(context, painter);
							break;
							
						default:
							break;
					}
					
					
				}
				
			}

			/**
			 * Method that generates string after given number of productions.
			 */
			@Override
			public String generate(int level) {
				String currentString = axiom;
				StringBuilder generatedString = new StringBuilder();
				char currentCommand;
				
				for (int i = 0; i < level; i++) {
					
					for(int j = 0; j < currentString.length(); j++) {
						currentCommand = currentString.charAt(j);
						
						if (registeredActions.get(currentCommand) == null) {
							generatedString.append(currentCommand);
						} else {
							generatedString.append(registeredActions.get(currentCommand));
						}	
					}
					
					currentString = generatedString.toString();
					generatedString = new StringBuilder();
				}
				
				return currentString.toString();
			}
			
		}
		
		return new Builder();
	}

	/**
	 * Configures system from text.
	 */
	@Override
	public LSystemBuilder configureFromText(String[] data) {
		String[] line;
		
		for (int i = 0; i < data.length; i++) {
			line = data[i].split("\\s+");
			
			switch (line[0]) {
				case "origin":
					setOrigin(Double.parseDouble(line[1]), Double.parseDouble(line[2]));
					break;
					
				case "angle":
					setAngle(Double.parseDouble(line[1]));
					break;
					
				case "unitLength":
					setUnitLength(Double.parseDouble(line[1]));
					break;
					
				case "unitLengthDegreeScaler" :
					StringBuilder number = new StringBuilder();
					
					for (int j = 1; j < line.length; j++) {
						number.append(line[j]);
					}
					
					String[] finalNumber = number.toString().split("/");
					setUnitLengthDegreeScaler(Double.parseDouble(finalNumber[0])/Double.parseDouble(finalNumber[1]));
					break;
					
				case "axiom" :
					setAxiom(line[1]);
					break;
					
				case "command" :
					if (line[1].charAt(0) == '[' || line[1].charAt(0) == ']') {
						registerCommand(line[1].charAt(0), line[2]);
					} else {
						registerCommand(line[1].charAt(0), line[2] + " " + line[3]);	
					}
					
					break;
					
				case "production" :
					registerProduction(line[1].charAt(0), line[2]);
					break;
					
				default:
					break;
			}
			
		}
		
		return this;
	}

	/**
	 * registers given command.
	 */
	@Override
	public LSystemBuilder registerCommand(char command, String action) {
		registeredCommands.put(command, action);
		return this;
	}

	/**
	 * Registers given producion.
	 */
	@Override
	public LSystemBuilder registerProduction(char axiom, String replacement) {
		registeredActions.put(axiom, replacement);
		return this;
	}

	/**
	 * Sets given angle.
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}

	/**
	 * Sets given axiom.
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	/**
	 * Sets given origin.
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		origin = new Vector2D(x, y);
		return this;
	}

	/**
	 * Sets given length.
	 */
	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}

	/**
	 * Sets given scaled length.
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}

}
