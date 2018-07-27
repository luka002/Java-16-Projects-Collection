package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Program that is representation of ray-caster for rendering
 * of 3D scenes. It draws scene based on arguments provided.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class RayCaster {

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
	 * Produces representation ray-tracer. Fills arrays that represent
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
				short[] rgb = new short[3];
				int offset = 0;
				
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner
												.add(xAxis.scalarMultiply((x*horizontal)/(width-1)))
												.sub(yAxis.scalarMultiply((y*vertical)/(height-1)));
						Ray ray = Ray.fromPoints(eye, screenPoint);
						tracer(scene, ray, rgb);
						
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}
				
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}
	
	/**
	 * Finds color for provided scene and ray and puts it in rgb.
	 * 
	 * @param scene scene.
	 * @param ray ray.
	 * @param rgb rgb.
	 */
	public static void tracer(Scene scene, Ray ray, short[] rgb) {
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;
		RayIntersection closest = findClosestIntersection(scene, ray);
		
		if (closest == null) {
			return;
		}
		
		rgb[0] = 15;
		rgb[1] = 15;
		rgb[2] = 15;
		
		for (LightSource light : scene.getLights()) {
			Ray newRay = Ray.fromPoints(light.getPoint(), closest.getPoint());
			RayIntersection lightIntersection = findClosestIntersection(scene, newRay);
			if (lightIntersection == null ||
					lightIntersection.getDistance() < closest.getPoint().sub(light.getPoint()).norm()-0.001) {
				continue;
			}
			Point3D r =	closest.getNormal()
							.scalarMultiply(2*newRay.direction.negate().scalarProduct(closest.getNormal()))
							.sub(newRay.direction.negate());
			
			double diffuseAngle = newRay.direction.negate().scalarProduct(lightIntersection.getNormal());
			double reflectedAngle = r.scalarProduct(ray.direction.negate());			
			
			if (diffuseAngle < 0) diffuseAngle = 0;
			if (reflectedAngle < 0) {
				reflectedAngle = 0;
			} else {
				reflectedAngle = Math.pow(reflectedAngle, closest.getKrn());
			}
			
			rgb[0] += (short) (light.getR()*(closest.getKdr()*diffuseAngle + closest.getKrr()*reflectedAngle));
			rgb[1] += (short) (light.getG()*(closest.getKdg()*diffuseAngle + closest.getKrg()*reflectedAngle));
			rgb[2] += (short) (light.getB()*(closest.getKdb()*diffuseAngle + closest.getKrb()*reflectedAngle));
		}
		
	}

	/**
	 * Finds closest intersection of scene and ray.
	 * 
	 * @param scene scene.
	 * @param ray ray.
	 * @return closest intersection.
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		double distance = -1;
		RayIntersection closest = null;
		
		for (GraphicalObject go : scene.getObjects()) {
			RayIntersection ri = go.findClosestRayIntersection(ray);
			
			if (ri == null) {
				continue;
			} else if (distance == -1) {
				distance = ri.getDistance();
				closest = ri;
			} else if (ri.getDistance() < distance) {
				distance = ri.getDistance();
				closest = ri;		
			}
			
			
		}
		
		return closest;
	}

}
