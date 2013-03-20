package _10_transform_safeoperation_str_len.constraints;

import _10_transform_safeoperation_str_len.predicates.*;

/**
 * Constraint class of String.
 * <pre>(StrMinLen23 and StrFirstUppercaseLetter).</pre>
 * Generated by {@link _10_transform_safeoperation_str_len.TransformSafeOperationStrLenConstraintCodeGenerator}.
 * Line-Number 103.
 */
public final class ANDB_StrMinLen23_StrFirstUppercaseLetter_ANDE
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
	public ANDB_StrMinLen23_StrFirstUppercaseLetter_ANDE(
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
		return ( new StrMinLen( 23 ).test( str ) &&
					new StrFirstUppercaseLetter().test( str ) );
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
	 * <pre>(StrMinLen10 and StrFirstUppercaseLetter).</pre>
	 * 
	 * @return compatible type of this type
	 */
	public ANDB_StrMinLen10_StrFirstUppercaseLetter_ANDE convertToANDB_StrMinLen10_StrFirstUppercaseLetter_ANDE()
	{
		return new ANDB_StrMinLen10_StrFirstUppercaseLetter_ANDE(
				this.str );
	}

	/**
	 * Convert this constraint type to a compatible type of this type.
	 * <pre>StrMinLen13.</pre>
	 * 
	 * @return compatible type of this type
	 */
	public StrMinLen13 convertToStrMinLen13()
	{
		return new StrMinLen13(
				this.str );
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
	 * Convert this constraint type to a compatible type of this type.
	 * <pre>StrMinLen20.</pre>
	 * 
	 * @return compatible type of this type
	 */
	public StrMinLen20 convertToStrMinLen20()
	{
		return new StrMinLen20(
				this.str );
	}

	/**
	 * Convert this constraint type to a compatible type of this type.
	 * <pre>(StrFirstUppercaseLetter and StrMinLen5).</pre>
	 * 
	 * @return compatible type of this type
	 */
	public ANDB_StrFirstUppercaseLetter_StrMinLen5_ANDE convertToANDB_StrFirstUppercaseLetter_StrMinLen5_ANDE()
	{
		return new ANDB_StrFirstUppercaseLetter_StrMinLen5_ANDE(
				this.str );
	}

	/**
	 * Convert this constraint type to a compatible type of this type.
	 * <pre>StrMinLen23.</pre>
	 * 
	 * @return compatible type of this type
	 */
	public StrMinLen23 convertToStrMinLen23()
	{
		return new StrMinLen23(
				this.str );
	}

	/**
	 * Convert this constraint type to a compatible type of this type.
	 * <pre>StrFirstUppercaseLetter.</pre>
	 * 
	 * @return compatible type of this type
	 */
	public StrFirstUppercaseLetterConstraint convertToStrFirstUppercaseLetterConstraint()
	{
		return new StrFirstUppercaseLetterConstraint(
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
		return "(StrMinLen23 and StrFirstUppercaseLetter): " + this.str;
	}

}