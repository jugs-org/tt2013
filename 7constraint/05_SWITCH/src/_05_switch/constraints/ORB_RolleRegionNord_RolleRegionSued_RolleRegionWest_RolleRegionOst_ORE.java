package _05_switch.constraints;

import _05_switch.enums.*;
import _05_switch.predicates.*;
import de.heinerkuecker.constraint.util.exc.UnreachableCodeException;

/**
 * Constraint class of Rolle.
 * <pre>(RolleRegionNord or RolleRegionSued or RolleRegionWest or RolleRegionOst).</pre>
 * Generated by {@link _05_switch.SwitchConstraintCodeGenerator}.
 * Line-Number 106.
 */
public final class ORB_RolleRegionNord_RolleRegionSued_RolleRegionWest_RolleRegionOst_ORE
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
	public ORB_RolleRegionNord_RolleRegionSued_RolleRegionWest_RolleRegionOst_ORE(
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
		return ( new RolleRegionNord().test( rolle ) ||
					new RolleRegionSued().test( rolle ) ||
					new RolleRegionWest().test( rolle ) ||
					new RolleRegionOst().test( rolle ) );
	}

	/**
	 * Abstract class for constraint safe switch Region
	 */
	abstract public class RegionSwitch
	{
		/**
		 * Abstract method for case RolleRegionNord
		 * @param constraint specialized case constraint
		 */
		abstract protected void caseRolleRegionNord(
				final RolleRegionNordConstraint constraint );

		/**
		 * Abstract method for case RolleRegionSued
		 * @param constraint specialized case constraint
		 */
		abstract protected void caseRolleRegionSued(
				final RolleRegionSuedConstraint constraint );

		/**
		 * Abstract method for case RolleRegionWest
		 * @param constraint specialized case constraint
		 */
		abstract protected void caseRolleRegionWest(
				final RolleRegionWestConstraint constraint );

		/**
		 * Abstract method for case RolleRegionOst
		 * @param constraint specialized case constraint
		 */
		abstract protected void caseRolleRegionOst(
				final RolleRegionOstConstraint constraint );

		/**
		 * Final do method for switch Region
		 */
		public final void doSwitch()
		{
			if ( RolleRegionNordConstraint.test( ORB_RolleRegionNord_RolleRegionSued_RolleRegionWest_RolleRegionOst_ORE.this.rolle ) )
			{
				caseRolleRegionNord(
						new RolleRegionNordConstraint(
								ORB_RolleRegionNord_RolleRegionSued_RolleRegionWest_RolleRegionOst_ORE.this.rolle ) );
			}
			else if ( RolleRegionSuedConstraint.test( ORB_RolleRegionNord_RolleRegionSued_RolleRegionWest_RolleRegionOst_ORE.this.rolle ) )
			{
				caseRolleRegionSued(
						new RolleRegionSuedConstraint(
								ORB_RolleRegionNord_RolleRegionSued_RolleRegionWest_RolleRegionOst_ORE.this.rolle ) );
			}
			else if ( RolleRegionWestConstraint.test( ORB_RolleRegionNord_RolleRegionSued_RolleRegionWest_RolleRegionOst_ORE.this.rolle ) )
			{
				caseRolleRegionWest(
						new RolleRegionWestConstraint(
								ORB_RolleRegionNord_RolleRegionSued_RolleRegionWest_RolleRegionOst_ORE.this.rolle ) );
			}
			else if ( RolleRegionOstConstraint.test( ORB_RolleRegionNord_RolleRegionSued_RolleRegionWest_RolleRegionOst_ORE.this.rolle ) )
			{
				caseRolleRegionOst(
						new RolleRegionOstConstraint(
								ORB_RolleRegionNord_RolleRegionSued_RolleRegionWest_RolleRegionOst_ORE.this.rolle ) );
			}
			else
			{
				throw new UnreachableCodeException();
			}
		}

	}

	/**
	 * Debug output.
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "(RolleRegionNord or RolleRegionSued or RolleRegionWest or RolleRegionOst): " + this.rolle;
	}

}