package hr.fer.zemris.java.raytracer.model;

/**
 * Defines a sphere in 3D system.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class Sphere extends GraphicalObject {
	
	/** Center of sphere */
	private Point3D center;
	/** Radius of sphere */
	private double radius;
	/** Coefficient for diffuse component for red color */
	private double kdr;
	/** Coefficient for diffuse component for green color */
	private double kdg;
	/** Coefficient for diffuse component for blue color */
	private double kdb;
	/** Coefficient for reflective component for red color */
	private double krr;
	/** Coefficient for reflective component for green color */
	private double krg;
	/** Coefficient for reflective component for blue color */
	private double krb;
	/** Coefficient n for reflective component */
	private double krn;
	
	public Sphere(Point3D center, double radius, double kdr, double kdg,
			double kdb, double krr, double krg, double krb, double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Point3D v = ray.start.sub(center);
		double discriminant = Math.sqrt(Math.pow(v.scalarProduct(ray.direction), 2) - 
									   ((Math.pow(v.norm(), 2) - radius*radius)));
		
		if (Double.isNaN(discriminant)) return null;
		
		double distance1 = -1*(v.scalarProduct(ray.direction)) + discriminant;
		double distance2 = -1*(v.scalarProduct(ray.direction)) - discriminant;
		
		if (distance1 <= distance2) {
			return intersection(ray, distance1);
		} else {
			return intersection(ray, distance2);
		}

	}
	
	/**
	 * Creates instance on RacIntersection class for provided
	 * ray and distance.
	 * 
	 * @param ray Ray.
	 * @param distance distance.
	 * @return RayIntersection.
	 */
	private RayIntersection intersection(Ray ray, double distance) {
		Point3D intersection = ray.start.add(ray.direction.scalarMultiply(distance));
		return new ConcreteRayIntersection(intersection, distance, true);
	}
	
	/**
	 * Concrete implementation of RayIntersection abstract class.
	 * 
	 * @author Luka Grgić
	 * @version 1.0
	 */
	private class ConcreteRayIntersection extends RayIntersection{
		
		/** Point on intersection */
		private Point3D point;

		/**
		 * Constructor initializes object.
		 * 
		 * @param point point of intersection.
		 * @param distance distance from view point to intersection.
		 * @param outer true if outer intersection else false.
		 */
		protected ConcreteRayIntersection(Point3D point, double distance, boolean outer) {
			super(point, distance, outer);
			this.point = point;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Point3D getNormal() {
			return point.sub(center).normalize();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getKdr() {
			return kdr;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getKdg() {
			return kdg;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getKdb() {
			return kdb;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getKrr() {
			return krr;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getKrg() {
			return krg;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getKrb() {
			return krb;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getKrn() {
			return krn;
		}
		
		
	}

}
