package _05_switch.constraints;

import _05_switch.enums.*;
import _05_switch.predicates.*;

/**
 * Constraint class of Rolle.
 * <pre>RolleRegionSued.</pre>
 * Generated by {@link _05_switch.SwitchConstraintCodeGenerator}.
 * Line-Number 0.
 */
public final class RolleRegionSuedConstraint
{
	/**
	 * Context object of type Rolle.
	 */
	public final Rolle rolle;

	/**
	 * Constraintsafe member _05_switch.enums.Region.Sued regionSued.
	 */
	public final _05_switch.enums.Region.Sued regionSued;

	/**
	 * Constructor.
	 * 
	 * @param rolle current context object
	 * @throws IllegalArgumentException if constraint violated
	 */
	public RolleRegionSuedConstraint(
			final Rolle rolle )
	{
		if ( ! test( rolle ) )
		{
			throw new IllegalArgumentException( "constraint violation" );
		}
		this.rolle = rolle;
		this.regionSued = (_05_switch.enums.Region.Sued) ( ( Rolle.Niederlassung ) rolle ).region;
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
		return new RolleRegionSued().test( rolle );
	}

	/**
	 * Convert this constraint type to a compatible type of this type.
	 * <pre>(RolleRegionNord or RolleRegionSued or RolleRegionWest or RolleRegionOst).</pre>
	 * 
	 * @return compatible type of this type
	 */
	public ORB_RolleRegionNord_RolleRegionSued_RolleRegionWest_RolleRegionOst_ORE convertToORB_RolleRegionNord_RolleRegionSued_RolleRegionWest_RolleRegionOst_ORE()
	{
		return new ORB_RolleRegionNord_RolleRegionSued_RolleRegionWest_RolleRegionOst_ORE(
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
		return "RolleRegionSued: " + this.rolle;
	}

}