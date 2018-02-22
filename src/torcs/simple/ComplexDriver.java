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
public class ComplexDriver extends Driver {
  
  // counting each time that control is called
  private int tickcounter = 0;
  
  public Action control(SensorModel m) {
    
    // adjust tick counter
    tickcounter++;
    
    // check, if we just started the race
    if (tickcounter == 1) {
      System.out.println("This is ComplexDriver on track "
      + getTrackName());
      System.out.println("This is a race "
      + (damage ? "with" : "without") + " damage.");
    }
    
    // create new action object to send our commands to the server
    Action action = new Action();
    
    /*
    * ----------------------- control velocity --------------------
    */
    
    // simply accelerate until we reach our target speed.
    if (m.speed < targetSpeed) {
      action.accelerate = Math.min((targetSpeed - m.speed) / 10, 1);
    } else {
      action.brake = Math.min((m.speed - targetSpeed) / 10, 1);
    }
    assert action.brake * action.accelerate < 0.1;
    
    // ------------------- control gear ------------------------
    
    if(m.speed <= 20){
      action.gear = 1;
    }
    else if (m.speed <= 50){
      action.gear = 2;
    }
    else if (m.speed <= 90){
      action.gear = 3;
    }
    else if (m.speed <= 140){
      action.gear = 4;
    }
    else if (m.speed <= 160){
      action.gear = 5;
    }
    else if (m.speed >= 160){
      action.gear = 6;
    }
    
    double[] trackedgeSensors = sensorModel.getTrackEdgeSensors();
    double links = trackedgeSensors[0];
    double rechts = trackedgeSensors[18];
    double mitte = trackedgeSensors[9];
    float targetAngle;
    brakes=0;
    if (mitte > 150) targetSpeed =500;
    //System.out.println(curve);
    if (mitte>50){
      //wenn keine kurve dann lenke und versuche in die mitte der fahrbahn zu kommen
      targetAngle = (float) (sensorModel.getAngleToTrackAxis() - sensorModel.getTrackPosition() * 0.5);
      action.steering = (targetAngle) / steerLock;
    }
    else{ //scheiﬂ auf die Streckenmitte
      targetAngle = (float) (sensorModel.getAngleToTrackAxis()*0.5);
      action.steering = (targetAngle) / steerLock;
    }
    
    
    return action;
  }

  public void shutdown() {
    System.out.println("Bye bye!");
  }
}
