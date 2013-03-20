package _10_transform_safeoperation_str_len.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import _10_transform_safeoperation_str_len.StringConstraintUtil;
import _10_transform_safeoperation_str_len.TransformSafeOperationStrLenConstraintCodeGenerator;
import _10_transform_safeoperation_str_len.constraints.ANDB_StrFirstUppercaseLetter_StrExactLen5_ANDE;
import _10_transform_safeoperation_str_len.constraints.ANDB_StrFirstUppercaseLetter_StrMinLen5_ANDE;
import _10_transform_safeoperation_str_len.constraints.ANDB_StrMinLen10_StrFirstUppercaseLetter_ANDE;
import _10_transform_safeoperation_str_len.constraints.ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE;
import _10_transform_safeoperation_str_len.constraints.ANDB_StrMinLen23_StrFirstUppercaseLetter_ANDE;
import _10_transform_safeoperation_str_len.constraints.ANDB_StrMinLen8_StrLastDigit_ANDE;
import _10_transform_safeoperation_str_len.constraints.StrMaxLen12;
import _10_transform_safeoperation_str_len.constraints.StrMinLen13;
import _10_transform_safeoperation_str_len.predicates.StrFirstCharacter;
import _10_transform_safeoperation_str_len.predicates.StrFirstUppercaseLetter;
import _10_transform_safeoperation_str_len.predicates.StrLastCharacter;
import _10_transform_safeoperation_str_len.predicates.StrLastDigit;
import de.heinerkuecker.constraint.AbstractConstraintCodeGenerator;
import de.heinerkuecker.constraint.Exclude;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;
import de.heinerkuecker.constraint.util.coll.ReadOnlySet;

/**
 * JUnit4-Testcase f¸r Constraints, welche
 * durch {@link TransformSafeOperationStrLenConstraintCodeGenerator}
 * generiert wurden.
 * 
 * @author Heiner K&uuml;cker
 */
public class TransformSafeOperationStrLenConstraintCodeGeneratorTest
{
    /**
     * Testcase-Methode f¸r verschiedene generierte sichere Operationen.
     */
    @Test
    public void testANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE()
    {
        final ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE constraint =
                new ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE(
                        "A1234567890" );
        
        ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE_substringFrom0To5( 
                constraint );
        
        ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE_substringFrom2ToEnd(
                constraint );
        
        ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE_substringFromStartTo5(
                constraint );
        
        ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE_addStrMaxLen12(
                constraint );
        
        ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE_addStrMinLen13(
                constraint );
    }

    /**
     * Test-Methode f¸r die generierte sichere Operation
     * {@link ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE#substringFromZeroTo5}.
     * 
     * @param constraint Constraint
     */
    private static void ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE_substringFrom0To5(
            final ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE constraint )
    {
        final ANDB_StrFirstUppercaseLetter_StrExactLen5_ANDE afterSubstrConstraint =
                constraint.substringFromZeroTo5();

        assertTrue( 
                Character.isLetter( afterSubstrConstraint.str.charAt( 0 ) ) &&
                Character.isUpperCase( afterSubstrConstraint.str.charAt( 0 ) ) );
    }

    /**
     * Test-Methode f¸r die generierte sichere Operation
     * {@link ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE#substringFrom2ToEnd}.
     * 
     * @param constraint Constraint
     */
    private static void ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE_substringFrom2ToEnd(
            final ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE constraint )
    {
        final ANDB_StrMinLen8_StrLastDigit_ANDE afterSubstrConstraint =
                constraint.substringFrom2ToEnd();

        assertTrue( 
                afterSubstrConstraint.str.length() >= 8 &&
                Character.isDigit( afterSubstrConstraint.str.charAt( afterSubstrConstraint.str.length() - 1 ) ) );
    }

    /**
     * Test-Methode f¸r die generierte sichere Operation
     * {@link ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE#substringFromStartTo5}.
     * 
     * @param constraint Constraint
     */
    private static void ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE_substringFromStartTo5(
            final ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE constraint )
    {
        final ANDB_StrFirstUppercaseLetter_StrMinLen5_ANDE afterSubstrConstraint =
                constraint.substringFromStartTo5();

        assertTrue( 
                Character.isLetter( afterSubstrConstraint.str.charAt( 0 ) ) &&
                Character.isUpperCase( afterSubstrConstraint.str.charAt( 0 ) ) );

        assertTrue( 
                afterSubstrConstraint.str.length() >= 5 );
    }

    /**
     * Test-Methode f¸r die generierte sichere Operation
     * {@link ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE#addStrMaxLen12}.
     * 
     * @param constraint Constraint
     */
    private static void ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE_addStrMaxLen12(
            final ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE constraint )
    {
        final ANDB_StrMinLen10_StrFirstUppercaseLetter_ANDE afterAddConstraint =
                constraint.addStrMaxLen12(
                        new StrMaxLen12( 
                                "123456789012" ) );

        assertTrue( 
                Character.isLetter( afterAddConstraint.str.charAt( 0 ) ) &&
                Character.isUpperCase( afterAddConstraint.str.charAt( 0 ) ) );

        assertTrue( 
                afterAddConstraint.str.length() >= 10 );
    }

    /**
     * Test-Methode f¸r die generierte sichere Operation
     * {@link ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE#addStrMinLen13}.
     * 
     * @param constraint Constraint
     */
    private static void ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE_addStrMinLen13(
            final ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE constraint )
    {
        final ANDB_StrMinLen23_StrFirstUppercaseLetter_ANDE afterAddConstraint =
                constraint.addStrMinLen13(
                        new StrMinLen13( 
                                "1234567890123" ) );

        assertTrue( 
                Character.isLetter( afterAddConstraint.str.charAt( 0 ) ) &&
                Character.isUpperCase( afterAddConstraint.str.charAt( 0 ) ) );

        assertTrue( 
                afterAddConstraint.str.length() >= 23 );
    }
    
    /**
     * Diese Methode testet das Abweisen
     * eines Constraints, bei welchem
     * das erste und letzte Zeichen
     * unterschiedlich ist,
     * aber keine Mindestl‰nge von
     * 2 Zeichen festgelegt ist.
     * 
     * @throws IOException rethrown
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDeniedOfGenerationFirstLastCharacterConstraintWithoutMinLength()
    throws IOException
    {
        new AbstractConstraintCodeGenerator<String>(
                //cacheFolder
                //new File( "C:\\tmp" )
                null ,
                //srcDirPathStr
                System.getProperty(
                        "user.dir" ) +
                File.separator +
                "src" , 
                //packageName
                "_10_transform_safeoperation_str_len.constraints" ,
                //importStrArr ,
                new String[]{
                    "_10_transform_safeoperation_str_len.predicates.*" ,
                } ,
                //contextObjClassName
                "String" , 
                //contextObjName
                "str" ,
                //writeToDisc
                false ,
                //deleteUnusedConstraintJavaFiles
                false , 
                //exclude
                new Exclude<String>() )
        {{
            add(
                    // der folgende String soll mit einem Buchstaben beginnen und mit einer Ziffer enden, 
                    // deshalb muss er mindestens zwei Zeichen lang sein,
                    and(
                            // ein String dessen erstes Zeichen ein Groﬂbuchstabe ist
                            new StrFirstUppercaseLetter() ,
                            // ein String dessen letztes Zeichen eine Ziffer ist
                            new StrLastDigit() ) );
        }

        /**
         * Abweisen eines Constraints, 
         * bei welchem das erste und 
         * letzte Zeichen unterschiedlich ist,
         * aber keine Mindestl‰nge von
         * 2 Zeichen festgelegt ist.
         * 
         * @see AbstractConstraintCodeGenerator#checkConstraint
         */
        @Override
        protected void checkConstraint(
                final Predicate<String> predicateToCheck )
        {
            final Integer strMinLenIntgrObj =
                    StringConstraintUtil.computeStrMinLen(
                            predicateToCheck );

            final ReadOnlySet<PrimitivPredicate<String>> nonNegatedPrimitivPredicateSet =
                    predicateToCheck.getNonNegatedPrimitivPredicateSet();

            final StrFirstCharacter strFirstCharacterPredicate =
                    StringConstraintUtil.getStrFirstCharacterPredicate(
                            nonNegatedPrimitivPredicateSet );
            
            final StrLastCharacter strLastCharacterPredicate =
                    StringConstraintUtil.getStrLastCharacterPredicate(
                            nonNegatedPrimitivPredicateSet );
            
            if ( strFirstCharacterPredicate != null &&
                    strLastCharacterPredicate != null &&
                    false == strFirstCharacterPredicate.getEnabledCharacters().equalsByElements(
                            strLastCharacterPredicate.getEnabledCharacters().toJavaSet() ) &&
                    ( strMinLenIntgrObj == null ||
                     strMinLenIntgrObj.intValue() < 2 ) )
            {
                throw new IllegalArgumentException(
                        "different first and last character constraint for String without min length " +
                        predicateToCheck +
                        " LineNr " + predicateToCheck.lineNr );
            }
        }
        
        }.execute();
    }

}