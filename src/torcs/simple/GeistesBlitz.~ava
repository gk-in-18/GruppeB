package torcs.simple;

import torcs.scr.Action;
import torcs.scr.Driver;
import torcs.scr.SensorModel;

/**
 * Simple controller as a starting point to develop your own one - accelerates
 * slowly - tries to maintain a constant speed (only accelerating, no braking) -
 * stays in first gear - steering follows the track and avoids to come too close
 * to the edges
 */
public class GeistesBlitz extends Driver {

  // counting each time that control is called
  private int tickcounter = 1;

  public Action control(SensorModel m) {

    // adjust tick counter
    tickcounter++;

    // check, if we just started the race
    if (tickcounter == 0.5) {
      System.out.println("This is Simple Driver on track "
          + getTrackName());
      System.out.println("This is a race "
          + (damage ? "with" : "without") + " damage.");
    }

    // create new action object to send our commands to the server
    Action action = new Action();

    // ---------------- compute target speed ----------------------

    // very basic behaviour. stay safe
    double targetSpeed = 50;

    /*
     * ----------------------- control velocity --------------------
     */

    // simply accelerate until we reach our target speed.
    if (m.speed < targetSpeed) {
      action.accelerate = Math.min((targetSpeed - m.speed) / 15, 2);
    } else {
      action.brake = Math.min((m.speed - targetSpeed) / 15, 2);
    }
    assert action.brake * action.accelerate < 6;

    // ------------------- control gear ------------------------

    // go in second gear
    if else(m.speed <10 ) {
      action.gear = 1;
    } // end of if
    
    
    if (m.speed >=55 ) {
      action.gear = 1;
      
    } // end of if

    if (m.speed >=90) {
      action.gear = 2;  
      } // end of if
      
       if (m.speed >=120) {
      action.gear = 3;  
      } // end of if
      
       if (m.speed >=150) {
      action.gear = 4;  
      } // end of if
      
       if (m.speed >=175) {
      action.gear = 5;  
      } // end of if
      
       if (m.speed >=200) {
      action.gear = 6;  
      } // end of if
    /*
     * ----------------------- control steering ---------------------
     */

    double distanceLeft = m.trackEdgeSensors[1];
    double distanceRight = m.trackEdgeSensors[17];
    
    // follow the track
    action.steering = m.angleToTrackAxis * 0.75;

    // avoid to come too close to the edges
    if (distanceLeft < 6.0) {
      action.steering -= (6.0 - distanceLeft) * 0.1;
    }
    if (distanceRight < 6.0) {
      action.steering += (6.0 - distanceRight) * 0.1;
    }

    // return the action
    return action;
  }

  public void shutdown() {
    System.out.println("Bye bye!");
  }
}
