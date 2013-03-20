package _03_excludes.constraints;

import _03_excludes.enums.*;
import _03_excludes.predicates.*;

/**
 * Constraint class of Rolle.
 * <pre>not(RolleEbeneFiliale).</pre>
 * Generated by {@link _03_excludes.ExcludesConstraintCodeGenerator}.
 * Line-Number 139.
 */
public final class NOT_RolleEbeneFiliale
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
	public NOT_RolleEbeneFiliale(
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
		return ( ! new RolleEbeneFiliale().test( rolle ) );
	}

	/**
	 * Convert this constraint type to a compatible type of this type.
	 * <pre>not(RolleFiliale).</pre>
	 * 
	 * @return compatible type of this type
	 */
	public NOT_RolleFiliale convertToNOT_RolleFiliale()
	{
		return new NOT_RolleFiliale(
				this.rolle );
	}

	/**
	 * Convert this constraint type to a compatible type of this type.
	 * <pre>(RolleEbeneZentrale or RolleEbeneNiederlassung).</pre>
	 * 
	 * @return compatible type of this type
	 */
	public ORB_RolleEbeneZentrale_RolleEbeneNiederlassung_ORE convertToORB_RolleEbeneZentrale_RolleEbeneNiederlassung_ORE()
	{
		return new ORB_RolleEbeneZentrale_RolleEbeneNiederlassung_ORE(
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
		return "not(RolleEbeneFiliale): " + this.rolle;
	}

}