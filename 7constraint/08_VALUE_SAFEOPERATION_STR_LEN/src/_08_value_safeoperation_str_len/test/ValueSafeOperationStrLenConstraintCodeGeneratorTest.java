package _08_value_safeoperation_str_len.test;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import _08_value_safeoperation_str_len.ValueSafeOperationStrLenConstraintCodeGenerator;
import _08_value_safeoperation_str_len.constraints.ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE;
import _08_value_safeoperation_str_len.constraints.StrHasDigitsConstraint;
import _08_value_safeoperation_str_len.constraints.StrIsNotNullConstraint;
import _08_value_safeoperation_str_len.predicates.StrIsNotNull;
import _08_value_safeoperation_str_len.safeoperation.StrDigitCountSafeOperation;
import de.heinerkuecker.constraint.AbstractConstraintCodeGenerator;
import de.heinerkuecker.constraint.AbstractSafeOperation;
import de.heinerkuecker.constraint.Exclude;
import de.heinerkuecker.constraint.ValueSafeOperation;

/**
 * JUnit4-Testcase für Constraints, welche
 * durch {@link ValueSafeOperationStrLenConstraintCodeGenerator}
 * generiert wurden.
 * 
 * @author Heiner K&uuml;cker
 */
public class ValueSafeOperationStrLenConstraintCodeGeneratorTest
{
// TODO Übungsaufgabe Stuttgarter Testtage 2013
//    @Test
//    public void testStrIsNotNull()
//    {
//        final StrIsNotNullConstraint constraint =
//                new StrIsNotNullConstraint(
//                        "" );
//
//        Assert.assertEquals(
//                0 ,
//                constraint.length() );
//    }

    @Test
    public void testANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE_length10()
    {
        final ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE constraint =
                new ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE(
                        "A123456789" );

        Assert.assertEquals(
                10 ,
                constraint.length() );
    }

    @Test
    public void testANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE_length11()
    {
        final ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE constraint =
                new ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE(
                        "A1234567890" );

        Assert.assertEquals(
                11 ,
                constraint.length() );
    }

    @Test
    public void testANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE_length12()
    {
        final ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE constraint =
                new ANDB_StrMinLen10_StrFirstUppercaseLetter_StrLastDigit_ANDE(
                        "A12345678901" );

        Assert.assertEquals(
                12 ,
                constraint.length() );
    }
    
    @Test
    public void testStrDigitCountSafeOperation()
    {
        final StrHasDigitsConstraint constraint =
                new StrHasDigitsConstraint(
                        "123A" );
        
        Assert.assertEquals(
                3 ,
                constraint.countOfDigits() );
    }
    
    /**
     * Diese Methode testet das Abweisen
     * einer {@link ValueSafeOperation}, 
     * bei welcher die Voraussetzung
     * (premise) nicht erfüllt ist.
     * 
     * @throws IOException rethrown
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDeniedOfGenerationSafeOperationWithoutPremise()
    throws IOException
    {
        new AbstractConstraintCodeGenerator<String>(
                //cacheFolder
                new File(
                        "C:\\tmp" ) ,
                //srcDirPathStr
                System.getProperty(
                        "user.dir" ) +
                File.separator +
                "src" , 
                //packageName
                "_08_value_safeoperation_str_len.constraints" ,
                //importStrArr ,
                new String[]{
                    "_08_value_safeoperation_str_len.predicates.*" ,
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
                    // ein String, der nicht null ist, korrekterweise wird hier ein String, welcher mindestens eine Ziffer enthält, erwartet
                    new StrIsNotNull() ,
                    new AbstractSafeOperation[]
                    {
                        // sichere Operation zur Ermittlung der Anzahl Ziffern im String, dafür darf der String nicht null sein
                        new StrDigitCountSafeOperation()
                    } );
        }}.execute();
    }

}