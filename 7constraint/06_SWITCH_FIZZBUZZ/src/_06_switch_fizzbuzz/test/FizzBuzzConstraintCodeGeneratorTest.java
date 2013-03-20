package _06_switch_fizzbuzz.test;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import _06_switch_fizzbuzz.FizzBuzzConstraintCodeGenerator;
import _06_switch_fizzbuzz.constraints.ANDB_Buzz_NOT_Fizz_ANDE;
import _06_switch_fizzbuzz.constraints.ANDB_Fizz_Buzz_ANDE;
import _06_switch_fizzbuzz.constraints.ANDB_Fizz_NOT_Buzz_ANDE;
import _06_switch_fizzbuzz.constraints.ANDB_NOT_Fizz_NOT_Buzz_ANDE;
import _06_switch_fizzbuzz.constraints.ORB_Fizz_NOT_Fizz_Buzz_NOT_Buzz_ORE;
import _06_switch_fizzbuzz.predicates.Buzz;
import _06_switch_fizzbuzz.predicates.Fizz;
import de.heinerkuecker.constraint.AbstractConstraintCodeGenerator;
import de.heinerkuecker.constraint.Exclude;
import de.heinerkuecker.constraint.SwitchDefinition;

/**
 * JUnit4-Test zum Prüfen des Abweisens
 * des im Javadoc von
 * {@link FizzBuzzConstraintCodeGenerator}
 * beschriebenen Programmfehlers.
 * 
 * @author Heiner K&uuml;cker
 */
public class FizzBuzzConstraintCodeGeneratorTest
{
    /**
     * Test der korrekten Generierung des Fizz-Buzz-Switch.
     */
    @Test
    public void testUseGeneratedSwitch()
    {
        for ( int i = 1 ; i <= 100 ; i++ )
        {
            final ORB_Fizz_NOT_Fizz_Buzz_NOT_Buzz_ORE constraint =
                    new ORB_Fizz_NOT_Fizz_Buzz_NOT_Buzz_ORE(
                            i );
            
            constraint.new FizzBuzzSwitch()
            {
                // ganzzahlig teilbar durch 3, nicht ganzzahlig teilbar durch 5
                @Override
                protected void caseANDB_Fizz_NOT_Buzz_ANDE(
                        final ANDB_Fizz_NOT_Buzz_ANDE constraint )
                {
                    System.out.println( "Fizz (" + constraint.value + ")" );

                    Assert.assertTrue(
                            constraint.value.intValue() % 3 == 0 &&
                            constraint.value.intValue() % 5 != 0 );
                }

                // ganzzahlig teilbar durch 5, nicht ganzzahlig teilbar durch 3
                @Override
                protected void caseANDB_Buzz_NOT_Fizz_ANDE(
                        final ANDB_Buzz_NOT_Fizz_ANDE constraint )
                {
                    System.out.println( "Buzz (" + constraint.value + ")" );
                    
                    Assert.assertTrue(
                            constraint.value.intValue() % 5 == 0 &&
                            constraint.value.intValue() % 3 != 0 );
                }
                
                // ganzzahlig teilbar durch 3 und ganzzahlig teilbar durch 5
                @Override
                protected void caseANDB_Fizz_Buzz_ANDE(
                        final ANDB_Fizz_Buzz_ANDE constraint )
                {
                    System.out.println( "Fizz Buzz (" + constraint.value + ")" );
                    
                    Assert.assertTrue(
                            constraint.value.intValue() % 3 == 0 &&
                            constraint.value.intValue() % 5 == 0 );
                }

                // nicht ganzzahlig teilbar durch 3 und nicht ganzzahlig teilbar durch 5
                @Override
                protected void caseANDB_NOT_Fizz_NOT_Buzz_ANDE(
                        final ANDB_NOT_Fizz_NOT_Buzz_ANDE constraint )
                {
                    System.out.println( constraint.value );
                    
                    Assert.assertTrue(
                            constraint.value.intValue() % 3 != 0 &&
                            constraint.value.intValue() % 5 != 0 );
                }
            }.doSwitch();
        }
    }

    /**
     * Diese Methode testet das Abweisen
     * einer {@link SwitchDefinition}, 
     * bei welcher die einzelnen Zweige
     * nicht disjoint sind.
     * 
     * @throws IOException rethrown
     */
// TODO Übungsaufgabe Stuttgarter Testtage 2013
//    @Test(expected = IllegalArgumentException.class)
//    public void testLackOfNotBuzzAndLackOfNotFizz()
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
//                "fizz_buzz" +
//                File.separator +
//                "src" , 
//                //packageName
//                "fizz_buzz" ,
//                //importStrArr ,
//                new String[]{ 
//                    "_06_switch_fizzbuzz.predicates.*" } ,
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
//                    or(
//                            or(
//                                    new Fizz() ,
//                                    not( new Fizz() ) ) ,
//                            or(
//                                    new Buzz() ,
//                                    not( new Buzz() ) ) ) ,
//                    switchDef( 
//                            "FizzBuzz" , 
//                            //caseMethodReturnTypeName
//                            "void" ,
//                            //caseMethodExceptionsStr
//                            null ,
//                            //switchPredicateArr
//                            and(
//                                    new Fizz() /*,
//                                    not( new Buzz() )*/ ) ,
//                            and(
//                                    new Buzz() /*,
//                                    not( new Fizz() )*/ ) ,
//                            and(
//                                    new Fizz() ,
//                                    new Buzz() ) /* TODO jetzt müsste ein default-Zweig generiert werden ,
//                            and(
//                                    not( new Fizz() ) ,
//                                    not( new Buzz() ) )*/ ) );
//        }}.execute();
//    }

    /**
     * Diese Methode testet das Abweisen
     * einer {@link SwitchDefinition}, 
     * bei welcher die einzelnen Zweige
     * nicht disjoint sind.
     * 
     * @throws IOException rethrown
     */
// TODO Übungsaufgabe Stuttgarter Testtage 2013    
//    @Test(expected = IllegalArgumentException.class)
//    public void testLackOfNotBuzz()
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
//                "fizz_buzz" +
//                File.separator +
//                "src" , 
//                //packageName
//                "fizz_buzz" ,
//                //importStrArr ,
//                new String[]{ 
//                    "_06_switch_fizzbuzz.predicates.*" } ,
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
//                    or(
//                            or(
//                                    new Fizz() ,
//                                    not( new Fizz() ) ) ,
//                            or(
//                                    new Buzz() ,
//                                    not( new Buzz() ) ) ) ,
//                    switchDef( 
//                            "FizzBuzz" , 
//                            //caseMethodReturnTypeName
//                            "void" ,
//                            //caseMethodExceptionsStr
//                            null ,
//                            //switchPredicateArr
//                            and(
//                                    new Fizz() /*,
//                                    not( new Buzz() )*/ ) ,
//                            and(
//                                    new Buzz() ,
//                                    not( new Fizz() ) ) ,
//                            and(
//                                    new Fizz() ,
//                                    new Buzz() ) /* TODO jetzt müsste ein default-Zweig generiert werden ,
//                            and(
//                                    not( new Fizz() ) ,
//                                    not( new Buzz() ) )*/ ) );
//        }}.execute();
//    }

    /**
     * Diese Methode testet das Abweisen
     * einer {@link SwitchDefinition}, 
     * bei welcher die einzelnen Zweige
     * nicht disjoint sind.
     * 
     * @throws IOException rethrown
     */
// TODO Übungsaufgabe Stuttgarter Testtage 2013
//    @Test(expected = IllegalArgumentException.class)
//    public void testLackOfNotFizz()
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
//                "fizz_buzz" +
//                File.separator +
//                "src" , 
//                //packageName
//                "fizz_buzz" ,
//                //importStrArr ,
//                new String[]{ 
//                    "_06_switch_fizzbuzz.predicates.*" } ,
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
//                    or(
//                            or(
//                                    new Fizz() ,
//                                    not( new Fizz() ) ) ,
//                            or(
//                                    new Buzz() ,
//                                    not( new Buzz() ) ) ) ,
//                    switchDef( 
//                            "FizzBuzz" , 
//                            //caseMethodReturnTypeName
//                            "void" ,
//                            //caseMethodExceptionsStr
//                            null ,
//                            //switchPredicateArr
//                            and(
//                                    new Fizz() ,
//                                    not( new Buzz() ) ) ,
//                            and(
//                                    new Buzz() /*,
//                                    not( new Fizz() )*/ ) ,
//                            and(
//                                    new Fizz() ,
//                                    new Buzz() ) /* TODO jetzt müsste ein default-Zweig generiert werden ,
//                            and(
//                                    not( new Fizz() ) ,
//                                    not( new Buzz() ) )*/ ) );
//        }}.execute();
//    }

}