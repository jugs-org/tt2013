package _09_new_safeoperation_str_len;

import java.io.File;

import _09_new_safeoperation_str_len.predicates.StrFirstUppercaseLetter;
import _09_new_safeoperation_str_len.predicates.StrIsNotNull;
import _09_new_safeoperation_str_len.predicates.StrLastDigit;
import _09_new_safeoperation_str_len.predicates.StrMaxLen;
import _09_new_safeoperation_str_len.predicates.StrMinLen;
import _09_new_safeoperation_str_len.safeoperation.SubstringFromPosToEndSafeOperation;
import _09_new_safeoperation_str_len.safeoperation.SubstringFromPosToPosSafeOperation;
import _09_new_safeoperation_str_len.safeoperation.SubstringFromStartToPosSafeOperation;
import de.heinerkuecker.constraint.AbstractConstraintCodeGenerator;
import de.heinerkuecker.constraint.AbstractSafeOperation;
import de.heinerkuecker.constraint.Exclude;

/**
 * Beispiel-Code-Generator für Strings mit maximalen und minimalen Längen
 * sowie sicheren Operationen.
 * 
 * @author Heiner K&uuml;cker
 */
public final class NewSafeOperationStrLenConstraintCodeGenerator
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
                    "_09_new_safeoperation_str_len.constraints" ,
                    //importStrArr ,
                    new String[]{
                        "_09_new_safeoperation_str_len.predicates.*" ,
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
                        new StrIsNotNull() );

                add(
                        // ein String, mit der maximalen Länge von 10 Zeichen, möglicherweise ist ein Datenbankfeld auf diese Länge beschränkt
                        // dieser String ist mit dem nicht-null-String kompatibel, eine entsprechende convertTo-Methode muss generiert werden
                        // dieser String ist mit dem maximale-Länge-20-String kompatibel sein, eine entsprechende convertTo-Methode muss generiert werden
                        new StrMaxLen( 10 ) );

                add(
                        // ein String, mit der maximalen Länge von 20 Zeichen, möglicherweise ist ein Datenbankfeld auf diese Länge beschränkt
                        new StrMaxLen( 20 ) );

                add(
                        // ein String, mit der minimalen Länge von 1 Zeichen
                        new StrMinLen(  1 ) );

                add(
                        // ein String, mit der minimalen Länge von 10 Zeichen
                        new StrMinLen( 10 ) );
                

                add(
                        // ein String, mit der minimalen Länge von 20 Zeichen
                        new StrMinLen( 20 ) );

                add(
                        // ein String, dessen erstes Zeichen ein Großbuchstabe ist
                        // dafür muss der String die minimale Länge von 1 Zeichen haben
                        new StrFirstUppercaseLetter() );

                add(
                        and(
                                // ein String mit der minimalen Länge von 10 Zeichen
                                new StrMinLen( 10 ) ,
                                // und dessen erstes Zeichen ein Großbuchstabe ist
                                new StrFirstUppercaseLetter() ,
                                // ein String dessen letztes Zeichen eine Ziffer ist
                                new StrLastDigit() ) ,
                        new AbstractSafeOperation[]
                        {
// TODO Übungsaufgabe Stuttgarter Testtage 2013
//                            // sichere Substring-Operation, dafür ist eine minimale Länge erforderlich
//                            // bei Substring ab Pos 0 soll das StrFirstUppercaseLetter-Constraint im Ergebnis-Constraint enthalten sein
//                            new SubstringFromPosToPosSafeOperation(
//                                    //fromPos
//                                    0 ,
//                                    //toPos
//                                    5 ) ,
//                            // sichere Substring-Operation, dafür ist eine minimale Länge erforderlich
//                            // bei Substring ab Pos 2 darf das StrFirstUppercaseLetter-Constraint nicht im Ergebnis-Constraint enthalten sein
//                            new SubstringFromPosToPosSafeOperation(
//                                    //fromPos
//                                    2 ,
//                                    //toPos
//                                    7 ) ,
//                            // sichere Substring-Operation, dafür ist eine minimale Länge erforderlich
//                            // bei SubstringFromPosToEndSafeOperation ab Pos 2 und StrMinLen > 2 soll das StrLastDigit-Constraint im Ergebnis-Constraint enthalten sein
//                            new SubstringFromPosToEndSafeOperation(
//                                    //fromPos
//                                    2 ) ,
//                            // sichere Substring-Operation, dafür ist eine minimale Länge erforderlich
//                            new SubstringFromStartToPosSafeOperation(
//                                    //toPos
//                                    5 )
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