package hr.fer.zemris.java.p12.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Concrete implementation of DAO interface.
 *  
 * @author Luka Grgić
 */
public class SQLDAO implements DAO {

	@Override
	public boolean vote(int pollID, int optionID) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		if (!pollOptionExists(con, pollID, optionID)) return false;
		
		String statement = "UPDATE PollOptions " +
							"SET votesCount=votesCount+1 " +
							"WHERE pollID=" + pollID + " AND id=" + optionID;		
		
		try (PreparedStatement pst = con.prepareStatement(statement)) {
			pst.executeUpdate();
		} catch(Exception e) {
			throw new DAOException("Error while trying to update vote count.", e);
		}	
		
		return true;
	}
	
	
	/**
	 * Checks if entry with pollid and optionID exists
	 * in PollOptions table
	 * 
	 * @param con connection
	 * @param pollID pollid
	 * @param optionID optionid
	 * @return
	 */
	private boolean pollOptionExists(Connection con, int pollID, int optionID) {
		String statement = "SELECT COUNT(*) AS total " +
							"FROM PollOptions " +
							"WHERE id=" + optionID + "AND pollID=" + pollID;

		try (PreparedStatement count = con.prepareStatement(statement);
				ResultSet rs = count.executeQuery()){
			
			rs.next();
			return rs.getInt("total") == 1;
			
		} catch (Exception e) {
			throw new DAOException("Error while trying to update vote count.", e);
		}
	}


	@Override
	public List<PollOption> getTopVoted(int pollID) throws DAOException {
		List<PollOption> options = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		String statement = "SELECT id, optionTitle, optionLink, pollID, votesCount " +
								"FROM PollOptions WHERE pollID=" + pollID +
												   "AND votesCount=" +
										"(SELECT MAX(votesCount) FROM PollOptions " +
												   				"WHERE pollID=" + pollID + ")" +
								"ORDER BY votesCount DESC";
		
		try (PreparedStatement pst = con.prepareStatement(statement);
				ResultSet rs = pst.executeQuery()) {
			
				while(rs!=null && rs.next()) {
					PollOption option = new PollOption();
					option.setId(rs.getLong(1));
					option.setOptionTitle(rs.getString(2));
					option.setOptionLink(rs.getString(3));
					option.setPollID(rs.getLong(4));
					option.setVotesCount(rs.getLong(5));
					options.add(option);
				}
			
		} catch(Exception e) {
			throw new DAOException("Error while trying to get top voted PollOptions.", e);
		}
		return options;
	}

	@Override
	public List<PollOption> getPollOptions(int pollID) throws DAOException {
		List<PollOption> options = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		String statement = "SELECT id, optionTitle, optionLink, pollID, votesCount " +
							"FROM PollOptions " +
							"WHERE pollID=" + pollID + 
							"ORDER BY votesCount DESC";
		
		try (PreparedStatement pst = con.prepareStatement(statement);
				ResultSet rs = pst.executeQuery()) {
			
				while(rs!=null && rs.next()) {
					PollOption option = new PollOption();
					option.setId(rs.getLong(1));
					option.setOptionTitle(rs.getString(2));
					option.setOptionLink(rs.getString(3));
					option.setPollID(rs.getLong(4));
					option.setVotesCount(rs.getLong(5));
					options.add(option);
				}
			
		} catch(Exception e) {
			throw new DAOException("Error while trying to get PollOptions.", e);
		}
		return options;
	}

	@Override
	public List<Poll> getPolls() throws DAOException {
		List<Poll> polls = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		String statement = "SELECT id, title, message FROM Polls ORDER BY id";
		
		try (PreparedStatement pst = con.prepareStatement(statement);
				ResultSet rs = pst.executeQuery()) {
			
				while(rs!=null && rs.next()) {
					Poll poll = new Poll();
					poll.setId(rs.getLong(1));
					poll.setTitle(rs.getString(2));
					poll.setMessage(rs.getString(3));
					polls.add(poll);
				}
			
		} catch(Exception e) {
			throw new DAOException("Error while trying to get Polls.", e);
		}
		return polls;
	}

	@Override
	public Poll getPoll(long id) throws DAOException {
		Poll poll = null;
		Connection con = SQLConnectionProvider.getConnection();
		String statement = "SELECT id, title, message FROM Polls WHERE id=" + id;
		
		try (PreparedStatement pst = con.prepareStatement(statement);
				ResultSet rs = pst.executeQuery()) {
			
				if(rs!=null && rs.next()) {
					poll = new Poll();
					poll.setId(rs.getLong(1));
					poll.setTitle(rs.getString(2));
					poll.setMessage(rs.getString(3));
				}
			
		} catch(Exception e) {
			throw new DAOException("Error while trying to get Poll.", e);
		}
		
		return poll;
	}	

}