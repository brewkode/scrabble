package remotecontrol

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers


class RemoteControlTest extends FlatSpec with ShouldMatchers {
  "RemoteControl" should "create new instance on provided input" in {

  }

  it should "move up b/w two channels and count the number of steps respecting blocked channels" in {
    val remoteControl = new RemoteControl(Array(1,12,4,8,20), Set(9,10,11,19), 1, 20)
    val up1: MinClickWithPrevious = remoteControl.moveUp(4, 8)
    up1.minClick should be(4)
    up1.previous should be(7)

    val up2: MinClickWithPrevious = remoteControl.moveUp(4, 20)
    up2.minClick should be(12)
    up2.previous should be(18)

  }

  it should "move up b/w two channels, wrap around if needed and count the number of steps respecting blocked channels" in {
    val remoteControl = new RemoteControl(Array(1,12,4,8,20), Set(9,10,11,19), 1, 20)
    val up1: MinClickWithPrevious = remoteControl.moveUp(20, 12)
    up1.minClick should be(9)
    up1.previous should be(8)
  }

  it should "move down b/w two channels and count the number of steps respecting blocked channels" in {
    val remoteControl = new RemoteControl(Array(1,12,4,8,20), Set(9,10,11,19), 1, 20)
    val up1: MinClickWithPrevious = remoteControl.moveDown(8, 4)
    up1.minClick should be(4)
    up1.previous should be(5)
  }

  it should "move down b/w two channels, wrap around if needed and count the number of steps respecting blocked channels" in {
    val remoteControl = new RemoteControl(Array(1,12,4,8,20), Set(9,10,11,19), 1, 20)
    val up1: MinClickWithPrevious = remoteControl.moveDown(12, 20)
    up1.minClick should be(9)
    up1.previous should be(1)
  }

  it should "return number of digits in the channel if key press was the move" in {
    val remoteControl = new RemoteControl(Array(1,12,4,8,20), Set(9,10,11,19), 1, 20)
    remoteControl.keyPress(100) should be(3)
    remoteControl.keyPress(1) should be(1)
    remoteControl.keyPress(12) should be(2)
    remoteControl.keyPress(11212) should be(5)
    remoteControl.keyPress(1001) should be(4)
  }
}
