package _05_switch;

import java.io.File;
import java.io.IOException;

import _05_switch.enums.Rolle;
import _05_switch.predicates.RolleEbeneFiliale;
import _05_switch.predicates.RolleEbeneNiederlassung;
import _05_switch.predicates.RolleEbeneZentrale;
import _05_switch.predicates.RolleRegionNord;
import _05_switch.predicates.RolleRegionOst;
import _05_switch.predicates.RolleRegionSued;
import _05_switch.predicates.RolleRegionWest;
import de.heinerkuecker.constraint.AbstractConstraintCodeGenerator;
import de.heinerkuecker.constraint.Exclude;
import de.heinerkuecker.constraint.ExcludeGroup;

/**
 * Code-Generator für 
 * Constraint-Expression-Java-Klassen
 * zur Prüfung von Constraints
 * zur Compile-Zeit.
 *
 * Achtung Achtung Achtung:
 * die main-Methode dieser Klasse schreibt auf Deine Platte,
 * also sichere Deine Daten und schaue Dir die Pfade genau an !!!
 * 
 * @author Heiner K&uuml;cker
 */
public final class SwitchConstraintCodeGenerator
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
     * Start Generierung.
     * 
     * @param args unused
     * @throws IOException rethrown
     */
    public static void main(
            final String [] args )
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
                "_05_switch.constraints" ,
                //importStrArr ,
                new String[]{ 
                    "_05_switch.enums.*" ,
                    "_05_switch.predicates.*" } ,
                //contextObjClassName
                "Rolle" , 
                //contextObjName
                "rolle" ,
                //writeToDisc
                true ,
                //deleteUnusedConstraintJavaFiles
                true , 
                exclude )
        {{
            add(
                    or(
                            new RolleEbeneZentrale() ,
                            new RolleEbeneNiederlassung() ,
                            new RolleEbeneFiliale() )
// TODO Übungsaufgabe Stuttgarter Testtage 2013
//                    switchDef( 
//                            "Ebene" , 
//                            //caseMethodReturnTypeName
//                            "void" ,
//                            //caseMethodExceptionsStr
//                            null ,
//                            //switchPredicateArr
//                            new RolleEbeneZentrale() ,
//                            new RolleEbeneNiederlassung() ,
//                            new RolleEbeneFiliale() )
                    );

            add(
                    or(
                            new RolleRegionNord() ,
                            new RolleRegionSued() ,
                            new RolleRegionWest() ,
                            new RolleRegionOst() ) ,
                    switchDef( 
                            "Region" , 
                            //caseMethodReturnTypeName
                            "void" ,
                            //caseMethodExceptionsStr
                            null ,
                            //switchPredicateArr
                            new RolleRegionNord() ,
                            new RolleRegionSued() ,
                            new RolleRegionWest() ,
                            new RolleRegionOst() ) );
        }}.execute();
    }

}