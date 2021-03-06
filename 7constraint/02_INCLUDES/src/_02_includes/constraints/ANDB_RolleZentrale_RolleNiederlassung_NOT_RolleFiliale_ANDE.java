package _02_includes.constraints;

import _02_includes.enums.*;
import _02_includes.predicates.*;

/**
 * Constraint class of Rolle.
 * <pre>(RolleZentrale and RolleNiederlassung and not(RolleFiliale)).</pre>
 * Generated by {@link _02_includes.IncludesConstraintCodeGenerator}.
 * Line-Number 116.
 */
public final class ANDB_RolleZentrale_RolleNiederlassung_NOT_RolleFiliale_ANDE
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
	public ANDB_RolleZentrale_RolleNiederlassung_NOT_RolleFiliale_ANDE(
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
		return ( new RolleZentrale().test( rolle ) &&
					new RolleNiederlassung().test( rolle ) &&
					( ! new RolleFiliale().test( rolle ) ) );
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
	 * Convert this constraint type to a compatible type of this type.
	 * <pre>(RolleZentrale or RolleNiederlassung).</pre>
	 * 
	 * @return compatible type of this type
	 */
	public ORB_RolleZentrale_RolleNiederlassung_ORE convertToORB_RolleZentrale_RolleNiederlassung_ORE()
	{
		return new ORB_RolleZentrale_RolleNiederlassung_ORE(
				this.rolle );
	}

	/**
	 * Convert this constraint type to a compatible type of this type.
	 * <pre>(RolleNiederlassung and not(RolleFiliale)).</pre>
	 * 
	 * @return compatible type of this type
	 */
	public ANDB_RolleNiederlassung_NOT_RolleFiliale_ANDE convertToANDB_RolleNiederlassung_NOT_RolleFiliale_ANDE()
	{
		return new ANDB_RolleNiederlassung_NOT_RolleFiliale_ANDE(
				this.rolle );
	}

	/**
	 * Convert this constraint type to a compatible type of this type.
	 * <pre>RolleZentrale.</pre>
	 * 
	 * @return compatible type of this type
	 */
	public RolleZentraleConstraint convertToRolleZentraleConstraint()
	{
		return new RolleZentraleConstraint(
				this.rolle );
	}

	/**
	 * Convert this constraint type to a compatible type of this type.
	 * <pre>RolleNiederlassung.</pre>
	 * 
	 * @return compatible type of this type
	 */
	public RolleNiederlassungConstraint convertToRolleNiederlassungConstraint()
	{
		return new RolleNiederlassungConstraint(
				this.rolle );
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
	 * Debug output.
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "(RolleZentrale and RolleNiederlassung and not(RolleFiliale)): " + this.rolle;
	}

}