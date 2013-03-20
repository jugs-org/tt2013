package _05_switch.constraints;

import _05_switch.enums.*;
import _05_switch.predicates.*;

/**
 * Constraint class of Rolle.
 * <pre>(RolleEbeneZentrale or RolleEbeneNiederlassung or RolleEbeneFiliale).</pre>
 * Generated by {@link _05_switch.SwitchConstraintCodeGenerator}.
 * Line-Number 88.
 */
public final class ORB_RolleEbeneZentrale_RolleEbeneNiederlassung_RolleEbeneFiliale_ORE
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
	public ORB_RolleEbeneZentrale_RolleEbeneNiederlassung_RolleEbeneFiliale_ORE(
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
		return ( new RolleEbeneZentrale().test( rolle ) ||
					new RolleEbeneNiederlassung().test( rolle ) ||
					new RolleEbeneFiliale().test( rolle ) );
	}

	/**
	 * Debug output.
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "(RolleEbeneZentrale or RolleEbeneNiederlassung or RolleEbeneFiliale): " + this.rolle;
	}

}