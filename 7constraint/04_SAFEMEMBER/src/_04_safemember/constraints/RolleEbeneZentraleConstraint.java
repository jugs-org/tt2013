package _04_safemember.constraints;

import _04_safemember.enums.*;
import _04_safemember.predicates.*;

/**
 * Constraint class of Rolle.
 * <pre>RolleEbeneZentrale.</pre>
 * Generated by {@link _04_safemember.SafeMememberConstraintCodeGenerator}.
 * Line-Number 95.
 */
public final class RolleEbeneZentraleConstraint
{
	/**
	 * Context object of type Rolle.
	 */
	public final Rolle rolle;

	/**
	 * Constraintsafe member _04_safemember.enums.Ebene.Zentrale ebeneZentrale.
	 */
	public final _04_safemember.enums.Ebene.Zentrale ebeneZentrale;

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
		this.ebeneZentrale = (_04_safemember.enums.Ebene.Zentrale) rolle.ebene;
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