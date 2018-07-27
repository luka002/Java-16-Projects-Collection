package hr.fer.zemris.java.custom.collections;

public class Test {

	public static void main(String[] args) {

		Dictionary dictionary = new Dictionary();
		
		dictionary.put(new String("Auto"), new String("Porsche"));
		dictionary.put(new String("Kamion"), new String("Iveco"));
		dictionary.put(new String("Formula"), new String("F1"));
		dictionary.put(new String("Traktor"), new String("Iris"));
		
		System.out.println(dictionary.get(new String("Auto")));
		System.out.println(dictionary.get(new String("Kamion")));
		
		dictionary.put(new String("Formula"), new String("Iveco222"));
		
		System.out.println(dictionary.get(new String("Formula")));
		System.out.println(dictionary.get(new String("Traktor")));
		System.out.println(dictionary.get(new String("klklkl")));


	}
}
