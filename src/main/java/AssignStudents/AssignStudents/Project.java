package AssignStudents.AssignStudents;

import java.util.ArrayList;
import java.util.List;

//Class for project nodes in our network graph;
//Adds the available capacity of the project
public class Project {
	private String name;
	private int capacity;
	private List<Student> assigned;
	
	Project(String p, int cap) {
		this.name = p;
		this.capacity = cap;
		this.assigned = new ArrayList<Student>(cap);
	}
	
	public String getProjectName() {
		return name;
	}
	
	public boolean assignStudent(Student s) {
		boolean res = false;
		if (assigned.size() < (capacity)) {
			if (assigned.contains(s) == false) {
				assigned.add(s);
				res =  true;
			}
		}
		return res;
	}
	
	public boolean removeStudent(Student s) {
		boolean res = false;	
		if (assigned.contains(s)) {
			assigned.remove(s);
			res =  true;
			}
		return res;
	}
	
	public List<Student> getAssigned() {
		return this.assigned;
	}
	
	public int getCapacity() {
		return this.capacity;
	}
}