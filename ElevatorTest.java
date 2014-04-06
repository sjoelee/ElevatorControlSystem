import java.util.Scanner;

public class ElevatorTest {
    int max_floors = 6; // 0 = ground floor
    public static void main(String[] args) {
        int curr_floor;
        int dir;

        Elevator e = new Elevator(0, 6);
        /* Console console = System.console(); */
        /* String input = console.readLine("Enter input:"); */
        e.enqueueFloorRequest(5);
        e.enqueueFloorRequest(1);
        int idx;
        for (idx = 0; idx < 10; idx++) {
            e.getState();
            System.out.println("Current goal: " + e.getGoalFloor());
            e.advance();
            if (idx == 5) {
                e.enqueueFloorRequest(3);
            }
        }
    }
}
