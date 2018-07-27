package hr.fer.zemris.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Vector3DTest {

	private final static double DELTA = 1E-3;
	
	@Test
	public void testNorm() {
		Vector3 v = new Vector3(1, 2, 2);
		
		assertEquals(3, v.norm(), DELTA);
	}
	
	@Test
	public void testNormalized() {
		Vector3 v = new Vector3(3, 2, -1).normalized();
		
		assertEquals(0.801, v.getX(), DELTA);
		assertEquals(0.534, v.getY(), DELTA);
		assertEquals(-0.267, v.getZ(), DELTA);
	}
	
	@Test
	public void testAdd() {
		Vector3 v = new Vector3(3, 2, -1).add(new Vector3(3, 2, -1));
		
		assertEquals(6, v.getX(), DELTA);
		assertEquals(4, v.getY(), DELTA);
		assertEquals(-2, v.getZ(), DELTA);
	}
	
	@Test
	public void testSub() {
		Vector3 v = new Vector3(3, 12, -1).sub(new Vector3(3, 2, -1));
		
		assertEquals(0, v.getX(), DELTA);
		assertEquals(10, v.getY(), DELTA);
		assertEquals(0, v.getZ(), DELTA);
	}
	
	@Test
	public void testDot() {
		double product = new Vector3(3, 2, -1).dot(new Vector3(3, 2, 2));
		
		assertEquals(11, product, DELTA);
	}

	@Test
	public void testCross() {
		Vector3 v = new Vector3(-1, 7, 4).cross(new Vector3(-5, 8, 4));
		
		assertEquals(-4, v.getX(), DELTA);
		assertEquals(-16, v.getY(), DELTA);
		assertEquals(27, v.getZ(), DELTA);
	}
	
	@Test
	public void testScale() {
		Vector3 v = new Vector3(-1, 7, 4).scale(2);
		
		assertEquals(-2, v.getX(), DELTA);
		assertEquals(14, v.getY(), DELTA);
		assertEquals(8, v.getZ(), DELTA);
	}
	
	@Test
	public void testCosAngle() {
		double angle = new Vector3(-1, 7, 4).cosAngle(new Vector3(1, 2, 3));
		
		assertEquals(0.822, angle, DELTA);
	}
	
	@Test
	public void testToArray() {
		double[] array = new Vector3(-1, 7, 4).toArray();
		
		assertEquals(-1, array[0], DELTA);
		assertEquals(7, array[1], DELTA);
		assertEquals(4, array[2], DELTA);
	}
	
}
