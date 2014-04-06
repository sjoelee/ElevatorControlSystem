public class Person {
    int curr_floor;
    int dir;
    bool inElevator;
    Elevator person_elev;
    private int desired_floor;

    public Person (init_curr_floor, init_desired_floor) {
        desired_floor = init_desired_floor;
        curr_floor = init_curr_floor;
        inElevator = false;
    }
    public void enterElevator (Elevator elev) {
        if (elev == null) {
            System.out.println("There is no elevator!");
            return;
        }
        person_elev = elev;
        inElevator = true;
        elev.requestFloor(desired_floor);
    }

    public void chooseDirection (int set_dir) {
        if (desired_floor - curr_floor > 0) {
            dir = 1;
        } else if (desired_floor - curr_floor < 0) {
            dir = -1;
        }
    }
}
            
