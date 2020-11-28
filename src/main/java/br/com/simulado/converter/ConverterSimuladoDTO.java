package br.com.simulado.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.simulado.models.dto.SimuladoDTO;

@FacesConverter(value = "SimuladoDtoConverter", forClass = SimuladoDTO.class)
public class ConverterSimuladoDTO implements Converter {

		@Override
		public Object getAsObject(FacesContext ctx, UIComponent componente, String value) {
			if (value != null) {
				return componente.getAttributes().get(value);
			}

			return null;
		}

		@Override
		public String getAsString(FacesContext ctx, UIComponent componente, Object value) {

			if (value != null) {
				SimuladoDTO entity = (SimuladoDTO) value;

				if (entity.getIdSimulado() != null) {
					this.addAttribute(componente, entity);

					if (entity.getIdSimulado() != null) {
						return String.valueOf(entity.getIdSimulado());
					}

					return (String) value;
				}
			}

			return "";
		}

		private void addAttribute(UIComponent componente, SimuladoDTO entity) {
			String key = entity.getIdSimulado().toString();
			this.getAttributesFrom(componente).put(key, entity);
		}

		private Map<String, Object> getAttributesFrom(UIComponent componente) {
			return componente.getAttributes();
		}

}
