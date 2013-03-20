package _09_new_safeoperation_str_len.constraints;

import _09_new_safeoperation_str_len.predicates.*;

/**
 * Constraint class of String.
 * <pre>StrFirstUppercaseLetter.</pre>
 * Generated by {@link _09_new_safeoperation_str_len.NewSafeOperationStrLenConstraintCodeGenerator}.
 * Line-Number 88.
 */
public final class StrFirstUppercaseLetterConstraint
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
	public StrFirstUppercaseLetterConstraint(
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
		return new StrFirstUppercaseLetter().test( str );
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
	 * Debug output.
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "StrFirstUppercaseLetter: " + this.str;
	}

}