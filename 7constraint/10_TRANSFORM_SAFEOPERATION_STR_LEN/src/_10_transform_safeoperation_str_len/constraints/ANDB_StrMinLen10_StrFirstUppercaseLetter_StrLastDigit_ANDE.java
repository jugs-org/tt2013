package _10_transform_safeoperation_str_len.constraints;

import _10_transform_safeoperation_str_len.predicates.*;

/**
 * Constraint class of String.
 * <pre>(StrMinLen10 and StrFirstUppercaseLetter and StrLastDigit).</pre>
 * Generated by {@link _10_transform_safeoperation_str_len.TransformSafeOperationStrLenConstraintCodeGenerator}.
 * Line-Number 103.
 */
public final class ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE
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
	public ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE(
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
		return ( new StrMinLen( 10 ).test( str ) &&
					new StrFirstUppercaseLetter().test( str ) &&
					new StrLastDigit().test( str ) );
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
	 * <pre>(StrFirstUppercaseLetter and StrLastDigit and StrMinLen10).</pre>
	 * 
	 * @return compatible type of this type
	 */
	public ANDB_StrFirstUppercaseLetter_StrLastDigit_StrMinLen10_ANDE convertToANDB_StrFirstUppercaseLetter_StrLastDigit_StrMinLen10_ANDE()
	{
		return new ANDB_StrFirstUppercaseLetter_StrLastDigit_StrMinLen10_ANDE(
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
	 * Convert this constraint type to a compatible type of this type.
	 * <pre>(StrMinLen8 and StrLastDigit).</pre>
	 * 
	 * @return compatible type of this type
	 */
	public ANDB_StrMinLen8_StrLastDigit_ANDE convertToANDB_StrMinLen8_StrLastDigit_ANDE()
	{
		return new ANDB_StrMinLen8_StrLastDigit_ANDE(
				this.str );
	}

	/**
	 * Sichere Sub-String-Operation.
	 *
	 * @return constraintgesicherter Sub-String
	 */
	public ANDB_StrFirstUppercaseLetter_StrExactLen5_ANDE substringFromZeroTo5()
	{
		return new ANDB_StrFirstUppercaseLetter_StrExactLen5_ANDE( str.substring( 0 , 5 ) );
	}

	/**
	 * Sichere Sub-String-Operation.
	 *
	 * @return constraintgesicherter Sub-String
	 */
	public StrExactLen5 substringFrom2To7()
	{
		return new StrExactLen5( str.substring( 2 , 7 ) );
	}

	/**
	 * Sichere Sub-String-Operation.
	 *
	 * @return constraintgesicherter Sub-String
	 */
	public ANDB_StrMinLen8_StrLastDigit_ANDE substringFrom2ToEnd()
	{
		return new ANDB_StrMinLen8_StrLastDigit_ANDE( str.substring( 2 ) );
	}

	/**
	 * Sichere Sub-String-Operation.
	 *
	 * @return constraintgesicherter Sub-String
	 */
	public ANDB_StrFirstUppercaseLetter_StrMinLen5_ANDE substringFromStartTo5()
	{
		return new ANDB_StrFirstUppercaseLetter_StrMinLen5_ANDE( str.substring( 0 , 5 ) );
	}

	/**
	 * Sichere String-Add-Operation.
	 *
	 * @param strConstraintToAdd auf maximale L�nge constraint-gesicherter zu addierender String
	 * @return constraint-gesicherter String
	 */
	public ANDB_StrMinLen10_StrFirstUppercaseLetter_ANDE addStrMaxLen12(
			final StrMaxLen12 strConstraintToAdd )
	{
		return new ANDB_StrMinLen10_StrFirstUppercaseLetter_ANDE( str.concat( strConstraintToAdd.str ) );
	}

	/**
	 * Sichere String-Add-Operation.
	 *
	 * @param strConstraintToAdd auf minimale L�nge constraint-gesicherter zu addierender String
	 * @return constraint-gesicherter String
	 */
	public ANDB_StrMinLen23_StrFirstUppercaseLetter_ANDE addStrMinLen13(
			final StrMinLen13 strConstraintToAdd )
	{
		return new ANDB_StrMinLen23_StrFirstUppercaseLetter_ANDE( str.concat( strConstraintToAdd.str ) );
	}

	/**
	 * Debug output.
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "(StrMinLen10 and StrFirstUppercaseLetter and StrLastDigit): " + this.str;
	}

}