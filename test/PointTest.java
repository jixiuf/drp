import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PointTest {

	public static void main(String[] args) {
		Person p = new Person("name");
		Stack<Person> s = new Stack<Person>();
		List<Person> ps = new ArrayList<Person>();
		s.push(p);
		ps.add(p);
		System.out.println(s.pop()==ps.get(0));
		
	}

}

class Person {
	public Person(String name) {
		this.name = name;
	}

	public String name;

}
