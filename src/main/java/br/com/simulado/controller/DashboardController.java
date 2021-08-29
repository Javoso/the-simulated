package br.com.simulado.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;

import br.com.simulado.models.Categoria;
import br.com.simulado.models.Simulado;
import br.com.simulado.models.Tentativa;
import br.com.simulado.models.Usuario;
import br.com.simulado.models.dto.SimuladoDTO;
import br.com.simulado.security.Logado;
import br.com.simulado.service.CategoriaService;
import br.com.simulado.service.SimuladoRespondidoService;
import br.com.simulado.service.SimuladoService;
import br.com.simulado.service.TentativaService;
import br.com.simulado.util.AES;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class DashboardController extends AbstractController {

	private static final long serialVersionUID = 8770610991326531611L;

	@Inject
	@Logado
	private Usuario estudanteLogado;

	@Inject
	private TentativaService tentativaService;

	@Inject
	private CategoriaService categoriaService;

	@Inject
	private SimuladoRespondidoService simuladoRespondidoService;

	@Inject
	private SimuladoService simuladoService;

	@Getter
	@Setter
	private SimuladoDTO simuladoDTO = new SimuladoDTO();

	@Getter
	@Setter
	private List<SimuladoDTO> simuladosDTO = new ArrayList<>();

	@Getter
	@Setter
	private List<Simulado> simulados = new ArrayList<>();

	@Getter
	@Setter
	private List<Tentativa> tentativas = new ArrayList<>();

	@Getter
	@Setter
	private List<Categoria> categorias = new ArrayList<>();

	@Getter
	@Setter
	private BarChartModel barModel;

	@Getter
	@Setter
	private BarChartModel barModelSimulado;

	@Override
	public void init() {
		// simuladosDTO =
		// simuladoRespondidoService.simuladosPorEstudante(estudanteLogado);
		String id = getParamName("idT");
		if (id != null) {
			System.out.println("Criptando");
			System.out.println(id);
			String decript = new AES().decodificar(id);
			System.out.println("Decriptando");
			System.out.println(decript);
		}
		simulados = simuladoService.simulados();
		categorias = categoriaService.categorias();
		gerarRankingDeCategoriasMaisDificeis();
		gerarRankingDeSimuladoMaisDificeis();
	}

	public boolean isListTentativasIsNotEmpty() {
		return !tentativas.isEmpty();
	}

	public void pesquisar() {
		try {
			tentativas = tentativaService.findBySimulado(simuladoDTO.getIdSimulado());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void gerarRankingDeCategoriasMaisDificeis() {
		barModel = new BarChartModel();
		ChartData data = new ChartData();

		BarChartDataSet barDataSet = new BarChartDataSet();
		barDataSet.setLabel("Ranking de Categorias mais dificeis");

		List<Number> values = new ArrayList<>();

		barDataSet.setBackgroundColor("#4BC0C0");
		data.addChartDataSet(barDataSet);

		List<String> labels = new ArrayList<>();
		categorias.forEach(categoria -> {
			labels.add(categoria.getNome());
			values.add(new Random().nextInt(50));
		});

		barDataSet.setData(values);
		data.setLabels(labels);
		barModel.setData(data);

		// Options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		CartesianLinearTicks ticks = new CartesianLinearTicks();
		ticks.setBeginAtZero(true);
		linearAxes.setTicks(ticks);
		cScales.addYAxesData(linearAxes);
		options.setScales(cScales);

		Title title = new Title();
		title.setDisplay(true);
		title.setText("Bar Chart");
		options.setTitle(title);

		Legend legend = new Legend();
		legend.setDisplay(true);
		legend.setPosition("top");
		LegendLabel legendLabels = new LegendLabel();
		legendLabels.setFontStyle("bold");
		legendLabels.setFontColor("#2980B9");
		legendLabels.setFontSize(17);
		legend.setLabels(legendLabels);
		options.setLegend(legend);

		barModel.setOptions(options);

	}
	
	public void gerarRankingDeSimuladoMaisDificeis() {
		barModelSimulado = new BarChartModel();
		ChartData data = new ChartData();

		BarChartDataSet barDataSet = new BarChartDataSet();
		barDataSet.setLabel("Ranking dos Simulado mais dificeis");

		List<Number> values = new ArrayList<>();

		barDataSet.setBackgroundColor("#4BC0C0");
		data.addChartDataSet(barDataSet);

		List<String> labels = new ArrayList<>();
		simulados.forEach(simulado -> {
			labels.add(simulado.getNome());
			values.add(new Random().nextInt(50));
		});

		barDataSet.setData(values);
		data.setLabels(labels);
		barModelSimulado.setData(data);

		// Options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		CartesianLinearTicks ticks = new CartesianLinearTicks();
		ticks.setBeginAtZero(true);
		linearAxes.setTicks(ticks);
		cScales.addYAxesData(linearAxes);
		options.setScales(cScales);

		Title title = new Title();
		title.setDisplay(true);
		title.setText("Bar Chart");
		options.setTitle(title);

		Legend legend = new Legend();
		legend.setDisplay(true);
		legend.setPosition("top");
		LegendLabel legendLabels = new LegendLabel();
		legendLabels.setFontStyle("bold");
		legendLabels.setFontColor("#2980B9");
		legendLabels.setFontSize(17);
		legend.setLabels(legendLabels);
		options.setLegend(legend);

		barModelSimulado.setOptions(options);

	}

	public int getNumeroDeQuestoes() {
		return 70;
	}

	public int getNumeroDeSimulados() {
		return 20;
	}

	public int getNumeroDeConteudoDeApoioPorCategoria() {
		return 120;
	}

	public String getCategoriaDeConteudoMaisVisto() {
		return "Matemática";
	}

}
