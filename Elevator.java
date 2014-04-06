import java.util.Arrays;

public class Elevator {
    int id;
    int dir; //-1 = down, +1 = up, 0 = stationary
    private int curr_floor;
    private int goal_floor; // -1 = no goal set
    private int max_floors;
    private boolean downReqExists;
    private boolean upReqExists;

    /* Createa a private list that keeps an up to date list of requests
     * for the elevator. A request is queued within the upRequests or downRequests
     * depending on where the elevator currently is.*/
    private boolean upRequests[];
    private boolean downRequests[];

    public Elevator (int init_id, int init_max_floors) { //constructor 
        id            = init_id;
        curr_floor    = 0;
        goal_floor    = -1;
        dir           = 0;
        upReqExists   = false;
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
        System.out.println("Elevator " + id + " is on floor " + 
                           curr_floor + " has direction " + dir);
    }

    private void setDirection () {
        System.out.println("Checking direction");
        if (upReqExists) {
            dir = 1;
            System.out.println("Direction set to up");
            return;
        } 
        if (downReqExists) {
            dir = -1;
            System.out.println("Direction set to down");
            return;
        }

        return;
    }

    /* Modify boolean array */
    public void enqueueFloorRequest (int desired_floor) {
        boolean isAboveCurrFloor = (desired_floor - curr_floor > 0) ? true : false;
        if (isAboveCurrFloor) {
            upRequests[desired_floor] = true;
            if (upReqExists == false) {
                upReqExists = true;
            }
        } else {
            downRequests[desired_floor] = true;
            if (downReqExists == false) {
                System.out.println("downreqExists set");
                downReqExists = true;
            }
        }
    }

    private void setGoalFloor () {
        int idx;
        switch (dir) {
        case 1:
            for (idx = curr_floor + 1; idx < max_floors; idx += 1) {
                if (upRequests[idx] == true) {
                    goal_floor = idx;
                    return;
                }
            }
            upReqExists = false;
            goal_floor = -1;
            dir = 0;
            break;
        case -1:
            for (idx = curr_floor - 1; idx >= 0; idx -= 1) {
                if (downRequests[idx] == true) {
                    goal_floor = idx;
                    return;
                }
            }
            downReqExists = false;
            goal_floor = -1;
            dir = 0;
            break;
        default:
            /* No floors to go to in direction it's currently in. Stay put. */
            goal_floor = -1;
            break;
        }
        return;
    }

    private void advanceToNextFloor () {
        /* What if the current floor is at the topmost or bottommost floor? */
        switch (dir) {
        case 1:
            if (curr_floor == max_floors - 1) { //Topmost floor
                upReqExists = false;
                dir = (downReqExists) ? -1 : 0;
                setGoalFloor();
            } else {
                curr_floor++;
            }
            break;
        case -1:
            if (curr_floor == 0) { //Ground floor
                downReqExists = false;
                dir = (upReqExists) ? 1 : 0;
                setGoalFloor();
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
    private void checkRequest (boolean[] requests) {
        if (curr_floor == goal_floor) {
            requests[curr_floor] = false;
            setGoalFloor();
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
        advanceToNextFloor();

        if (getGoalFloor() == -1) {
            setDirection();
            setGoalFloor();
            return;
        }

        switch (dir) {
        case 1:
            checkRequest(upRequests);
            break;
        case -1:
            checkRequest(downRequests);
            break;
        default:
            break;
        }

        return;
    }
}
