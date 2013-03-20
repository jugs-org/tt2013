package _03_excludes.test;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import _03_excludes.ExcludesConstraintCodeGenerator;
import _03_excludes.enums.Rolle;
import _03_excludes.predicates.RolleEbeneFiliale;
import _03_excludes.predicates.RolleEbeneNiederlassung;
import _03_excludes.predicates.RolleEbeneZentrale;
import _03_excludes.predicates.RolleFiliale;
import _03_excludes.predicates.RolleFilialeNord;
import _03_excludes.predicates.RolleFilialeSued;
import _03_excludes.predicates.RolleNiederlassung;
import _03_excludes.predicates.RolleRegionNord;
import _03_excludes.predicates.RolleRegionOst;
import _03_excludes.predicates.RolleRegionSued;
import _03_excludes.predicates.RolleRegionWest;
import _03_excludes.predicates.RolleZentrale;
import de.heinerkuecker.constraint.AbstractConstraintCodeGenerator;
import de.heinerkuecker.constraint.Exclude;
import de.heinerkuecker.constraint.ExcludeGroup;

/**
 * JUnit4-Test für {@link ExcludesConstraintCodeGenerator}.
 * 
 * @author Heiner K&uuml;cker
 */
public class ExcludesConstraintCodeGeneratorTest
{
    /**
     * sich ausschliessende Prädikate
     */
    private static final Exclude<Rolle> exclude =
            new Exclude<Rolle>(
                    new ExcludeGroup<Rolle>(
                            "Ebene" ,
                            //isClosedWorldAssumption
                            true ,
                            new RolleEbeneZentrale() ,
                            new RolleEbeneNiederlassung() ,
                            new RolleEbeneFiliale() ) ,
                    new ExcludeGroup<Rolle>(
                            "Region" ,
                            //isClosedWorldAssumption
                            true ,
                            new RolleRegionNord() ,
                            new RolleRegionSued() ,
                            new RolleRegionWest() ,
                            new RolleRegionOst() ) );
    
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
                "_03_excludes.constraints" ,
                //importStrArr ,
                new String[]{ 
                    "_03_excludes.enums.*" ,
                    "_03_excludes.predicates.*" } ,
                //contextObjClassName
                "Rolle" , 
                //contextObjName
                "rolle" ,
                //writeToDisc
                false ,
                //deleteUnusedConstraintJavaFiles
                false , 
                exclude )
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
                "_03_excludes.constraints" ,
                //importStrArr ,
                new String[]{ 
                    "_03_excludes.enums.*" ,
                    "_03_excludes.predicates.*" } ,
                //contextObjClassName
                "Rolle" , 
                //contextObjName
                "rolle" ,
                //writeToDisc
                false ,
                //deleteUnusedConstraintJavaFiles
                false , 
                exclude )
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
    //            "_03_excludes.constraints" ,
    //            //importStrArr ,
    //            new String[]{ 
    //                "_03_excludes.enums.*" ,
    //                "_03_excludes.predicates.*" } ,
    //            //contextObjClassName
    //            "Rolle" , 
    //            //contextObjName
    //            "rolle" ,
    //            //writeToDisc
    //            false ,
    //            //deleteUnusedConstraintJavaFiles
    //            false , 
    //            exclude )
    //    {{
    //        add(
    //                new RolleNiederlassungNord() );
    //        
    //        // eine äquivalentes Constraint entsteht durch Absorbieren includierter Constraints
    //        add(
    //                and(
    //                        new RolleNiederlassung() ,
    //                        new RolleNiederlassungNord() ) );
    //    }}.execute();
    //}

    /**
     * Test des Ablehnens eines durch
     * excludierte Prädikate nicht
     * erfüllbaren Constraints (Kontradiktion)
     * 
     * Exclude: A, B
     * 
     * (A and B)
     * 
     * durch den Constraint-Code-Generator.
     * 
     * @throws IOException rethrown
     */
    @Test(expected = IllegalStateException.class)
    public void testExcludeContradictionEbene()
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
                "_03_excludes.constraints" ,
                //importStrArr ,
                new String[]{ 
                    "_03_excludes.enums.*" ,
                    "_03_excludes.predicates.*" } ,
                //contextObjClassName
                "Rolle" , 
                //contextObjName
                "rolle" ,
                //writeToDisc
                false ,
                //deleteUnusedConstraintJavaFiles
                false , 
                exclude )
        {{
            // durch excludierte Prädikate nicht erfüllbares Constraint
            add(
                    and(
                            new RolleEbeneZentrale() ,
                            new RolleEbeneNiederlassung() ) );
        }}.execute();
    }

    /**
     * Test des Ablehnens eines durch
     * excludierte Prädikate nicht
     * erfüllbaren Constraints (Kontradiktion)
     * 
     * Exclude: A, B
     * 
     * (A and B)
     * 
     * durch den Constraint-Code-Generator.
     * 
     * @throws IOException rethrown
     */
    @Test(expected = IllegalStateException.class)
    public void testExcludeContradictionRegion()
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
                "_03_excludes.constraints" ,
                //importStrArr ,
                new String[]{ 
                    "_03_excludes.enums.*" ,
                    "_03_excludes.predicates.*" } ,
                //contextObjClassName
                "Rolle" , 
                //contextObjName
                "rolle" ,
                //writeToDisc
                false ,
                //deleteUnusedConstraintJavaFiles
                false , 
                exclude )
        {{
            // durch excludierte Prädikate nicht erfüllbares Constraint
            add(
                    and(
                            new RolleRegionNord() ,
                            new RolleRegionSued() ) );
        }}.execute();
    }

    /**
     * Test des Ablehnens eines durch
     * excludierte Prädikate nicht
     * erfüllbaren Constraints (Kontradiktion)
     * 
     * Exclude: A, B
     * 
     * (A and B)
     * 
     * durch den Constraint-Code-Generator.
     * 
     * @throws IOException rethrown
     */
    @Test(expected = IllegalStateException.class)
    public void testExcludeContradictionEbeneOverInclude()
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
                "_03_excludes.constraints" ,
                //importStrArr ,
                new String[]{ 
                    "_03_excludes.enums.*" ,
                    "_03_excludes.predicates.*" } ,
                //contextObjClassName
                "Rolle" , 
                //contextObjName
                "rolle" ,
                //writeToDisc
                false ,
                //deleteUnusedConstraintJavaFiles
                false , 
                exclude )
        {{
            // durch excludierte Prädikate nicht erfüllbares Constraint
            add(
                    and(
                            new RolleZentrale() ,
                            new RolleEbeneNiederlassung() ) );
        }}.execute();
    }

    /**
     * Test des Ablehnens eines durch
     * excludierte Prädikate nicht
     * erfüllbaren Constraints (Kontradiktion)
     * 
     * Exclude: A, B
     * 
     * (A and B)
     * 
     * durch den Constraint-Code-Generator.
     * 
     * @throws IOException rethrown
     */
    @Test(expected = IllegalStateException.class)
    public void testExcludeContradictionRegionOverInclude()
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
                "_03_excludes.constraints" ,
                //importStrArr ,
                new String[]{ 
                    "_03_excludes.enums.*" ,
                    "_03_excludes.predicates.*" } ,
                //contextObjClassName
                "Rolle" , 
                //contextObjName
                "rolle" ,
                //writeToDisc
                false ,
                //deleteUnusedConstraintJavaFiles
                false , 
                exclude )
        {{
            // durch excludierte Prädikate nicht erfüllbares Constraint
            add(
                    and(
                            new RolleFilialeNord() ,
                            new RolleFilialeSued() ) );
        }}.execute();
    }

    /**
     * Test des Ablehnens eines durch
     * excludierte Prädikate nicht
     * erfüllbaren Constraints (Kontradiktion)
     * 
     * Exclude: A, B
     * 
     * (A and B)
     * 
     * durch den Constraint-Code-Generator.
     * 
     * @throws IOException rethrown
     */
    @Test(expected = IllegalStateException.class)
    public void testExcludeContradictionEbeneOverNegation()
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
                "_03_excludes.constraints" ,
                //importStrArr ,
                new String[]{ 
                    "_03_excludes.enums.*" ,
                    "_03_excludes.predicates.*" } ,
                //contextObjClassName
                "Rolle" , 
                //contextObjName
                "rolle" ,
                //writeToDisc
                false ,
                //deleteUnusedConstraintJavaFiles
                false , 
                exclude )
        {{
            // durch excludierte Prädikate nicht erfüllbares Constraint
            add(
                    and(
                            new RolleZentrale() ,
                            not(
                                    new RolleEbeneZentrale() ) ) );
        }}.execute();
    }

    /**
     * Test des Ablehnens eines durch
     * excludierte Prädikate nicht
     * erfüllbaren Constraints (Kontradiktion)
     * 
     * Exclude: A, B
     * 
     * (A and B)
     * 
     * durch den Constraint-Code-Generator.
     * 
     * @throws IOException rethrown
     */
    @Test(expected = IllegalStateException.class)
    public void testExcludeContradictionRegionOverNegation()
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
                "_03_excludes.constraints" ,
                //importStrArr ,
                new String[]{ 
                    "_03_excludes.enums.*" ,
                    "_03_excludes.predicates.*" } ,
                //contextObjClassName
                "Rolle" , 
                //contextObjName
                "rolle" ,
                //writeToDisc
                false ,
                //deleteUnusedConstraintJavaFiles
                false , 
                exclude )
        {{
            // durch excludierte Prädikate nicht erfüllbares Constraint
            add(
                    and(
                            new RolleFilialeNord() ,
                            not(
                                    new RolleRegionNord() ) ) );
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
    //public void testEqualConstraintBecauseNegatedExclude1()
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
    //            "_03_excludes.constraints" ,
    //            //importStrArr ,
    //            new String[]{ 
    //                "_03_excludes.enums.*" ,
    //                "_03_excludes.predicates.*" } ,
    //            //contextObjClassName
    //            "Rolle" , 
    //            //contextObjName
    //            "rolle" ,
    //            //writeToDisc
    //            false ,
    //            //deleteUnusedConstraintJavaFiles
    //            false , 
    //            exclude )
    //    {{
    //        add(
    //                new RolleNiederlassung() );
    //        
    //        // eine äquivalentes Constraint entsteht durch Ausschliessen excludierter Constraints (and-not)
    //        add(
    //                and(
    //                        new RolleNiederlassung() ,
    //                        not(
    //                                new RolleFiliale() ) ) );
    //    }}.execute();
    //}

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
    //public void testEqualConstraintBecauseNegatedExclude2()
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
    //            "_03_excludes.constraints" ,
    //            //importStrArr ,
    //            new String[]{ 
    //                "_03_excludes.enums.*" ,
    //                "_03_excludes.predicates.*" } ,
    //            //contextObjClassName
    //            "Rolle" , 
    //            //contextObjName
    //            "rolle" ,
    //            //writeToDisc
    //            false ,
    //            //deleteUnusedConstraintJavaFiles
    //            false , 
    //            exclude )
    //    {{
    //        add(
    //                or(
    //                        new RolleZentrale() ,
    //                        new RolleNiederlassung() ) );
    //
    //        // eine äquivalentes Constraint entsteht durch Ausschliessen excludierter Constraints (and-not)
    //        add(
    //                and(
    //                        or(
    //                                new RolleZentrale() ,
    //                                new RolleNiederlassung() ) ,
    //                        not(
    //                                new RolleFiliale() ) ) );
    //    }}.execute();
    //}

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
    //public void testEqualConstraintBecauseOrToXorForExclude()
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
    //            "_03_excludes.constraints" ,
    //            //importStrArr ,
    //            new String[]{ 
    //                "_03_excludes.enums.*" ,
    //                "_03_excludes.predicates.*" } ,
    //            //contextObjClassName
    //            "Rolle" , 
    //            //contextObjName
    //            "rolle" ,
    //            //writeToDisc
    //            false ,
    //            //deleteUnusedConstraintJavaFiles
    //            false , 
    //            exclude )
    //    {{
    //        add(
    //                or(
    //                        new RolleZentrale() ,
    //                        new RolleNiederlassung() ) );
    //
    //        // eine äquivalentes Constraint entsteht durch Behandlung Or-verknüpfter sich ausschliessender Prädikate als Xor-Verknüpfung
    //        add(
    //                xor(
    //                        new RolleZentrale() ,
    //                        new RolleNiederlassung() ) );
    //    }}.execute();
    //}

}