package remotecontrol

case class MinClickWithPrevious(previous: Int, minClick: Int) {
  def addOne() = MinClickWithPrevious(this.previous, this.minClick+1)
}

class RemoteControl(inputChannels: Array[Int], blockedChannels: Set[Int], low: Int, high: Int) {
  def traverse(): Int = {
    val gotoStart = keyPress(inputChannels.head)
    val prev = inputChannels.head
    // TODO: use gotoStart
    val minClickSoFar = MinClickWithPrevious(prev, gotoStart)
    val pairs: Array[(Int, Int)] = inputChannels.zip(inputChannels.drop(1))
    val overallMinClick: MinClickWithPrevious = pairs.foldLeft(minClickSoFar)((sofar, cur) => {
      val (left: Int, right: Int) = cur
      val minClickThisStep = minimumClicks(left, right, sofar.previous)
      MinClickWithPrevious(minClickThisStep.previous, minClickThisStep.minClick + sofar.minClick)
    })
    overallMinClick.minClick
  }

  def minimumClicks(src: Int, dest: Int, previous: Int): MinClickWithPrevious = {
    val noop = if (src == dest) MinClickWithPrevious(previous, 0) else MinClickWithPrevious(previous, Int.MaxValue)
    val keyPressRes = MinClickWithPrevious(src, keyPress(dest))
    val backRes = if (dest == previous) MinClickWithPrevious(src, 1) else MinClickWithPrevious(src, Int.MaxValue)
    val moveUpRes = moveUp(src, dest)
    val moveDownRes = moveDown(src, dest)
    val backMoveUp = moveUp(previous, dest).addOne()
    val backMoveDown = moveDown(previous, dest).addOne()
    val min: MinClickWithPrevious = List(noop, keyPressRes, backRes, moveUpRes, moveDownRes,backMoveUp,backMoveDown).minBy(_.minClick)
    println("%d %d => %d %d".format(src, dest, min.minClick, min.previous))
    min
  }

  def keyPress(dest: Int) : Int = {
    if (dest == 0) return 0
    1+keyPress(dest/10)
  }

  def moveUp(src: Int, dest: Int) = {
    val path = channelsBetween(src, dest).filterNot(blockedChannels contains)
    MinClickWithPrevious(path(path.length-2), path.length-1)
  }

  def moveDown(src: Int, dest: Int) = {
    val path = channelsBetween(dest, src).reverse.filterNot(blockedChannels contains)
    MinClickWithPrevious(path(path.length-2), path.length-1)
  }

  def channelsBetween(left: Int, right: Int): List[Int] = {
    if (left < right)
      (left to right).toList
    else
      ((left to high) ++ (low to right)).toList
  }
}

object RemoteControl {
  def apply(startEnd: String, toView: String, blocked: String) = {
    val channelsToView = toView.split(" ").map(_.toInt)
    val blockedChannels = blocked.split(" ").map(_.toInt).toSet
    val startEndChannels = startEnd.split(" ").map(_.toInt)
    new RemoteControl(channelsToView, blockedChannels, startEndChannels(0), startEndChannels(1))
  }

  def main(args:Array[String]) {
    println(RemoteControl("1 20", "15 14 17 1 17", "18 19").traverse())
    println(RemoteControl("103 108", "105 106 107 103", "104").traverse())
    println(RemoteControl("1 100", "10 13 13 100 99 98 77 81", "78 79 80 3").traverse())
  }
}