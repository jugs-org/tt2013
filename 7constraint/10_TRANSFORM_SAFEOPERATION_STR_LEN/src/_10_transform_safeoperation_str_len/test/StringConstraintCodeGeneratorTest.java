package _10_transform_safeoperation_str_len.test;

import static org.junit.Assert.*;

import org.junit.Test;

import _10_transform_safeoperation_str_len.predicates.StrFirstUppercaseLetter;
import _10_transform_safeoperation_str_len.predicates.StrIsNotNull;
import _10_transform_safeoperation_str_len.predicates.StrMaxLen;
import _10_transform_safeoperation_str_len.predicates.StrMinLen;

import de.heinerkuecker.constraint.AbstractStringConstraintTest;
import de.heinerkuecker.constraint.BruteForceCompatibleChecker;
import de.heinerkuecker.constraint.BruteForceSolver;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PredicateSimplifier;

/**
 * @author Heiner K&uuml;cker
 */
public class StringConstraintCodeGeneratorTest
extends AbstractStringConstraintTest
{
    /**
     * Test (StrMinLen10 and StrMaxLen20 is no contradiction
     */
    @Test
    public void test_ANDB_StrMinLen10_StrMaxLen20_ANDE_IsNoContradiction()
    {
        assertFalse(
                and(
                        new StrMinLen( 10 ) ,
                        new StrMaxLen( 20 ) ).isContradiction(
                                getCache() ) );
    }
    
    /**
     * Test (StrMinLen10 and StrMaxLen20) is compatible to StrIsNotNull.
     */
    @Test
    public void test_ANDB_StrMinLen10_StrMaxLen20_ANDE_to_StrIsNotNull_IsCompatible()
    {
        assertTrue(
                BruteForceCompatibleChecker.isCompatibleTo(
                        getCache() ,
                        and(
                                new StrMinLen( 10 ) ,
                                new StrMaxLen( 20 ) ) ,
                        new StrIsNotNull() ) );
    }
    
    // TODO noch die restlichen Tests aus der main-Methode hier einfügen
    
    /**
     * Test (StrMinLen10 and StrFirstUppercaseLetter) is not equal to StrMinLen10.
     */
    @Test
    public void test_ANDB_StrMinLen10_StrFirstUppercaseLetter_ANDE_to_StrMinLen10_IsNoEqual()
    {
        assertFalse(
                BruteForceSolver.isEquivalent(
                        getCache() ,
                        and(
                                new StrMinLen( 10 ) ,
                                new StrFirstUppercaseLetter() ) ,
                        new StrMinLen( 10 ) ) );
    }
    
    /**
     * Test-Main-Methode wegen einem Heisenbug.
     * Im Debugger ist der Test erfolgreich, im Run schlägt er fehl.
     * 
     * @param args
     */
    public static void main(
            final String[] args)
    {
        /*final*/ StringConstraintCodeGeneratorTest test;

        //test = new StringConstraintCodeGeneratorTest();
        //
        //if ( test.and(
        //        new StrMinLen( 10 ) ,
        //        new StrMaxLen( 20 ) 
        //    ).isContradiction(
        //            test.getCache() ) )
        //{
        //    throw new RuntimeException(
        //            "test failed" );
        //}

        //test = new StringConstraintCodeGeneratorTest();
        //
        //if ( false == BruteForceCompatibleChecker.isCompatibleTo(
        //        test.getCache() ,
        //        test.and(
        //                new StrMinLen( 10 ) ,
        //                new StrMaxLen( 20 ) ) ,
        //        new StrIsNotNull() ) )
        //{
        //    throw new RuntimeException(
        //            "test failed" );
        //}
        
        test = new StringConstraintCodeGeneratorTest();
        
        if ( BruteForceCompatibleChecker.isCompatibleTo(
                test.getCache() ,
                new StrMinLen( 10 ) ,
                new StrMaxLen( 20 ) ) )
        {
            throw new RuntimeException(
                    "test failed" );
        }

        test = new StringConstraintCodeGeneratorTest();
        
        final Predicate<String> constraint =
                test.and(
                        new StrMinLen( 10 ) ,
                        new StrMaxLen( 20 ) );

        final Predicate<String> simplifiedConstraint =
                PredicateSimplifier.simplify(
                        constraint ,
                        test.getCache() );

        if ( false == constraint.equals(
                simplifiedConstraint ) )
        {
            throw new RuntimeException(
                    "test failed" );
        }
        
        System.out.println( "ok" );
    }

}
