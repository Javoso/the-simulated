package br.com.simulado.controller.pesquisar;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;

import br.com.simulado.controller.AbstractController;
import br.com.simulado.models.Categoria;
import br.com.simulado.models.Simulado;
import br.com.simulado.models.SubCategoria;
import br.com.simulado.models.Tentativa;
import br.com.simulado.models.Usuario;
import br.com.simulado.security.Logado;
import br.com.simulado.service.CategoriaService;
import br.com.simulado.service.SimuladoService;
import br.com.simulado.service.SubCategoriaService;
import br.com.simulado.service.TentativaService;
import br.com.simulado.service.UsuarioService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class PesquisarTentativaController extends AbstractController {

	private static final long serialVersionUID = 7230395733393389515L;

	@Getter
	@Setter
	private Simulado simulado = new Simulado();

	@Getter
	@Setter
	private float quantidadeDeQuestoesAcertadas;

	@Getter
	@Setter
	private float quantidadeDeQuestoesErradas;

	@Getter
	@Setter
	private int totalDeQuestoes;

	@Getter
	@Setter
	private Usuario estudante = new Usuario();

	@Inject
	@Logado
	private Usuario usuarioLogado;

	@Getter
	@Setter
	private Tentativa tentativa = new Tentativa();

	@Getter
	@Setter
	private Categoria categoriaComMaiorIndiceDeErro = new Categoria();

	@Getter
	@Setter
	private Categoria categoriaComMaiorIndiceDeAcerto = new Categoria();

	@Inject
	private TentativaService tentativaService;

	@Inject
	private UsuarioService usuarioService;

	@Inject
	private SimuladoService simuladoService;

	@Inject
	private CategoriaService categoriaService;

	@Inject
	private SubCategoriaService subcategoriaService;

	@Getter
	@Setter
	private List<Categoria> categorias = new ArrayList<>();

	@Getter
	@Setter
	private List<SubCategoria> subcategoriasParaEstudo = new ArrayList<>();

	@Getter
	@Setter
	private DonutChartModel donutModel;

	@Getter
	@Setter
	private BarChartModel barModel;

	@Getter
	@Setter
	private String conteudoParaAjudar;

	@Getter
	@Setter
	private String conteudoParaMelhorar;

	@Override
	public void init() {

		String idSimulado = getParamNameDecodificado("simuladoId");
		String idUser = getParamNameDecodificado("userId");
		String idTentativa = getParamNameDecodificado("tentativaId");

		if (idSimulado != null && idUser != null) {
			try {
				simulado = simuladoService.findById(idSimulado);
				estudante = usuarioService.findById(idUser);
				tentativa = tentativaService.findById(idTentativa);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		categorias = categoriaService.categorias();

		initValues();
		createDonutModel();
		createBarModel();
		verificaCategoriaComMaiorIndexDeErro();
		verificaCategoriaComMaiorIndexDeAcerto();

		if (nonNull(categoriaComMaiorIndiceDeAcerto.getId()) && nonNull(categoriaComMaiorIndiceDeErro.getId())) {
			try {
				subcategoriasParaEstudo = subcategoriaService.subCategoriasPorCategoria(categoriaComMaiorIndiceDeErro);
			} catch (Exception e) {
				e.printStackTrace();
			}
			conteudoParaAjudar = "../conteudo/conteudo-apoio.xhtml?watch="
					+ categoriaComMaiorIndiceDeErro.codificarId();
			conteudoParaMelhorar = "../conteudo/conteudo-apoio.xhtml?watch="
					+ categoriaComMaiorIndiceDeAcerto.codificarId();
		}
	}

	public void createDonutModel() {
		donutModel = new DonutChartModel();
		ChartData data = new ChartData();

		DonutChartDataSet dataSet = new DonutChartDataSet();
		List<Number> values = new ArrayList<>();
		values.add(porcentagemDeAcerto());
		values.add(porcentagemDeErro());
		dataSet.setData(values);

		List<String> bgColors = new ArrayList<>();
		bgColors.add("#337AB7");
		bgColors.add("#D9534F");
		dataSet.setBackgroundColor(bgColors);

		data.addChartDataSet(dataSet);
		List<String> labels = new ArrayList<>();
		labels.add("Acerto");
		labels.add("Erro");
		data.setLabels(labels);

		donutModel.setData(data);
	}

	public void createBarModel() {

		List<Number> valoresParaCategoriasComRespostasAcertadas = new ArrayList<>();
		List<Number> valoresParaCategoriasComRespostasErradas = new ArrayList<>();
		List<String> corParaCategoriasComRespostasAcertadas = new ArrayList<>();
		List<String> corParaCategoriasComRespostasErradas = new ArrayList<>();
		List<String> titles = new ArrayList<>();

		barModel = new BarChartModel();
		ChartData data = new ChartData();

		BarChartDataSet barDataSetCorretas = new BarChartDataSet();
		BarChartDataSet barDataSetErradas = new BarChartDataSet();

		barDataSetCorretas.setLabel("Corretas");
		barDataSetErradas.setLabel("Erradas");

		categorias.forEach(categoria -> {
			valoresParaCategoriasComRespostasAcertadas.add(respostasCorretasPorCategoria(categoria));
			valoresParaCategoriasComRespostasErradas.add(respostasErradasPorCategoria(categoria));
			titles.add(categoria.getNome());
			corParaCategoriasComRespostasAcertadas.add("#337AB7");
			corParaCategoriasComRespostasErradas.add("#D9534F");
		});

		barDataSetCorretas.setData(valoresParaCategoriasComRespostasAcertadas);
		barDataSetErradas.setData(valoresParaCategoriasComRespostasErradas);

		barDataSetCorretas.setBorderColor(corParaCategoriasComRespostasAcertadas);
		barDataSetCorretas.setBackgroundColor(corParaCategoriasComRespostasAcertadas);
		barDataSetCorretas.setBorderWidth(1);

		barDataSetErradas.setBorderColor(corParaCategoriasComRespostasErradas);
		barDataSetErradas.setBackgroundColor(corParaCategoriasComRespostasErradas);
		barDataSetErradas.setBorderWidth(1);

		data.addChartDataSet(barDataSetCorretas);
		data.addChartDataSet(barDataSetErradas);

		data.setLabels(titles);
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

		Legend legend = new Legend();
		legend.setDisplay(true);
		legend.setPosition("top");
		LegendLabel legendLabels = new LegendLabel();
		legendLabels.setFontStyle("bold");
		legendLabels.setFontColor("#2980B9");
		legendLabels.setFontSize(24);
		legend.setLabels(legendLabels);
		options.setLegend(legend);

		barModel.setOptions(options);
	}

	float valueMaior = 0;
	float valueMenor = 0;
	float value = 0;
	float valueTotal = 0;

	public void verificaCategoriaComMaiorIndexDeErro() {
		valueMaior = 0;
		valueMenor = 0;
		value = 0;
		valueTotal = 0;
		categorias.forEach(categoria -> {
			valueTotal = respostasPorCategoria(categoria);
			value = respostasErradasPorCategoria(categoria);
			value = ((value * 100) / valueTotal);
			if (value < valueMenor)
				valueMenor = value;
			if (value > valueMaior) {
				valueMaior = value;
				categoriaComMaiorIndiceDeErro = categoria;
			}
		});

	}

	public void verificaCategoriaComMaiorIndexDeAcerto() {
		valueMaior = 0;
		valueMenor = 0;
		value = 0;
		valueTotal = 0;
		categorias.forEach(categoria -> {
			valueTotal = respostasPorCategoria(categoria);
			value = respostasCorretasPorCategoria(categoria);
			value = ((value * 100) / valueTotal);
			if (value < valueMenor)
				valueMenor = value;
			if (value > valueMaior) {
				valueMaior = value;
				categoriaComMaiorIndiceDeAcerto = categoria;
			}
		});
	}

	public int respostasPorCategoria(Categoria categoria) {
		return tentativa.getRespostas().stream()
				.filter(resposta -> resposta.getQuestaoSelecionada().categoriaIgual(categoria))
				.collect(Collectors.toList()).size();
	}

	public int respostasCorretasPorCategoria(Categoria categoria) {
		return tentativa.getRespostas().stream()
				.filter(resposta -> resposta.getQuestaoSelecionada().categoriaIgual(categoria))
				.filter(resposta -> resposta.getAlternativaEscolhida().eCorreta()).collect(Collectors.toList()).size();
	}

	public int respostasErradasPorCategoria(Categoria categoria) {
		return tentativa.getRespostas().stream()
				.filter(resposta -> resposta.getQuestaoSelecionada().categoriaIgual(categoria))
				.filter(resposta -> !resposta.getAlternativaEscolhida().eCorreta()).collect(Collectors.toList()).size();
	}

	public int totalDeQuestoesAcertadas() {
		return tentativa.getRespostas().stream().filter(resposta -> resposta.getAlternativaEscolhida().eCorreta())
				.collect(Collectors.toList()).size();
	}

	public void initValues() {
		this.totalDeQuestoes = tentativa.getRespostas().size();
		this.quantidadeDeQuestoesAcertadas = tentativa.getRespostas().stream()
				.filter(resposta -> resposta.getAlternativaEscolhida().eCorreta()).collect(Collectors.toList()).size();
		this.quantidadeDeQuestoesErradas = totalDeQuestoes - quantidadeDeQuestoesAcertadas;
	}

	public float porcentagemDeAcerto() {
		if (quantidadeDeQuestoesAcertadas != 0 && totalDeQuestoes != 0) {
			return (float) ((quantidadeDeQuestoesAcertadas * 100) / totalDeQuestoes);
		} else {
			return 0;
		}
	}

	public float porcentagemDeErro() {
		if (quantidadeDeQuestoesErradas != 0 && totalDeQuestoes != 0) {
			return (float) ((quantidadeDeQuestoesErradas * 100) / totalDeQuestoes);
		} else {
			return 0;
		}
	}

}
