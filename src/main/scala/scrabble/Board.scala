//package scrabble
//
//
//class Board(cells: Array[Array[ScorableCell]]) {
//  def scoreFor(word: String, row: Int, col: Int, fillHorizontal: Boolean) = {
//    if(fillHorizontal){
//      val wordSum = (col to col+word.length).map(i => cells(row)(i).scoreLetter(word(i-col))).sum
//      val wordFactor = (col to col+word.length).map(cells(row)(i).wordFactor()).max
//      wordSum * wordFactor
//    }else{
//      val wordSum = (row to row+word.length).map(i => cells(i)(col).scoreLetter(word(i-row))).sum
//      val wordFactor = (col to col+word.length).map(cells(row)(i).wordFactor()).max
//      wordSum * wordFactor
//    }
//  }
//}
//
//object Board {
//  def initializeBoard(): Board = {
//    // Fill first quadrant and apply mirroring
//  }
//}
//
//trait ScorableCell extends LetterScore {
//  def scoreLetter(l: Char): Int = scoreFor(l)
//  def wordFactor(): Int = 1
//}
//
//trait LetterScore {
//  // Fill the scoring table here
//  def scoreFor(l: Char) = l.toInt
//}
//
//class DoubleLetterCell extends ScorableCell {
//  def scoreLetter(l: Char): Int = 2 * super.scoreLetter(l)
//}
//
//class TripleLetterCell extends ScorableCell {
//  def scoreLetter(l: Char): Int = 3 * super.scoreLetter(l)
//}
//
//class DoubleWordCell extends ScorableCell {
//  def wordFactor(): Int = 2
//}
//
//class TripleWordCell extends ScorableCell {
//  def wordFactor(): Int = 3
//}