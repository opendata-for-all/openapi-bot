package com.xatkit.plugins.openapi.platform.utils;

import java.text.ParseException;
import java.util.Locale;

import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.RuleBasedNumberFormat;

public class NumberUtils {
	
	public static Integer parseInteger(String text) {
		Integer value = null;
		try {
		value = Integer.valueOf(text);}
		catch(NumberFormatException e) {
			RuleBasedNumberFormat  numberFormat = new RuleBasedNumberFormat(Locale.US,RuleBasedNumberFormat.SPELLOUT);
			try {
				value = Integer.valueOf( numberFormat.parse(text).intValue());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
		return value;
		
	
	}

	public static String formatInteger(Integer number) {
		NumberFormat numberFormat = RuleBasedNumberFormat.getIntegerInstance(Locale.US);
		return numberFormat.format(number);
	}
	
	public static Integer formatOrdinal(String text) {
		Integer value = null;
		
		RuleBasedNumberFormat nf = new RuleBasedNumberFormat(Locale.US, RuleBasedNumberFormat.SPELLOUT);
		try {
			value = Integer.valueOf(nf.parse(text).intValue());
		} catch (ParseException e) {
		
			e.printStackTrace();
		}
		return value;
	}
}
