package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Program that draws fractals derived from Newton-Raphson iteration.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class Newton {

	/**
	 * Method that first executes when program starts.
	 * For fractal enter:
	 * Root 1> 1
	 * Root 2> -1 + i0
	 * Root 3> i
	 * Root 4> 0 - i1
	 * Root 5> done
	 * 
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		List<Complex> list = new ArrayList<>();
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\n" + 
				"Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		int root = 1;
		while (true) {
			System.out.print("Root " + (root++) + "> ");
			String input = scanner.nextLine();
			
			if (input.equals("done")) {
				break;
			} else {
				try {
					list.add(Complex.parse(input));
				} catch (NumberFormatException e) {
					System.out.println("Wrong complex number provided. Try again.");
					root--;
				}
			}
		}
		scanner.close();
		
		if (list.size() < 2) {
			System.out.println("At least two roots are needed for calculation.");
			System.exit(1);
		}
		
		System.out.println("Image of fractal will appear shortly. Thank you.");
		Complex[] complexNumbers = new Complex[list.size()];
		FractalViewer.show(new MyProducer(new ComplexRootedPolynomial(list.toArray(complexNumbers))));	
	}

	/**
	 * Representation of one job.
	 * 
	 * @author Luka Grgić
	 * @version 1.0
	 */
	public static class PosaoIzracuna implements Callable<Void> {
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		ComplexRootedPolynomial crp;

		public PosaoIzracuna(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax, 
				int m, short[] data, ComplexRootedPolynomial crp) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.crp = crp;
		}

		/**
		 * Method called on job start.
		 */
		@Override
		public Void call() {
			for (int y = yMin; y <= yMax; y++) {
				for (int x = 0; x < width; x++) {
					double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
					double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
					Complex zn = new Complex(cre, cim);
					Complex zn1 = null;
					int iter = 0;
					double module;
					
					do {
						Complex numerator = crp.toComplexPolynom().apply(zn);
						Complex denominator = crp.toComplexPolynom().derive().apply(zn);
						Complex fraction = numerator.divide(denominator);
						zn1 = zn.sub(fraction);
						module = zn1.sub(zn).module();
						zn = zn1;
						iter++;
					} while (module > 1E-3 && iter < m);

					int index = crp.indexOfClosestRootFor(zn1, 1E-3);
					data[width * y + x] = (short) (index + 1);
				}
			}

			return null;
		}
	}
	
	/**
	 * Represents pool of threads.
	 */
	static ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new ThreadFactory() {
		@Override
		public Thread newThread(Runnable r) {
			Thread worker = new Thread(r);
			worker.setDaemon(true);
			return worker;
		}
	});
	
	/**
	 * Produces jobs and executes them.
	 * 
	 * @author Luka Grgić
	 * @version 1.0
	 */
	public static class MyProducer implements IFractalProducer {
		
		/** Complex number polynom */
		private ComplexRootedPolynomial crp;
		
		/**
		 * Constructor.
		 * 
		 * @param crp Complex number polynom.
		 */
		public MyProducer(ComplexRootedPolynomial crp) {
			this.crp = crp;
		}
		
		/**
		 * Produces jobs and executes them. Jobs fill
		 * the array that represents pixel color and gives it
		 * to observer.
		 * 
		 * @param reMin Minimal x coordinate.
		 * @param reMax Maximal x coordinate.
		 * @param imMin Minimal y coordinate.
		 * @param imMax Maximal y coordinate.
		 * @param width Screen width.
		 * @param height Screen height.
		 * @param requestNo requestno
		 * @param observer Accepts data for visualization.
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer) {
			System.out.println("Zapocinjem izracun...");
			int m = 16*16*16;
			short[] data = new short[width * height];
			final int brojTraka = Runtime.getRuntime().availableProcessors()*8;
			int brojYPoTraci = height / brojTraka;

			List<Future<Void>> rezultati = new ArrayList<>();
			
			for(int i = 0; i < brojTraka; i++) {
				int yMin = i*brojYPoTraci;
				int yMax = (i+1)*brojYPoTraci-1;
				if(i==brojTraka-1) {
					yMax = height-1;
				}
				PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, crp);
				rezultati.add(pool.submit(posao));
			}
			for(Future<Void> posao : rezultati) {
				try {
					posao.get();
				} catch (InterruptedException | ExecutionException e) {
					System.out.println(e.getMessage());
				}
			}
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(crp.toComplexPolynom().order() + 1), requestNo);
		}
	}
}
