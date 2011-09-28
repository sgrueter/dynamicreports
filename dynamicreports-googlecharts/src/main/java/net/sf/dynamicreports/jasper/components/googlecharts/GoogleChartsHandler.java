/**
 * DynamicReports - Free Java reporting library for creating reports dynamically
 *
 * Copyright (C) 2010 - 2011 Ricardo Mariaca
 * http://dynamicreports.sourceforge.net
 *
 * This file is part of DynamicReports.
 *
 * DynamicReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DynamicReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with DynamicReports. If not, see <http://www.gnu.org/licenses/>.
 */

package net.sf.dynamicreports.jasper.components.googlecharts;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.component.Component;
import net.sf.jasperreports.engine.component.ComponentKey;
import net.sf.jasperreports.engine.component.ComponentXmlWriter;
import net.sf.jasperreports.engine.component.ComponentsEnvironment;
import net.sf.jasperreports.engine.component.XmlDigesterConfigurer;
import net.sf.jasperreports.engine.export.GenericElementHandler;
import net.sf.jasperreports.engine.export.GenericElementHandlerBundle;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.util.JRXmlWriteHelper;
import net.sf.jasperreports.engine.util.XmlNamespace;
import net.sf.jasperreports.engine.xml.JRXmlWriter;

import org.apache.commons.digester.Digester;

/**
 * @author Ricardo Mariaca (dynamicreports@gmail.com)
 */
public class GoogleChartsHandler implements XmlDigesterConfigurer, ComponentXmlWriter, GenericElementHandlerBundle {
	private Map<String, Class<? extends Component>> components;
	private Map<String, GenericElementHandler> handlers;

	public GoogleChartsHandler() {
		components = new HashMap<String, Class<? extends Component>>();
		handlers = new HashMap<String, GenericElementHandler>();
	}

	public void add(String name, Class<? extends Component> componentClass, GenericElementHandler handler) {
		components.put(name, componentClass);
		handlers.put(name, handler);
	}

	public void configureDigester(Digester digester) {
		for (String name : components.keySet()) {
			String mapPattern = "*/componentElement/googleCharts/" + name;
			digester.addObjectCreate(mapPattern, components.get(name));
		}
	}

	public void writeToXml(ComponentKey componentKey, Component component, JRXmlWriter reportWriter) throws IOException {
		if (components.containsKey(componentKey.getName())) {
			JRXmlWriteHelper writer = reportWriter.getXmlWriteHelper();

			String namespaceURI = componentKey.getNamespace();
			String schemaLocation = ComponentsEnvironment.getComponentsBundle(namespaceURI).getXmlParser().getPublicSchemaLocation();
			XmlNamespace namespace = new XmlNamespace(namespaceURI, componentKey.getNamespacePrefix(), schemaLocation);

			writer.startElement(componentKey.getName(), namespace);

			writer.closeElement();
		}
	}

	public String getNamespace() {
		return GoogleChartsExtensionsRegistryFactory.NAMESPACE;
	}

	public GenericElementHandler getHandler(String elementName, String exporterKey) {
		if (handlers.containsKey(elementName)) {
			if (JRHtmlExporter.HTML_EXPORTER_KEY.equals(exporterKey)
					|| JRXhtmlExporter.XHTML_EXPORTER_KEY.equals(exporterKey)) {
				return handlers.get(elementName);
			}
		}
		return null;
	}
}