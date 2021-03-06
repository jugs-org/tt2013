package _03_excludes.constraints;

import _03_excludes.enums.*;
import _03_excludes.predicates.*;

/**
 * Constraint class of Rolle.
 * <pre>RolleFiliale.</pre>
 * Generated by {@link _03_excludes.ExcludesConstraintCodeGenerator}.
 * Line-Number 107.
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
	 * Convert this constraint type to a compatible type of this type.
	 * <pre>RolleEbeneFiliale.</pre>
	 * 
	 * @return compatible type of this type
	 */
	public RolleEbeneFilialeConstraint convertToRolleEbeneFilialeConstraint()
	{
		return new RolleEbeneFilialeConstraint(
				this.rolle );
	}

	/**
	 * Convert this constraint type to a compatible type of this type.
	 * <pre>(not(RolleZentrale) and not(RolleNiederlassung)).</pre>
	 * 
	 * @return compatible type of this type
	 */
	public ANDB_NOT_RolleZentrale_NOT_RolleNiederlassung_ANDE convertToANDB_NOT_RolleZentrale_NOT_RolleNiederlassung_ANDE()
	{
		return new ANDB_NOT_RolleZentrale_NOT_RolleNiederlassung_ANDE(
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
		return "RolleFiliale: " + this.rolle;
	}

}