package hr.fer.zemris.java.hw04.db;

import java.util.List;

/**
 * Class that is responsible for filtering student records
 * based on queries.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class QueryFilter implements IFilter {

	/** List of queries */
	private List<ConditionalExpression> queries;
	
	/**
	 * Constructor.
	 * 
	 * @param queries List of queries.
	 */
	public QueryFilter(List<ConditionalExpression> queries) {
		this.queries = queries;
	}
	
	/**
	 * Filters student based on queries.
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression expression : queries) {

			if (!expression.getComparisonOperator().satisfied(
					expression.getFieldValue().get(record), expression.getStringLiteral())) {
				return false;
			}
			
		}
		
		return true;
	}
	
}
