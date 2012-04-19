/*
 * jQuery Mobile Framework : plugin to provide a date and time picker.
 * Copyright (c) JTSage
 * CC 3.0 Attribution.  May be relicensed without permission/notifcation.
 * https://github.com/jtsage/jquery-mobile-datebox
 *
 * Translation by: Chris P. Vigelius <me@cv.gd>
 *
 */

jQuery.extend(jQuery.mobile.datebox.prototype.options.lang, {
	'de': {
		setDateButtonLabel: "Datum einstellen",
		setTimeButtonLabel: "Zeit einstellen",
		setDurationButtonLabel: "Dauer einstellen",
		calTodayButtonLabel: "heute",
		titleDateDialogLabel: "Datum wählen",
		titleTimeDialogLabel: "Wählen Sie Zeit",
		daysOfWeek: ["Sonntag", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag"],
		daysOfWeekShort: ["So", "Mo", "Di", "Mi", "Do", "Fr", "Sa"],
		monthsOfYear: ["Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"],
		monthsOfYearShort: ["Jan", "Feb", "Mär", "Apr", "Mai", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dez"],
		durationLabel: ["Tage", "Stunden", "Minuten", "Sekunden"],
		durationDays: ["Tag", "Tage"],
		tooltip: "Offene Datumsauswahl",
		nextMonth: "Nächsten Monat",
		prevMonth: "Vorheriger Monat",
		timeFormat: 24,
		headerFormat: '%A, %B %-d, %Y',
		dateFieldOrder: ['d','m','y'],
		timeFieldOrder: ['h', 'i', 'a'],
		slideFieldOrder: ['y', 'm', 'd'],
		dateFormat: "%d.%m.%Y",
		useArabicIndic: false,
		isRTL: false,
		calStartDay: 0,
		clearButton: "Klar",
		durationOrder: ['d', 'h', 'i', 's'],
		meridiem: ["AM", "PM"],
		timeOutput: "%k:%M",
		durationFormat: "%Dd %DA, %Dl:%DM:%DS"
	}
});
jQuery.extend(jQuery.mobile.datebox.prototype.options, {
	useLang: 'de'
});

