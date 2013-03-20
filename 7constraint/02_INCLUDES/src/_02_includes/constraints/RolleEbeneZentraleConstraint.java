package _02_includes.constraints;

import _02_includes.enums.*;
import _02_includes.predicates.*;

/**
 * Constraint class of Rolle.
 * <pre>RolleEbeneZentrale.</pre>
 * Generated by {@link _02_includes.IncludesConstraintCodeGenerator}.
 * Line-Number 71.
 */
public final class RolleEbeneZentraleConstraint
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
	public RolleEbeneZentraleConstraint(
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
		return new RolleEbeneZentrale().test( rolle );
	}

	/**
	 * Debug output.
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "RolleEbeneZentrale: " + this.rolle;
	}

}