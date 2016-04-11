package net.nba.model;

import java.io.Serializable;

/**
 * @author sunfan314
 *	PlayerMatchStatistics复合主键类
 */
public class PlayerMatchStatisticsPK implements Serializable {

	private int matchId;

	private int playerId;

	public PlayerMatchStatisticsPK() {

	}

	public PlayerMatchStatisticsPK(int matchId, int playerId) {
		this.matchId = matchId;
		this.playerId = playerId;
	}

	public int getMatchId() {
		return matchId;
	}

	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + matchId;
		result = prime * result + playerId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerMatchStatisticsPK other = (PlayerMatchStatisticsPK) obj;
		if (matchId != other.matchId)
			return false;
		if (playerId != other.playerId)
			return false;
		return true;
	}

}
