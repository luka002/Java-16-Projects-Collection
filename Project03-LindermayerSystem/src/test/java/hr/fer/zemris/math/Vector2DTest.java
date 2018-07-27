package hr.fer.zemris.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Vector2DTest {

	@Test
	public void constructorAndGetters() {
		Vector2D vector = new Vector2D(2.0, 3.0);
		
		assertEquals(2.0, vector.getX(), 1e-3);
		assertEquals(3.0, vector.getY(), 1e-3);
	}
	
	@Test
	public void translateTest() {
		Vector2D vector = new Vector2D(2.0, 3.0);
		
		vector.translate(new Vector2D(1.0, 7.0));
		
		assertEquals(3.0, vector.getX(), 1e-3);
		assertEquals(10.0, vector.getY(), 1e-3);
	}
	
	@Test
	public void translatedTest() {
		Vector2D vector = new Vector2D(2.0, 3.0);
		
		Vector2D vector2 = vector.translated(new Vector2D(1.0, 7.0));
		
		assertEquals(2.0, vector.getX(), 1e-3);
		assertEquals(3.0, vector.getY(), 1e-3);
		
		assertEquals(3.0, vector2.getX(), 1e-3);
		assertEquals(10.0, vector2.getY(), 1e-3);
	}
	
	@Test
	public void rotateTest() {
		Vector2D vector = new Vector2D(2.0, 0.0);
		
		vector.rotate(270);
		vector.rotate(-180);
		
		assertEquals(0.0, vector.getX(), 1e-3);
		assertEquals(2.0, vector.getY(), 1e-3);
	}
	
	@Test
	public void rotatedTest() {
		Vector2D vector = new Vector2D(2.0, 2.0);
		
		Vector2D vector2 = vector.rotated(180);
		
		assertEquals(2.0, vector.getX(), 1e-3);
		assertEquals(2.0, vector.getY(), 1e-3);
		
		assertEquals(-2.0, vector2.getX(), 1e-3);
		assertEquals(-2.0, vector2.getY(), 1e-3);
	}
	
	@Test
	public void scaleTest() {
		Vector2D vector = new Vector2D(2.0, 3.0);
		
		vector.scale(1.2);
				
		assertEquals(2.0*1.2, vector.getX(), 1e-3);
		assertEquals(3.0*1.2, vector.getY(), 1e-3);
	}
	
	@Test
	public void scaledTest() {
		Vector2D vector = new Vector2D(2.0, 2.0);
		
		Vector2D vector2 = vector.scaled(0.6);
		
		assertEquals(2.0, vector.getX(), 1e-3);
		assertEquals(2.0, vector.getY(), 1e-3);
		
		assertEquals(2.0*0.6, vector2.getX(), 1e-3);
		assertEquals(2.0*0.6, vector2.getY(), 1e-3);
	}
	
	@Test
	public void copyTest() {
		Vector2D vector = new Vector2D(2.0, 2.0);
		
		Vector2D vector2 = vector.copy();
		
		assertEquals(2.0, vector2.getX(), 1e-3);
		assertEquals(2.0, vector2.getY(), 1e-3);
	}
	
}
