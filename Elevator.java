import java.util.Arrays;

public class Elevator {
    int id;
    int dir; //-1 = down, +1 = up, 0 = stationary
    private int curr_floor;
    private int goal_floor;
    private int max_floors;
    private boolean downReqExists;
    private boolean upReqExists;

    /* Createa a private list that keeps an up to date list of requests
     * for the elevator. A request is queued within the upRequests or downRequests
     * depending on where the elevator currently is.*/
    private boolean upRequests[];
    private boolean downRequests[];

    public Elevator (int init_id, int init_max_floors) { //constructor 
        id = init_id;
        curr_floor = 0;
        goal_floor = -1; // no goal is set
        dir = 0;
        upReqExists = false;
        downReqExists = false;

        if (init_max_floors > 0) {
            max_floors = init_max_floors;
            upRequests = new boolean[init_max_floors];
            downRequests = new boolean[init_max_floors];
            Arrays.fill(upRequests, false);
            Arrays.fill(downRequests, false);
        } else {
            System.out.println("Max floors is non-positive!");
            /* Needs to error here */
        }
    }

    public int getID () {
        return id;
    }

    public int getCurrentFloor () {
        System.out.println("Elevator " + id + " is on floor " + curr_floor);
        return curr_floor;
    }

    public int getGoalFloor () {
        return goal_floor;
    }
    
    public int getDirection () {
        System.out.println("Elevator " + id + " has direction: " + dir);
        return dir;
    }

    public void getState() {
        System.out.println("Elevator " + id + " is on floor " + curr_floor + " has direction " + dir);
    }

    private void setDirection (int set_dir) {
        if (set_dir < -1 || set_dir > 1) {
            System.out.println("Invalid direction!");
            return;
        }
        if (dir == 0) {
            dir = set_dir;
        } else {
            System.out.println("Elevator is already in motion");
        }

        return;
    }

    /* Modify boolean array */
    public void enqueueFloorRequest (int desired_floor) {
        desired_floor--; //Offset by 1, because ground floor is at 0, not 1.
        if (dir == 0) {
            if (desired_floor - curr_floor > 0) {
                dir = 1;
            } else {
                dir = -1;
            }
        }
        switch (desired_floor) {
        case 1: 
            upRequests[desired_floor] = true;
            break;
        case -1:
            downRequests[desired_floor] = true;
            break;
        default:
            break;
        }
    }

    private int setGoalFloorHelper (boolean[] requests) {
        int idx;
        for (idx = curr_floor + 1; idx < max_floors; idx++) {
            if (requests[idx] == true) {
                return idx;
            }
        }
        return -1;
    }

    private void setGoalFloor (int dir) {
        switch (dir) {
        case 1:
            goal_floor = setGoalFloorHelper(upRequests);
            break;
        case -1:
            goal_floor = setGoalFloorHelper(downRequests);
            break;
        default:
            /* No floors to go to in direction it's currently in. Stay put. */
            goal_floor = -1;
            break;
        }
    }

    private void advanceToNextFloor (int dir) {
        /* What if the current floor is at the topmost or bottommost floor? */
        switch (dir) {
        case 1:
            if (curr_floor == max_floors - 1) { //Topmost floor
                dir = (downReqExists) ? -1 : 0;
                setGoalFloor(dir);
            } else {
                curr_floor++;
            }
            break;
        case -1:
            if (curr_floor == 0) { //Ground floor
                dir = (upReqExists) ? 1 : 0;
                setGoalFloor(dir);
            } else {
                curr_floor--;
            }
            break;
        default:
            break;
        }
    }

    /* Determines whether or not elevator is at desired floor. If so, mark
     * request as false (i.e. finished) and set next request. */
    private void checkRequest (int dir, boolean[] requests) {
        if (curr_floor == goal_floor) {
            requests[curr_floor] = false;
            setGoalFloor(dir);
        } 
    }

    /* Questions:
     *   1. How do I know if I'm done with all my requests? I should not always be 
     *      checking to see if my request array is empty. Keep a counter? But what
     *      if a person enqueues the same request?
     */

    /* Advancing one time unit. Finish servicing requests in a specific
     * direction. Once requests are fully serviced, see if there exists 
     * requests for the other direction.
     *
     * 1. Advance to next floor.
     *
     * 2. Check if curr_floor == desired floor.
     *  - if yes, process next request (look at next requests)
     *  - if no, keep desired floor.
     */
    public void advance() {
        advanceToNextFloor(dir);

        if (getGoalFloor() == -1) {
            setGoalFloor(dir);
            return;
        }

        switch (dir) {
        case 1:
            checkRequest(dir, upRequests);
            break;
        case -1:
            checkRequest(dir, downRequests);
            break;
        default:
            break;
        }

        return;
    }
}
