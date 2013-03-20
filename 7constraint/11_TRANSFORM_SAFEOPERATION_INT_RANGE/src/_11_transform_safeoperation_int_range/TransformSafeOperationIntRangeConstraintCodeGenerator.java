package _11_transform_safeoperation_int_range;

import java.io.File;

import _11_transform_safeoperation_int_range.predicates.IntEqual;
import _11_transform_safeoperation_int_range.predicates.IntGreater;
import _11_transform_safeoperation_int_range.predicates.IntLesser;
import _11_transform_safeoperation_int_range.safeoperation.AddIntSafeOperation;
import de.heinerkuecker.constraint.AbstractConstraintCodeGenerator;
import de.heinerkuecker.constraint.AbstractSafeOperation;
import de.heinerkuecker.constraint.Exclude;
import de.heinerkuecker.constraint.ExcludeGroup;

/**
 * Testfall zur Generierung von Constraint-Klassen
 * mit einem Range-Check auf einen Integer-Wert.
 * <pre>
 * ürsprüngliches Constraint: x &gt; 0 || x == 0
 * </pre>
 * und einer sicheren Operation 
 * {@link AddIntSafeOperation}
 * zum Addieren eines konstanten Wertes.
 * 
 * @author Heiner K&uuml;cker
 */
public final class TransformSafeOperationIntRangeConstraintCodeGenerator
{
    /**
     * sich ausschliessende Prädikate
     * 
     * TODO der gegenseitige Ausschluss von Werten kleiner-als, gleich und grösser-als sollte über den Generator gelöst werden, nicht im Anwendungscode
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
                    "_11_transform_safeoperation_int_range.constraints" ,
                    //importStrArr ,
                    new String[]{ 
                        "_11_transform_safeoperation_int_range.predicates.*" } ,
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
                add(
                        or(
                                new IntGreater( 0 ) , // x > 0
                                new IntEqual  ( 0 )   // x == 0
                                ) ,
                        new AbstractSafeOperation[]
                        {
                            // TODO Übungsaufgabe Stuttgarter Testtage 2013: new AddIntSafeOperation( 1 )
                        } ,
                        switchDef( 
                                "EqualZeroGreaterZero" , 
                                //caseMethodReturnTypeName
                                "void" ,
                                //caseMethodExceptionsStr
                                null ,
                                //switchPredicateArr
                                new IntEqual  ( 0 ) , // x == 0
                                new IntGreater( 0 )   // x  > 0
                                ) );
            }}.execute();
        }
        catch ( Exception e )
        {
            System.err.println( e.getMessage() );
            e.printStackTrace();
        }
    }

}