package _01_simple.constraints;

import _01_simple.enums.*;
import _01_simple.predicates.*;

/**
 * Constraint class of Rolle.
 * <pre>RolleFiliale.</pre>
 * Generated by {@link _01_simple.SimpleConstraintCodeGenerator}.
 * Line-Number 73.
 */
public final class RolleFilialeConstraint
{
	/**
	 * Context object of type Rolle.
	 */
	public final Rolle rolle;

	/**
	 * Constructor.
	 * 
	 * @param rolle current context object
	 * @throws IllegalArgumentException if constraint violated
	 */
	public RolleFilialeConstraint(
			final Rolle rolle )
	{
		if ( ! test( rolle ) )
		{
			throw new IllegalArgumentException( "constraint violation" );
		}
		this.rolle = rolle;
	}

	/**
	 * Test method.
	 * 
	 * @param rolle current context object
	 * @return constraint expression satisfied or not
	 */
	public static boolean test(
			final Rolle rolle )
	{
		return new RolleFiliale().test( rolle );
	}

	/**
	 * Debug output.
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "RolleFiliale: " + this.rolle;
	}

}