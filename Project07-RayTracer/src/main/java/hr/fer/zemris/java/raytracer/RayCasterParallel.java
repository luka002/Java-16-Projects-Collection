package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Program that is representation of ray-caster for rendering
 * of 3D scenes. It creates smaller jobs for multiple threads
 * using JoinForkPool and executes them recursively until threshold
 * is reached and then draws in on the screen.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class RayCasterParallel {
	
	/**
	 * Method that first executes when program starts.
	 * 
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Representation of one job.
	 * 
	 * @author Luka Grgić
	 * @version 1.0
	 */
	public static class Job extends RecursiveAction {
		private static final long serialVersionUID = 1L;
		/** Eye position */
		private Point3D eye;
		/** x axis */
		private Point3D xAxis;
		/** y axis */
		private Point3D yAxis;
		/** Point of upper left screen corner. */
		private Point3D screenCorner;
		/** Scene */
		private Scene scene;
		/** Array for red color */
		private short[] red;
		/** Array for green color */
		private short[] green;
		/** Array for blue color */
		private short[] blue;
		/** Distance from view position to upper/lower boundary */
		private double horizontal;
		/** Distance from view position to left/right boundary */
		private double vertical;
		/** Screen width */
		private int width;
		/** Screen height */
		private int height;
		/** y where job starts */
		private int yMin;
		/** y where job finished */
		private int yMax;
		/** Threshold for how many rows are allowed per job */
		static final int treshold = 16;

		/**
		 * Constructor.
		 * 
		 * @param eye Eye position.
		 * @param xAxis x axis.
		 * @param yAxis y axis. 
		 * @param screenCorner Point of upper left screen corner.
		 * @param scene Scene.
		 * @param red Array for red color.
		 * @param green Array for green color.
		 * @param blue Array for blue color.
		 * @param rgb Array for rbg of one point.
		 * @param horizontal Distance from view position to upper/lower boundary.
		 * @param vertical Distance from view position to left/right boundary.
		 * @param width Screen width.
		 * @param height Screen height.
		 * @param yMin y where job starts.
		 * @param yMax y where job finishes.
		 */
		public Job(Point3D eye, Point3D xAxis, Point3D yAxis, Point3D screenCorner, Scene scene, short[] red,
				short[] green, short[] blue, double horizontal, double vertical, int width, int height, int yMin,
				int yMax) {
			super();
			this.eye = eye;
			this.xAxis = xAxis;
			this.yAxis = yAxis;
			this.screenCorner = screenCorner;
			this.scene = scene;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
		}

		/**
		 * Recursively delegates job on smaller jobs until
		 * threshold is reached and then executed the job.
		 */
		@Override
		public void compute() {
			if(yMax-yMin+1 <= treshold) {
				computeDirect();
				return;
			}
			invokeAll(
				new Job(eye, xAxis, yAxis, screenCorner, scene, red, green, blue, horizontal, 
						vertical, width, height, yMin, yMin+(yMax-yMin)/2),
				new Job(eye, xAxis, yAxis, screenCorner, scene, red, green, blue, horizontal, 
						vertical, width, height, yMin+(yMax-yMin)/2+1, yMax)
			);
		}

		/**
		 * Job that will be executed if threshold is reached.
		 */
		public void computeDirect() {
				short[] rgb = new short[3];
				
				for (int y = yMin; y <= yMax; y++) {
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner
												.add(xAxis.scalarMultiply((x * horizontal) / (width - 1)))
												.sub(yAxis.scalarMultiply((y * vertical) / (height - 1)));
						Ray ray = Ray.fromPoints(eye, screenPoint);
						RayCaster.tracer(scene, ray, rgb);
						red[y * width + x] = rgb[0] > 255 ? 255 : rgb[0];
						green[y * width + x] = rgb[1] > 255 ? 255 : rgb[1];
						blue[y * width + x] = rgb[2] > 255 ? 255 : rgb[2];
					}
				}
			
		}
	}

	/**
	 * Produces ray-tracer. Fills arrays that represent
	 * color for each pixel on the screen.
	 * 
	 * @return ray-tracer.
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			
			/**
			 * Fills arrays that represent colors and that gives them to observer.
			 * 
			 * @param eye Eye position.
			 * @param view View position.
			 * @param viewUp View up vector.
			 * @param horizontal Distance from view position to upper/lower boundary.
			 * @param vertical Distance from view position to left/right boundary.
			 * @param width Screen width.
			 * @param height Screen height.
			 * @param requestNo requestno
			 * @param observer Accepts data for visualization.
			 */
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer) {
				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];
				
				Point3D zAxis = (view.sub(eye)).normalize();
				Point3D yAxis = (viewUp.sub(zAxis.scalarMultiply(zAxis.scalarProduct(viewUp)))).normalize();
				Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal/2)).add(yAxis.scalarMultiply(vertical/2));

				Scene scene = RayTracerViewer.createPredefinedScene();

				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new Job(eye, xAxis, yAxis, screenCorner, scene, red, green, 
						blue, horizontal, vertical, width, height, 0, height-1));
				pool.shutdown();
				
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}

}
