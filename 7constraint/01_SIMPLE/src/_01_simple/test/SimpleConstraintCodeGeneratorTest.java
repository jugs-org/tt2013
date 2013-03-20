package _01_simple.test;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import _01_simple.SimpleConstraintCodeGenerator;
import _01_simple.enums.Rolle;
import _01_simple.predicates.RolleZentrale;
import de.heinerkuecker.constraint.AbstractConstraintCodeGenerator;
import de.heinerkuecker.constraint.Exclude;

/**
 * JUnit4-Test für {@link SimpleConstraintCodeGenerator}.
 * 
 * @author Heiner K&uuml;cker
 */
public class SimpleConstraintCodeGeneratorTest
{
    /**
     * Test des Ablehnens eines nicht erfüllbaren
     * Constraints (Kontradiktion)
     * 
     * (A and not(A))
     * 
     * durch den Constraint-Code-Generator.
     */
    @Test(expected = IllegalStateException.class)
    public void testSelfNotContradiction()
    throws IOException
    {
        new AbstractConstraintCodeGenerator<Rolle>(
                //cacheFolder
                null ,
                //srcDirPathStr
                System.getProperty(
                        "user.dir" ) +
                File.separator +
                "src" , 
                //packageName
                "_01_simple.constraints" ,
                //importStrArr ,
                new String[]{ 
                    "_01_simple.enums.*" ,
                    "_01_simple.predicates.*" } ,
                //contextObjClassName
                "Rolle" , 
                //contextObjName
                "rolle" ,
                //writeToDisc
                false ,
                //deleteUnusedConstraintJavaFiles
                false , 
                //exclude
                new Exclude<Rolle>() )
        {{
            // TODO nicht erfüllbares Constraint
            add(
                    and(
                            new RolleZentrale() ,
                            not(
                                    new RolleZentrale() ) ) );
            
            // TODO nicht erfüllbares Constraint
            //add(
            //        xor(
            //                new RolleZentrale() ,
            //                new RolleZentrale() ) );

        }}.execute();
    }

    /**
     * Test des Ablehnens eines nicht erfüllbaren
     * Constraints (Kontradiktion)
     * 
     * (A xor A)
     * 
     * durch den Constraint-Code-Generator.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSelfXorContradiction()
    throws IOException
    {
        new AbstractConstraintCodeGenerator<Rolle>(
                //cacheFolder
                null ,
                //srcDirPathStr
                System.getProperty(
                        "user.dir" ) +
                File.separator +
                "src" , 
                //packageName
                "_01_simple.constraints" ,
                //importStrArr ,
                new String[]{ 
                    "_01_simple.enums.*" ,
                    "_01_simple.predicates.*" } ,
                //contextObjClassName
                "Rolle" , 
                //contextObjName
                "rolle" ,
                //writeToDisc
                false ,
                //deleteUnusedConstraintJavaFiles
                false , 
                //exclude
                new Exclude<Rolle>() )
        {{
            // nicht erfüllbares Constraint
            add(
                    xor(
                            new RolleZentrale() ,
                            new RolleZentrale() ) );

        }}.execute();
    }

}