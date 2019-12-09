import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class keeps and maintaings the graph and internal data of the friend network for A03. 
 * It does this by keeping an ArrayList of the people stored as strings and then using this list
 * it creates a boolean matrix where each element of the matrix represents a friendship between
 * two people. 
 * 
 * An example is put below
 * 
 * 			Alex | James | Charles 
 * 	Alex   |False| True  | False
 * James   |False| False | True
 * Charles |True | True  | False
 * 
 * 
 * In this case the ArrayList orderedElements would be orderedElements = [Alex, James, Charles]
 * and the connected to matrix would be
 *  connectedTo = |False| True  | False
 * 		  	      |False| False | True
 * 		          |True | True  | False
 * 
 * This matix can be read by line by like the following
 * 
 * Alex is friends with James (indicated by a true in that element and not friends is false)
 * James is friends with Charles
 * Charles is friends with Alex and James
 * 
 * For an explict example run the program and look at the code in main and you can see
 * the basic functioning of the code
 * 
 * 
 * @author zachh
 *
 */
public class NetworkGraph {
	
	private boolean connectedTo[][] = new boolean[1][1];	//matrix that keeps track of edges to
	private boolean connectedFrom[][] = new boolean[1][1];	//matrix taht keeps track of deges from
	private int size;								///keeps track of number of elements
	
	//keeps track of which element is in the graph
	private List<String> orderedElements = new ArrayList<String>();	
	private Set<String> allElements = new HashSet<String>();
	
	private List<Person> personList = new ArrayList<Person>(); //not used in current implenetation
	
	private Person currentPerson;
	
	/*
	 * Default no-argument constructor
	 */ 
	public NetworkGraph() {
		connectedTo[0][0] = false;
		connectedFrom[0][0] = false;
		size = 0;	
	}

	/**
     * Add new Person to the graph.
     *
     * If vertex is null or already exists,
     * method ends without adding a vertex or 
     * throwing an exception.
     * 
     * Valid argument conditions:
     * 1. vertex is non-null
     * 2. vertex is not already in the graph 
     */
	public void addPerson(String name) {
		//if vertex is null or already added just return without doing anything
		if (name == null){
			return;
		}
		if (orderedElements.contains(name)){
			return;
		}
				
		//make a new connectity matrix with an extra dimension for the newly added vertex 
		//and copy all the points form the last matrix into the new temp
		boolean tempTo[][] = new boolean[size + 1][size + 1];
		boolean tempFrom[][] = new boolean[size + 1][size + 1];
		//copies all the data from the current matrix into the new matrix
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				tempTo[i][j] = connectedTo[i][j];
				tempFrom[i][j] = connectedFrom[i][j];
			}
		}
		orderedElements.add(name);
		allElements.add(name);

		size++;
		connectedTo = tempTo;
		connectedFrom = tempFrom;
		
	}

	/**
     * Remove a vertex and all associated 
     * edges from the graph.
     * 
     * If vertex is null or does not exist,
     * method ends without removing a vertex, edges, 
     * or throwing an exception.
     * 
     * Valid argument conditions:
     * 1. vertex is non-null
     * 2. vertex is not already in the graph 
     */
	public void removePerson(String name) {
		
		//if vertex is null or not added just return without doing anything
		if (name == null || !orderedElements.contains(name)) {
			return;
		}
		
		int removedIndex = orderedElements.indexOf(name);
		
		
		//make a new connectity matrix with an extra dimension for the newly added vertex 
		//and copy all the points form the last matrix into the new temp
		boolean tempTo[][] = new boolean[size - 1][size - 1];
		boolean tempFrom[][] = new boolean[size - 1][size - 1];
		//copies all the data from the current matrix into the new matrix
		//the offsets are to handle when you are copying the data over to a smaller matrix
		//it makes sure after you get to the removed vertex the data after is put in the index
		//minus 1 otherwise the data would overflow the size of the new smaller matrix
		int iOffset = 0;
		for (int i = 0; i < size; i++) {
			if (i == removedIndex) {
				iOffset = -1;
				continue;
			}
			int jOffset = 0;
			for (int j = 0; j < size; j++) {
				//checks if this is the data from the removed index if it is don't copy anything
				if (j == removedIndex) {
					jOffset = -1;
					continue;
				}
				tempTo[i + iOffset][j + jOffset] = connectedTo[i][j];
				tempFrom[i + iOffset][j + jOffset] = connectedFrom[i][j];
			}
		}
		
		orderedElements.remove(removedIndex);
		allElements.remove(name);
		//shifts all the elements in the list over after removing that vertex
		for (int i = removedIndex; i < size -1; i++) {			
			//could have an off by one error
			orderedElements.add(i,orderedElements.get(i+1));
		}
		orderedElements.remove(size-1);
		size--;

	}

	/**
     * Add the edge from vertex1 to vertex2
     * to this graph.  (edge is directed and unweighted)
     * If either vertex does not exist,
     * add vertex, and add edge, no exception is thrown.
     * If the edge exists in the graph,
     * no edge is added and no exception is thrown.
     * 
     * Valid argument conditions:
     * 1. neither vertex is null
     * 2. both vertices are in the graph 
     * 3. the edge is not in the graph
	 */
	public void addFriend(String person1, String person2) {
		
		//if either vertex are null return without throwing exception
		if (person1 == null || person2 == null) {
			return;
		}
		if (!orderedElements.contains(person1)) {
			return;
		}
		if (!orderedElements.contains(person2)) {
			return;
		}
		
		//the i and j where you need to add change to true since they 
		//will now be an edge between them
		int firstIndex = orderedElements.indexOf(person1);
		int secondIndex = orderedElements.indexOf(person2);
	
		connectedFrom[firstIndex][secondIndex] = true;
		connectedTo[secondIndex][firstIndex] = true;
				
	}
	
	/**
     * Remove the edge from person1 to person2
     * from this graph.  
     * If either person does not exist,
     * or if an edge from vertex1 to vertex2 does not exist,
     * no edge is removed and no exception is thrown.
     * 
     * Valid argument conditions:
     * 1. neither vertex is null
     * 2. both vertices are in the graph 
     * 3. the edge from vertex1 to vertex2 is in the graph
     */
	public void removeFriend(String vertex1, String vertex2) {
		//if either vertex are null return without throwing exception
		if (vertex1 == null || vertex2 == null) {
			return;
		}
		if (!orderedElements.contains(vertex1)) {
			return;
		}
		if (!orderedElements.contains(vertex2)) {
			return;
		}
		
		//the i and j where you need to add change to false since they 
		//will now be no edge between them
		int firstIndex = orderedElements.indexOf(vertex1);
		int secondIndex = orderedElements.indexOf(vertex2);
	
		connectedFrom[firstIndex][secondIndex] = false;
		connectedTo[secondIndex][firstIndex] = false;
				
	}	

	/**
     * Returns a Set that contains all the people in the network
     * 
	 */
	public Set<String> getAllPeople() {
		return allElements;
	}

	/**
     * Get all the friends of the input name
     * 
     * if this is returning all the connections to the name just switch connectedFrom[nameIndex][j]
     * to connectedTo[nameIndex][j] 
     *
	 */
	public List<String> getFriends(String name) {
		
		List<String> friends = new ArrayList<String>();
		int nameIndex = orderedElements.indexOf(name);
    	//this checks the connection matrix to see which nodes are connected and adds them if
		//there is a connection from the input string to the another person
    	for (int j = 0; j < size; j++) {
    		if (connectedFrom[nameIndex][j] == true) {
    			String adjacentString = orderedElements.get(j);
    			friends.add(adjacentString);
    		}
    	}    		
		return friends;
	}
	
	/**
	 * Gets the number of friends in the network of the input name
	 * 
	 * @param name of the person you want to get the number of friends of
	 * @return int of the number of friends
	 */
	public int getNumberOfFriends(String name) {
		return getFriends(name).size();
	}
	
	/**
     * Returns the number of edges or friendships in this graph.
     */
    public int size() {
    	//sum all the elements in the matrix to get the number of connections between people
    	int sum = 0;
    	for (int i = 0; i < size; i++) {
    		for (int j = 0; j < size; j++) {
    			if (connectedTo[i][j] == true) {
    				sum++;
    			}
    		}
    	}
		return sum;
	}

	/**
     * Returns the number of people in this graph.
     */
	public int order() {
        return size;
    }
	
	/**
	 * clears all the data in the network and resets it to the default values. 
	 */
	public void clearNetwork() {
		connectedTo = new boolean[1][1];
		connectedFrom = new boolean[1][1];
		size = 0;
		
		orderedElements = new ArrayList<String>();	
		allElements = new HashSet<String>();
		personList = new ArrayList<Person>();
		currentPerson = null;
	}
	/**
	 * Some example code showing what is class javadoc
	 */
	public static void main(String[] args) {
		//create a network instances that is empty
		NetworkGraph newNetwork = new NetworkGraph();
		
		//then add the people into the network
		newNetwork.addPerson("Alex");
		newNetwork.addPerson("James");
		newNetwork.addPerson("Charles");
		
		//then add the friendships into the network
		newNetwork.addFriend("Alex", "James");
		newNetwork.addFriend("James", "Charles");
		newNetwork.addFriend("Charles", "Alex");
		newNetwork.addFriend("Charles", "James");
		
		//can retrieve friend list in string array here
		System.out.println(newNetwork.getFriends("Alex"));
		System.out.println(newNetwork.getFriends("James"));
		System.out.println(newNetwork.getFriends("Charles"));
		
		//can get number of friends of any person in string format
		System.out.println(newNetwork.getNumberOfFriends("Charles"));
		
		//can clear the network
		newNetwork.clearNetwork();
		
		//showing the cleared output
		System.out.println(newNetwork.getNumberOfFriends("Charles"));
		
	}
	
}
