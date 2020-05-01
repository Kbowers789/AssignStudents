package AssignStudents.AssignStudents;

import java.util.Arrays;

/* Class for the Hungarian Algorithm (aka Kuhn-Munkres Algorithm) to assign the students
 * to projects. This is done by creating a column for each project slot, and padding the
 * student rows with filler students to make a square matrix, then running the algorithm
 * on this matrix.
 * This code is based off of the code written by Manish Bhojasia on the Sanfoundry website
 * (https://www.sanfoundry.com/java-program-implement-hungarian-algorithm-bipartite-matching)
 */

public class MatchStudents {
	private int [][] costMatrix;
	private int sCount, pCount, maxDim, padVal;
	private int[] assignmentByStudent, assignmentByProject;
	private int[] minSlackStudentByProject, minSlackValueByProject;
	private int[] assignedProjectByStudent, assignedStudentByProject;
	private final int[] parentStudentByAssignedProject;
	private boolean[] assignedStudents;
	
	public MatchStudents(int[][]costMatrix) {
		// defining internal matrix based on passed in matrix from the Graph class
		this.maxDim = Math.max(costMatrix.length,  costMatrix[0].length);
		this.sCount = costMatrix.length;
		this.pCount = costMatrix[0].length;
		this.padVal = this.sCount * this.pCount;
		this.costMatrix = new int[this.maxDim][this.maxDim];
		
		// padding costMatrix for any initially missing rows
		// (columns should always be the max number - is checked prior to initializing this class)
		for (int i = 0; i < this.maxDim; i++) {
			if (i < sCount) {
				if (costMatrix[i].length != this.pCount) {
					throw new IllegalArgumentException("Matrix dimensions incorrect.");
				}
				this.costMatrix[i] = Arrays.copyOf(costMatrix[i], this.maxDim);
			}
			else {
				this.costMatrix[i] = new int[this.maxDim];
			}
		}
		
		System.out.println("Matrix after being passed and proccessed by MatchStudents Class:");
		// turning all zzero values in matrix to a large constant (padVal)
		for (int s = 0; s < this.costMatrix.length; s++) {
			for (int p = 0; p < this.costMatrix[s].length; p++) {
				if (this.costMatrix[s][p]== 0) {
					this.costMatrix[s][p] = padVal;
					
				}
				System.out.print(this.costMatrix[s][p] + "\t");
			}
			System.out.println();
		}
		
		
		// defining all supporting arrays
		assignmentByStudent = new int[this.maxDim];
		assignmentByProject = new int[this.maxDim];
		minSlackStudentByProject = new int[this.maxDim];
		minSlackValueByProject = new int[this.maxDim];
		parentStudentByAssignedProject = new int[this.maxDim];
		assignedStudents = new boolean[this.maxDim];
		assignedProjectByStudent = new int[this.maxDim];
		Arrays.fill(assignedProjectByStudent, -1);
		assignedStudentByProject = new int[this.maxDim];
		Arrays.fill(assignedStudentByProject, -1);
	}
	
	 // filling in an initial set of assignments to use for algorithm
	protected void getInitialFeasibleAssignment() {
		for (int p = 0; p < maxDim; p++) {
			assignmentByProject[p] = padVal*padVal;
		}
		for (int s = 0; s < maxDim; s++) {
			for (int p = 0; p < maxDim; p++) {
				if (costMatrix[s][p] < assignmentByProject[p]) {
					assignmentByProject[p] = costMatrix[s][p];
				}
			}
		}
	}
	
	// finding the next unassigned student
	protected int getUnassignedStudent() {
		int s;
		for (s = 0; s < maxDim; s++) {
			if (assignedProjectByStudent[s] == -1) {
				break;
			}
		}
		return s;
	}
	
	// a greedy matching for when no unique assignments can be made in the matrix
	protected void greedyMatch() {
		for (int s = 0; s < maxDim; s++) {
			for (int p = 0; p < maxDim; p++) {
				if (assignedProjectByStudent[s] == -1
						&& assignedStudentByProject[p] == -1
						&& costMatrix[s][p] - assignmentByStudent[s] - assignmentByProject[p] == 0) {
					match(s, p);
				}
			}
		}
	}
	
	// marking a student as being assigned, and adding the updated values/assignments to the matching arrays
	protected void initializePhase(int s) {
		Arrays.fill(assignedStudents, false);
		Arrays.fill(parentStudentByAssignedProject, -1);
		assignedStudents[s] = true;
		for (int p = 0; p < maxDim; p++) {
			minSlackValueByProject[p] = costMatrix[s][p] - assignmentByStudent[s] - assignmentByProject[p];
			minSlackStudentByProject[p] = s;
		}
	}
	
	// adding the student and project index numbers to their respective tracking arrays
	protected void match(int s, int p) {
		assignedProjectByStudent[s] = p;
		assignedStudentByProject[p] = s;
	}
	
	// finding and subtracting the min value from each row in the matrix
	protected void reduce() {
		for (int s = 0; s < maxDim; s++) {
			int min = padVal * padVal;
			for (int p = 0; p < maxDim; p++) {
				if (costMatrix[s][p] < min) {
					min = costMatrix[s][p];
				}
			}
			for (int p = 0; p < maxDim; p++) {
				costMatrix[s][p] -= min;
			}
		}
		int[] min = new int[maxDim];
		for (int p = 0; p < maxDim; p++) {
			min[p] = padVal*padVal;
		}
		for (int s = 0; s < maxDim; s++) {
			for (int p = 0; p < maxDim; p++) {
				if (costMatrix[s][p] < min[p]) {
					min[p] = costMatrix[s][p];
				}
			}
		}
		for (int s = 0; s < maxDim; s++) {
			for (int p = 0; p < maxDim; p++) {
				costMatrix[s][p] -= min[p];
			}
		}
	}
	
	// updating label arrays
	protected void updateLabeling(int slack) {
		for (int s = 0; s < maxDim; s++) {
			if (assignedStudents[s]) {
				assignmentByStudent[s] += slack;
			}
		}
		for (int p = 0; p < maxDim; p++) {
			if (parentStudentByAssignedProject[p] != -1) {
				assignmentByProject[p] -= slack;
			}
			else {
				minSlackValueByProject[p] -= slack;
			}
		}
	}
	
	protected void assignPhase() {
		while (true) {
			int minSlackStudent = -1, minSlackProject = -1;
			int minSlackValue = padVal * padVal;
			for (int p = 0; p < maxDim; p++) {
				if (parentStudentByAssignedProject[p] == -1) {
					if (minSlackValueByProject[p] < minSlackValue) {
						minSlackValue = minSlackValueByProject[p];
						minSlackStudent = minSlackStudentByProject[p];
						minSlackProject = p;
					}
				}
			}
			if (minSlackValue > 0) {
				updateLabeling(minSlackValue);
			}
			parentStudentByAssignedProject[minSlackProject] = minSlackStudent;
			if (assignedStudentByProject[minSlackProject] == -1) {
				int assignedProject = minSlackProject;
				int parentStudent = parentStudentByAssignedProject[assignedProject];
				while(true) {
					int temp = assignedProjectByStudent[parentStudent];
					match(parentStudent, assignedProject);
					assignedProject = temp;
					if (assignedProject == -1) {
						break;
					}
					parentStudent = parentStudentByAssignedProject[assignedProject];
				}
				return;
			}
			else {
				int student = assignedStudentByProject[minSlackProject];
				assignedStudents[student] = true;
				for (int p = 0; p < maxDim; p++) {
					if (parentStudentByAssignedProject[p] == -1) {
						int slack = costMatrix[student][p] - assignmentByStudent[student] - assignmentByProject[p];
						if (minSlackValueByProject[p] > slack) {
							minSlackValueByProject[p] = slack;
							minSlackStudentByProject[p] = student;
						}
					}
				}
			}
		}
	}
	
	// public function to be called by Main to run this class's algorithm process
	public int[] assign() {
		reduce();
		getInitialFeasibleAssignment();
		greedyMatch();
		int s = getUnassignedStudent();
		while (s < maxDim) {
			initializePhase(s);
			assignPhase();
			s = getUnassignedStudent();
		}
		int[] assignments = Arrays.copyOf(assignedStudentByProject, pCount);
		for (s = 0; s < assignments.length; s++) {
			if (assignments[s] >= pCount) {
				assignments[s] = -1;
			}
		}
		return assignments;
	}

}