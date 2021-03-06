package _04_safemember.constraints;

import _04_safemember.enums.*;
import _04_safemember.predicates.*;

/**
 * Constraint class of Rolle.
 * <pre>RolleNiederlassung.</pre>
 * Generated by {@link _04_safemember.SafeMememberConstraintCodeGenerator}.
 * Line-Number 98.
 */
public final class RolleNiederlassungConstraint
{
	/**
	 * Context object of type Rolle.
	 */
	public final Rolle rolle;

	/**
	 * Constraintsafe member _04_safemember.enums.Ebene.Niederlassung ebeneNiederlassung.
	 */
	public final _04_safemember.enums.Ebene.Niederlassung ebeneNiederlassung;

	/**
	 * Constructor.
	 * 
	 * @param rolle current context object
	 * @throws IllegalArgumentException if constraint violated
	 */
	public RolleNiederlassungConstraint(
			final Rolle rolle )
	{
		if ( ! test( rolle ) )
		{
			throw new IllegalArgumentException( "constraint violation" );
		}
		this.rolle = rolle;
		this.ebeneNiederlassung = (_04_safemember.enums.Ebene.Niederlassung) rolle.ebene;
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
		return new RolleNiederlassung().test( rolle );
	}

	/**
	 * Convert this constraint type to a compatible type of this type.
	 * <pre>RolleEbeneNiederlassung.</pre>
	 * 
	 * @return compatible type of this type
	 */
	public RolleEbeneNiederlassungConstraint convertToRolleEbeneNiederlassungConstraint()
	{
		return new RolleEbeneNiederlassungConstraint(
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
		return "RolleNiederlassung: " + this.rolle;
	}

}