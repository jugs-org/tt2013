package _08_value_safeoperation_str_len;

import java.io.File;

import _08_value_safeoperation_str_len.predicates.StrExactLen;
import _08_value_safeoperation_str_len.predicates.StrFirstUppercaseLetter;
import _08_value_safeoperation_str_len.predicates.StrHasDigits;
import _08_value_safeoperation_str_len.predicates.StrIsNotNull;
import _08_value_safeoperation_str_len.predicates.StrLastDigit;
import _08_value_safeoperation_str_len.predicates.StrMaxLen;
import _08_value_safeoperation_str_len.predicates.StrMinLen;
import _08_value_safeoperation_str_len.safeoperation.StrDigitCountSafeOperation;
import _08_value_safeoperation_str_len.safeoperation.StrLenSafeOperation;
import de.heinerkuecker.constraint.AbstractConstraintCodeGenerator;
import de.heinerkuecker.constraint.AbstractSafeOperation;
import de.heinerkuecker.constraint.Exclude;

/**
 * Beispiel-Code-Generator f�r Strings mit maximalen und minimalen L�ngen
 * sowie sicheren Operationen.
 * 
 * @author Heiner K&uuml;cker
 */
public final class ValueSafeOperationStrLenConstraintCodeGenerator
{
    /**
     * Start Generierung.
     * 
     * @param args unused
     */
    public static void main(
            final String [] args )
    {
        try
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
                    true ,
                    //deleteUnusedConstraintJavaFiles
                    true , 
                    //exclude
                    new Exclude<String>() )
            {{
                add(
                        // ein String, der nicht null ist
                        new StrIsNotNull() ,
                        new AbstractSafeOperation[]
                        {
                            // sichere Operation zur Ermittlung der L�nge des Strings, daf�r darf der String nicht null sein
                            // TODO �bungsaufgabe Stuttgarter Testtage 2013: new StrLenSafeOperation()
                        } );

                add(
                        // ein String, mit der maximalen L�nge von 10 Zeichen, m�glicherweise ist ein Datenbankfeld auf diese L�nge beschr�nkt
                        // dieser String ist mit dem nicht-null-String kompatibel, eine entsprechende convertTo-Methode muss generiert werden
                        // dieser String ist mit dem maximale-L�nge-20-String kompatibel sein, eine entsprechende convertTo-Methode muss generiert werden
                        new StrMaxLen( 10 ) ,
                        new AbstractSafeOperation[]
                        {
                            // sichere Operation zur Ermittlung der L�nge des Strings, daf�r darf der String nicht null sein
                            new StrLenSafeOperation()
                        } );

                add(
                        // ein String, mit der maximalen L�nge von 20 Zeichen, m�glicherweise ist ein Datenbankfeld auf diese L�nge beschr�nkt
                        new StrMaxLen( 20 ) ,
                        new AbstractSafeOperation[]
                        {
                            // sichere Operation zur Ermittlung der L�nge des Strings, daf�r darf der String nicht null sein
                            new StrLenSafeOperation()
                        } );

                add(
                        // ein String, mit der minimalen L�nge von 1 Zeichen
                        new StrMinLen(  1 ) ,
                        new AbstractSafeOperation[]
                        {
                            // sichere Operation zur Ermittlung der L�nge des Strings, daf�r darf der String nicht null sein
                            new StrLenSafeOperation()
                        } );

                add(
                        // ein String, mit der minimalen L�nge von 10 Zeichen
                        new StrMinLen( 10 ) ,
                        new AbstractSafeOperation[]
                        {
                            // sichere Operation zur Ermittlung der L�nge des Strings, daf�r darf der String nicht null sein
                            new StrLenSafeOperation()
                        } );

                add(
                        // ein String, mit der minimalen L�nge von 20 Zeichen
                        new StrMinLen( 20 ) ,
                        new AbstractSafeOperation[]
                        {
                            // sichere Operation zur Ermittlung der L�nge des Strings, daf�r darf der String nicht null sein
                            new StrLenSafeOperation()
                        } );

                add(
                        // ein String, dessen erstes Zeichen ein Gro�buchstabe ist
                        // daf�r muss der String die minimalen L�nge von 1 Zeichen haben
                        new StrFirstUppercaseLetter() ,
                        new AbstractSafeOperation[]
                        {
                            // sichere Operation zur Ermittlung der L�nge des Strings, daf�r darf der String nicht null sein
                            new StrLenSafeOperation()
                        } );

                add(
                        and(
                                // ein String mit der minimalen L�nge von 10 Zeichen
                                new StrMinLen( 10 ) ,
                                // und dessen erstes Zeichen ein Gro�buchstabe ist
                                new StrFirstUppercaseLetter() ,
                                // ein String dessen letztes Zeichen eine Ziffer ist
                                new StrLastDigit() ) ,
                        new AbstractSafeOperation[]
                        {
                            // sichere Operation zur Ermittlung der L�nge des Strings, daf�r darf der String nicht null sein
                            new StrLenSafeOperation()
                        } );

                add(
                        // ein String, mit der exakten L�nge von 15 Zeichen
                        new StrExactLen(
                                //exactLen
                                15 ) ,
                        new AbstractSafeOperation[]
                        {
                            // sichere Operation zur Ermittlung der L�nge des Strings, daf�r darf der String nicht null sein
                            new StrLenSafeOperation()
                        } );
                
                add(
                        // ein String, welcher mindestens eine Ziffer enth�lt
                        new StrHasDigits() ,
                        new AbstractSafeOperation[]
                        {
                            // sichere Operation zur Ermittlung der Anzahl Ziffern im String, daf�r darf der String nicht null sein
                            new StrDigitCountSafeOperation()
                        } );

            }}.execute();
        }
        catch ( Exception e )
        {
            System.err.println( e.getMessage() );
            e.printStackTrace();
        }
    }

}