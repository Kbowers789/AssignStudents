package AssignStudents.AssignStudents;

import java.util.ArrayList;
import java.util.List;

// Class for student nodes in our network graph;
// Adds the student rankings of the projects
public class Student {
	private String name;
	private List<String> ranks;
	
	Student(String name, List<String> r) {
		this.name = name;
		this.ranks = new ArrayList<String>(r);
	}
	
	public String getStudentName() {
		return this.name;
	}

	public List<String> getRanks() {
		return this.ranks;
	}
	
	public int findRank(String project) {
		if (ranks.contains(project)) {
			return ranks.indexOf(project) + 1;
		}
		else return 0;
	}	
}