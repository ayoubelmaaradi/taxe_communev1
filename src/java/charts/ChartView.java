/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charts;

import bean.AnnexeAdministratif;
import bean.Quartier;
import bean.Rue;
import bean.Secteur;
import bean.TaxeTrim;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import service.TaxeTrimFacade;

/**
 *
 * @author HP
 */
@Named(value = "chartView")
@ManagedBean
public class ChartView implements Serializable {

    private BarChartModel barModel;
    @EJB
    private TaxeTrimFacade taxeTrimFacade;
    private Secteur secteur;
    private Quartier quartier;
    private AnnexeAdministratif annex;
    private Rue rue;
    private String activite;
    private int firstYear;
    private int secondYear;
    private TaxeTrim tax;

    private BarChartModel initBarModel() {
        ChartSeries firstYearTaxe = new ChartSeries();
        ChartSeries secondYearTaxe = new ChartSeries();
        BarChartModel model = new BarChartModel();
        firstYearTaxe.setLabel("" + firstYear);
        secondYearTaxe.setLabel("" + secondYear);

        int x;
        List<TaxeTrim> listTaxes = taxeTrimFacade.findTaxByCritere(activite, firstYear, secondYear, rue, quartier, annex, secteur);
        for (x = 1; x < 5; x++) {
            Double b = 0.0;
            Double c = 0.0;
            for (TaxeTrim taxeTrim : listTaxes) {
                if (taxeTrim.getTaxeAnnuel().getAnnee() == firstYear && taxeTrim.getNumeroTrim() == x) {
                    b += taxeTrim.getMontantTotal();
                }
                if (taxeTrim.getTaxeAnnuel().getAnnee() == secondYear && taxeTrim.getNumeroTrim() == x) {
                    c += taxeTrim.getMontantTotal();
                }
            }
            firstYearTaxe.set("Trimestre " + x, b);
            secondYearTaxe.set("Trimestre " + x, c);

        }

        model.addSeries(firstYearTaxe);
        model.addSeries(secondYearTaxe);

        return model;
    }

    private void createBarModel() {
        barModel = initBarModel();

        barModel.setTitle("Statistique");
        barModel.setLegendPosition("ne");

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Les années");

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Montant");
        yAxis.setMin(0);
        yAxis.setMax(200);
    }

    private void createBarModels() {
        createBarModel();
    }

    public ChartView() {
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

}
