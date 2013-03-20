package _10_transform_safeoperation_str_len;

import java.io.File;

import _10_transform_safeoperation_str_len.predicates.StrExactLen;
import _10_transform_safeoperation_str_len.predicates.StrFirstCharacter;
import _10_transform_safeoperation_str_len.predicates.StrFirstUppercaseLetter;
import _10_transform_safeoperation_str_len.predicates.StrIsNotNull;
import _10_transform_safeoperation_str_len.predicates.StrLastCharacter;
import _10_transform_safeoperation_str_len.predicates.StrLastDigit;
import _10_transform_safeoperation_str_len.predicates.StrMaxLen;
import _10_transform_safeoperation_str_len.predicates.StrMinLen;
import _10_transform_safeoperation_str_len.safeoperation.AddStrMaxLenSafeOperation;
import _10_transform_safeoperation_str_len.safeoperation.AddStrMinLenSafeOperation;
import _10_transform_safeoperation_str_len.safeoperation.AddStrMinMaxLenSafeOperation;
import _10_transform_safeoperation_str_len.safeoperation.SubstringFromPosToEndSafeOperation;
import _10_transform_safeoperation_str_len.safeoperation.SubstringFromPosToPosSafeOperation;
import _10_transform_safeoperation_str_len.safeoperation.SubstringFromStartToPosSafeOperation;
import de.heinerkuecker.constraint.AbstractConstraintCodeGenerator;
import de.heinerkuecker.constraint.AbstractSafeOperation;
import de.heinerkuecker.constraint.Exclude;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;
import de.heinerkuecker.constraint.util.coll.ReadOnlySet;

/**
 * Beispiel-Code-Generator f�r Strings mit maximalen und minimalen L�ngen
 * sowie sicheren Operationen.
 * 
 * @author Heiner K&uuml;cker
 */
public final class TransformSafeOperationStrLenConstraintCodeGenerator
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
                        // ein String, mit der maximalen L�nge von 10 Zeichen, m�glicherweise ist ein Datenbankfeld auf diese L�nge beschr�nkt
                        // dieser String ist mit dem nicht-null-String kompatibel, eine entsprechende convertTo-Methode muss generiert werden
                        // dieser String ist mit dem maximale-L�nge-20-String kompatibel sein, eine entsprechende convertTo-Methode muss generiert werden
                        new StrMaxLen( 10 ) );

                add(
                        // ein String, mit der maximalen L�nge von 20 Zeichen, m�glicherweise ist ein Datenbankfeld auf diese L�nge beschr�nkt
                        new StrMaxLen( 20 ) );

                add(
                        // ein String, mit der minimalen L�nge von 1 Zeichen
                        new StrMinLen(  1 ) );

                add(
                        // ein String, mit der minimalen L�nge von 10 Zeichen
                        new StrMinLen( 10 ) );
                

                add(
                        // ein String, mit der minimalen L�nge von 20 Zeichen
                        new StrMinLen( 20 ) );

                add(
                        // ein String, dessen erstes Zeichen ein Gro�buchstabe ist
                        // daf�r muss der String die minimalen L�nge von 1 Zeichen haben
                        new StrFirstUppercaseLetter() );

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
// TODO �bungsaufgabe Stuttgarter Testtage 2013
//                            // sichere Substring-Operation, daf�r ist eine minimale L�nge erforderlich
//                            // bei Substring ab Pos 0 soll das StrFirstUppercaseLetter-Constraint im Ergebnis-Constraint enthalten sein
//                            new SubstringFromPosToPosSafeOperation(
//                                    //fromPos
//                                    0 ,
//                                    //toPos
//                                    5 ) ,
//                            // sichere Substring-Operation, daf�r ist eine minimale L�nge erforderlich
//                            // bei Substring ab Pos 2 darf das StrFirstUppercaseLetter-Constraint nicht im Ergebnis-Constraint enthalten sein
//                            new SubstringFromPosToPosSafeOperation(
//                                    //fromPos
//                                    2 ,
//                                    //toPos
//                                    7 ) ,
//                            // sichere Substring-Operation, daf�r ist eine minimale L�nge erforderlich
//                            // bei SubstringFromPosToEndSafeOperation ab Pos 2 und StrMinLen > 2 soll das StrLastDigit-Constraint im Ergebnis-Constraint enthalten sein
//                            new SubstringFromPosToEndSafeOperation(
//                                    //fromPos
//                                    2 ) ,
//                            // sichere Substring-Operation, daf�r ist eine minimale L�nge erforderlich
//                            // bei Substring ab Pos 0 soll das StrFirstUppercaseLetter-Constraint im Ergebnis-Constraint enthalten sein
//                            new SubstringFromStartToPosSafeOperation(
//                                    //toPos
//                                    5 ) ,
//                            // sichere Add-Operation
//                            // das StrFirstUppercaseLetter-Constraint soll im Ergebnis-Constraint enthalten sein
//                            new AddStrMaxLenSafeOperation(
//                                    //strToAddMaxLen
//                                    12 ) ,
//                            // sichere Add-Operation, es entsteht eine neue Mindestl�nge
//                            // das StrFirstUppercaseLetter-Constraint soll im Ergebnis-Constraint enthalten sein
//                            new AddStrMinLenSafeOperation(
//                                    //strToAddMaxLen
//                                    13 ) 
                        }
                   );

                add(
                        and(
                                // ein String mit der minimalen L�nge von 10 Zeichen
                                new StrMinLen( 10 ) ,
                                // ein String mit der maximalen L�nge von 20 Zeichen
                                new StrMaxLen( 20 ) ) ,
                        new AbstractSafeOperation[]
                        {
// TODO �bungsaufgabe Stuttgarter Testtage 2013
//                            // sichere Add-Operation, es entsteht eine neue Maximall�nge
//                            new AddStrMaxLenSafeOperation(
//                                    //strToAddMaxLen
//                                    12 ) ,
//                            // sichere Add-Operation, es entsteht eine neue Mindestl�nge
//                            new AddStrMinLenSafeOperation(
//                                    //strToAddMaxLen
//                                    13 ) ,
//                            // sichere Add-Operation, es entsteht eine neue Mindestl�nge und Maximall�nge
//                            new AddStrMinMaxLenSafeOperation( 
//                                    //strToAddMinLen
//                                    15 ,
//                                    //strToAddMaxLen
//                                    17 ) 
                        }
                   );

                add(
                        // ein String, mit der exakten L�nge von 15 Zeichen
                        new StrExactLen(
                                //exactLen
                                15 ) );
                
                // ein etwas komplizierterer Fall, da der folgende String mit einem Buchstaben beginnen und mit einer Ziffer enden soll, muss er mindestens zwei Zeichen lang sein, dies wird in der Vererbungs-Methode AbstractConstraintCodeGenerator#checkConstraint abgesichert
                add(
                        and(
                                // ein String dessen erstes Zeichen ein Gro�buchstabe ist
                                new StrFirstUppercaseLetter() ,
                                // ein String dessen letztes Zeichen eine Ziffer ist
                                new StrLastDigit() ,
                                // ein String mit der minimalen L�nge von 10 Zeichen
                                new StrMinLen( 10 ) ) );
            }

            /**
             * Abweisen eines Constraints, 
             * bei welchem das erste und 
             * letzte Zeichen unterschiedlich ist,
             * aber keine Mindestl�nge von
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
        catch ( Exception e )
        {
            System.err.println( e.getMessage() );
            e.printStackTrace();
        }
    }

}