public class ElevatorTest {
    int max_floors = 5;
    public static void main(String[] args) {
        int curr_floor;
        int dir;

        Elevator e = new Elevator(0, 5);
        e.getState();
        e.requestFloor(5);
        e.advance();
        e.getState();
        e.advance();
        e.getState();
    }
}
