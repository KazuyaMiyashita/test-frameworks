package leaderboard

import org.scalatest._
import org.scalamock.scalatest.MockFactory

class LeaderBoardSpec extends FunSuite with Matchers with MockFactory {

  test("PlayerDatabaseのスタブを作って叩く") {
    val fakeDb = stub[PlayerDatabase]
    
    (fakeDb.getPlayerById _) when(222) returns(Player(222, "boris", Country.Russia))
    (fakeDb.getPlayerById _) when(333) returns(Player(333, "hans", Country.Germany))

    fakeDb.getPlayerById(222).nickname shouldEqual "boris"
  }

  test("MatchResultObserver.recordMatchResultにおいてcountryLeaderBoard.addVictoryForCountryに勝者の国が渡される") {
    val winner = Player(id = 222, nickname = "boris", country = Country.Russia)
    val loser = Player(id = 333, nickname = "hans", country = Country.Germany)

    val countryLeaderBoardMock = mock[CountryLeaderBoard]
    val playerDatabaseStub = stub[PlayerDatabase]
    
    (playerDatabaseStub.getPlayerById _).when(winner.id).returns(winner)
    (playerDatabaseStub.getPlayerById _).when(loser.id).returns(loser)
      
    (countryLeaderBoardMock.addVictoryForCountry _).expects(Country.Russia)

    val matchResultObserver = new MatchResultObserver(
      playerDatabaseStub, countryLeaderBoardMock)
    matchResultObserver.recordMatchResult(MatchResult(winner = winner.id, loser = loser.id))
  }

}
