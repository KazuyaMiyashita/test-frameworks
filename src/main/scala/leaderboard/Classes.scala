
package leaderboard

trait Country
object Country {
  case object Russia extends Country
  case object Germany extends Country
}

case class Player(id: PlayerId, nickname: String, country: Country)
case class MatchResult(winner: PlayerId, loser: PlayerId)

case class CountryLeaderBoardEntry(country: Country, points: Int)

trait CountryLeaderBoard {
  def addVictoryForCountry(country: Country): Unit
  def getTopCountries(): List[CountryLeaderBoardEntry]
}

class MatchResultObserver(
    playerDatabase: PlayerDatabase,
    countryLeaderBoard: CountryLeaderBoard
) {

  def recordMatchResult(result: MatchResult): Unit = {
    val player = playerDatabase.getPlayerById(result.winner)
    countryLeaderBoard.addVictoryForCountry(player.country)
  }

}

trait PlayerDatabase {
  def getPlayerById(playerId: PlayerId): Player
}
