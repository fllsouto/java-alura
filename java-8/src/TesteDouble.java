import java.util.Objects;

public class TesteDouble {

	public static void main(String[] args) {
		final Double a = 12345d;
		final Double b = 12345d;
		
		final Integer aa = 127;
		final Integer bb = 127;
		
		final Integer cc = 130;
		final Integer dd = 130;
		
		System.out.print("a == b: ");
		System.out.println(a == b);
		
		System.out.print("aa == bb: ");
		System.out.println(aa == bb);
		
		System.out.print("cc == dd: ");
		System.out.println(cc == dd);
		
		System.out.print("cc equals dd: ");
		System.out.println(cc.equals(dd));
		
		double result = 1.00 - (0.9909 + 0.0091)*1;
		System.out.println("result: " + result);
		
		String foo1 = "foo";
		String foo2 = "foo";
		String foo3 = new String("foo");

		System.out.println("foo1 address: " + Objects.hashCode(foo1));
		System.out.println("foo2 address: " + Objects.hashCode(foo2));
		System.out.println("foo3 address: " + Objects.hashCode(foo3));
		System.out.println(foo1 == foo2);
		System.out.println(foo2 == foo3);
	}
}
