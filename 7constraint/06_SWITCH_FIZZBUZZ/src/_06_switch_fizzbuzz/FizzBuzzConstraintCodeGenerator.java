package _06_switch_fizzbuzz;

import java.io.File;

import _06_switch_fizzbuzz.predicates.Buzz;
import _06_switch_fizzbuzz.predicates.Fizz;
import _06_switch_fizzbuzz.test.FizzBuzzConstraintCodeGeneratorTest;
import de.heinerkuecker.constraint.AbstractConstraintCodeGenerator;
import de.heinerkuecker.constraint.Exclude;

/**
 * Dieser Constraint-Code-Generator
 * demonstriert die Vermeidung folgenden
 * angenommen Programmfehlers:<br/><br/>
 * 
 * Es soll das Spiel Fizz Buzz umgesetzt werden.<br/>
 * Dazu wird von 1 bis 100 gezählt.<br/>
 * Wenn die auftauchende Zahl ohne Rest durch 3 teilbar ist, wird 'Buzz' ausgegeben.<br/>
 * Wenn die auftauchende Zahl ohne Rest durch 5 teilbar ist, wird 'Fizz' ausgegeben.<br/>
 * Wenn die auftauchende Zahl ohne Rest durch 15 teilbar ist, wird 'FizzBuzz' ausgegeben.<br/>
 * Folgender Code wird geschrieben:
 * <pre>
 * for ( int i = 1 ; i &lt;= 100 ; i++ )
 * {
 *     if ( i % 3 == 0 )
 *     {
 *         System.out.println( "Fizz" );
 *     }
 *     else if ( i % 5 == 0 )
 *     {
 *         System.out.println( "Buzz" );
 *     }
 *     else if ( i % 15 == 0 )
 *     {
 *         System.out.println( "FizzBuzz" );
 *     }
 *     else
 *     {
 *         System.out.println( i );
 *     }
 * }
 * </pre>
 * Der dritte Zweig im if-else-Verteiler wird nie erreicht,
 * weil vorher schon der erste Zweig ausgeführt wurde.<br/>
 * Der dritte Zweig im if-else-Verteiler ist unreacheable.<br/>
 * In diesem Beispiel soll gezeigt werden, dass dieser
 * Programmierfehler durch den ConstraintCodeGenerator
 * verhindert werden kann.<br/>
 * Korrekt wäre (jetzt mal abgesehen davon, dass man beim
 * zweiten Zeig einfach das else weglassen und dann den
 * dritten Zweig einfach weglassen könnte).
 * <pre>
 * for ( int i = 1 ; i &lt;= 100 ; i++ )
 * {
 *     if ( i % 3 == 0 &&
 *          ! ( i % 5 == 0 ) )
 *     {
 *         System.out.println( "Fizz" );
 *     }
 *     else if ( i % 5 == 0 &&
 *          ! ( i % 3 == 0 ) )
 *     {
 *         System.out.println( "Buzz" );
 *     }
 *     else if ( i % 15 == 0 )
 *     {
 *         System.out.println( "FizzBuzz" );
 *     }
 *     else if ( ! ( i % 3 == 0 ) &&
 *               ! ( i % 5 == 0 ) )
 *     {
 *         System.out.println( i );
 *     }
 *     else
 *     {
 *         throw new IllegalStateException();
 *     }
 * }
 * </pre>
 * Siehe hierzu bitte die entsprechenden JUnit4-Tests
 * {@link FizzBuzzConstraintCodeGeneratorTest}.
 * 
 * @author Heiner K&uuml;cker
 */
public final class FizzBuzzConstraintCodeGenerator
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
                    "_06_switch_fizzbuzz.constraints" ,
                    //importStrArr ,
                    new String[]{ 
                        "_06_switch_fizzbuzz.predicates.*" } ,
                    //contextObjClassName
                    "Integer" , 
                    //contextObjName
                    "value" ,
                    //writeToDisc
                    true ,
                    //deleteUnusedConstraintJavaFiles
                    true , 
                    //exclude
                    new Exclude<Integer>() )
            {{
                add(
                        or(
                                or(
                                        new Fizz() ,
                                        not( new Fizz() ) ) ,
                                or(
                                        new Buzz() ,
                                        not( new Buzz() ) ) )
// TODO Übungsaufgabe Stuttgarter Testtage 2013
//                        switchDef( 
//                                "FizzBuzz" , 
//                                //caseMethodReturnTypeName
//                                "void" ,
//                                //caseMethodExceptionsStr
//                                null ,
//                                //switchPredicateArr
//                                and(
//                                        // ganzzahlig teilbar durch 3, nicht ganzzahlig teilbar durch 5
//                                        new Fizz() ,
//                                        not( new Buzz() ) ) ,
//                                and(
//                                        // ganzzahlig teilbar durch 5, nicht ganzzahlig teilbar durch 3
//                                        new Buzz() ,
//                                        not( new Fizz() ) ) ,
//                                and(
//                                        // ganzzahlig teilbar durch 3 und ganzzahlig teilbar durch 5
//                                        new Fizz() ,
//                                        new Buzz() ) /* TODO jetzt müsste ein default-Zweig generiert werden ,
//                                and(
//                                        // nicht ganzzahlig teilbar durch 3 und nicht ganzzahlig teilbar durch 5
//                                        not( new Fizz() ) ,
//                                        not( new Buzz() ) )*/ )
                        );
            }}.execute();
        }
        catch ( Exception e )
        {
            System.err.println( e.getMessage() );
            e.printStackTrace();
        }
    }

}