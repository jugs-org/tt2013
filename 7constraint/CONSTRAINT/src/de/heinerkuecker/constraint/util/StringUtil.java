package de.heinerkuecker.constraint.util;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Statische Hilfsmethoden
 * fuer Strings.
 */
public class StringUtil
{
    /**
     * Konstruktor private,
     * Instanziieren verboten.
     */
    private StringUtil()
    {
        super();
    }
	/**
	 * Aufsplitten eines Strings in Felder.
	 *
	 * @param str Der zu splittende String
	 * @param String str Der zu splittende String
	 * @param char delim Der Delimiter
	 * @return String[] Array von Strings, das die Teilstrings enthält.
	 */
    public static String [] split(
    		final String strng , 
    		final char delimiter )
    {
        char [] letters = strng.toCharArray();
        int size = 0;
        for(int i=0; i<letters.length; i++) {
            if(letters[i] == delimiter) {
                size++;
            }
        }
        String[] strngs = new String[size];
        /*final*/ StringBuilder field = new StringBuilder();
        for(int i=0,j=0; i<letters.length; i++) {
            if(letters[i] == delimiter && j<size) {
                strngs[j] = field.toString();
                field = new StringBuilder();
                ++j;
            } else {
                field.append(letters[i]);
            }
        }
        return strngs;
    }

	/**
	* Aufsplitten eines Strings in Felder
	*
	* @param String str Der zu splittende String
	* @param String delim Der Delimiter. 
	*		 			  Jedes eizelne Zeichen wird als Trenner angesehen.
	* @return String[] Array von Strings, das die Teilstrings enthält.
	*/

	public static String[] split(String str, String delim) {
		int i=0;
		StringTokenizer tokenizer = new StringTokenizer(str, delim);
		String[] ret = new String[tokenizer.countTokens()];
		while (tokenizer.hasMoreTokens()) {
			ret[i++] = tokenizer.nextToken();
		}
		return ret;
	}

	/**
	* Aufsplitten eines Strings in Felder
	*
	* @param String str Der zu splittende String
	* @param String delim Der Delimiter
	* @return String[] Array von Strings, das die Teilstrings enthält.
	*/

    public static String[] splitWithRegex(String str, String delim) {
        return str.split(delim);
    }

	/**
	* Ersetzt die Vorkommen von Teilstrings in einem String durch
	* einen neuen Teilstring
	*
	* @param str Ursprünglicher String
	* @param oldStr Zu ersetzender Teilstring
	* @param newStr Teilstring, durch den Ersetzt werden soll
	* @return String mit erSetzten Teilstrings
	*/
	public static String replace(
	        String str , 
	        String oldStr , 
	        String newStr )
	{
	   final StringBuilder sbRes = 
	       new StringBuilder(
	               str.length() );

	   int iPos;
       int iLastPos = 0;

       while ( ( iPos = str.indexOf( oldStr , iLastPos ) ) != -1 )
       {
           sbRes.append(str.substring(iLastPos, iPos));
           sbRes.append(newStr);
           iLastPos = iPos + oldStr.length();
       }

       sbRes.append( 
               str.substring(
                       iLastPos, 
                       str.length() ) );

	   return sbRes.toString();
	}

    /**
    * Ersetzt alle Whitespace-Charcter in einem 
    * String durch einen neuen Teilstring
    *
    * @param str Ursprünglicher String
    * @param newStr Teilstring, durch den Ersetzt werden soll
    * @return String mit erSetzten Teilstrings
    */
    public static String replaceWhitespace(
            String str , 
            String newStr )
    {
        if (str == null)
        {
          return (null);
        }

        final StringBuilder strBuff = new StringBuilder( (int) Math.round( str.length() * 1.1 ) );
        
        //synchronized ( strBuff )
            // StringBuffer-Mutex behalten für bessere Performance
        {
            for (int i = 0; i < str.length(); i++)
            {
                final char runChar = str.charAt( i );

                if ( Character.isWhitespace( runChar ) )
                {
                    // Zeichen schlucken
                }
                else
                {
                    strBuff.append( runChar );
                }
            }

            return (strBuff.toString());
        }
    }

	/**
	* @return true - String besteht nur aus Ziffern, false - String enthält
	* andere Zeichen als Ziffern
	*/
	public static boolean isDigit(String s)
	{
		StringCharacterIterator it = new StringCharacterIterator(s);
		for(char c = it.first(); c != CharacterIterator.DONE; c = it.next()) {
			if(Character.isDigit(c) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	* Filtert nur die Buchstaben und die Zahlen aus dem übergebenen String heraus
	* und gibt den gefilterten String zurück
	*
	* @param s Ursprünglicher String
	* @return String nur mit Buchstaben oder Ziffern
	*/

    public static String toLetterOrDigit (String s)
    {
        final StringBuilder buf = new StringBuilder(s.length()*2);
        char [] a = s.toCharArray();
        for(int i=0; i<a.length; i++) {
            if( Character.isLetterOrDigit(a[i])) {
                buf.append(a[i]);
			}
        }
        return(buf.toString());

    }

    /**
     * Pruefen, ob der uebergebene String
     * 'null' ist, die Laenge 0 hat oder
     * nur aus weissen Zeichen besteht.
     * 
     * @param str zu pruefender String
     * @return ob der uebergeben String leer ist
     * <a href="http://groups.google.de/group/de.comp.lang.java/browse_thread/thread/e0ceda9c412074c1?hl=de#">PMD InefficientEmptyStringCheck</a>
     */
    public static boolean isEmpty(
    		final String str )
    {
    	return 
    		str == null ||
    		str.length() == 0 ||
    		//str.trim().length() == 0;
    		// Beschleunigung nach: http://groups.google.de/group/de.comp.lang.java/browse_thread/thread/e0ceda9c412074c1?hl=de#
    		hasOnlyWhitespaces( str );
    }
    
    /**
     * Interne Methode zum Prüfen,
     * ob der übergebene String
     * nur aus weissen Zeichen besteht.
     * 
     * @param str zu prüfender String
     * @return ob der übergebene String
     *         nur aus weissen Zeichen besteht
     */
    private static boolean hasOnlyWhitespaces( 
            final String str )
    {
        for ( int i = 0 ; i < str.length() ; i++ )
        {
            if ( str.charAt( i ) > ' ' )
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Pruefen, ob der uebergebene String
     * <b>NICHT</b>
     * 'null' ist, die Laenge 0 hat oder
     * nur aus weissen Zeichen besteht.
     * 
     * @param str zu pruefender String
     * @return ob der uebergeben String <b>nicht</b> leer ist
     */
    public static boolean isNotEmpty(
            final String str)
    {
        return ! isEmpty( str );
    }
    
    /**
     * Prüfen, ob das übergebene String-Array
     * 'null' ist oder die Länge 0 hat.
     * 
     * @param strArr zu prüfendes String-Array
     * @return
     *      ob das übergebene String-Array
     *      'null' ist oder die Länge 0 hat.
     */
    public static boolean isEmpty(
            final String[] strArr )
    {
        return 
            strArr == null
            || strArr.length == 0;
    }
    
    /**
     * Prüfen, ob das übergebene String-Array
     * <b>NICHT</b>
     * 'null' ist oder die Länge 0 hat.
     * 
     * @param strArr zu prüfendes String-Array
     * @return
     *      ob das übergebene String-Array
     *      <b>nicht</b> 'null' ist oder die Länge 0 hat.
     */
    public static boolean isNotEmpty(
            final String[] strArr )
    {
        return ! isEmpty( strArr );
    }
    
    /**
     * Diese Methode haengt an den uebergebenen
     * {@link StringBuffer} den uebergebenen
     * {@link String} strToAppend an, wenn
     * dieser nicht leer ist.
     * 
     * Falls der uebergebene {@link StringBuffer}
     * nicht leer war, wird vor dem Anhaengen
     * des strToAppend der Trenn-String
     * delimStr angehaengt, damit die einzelnen
     * String-Teile getrennt sind.
     * 
     * @param strBuff     an diesen {@link StringBuffer} wird angehaengt
     * @param delimStr    Trenn-String
     * @param strToAppend dieser {@link String} wird angehaengt
     * @throws NullPointerException wenn der Parameter strBuff null ist
     */
    public static void appendIfNotEmptyWithDelimiter(
    		final StringBuffer strBuff ,
    		final String delimStr ,
    		final String strToAppend )
    {
    	if ( isEmpty( strToAppend ) )
    	{
    		return;
    	}
    	
    	if ( ! isEmpty( strBuff.toString() ) )
    	{
    		strBuff.append( delimStr );
    	}
    	
    	strBuff.append( strToAppend );
    }

    /**
     * Verbinden aller Elemente des uebergebenen
     * Object-Arrays als String mit Komma als
     * Trennzeichen und jedes Element in
     * Apostrophe (Hochkomma) eingeschlossen.
     * 
     * Diese Methode dient zum Zusammenfügen
     * der IN-Klausel eines SQL-WHERE-Klausel-Strings.
     * 
     * @param objArrToConcat als String zu verbindendes Object-Array
     * @return als String verbundenes Object-Array mit Komma-Delimitern
     */
	public static String concatArrWithCommaElementsInApostroph(
			final Object[] objArrToConcat )
	{
	    final StringBuilder strBuff = 
	    	new StringBuilder(
	    			6 * objArrToConcat.length );
	
	    for(int i=0; i<objArrToConcat.length; i++)
	    {
	        if( i > 0 )
	        {
				strBuff.append(",");
			}

	        strBuff.append( "'" ).append( objArrToConcat[ i ] ).append( "'" );
	    }
	    return strBuff.toString();
	}

	/**
     * Verbinden aller Elemente der uebergebenen
     * Collection als String mit Komma als
     * Trennzeichen und jedes Element in
     * Apostrophe (Hochkomma) eingeschlossen.
     * 
     * Diese Methode dient zum Zusammenfügen
     * der IN-Klausel eines SQL-WHERE-Klausel-Strings.
     * 
     * @param collToConcat als String zu verbindende Collection
     * @return als String verbundene Collection mit Komma-Delimitern
     */
	public static String concatCollWithCommaElementsInApostroph(
			final Collection<?> collToConcat )
	{
	    final StringBuilder strBuff = 
	    	new StringBuilder(
	    			6 * collToConcat.size() );

	    int i = 0;
	    for (Iterator<?> iterator = collToConcat.iterator(); iterator.hasNext();)
	    {
			final Object obj = (Object) iterator.next();
			
	        if( i++ > 0 )
	        {
				strBuff.append(",");
			}

	        strBuff.append( "'" ).append( obj ).append( "'" );
	    }
	    return strBuff.toString();
	}

	/**
	 * Umwandlung des übergebenen Objektes
	 * in einen String mit Hilfe der
	 * {@link Object#toString()},
	 * wobei aber Arrays mit Hilfe von
	 * {@link Arrays#asList(Object[])}
	 * in eine lesbare Form gebracht werden.
	 * 
	 * @param obj in einen String umzuwandelndes Objekt
	 * @return String-Darstellung des übergebenen Objekts
	 */
	public static String toStr(
			final Object obj )
	{
		if ( obj instanceof Object[] )
		{
			return Arrays.asList( (Object[]) obj ).toString();
		}
		return String.valueOf( obj );
	}
	
	/**
	 * Der &uuml;bergebene String wird auf die
	 * gew&uuml;nschte L&auml;nge links mit 
	 * Nullen aufgef&uuml;llt.
	 * 
	 * @param strToFillWithLeadingZeros aufzuf&uuml;llender String
	 * @param desiredLength gew&uuml;nschte L&auml;nge
	 * @return auf die gew&uuml;nschte L&auml;nge links
	 *         mit Nullen aufgef&uuml;llter String
	 */
	public static String leadingZeros(
			final String strToFillWithLeadingZeros , 
			final int desiredLength )
	{
		if ( strToFillWithLeadingZeros == null )
		{
			return null;
		}
		
		if ( strToFillWithLeadingZeros.length() >= desiredLength )
		{
			return strToFillWithLeadingZeros;
		}
		
		final StringBuilder strBuff = new StringBuilder( desiredLength );
		
		int i = strToFillWithLeadingZeros.length();
		
		while ( i < desiredLength )
		{
			strBuff.append( '0' );
			i++;
		}
		
		strBuff.append( strToFillWithLeadingZeros );
		
		return strBuff.toString();
	}
	
	/**
	 * Der &uuml;bergebene String wird auf die
	 * gew&uuml;nschte L&auml;nge rechts mit 
	 * Leerzeichen aufgef&uuml;llt.
	 * 
	 * @param str aufzuf&uuml;llender String
	 * @param desiredLength gew&uuml;nschte L&auml;nge
	 * @return auf die gew&uuml;nschte L&auml;nge rechts
	 *         mit Leerzeichen aufgef&uuml;llter String
	 */
	public static String pad(
			final String str, 
			final int desiredLength )
	{
		if ( str == null )
		{
			return null;
		}
		
		if ( str.length() >= desiredLength )
		{
			return str;
		}
		
		final StringBuilder strBuff = new StringBuilder( desiredLength );
		
		strBuff.append( str );

		while ( strBuff.length() < desiredLength )
		{
			strBuff.append( ' ' );
		}
		
		return strBuff.toString();
	}

	/**
	 * Der &uuml;bergebene String wird auf die
	 * gew&uuml;nschte L&auml;nge links mit 
	 * Leerzeichen aufgef&uuml;llt.
	 * 
	 * @param str aufzuf&uuml;llender String
	 * @param desiredLength gew&uuml;nschte L&auml;nge
	 * @return auf die gew&uuml;nschte L&auml;nge links
	 *         mit Leerzeichen aufgef&uuml;llter String
	 */
	public static String padLeft(
			final String str, 
			final int desiredLength )
	{
		if ( str == null )
		{
			return null;
		}
		
		if ( str.length() >= desiredLength )
		{
			return str;
		}
		
		final StringBuilder strBuff = new StringBuilder( desiredLength );
		
		int i = str.length();
		
		while ( i < desiredLength )
		{
			strBuff.append( ' ' );
			i++;
		}
		
		strBuff.append( str );
		
		return strBuff.toString();
	}

	/**
	 * Prüfen, ob das übergebene
	 * String-Array null oder leer
	 * (Länge == 0)
	 * ist oder Elemente enthält
	 * die null oder Leerstring
	 * sind.
	 * 
	 * @param strArr zu prüfendes String-Array
	 * @return ob das übergebene String-Array leer ist 
	 *         oder Leer-Elemente enthält
	 */
	public static final boolean isEmptyOrHasEmptyElement(
			final String[] strArr )
	{
		if ( strArr == null ||
			strArr.length == 0 )
		{
			return true;
		}
	
		for (int i = 0; i < strArr.length; i++)
		{
			String str = strArr[i];
			
			if ( isEmpty( str ) )
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Zurückgeben des übergebenen Strings,
	 * wenn dieser nicht null ist oder
	 * eines Leerstrings, wenn der übergebene
	 * String null ist.
	 * 
	 * @param str von null nach Leerstring umzuwandelnder String
	 * @return Leerstring, falls null übergeben wurde
	 */
	public static String nullToEmptyStr(
			final String str )
	{
		if ( str == null )
		{
			return "";
		}
		return str;
	}

	/**
	 * Prüfen, ob der übergebene String einen gültigen Short-Wert darstellt.
	 * 
	 * @param shortStr zu parsender numerischer String
	 * @return ob der übergebene String einen gültigen Short-Wert darstellt
	 */
	public static boolean isShortStr(
			final String shortStr )
	{
		try
		{
			Short.parseShort(
					shortStr );
			
			return true;
		} 
		catch ( NumberFormatException e ) 
		{
			return false;
		}
	}

	/**
	 * Ersten Buchstaben des übergebenen Strings
	 * in einen Kleinbuchstaben umwandeln.
	 * 
	 * @param str umzuwandelnder String
	 * @return übergebener String mit Kleinbuchstaben am Anfang
	 */
	public static String firstCharToLowerCase(
			final String str ) 
	{
		if ( isEmpty( str ) )
		{
			return str;
		}
		
		if ( str.length() == 1 )
		{
			return str.toLowerCase();
		}
		return
		str.substring( 0 , 1 ).toLowerCase() +
		str.substring( 1 );
	}

    /**
     * Prüfen, ob der übergebene String einer gültigen Uhrzeit entspricht.
     * Format: 'hh:mm'.
     * 
     * @param clockTimeStr
     *      zu prüfender Uhrzeit-String
     * @return
     *      ob der übergebene String einer gültigen Uhrzeit entspricht.
     *      Format: 'hh:mm'
     */
    public static boolean isClockTimeStr(
            final String clockTimeStr )
    {
        if ( isEmpty( clockTimeStr ) )
        {
            return false;
        }
        
        if ( clockTimeStr.indexOf( ':' ) < 0 )
            // kein Doppel-Punkt im String enthalten
        {
            return false;
        }
        
        final StringTokenizer tokenizer = 
            new StringTokenizer(
                    clockTimeStr ,
                    ":" );
        
        if ( tokenizer.countTokens() != 2 )
        {
            return false;
        }

        try
        {
            int hourIntValue    = Integer.parseInt( tokenizer.nextToken() );
            int minutesIntValue = Integer.parseInt( tokenizer.nextToken() );
            
            if ( hourIntValue < 0 || hourIntValue > 23 )
            {
                return false;
            }
            
            if ( minutesIntValue < 0 || minutesIntValue > 59 )
            {
                return false;
            }
        }
        catch ( NumberFormatException numberFormatExc )
        {
            return false;
        }
        return true;
    }

    /**
     * Nullsicheres Trimmen des übergebenen Strings.
     * 
     * @param strToTrim zu trimmender String oder <code>null</code>
     * @return getrimmter String oder <code>null</code>
     */
    public static String trim( 
            final String strToTrim )
    {
        if ( strToTrim == null )
        {
            return null;
        }
        return strToTrim.trim();
    }

    /**
     * Der übergebene String wird auf die übergebene 
     * Länge gekürzt, was nicht ganz korrekt ist, 
     * da der übergebene String unveränderlich ist.
     * Es entsteht eine gekürzte Kopie des
     * originalen Strings.
     *
     * @param srcStr Original-String
     * @param maxLength maximale gewünschte Länge
     * @return abgeschnittener Teil vom übergebenen Original-String oder Original-String, falls die übergebene maximale Länge eingehalten wurde
     */
    public static String cutToMaxLength(
            final String srcStr , 
            final int maxLength ) 
    {
      if (srcStr == null) 
      {
         // garbage in , garbage out, avoid NullPointerException
        return srcStr;
      }

      if ( srcStr.length() <= maxLength )
      {
          // Abschneiden nicht nötig, maximale Länge wird eingehalten
          return srcStr;
      }
      
      return srcStr.substring( 0 , maxLength );
    }

    /**
     * Filter the specified string for characters that are senstive to
     * HTML interpreters, returning the string with these characters replaced
     * by the corresponding character entities.
     *
     * @param pStr The string to be filtered and returned
     * @return the filtered String
     */
    public static final String encodeHtml(
            final String pStr )
    {
      if (pStr == null)
      {
        return (null);
      }

      final StringBuilder strBuff = new StringBuilder( (int) Math.round( pStr.length() * 1.1 ) );
      
      //synchronized ( strBuff )
          // StringBuffer-Mutex behalten für bessere Performance
      {
          for (int i = 0; i < pStr.length(); i++)
          {
              final char runChar = pStr.charAt( i );
              switch ( runChar )
              {
                  case '<':
                      strBuff.append("&lt;");

                      break;

                  case '>':
                      strBuff.append("&gt;");

                      break;

                  case '&':
                      strBuff.append("&amp;");

                      break;

                  case '"':
                      strBuff.append("&quot;");

                      break;

                  default:
                      strBuff.append( runChar );
              }
            }

            return (strBuff.toString());
      }
    } // end method encodeHtml

    /**
     * Filter the specified string for characters that are senstive to
     * HTML interpreters, returning the string with these characters 
     * removed.
     *
     * @param pStr The string to be filtered and returned
     * @return the filtered String
     */
    public static final String removeHtmlSpecialChars(
            final String pStr )
    {
      if (pStr == null)
      {
        return (null);
      }

      char  content[] = new char[pStr.length()];

      pStr.getChars(0, pStr.length(), content, 0);

      final StringBuilder strBuff = new StringBuilder(content.length);
      
      //synchronized ( strBuff )
          // StringBuffer-Mutex behalten für bessere Performance
      {
          for (int i = 0; i < content.length; i++) {
              switch (content[i]) {

              case '<':
                //strBuff.append("&lt;");

                break;

              case '>':
                //strBuff.append("&gt;");

                break;

              case '&':
                //strBuff.append("&amp;");

                break;

              case '"':
                //strBuff.append("&quot;");

                break;

              default:
                strBuff.append(content[i]);
              }
            }

            return (strBuff.toString());
      }
    } // end method encodeHtml
    
    /**
     * Bereinigen des übergebenen
     * Telefon-Nummer-Strings von Zeichen,
     * die nicht für den Export zur
     * Veranstaltungsdatenbank
     * erlaubt sind.
     * 
     * @param telefonNrStrToClean zu bereinigender Telefon-Nummer-String
     * @return bereinigter Telefon-Nummer-String
     */
    public static String cleanTelefonNummerFuerVdb(
            final String telefonNrStrToClean )
    {
        if ( telefonNrStrToClean == null )
        {
            return null;
        }
        /*
         * Erlaubt ist
         * Ziffer 0-9,
         * Leerzeichen,
         * Scrägstrich (Slash)
         * und Bindestrich.
         */
        
        /// eventuelle Landesvorwahl entfernen
        final String tmpTelefonNrStr =
            replace(
                    telefonNrStrToClean ,
                    "+49" ,
                    "0" );
        
        final StringBuilder cleanedTelNrStrBuff =
            new StringBuilder(
                    tmpTelefonNrStr.length() );
        
        for ( int i = 0 ; i < tmpTelefonNrStr.length() ; i++ )
        {
            final char tmpChr = 
                tmpTelefonNrStr.charAt( 
                        i );
            
            if ( Character.isDigit( tmpChr ) ||
                 tmpChr == ' ' ||
                 tmpChr == '/' ||
                 tmpChr == '-' )
            {
                cleanedTelNrStrBuff.append( 
                        tmpChr );
            }
        }
        
        return cleanedTelNrStrBuff.toString();
    }
    
    /**
     * Prüfen des übergebenen Strings
     * auf die Einhaltung des Formates
     * 'dd.MM.yyyy' oder 'dd.MM.yy'.
     * 
     * @param pDateStr
     * @return ob deutsches Datums-Format eingehalten
     */
    public static boolean isGermanDateStr(
            final String pDateStr )
    {
        if ( pDateStr == null )
        {
            return false;
        }
        
        final String trimmedDateStr =
            pDateStr.trim();
        
        final String[] tokenizedDateStrArr =
            trimmedDateStr.split( "\\." );
        
        return 
            tokenizedDateStrArr != null &&
            tokenizedDateStrArr.length == 3 &&
            isDigit( tokenizedDateStrArr[ 0 ] ) &&
            isDigit( tokenizedDateStrArr[ 1 ] ) &&
            isDigit( tokenizedDateStrArr[ 2 ] );
    }
    
    /**
     * String auf Start mit anderem String pr&uuml;fen
     * unter Ignorierung Gross-Klein-Schreibung.
     *
     * @param strToCheck  zu pr&uuml;fender String
     * @param expectedStartStr  erwarteter Start-String
     *
     * @return ja, wenn paStr mit paStrStart beginnt
     */
    public static boolean startsWithIgnoreCase(
            final String strToCheck , 
            final String expectedStartStr )
    {
        if (strToCheck != null && expectedStartStr != null)
        {
            return 
                strToCheck.toLowerCase().startsWith(
                        expectedStartStr.toLowerCase() );
        }
        return false;
    } // end method startsWithIgnoreCase

    /**
     * Diese Methode entfernt führende Nullen aus
     * dem übergebenen numerischen String.
     * 
     * @param numStr numerischer String mit eventuellen führenden Nullen
     * @return numerischer String ohne führenden Nullen
     */
    public static String removeLeadingZeros( 
            final String numStr )
    {
        if ( numStr == null )
        {
            return null;
        }
        String resultStr =
            numStr.trim();
        
        while ( 
                // String hat mehr als ein Zeichen
                resultStr.length() > 1 &&
                // am Anfang steht eine '0'
                resultStr.startsWith( "0" ) &&
                // es folgt eine Ziffer, also kein Komma oder Dezimalpunkt oder andere Nicht-Ziffer
                Character.isDigit( resultStr.charAt( 1 ) ) )
        {
            // erstes Zeichen entfernen
            resultStr =
                resultStr.substring( 1 );
        }
        return resultStr;
    }

    /**
     * Ersetzen von Zeilenumbrüchen durch
     * &lt;br/&gt;-Tags.
     * 
     * @param strToReplace zu ersetzender String
     * @return erstzter String
     */
    public static String replaceBreaksToBrTags( 
            final String strToReplace )
    {
        if ( strToReplace == null )
        {
            return null;
        }
        final String strWithCrLfReplaced =
            replace( 
                    strToReplace , 
                    "\r\n" , 
                    "<br/>" );
        
        final String strWithLfReplaced =
            replace( 
                    strWithCrLfReplaced , 
                    "\n" , 
                    "<br/>" );
        
        final String strWithCrReplaced =
            replace( 
                    strWithLfReplaced , 
                    "\r" , 
                    "<br/>" );
        
        return strWithCrReplaced;
    }

    /**
     * Check the specified String for Digits
     * or other characters.
     * 
     * @param pStr String to check
     * @return whether only digits or not
     */
    public static boolean onlyDigits(
            final String pStr )
    {
        if (pStr == null)
        {
            return false;
        }
        for ( int i = 0 , len = pStr.length() ; i < len ; i++ )
        {
            if ( ! Character.isDigit( pStr.charAt(i) ) )
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Alle Zeichen, die keine Ziffern sind,
     * aus dem übergebenen String entfernen.
     * 
     * @param strWithNoDigits String eventuell nicht nur mit Ziffern
     * @return String nur mit Ziffern
     */
    public static String removeNoDigits(
            final String strWithNoDigits )
    {
        if ( strWithNoDigits == null )
        {
            return null;
        }
        
        if ( onlyDigits( strWithNoDigits ) )
        {
            return strWithNoDigits;
        }
        
        final StringBuilder buff = new StringBuilder( strWithNoDigits.length() );
        
        for ( int i = 0 , len = strWithNoDigits.length() ; i < len ; i++ )
        {
            final char charAt = 
                strWithNoDigits.charAt( i );

            if ( Character.isDigit( charAt ) )
            {
                buff.append( charAt );
            }
        }
        return buff.toString();
    }

}
