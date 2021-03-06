/*
 * The MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package diablo2015;

import diablo2015.Tickable;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

/**
 * Controls the elevator that lifts the totes
 *
 * @author Erich
 */
public class Lifter implements Tickable {

    /**
     * Speed that the elevator will be moving at
     */
    private double leftSpeed = 0;
    private double rightSpeed = 0;
    /**
     * Speed controller for left motor of elevator.
     */
    private final Victor left;
    /**
     * Speed controller for right motor of elevator.
     */
    private final Victor right;
    /**
     * Limit Switch for bottom left of elevator.
     */
    private final DigitalInput leftMin;
    /**
     * Limit Switch for top left of elevator.
     */
    private final DigitalInput leftMax;
    /**
     * Limit Switch for bottom right of elevator.
     */
    private final DigitalInput rightMin;
    /**
     * Limit Switch for top right of elevator.
     */
    private final DigitalInput rightMax;
    /**
     * Says if elevator is fully lowered
     */
    private boolean loweredLeft;
    /**
     * Says if elevator is fully raised
     */
    private boolean raisedLeft;
    private boolean loweredRight;
    private boolean raisedRight;

    /**
     *
     * @param left The speed controller for the left elevator motor
     * @param right The speed controller for the right elevator motor
     * @param leftMin The limit switch for the bottom left of the elevator
     * @param leftMax The limit switch for the top left of the elevator
     * @param rightMin The limit switch for the bottom right of the elevator
     * @param rightMax The limit switch for the top right of the elevator
     */
    public Lifter(Victor left, Victor right, DigitalInput leftMin, DigitalInput leftMax, DigitalInput rightMin, DigitalInput rightMax) {
        this.left = left;
        this.right = right;
        this.leftMin = leftMin;
        this.leftMax = leftMax;
        this.rightMin = rightMin;
        this.rightMax = rightMax;
    }

    /**
     * Lowers the elevator if it is not already at bottom.
     */
    public void down() {
        leftSpeed = 1;
        rightSpeed = 1;
    }

    /**
     * Raises the elevator if it is not already at the top.
     */
    public void up() {

        leftSpeed = -1;
        rightSpeed = -1;
    }

    public void stop() {
        leftSpeed = 0;
        rightSpeed = 0;
    }

    /**
     * Checks if elevator is fully raised or lowered. If it is, stop the motors.
     */
    public void tick() {
        loweredLeft = !leftMin.get();
        loweredRight = !rightMin.get();
        raisedLeft = !leftMax.get();
        raisedRight = !rightMax.get();

        if (loweredLeft && leftSpeed < 0) {
            leftSpeed = 0;
        }
        if (loweredRight && rightSpeed < 0) {
            rightSpeed = 0;
        }
        if (raisedLeft && leftSpeed > 0) {
            leftSpeed = 0;
        }
        if (raisedRight && rightSpeed > 0) {
            rightSpeed = 0;
        }
        left.set(leftSpeed);
        right.set(rightSpeed);
    }

    public String toString() {
        if (!leftMax.get() && !rightMax.get()) {
            return "FULLY LOWERED";
        } else if (!leftMin.get() && !rightMin.get()) {
            return "FULLY RAISED";
        } else if (leftSpeed > 0) {
            return "DOWN";
        } else if (leftSpeed < 0) {
            return "UP";
        } else {
            return "STOPPED";
        }
    }
}
