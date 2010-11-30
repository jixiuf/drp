
class User{
	String name;
}
public class ParamTest {
	public void change (String changeit,User user) {
		changeit="ccccccccccccccc";
		System.out.println("in:"+changeit);
		user.name="cccccccccc";
		System.out.println("user.name   in:"+changeit);
	}

	public static void main(String[] args) {
		String changeit="init";
		User user= new User();
		user.name="init";
		new ParamTest().change(changeit,user);
		System.out.println("out:"+changeit);
		System.out.println("user.name out:"+user.name);
//============================================



	}

}
