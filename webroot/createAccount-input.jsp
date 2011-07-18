<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">



<s:url action="createAccount" var="url_createAccount"></s:url>
<jsp:include page="jsp/layout/header.jsp" />
<div id="home">
<h1>Locanda - <s:text name="createAccount" /></h1>
<p><s:text name="homeWelcomeMessage" />.</p>
<s:actionerror /><s:fielderror></s:fielderror>

<form role="application" class="yform"
	action="<s:property value="url_createAccount"/>" method="post">
<fieldset><legend><s:text name="insertyourData" /></legend>
<div class="type-text"><label for="firstname"><s:text
	name="name" /></label> <input type="text" size="20" id="firstname"
	name="user.name" class="required"
	value="<s:property value="user.name"/>" /></div>
<div class="type-text"><label for="surname"><s:text
	name="lastName" /></label> <input type="text" size="20" id="surname"
	name="user.surname" class="required"
	value="<s:property value="user.surname"/>" /></div>
<div class="type-text"><label for="email">E-Mail <sup
	title="This field is mandatory.">*</sup></label> <input type="text"
	aria-required="true" size="20" id="email" name="user.email"
	class="required" value="<s:property value="user.email"/>" /></div>
<div class="type-text"><label for="phone"><s:text
	name="phone" /> <sup title="This field is mandatory.">*</sup></label> <input
	type="text" aria-required="true" size="20" id="phone" name="user.phone"
	class="required" value="<s:property value="user.phone"/>" /></div>
<div class="type-text">
                <label for="message"><s:text name="disclaimer" /></label><textarea
	rows="5" cols="60">Ai sensi dell'articolo 13 del Dlgs. n. 196/2003, relativo alla tutela delle persone e di altri soggetti rispetto
al trattamento di dati personali, il trattamento dei dati che ci sta affidando sarà improntato ai principi di correttezza, liceità e trasparenza e di tutela della sua riservatezza e dei suoi diritti.

La informiamo, pertanto, che:

1. I dati da lei forniti verranno trattati per le seguenti finalità: Progetto Locanda

2. Il trattamento sarà effettuato con le seguenti modalità: (manuale e informatizzato).

3. Il conferimento dei dati è obbligatorio e l'eventuale rifiuto di fornire gli stessi e il consenso al loro
trattamento comporta l’impossibilità alla partecipazione alla procedura.

4. I dati non saranno comunicati ad altri soggetti, né saranno oggetto di diffusione, con la sola eccezione della denominazione o ragione sociale dei partecipanti al Progetto in occasione di eventi o manifestazioni
volte a promuovere il Progetto o la sua diffusione.

5. Il titolare del trattamento è Sardegna Ricerche con sede legale in via Palabanda, 9 09123 Cagliari.

6. Il responsabile del trattamento è l’ing. Andrea Redegoso. E-mail: privacy@sardegnaricerche.it

7. In ogni momento potrà esercitare i Suoi diritti nei confronti del titolare del trattamento, ai sensi dell'art. 7 del D.lgs.196/2003.

Il sottoscritto presta il suo consenso al trattamento dei dati personali per i fini indicati nella suddetta informativa.
</textarea></div>
<div class="type-check">
                <input type="checkbox" aria-required="true" size="20" id="check_disclaimer" name="disclaimer" class="required" />
                <label for="check_disclaimer"><s:text name="disclaimerAccept" /></label>
              </div>
</fieldset>
<div class="type-button">
<button class="btn_submit" type="submit" role="button"
	aria-disabled="false"><s:text name="createAccount" /></button>
</div>
</form>


<p id="home_images"></p>
</div>
<jsp:include page="jsp/layout/footer.jsp" />
