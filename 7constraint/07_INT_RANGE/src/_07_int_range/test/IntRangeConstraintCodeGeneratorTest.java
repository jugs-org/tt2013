package _07_int_range.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import _07_int_range.constraints.ANDB_IntGreaterMinus10_IntLesser10_ANDE;
import _07_int_range.constraints.ANDB_IntGreaterMinus10_IntLesserZero_ANDE;
import _07_int_range.constraints.ANDB_IntLesser10_IntGreaterZero_ANDE;
import _07_int_range.constraints.IntEqualZero;

/**
 * @author Heiner K&uuml;cker
 */
public class IntRangeConstraintCodeGeneratorTest
{
    /**
     * Diese Methode testet das Abweisen
     * eines Constraints, bei dem eine
     * int-Zahl gleichzeitig grösser
     * und kleiner 0 sein soll.
     * 
     * @throws IOException rethrown
     */
// TODO Übungsaufgabe Stuttgarter Testtage 2013    
//    @Test(expected = IllegalStateException.class)
//    public void testKontradiction()
//    throws IOException
//    {
//        new AbstractConstraintCodeGenerator<Integer>(
//                //cacheFolder
//                new File(
//                        "C:\\tmp" ) ,
//                //srcDirPathStr
//                System.getProperty(
//                        "user.dir" ) +
//                File.separator +
//                "int_range" +
//                File.separator +
//                "src" , 
//                //packageName
//                "int_range" ,
//                //importStrArr ,
//                new String[]{ 
//                    "_07_int_range.predicates.*" } ,
//                //contextObjClassName
//                "Integer" , 
//                //contextObjName
//                "value" ,
//                //writeToDisc
//                false ,
//                //deleteUnusedConstraintJavaFiles
//                false , 
//                //exclude
//                new Exclude<Integer>() )
//        {{
//            add(
//                    and(
//                            new IntGreater( 0 ) , // x > 0
//                            new IntLesser ( 0 )   // x < 0
//                            ) );
//        }}.execute();
//    }

    /**
     * Diese Methode testet das Abweisen
     * eines Constraints, bei dem eine
     * int-Zahl gleichzeitig grösser
     * und kleiner 0 sein soll.
     * 
     * @throws IOException rethrown
     */
// TODO Übungsaufgabe Stuttgarter Testtage 2013    
//    @Test(expected = SpecializedPredicateInSwitchDefinitionIsContradiction.class)
//    public void testIllegalSwitch()
//    throws IOException
//    {
//        new AbstractConstraintCodeGenerator<Integer>(
//                //cacheFolder
//                new File(
//                        "C:\\tmp" ) ,
//                //srcDirPathStr
//                System.getProperty(
//                        "user.dir" ) +
//                File.separator +
//                "int_range" +
//                File.separator +
//                "src" , 
//                //packageName
//                "int_range" ,
//                //importStrArr ,
//                new String[]{ 
//                    "_07_int_range.predicates.*" } ,
//                //contextObjClassName
//                "Integer" , 
//                //contextObjName
//                "value" ,
//                //writeToDisc
//                false ,
//                //deleteUnusedConstraintJavaFiles
//                false , 
//                //exclude
//                new Exclude<Integer>() )
//        {{
//            add(
//                    and(
//                            new IntGreater( -10 ) , // x > -10
//                            new IntLesser (  10 )   // x <  10
//                            ) ,
//                    switchDef( 
//                            "Lesser0Equal0Greater0" , 
//                            //caseMethodReturnTypeName
//                            "void" ,
//                            //caseMethodExceptionsStr
//                            null ,
//                            //switchPredicateArr
//                            and(
//                                    new IntLesser ( 0 )   , // x < 0
//                                    new IntGreater( 0 ) ) , // x > 0
//                            new IntEqual  ( 0 ) // x == 0
//                            ) );
//        }}.execute();
//    }

    /**
     * Test Range-Switch
     */
// TODO Übungsaufgabe Stuttgarter Testtage 2013    
//    @Test
//    public void testUse()
//    {
//        for ( int i = -9 ; i < 10 ; i++ )
//        {
//            doANDB_IntGreaterMinus10_IntLesser10_ANDE(
//                    new ANDB_IntGreaterMinus10_IntLesser10_ANDE(
//                            i ) );
//        }
//    }
    
    /**
     * private Hilfs-Methode für {@link #testUse}.
     * 
     * @param constraint Constraint
     */
// TODO Übungsaufgabe Stuttgarter Testtage 2013
//    private static void doANDB_IntGreaterMinus10_IntLesser10_ANDE(
//            final ANDB_IntGreaterMinus10_IntLesser10_ANDE constraint )
//    {
//        constraint.new LesserZeroEqualZeroGreaterZeroSwitch()
//        {
//            @Override
//            protected void caseIntLesserZero(
//                    final ANDB_IntGreaterMinus10_IntLesserZero_ANDE constraint )
//            {
//                assertTrue(
//                        constraint.value > - 10 &&
//                        constraint.value < 0 );
//            }
//            
//            @Override
//            protected void caseIntEqualZero(
//                    final IntEqualZero constraint )
//            {
//                assertTrue(
//                        constraint.value == 0 );
//            }
//            
//            
//            @Override
//            protected void caseIntGreaterZero(
//                    final ANDB_IntLesser10_IntGreaterZero_ANDE constraint )
//            {
//                assertTrue(
//                        constraint.value > 0 &&
//                        constraint.value < 10 );
//            }
//
//            //@Override
//            //protected void caseDefault()
//            //{
//            //    // TODO dieser Zweig wird nie durchlaufen, die Generierung wird durch das Exclude unterbunden
//            //    fail();
//            //    
//            //}
//        }.doSwitch();
//    }

}