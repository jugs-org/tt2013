package _11_transform_safeoperation_int_range.test;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import _11_transform_safeoperation_int_range.constraints.ORB_IntGreater1_IntEqual1_ORE;
import _11_transform_safeoperation_int_range.constraints.ORB_IntGreaterZero_IntEqualZero_ORE;
import _11_transform_safeoperation_int_range.predicates.IntEqual;
import _11_transform_safeoperation_int_range.predicates.IntGreater;
import _11_transform_safeoperation_int_range.predicates.IntLesser;
import de.heinerkuecker.constraint.AbstractConstraintCodeGenerator;
import de.heinerkuecker.constraint.Exclude;

/**
 * @author Heiner K&uuml;cker
 */
public class TransformSafeOperationIntRangeConstraintCodeGeneratorTest
{
    /**
     * Diese Methode testet das Abweisen
     * eines Constraints, bei dem eine
     * int-Zahl gleichzeitig grösser
     * und kleiner 0 sein soll.
     * 
     * @throws IOException rethrown
     */
    @Test(expected = IllegalStateException.class)
    public void testKontradiction()
    throws IOException
    {
        new AbstractConstraintCodeGenerator<Integer>(
                //cacheFolder
                new File(
                        "C:\\tmp" ) ,
                //srcDirPathStr
                System.getProperty(
                        "user.dir" ) +
                File.separator +
                "int_range" +
                File.separator +
                "src" , 
                //packageName
                "int_range" ,
                //importStrArr ,
                new String[]{ 
                    "_11_transform_safeoperation_int_range.predicates.*" } ,
                //contextObjClassName
                "Integer" , 
                //contextObjName
                "value" ,
                //writeToDisc
                false ,
                //deleteUnusedConstraintJavaFiles
                false , 
                //exclude
                new Exclude<Integer>() )
        {{
            add(
                    and(
                            new IntGreater( 0 ) , // x > 0
                            new IntLesser ( 0 )   // x < 0
                            ) );
        }}.execute();
    }

    /**
     * Diese Methode testet das Abweisen
     * eines Constraints, bei dem im
     * Switch ein Zweig ausserhalb
     * des usprünglichen Werte-Bereiches
     * liegt.
     * 
     * @throws IOException rethrown
     */
    @Test(expected = IllegalStateException.class)
    public void testIllegalSwitch()
    throws IOException
    {
        new AbstractConstraintCodeGenerator<Integer>(
                //cacheFolder
                new File(
                        "C:\\tmp" ) ,
                //srcDirPathStr
                System.getProperty(
                        "user.dir" ) +
                File.separator +
                "int_range" +
                File.separator +
                "src" , 
                //packageName
                "int_range" ,
                //importStrArr ,
                new String[]{ 
                    "_11_transform_safeoperation_int_range.predicates.*" } ,
                //contextObjClassName
                "Integer" , 
                //contextObjName
                "value" ,
                //writeToDisc
                false ,
                //deleteUnusedConstraintJavaFiles
                false , 
                //exclude
                new Exclude<Integer>() )
        {{
            add(
                    and(
                            new IntGreater( 0 ) , // x > 0
                            new IntEqual  ( 0 )   // x == 0
                            ) ,
                    switchDef( 
                            "LesserZeroEqualZeroGreaterZero" , 
                            //caseMethodReturnTypeName
                            "void" ,
                            //caseMethodExceptionsStr
                            null ,
                            //switchPredicateArr
                            new IntLesser ( 0 ) , // x  < 0 TODO das ist nicht möglich
                            new IntEqual  ( 0 ) , // x == 0
                            new IntGreater( 0 )   // x  > 0
                            ) );
        }}.execute();
    }

    /**
     * Test sichere Operation Add 1 to 0
     */
// TODO Übungsaufgabe Stuttgarter Testtage 2013
//    @Test
//    public void testAddOneToZero()
//    {
//        final ORB_IntGreaterZero_IntEqualZero_ORE srcConstraint =
//                new ORB_IntGreaterZero_IntEqualZero_ORE(
//                        0 );
//        
//        final ORB_IntGreater1_IntEqual1_ORE dstConstraint =
//                srcConstraint.add1();
//        
//        Assert.assertEquals(
//                1 ,
//                dstConstraint.value.intValue() );
//    }
    
    /**
     * Test sichere Operation Add 1 to 1
     */
// TODO Übungsaufgabe Stuttgarter Testtage 2013
//    @Test
//    public void testAddOneToOne()
//    {
//        final ORB_IntGreaterZero_IntEqualZero_ORE srcConstraint =
//                new ORB_IntGreaterZero_IntEqualZero_ORE(
//                        1 );
//        
//        final ORB_IntGreater1_IntEqual1_ORE dstConstraint =
//                srcConstraint.add1();
//        
//        Assert.assertEquals(
//                2 ,
//                dstConstraint.value.intValue() );
//    }
    
}