package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Custom layout manager that represents 5x7 grid that 
 * is filled with components. Every cell holds one component
 * except for cell 1,1 which spans from 1,1 to 1,5.
 * Gap between cells can be defined.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class CalcLayout implements LayoutManager2 {
	
	/** Gap between cells. */
	private int gap;
	/** Map containing component and its position. */
	private Map<Component, RCPosition> components;

	/** Gap is zero. */
	private final static int NO_GAP = 0;
	
	/**
	 * Constructor initializes object.
	 * 
	 * @param gap Gap between cells.
	 */
	public CalcLayout(int gap) {
		this.gap = gap;
		this.components = new HashMap<>();
	}
	
	/**
	 * Default constructor defines no gap.
	 */
	public CalcLayout() {
		this(NO_GAP);
	}
	
	/**
	 * @return map with components and its positions.
	 */
	public Map<Component, RCPosition> getComponents() {
		return components;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addLayoutComponent(String string, Component component) {
	}

	/**
	 * Based on parent size calculates size of
	 * each component so it fits in parent like 5x7 grid.
	 * 
	 * @param parent parent
	 */
	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		int componentNumber = parent.getComponentCount();
		
		if (componentNumber == 0) {
			return;
		}
		
		Dimension size = parent.getSize();
		int totalWidth = size.width - (insets.left + insets.right);
		int totalHeight = size.height - (insets.top + insets.bottom);
		
		int cellWidth = (totalWidth - gap*6)/7;
		int cellHeight = (totalHeight - gap*4)/5;
		
		for (Component component : components.keySet()) {
			RCPosition position = components.get(component);
			
			if (position.getRow() == 1 && position.getColumn() == 1) {
				int x = insets.left;
				int y = insets.top;
				int w = cellWidth*5 + 4*gap;
				int h = cellHeight;
				component.setBounds(x, y, w, h);
				
			} else if (position.getRow() == 1) {
				int x = insets.left + (position.getColumn()-1)*(cellWidth + gap);
				int y = insets.top;
				component.setBounds(x, y, cellWidth, cellHeight);
				
			} else if (position.getColumn() == 1) {
				int x = insets.left;
				int y = insets.top + (position.getRow()-1)*(cellHeight + gap);
				component.setBounds(x, y, cellWidth, cellHeight);
				
			} else {
				int x = insets.left + (position.getColumn()-1)*(cellWidth + gap);
				int y = insets.top + (position.getRow()-1)*(cellHeight + gap);
				component.setBounds(x, y, cellWidth, cellHeight);
			}
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension minimumLayoutSize(Container container) {
		return getLayoutSize(container, ComponentSize.MINIMUM);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension preferredLayoutSize(Container container) {
		return getLayoutSize(container, ComponentSize.PREFERRED);
	}
	
	/**
	 * Calculates min, max or preferred layout size.
	 * Parameter size determines is it min, max or preferred.
	 * 
	 * @param parent parent
	 * @param size which size to calculate, min, max or preferred.
	 * @return layout size
	 */
	private Dimension getLayoutSize(Container parent, ComponentSize size) {
		Dimension cellSize = getCellSize(parent, size);
		Insets insets = parent.getInsets();
		Dimension layoutSize = new Dimension(0, 0);
		
		layoutSize.width = insets.left + insets.right + gap*6 + cellSize.width*7;
		layoutSize.height = insets.top + insets.bottom + gap*4 + cellSize.height*5;
		
		return layoutSize;
	}

	/**
	 * Calculates min, max or preferred size of cells.
	 * 
	 * @param parent parent
	 * @param size which size to calculate, min, max or preferred.
	 * @return cell size
	 */
	private Dimension getCellSize(Container parent, ComponentSize size) {
		Dimension cellSize;
		if (size == ComponentSize.MAXIMUM) {
			cellSize = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
		} else {
			cellSize = new Dimension(0, 0);
		}
		
		for (Component component : components.keySet()) {
			RCPosition position = components.get(component);
			Dimension componentSize;
			int x, y;
			if (position.getRow() == 1 && position.getColumn() == 1) {
				x = 4*gap;
				y = 5;
			} else {
				x = 0;
				y = 1;
			}
			
			if (size == ComponentSize.MAXIMUM) {
				componentSize = component.getMaximumSize();
				if (componentSize == null) continue;	
				cellSize.width = Math.min(cellSize.width, (componentSize.width-x)/y);
				cellSize.height = Math.min(cellSize.height, componentSize.height);
			
			} else if (size == ComponentSize.MINIMUM) {
				componentSize = component.getMinimumSize();
				if (componentSize == null) continue;
				cellSize.width = Math.max(cellSize.width, (componentSize.width-x)/y);
				cellSize.height = Math.max(cellSize.height, componentSize.height);
				
			} else if (size == ComponentSize.PREFERRED){
				componentSize = component.getPreferredSize();
				if (componentSize == null) continue;
				cellSize.width = Math.max(cellSize.width, (componentSize.width-x)/y);
				cellSize.height = Math.max(cellSize.height, componentSize.height);
			}	
		}
		return cellSize;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeLayoutComponent(Component component) {
		components.remove(component);
	}

	/**
	 * Adds component to the layout and inserts it in the position
	 * that is specified as constraint.
	 * 
	 * @param component component to be added
	 * @param constraint position of component in the grid
	 * @throws CalcLayoutException in position is invalid or
	 * constraint is provided incorrectly.
	 */
	@Override
	public void addLayoutComponent(Component component, Object constraint) {
		Objects.requireNonNull(constraint, "Constraints can not be null.");
		RCPosition componentPosition = null;
		
		if (constraint.getClass() == String.class) {
			String[] position = ((String) constraint).split(",");
			
			try {
				componentPosition = new RCPosition(Integer.parseInt(position[0]),
													Integer.parseInt(position[1]));
			} catch (NumberFormatException | IndexOutOfBoundsException e) {
				throw new CalcLayoutException("Wrong constraints syntax.", e);
			}	
		} else if (constraint.getClass() == RCPosition.class) {
			componentPosition = (RCPosition) constraint;
		} else {
			throw new CalcLayoutException("Invalid constraints provided.");
		}
		
		if (components.containsKey(component)) {
			components.remove(component);
		}
		
		int row = componentPosition.getRow();
		int column = componentPosition.getColumn();
		
		if (components.containsValue(componentPosition)) {
			throw new CalcLayoutException("Position not available.");
		} else if(row < 1 || row > 5) {
			throw new CalcLayoutException("Specified row has to be between 1 and 5.");
		} else if (row == 1 && (column != 1 && column != 6 && column != 7)) {
			throw new CalcLayoutException("First row only has columns 1, 6 and 7.");
		} else if (column < 1 || column > 7) {
			throw new CalcLayoutException("Specified column has to be between 1 and 7.");
		} else {
			components.put(component, componentPosition);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getLayoutAlignmentX(Container container) {
		return 0.5f;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getLayoutAlignmentY(Container container) {
		return 0.5f;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void invalidateLayout(Container container) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension maximumLayoutSize(Container container) {
		return getLayoutSize(container, ComponentSize.MAXIMUM);
	}

}
