package hr.fer.zemris.java.p12.dao;

import java.util.List;

import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Interface for accessing data from the database.
 * 
 * @author Luka Grgić
 */
public interface DAO {

	/**
	 * Gets the tuples from PollOptions table that have the
	 * highest votesCount and have pollID as pollID provided
	 * as parameter in method.
	 * 
	 * @param pollID pollID
	 * @return PollOptions list with tuples that have highest votesCount
	 * @throws DAOException if error with database
	 */
	public List<PollOption> getTopVoted(int pollID) throws DAOException;
	
	/**
	 * Increases votesCount field from PollOptions table tuple that
	 * has pollID and ID equivalent to pollID and optionID parameters
	 * provided. 
	 * 
	 * @param pollID pollID
	 * @param optionID ID
	 * @return true if successful else false
	 * @throws DAOException if error with database
	 */
	public boolean vote(int pollID, int optionID) throws DAOException;
	
	/**
	 * Gets the tuples from PollOptions table that have pollID
	 * as pollID provided in method.
	 * 
	 * @param pollID pollID
	 * @return PollOptions list with provided pollID
	 * @throws DAOException if error with database
	 */
	public List<PollOption> getPollOptions(int pollID) throws DAOException;
	
	/**
	 * Gets all tuples from Polls table.
	 * 
	 * @return all tuples from Polls table.
	 * @throws DAOException if error with database
	 */
	public List<Poll> getPolls() throws DAOException;

	/**
	 * Gets tuple from Polls table with id as id
	 * provided in method.
	 * 
	 * @param id id
	 * @return tuple with id as id
	 * @throws DAOException if error with database
	 */
	public Poll getPoll(long id) throws DAOException;
	
}