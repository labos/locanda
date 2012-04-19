/*
 * jQuery Mobile Framework : plugin to provide a date and time picker.
 * Copyright (c) JTSage
 * CC 3.0 Attribution.  May be relicensed without permission/notifcation.
 * https://github.com/jtsage/jquery-mobile-datebox
 *
 * Translation by: Giuseppe Petagine <giuseppe.petagine@virgilio.it>
 *
 */

jQuery.extend(jQuery.mobile.datebox.prototype.options.lang, {
	'it': {
		setDateButtonLabel: "Imposta data",
		setTimeButtonLabel: "Imposta ora",
		setDurationButtonLabel: "Setta Durata",
		calTodayButtonLabel: "Vai ad oggi",
		titleDateDialogLabel: "Scegli data",
		titleTimeDialogLabel: "Scegli ora",
		daysOfWeek: ["Domenica", "Luned&#236;", "Marted&#236;", "Mercoled&#236;", "Gioved&#236;", "Venerd&#236;", "Sabato"],
		daysOfWeekShort: ["Do", "Lu", "Ma", "Me", "Gi", "Ve", "Sa"],
		monthsOfYear: ["Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"],
		monthsOfYearShort: ["Gen", "Feb", "Mar", "Apr", "Mag", "Giu", "Lug", "Ago", "Set", "Ott", "Nov", "Dic"],
		durationLabel: ["Giorni", "Ore", "Minuti", "Secondi"],
		durationDays: ["Giorno", "Giorni"],
		tooltip: "Apri Selettore Data",
		nextMonth: "Mese successivo",
		prevMonth: "Mese precedente",
		timeFormat: 12,
		headerFormat: '%A %-d %B %Y',
		dateFieldOrder: ['d','m','y'],
		timeFieldOrder: ['h', 'i', 'a'],
		slideFieldOrder: ['y', 'm', 'd'],
		dateFormat: "%d-%m-%Y",
		useArabicIndic: false,
		isRTL: false,
		calStartDay: 0,
		clearButton: "Pulisci",
		durationOrder: ['d', 'h', 'i', 's'],
		meridiem: ["AM", "PM"],
		timeOutput: "%l:%M %p",
		durationFormat: "%Dd %DA, %Dl:%DM:%DS"
	}
});
jQuery.extend(jQuery.mobile.datebox.prototype.options, {
	useLang: 'it'
});

