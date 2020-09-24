package br.com.simulado.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.simulado.models.SubCategoria;

@FacesConverter(value = "SubCategoriaConverter", forClass = SubCategoria.class)
public class ConverterSubCategoria implements Converter {

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
				SubCategoria entity = (SubCategoria) value;

				if (entity.getId() != null) {
					this.addAttribute(componente, entity);

					if (entity.getId() != null) {
						return String.valueOf(entity.getId());
					}

					return (String) value;
				}
			}

			return "";
		}

		private void addAttribute(UIComponent componente, SubCategoria entity) {
			String key = entity.getId().toString();
			this.getAttributesFrom(componente).put(key, entity);
		}

		private Map<String, Object> getAttributesFrom(UIComponent componente) {
			return componente.getAttributes();
		}

}
