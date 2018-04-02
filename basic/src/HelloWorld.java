import java.util.concurrent.TimeUnit;

public class HelloWorld {
	public static void main(String[] args) {
		/*
		System.out.println("Hello World!!");
		System.out.printf("/%5d/%n", 123);
		System.out.printf("/%+5d/%n", 123);
		System.out.printf("/%X/%n", 123);
		System.out.printf("/%#X/%n", 123);
		System.out.printf("/%f/%n", 12345.678);
		System.out.printf("/%10.2f/%n", 12345.678);
		System.out.printf("/%-10.1f/%n", 12345.678);
		System.out.printf("/%010.2f/%n", 12345.678);
		System.out.printf("/%10.2E/%n", 12345.678);
		System.out.printf("/%10.2f/%n", 12345.678);
		System.out.printf("/%10.1f/%n", 12345.678);
		System.out.printf("/%10s/%n", 12345.678);
		*/
		int h=3, m=2, s=1;
		while(true) {
			try{
				TimeUnit.SECONDS.sleep(1);
				System.out.printf("%d®É%d¤À%d¬í%n", h, m, s);
			}
			catch(InterruptedException e){
					e.printStackTrace();
			}
		}
		
		
		
		
	}

}
