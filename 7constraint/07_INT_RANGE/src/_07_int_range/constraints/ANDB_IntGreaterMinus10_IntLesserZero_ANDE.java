package _07_int_range.constraints;

import _07_int_range.predicates.*;

/**
 * Constraint class of Integer.
 * <pre>(IntGreaterMinus10 and IntLesserZero).</pre>
 * Generated by {@link _07_int_range.IntRangeConstraintCodeGenerator}.
 * Line-Number 79.
 */
public final class ANDB_IntGreaterMinus10_IntLesserZero_ANDE
{
	/**
	 * Context object of type Integer.
	 */
	public final Integer value;

	/**
	 * Constructor.
	 * 
	 * @param value current context object
	 * @throws IllegalArgumentException if constraint violated
	 */
	public ANDB_IntGreaterMinus10_IntLesserZero_ANDE(
			final Integer value )
	{
		if ( ! test( value ) )
		{
			throw new IllegalArgumentException( "constraint violation" );
		}
		this.value = value;
	}

	/**
	 * Test method.
	 * 
	 * @param value current context object
	 * @return constraint expression satisfied or not
	 */
	public static boolean test(
			final Integer value )
	{
		return ( new IntGreater( -10 ).test( value ) &&
					new IntLesser( 0 ).test( value ) );
	}

	/**
	 * Debug output.
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "(IntGreaterMinus10 and IntLesserZero): " + this.value;
	}

}