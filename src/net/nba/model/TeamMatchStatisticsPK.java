package net.nba.model;

import java.io.Serializable;

/**
 * @author sunfan314
 *	TeamMatchStatistics主键类
 */
public class TeamMatchStatisticsPK implements Serializable{

	private int matchId;

	private int teamId;

	public TeamMatchStatisticsPK() {
		super();
	}

	public TeamMatchStatisticsPK(int matchId, int teamId) {
		super();
		this.matchId = matchId;
		this.teamId = teamId;
	}

	public int getMatchId() {
		return matchId;
	}

	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + matchId;
		result = prime * result + teamId;
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
		TeamMatchStatisticsPK other = (TeamMatchStatisticsPK) obj;
		if (matchId != other.matchId)
			return false;
		if (teamId != other.teamId)
			return false;
		return true;
	}

}
