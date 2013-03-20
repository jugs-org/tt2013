package _07_int_range;

import java.io.File;

import _07_int_range.predicates.IntEqual;
import _07_int_range.predicates.IntGreater;
import _07_int_range.predicates.IntLesser;
import de.heinerkuecker.constraint.AbstractConstraintCodeGenerator;
import de.heinerkuecker.constraint.Exclude;
import de.heinerkuecker.constraint.ExcludeGroup;

/**
 * Testfall zur Generierung von Constraint-Klassen
 * mit einem Range-Check auf einen Integer-Wert.
 * <pre>
 * ürsprüngliches Constraint: x &gt; -10 && x &lt; 10
 * 
 * Switch:
 *    Case x   &lt;  0
 *    Case x  ==  0
 *    Case x   &gt;  0
 * </pre>
 * 
 * @author Heiner K&uuml;cker
 */
public final class IntRangeConstraintCodeGenerator
{
    /**
     * sich ausschliessende Prädikate
     * 
     * TODO der gegenseitige Ausschluss von Werten kleiner-als, gleich und grösser-als sollte über den Generator gelöst werden, nicht im Anwendungscode, aber das Prädikat kann nicht wissen, ob in der Constraint-Expression kein Null-Wert erlaubt ist und somit die closed world assumption gilt
     */
    private static final Exclude<Integer> exclude =
            new Exclude<Integer>(
                    new ExcludeGroup<Integer>(
                            "IntLesserOrGreaterOrEqualZero" ,
                            //isClosedWorldAssumption
                            true ,
                            new IntLesser ( 0 ) , // x  < 0
                            new IntEqual  ( 0 ) , // x == 0
                            new IntGreater( 0 )   // x  > 0
                            ) );

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
            new AbstractConstraintCodeGenerator<Integer>(
                    //cacheFolder
                    new File(
                            "C:\\tmp" ) ,
                    //srcDirPathStr
                    System.getProperty(
                            "user.dir" ) +
                    File.separator +
                    "src" , 
                    //packageName
                    "_07_int_range.constraints" ,
                    //importStrArr ,
                    new String[]{ 
                        "_07_int_range.predicates.*" } ,
                    //contextObjClassName
                    "Integer" , 
                    //contextObjName
                    "value" ,
                    //writeToDisc
                    true ,
                    //deleteUnusedConstraintJavaFiles
                    true , 
                    exclude )
            {{
// TODO Übungsaufgabe Stuttgarter Testtage 2013                
//                add(
//                        and(
//                                new IntGreater( -10 ) , // x > -10
//                                new IntLesser (  10 )   // x <  10
//                                ) ,
//                        switchDef( 
//                                "LesserZeroEqualZeroGreaterZero" , 
//                                //caseMethodReturnTypeName
//                                "void" ,
//                                //caseMethodExceptionsStr
//                                null ,
//                                //switchPredicateArr
//                                new IntLesser ( 0 ) , // x  < 0
//                                new IntEqual  ( 0 ) , // x == 0
//                                new IntGreater( 0 )   // x  > 0
//                                ) );
            }}.execute();
        }
        catch ( Exception e )
        {
            System.err.println( e.getMessage() );
            e.printStackTrace();
        }
    }

}