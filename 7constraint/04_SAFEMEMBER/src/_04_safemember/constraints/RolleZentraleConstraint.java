package _04_safemember.constraints;

import _04_safemember.enums.*;
import _04_safemember.predicates.*;

/**
 * Constraint class of Rolle.
 * <pre>RolleZentrale.</pre>
 * Generated by {@link _04_safemember.SafeMememberConstraintCodeGenerator}.
 * Line-Number 92.
 */
public final class RolleZentraleConstraint
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
	public RolleZentraleConstraint(
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
		return new RolleZentrale().test( rolle );
	}

	/**
	 * Convert this constraint type to a compatible type of this type.
	 * <pre>RolleEbeneZentrale.</pre>
	 * 
	 * @return compatible type of this type
	 */
	public RolleEbeneZentraleConstraint convertToRolleEbeneZentraleConstraint()
	{
		return new RolleEbeneZentraleConstraint(
				this.rolle );
	}

	/**
	 * Debug output.
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "RolleZentrale: " + this.rolle;
	}

}