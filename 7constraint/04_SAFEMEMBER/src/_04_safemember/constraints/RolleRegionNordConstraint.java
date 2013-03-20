package _04_safemember.constraints;

import _04_safemember.enums.*;
import _04_safemember.predicates.*;

/**
 * Constraint class of Rolle.
 * <pre>RolleRegionNord.</pre>
 * Generated by {@link _04_safemember.SafeMememberConstraintCodeGenerator}.
 * Line-Number 116.
 */
public final class RolleRegionNordConstraint
{
	/**
	 * Context object of type Rolle.
	 */
	public final Rolle rolle;

	/**
	 * Constraintsafe member _04_safemember.enums.Region.Nord regionNord.
	 */
	public final _04_safemember.enums.Region.Nord regionNord;

	/**
	 * Constructor.
	 * 
	 * @param rolle current context object
	 * @throws IllegalArgumentException if constraint violated
	 */
	public RolleRegionNordConstraint(
			final Rolle rolle )
	{
		if ( ! test( rolle ) )
		{
			throw new IllegalArgumentException( "constraint violation" );
		}
		this.rolle = rolle;
		this.regionNord = (_04_safemember.enums.Region.Nord) ( ( Rolle.Niederlassung ) rolle ).region;
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
		return new RolleRegionNord().test( rolle );
	}

	/**
	 * Debug output.
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "RolleRegionNord: " + this.rolle;
	}

}