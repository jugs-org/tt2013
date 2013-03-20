package _07_int_range.constraints;

import _07_int_range.predicates.*;

/**
 * Constraint class of Integer.
 * <pre>(IntLesser10 and IntGreaterZero).</pre>
 * Generated by {@link _07_int_range.IntRangeConstraintCodeGenerator}.
 * Line-Number 79.
 */
public final class ANDB_IntLesser10_IntGreaterZero_ANDE
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
	public ANDB_IntLesser10_IntGreaterZero_ANDE(
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
		return ( new IntLesser( 10 ).test( value ) &&
					new IntGreater( 0 ).test( value ) );
	}

	/**
	 * Debug output.
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "(IntLesser10 and IntGreaterZero): " + this.value;
	}

}