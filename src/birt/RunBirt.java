package birt;

import javax.servlet.ServletContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.HTMLServerImageHandler;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;
import com.ibm.icu.util.ULocale;
import org.apache.struts2.*;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

public class RunBirt {

	private IReportEngine birtReportEngine = null;
	protected String reportName = null;
	private Integer idStructure;
	private Locale locale = null;

	public byte[] runReport(ServletContext sc, HttpServletRequest req)
			throws ServletException {

		this.setBirtReportEngine(BirtEngine.getBirtEngine(sc));

		IReportRunnable design;
		try {
			this.setReportName(req.getParameter("rp") + ".rptdesign");

			// Open report design
			design = birtReportEngine.openReportDesign(sc
					.getRealPath("/reports/") + "/" + this.getReportName());
			// create task to run and render report
			IRunAndRenderTask task = birtReportEngine
					.createRunAndRenderTask(design);

			if (req.getParameter("bookid") != null) {
				task.setParameterValue("bookid", Integer.valueOf(req.getParameter("bookid")));
			}

			if (req.getParameter("structid") != null) {
				task.setParameterValue("structid",
						Integer.valueOf(req.getParameter("structid")));
			} else {

				task.setParameterValue("structid", this.getIdStructure());
			}

			// set output PDF options
			PDFRenderOption optionsPDF = new PDFRenderOption();

			// NO optionsPDF.setOutputFileName(this.reportName);
			optionsPDF.setOutputFormat(PDFRenderOption.OUTPUT_FORMAT_PDF);
			// task.setLocale(req.getLocale());
			task.setLocale(this.getLocale());
			// NO
			// optionsPDF.setOutputFileName(PDFRenderOption.OUTPUT_FILE_NAME);
			ByteArrayOutputStream oStream = new ByteArrayOutputStream();
			optionsPDF.setOutputStream(oStream);
			task.setRenderOption(optionsPDF);

			// run report
			task.run();
			task.close();
			return oStream.toByteArray();

		} catch (Exception e) {

			e.printStackTrace();
			throw new ServletException(e);
		}
	}

	public Integer getIdStructure() {
		return idStructure;
	}

	public void setIdStructure(Integer idStructure) {
		this.idStructure = idStructure;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public IReportEngine getBirtReportEngine() {
		return birtReportEngine;
	}

	public void setBirtReportEngine(IReportEngine birtReportEngine) {
		this.birtReportEngine = birtReportEngine;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
}