import java.util.HashSet;

class Ball {
	private int number;
	
	public Ball(int number) {
		this.number = number;
	}
	
	public int hashCode()
	{
		return number;
	}
	
	public boolean equals( Object obj )
	{
		boolean flag = false;
		Ball b = (Ball)obj;
		if( b.getNumber() == this.number )
			flag = true;
		return flag;
	}

	private int getNumber() {
		return this.number;
	}
}

public class Casino {
	public static void main(String[] args) {
		HashSet<Ball> casino = new HashSet<Ball>();
		casino.add(new Ball(4));
		casino.add(new Ball(8));
		casino.add(new Ball(15));
		casino.add(new Ball(16));
		casino.add(new Ball(23));
		casino.add(new Ball(42));
		
		System.out.println("HashSet Size--->>>"+casino.size());
		System.out.println("hs.contains( new Emp(25))--->>> "
				+ casino.contains(new Ball(23)));
		System.out.println("hs.remove( new Emp(24)--->>> " 
				+ casino.remove( new Ball(42)));
		System.out.println("Now HashSet Size--->>>" 
				+ casino.size());
		
	}
}
