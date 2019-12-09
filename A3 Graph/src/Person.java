import java.util.List;

public class Person {
	private String name;
	private List<String> friendsList;
	
	//constructor for a person with no friends just inputting the name
	public Person(String name) {
		this.name = name;
	}
		
	
	/**
	 * This method returns the friends of the person, but you have to pass it the network 
	 * which you want to see who is friends with them. 
	 * 
	 * I would recommonend using the getFriends method inside NetworkGraph.java instead 
	 * of this method but I added it just to be consistant with the design document
	 * 
	 * @param network the network you are asking who is friends with other people
	 * @return an arrayList object of strings with the friends names
	 */
	public List<String> getFriendsList(NetworkGraph network) {
		friendsList = network.getFriends(this.name);
		return friendsList;
	}
	/**
	 * This function returns the number of friends of the person.
	 * 
	 *  I would recommonend using the getNumberOfFriends method inside NetworkGraph.java instead 
	 * of this method but I added it just to be consistant with the design document
	 * 
	 * @param network the network that the friend is in
	 * @return int of the number of people
	 */
	public int getNumFriends(NetworkGraph network) {
		//gets the friendsList and checks the size and returns the size
		return getFriendsList(network).size();
	}
}
