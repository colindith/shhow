
public class ThreeOperator {

	public static void main(String[] args) {
		int coverage=200, area=300;
		
		int count=area/coverage;
		count+=(area%coverage==0)?0:1;
		String unit=(count==1)?"can":"cans";
		System.out.printf("Need %d %s to paint%n", count, unit);
	}

}
