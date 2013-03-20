package _02_includes.use;

import _02_includes.IncludesConstraintCodeGenerator;
import _02_includes.constraints.RolleEbeneNiederlassungConstraint;
import _02_includes.constraints.RolleNiederlassungConstraint;
import _02_includes.constraints.RolleNiederlassungNordConstraint;
import _02_includes.constraints.RolleRegionNordConstraint;
import _02_includes.enums.Rolle;

/**
 * Diese Klasse demonstriert die Anwendung
 * der von {@link ExcludesConstraintCodeGenerator}
 * generierten Constraints.
 * 
 * @author Heiner K&uuml;cker
 */
public final class IncludesConstraintUse
{
    /**
     * Beispiel für eine Methode, die von Benutzern mit der
     * Rolle NiederlassungNord {@link Rolle.NiederlassungNord}
     * aufgerufen werden darf.
     * 
     * @param constraint
     */
    public static void doRolleNiederlassungNord(
            final RolleNiederlassungNordConstraint constraint )
    {
        doRolleNiederlassung(
                // Kompatibilität Constraint über Konvertierungs-Methode
                constraint.convertToRolleNiederlassungConstraint() );
        
        doRolleEbeneNiederlassung(
                // Kompatibilität Constraint über Konvertierungs-Methode
                constraint.convertToRolleEbeneNiederlassungConstraint() );

        doRolleRegionNord(
                // Kompatibilität Constraint über Konvertierungs-Methode
                constraint.convertToRolleRegionNordConstraint() );
    }

    /**
     * Beispiel für eine Methode, die von Benutzern mit der
     * Rollen Niederlassung {@link Rolle.Niederlassung}
     * aufgerufen werden darf.
     * 
     * @param constraint
     */
    public static void doRolleNiederlassung(
            final RolleNiederlassungConstraint constraint )
    {
        
    }

    /**
     * Beispiel für eine Methode, die von Benutzern mit der
     * Rollen Niederlassung {@link Rolle.Niederlassung}
     * aufgerufen werden darf.
     * 
     * @param constraint
     */
    public static void doRolleEbeneNiederlassung(
            final RolleEbeneNiederlassungConstraint constraint )
    {
        
    }

    /**
     * Beispiel für eine Methode, die von Benutzern mit der
     * Rolle NiederlassungNord {@link Rolle.RolleRegionNord}
     * aufgerufen werden darf.
     * 
     * @param constraint
     */
    public static void doRolleRegionNord(
            final RolleRegionNordConstraint constraint )
    {
        
    }

}