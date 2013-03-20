package _03_excludes.constraints;

import _03_excludes.enums.*;
import _03_excludes.predicates.*;

/**
 * Constraint class of Rolle.
 * <pre>(not(RolleZentrale) and not(RolleNiederlassung)).</pre>
 * Generated by {@link _03_excludes.ExcludesConstraintCodeGenerator}.
 * Line-Number 129.
 */
public final class ANDB_NOT_RolleZentrale_NOT_RolleNiederlassung_ANDE
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
	public ANDB_NOT_RolleZentrale_NOT_RolleNiederlassung_ANDE(
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
		return ( ( ! new RolleZentrale().test( rolle ) ) &&
					( ! new RolleNiederlassung().test( rolle ) ) );
	}

	/**
	 * Debug output.
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "(not(RolleZentrale) and not(RolleNiederlassung)): " + this.rolle;
	}

}