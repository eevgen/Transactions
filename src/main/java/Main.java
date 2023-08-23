import java.util.*;

public class Main {
    private static ArrayList usersList = new ArrayList();
    public static void main(String[] args) {

        User user1 = new User("Oleg");
        User user2 = new User("Murat");
        User user3 = new User("Lena");
        User user4 = new User("Sasha");
        User user5 = new User("Lera");


        putUsersIntoList(user1, user2, user3, user4, user5);

        Thread allProcesses = new Thread(new Processes());

        allProcesses.start();
    }
    private static void putUsersIntoList(User... users) {
        Arrays.stream(users).forEach(user -> usersList.add(user));
    }

    public ArrayList getUsersList() {
        return usersList;
    }
}