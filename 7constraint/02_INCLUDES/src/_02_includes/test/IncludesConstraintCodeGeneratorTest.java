package _02_includes.test;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import _02_includes.IncludesConstraintCodeGenerator;
import _02_includes.enums.Rolle;
import _02_includes.predicates.RolleNiederlassung;
import _02_includes.predicates.RolleNiederlassungNord;
import _02_includes.predicates.RolleZentrale;
import de.heinerkuecker.constraint.AbstractConstraintCodeGenerator;
import de.heinerkuecker.constraint.And;
import de.heinerkuecker.constraint.Exclude;

/**
 * JUnit4-Test für {@link IncludesConstraintCodeGenerator}.
 * 
 * @author Heiner K&uuml;cker
 */
public class IncludesConstraintCodeGeneratorTest
{
    /**
     * Test des Ablehnens eines nicht erfüllbaren
     * Constraints (Kontradiktion)
     * 
     * (A and not(A))
     * 
     * durch den Constraint-Code-Generator.
     * 
     * @throws IOException rethrown
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
                "_02_includes.constraints" ,
                //importStrArr ,
                new String[]{ 
                    "_02_includes.enums.*" ,
                    "_02_includes.predicates.*" } ,
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
            
        }}.execute();
    }

    /**
     * Test des Ablehnens eines nicht erfüllbaren
     * Constraints (Kontradiktion)
     * 
     * (A xor A)
     * 
     * durch den Constraint-Code-Generator.
     * 
     * @throws IOException rethrown
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
                "_02_includes.constraints" ,
                //importStrArr ,
                new String[]{ 
                    "_02_includes.enums.*" ,
                    "_02_includes.predicates.*" } ,
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

    ///**
    // * Test des Ablehnens eines durch
    // * Absorption includierter Prädikate
    // * entstandenen gleichen Constraints:
    // * durch den Constraint-Code-Generator.
    // * 
    // * TODO das Absorbieren includierter Prädikate erfolgte über den Parameter cleanForImplicits der Methode {@link And#instanceOf}, ist entfallen
    // * 
    // * @throws IOException rethrown
    // */
    //@Test(expected = IllegalStateException.class)
    //public void testEqualConstraintBecauseAbsorpInclude()
    //throws IOException
    //{
    //    new AbstractConstraintCodeGenerator<Rolle>(
    //            //cacheFolder
    //            null ,
    //            //srcDirPathStr
    //            System.getProperty(
    //                    "user.dir" ) +
    //            File.separator +
    //            "src" , 
    //            //packageName
    //            "_02_includes.constraints" ,
    //            //importStrArr ,
    //            new String[]{ 
    //                "_02_includes.enums.*" ,
    //                "_02_includes.predicates.*" } ,
    //            //contextObjClassName
    //            "Rolle" , 
    //            //contextObjName
    //            "rolle" ,
    //            //writeToDisc
    //            false ,
    //            //deleteUnusedConstraintJavaFiles
    //            false , 
    //            //exclude
    //            new Exclude<Rolle>() )
    //    {{
    //        add(
    //                new RolleNiederlassungNord() );
    //        
    //        // ein äquivalentes Constraint entsteht durch Absorbieren includierter Constraints
    //        add(
    //                and(
    //                        new RolleNiederlassung() ,
    //                        new RolleNiederlassungNord() ) );
    //    }}.execute();
    //}

}