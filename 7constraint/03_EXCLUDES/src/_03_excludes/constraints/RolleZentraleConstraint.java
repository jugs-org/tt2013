package _03_excludes.constraints;

import _03_excludes.enums.*;
import _03_excludes.predicates.*;

/**
 * Constraint class of Rolle.
 * <pre>RolleZentrale.</pre>
 * Generated by {@link _03_excludes.ExcludesConstraintCodeGenerator}.
 * Line-Number 92.
 */
public final class RolleZentraleConstraint
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
	public RolleZentraleConstraint(
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
		return new RolleZentrale().test( rolle );
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
	 * <pre>not(RolleEbeneFiliale).</pre>
	 * 
	 * @return compatible type of this type
	 */
	public NOT_RolleEbeneFiliale convertToNOT_RolleEbeneFiliale()
	{
		return new NOT_RolleEbeneFiliale(
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