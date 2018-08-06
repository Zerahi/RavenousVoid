package com.zerahi.ravvoid.register;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.zerahi.ravvoid.utils.CustomTriggers;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class Triggers {
    public static final List<CustomTriggers> TRIGGERS = new ArrayList<CustomTriggers> ();

	public static final CustomTriggers RAVVOID = new CustomTriggers("ravvoid");
	public static final CustomTriggers VOIDORE = new CustomTriggers("voidore");
	public static final CustomTriggers VOIDSHARD = new CustomTriggers("voidshard");
	public static final CustomTriggers VOIDFRAGMENTS = new CustomTriggers("voidfragments");
	public static final CustomTriggers FRAGMENTPILE = new CustomTriggers("fragmentpile");
	public static final CustomTriggers DARKNESS = new CustomTriggers("darkness");
	public static final CustomTriggers VOIDORB = new CustomTriggers("voidorb");
	public static final CustomTriggers VOIDALTAR = new CustomTriggers("voidaltar");
	public static final CustomTriggers VOIDREND = new CustomTriggers("voidrend");
	public static final CustomTriggers CRYSTALLIZER = new CustomTriggers("crystallizer");
	public static final CustomTriggers VOIDBEAST = new CustomTriggers("voidbeast");
	public static final CustomTriggers SHADE = new CustomTriggers("shade");
	public static final CustomTriggers PUREVOIDSHARD = new CustomTriggers("purevoidshard");
	public static final CustomTriggers AWAKENEDVOIDORB = new CustomTriggers("awakenedvoidorb");
	public static final CustomTriggers CONDUIT = new CustomTriggers("conduit");
	public static final CustomTriggers SIGHT = new CustomTriggers("sight");
	public static final CustomTriggers VOIDRIFT = new CustomTriggers("voidrift");
	public static final CustomTriggers Through = new CustomTriggers("through");
    
	public static void init() {
		TRIGGERS.add(RAVVOID);
		TRIGGERS.add(VOIDORE);
		TRIGGERS.add(VOIDSHARD);
		TRIGGERS.add(VOIDFRAGMENTS);
		TRIGGERS.add(FRAGMENTPILE);
		TRIGGERS.add(DARKNESS);
		TRIGGERS.add(VOIDORB);
		TRIGGERS.add(VOIDALTAR);
		TRIGGERS.add(VOIDREND);
		TRIGGERS.add(CRYSTALLIZER);
		TRIGGERS.add(VOIDBEAST);
		TRIGGERS.add(SHADE);
		TRIGGERS.add(PUREVOIDSHARD);
		TRIGGERS.add(AWAKENEDVOIDORB);
		TRIGGERS.add(CONDUIT);
		TRIGGERS.add(SIGHT);
		TRIGGERS.add(VOIDRIFT);
		TRIGGERS.add(Through);
		
		Method method; {}
		try {
		 method = ReflectionHelper.findMethod(CriteriaTriggers.class, "register", "func_192118_a", ICriterionTrigger.class);
		 method.setAccessible(true);
			for (int i=0; i < Triggers.TRIGGERS.size(); i++)
			{
			     method.invoke(null, Triggers.TRIGGERS.get(i));
			} 
		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {}
}
}
