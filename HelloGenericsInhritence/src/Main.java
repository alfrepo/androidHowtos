
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		A<View> myA = new A<View>();
		A<ViewGroup> myAvg = new A<ViewGroup>();
		B myB = new B();
		C myC = new C();
		
		add(myB);
		add(myC);
		add2(myC);
		
	}
	
	static void add(A<? extends ViewGroup> param){}
	static void add2(Object o){}

}
