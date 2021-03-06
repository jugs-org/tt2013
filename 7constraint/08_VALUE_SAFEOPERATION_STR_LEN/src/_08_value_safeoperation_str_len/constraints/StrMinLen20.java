package _08_value_safeoperation_str_len.constraints;

import _08_value_safeoperation_str_len.predicates.*;

/**
 * Constraint class of String.
 * <pre>StrMinLen20.</pre>
 * Generated by {@link _08_value_safeoperation_str_len.ValueSafeOperationStrLenConstraintCodeGenerator}.
 * Line-Number 109.
 */
public final class StrMinLen20
{
	/**
	 * Context object of type String.
	 */
	public final String str;

	/**
	 * Constructor.
	 * 
	 * @param str current context object
	 * @throws IllegalArgumentException if constraint violated
	 */
	public StrMinLen20(
			final String str )
	{
		if ( ! test( str ) )
		{
			throw new IllegalArgumentException( "constraint violation" );
		}
		this.str = str;
	}

	/**
	 * Test method.
	 * 
	 * @param str current context object
	 * @return constraint expression satisfied or not
	 */
	public static boolean test(
			final String str )
	{
		return new StrMinLen( 20 ).test( str );
	}

	/**
	 * Convert this constraint type to a compatible type of this type.
	 * <pre>StrIsNotNull.</pre>
	 * 
	 * @return compatible type of this type
	 */
	public StrIsNotNullConstraint convertToStrIsNotNullConstraint()
	{
		return new StrIsNotNullConstraint(
				this.str );
	}

	/**
	 * Convert this constraint type to a compatible type of this type.
	 * <pre>StrMinLen10.</pre>
	 * 
	 * @return compatible type of this type
	 */
	public StrMinLen10 convertToStrMinLen10()
	{
		return new StrMinLen10(
				this.str );
	}

	/**
	 * Convert this constraint type to a compatible type of this type.
	 * <pre>StrMinLen1.</pre>
	 * 
	 * @return compatible type of this type
	 */
	public StrMinLen1 convertToStrMinLen1()
	{
		return new StrMinLen1(
				this.str );
	}

	/**
	 * Sichere Ermittlung String-L�nge.
	 *
	 * @return String-L�nge als int-Wert
	 */
	public int length()
	{
		return this.str.length();
	}

	/**
	 * Debug output.
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "StrMinLen20: " + this.str;
	}

}