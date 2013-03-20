package _05_switch.predicates;

import de.heinerkuecker.constraint.SafeMember;

/**
 * Definition von constraint-sicheren Membern
 * für Rollen-Constraints.
 *
 * @author Heiner K&uuml;cker
 */
public class RolleSafeMember
{
    /**
     * Rolle-Ebene Zentrale
     */
    public static final SafeMember ebeneZentrale =
            new SafeMember(
                    //name
                    "ebeneZentrale" ,
                    //typStr
                    _05_switch.enums.Ebene.Zentrale.class.getCanonicalName()
                    //contextObjMemberOrGetterStr
                    //".ebene"
                    )
            {
                @Override
                protected String createContextObjMemberOrGetterStr(
                        final String contextObjName )
                {
                    return contextObjName + ".ebene";
                }
            };

    /**
     * Rolle-Ebene Niederlassung
     */
    public static final SafeMember ebeneNiederlassung =
            new SafeMember(
                    //name
                    "ebeneNiederlassung" ,
                    //typStr
                    _05_switch.enums.Ebene.Niederlassung.class.getCanonicalName()
                    //contextObjMemberOrGetterStr
                    //".ebene"
                    )
            {
                @Override
                protected String createContextObjMemberOrGetterStr(
                        final String contextObjName )
                {
                    return contextObjName + ".ebene";
                }
            };

    /**
     * Rolle-Ebene Filiale
     */
    public static final SafeMember ebeneFiliale =
            new SafeMember(
                    //name
                    "ebeneFiliale" ,
                    //typStr
                    _05_switch.enums.Ebene.Filiale.class.getCanonicalName()
                    //contextObjMemberOrGetterStr
                    //".ebene"
                    )
            {
                @Override
                protected String createContextObjMemberOrGetterStr(
                        final String contextObjName )
                {
                    return contextObjName + ".ebene";
                }
            };

    /**
     * Rolle-Region Nord
     */
    public static final SafeMember regionNord =
            new SafeMember(
                    //name
                    "regionNord" ,
                    //typStr
                    _05_switch.enums.Region.Nord.class.getCanonicalName()
                    //contextObjMemberOrGetterStr
                    //".region"
                    )
            {
                @Override
                protected String createContextObjMemberOrGetterStr(
                        final String contextObjName )
                {
                    return "( ( Rolle.Niederlassung ) " + contextObjName + " ).region";
                }
            };

    /**
     * Rolle-Region Süd
     */
    public static final SafeMember regionSued =
            new SafeMember(
                    //name
                    "regionSued" ,
                    //typStr
                    _05_switch.enums.Region.Sued.class.getCanonicalName()
                    //contextObjMemberOrGetterStr
                    //".region"
                    )
            {
                @Override
                protected String createContextObjMemberOrGetterStr(
                        final String contextObjName )
                {
                    return "( ( Rolle.Niederlassung ) " + contextObjName + " ).region";
                }
            };

    /**
     * Rolle-Region West
     */
    public static final SafeMember regionWest =
            new SafeMember(
                    //name
                    "regionWest" ,
                    //typStr
                    _05_switch.enums.Region.West.class.getCanonicalName()
                    //contextObjMemberOrGetterStr
                    //".region"
                    )
            {
                @Override
                protected String createContextObjMemberOrGetterStr(
                        final String contextObjName )
                {
                    return "( ( Rolle.Niederlassung ) " + contextObjName + " ).region";
                }
            };

    /**
     * Rolle-Region Ost
     */
    public static final SafeMember regionOst =
            new SafeMember(
                    //name
                    "regionOst" ,
                    //typStr
                    _05_switch.enums.Region.Ost.class.getCanonicalName()
                    //contextObjMemberOrGetterStr
                    //".region"
                    )
            {
                @Override
                protected String createContextObjMemberOrGetterStr(
                        final String contextObjName )
                {
                    return "( ( Rolle.Niederlassung ) " + contextObjName + " ).region";
                }
            };

}