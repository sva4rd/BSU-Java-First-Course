package stage2;

import java.util.*;

public class AppLocale {
	private static final String strMsg = "Msg";
	private static Locale loc = Locale.getDefault();
	private static ResourceBundle res = 
			ResourceBundle.getBundle( AppLocale.strMsg, AppLocale.loc );
	
	static Locale get() {
		return AppLocale.loc;
	}
	
	static void set( Locale loc ) {
		AppLocale.loc = loc;
		res = ResourceBundle.getBundle( AppLocale.strMsg, AppLocale.loc );
	}
	
	static ResourceBundle getBundle() {
		return AppLocale.res;
	}
	
	static String getString( String key ) {
		return AppLocale.res.getString(key);
	}
	
	// Resource keys:
	
	public static final String kitchen="kitchen";
	public static final String bathroom="bathroom";
	public static final String bedroom="bedroom";
	public static final String toilet="toilet";
	public static final String hallway="hallway";
	public static final String storeroom="storeroom";
	public static final String living_room="living_room";
	public static final String office="office";
	public static final String brigade_member="brigade_member";
	public static final String brigade="brigade";
	public static final String customer="customer";
	public static final String termsOfReference="termsOfReference";
	public static final String designBill="designBill";
	public static final String mainConstructor="mainConstructor";
	public static final String designCost="designCost";
	public static final String constructionCost="constructionCost";
	public static final String creation="creation";
	public static final String noTask="noTask";
	public static final String noBill="noBill";
}
